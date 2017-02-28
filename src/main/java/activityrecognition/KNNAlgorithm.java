package activityrecognition;

import activityrecognition.dao.ClassificationAlgorithmsDao;
import activityrecognition.daoImpl.ClassificationAlgorithmsImpl;
import activityrecognition.entity.KNNNode;

import java.util.*;

/**
 * KNN算法主体类
 * 
 */
public class KNNAlgorithm {
	/**
	 * 设置优先级队列的比较函数，距离越大，优先级越高
	 */
	private Comparator<KNNNode> comparator = new Comparator<KNNNode>() {
		public int compare(KNNNode o1, KNNNode o2) {
			if (o1.getDistance() >= o2.getDistance()) {
				return -1;
			} else {
				return 1;
			}
		}
	};
	/**
	 * 获取K个不同的随机数
	 * @param k 随机数的个数
	 * @param max 随机数最大的范围
	 * @return 生成的随机数数组
	 */
	public List<Integer> getRandKNum(int k, int max) {
		List<Integer> rand = new ArrayList<Integer>(k);
		for (int i = 0; i < k; i++) {
			int temp = (int) (Math.random() * max);
			if (!rand.contains(temp)) {
				rand.add(temp);
			} else {
				i--;
			}
		}
		return rand;
	}
	/**
	 * 计算测试元组与训练元组之前的距离
	 * @param d1 测试元组
	 * @param d2 训练元组
	 * @return 距离值
	 */
	public double calDistance(List<Double> d1, List<Double> d2) {
		double distance = 0.00;
		for (int i = 0; i < d1.size(); i++) {
			distance += (d1.get(i) - d2.get(i)) * (d1.get(i) - d2.get(i));
		}
		return distance;
	}
	/**
	 * 执行KNN算法，获取测试元组的类别
	 * @param datas 训练数据集
	 * @param testData 测试元组
	 * @param k 设定的K值
	 * @return 测试元组的类别
	 */
	public String knn(List<List<Double>> datas, List<Double> testData, int k) {
		PriorityQueue<KNNNode> pq = new PriorityQueue<KNNNode>(k, comparator);
		List<Integer> randNum = getRandKNum(k, datas.size());
		for (int i = 0; i < k; i++) {
			int index = randNum.get(i);
			List<Double> currData = datas.get(index);
			String c = currData.get(currData.size() - 1).toString();
			KNNNode node = new KNNNode(index, calDistance(testData, currData), c);
			pq.add(node);
		}
		for (int i = 0; i < datas.size(); i++) {
			List<Double> t = datas.get(i);
			double distance = calDistance(testData, t);
			KNNNode top = pq.peek();
			if (top.getDistance() > distance) {
				pq.remove();
				pq.add(new KNNNode(i, distance, t.get(t.size() - 1).toString()));
			}
		}
		
		return getMostClass(pq);
	}
	/**
	 * 获取所得到的k个最近邻元组的多数类
	 * @param pq 存储k个最近近邻元组的优先级队列
	 * @return 多数类的名称
	 */
	private String getMostClass(PriorityQueue<KNNNode> pq) {
		Map<String, Integer> classCount = new HashMap<String, Integer>();
		for (int i = 0; i < pq.size(); i++) {
			KNNNode node = pq.remove();
			i--;
			String c = node.getLabel();
			if (classCount.containsKey(c)) {
				classCount.put(c, classCount.get(c) + 1);
			} else {
				classCount.put(c, 1);
			}
		}
		int maxIndex = -1;
		int maxCount = 0;
		Object[] classes = classCount.keySet().toArray();
		for (int i = 0; i < classes.length; i++) {
			if (classCount.get(classes[i]) > maxCount) {
				maxIndex = i;
				maxCount = classCount.get(classes[i]);
			}
		}
		return classes[maxIndex].toString();
	}
	
	public void startKNN(int[] selectedColumn){
		// 创建ClassificationAlgorithmsDao类
		ClassificationAlgorithmsDao dao = new ClassificationAlgorithmsImpl();
		// 存储训练集数据
		List<List<Double>> trainList = null;
		// 存储测试集数据
		List<List<Double>> testList = null;


		// 十折交叉验证
		for(int i = 0; i < 1; i++){
//			// 查询测试数据信息的sql语句
//			String sqlFindTest = "select * from featureextraction where Id between " + (i*2802+1) + " and " + ((i+1)*2802) + ";";
//			// 执行查询操作
//			testList = dao.search(sqlFindTest);
//
//			// 查询训练数据信息的sql语句(前半部分)
//			String sqlFindTrain1 = "select * from featureextraction where Id between 0 and " + (i*2802) + ";";
//			// 执行查询操作
//			trainList = dao.search(sqlFindTrain1);
//			// 查询训练数据信息的sql语句(后半部分)
//			String sqlFindTrain2 = "select * from featureextraction where Id between " + ((i+1)*2802+1) + " and 28025;";
//			// 执行查询操作
//			trainList.addAll(dao.search(sqlFindTrain2));

		    String sqlFindTest1 = "select * from featureextraction_stand32_33 where Id > 7000;";
		    testList = dao.search(sqlFindTest1, selectedColumn);
		    String sqlFindTest2 = "select * from featureextraction_sit32_33 where Id > 2000;";
		    testList.addAll(dao.search(sqlFindTest2, selectedColumn));

		    String sqlFindTrain1 = "select * from featureextraction_stand32_33 where Id < 7001;";
		    trainList = dao.search(sqlFindTrain1, selectedColumn);
		    String sqlFindTrain2 = "select * from featureextraction_sit32_33 where Id < 2001;";
		    trainList.addAll(dao.search(sqlFindTrain2, selectedColumn));

			// 正确分类的数据量
			int correctClassify = 0;
			
			// 通过KNN算法进行分类
			for(int j = 0; j < testList.size(); j++){
				// 从测试数据列表中取出单个测试数据信息
				 List<Double> test = testList.get(j);    
//	             System.out.print("类别为: ");  
//	             System.out.println(Math.round(Float.parseFloat((knn(trainList, test, 3)))));
				 String knnClassify = String.valueOf((double)Math.round(Float.parseFloat((knn(trainList, test, 3)))));
				 String realClassify = String.valueOf(test.get(test.size()-1));
				 if(knnClassify.equals(realClassify)){
					 correctClassify++;
				 }
			}
			System.out.println("测试数据量为" + testList.size() + "，正确分类数据量为" + correctClassify
					+ "，识别率为" + ((double)correctClassify / testList.size()));
		}
	}
}

