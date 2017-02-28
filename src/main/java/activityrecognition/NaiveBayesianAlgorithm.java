package activityrecognition;

import activityrecognition.dao.ClassificationAlgorithmsDao;
import activityrecognition.daoImpl.ClassificationAlgorithmsImpl;

import java.util.List;

/**
 * 朴素贝叶斯分类算法（连续型）
 * @author Administrator
 *
 */
public class NaiveBayesianAlgorithm {
	// 动作stand每一个特征属性的均值和方差
	private double[] meanStand = null;
	private double[] varianceStand = null;
	// 动作walk每一个特征属性的均值和方差
	private double[] meanWalk = null;
	private double[] varianceWalk = null;
	// 动作sit每一个特征属性的均值和方差
	private double[] meanSit = null;
	private double[] varianceSit = null;
	// 动作lie每一个特征属性的均值和方差
	private double[] meanLie = null;
	private double[] varianceLie = null;
	// 创建ClassificationAlgorithmsDao类
	ClassificationAlgorithmsDao dao = new ClassificationAlgorithmsImpl();

	int walk_num = 502;
	int stand_num = 973;
	int lie_num = 34;
	int sit_num = 305;
	double accuracy_final = 0.0;

	// NBC算法:利用训练数据训练NBC识别模型
	public void trainNBC(int[] selectedColumn) {
//		// 对于locomotion = 1
//		// 查询相关数据
//		String sqlStand = "select * from featureextraction where locomotion = 1;";
//		List<List<Double>> listStand = dao.search(sqlStand);
//		// 计算均值
//		meanStand = getMean(listStand);
//		// 计算方差
//		varianceStand = getVariance(listStand, meanStand);
//
//		// 对于locomotion = 2
//		// 查询相关数据
//		String sqlWalk = "select * from featureextraction where locomotion = 2;";
//		List<List<Double>> listWalk = dao.search(sqlWalk);
//		// 计算均值
//		meanWalk = getMean(listWalk);
//		// 计算方差
//		varianceWalk = getVariance(listWalk, meanWalk);
//
//		// 对于locomotion = 4
//		// 查询相关数据
//		String sqlSit = "select * from featureextraction where locomotion = 4;";
//		List<List<Double>> listSit = dao.search(sqlSit);
//		// 计算均值
//		meanSit = getMean(listSit);
//		// 计算方差
//		varianceSit = getVariance(listSit, meanSit);
//
//		// 对于locomotion = 5
//		// 查询相关数据
//		String sqlLie = "select * from featureextraction where locomotion = 5;";
//		List<List<Double>> listLie = dao.search(sqlLie);
//		// 计算均值
//		meanLie = getMean(listLie);
//		// 计算方差
//		varianceLie = getVariance(listLie, meanLie);

		// 查询相关数据
		String sqlStand = "select * from featureextraction_stand32_33 where Id < 7001;";
		List<List<Double>> listStand = dao.search(sqlStand, selectedColumn);
		// 计算均值
		meanStand = getMean(listStand, selectedColumn);
		// 计算方差
		varianceStand = getVariance(listStand, meanStand, selectedColumn);

		// 查询相关数据
		String sqlWalk = "select * from featureextraction_sit32_33 where Id < 2001;";
		List<List<Double>> listWalk = dao.search(sqlWalk, selectedColumn);
		// 计算均值
		meanWalk = getMean(listWalk, selectedColumn);
		// 计算方差
		varianceWalk = getVariance(listWalk, meanWalk, selectedColumn);
	}
	
	public double testNBC(List<Double> test, int[] selectedColumn){
		// 识别率
		double resultStand = 1;
		double resultWalk = 1;
//		double resultSit = 1;
//		double resultLie = 1;
		
		// 对于locomotion = 1
		for(int i = 0; i < selectedColumn.length; i++){
			resultStand *= gaussianDensity(meanStand[i], varianceStand[i], test.get(i));
		}
		
		
		// 对于locomotion = 2
		for(int i = 0; i < selectedColumn.length; i++){
			resultWalk *= gaussianDensity(meanWalk[i], varianceWalk[i], test.get(i));
		}
		
		
//		// 对于locomotion = 4
//		for(int i = 0; i < featureDimension; i++){
//			resultSit *= gaussianDensity(meanSit[i], varianceSit[i], test.get(i));
//		}
//
//
//		// 对于locomotion = 5
//		for(int i = 0; i < featureDimension; i++){
//			resultLie *= gaussianDensity(meanLie[i], varianceLie[i], test.get(i));
//		}
		
//		return Max(resultStand, resultWalk, resultSit, resultLie);
		return resultStand > resultWalk ? 1 : 4;
	}
	
	// 计算均值
	public double[] getMean(List<List<Double>> listStand, int[] selectedColumn){
		// 存放均值的数组
		double[] mean = new double[selectedColumn.length];
		
		for(int i = 0; i < listStand.size(); i++){
			for(int j = 0; j < selectedColumn.length; j++){
				mean[j] += listStand.get(i).get(j);
			}
		}
		for(int i = 0; i < selectedColumn.length; i++){
			mean[i] = mean[i] / listStand.size();
		}
		return mean;
	}
	
	// 计算方差
	public double[] getVariance(List<List<Double>> listStand, double[] mean, int[] selectedColumn){
		// 存放方差的数组
		double[] variance = new double[selectedColumn.length];
		
		for(int i = 0; i < listStand.size(); i++){
			for(int j = 0; j < selectedColumn.length; j++){
				variance[j] += Math.pow((listStand.get(i).get(j) - mean[j]), 2);
			}
		}
		for(int i = 0; i < selectedColumn.length; i++){
			variance[i] = variance[i] / listStand.size();
		}
		return variance;
	}

    /** 高斯概率密度
     * 
     * @param mean   样本所在列的均值
     * @param variance   样本方差
     * @param x   样本的取值
     * @return
     */
    public double gaussianDensity(double mean, double variance, double x)
    {
        return 1 / Math.sqrt(2 * Math.PI * variance) * Math.pow(Math.E, -(x - mean) * (x - mean) / (2 * variance));
    }
    
    // 四个值之间比较大小
    public double Max(double a, double b, double c, double d)
    {
        double temp;
        if (a >= b) temp = a; else temp =b;
        if (c >= temp) temp = c;
        if (d >= temp) temp = d;
        
        if(temp == a){
        	return 1;
        } if(temp == b){
        	return 2;
        } if(temp == c){
        	return 4;
        } if(temp == d){
        	return 5;
        }else{
        	return 0;
        }
    }
    
    
 // KNN分类
 	public void startNBC(int[] selectedColumn){
 		// 存储测试集数据
 		List<List<Double>> testList = null;

 		// 十折交叉验证7003
 		for(int i = 0; i < 1; i++){
 			// 训练NBC模型
			trainNBC(selectedColumn);

			String sqlFindTest1 = "select * from featureextraction_stand32_33 where Id > 7000;";
			testList = dao.search(sqlFindTest1, selectedColumn);
			String sqlFindTest2 = "select * from featureextraction_sit32_33 where Id > 2000;";
			testList.addAll(dao.search(sqlFindTest2, selectedColumn));
 			// 正确分类的数据量
 			int correctClassify = 0;

 			// 通过NBC算法进行分类
 			for(int j = 0; j < testList.size(); j++){
 				// 从测试数据列表中取出单个测试数据信息
 				 List<Double> test = testList.get(j);
// 	             System.out.print("类别为: ");
// 	             System.out.println(Math.round(Float.parseFloat((knn(trainList, test, 3)))));

 				 String nbcClassify = String.valueOf(testNBC(test, selectedColumn));
 				 String realClassify = String.valueOf(test.get(test.size()-1));
 				 if(nbcClassify.equals(realClassify)){
 					 correctClassify++;
 				 }
 			}
 			System.out.println("测试数据量为" + testList.size() + "，正确分类数据量为" + correctClassify
 					+ "，识别率为" + ((double)correctClassify / testList.size()));
 		}
 	}
}
