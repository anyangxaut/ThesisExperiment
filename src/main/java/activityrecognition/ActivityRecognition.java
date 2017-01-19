package activityrecognition;

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
//        Dim=10，vol=33，number=100
//        int[] selectedColumn = new int[]{1, 8, 15, 16, 20, 23, 24, 25, 26, 29};
//        int[] selectedColumn = new int[]{5, 7, 15, 20, 22, 23, 28, 29, 30, 33};
//        int[] selectedColumn = new int[]{7, 8, 15, 18, 19, 20, 22, 23, 26, 29};
//        int[] selectedColumn = new int[]{2, 3, 6, 7, 11, 19, 20, 24, 28, 29};
//        int[] selectedColumn = new int[]{1, 3, 5, 8, 13, 19, 20, 22, 27, 33};
//        int[] selectedColumn = new int[]{1, 3, 4, 8, 9, 10, 20, 21, 24, 25};

//        Dim=10，vol=33，number=500
//        int[] selectedColumn = new int[]{1, 2, 5, 8, 13, 15, 17, 19, 21, 23};
//        int[] selectedColumn = new int[]{3, 5, 8, 12, 17, 18, 21, 22, 29, 32};
//        int[] selectedColumn = new int[]{1, 3, 5, 6, 8, 11, 17, 18, 21, 22};
//        int[] selectedColumn = new int[]{2, 3, 5, 9, 12, 18, 22, 23, 28, 33};
//        int[] selectedColumn = new int[]{5, 7, 14, 17, 21, 28, 30, 31, 32, 33};
//        int[] selectedColumn = new int[]{2, 3, 5, 8, 11, 12, 20, 26, 28, 33};
//        int[] selectedColumn = new int[]{2, 6, 7, 9, 17, 22, 23, 26, 30, 33};

//        featureextraction_walk32_33_500      featureextraction_stand32_33_500
//        Dim=10，vol=33，number=500
//        int[] selectedColumn = new int[]{1, 9, 10, 14, 17, 29, 30, 31, 32, 33};
//        int[] selectedColumn = new int[]{2, 4, 7, 8, 11, 16, 17, 18, 20, 31};
//        int[] selectedColumn = new int[]{2, 3, 9, 12, 14, 18, 22, 23, 29, 30};
//        int[] selectedColumn = new int[]{2, 8, 11, 16, 17, 22, 25, 26, 29, 33};
//        int[] selectedColumn = new int[]{6, 8, 9, 10, 13, 17, 21, 27, 32, 33};
//        int[] selectedColumn = new int[]{1, 3, 4, 7, 9, 11, 12, 24, 25, 26};


//        Dim=15，vol=33，number=500
//        int[] selectedColumn = new int[]{2, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14, 16, 17, 18, 23};
//        int[] selectedColumn = new int[]{2, 4, 5, 7, 8, 10, 11, 13, 16, 17, 19, 20, 23, 26, 28};
//        int[] selectedColumn = new int[]{4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 20, 23, 28, 33};
//        int[] selectedColumn = new int[]{1, 2, 5, 7, 8, 11, 12, 13, 14, 16, 17, 20, 24, 31, 32};
//        int[] selectedColumn = new int[]{2, 4, 5, 7, 8, 10, 11, 13, 15, 17, 18, 19, 27, 29, 31};
//        int[] selectedColumn = new int[]{1, 2, 4, 5, 7, 8, 11, 13, 15, 17, 18, 25, 26, 28, 30};
//        int[] selectedColumn = new int[]{2, 4, 7, 8, 11, 13, 16, 17, 18, 19, 20, 22, 23, 30, 31};
//        int[] selectedColumn = new int[]{2, 4, 5, 7, 8, 10, 11, 13, 18, 22, 23, 27, 28, 29, 33};

//        Dim=20，vol=33，number=500
//        int[] selectedColumn = new int[]{2, 3, 4, 5, 6, 7, 8, 10, 11, 13, 14, 16, 18, 19, 23, 25, 27, 29, 30, 31};
//        int[] selectedColumn = new int[]{1, 2, 4, 5, 6, 7, 8, 9, 11, 12, 13, 16, 17, 19, 20, 22, 23, 28, 29, 30};
//        int[] selectedColumn = new int[]{1, 2, 4, 5, 7, 8, 11, 13, 15, 16, 17, 18, 19, 23, 25, 27, 30, 31, 32, 33};
//        int[] selectedColumn = new int[]{1, 2, 4, 5, 7, 8, 11, 12, 13, 14, 17, 18, 19, 20, 23, 24, 25, 27, 30, 31};
//        int[] selectedColumn = new int[]{2, 4, 5, 7, 8, 10, 11, 12, 13, 14, 15, 16, 17, 19, 20, 23, 27, 31, 32, 33};
//        int[] selectedColumn = new int[]{1, 2, 4, 5, 7, 8, 11, 12, 13, 14, 15, 16, 17, 19, 22, 23, 25, 27, 28, 31};
//        int[] selectedColumn = new int[]{1, 2, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 16, 17, 19, 23, 28, 30, 31, 33};
//        int[] selectedColumn = new int[]{2, 4, 5, 7, 8, 10, 11, 13, 14, 16, 17, 19, 20, 23, 24, 25, 27, 28, 29, 31};

//        BPSO+KNN：训练800，测试200
//        featureextraction_walk32_33_1000      featureextraction_stand32_33_1000
//        Dim=10，vol=33，number=1000

        int[] selectedColumn = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33};

        System.out.println("********************KNN分类算法开始**********************");
        KNNAlgorithm knn = new KNNAlgorithm();
        knn.startKNN(selectedColumn);
        System.out.println("********************KNN分类算法结束**********************");
		System.out.println("********************NBC分类算法开始**********************");
		NaiveBayesianAlgorithm nbc = new NaiveBayesianAlgorithm();
		nbc.startNBC(selectedColumn);
		System.out.println("********************NBC分类算法结束**********************");
//        System.out.println("********************C4.5决策树分类算法开始**********************");
//        DecisionTreeAlgorithm dt = new DecisionTreeAlgorithm();
//        dt.startDecisionTree(selectedColumn);
//        System.out.println("********************C4.5决策树分类算法结束**********************");
    }
}
