package activityrecognition;

import activityrecognition.dao.SplitLocomotionDao;
import activityrecognition.daoImpl.SplitLocomotionImpl;
import activityrecognition.entity.DataEntity;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * // 窗口划分，特征提取(采样频率64hz，窗口大小0.5s，1s，2s，重叠率50%，均值，方差，平均绝对误差，均方根，四分位差，
 * 极差，峰度，偏度，协方差，能量，相关系数)
 *
 * @author Administrator
 */
public class FeatureExtraction {
    // 窗口大小
    private final int windowSize;
    // 重叠率
    private final double overlap;

    // 通过构造方法初始化类属性字段
    public FeatureExtraction(int windowSize, double overlap) {
        this.windowSize = windowSize;
        this.overlap = overlap;
    }

    public void startFeatureExtraction() {
        System.out.println("********************特征提取开始**********************");
        // 对"stand"动作进行特征提取
        featureExtraction(1, "stand", 155765);
        // 对"walk"动作进行特征提取
        featureExtraction(2, "walk", 80474);
        // 对"sit"动作进行特征提取
        featureExtraction(4, "sit", 48869);
        // 对"lie"动作进行特征提取
        featureExtraction(5, "lie", 5572);
        System.out.println("********************特征提取结束**********************");
    }

    public void featureExtraction(int Locomotion, String tableName, int sumData) {
        // 创建FeatureExtractionDao类
        SplitLocomotionDao dao = new SplitLocomotionImpl();
        // 计算重叠窗口大小
        final int overlapSzie = (int) (windowSize * overlap);
        String[] sqlAdd = new String[sumData];
        // sql数组下标
        int index = 0;
        // 以窗口大小windowSize，重叠率overlap进行窗口切分，并提取其特征值
        for (int i = 0; i < sumData; i = i + overlapSzie) {
            // 当前窗口下线
            int minWindow = i + 1;
            // 当前窗口上线
            int maxWindow = i + windowSize;
            // 当maxWindow大于sumData时，跳出循环
            if (maxWindow > sumData) {
                break;
            }
            // 查询特定窗口大小数据信息的sql语句
            String sqlFind = "select * from " + tableName + " where Id between " + minWindow + " and " + maxWindow + ";";
            // 执行查询操作
            List<DataEntity> list = dao.search(sqlFind);

            // 计算均值-9
            double[] meanValue = means(list);
            // 计算方差-9
            double[] varianceValue = variance(list, meanValue);
            // 计算平均绝对误差
            double[] madValue = mad(list, meanValue);
            // 计算均方根-9
           double[] rmsValue = rms(list);
            // 计算四分位差-9
            double[] iqrValue = iqr(list);
            // 计算极差-9
            double[] rangeValue = range(list);
            // 计算峰度-9
            double[] kurtosisValue = kurtosis(list, meanValue, varianceValue);
            // 计算偏度-9
            double[] skewnessValue = skewness(list, meanValue, varianceValue);
            // 计算协方差-9
            double[] covarianceValue = covariance(list, meanValue);
            // 计算相关系数-9
            double[] correlationValue = correlation(covarianceValue, varianceValue);
            // 计算能量-9
            double[] energyValue = energy(list);

            sqlAdd[index++] = "INSERT INTO `featureextraction_" + tableName + "128_99` (" +
                    "`RKN_accX_mean`, `RKN_accY_mean`, `RKN_accZ_mean`," +
                    " `HIP_accX_mean`, `HIP_accY_mean`, `HIP_accZ_mean`, " +
                    "`LUA_accX_mean`, `LUA_accY_mean`, `LUA_accZ_mean`, " +
                    "`RKN_accX_variance`, `RKN_accY_variance`, `RKN_accZ_variance`, " +
                    "`HIP_accX_variance`, `HIP_accY_variance`, `HIP_accZ_variance`, " +
                    "`LUA_accX_variance`, `LUA_accY_variance`, `LUA_accZ_variance`, " +
                    "`RKN_accX_mad`, `RKN_accY_mad`, `RKN_accZ_mad`, " +
                    "`HIP_accX_mad`, `HIP_accY_mad`, `HIP_accZ_mad`, " +
                    "`LUA_accX_mad`, `LUA_accY_mad`, `LUA_accZ_mad`" +
                    ", `RKN_accX_rms`, `RKN_accY_rms`, `RKN_accZ_rms`, " +
                    "`HIP_accX_rms`, `HIP_accY_rms`, `HIP_accZ_rms`, " +
                    "`LUA_accX_rms`, `LUA_accY_rms`, `LUA_accZ_rms`, " +
                    "`RKN_accX_iqr`, `RKN_accY_iqr`, `RKN_accZ_iqr`, " +
                    "`HIP_accX_iqr`, `HIP_accY_iqr`, `HIP_accZ_iqr`, " +
                    "`LUA_accX_iqr`, `LUA_accY_iqr`, `LUA_accZ_iqr`, " +
                    "`RKN_accX_range`, `RKN_accY_range`, `RKN_accZ_range`, " +
                    "`HIP_accX_range`, `HIP_accY_range`, `HIP_accZ_range`, " +
                    "`LUA_accX_range`, `LUA_accY_range`, `LUA_accZ_range`, " +
                    "`RKN_accX_kurtosis`, `RKN_accY_kurtosis`, `RKN_accZ_kurtosis`, " +
                    "`HIP_accX_kurtosis`, `HIP_accY_kurtosis`, `HIP_accZ_kurtosis`, " +
                    "`LUA_accX_kurtosis`, `LUA_accY_kurtosis`, `LUA_accZ_kurtosis`, " +
                    "`RKN_accX_skewness`, `RKN_accY_skewness`, `RKN_accZ_skewness`, " +
                    "`HIP_accX_skewness`, `HIP_accY_skewness`, `HIP_accZ_skewness`, " +
                    "`LUA_accX_skewness`, `LUA_accY_skewness`, `LUA_accZ_skewness`, " +
                    "`RKN_accX_covariance`, `RKN_accY_covariance`, `RKN_accZ_covariance`, " +
                    "`HIP_accX_covariance`, `HIP_accY_covariance`, `HIP_accZ_covariance`, " +
                    "`LUA_accX_covariance`, `LUA_accY_covariance`, `LUA_accZ_covariance`, " +
                    "`RKN_accX_accY_correlation`, `RKN_accY_accZ_correlation`, `RKN_accZ_accX_correlation`, " +
                    "`HIP_accX_accY_correlation`, `HIP_accY_accZ_correlation`, `HIP_accZ_accX_correlation`, " +
                    "`LUA_accX_accY_correlation`, `LUA_accY_accZ_correlation`, `LUA_accZ_accX_correlation`, " +
                    "`RKN_accX_energy`, `RKN_accY_energy`, `RKN_accZ_energy`, " +
                    "`HIP_accX_energy`, `HIP_accY_energy`, `HIP_accZ_energy`, " +
                    "`LUA_accX_energy`, `LUA_accY_energy`, `LUA_accZ_energy`, `locomotion`) " +
                    "VALUES('" + meanValue[0] + "', '" + meanValue[1] + "', '" + meanValue[2] + "', '" + meanValue[3] +
                    "', '" + meanValue[4] + "', '" + meanValue[5] + "', '" + meanValue[6] + "', '" + meanValue[7] +
                    "', '" + meanValue[8] + "', '" + varianceValue[0] + "', '" + varianceValue[1] + "', '" +
                    varianceValue[2] + "', '" + varianceValue[3] + "', '" + varianceValue[4] + "', '" +
                    varianceValue[5] + "', '" + varianceValue[6] + "', '" + varianceValue[7] + "', '" +
                    varianceValue[8] + "', '" + madValue[0] + "', '" + madValue[1] + "', '" + madValue[2] + "', " +
                    "'" + madValue[3] + "', '" + madValue[4] + "', '" + madValue[5] + "', '" + madValue[6] + "', " +
                    "'" + madValue[7] + "', '" + madValue[8] + "', '" + rmsValue[0] + "', '" + rmsValue[1] + "', " +
                    "'" + rmsValue[2] + "', '" + rmsValue[3] + "', '" + rmsValue[4] + "', " +
                    "'" + rmsValue[5] + "', '" + rmsValue[6] + "', '" + rmsValue[7] + "', '" + rmsValue[8] + "', " +
                    "'" + iqrValue[0] + "', '" + iqrValue[1] + "', '" + iqrValue[2] + "', '" + iqrValue[3] +
                    "', '" + iqrValue[4] + "', '" + iqrValue[5] + "', '" + iqrValue[6] + "', '" + iqrValue[7] +
                    "', '" + iqrValue[8] + "', '" + rangeValue[0] + "', '" + rangeValue[1] + "', '" + rangeValue[2] +
                    "', '" + rangeValue[3] + "', '" + rangeValue[4] + "', '" + rangeValue[5] + "', '" + rangeValue[6]
                    + "', '" + rangeValue[7] + "', '" + rangeValue[8] + "', '" + kurtosisValue[0] + "', " +
                    "'" + kurtosisValue[1] + "', '" + kurtosisValue[2] + "', '" + kurtosisValue[3] +
                    "', '" + kurtosisValue[4] + "', '" + kurtosisValue[5] + "', '" + kurtosisValue[6] + "', " +
                    "'" + kurtosisValue[7] + "', '" + kurtosisValue[8] + "', '" + skewnessValue[0] + "', " +
                    "'" + skewnessValue[1] + "', '" + skewnessValue[2] + "', '" + skewnessValue[3] + "', " +
                    "'" + skewnessValue[4] + "', '" + skewnessValue[5] + "', '" + skewnessValue[6] + "', " +
                    "'" + skewnessValue[7] + "', '" + skewnessValue[8] + "', '" + covarianceValue[0] + "', " +
                    "'" + covarianceValue[1] + "', '" + covarianceValue[2] + "', '" + covarianceValue[3] + "', " +
                    "'" + covarianceValue[4] + "', '" + covarianceValue[5] + "', '" + covarianceValue[6] + "', " +
                    "'" + covarianceValue[7] + "', '" + covarianceValue[8] + "', '" + correlationValue[0] + "', " +
                    "'" + correlationValue[1] + "', '" + correlationValue[2] + "', '" + correlationValue[3] + "', " +
                    "'" + correlationValue[4] + "', '" + correlationValue[5] + "', '" + correlationValue[6] +
                    "', '" + correlationValue[7] + "', '" + correlationValue[8] + "', '" + energyValue[0] + "', '" +
                    energyValue[1] + "', '" + energyValue[2] + "', '" + energyValue[3] + "'," + " '" +
                    energyValue[4] + "', '" + energyValue[5] + "', '" + energyValue[6] + "', '" + energyValue[7] +
                    "', '" + energyValue[8] + "', '" + Locomotion + "');";
        }
        // 执行插入语句
        dao.save(sqlAdd);
        System.out.println("动作" + tableName + "特征提取完毕！");
    }

    // 计算均值-9
    public double[] means(List<DataEntity> list) {
        // 各轴加速度数据之和
        double sum_RKN_accX = 0;
        double sum_RKN_accY = 0;
        double sum_RKN_accZ = 0;
        double sum_HIP_accX = 0;
        double sum_HIP_accY = 0;
        double sum_HIP_accZ = 0;
        double sum_LUA_accX = 0;
        double sum_LUA_accY = 0;
        double sum_LUA_accZ = 0;
        // 各轴加速度数据均值
        double[] means = new double[9];

        // 循环读取查询到的数据记录
        for (int i = 0; i < list.size(); i++) {
            DataEntity dataEntity = list.get(i);
            // 计算各轴加速度数据之和
            sum_RKN_accX = sum_RKN_accX + Double.parseDouble(dataEntity.getRkn_x());
            sum_RKN_accY = sum_RKN_accY + Double.parseDouble(dataEntity.getRkn_y());
            sum_RKN_accZ = sum_RKN_accZ + Double.parseDouble(dataEntity.getRkn_z());
            sum_HIP_accX = sum_HIP_accX + Double.parseDouble(dataEntity.getHip_x());
            sum_HIP_accY = sum_HIP_accY + Double.parseDouble(dataEntity.getHip_y());
            sum_HIP_accZ = sum_HIP_accZ + Double.parseDouble(dataEntity.getHip_z());
            sum_LUA_accX = sum_LUA_accX + Double.parseDouble(dataEntity.getLua_x());
            sum_LUA_accY = sum_LUA_accY + Double.parseDouble(dataEntity.getLua_y());
            sum_LUA_accZ = sum_LUA_accZ + Double.parseDouble(dataEntity.getLua_z());
        }

        // 计算各轴加速度数据均值
        means[0] = sum_RKN_accX / windowSize;
        means[1] = sum_RKN_accY / windowSize;
        means[2] = sum_RKN_accZ / windowSize;
        means[3] = sum_HIP_accX / windowSize;
        means[4] = sum_HIP_accY / windowSize;
        means[5] = sum_HIP_accZ / windowSize;
        means[6] = sum_LUA_accX / windowSize;
        means[7] = sum_LUA_accY / windowSize;
        means[8] = sum_LUA_accZ / windowSize;

        return means;
    }

    // 计算方差-9
    public double[] variance(List<DataEntity> list, double[] meanValue) {
        // 各轴加速度数据与均值之差的平方求和
        double square_RKN_accX = 0;
        double square_RKN_accY = 0;
        double square_RKN_accZ = 0;
        double square_HIP_accX = 0;
        double square_HIP_accY = 0;
        double square_HIP_accZ = 0;
        double square_LUA_accX = 0;
        double square_LUA_accY = 0;
        double square_LUA_accZ = 0;
        // 各轴加速度数据方差
        double[] variance = new double[9];

        // 循环读取查询到的数据记录
        for (int i = 0; i < list.size(); i++) {
            DataEntity dataEntity = list.get(i);
            // 计算各轴加速度数据与均值之差的平方求和
            double tmp = Double.parseDouble(dataEntity.getRkn_x()) - meanValue[0];
            square_RKN_accX = square_RKN_accX + Math.pow(tmp, 2);
            tmp = Double.parseDouble(dataEntity.getRkn_y()) - meanValue[1];
            square_RKN_accY = square_RKN_accY + Math.pow(tmp, 2);
            tmp = Double.parseDouble(dataEntity.getRkn_z()) - meanValue[2];
            square_RKN_accZ = square_RKN_accZ + Math.pow(tmp, 2);
            tmp = Double.parseDouble(dataEntity.getHip_x()) - meanValue[3];
            square_HIP_accX = square_HIP_accX + Math.pow(tmp, 2);
            tmp = Double.parseDouble(dataEntity.getHip_y()) - meanValue[4];
            square_HIP_accY = square_HIP_accY + Math.pow(tmp, 2);
            tmp = Double.parseDouble(dataEntity.getHip_z()) - meanValue[5];
            square_HIP_accZ = square_HIP_accZ + Math.pow(tmp, 2);
            tmp = Double.parseDouble(dataEntity.getLua_x()) - meanValue[6];
            square_LUA_accX = square_LUA_accX + Math.pow(tmp, 2);
            tmp = Double.parseDouble(dataEntity.getLua_y()) - meanValue[7];
            square_LUA_accY = square_LUA_accY + Math.pow(tmp, 2);
            tmp = Double.parseDouble(dataEntity.getLua_z()) - meanValue[8];
            square_LUA_accZ = square_LUA_accZ + Math.pow(tmp, 2);
        }
        // 计算各轴加速度数据方差
        variance[0] = square_RKN_accX / windowSize;
        variance[1] = square_RKN_accY / windowSize;
        variance[2] = square_RKN_accZ / windowSize;
        variance[3] = square_HIP_accX / windowSize;
        variance[4] = square_HIP_accY / windowSize;
        variance[5] = square_HIP_accZ / windowSize;
        variance[6] = square_LUA_accX / windowSize;
        variance[7] = square_LUA_accY / windowSize;
        variance[8] = square_LUA_accZ / windowSize;

        return variance;
    }

    // 计算平均绝对误差-9
    public double[] mad(List<DataEntity> list, double[] meanValue) {
        // 各轴加速度数据与均值之差的绝对值求和
        double mad_RKN_accX = 0;
        double mad_RKN_accY = 0;
        double mad_RKN_accZ = 0;
        double mad_HIP_accX = 0;
        double mad_HIP_accY = 0;
        double mad_HIP_accZ = 0;
        double mad_LUA_accX = 0;
        double mad_LUA_accY = 0;
        double mad_LUA_accZ = 0;
        // 各轴加速度数据平均绝对误差
        double[] mad = new double[9];
        for (int i = 0; i < list.size(); i++) {
            DataEntity dataEntity = list.get(i);
            // 计算各轴加速度数据与均值之差的平方求和
            double tmp = Double.parseDouble(dataEntity.getRkn_x()) - meanValue[0];
            mad_RKN_accX = mad_RKN_accX + Math.abs(tmp);
            tmp = Double.parseDouble(dataEntity.getRkn_y()) - meanValue[1];
            mad_RKN_accY = mad_RKN_accY + Math.abs(tmp);
            tmp = Double.parseDouble(dataEntity.getRkn_z()) - meanValue[2];
            mad_RKN_accZ = mad_RKN_accZ + Math.abs(tmp);
            tmp = Double.parseDouble(dataEntity.getHip_x()) - meanValue[3];
            mad_HIP_accX = mad_HIP_accX + Math.abs(tmp);
            tmp = Double.parseDouble(dataEntity.getHip_y()) - meanValue[4];
            mad_HIP_accY = mad_HIP_accY + Math.abs(tmp);
            tmp = Double.parseDouble(dataEntity.getHip_z()) - meanValue[5];
            mad_HIP_accZ = mad_HIP_accZ + Math.abs(tmp);
            tmp = Double.parseDouble(dataEntity.getLua_x()) - meanValue[6];
            mad_LUA_accX = mad_LUA_accX + Math.abs(tmp);
            tmp = Double.parseDouble(dataEntity.getLua_y()) - meanValue[7];
            mad_LUA_accY = mad_LUA_accY + Math.abs(tmp);
            tmp = Double.parseDouble(dataEntity.getLua_z()) - meanValue[8];
            mad_LUA_accZ = mad_LUA_accZ + Math.abs(tmp);
        }
        // 计算各轴加速度数据平均绝对误差
        mad[0] = mad_RKN_accX / windowSize;
        mad[1] = mad_RKN_accY / windowSize;
        mad[2] = mad_RKN_accZ / windowSize;
        mad[3] = mad_HIP_accX / windowSize;
        mad[4] = mad_HIP_accY / windowSize;
        mad[5] = mad_HIP_accZ / windowSize;
        mad[6] = mad_LUA_accX / windowSize;
        mad[7] = mad_LUA_accY / windowSize;
        mad[8] = mad_LUA_accZ / windowSize;

        return mad;
    }

    // 计算均方根-9
    public double[] rms(List<DataEntity> list) {
        double rms_RKN_accX = 0;
        double rms_RKN_accY = 0;
        double rms_RKN_accZ = 0;
        double rms_HIP_accX = 0;
        double rms_HIP_accY = 0;
        double rms_HIP_accZ = 0;
        double rms_LUA_accX = 0;
        double rms_LUA_accY = 0;
        double rms_LUA_accZ = 0;
        double[] rms = new double[9];
        for (int i = 0; i < list.size(); i++) {
            DataEntity dataEntity = list.get(i);
            double tmp = Math.pow(Double.parseDouble(dataEntity.getRkn_x()), 2);
            rms_RKN_accX = rms_RKN_accX + tmp;
            tmp =  Math.pow(Double.parseDouble(dataEntity.getRkn_y()), 2);
            rms_RKN_accY = rms_RKN_accY + tmp;
            tmp = Math.pow(Double.parseDouble(dataEntity.getRkn_z()), 2);
            rms_RKN_accZ = rms_RKN_accZ + tmp;
            tmp = Math.pow(Double.parseDouble(dataEntity.getHip_x()), 2);
            rms_HIP_accX = rms_HIP_accX + tmp;
            tmp = Math.pow(Double.parseDouble(dataEntity.getHip_y()), 2);
            rms_HIP_accY = rms_HIP_accY + tmp;
            tmp = Math.pow(Double.parseDouble(dataEntity.getHip_z()), 2);
            rms_HIP_accZ = rms_HIP_accZ + tmp;
            tmp = Math.pow(Double.parseDouble(dataEntity.getLua_x()), 2);
            rms_LUA_accX = rms_LUA_accX + tmp;
            tmp = Math.pow(Double.parseDouble(dataEntity.getLua_y()), 2);
            rms_LUA_accY = rms_LUA_accY + tmp;
            tmp = Math.pow(Double.parseDouble(dataEntity.getLua_z()), 2);
            rms_LUA_accZ = rms_LUA_accZ + tmp;
        }
        // 计算各轴加速度数据均方根
        rms[0] = Math.sqrt(rms_RKN_accX / windowSize);
        rms[1] = Math.sqrt(rms_RKN_accY / windowSize);
        rms[2] = Math.sqrt(rms_RKN_accZ / windowSize);
        rms[3] = Math.sqrt(rms_HIP_accX / windowSize);
        rms[4] = Math.sqrt(rms_HIP_accY / windowSize);
        rms[5] = Math.sqrt(rms_HIP_accZ / windowSize);
        rms[6] = Math.sqrt(rms_LUA_accX / windowSize);
        rms[7] = Math.sqrt(rms_LUA_accY / windowSize);
        rms[8] = Math.sqrt(rms_LUA_accZ / windowSize);

        return rms;
    }

    // 计算四分位差-9
    public double[] iqr(List<DataEntity> list) {
        double[] array_RKN_accX = new double[list.size()];
        double[] array_RKN_accY = new double[list.size()];
        double[] array_RKN_accZ = new double[list.size()];
        double[] array_HIP_accX = new double[list.size()];
        double[] array_HIP_accY = new double[list.size()];
        double[] array_HIP_accZ = new double[list.size()];
        double[] array_LUA_accX = new double[list.size()];
        double[] array_LUA_accY = new double[list.size()];
        double[] array_LUA_accZ = new double[list.size()];
        double[] iqr = new double[9];
        for (int i = 0; i < list.size(); i++) {
            DataEntity dataEntity = list.get(i);
            double tmp = Double.parseDouble(dataEntity.getRkn_x());
            array_RKN_accX[i] = tmp;
            tmp = Double.parseDouble(dataEntity.getRkn_y());
            array_RKN_accY[i] = tmp;
            tmp = Double.parseDouble(dataEntity.getRkn_z());
            array_RKN_accZ[i] = tmp;
            tmp = Double.parseDouble(dataEntity.getHip_x());
            array_HIP_accX[i] = tmp;
            tmp = Double.parseDouble(dataEntity.getHip_y());
            array_HIP_accY[i] = tmp;
            tmp = Double.parseDouble(dataEntity.getHip_z());
            array_HIP_accZ[i] = tmp;
            tmp = Double.parseDouble(dataEntity.getLua_x());
            array_LUA_accX[i] = tmp;
            tmp = Double.parseDouble(dataEntity.getLua_y());
            array_LUA_accY[i] = tmp;
            tmp = Double.parseDouble(dataEntity.getLua_z());
            array_LUA_accZ[i] = tmp;
        }
        Arrays.sort(array_RKN_accX);
        Arrays.sort(array_RKN_accY);
        Arrays.sort(array_RKN_accZ);
        Arrays.sort(array_HIP_accX);
        Arrays.sort(array_HIP_accY);
        Arrays.sort(array_HIP_accZ);
        Arrays.sort(array_LUA_accX);
        Arrays.sort(array_LUA_accY);
        Arrays.sort(array_LUA_accZ);

        iqr[0] = (array_RKN_accX[3 * (list.size() + 1) / 4] - array_RKN_accX[(list.size() + 1) / 4]) / 2;
        iqr[1] = (array_RKN_accY[3 * (list.size() + 1) / 4] - array_RKN_accY[(list.size() + 1) / 4]) / 2;
        iqr[2] = (array_RKN_accZ[3 * (list.size() + 1) / 4] - array_RKN_accZ[(list.size() + 1) / 4]) / 2;
        iqr[3] = (array_HIP_accX[3 * (list.size() + 1) / 4] - array_HIP_accX[(list.size() + 1) / 4]) / 2;
        iqr[4] = (array_HIP_accY[3 * (list.size() + 1) / 4] - array_HIP_accY[(list.size() + 1) / 4]) / 2;
        iqr[5] = (array_HIP_accZ[3 * (list.size() + 1) / 4] - array_HIP_accZ[(list.size() + 1) / 4]) / 2;
        iqr[6] = (array_LUA_accX[3 * (list.size() + 1) / 4] - array_LUA_accX[(list.size() + 1) / 4]) / 2;
        iqr[7] = (array_LUA_accY[3 * (list.size() + 1) / 4] - array_LUA_accY[(list.size() + 1) / 4]) / 2;
        iqr[8] = (array_LUA_accZ[3 * (list.size() + 1) / 4] - array_LUA_accZ[(list.size() + 1) / 4]) / 2;

        return iqr;
    }

    // 计算极差-9
    public double[] range(List<DataEntity> list) {
        double[] array_RKN_accX = new double[list.size()];
        double[] array_RKN_accY = new double[list.size()];
        double[] array_RKN_accZ = new double[list.size()];
        double[] array_HIP_accX = new double[list.size()];
        double[] array_HIP_accY = new double[list.size()];
        double[] array_HIP_accZ = new double[list.size()];
        double[] array_LUA_accX = new double[list.size()];
        double[] array_LUA_accY = new double[list.size()];
        double[] array_LUA_accZ = new double[list.size()];
        double[] range = new double[9];
        for (int i = 0; i < list.size(); i++) {
            DataEntity dataEntity = list.get(i);
            double tmp = Double.parseDouble(dataEntity.getRkn_x());
            array_RKN_accX[i] = tmp;
            tmp = Double.parseDouble(dataEntity.getRkn_y());
            array_RKN_accY[i] = tmp;
            tmp = Double.parseDouble(dataEntity.getRkn_z());
            array_RKN_accZ[i] = tmp;
            tmp = Double.parseDouble(dataEntity.getHip_x());
            array_HIP_accX[i] = tmp;
            tmp = Double.parseDouble(dataEntity.getHip_y());
            array_HIP_accY[i] = tmp;
            tmp = Double.parseDouble(dataEntity.getHip_z());
            array_HIP_accZ[i] = tmp;
            tmp = Double.parseDouble(dataEntity.getLua_x());
            array_LUA_accX[i] = tmp;
            tmp = Double.parseDouble(dataEntity.getLua_y());
            array_LUA_accY[i] = tmp;
            tmp = Double.parseDouble(dataEntity.getLua_z());
            array_LUA_accZ[i] = tmp;
        }
        Arrays.sort(array_RKN_accX);
        Arrays.sort(array_RKN_accY);
        Arrays.sort(array_RKN_accZ);
        Arrays.sort(array_HIP_accX);
        Arrays.sort(array_HIP_accY);
        Arrays.sort(array_HIP_accZ);
        Arrays.sort(array_LUA_accX);
        Arrays.sort(array_LUA_accY);
        Arrays.sort(array_LUA_accZ);

        range[0] = array_RKN_accX[list.size() - 1] - array_RKN_accX[0];
        range[1] = array_RKN_accY[list.size() - 1] - array_RKN_accY[0];
        range[2] = array_RKN_accZ[list.size() - 1] - array_RKN_accZ[0];
        range[3] = array_HIP_accX[list.size() - 1] - array_HIP_accX[0];
        range[4] = array_HIP_accY[list.size() - 1] - array_HIP_accY[0];
        range[5] = array_HIP_accZ[list.size() - 1] - array_HIP_accZ[0];
        range[6] = array_LUA_accX[list.size() - 1] - array_LUA_accX[0];
        range[7] = array_LUA_accY[list.size() - 1] - array_LUA_accY[0];
        range[8] = array_LUA_accZ[list.size() - 1] - array_LUA_accZ[0];

        return range;
    }

    // 计算峰度-9
    public double[] kurtosis(List<DataEntity> list, double[] meanValue, double[] variance) {
        double kurtosis_RKN_accX = 0;
        double kurtosis_RKN_accY = 0;
        double kurtosis_RKN_accZ = 0;
        double kurtosis_HIP_accX = 0;
        double kurtosis_HIP_accY = 0;
        double kurtosis_HIP_accZ = 0;
        double kurtosis_LUA_accX = 0;
        double kurtosis_LUA_accY = 0;
        double kurtosis_LUA_accZ = 0;
        double[] kurtosis = new double[9];
        for (int i = 0; i < list.size(); i++) {
            DataEntity dataEntity = list.get(i);
            double tmp = (Double.parseDouble(dataEntity.getRkn_x()) - meanValue[0]) / Math.sqrt(variance[0]);
            kurtosis_RKN_accX = kurtosis_RKN_accX + Math.pow(tmp, 4);
            tmp = (Double.parseDouble(dataEntity.getRkn_y()) - meanValue[1]) / Math.sqrt(variance[1]);
            kurtosis_RKN_accY = kurtosis_RKN_accY + Math.pow(tmp, 4);
            tmp = (Double.parseDouble(dataEntity.getRkn_z()) - meanValue[2]) / Math.sqrt(variance[2]);
            kurtosis_RKN_accZ = kurtosis_RKN_accZ + Math.pow(tmp, 4);
            tmp = (Double.parseDouble(dataEntity.getHip_x()) - meanValue[3]) / Math.sqrt(variance[3]);
            kurtosis_HIP_accX = kurtosis_HIP_accX + Math.pow(tmp, 4);
            tmp = (Double.parseDouble(dataEntity.getHip_y()) - meanValue[4]) / Math.sqrt(variance[4]);
            kurtosis_HIP_accY = kurtosis_HIP_accY + Math.pow(tmp, 4);
            tmp = (Double.parseDouble(dataEntity.getHip_z()) - meanValue[5]) / Math.sqrt(variance[5]);
            kurtosis_HIP_accZ = kurtosis_HIP_accZ + Math.pow(tmp, 4);
            tmp = (Double.parseDouble(dataEntity.getLua_x()) - meanValue[6]) / Math.sqrt(variance[6]);
            kurtosis_LUA_accX = kurtosis_LUA_accX + Math.pow(tmp, 4);
            tmp = (Double.parseDouble(dataEntity.getLua_y()) - meanValue[7]) / Math.sqrt(variance[7]);
            kurtosis_LUA_accY = kurtosis_LUA_accY + Math.pow(tmp, 4);
            tmp = (Double.parseDouble(dataEntity.getLua_z()) - meanValue[8]) / Math.sqrt(variance[8]);
            kurtosis_LUA_accZ = kurtosis_LUA_accZ + Math.pow(tmp, 4);
        }
        kurtosis[0] = kurtosis_RKN_accX / windowSize;
        kurtosis[1] = kurtosis_RKN_accY / windowSize;
        kurtosis[2] = kurtosis_RKN_accZ / windowSize;
        kurtosis[3] = kurtosis_HIP_accX / windowSize;
        kurtosis[4] = kurtosis_HIP_accY / windowSize;
        kurtosis[5] = kurtosis_HIP_accZ / windowSize;
        kurtosis[6] = kurtosis_LUA_accX / windowSize;
        kurtosis[7] = kurtosis_LUA_accY / windowSize;
        kurtosis[8] = kurtosis_LUA_accZ / windowSize;

        return kurtosis;
    }

    // 计算偏度-9
    public double[] skewness(List<DataEntity> list, double[] meanValue, double[] variance) {
        double skewness_RKN_accX = 0;
        double skewness_RKN_accY = 0;
        double skewness_RKN_accZ = 0;
        double skewness_HIP_accX = 0;
        double skewness_HIP_accY = 0;
        double skewness_HIP_accZ = 0;
        double skewness_LUA_accX = 0;
        double skewness_LUA_accY = 0;
        double skewness_LUA_accZ = 0;
        double[] skewness = new double[9];
        for (int i = 0; i < list.size(); i++) {
            DataEntity dataEntity = list.get(i);
            double tmp = (Double.parseDouble(dataEntity.getRkn_x()) - meanValue[0]) / Math.sqrt(variance[0]);
            skewness_RKN_accX = skewness_RKN_accX + Math.pow(tmp, 3);
            tmp = (Double.parseDouble(dataEntity.getRkn_y()) - meanValue[1]) / Math.sqrt(variance[1]);
            skewness_RKN_accY = skewness_RKN_accY + Math.pow(tmp, 3);
            tmp = (Double.parseDouble(dataEntity.getRkn_z()) - meanValue[2]) / Math.sqrt(variance[2]);
            skewness_RKN_accZ = skewness_RKN_accZ + Math.pow(tmp, 3);
            tmp = (Double.parseDouble(dataEntity.getHip_x()) - meanValue[3]) / Math.sqrt(variance[3]);
            skewness_HIP_accX = skewness_HIP_accX + Math.pow(tmp, 3);
            tmp = (Double.parseDouble(dataEntity.getHip_y()) - meanValue[4]) / Math.sqrt(variance[4]);
            skewness_HIP_accY = skewness_HIP_accY + Math.pow(tmp, 3);
            tmp = (Double.parseDouble(dataEntity.getHip_z()) - meanValue[5]) / Math.sqrt(variance[5]);
            skewness_HIP_accZ = skewness_HIP_accZ + Math.pow(tmp, 3);
            tmp = (Double.parseDouble(dataEntity.getLua_x()) - meanValue[6]) / Math.sqrt(variance[6]);
            skewness_LUA_accX = skewness_LUA_accX + Math.pow(tmp, 3);
            tmp = (Double.parseDouble(dataEntity.getLua_y()) - meanValue[7]) / Math.sqrt(variance[7]);
            skewness_LUA_accY = skewness_LUA_accY + Math.pow(tmp, 3);
            tmp = (Double.parseDouble(dataEntity.getLua_z()) - meanValue[8]) / Math.sqrt(variance[8]);
            skewness_LUA_accZ = skewness_LUA_accZ + Math.pow(tmp, 3);
        }
        skewness[0] = skewness_RKN_accX / windowSize;
        skewness[1] = skewness_RKN_accY / windowSize;
        skewness[2] = skewness_RKN_accZ / windowSize;
        skewness[3] = skewness_HIP_accX / windowSize;
        skewness[4] = skewness_HIP_accY / windowSize;
        skewness[5] = skewness_HIP_accZ / windowSize;
        skewness[6] = skewness_LUA_accX / windowSize;
        skewness[7] = skewness_LUA_accY / windowSize;
        skewness[8] = skewness_LUA_accZ / windowSize;

        return skewness;
    }

    // 计算协方差-9
    public double[] covariance(List<DataEntity> list, double[] meanValue) {
        double covariance_RKN_accX_accY = 0;
        double covariance_RKN_accY_accZ = 0;
        double covariance_RKN_accZ_accX = 0;
        double covariance_HIP_accX_accY = 0;
        double covariance_HIP_accY_accZ = 0;
        double covariance_HIP_accZ_accX = 0;
        double covariance_LUA_accX_accY = 0;
        double covariance_LUA_accY_accZ = 0;
        double covariance_LUA_accZ_accX = 0;
        double[] covariance = new double[9];
        for (int i = 0; i < list.size(); i++) {
            DataEntity dataEntity = list.get(i);
            double tmp = (Double.parseDouble(dataEntity.getRkn_x()) - meanValue[0]) *
                    (Double.parseDouble(dataEntity.getRkn_y()) - meanValue[1]);
            covariance_RKN_accX_accY = covariance_RKN_accX_accY + tmp;
            tmp = (Double.parseDouble(dataEntity.getRkn_y()) - meanValue[1]) *
                    (Double.parseDouble(dataEntity.getRkn_z()) - meanValue[2]);
            covariance_RKN_accY_accZ = covariance_RKN_accY_accZ + tmp;
            tmp = (Double.parseDouble(dataEntity.getRkn_z()) - meanValue[2]) *
                    (Double.parseDouble(dataEntity.getRkn_x()) - meanValue[0]);
            covariance_RKN_accZ_accX = covariance_RKN_accZ_accX + tmp;
            tmp = (Double.parseDouble(dataEntity.getHip_x()) - meanValue[3]) *
                    (Double.parseDouble(dataEntity.getHip_y()) - meanValue[4]);
            covariance_HIP_accX_accY = covariance_HIP_accX_accY + tmp;
            tmp = (Double.parseDouble(dataEntity.getHip_y()) - meanValue[4]) *
                    (Double.parseDouble(dataEntity.getHip_z()) - meanValue[5]);
            covariance_HIP_accY_accZ = covariance_HIP_accY_accZ + tmp;
            tmp = (Double.parseDouble(dataEntity.getHip_z()) - meanValue[5]) *
                    (Double.parseDouble(dataEntity.getHip_x()) - meanValue[3]);
            covariance_HIP_accZ_accX = covariance_HIP_accZ_accX + tmp;
            tmp = (Double.parseDouble(dataEntity.getLua_x()) - meanValue[6]) *
                    (Double.parseDouble(dataEntity.getLua_y()) - meanValue[7]);
            covariance_LUA_accX_accY = covariance_LUA_accX_accY + tmp;
            tmp = (Double.parseDouble(dataEntity.getLua_y()) - meanValue[7]) *
                    (Double.parseDouble(dataEntity.getLua_z()) - meanValue[8]);
            covariance_LUA_accY_accZ = covariance_LUA_accY_accZ + tmp;
            tmp = (Double.parseDouble(dataEntity.getLua_z()) - meanValue[8]) *
                    (Double.parseDouble(dataEntity.getLua_x()) - meanValue[6]);
            covariance_LUA_accZ_accX = covariance_LUA_accZ_accX + tmp;
        }
        covariance[0] = covariance_RKN_accX_accY / windowSize;
        covariance[1] = covariance_RKN_accY_accZ / windowSize;
        covariance[2] = covariance_RKN_accZ_accX / windowSize;
        covariance[3] = covariance_HIP_accX_accY / windowSize;
        covariance[4] = covariance_HIP_accY_accZ / windowSize;
        covariance[5] = covariance_HIP_accZ_accX / windowSize;
        covariance[6] = covariance_LUA_accX_accY / windowSize;
        covariance[7] = covariance_LUA_accY_accZ / windowSize;
        covariance[8] = covariance_LUA_accZ_accX / windowSize;

        return covariance;
    }


    // 计算相关系数
    public double[] correlation(double[] covariance, double[] variance) {
        double[] correlation = new double[9];
        correlation[0] = covariance[0] / Math.sqrt(variance[0] * variance[1]);
        correlation[1] = covariance[1] / Math.sqrt(variance[1] * variance[2]);
        correlation[2] = covariance[2] / Math.sqrt(variance[2] * variance[0]);
        correlation[3] = covariance[3] / Math.sqrt(variance[3] * variance[4]);
        correlation[4] = covariance[4] / Math.sqrt(variance[4] * variance[5]);
        correlation[5] = covariance[5] / Math.sqrt(variance[5] * variance[3]);
        correlation[6] = covariance[6] / Math.sqrt(variance[6] * variance[7]);
        correlation[7] = covariance[7] / Math.sqrt(variance[7] * variance[8]);
        correlation[8] = covariance[8] / Math.sqrt(variance[8] * variance[6]);

        return correlation;
    }


    // 计算能量
    public double[] energy(List<DataEntity> list) {
        // 数据实部
        Double[] real_RKN_accX = new Double[windowSize];
        Double[] real_RKN_accY = new Double[windowSize];
        Double[] real_RKN_accZ = new Double[windowSize];
        Double[] real_HIP_accX = new Double[windowSize];
        Double[] real_HIP_accY = new Double[windowSize];
        Double[] real_HIP_accZ = new Double[windowSize];
        Double[] real_LUA_accX = new Double[windowSize];
        Double[] real_LUA_accY = new Double[windowSize];
        Double[] real_LUA_accZ = new Double[windowSize];
        // 数据虚部
        Double[] imag_RKN_accX = new Double[windowSize];
        Double[] imag_RKN_accY = new Double[windowSize];
        Double[] imag_RKN_accZ = new Double[windowSize];
        Double[] imag_HIP_accX = new Double[windowSize];
        Double[] imag_HIP_accY = new Double[windowSize];
        Double[] imag_HIP_accZ = new Double[windowSize];
        Double[] imag_LUA_accX = new Double[windowSize];
        Double[] imag_LUA_accY = new Double[windowSize];
        Double[] imag_LUA_accZ = new Double[windowSize];
        // 各轴数据的能量
        double[] energy = new double[9];
        // 下标
        int index = 0;

        // 循环读取查询到的数据记录
        for (int i = 0; i < list.size(); i++) {
            DataEntity dataEntity = list.get(i);
            // 数据实部
            real_RKN_accX[index] = Double.parseDouble(dataEntity.getRkn_x());
            real_RKN_accY[index] = Double.parseDouble(dataEntity.getRkn_y());
            real_RKN_accZ[index] = Double.parseDouble(dataEntity.getRkn_z());
            real_HIP_accX[index] = Double.parseDouble(dataEntity.getHip_x());
            real_HIP_accY[index] = Double.parseDouble(dataEntity.getHip_y());
            real_HIP_accZ[index] = Double.parseDouble(dataEntity.getHip_z());
            real_LUA_accX[index] = Double.parseDouble(dataEntity.getLua_x());
            real_LUA_accY[index] = Double.parseDouble(dataEntity.getLua_y());
            real_LUA_accZ[index] = Double.parseDouble(dataEntity.getLua_z());
            // 数据虚部
            imag_RKN_accX[index] = new Double(0);
            imag_RKN_accY[index] = new Double(0);
            imag_RKN_accZ[index] = new Double(0);
            imag_HIP_accX[index] = new Double(0);
            imag_HIP_accY[index] = new Double(0);
            imag_HIP_accZ[index] = new Double(0);
            imag_LUA_accX[index] = new Double(0);
            imag_LUA_accY[index] = new Double(0);
            imag_LUA_accZ[index] = new Double(0);
            index++;
        }
        // 快速傅里叶变换
        FFT(real_RKN_accX, imag_RKN_accX, windowSize);
        FFT(real_RKN_accY, imag_RKN_accY, windowSize);
        FFT(real_RKN_accZ, imag_RKN_accZ, windowSize);
        FFT(real_HIP_accX, imag_HIP_accX, windowSize);
        FFT(real_HIP_accY, imag_HIP_accY, windowSize);
        FFT(real_HIP_accZ, imag_HIP_accZ, windowSize);
        FFT(real_LUA_accX, imag_LUA_accX, windowSize);
        FFT(real_LUA_accY, imag_LUA_accY, windowSize);
        FFT(real_LUA_accZ, imag_LUA_accZ, windowSize);
        // 计算能量
        for (int i = 0; i < windowSize; i++) {
            energy[0] += Math.abs((real_RKN_accX[i]) * (real_RKN_accX[i]) - (imag_RKN_accX[i]) * (imag_RKN_accX[i]));
            energy[1] += Math.abs((real_RKN_accY[i]) * (real_RKN_accY[i]) - (imag_RKN_accY[i]) * (imag_RKN_accY[i]));
            energy[2] += Math.abs((real_RKN_accZ[i]) * (real_RKN_accZ[i]) - (imag_RKN_accZ[i]) * (imag_RKN_accZ[i]));
            energy[3] += Math.abs((real_HIP_accX[i]) * (real_HIP_accX[i]) - (imag_HIP_accX[i]) * (imag_HIP_accX[i]));
            energy[4] += Math.abs((real_HIP_accY[i]) * (real_HIP_accY[i]) - (imag_HIP_accY[i]) * (imag_HIP_accY[i]));
            energy[5] += Math.abs((real_HIP_accZ[i]) * (real_HIP_accZ[i]) - (imag_HIP_accZ[i]) * (imag_HIP_accZ[i]));
            energy[6] += Math.abs((real_LUA_accX[i]) * (real_LUA_accX[i]) - (imag_LUA_accX[i]) * (imag_LUA_accX[i]));
            energy[7] += Math.abs((real_LUA_accY[i]) * (real_LUA_accY[i]) - (imag_LUA_accY[i]) * (imag_LUA_accY[i]));
            energy[8] += Math.abs((real_LUA_accZ[i]) * (real_LUA_accZ[i]) - (imag_LUA_accZ[i]) * (imag_LUA_accZ[i]));
        }
        for (int j = 0; j < 9; j++) {
            energy[j] = energy[j] / windowSize;
        }

        return energy;
    }

    // 快速傅立叶变换FFT
    public void FFT(Double[] xreal, Double[] ximag, int n) {
        // 快速傅立叶变换，将复数 x 变换后仍保存在 x 中，xreal, ximag 分别是 x 的实部和虚部
        double[] wreal = new double[n / 2];
        double[] wimag = new double[n / 2];
        double treal, timag, ureal, uimag, arg;
        int m, k, g, t, index1, index2;
        int i, j, a, b, p;

        // 比特（位）反转置换
        for (i = 1, p = 0; i < n; i *= 2) {
            p++;
        }
        for (i = 0; i < n; i++) {
            a = i;
            b = 0;
            for (j = 0; j < p; j++) {
                b = (b << 1) + (a & 1);    // b = b * 2 + a % 2;
                a >>= 1;        // a = a / 2;
            }
            if (b > i) {
                // 将b和i交换
                double tmp;
                tmp = xreal[i];
                xreal[i] = xreal[b];
                xreal[b] = tmp;

                tmp = ximag[i];
                ximag[i] = ximag[b];
                ximag[b] = tmp;
            }
        }

        // 计算 1 的前 n / 2 个 n 次方根的共轭复数 W'j = wreal [j] + i * wimag [j] , j = 0, 1, ... , n / 2 - 1
        arg = -2 * Math.PI / n;
        treal = Math.cos(arg);
        timag = Math.sin(arg);
        wreal[0] = 1.0;
        wimag[0] = 0.0;
        for (g = 1; g < n / 2; g++) {
            wreal[g] = wreal[g - 1] * treal - wimag[g - 1] * timag;
            wimag[g] = wreal[g - 1] * timag + wimag[g - 1] * treal;
        }

        for (m = 2; m <= n; m *= 2) {
            for (k = 0; k < n; k += m) {
                for (g = 0; g < m / 2; g++) {
                    index1 = k + g;
                    index2 = index1 + m / 2;
                    t = n * g / m;    // 旋转因子 w 的实部在 wreal [] 中的下标为 t
                    treal = wreal[t] * xreal[index2] - wimag[t] * ximag[index2];
                    timag = wreal[t] * ximag[index2] + wimag[t] * xreal[index2];
                    ureal = xreal[index1];
                    uimag = ximag[index1];
                    xreal[index1] = ureal + treal;
                    ximag[index1] = uimag + timag;
                    xreal[index2] = ureal - treal;
                    ximag[index2] = uimag - timag;
                }
            }
        }
    }
}
