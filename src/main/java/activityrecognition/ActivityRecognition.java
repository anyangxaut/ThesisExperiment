package activityrecognition;

/**
 * Created by anyang on 2016/12/22.
 */
public class ActivityRecognition {

    public static void main(String[] args) {
        // 数据预处理
        DataPreprocess dataPreprocess = new DataPreprocess("E:\\IntellijIDEA\\ThesisExperiment\\dataset\\");
        dataPreprocess.startPreprocess();
    }
}
