package activityrecognition.svm;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import activityrecognition.dao.ClassificationAlgorithmsDao;
import activityrecognition.daoImpl.ClassificationAlgorithmsImpl;

public class LibSVMTest {

	public void startSVM(int[] selectedColumn){
		// 创建DSDataFusionDao类
		ClassificationAlgorithmsDao dao = new ClassificationAlgorithmsImpl();
		// 存储训练集数据
		List<List<Double>> trainList = null;
		// 存储测试集数据
		List<List<Double>> testList = null;

		int walk_num = 502;
		int stand_num = 973;
		int lie_num = 34;
		int sit_num = 305;

		double accuracy_final = 0.0;


		// 十折交叉验证
		for(int i = 0; i < 1; i++){

			String sqlFindTest1 = "select * from featureextraction_stand32_33 where Id > 7000;";
			testList = dao.search(sqlFindTest1, selectedColumn);
			String sqlFindTest2 = "select * from featureextraction_sit32_33 where Id > 2000;";
			testList.addAll(dao.search(sqlFindTest2, selectedColumn));

			String sqlFindTrain1 = "select * from featureextraction_stand32_33 where Id < 7001;";
			trainList = dao.search(sqlFindTrain1, selectedColumn);
			String sqlFindTrain2 = "select * from featureextraction_sit32_33 where Id < 2001;";
			trainList.addAll(dao.search(sqlFindTrain2, selectedColumn));

		// 向UCI-breast-cancer-test文件写入测试数据
		saveDataAsFile(testList, "UCI-breast-cancer-test");

		// 向UCI-breast-cancer-tra文件写入测试数据
		saveDataAsFile(trainList, "UCI-breast-cancer-tra");

			try {
				String[] trainArgs = {"-b", "1", "UCI-breast-cancer-tra"};
				String modelFile = svm_train.main(trainArgs);
				String[] testArgs = {"-b", "1", "UCI-breast-cancer-test", modelFile, "UCI-breast-cancer-result"};//directory of test file, model file, result file
				Double accuracy = svm_predict.main(testArgs);
				System.out.println("识别率为" + accuracy);
				accuracy_final += accuracy;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.toString());
			}

			clearFile("UCI-breast-cancer-tra");
			clearFile("UCI-breast-cancer-test");
			clearFile("UCI-breast-cancer-result");
			clearFile("UCI-breast-cancer-tra.model");

	}
	}
	
	
	    // 文件存储
		private static boolean saveDataAsFile(List<List<Double>> dataList, String fileName){
			FileWriter writer;
			try {
				// 通过保存文件的路径及其文件名称初始化FileWriter对象
				writer = new FileWriter(fileName, true);
				// 向文件写入测试数据
				for(int j = 0; j < dataList.size(); j++){
					// 从测试数据列表中取出单个测试数据信息
					 List<Double> test = dataList.get(j); 
					 StringBuilder sb = new StringBuilder();
					 int size = test.size() - 1;
					 sb.append(test.get(size));
					 sb.append("	");
					 for(int k = 0; k < size; k++){
						 sb.append((k+1) + ":");
						 sb.append(test.get(k));
						 sb.append("	");
					 }
					 writer.write(sb.toString()  + "\n");
				}
				writer.close();
				return true;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		// 清除文件内容
		private static boolean clearFile(String fileName){
			boolean result = false;
			try {
				FileWriter writer = new FileWriter(fileName, true);
				writer.write("");
				writer.close();result = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
}
