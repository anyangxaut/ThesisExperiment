package activityrecognition;

import activityrecognition.svm.LibSVMTest;

/**
 * Created by anyang on 2016/12/22.
 */
public class ActivityRecognition {

    public static void main(String[] args) {
        // 数据预处理
//        DataPreprocess dataPreprocess = new DataPreprocess("E:\\IntellijIDEA\\ThesisExperiment\\dataset\\");
//        dataPreprocess.startPreprocess();

//        // 将不同的动作类型数据存储在不同的表中
//        SplitLocomotion splitLocomotion = new SplitLocomotion();
//        splitLocomotion.startSplit();
//        SplitLocomotion33 splitLocomotion33 = new SplitLocomotion33();
//        splitLocomotion33.startSplit();

        // 窗口划分，特征提取
//		FeatureExtraction featureExtraction = new FeatureExtraction(128, 0.5);
//        featureExtraction.startFeatureExtraction();
//        FeatureExtraction33 featureExtraction33 = new FeatureExtraction33(128, 0.5);
//        featureExtraction33.startFeatureExtraction();

        // 特征优选

        // 分类识别

        // 改进版BPSO+KNN(BPSO+相关系数)
        // Sit和lie:featureextraction_sit32_33,featureextraction_lie32_33
//         全选：int[] selectedColumn = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
//                17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33};
//        1:int[] selectedColumn = new int[]{2, 3, 6, 8, 9, 10, 12, 14, 19, 21, 22, 23, 24, 26, 27, 28, 29, 30};
//        2:int[] selectedColumn = new int[]{2, 3, 8, 10, 11, 12, 13, 15, 19, 20, 21, 22, 23, 24, 28, 29, 30};
//        3:int[] selectedColumn = new int[]{1, 4, 6, 8, 9, 13, 14, 16, 21, 23, 25, 27, 28, 29, 31, 33};
//        4:int[] selectedColumn = new int[]{1, 2, 6, 7, 8, 11, 13, 19, 20, 21, 22, 23, 24, 26, 27, 28, 29, 30};
//        5:int[] selectedColumn = new int[]{1, 3, 12, 13, 15, 20, 21, 22, 30};
//       6:int[] selectedColumn = new int[]{1, 9, 10, 12, 13, 20, 24, 28, 30};
//        7:int[] selectedColumn = new int[]{1, 3, 9, 12, 15, 19, 20, 21, 23, 24, 30};
//        8:int[] selectedColumn = new int[]{1, 7, 9, 12, 13,  15, 19, 20, 21, 24, 30};

        // walk和lie:featureextraction_walk32_33,featureextraction_lie32_33
//         全选：int[] selectedColumn = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
//                17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33};
//        1:int[] selectedColumn = new int[]{1, 3, 5, 6, 7, 10, 11, 12, 15, 16, 17, 19, 20, 22, 24, 25, 26, 27, 28, 29, 31, 32};
//        2:int[] selectedColumn = new int[]{1, 2, 11, 14, 15, 17, 19, 20, 21, 22, 23, 28, 29};
//        3:int[] selectedColumn = new int[]{1, 8, 14, 15, 21, 23, 29};
//        4:int[] selectedColumn = new int[]{2, 7, 8, 10, 15, 17, 19, 20, 21, 22, 23, 24, 28, 29, 30};
//        5:int[] selectedColumn = new int[]{6, 7, 11, 12, 13, 14, 21, 22, 24, 26, 28, 29, 31, 32};
//       6:int[] selectedColumn = new int[]{1, 3, 4, 5, 6, 7, 9, 13, 14, 18, 27, 28, 29, 30, 31, 32};
//        7:int[] selectedColumn = new int[]{3, 7, 8, 11, 12, 13, 15, 16, 19, 23, 24, 28, 31};
//        8:int[] selectedColumn = new int[]{2, 8, 9, 11, 13, 16, 19, 22, 30, 31};

        // walk和stand:featureextraction_walk32_33,featureextraction_stand32_33
//         全选：int[] selectedColumn = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
//                17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33};
//        1:int[] selectedColumn = new int[]{2, 3, 6, 7, 8, 12, 13, 15, 16, 17, 20, 21, 22, 23, 24, 26, 27, 28, 29, 30, 31, 32};
//        2:int[] selectedColumn = new int[]{3, 6, 7, 9, 11, 13, 15, 16, 17, 19, 22, 23, 24, 26, 27, 28, 31, 32};
//        3:int[] selectedColumn = new int[]{1, 2, 6, 11, 12, 13, 16, 17, 19, 24, 26, 27, 28, 30, 31, 32};
//        4:int[] selectedColumn = new int[]{6, 7, 9, 11, 15, 16, 17, 19, 24, 26, 27, 29, 30, 31, 32};
//        5:int[] selectedColumn = new int[]{3, 6, 7, 9, 10, 11, 12, 16, 17, 18, 20, 21, 23, 26, 27, 29, 30, 31, 32};
//       6:int[] selectedColumn = new int[]{3, 6, 8, 9, 10, 12, 13, 16, 17, 18, 20, 21, 23, 26, 27, 28, 31, 32};
//        7:int[] selectedColumn = new int[]{1, 6, 8, 9, 10, 11, 14, 15, 16, 18, 20, 21, 22, 24, 26, 27, 29, 30, 31, 32};
//        8:int[] selectedColumn = new int[]{1, 2, 3, 6, 11, 14, 16, 17, 18, 19, 20, 21, 26, 27, 29, 31, 32};

        // stand和lie:featureextraction_stand32_33,featureextraction_lie32_33
//         全选：int[] selectedColumn = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
//                17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33};
//        1:int[] selectedColumn = new int[]{1, 2, 4, 5, 8, 9, 10, 12, 13, 15, 18, 19, 20, 21, 23, 24, 25, 26, 27, 29, 31};
//        2:int[] selectedColumn = new int[]{1, 2, 4, 5, 7, 11, 12, 13, 14, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 31};
//        3:int[] selectedColumn = new int[]{2, 4, 5, 7, 10, 11, 12, 16, 18, 19, 20, 21, 23, 24, 25, 27, 29, 30, 31};
//        4:int[] selectedColumn = new int[]{1, 2, 4, 5, 7, 8, 9, 12, 13, 14, 15, 16, 19, 20, 21, 22, 24, 25, 28, 30, 31};
//        5:int[] selectedColumn = new int[]{1, 8, 10, 16, 20, 22, 23, 25, 26, 30, 31};
//       6:int[] selectedColumn = new int[]{2, 7, 14, 17, 19, 23, 28, 29, 30, 31};
//        7:int[] selectedColumn = new int[]{7, 11, 13, 15, 17, 19, 23, 24, 28, 30, 31};
//        8:int[] selectedColumn = new int[]{2, 9, 11, 12, 13, 14, 16, 26, 31};

        // walk和sit:featureextraction_walk32_33,featureextraction_sit32_33
//         全选：int[] selectedColumn = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
//                17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33};
//        1:int[] selectedColumn = new int[]{3, 4, 11, 12, 13, 16, 17, 18, 19, 21, 25, 28, 29, 30, 31, 33};
//        2:int[] selectedColumn = new int[]{2, 6, 7, 8, 10, 11, 12, 13, 15, 16, 19, 20, 21, 22, 24, 25, 26, 30, 31, 33};
//        3:int[] selectedColumn = new int[]{2, 4, 7, 8, 10, 19, 20, 21, 22, 23, 24, 25, 27, 29, 31, 33};
//        4:int[] selectedColumn = new int[]{2, 3, 6, 7, 8, 9, 11, 12, 21, 22, 23, 24, 25, 26, 28, 31, 33};
//        5:int[] selectedColumn = new int[]{3, 4, 7, 8, 10, 11, 12, 13, 14, 15, 17, 20, 23, 25, 27, 28, 30, 31, 33};
//       6:int[] selectedColumn = new int[]{2, 4, 8, 9, 10, 12, 13, 16, 17, 21, 22, 27, 30, 31, 33};
//        7:int[] selectedColumn = new int[]{1, 2, 6, 8, 10, 11, 12, 15, 17, 18, 20, 21, 23, 24, 25, 26, 31, 33};
//        8:int[] selectedColumn = new int[]{4, 8, 9, 11, 13, 15, 16, 17, 18, 20, 21, 25, 28, 29, 30, 31, 33};

        //stand和sit:featureextraction_stand32_33,featureextraction_sit32_33
//         全选：int[] selectedColumn = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
//                17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33};
//        1:int[] selectedColumn = new int[]{1, 2, 4, 10, 12, 13, 20, 21, 22, 23, 24, 25, 26, 29, 30, 31, 33};
//        2:int[] selectedColumn = new int[]{2, 3, 4, 12, 13, 19, 20, 21, 23, 24, 25, 26, 28, 29, 30, 31, 33};
//        3:int[] selectedColumn = new int[]{3, 4, 10, 11, 12, 19, 21, 22, 23, 24, 25, 26, 28, 29, 30, 31, 33};
//        4:int[] selectedColumn = new int[]{3, 4, 11, 12, 19, 21, 22, 23, 24, 25, 26, 28, 29, 30, 31, 33};
//        5:int[] selectedColumn = new int[]{1, 3, 7, 12, 21, 22, 23, 24, 28, 29, 31, 33};
//       6:int[] selectedColumn = new int[]{1, 3, 4, 7, 8, 10, 12, 15, 19, 20, 21, 22, 24, 25, 26, 28, 31, 33};
//        7:int[] selectedColumn = new int[]{4, 12, 14, 20, 21, 23, 25, 26, 28, 30, 31, 33};
//        8:int[] selectedColumn = new int[]{1, 3, 12, 13, 21, 28, 29, 31, 33};

        System.out.println("********************KNN分类算法开始**********************");
        KNNAlgorithm knn = new KNNAlgorithm();
        knn.startKNN(selectedColumn);
        System.out.println("********************KNN分类算法结束**********************");
		System.out.println("********************NBC分类算法开始**********************");
		NaiveBayesianAlgorithm nbc = new NaiveBayesianAlgorithm();
		nbc.startNBC(selectedColumn);
		System.out.println("********************NBC分类算法结束**********************");
        System.out.println("********************C4.5决策树分类算法开始**********************");
        DecisionTreeAlgorithm dt = new DecisionTreeAlgorithm();
        dt.startDecisionTree(selectedColumn);
        System.out.println("********************C4.5决策树分类算法结束**********************");
        System.out.println("********************SVM分类算法开始**********************");
        LibSVMTest libSVMTest = new LibSVMTest();
        libSVMTest.startSVM(selectedColumn);
        System.out.println("********************SVM分类算法结束**********************");
    }
}
