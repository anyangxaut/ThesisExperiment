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

        // 窗口划分，特征提取
		FeatureExtraction featureExtraction = new FeatureExtraction(32, 0.5);
        featureExtraction.startFeatureExtraction();

        // 特征优选


        // 分类识别

    }
}
