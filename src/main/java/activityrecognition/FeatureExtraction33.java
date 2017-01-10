package activityrecognition;

import activityrecognition.dao.SplitLocomotionDao33;
import activityrecognition.daoImpl.SplitLocomotionImpl33;
import activityrecognition.entity.DataEntity;
import activityrecognition.entity.DataEntity33;

import java.util.Arrays;
import java.util.List;

/**
 * // 窗口划分，特征提取(采样频率64hz，窗口大小0.5s，1s，2s，重叠率50%，均值，方差，平均绝对误差，均方根，四分位差，
 * 极差，峰度，偏度，协方差，能量，相关系数)
 *
 * @author Administrator
 */
public class FeatureExtraction33 {
    // 窗口大小
    private final int windowSize;
    // 重叠率
    private final double overlap;

    // 通过构造方法初始化类属性字段
    public FeatureExtraction33(int windowSize, double overlap) {
        this.windowSize = windowSize;
        this.overlap = overlap;
    }

    public void startFeatureExtraction() {
        System.out.println("********************特征提取开始**********************");
        // 对"stand"动作进行特征提取
        featureExtraction(1, "stand_33", 155765);
        // 对"walk"动作进行特征提取
        featureExtraction(2, "walk_33", 80474);
        // 对"sit"动作进行特征提取
        featureExtraction(4, "sit_33", 48869);
        // 对"lie"动作进行特征提取
        featureExtraction(5, "lie_33", 5572);
        System.out.println("********************特征提取结束**********************");
    }

    public void featureExtraction(int Locomotion, String tableName, int sumData) {
        // 创建FeatureExtractionDao类
        SplitLocomotionDao33 dao = new SplitLocomotionImpl33();
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
            List<DataEntity33> list = dao.search(sqlFind);

            // 计算均值-3
            double[] meanValue = means(list);
            // 计算方差-3
            double[] varianceValue = variance(list, meanValue);
            // 计算平均绝对误差
            double[] madValue = mad(list, meanValue);
            // 计算均方根-3
           double[] rmsValue = rms(list);
            // 计算四分位差-3
            double[] iqrValue = iqr(list);
            // 计算极差-3
            double[] rangeValue = range(list);
            // 计算峰度-3
            double[] kurtosisValue = kurtosis(list, meanValue, varianceValue);
            // 计算偏度-3
            double[] skewnessValue = skewness(list, meanValue, varianceValue);
            // 计算协方差-3
            double[] covarianceValue = covariance(list, meanValue);
            // 计算相关系数-3
            double[] correlationValue = correlation(covarianceValue, varianceValue);
            // 计算能量-3
            double[] energyValue = energy(list);

            String[] tablename33 = tableName.split("_");

            sqlAdd[index++] = "INSERT INTO `featureextraction_" + tablename33[0] + "128_33` (" +
                    "`RKN_mean`, " +
                    "`HIP_mean`, " +
                    "`LUA_mean`, " +
                    "`RKN_variance`, " +
                    "`HIP_variance`, " +
                    "`LUA_variance`, " +
                    "`RKN_mad`, " +
                    "`HIP_mad`, " +
                    "`LUA_mad`, " +
                    "`RKN_rms`, " +
                    "`HIP_rms`, " +
                    "`LUA_rms`, " +
                    "`RKN_iqr`, " +
                    "`HIP_iqr`, " +
                    "`LUA_iqr`, " +
                    "`RKN_range`, " +
                    "`HIP_range`, " +
                    "`LUA_range`, " +
                    "`RKN_kurtosis`, " +
                    "`HIP_kurtosis`, " +
                    "`LUA_kurtosis`, " +
                    "`RKN_skewness`, " +
                    "`HIP_skewness`, " +
                    "`LUA_skewness`, " +
                    "`RKN_covariance`, " +
                    "`HIP_covariance`, " +
                    "`LUA_covariance`, " +
                    "`RKN_HIP_correlation`, " +
                    "`HIP_LUA_correlation`, " +
                    "`LUA_RKN_correlation`, " +
                    "`RKN_energy`, " +
                    "`HIP_energy`, " +
                    "`LUA_energy`, `locomotion`) " +
                    "VALUES('" + meanValue[0] + "', '" + meanValue[1] + "', '" + meanValue[2] + "', '"
                    + varianceValue[0] + "', '" + varianceValue[1] + "', '" +
                    varianceValue[2] + "', '" + madValue[0] + "', '" + madValue[1] + "', '" + madValue[2] + "', " +
                    "'" + rmsValue[0] + "', '" + rmsValue[1] + "', " +
                    "'" + rmsValue[2] + "', '" + iqrValue[0] + "', '" + iqrValue[1] + "', '" + iqrValue[2] + "', '"
                    + rangeValue[0] + "', '" + rangeValue[1] + "', '" + rangeValue[2] +
                    "', '" + kurtosisValue[0] + "', " +
                    "'" + kurtosisValue[1] + "', '" + kurtosisValue[2] + "', '" + skewnessValue[0] + "', " +
                    "'" + skewnessValue[1] + "', '" + skewnessValue[2] + "', '" + covarianceValue[0] + "', " +
                    "'" + covarianceValue[1] + "', '" + covarianceValue[2] + "', '" + correlationValue[0] + "', " +
                    "'" + correlationValue[1] + "', '" + correlationValue[2] + "', '" + energyValue[0] + "', '" +
                    energyValue[1] + "', '" + energyValue[2] + "', '" + Locomotion + "');";
        }
        // 执行插入语句
        dao.save(sqlAdd);
        System.out.println("动作" + tableName + "特征提取完毕！");
    }

    // 计算均值-3
    public double[] means(List<DataEntity33> list) {
        // 各轴加速度数据之和
        double sum_RKN = 0;
        double sum_HIP = 0;
        double sum_LUA = 0;
        // 各轴加速度数据均值
        double[] means = new double[3];

        // 循环读取查询到的数据记录
        for (int i = 0; i < list.size(); i++) {
            DataEntity33 dataEntity33 = list.get(i);
            // 计算各轴加速度数据之和
            sum_RKN = sum_RKN + Double.parseDouble(dataEntity33.getRkn());
            sum_HIP = sum_HIP + Double.parseDouble(dataEntity33.getHip());
            sum_LUA = sum_LUA + Double.parseDouble(dataEntity33.getLua());
        }

        // 计算各轴加速度数据均值
        means[0] = sum_RKN / windowSize;
        means[1] = sum_HIP / windowSize;
        means[2] = sum_LUA / windowSize;

        return means;
    }

    // 计算方差-3
    public double[] variance(List<DataEntity33> list, double[] meanValue) {
        // 各轴加速度数据与均值之差的平方求和
        double square_RKN = 0;
        double square_HIP = 0;
        double square_LUA = 0;
        // 各轴加速度数据方差
        double[] variance = new double[3];

        // 循环读取查询到的数据记录
        for (int i = 0; i < list.size(); i++) {
            DataEntity33 dataEntity33 = list.get(i);
            // 计算各轴加速度数据与均值之差的平方求和
            double tmp = Double.parseDouble(dataEntity33.getRkn()) - meanValue[0];
            square_RKN = square_RKN + Math.pow(tmp, 2);
            tmp = Double.parseDouble(dataEntity33.getHip()) - meanValue[1];
            square_HIP = square_HIP + Math.pow(tmp, 2);
            tmp = Double.parseDouble(dataEntity33.getLua()) - meanValue[2];
            square_LUA = square_LUA + Math.pow(tmp, 2);
        }
        // 计算各轴加速度数据方差
        variance[0] = square_RKN / windowSize;
        variance[1] = square_HIP / windowSize;
        variance[2] = square_LUA / windowSize;

        return variance;
    }

    // 计算平均绝对误差-3
    public double[] mad(List<DataEntity33> list, double[] meanValue) {
        // 各轴加速度数据与均值之差的绝对值求和
        double mad_RKN = 0;
        double mad_HIP = 0;
        double mad_LUA = 0;
        // 各轴加速度数据平均绝对误差
        double[] mad = new double[3];
        for (int i = 0; i < list.size(); i++) {
            DataEntity33 dataEntity33 = list.get(i);
            // 计算各轴加速度数据与均值之差的平方求和
            double tmp = Double.parseDouble(dataEntity33.getRkn()) - meanValue[0];
            mad_RKN = mad_RKN + Math.abs(tmp);
            tmp = Double.parseDouble(dataEntity33.getHip()) - meanValue[1];
            mad_HIP = mad_HIP + Math.abs(tmp);
            tmp = Double.parseDouble(dataEntity33.getLua()) - meanValue[2];
            mad_LUA = mad_LUA + Math.abs(tmp);
        }
        // 计算各轴加速度数据平均绝对误差
        mad[0] = mad_RKN / windowSize;
        mad[1] = mad_HIP / windowSize;
        mad[2] = mad_LUA / windowSize;

        return mad;
    }

    // 计算均方根-3
    public double[] rms(List<DataEntity33> list) {
        double rms_RKN = 0;
        double rms_HIP = 0;
        double rms_LUA = 0;
        double[] rms = new double[3];
        for (int i = 0; i < list.size(); i++) {
            DataEntity33 dataEntity33 = list.get(i);
            double tmp = Math.pow(Double.parseDouble(dataEntity33.getRkn()), 2);
            rms_RKN = rms_RKN + tmp;
            tmp = Math.pow(Double.parseDouble(dataEntity33.getHip()), 2);
            rms_HIP = rms_HIP + tmp;
            tmp = Math.pow(Double.parseDouble(dataEntity33.getLua()), 2);
            rms_LUA = rms_LUA + tmp;
        }
        // 计算各轴加速度数据均方根
        rms[0] = Math.sqrt(rms_RKN / windowSize);
        rms[1] = Math.sqrt(rms_HIP / windowSize);
        rms[2] = Math.sqrt(rms_LUA / windowSize);

        return rms;
    }

    // 计算四分位差-3
    public double[] iqr(List<DataEntity33> list) {
        double[] array_RKN = new double[list.size()];
        double[] array_HIP = new double[list.size()];
        double[] array_LUA = new double[list.size()];
        double[] iqr = new double[3];
        for (int i = 0; i < list.size(); i++) {
            DataEntity33 dataEntity33 = list.get(i);
            double tmp = Double.parseDouble(dataEntity33.getRkn());
            array_RKN[i] = tmp;
            tmp = Double.parseDouble(dataEntity33.getHip());
            array_HIP[i] = tmp;
            tmp = Double.parseDouble(dataEntity33.getLua());
            array_LUA[i] = tmp;
        }
        Arrays.sort(array_RKN);
        Arrays.sort(array_HIP);
        Arrays.sort(array_LUA);

        iqr[0] = (array_RKN[3 * (list.size() + 1) / 4] - array_RKN[(list.size() + 1) / 4]) / 2;
        iqr[1] = (array_HIP[3 * (list.size() + 1) / 4] - array_HIP[(list.size() + 1) / 4]) / 2;
        iqr[2] = (array_LUA[3 * (list.size() + 1) / 4] - array_LUA[(list.size() + 1) / 4]) / 2;

        return iqr;
    }

    // 计算极差-3
    public double[] range(List<DataEntity33> list) {
        double[] array_RKN = new double[list.size()];
        double[] array_HIP = new double[list.size()];
        double[] array_LUA = new double[list.size()];
        double[] range = new double[3];
        for (int i = 0; i < list.size(); i++) {
            DataEntity33 dataEntity33 = list.get(i);
            double tmp = Double.parseDouble(dataEntity33.getRkn());
            array_RKN[i] = tmp;
            tmp = Double.parseDouble(dataEntity33.getHip());
            array_HIP[i] = tmp;
            tmp = Double.parseDouble(dataEntity33.getLua());
            array_LUA[i] = tmp;
        }
        Arrays.sort(array_RKN);
        Arrays.sort(array_HIP);
        Arrays.sort(array_LUA);

        range[0] = array_RKN[list.size() - 1] - array_RKN[0];
        range[1] = array_HIP[list.size() - 1] - array_HIP[0];
        range[2] = array_LUA[list.size() - 1] - array_LUA[0];

        return range;
    }

    // 计算峰度-3
    public double[] kurtosis(List<DataEntity33> list, double[] meanValue, double[] variance) {
        double kurtosis_RKN = 0;
        double kurtosis_HIP = 0;
        double kurtosis_LUA = 0;
        double[] kurtosis = new double[3];
        for (int i = 0; i < list.size(); i++) {
            DataEntity33 dataEntity33 = list.get(i);
            double tmp = (Double.parseDouble(dataEntity33.getRkn()) - meanValue[0]) / Math.sqrt(variance[0]);
            kurtosis_RKN = kurtosis_RKN + Math.pow(tmp, 4);
            tmp = (Double.parseDouble(dataEntity33.getHip()) - meanValue[1]) / Math.sqrt(variance[1]);
            kurtosis_HIP = kurtosis_HIP + Math.pow(tmp, 4);
            tmp = (Double.parseDouble(dataEntity33.getLua()) - meanValue[2]) / Math.sqrt(variance[2]);
            kurtosis_LUA = kurtosis_LUA + Math.pow(tmp, 4);
        }
        kurtosis[0] = kurtosis_RKN / windowSize;
        kurtosis[1] = kurtosis_HIP / windowSize;
        kurtosis[2] = kurtosis_LUA / windowSize;

        return kurtosis;
    }

    // 计算偏度-3
    public double[] skewness(List<DataEntity33> list, double[] meanValue, double[] variance) {
        double skewness_RKN = 0;
        double skewness_HIP = 0;
        double skewness_LUA = 0;
        double[] skewness = new double[3];
        for (int i = 0; i < list.size(); i++) {
            DataEntity33 dataEntity33 = list.get(i);
            double tmp = (Double.parseDouble(dataEntity33.getRkn()) - meanValue[0]) / Math.sqrt(variance[0]);
            skewness_RKN = skewness_RKN + Math.pow(tmp, 3);
            tmp = (Double.parseDouble(dataEntity33.getHip()) - meanValue[1]) / Math.sqrt(variance[1]);
            skewness_HIP = skewness_HIP + Math.pow(tmp, 3);
            tmp = (Double.parseDouble(dataEntity33.getLua()) - meanValue[2]) / Math.sqrt(variance[2]);
            skewness_LUA = skewness_LUA + Math.pow(tmp, 3);
        }
        skewness[0] = skewness_RKN / windowSize;
        skewness[1] = skewness_HIP / windowSize;
        skewness[2] = skewness_LUA / windowSize;

        return skewness;
    }

    // 计算协方差-3
    public double[] covariance(List<DataEntity33> list, double[] meanValue) {
        double covariance_RKN_HIP = 0;
        double covariance_HIP_LUA = 0;
        double covariance_LUA_RKN = 0;
        double[] covariance = new double[3];
        for (int i = 0; i < list.size(); i++) {
            DataEntity33 dataEntity33 = list.get(i);
            double tmp = (Double.parseDouble(dataEntity33.getRkn()) - meanValue[0]) *
                    (Double.parseDouble(dataEntity33.getHip()) - meanValue[1]);
            covariance_RKN_HIP = covariance_RKN_HIP + tmp;
            tmp = (Double.parseDouble(dataEntity33.getHip()) - meanValue[1]) *
                    (Double.parseDouble(dataEntity33.getLua()) - meanValue[2]);
            covariance_HIP_LUA = covariance_HIP_LUA + tmp;
            tmp = (Double.parseDouble(dataEntity33.getLua()) - meanValue[2]) *
                    (Double.parseDouble(dataEntity33.getRkn()) - meanValue[0]);
            covariance_LUA_RKN = covariance_LUA_RKN + tmp;
        }
        covariance[0] = covariance_RKN_HIP / windowSize;
        covariance[1] = covariance_HIP_LUA / windowSize;
        covariance[2] = covariance_LUA_RKN / windowSize;

        return covariance;
    }


    // 计算相关系数
    public double[] correlation(double[] covariance, double[] variance) {
        double[] correlation = new double[3];
        correlation[0] = covariance[0] / Math.sqrt(variance[0] * variance[1]);
        correlation[1] = covariance[1] / Math.sqrt(variance[1] * variance[2]);
        correlation[2] = covariance[2] / Math.sqrt(variance[2] * variance[0]);

        return correlation;
    }


    // 计算能量
    public double[] energy(List<DataEntity33> list) {
        // 数据实部
        Double[] real_RKN = new Double[windowSize];
        Double[] real_HIP = new Double[windowSize];
        Double[] real_LUA = new Double[windowSize];
        // 数据虚部
        Double[] imag_RKN = new Double[windowSize];
        Double[] imag_HIP = new Double[windowSize];
        Double[] imag_LUA = new Double[windowSize];
        // 各轴数据的能量
        double[] energy = new double[3];
        // 下标
        int index = 0;

        // 循环读取查询到的数据记录
        for (int i = 0; i < list.size(); i++) {
            DataEntity33 dataEntity33 = list.get(i);
            // 数据实部
            real_RKN[index] = Double.parseDouble(dataEntity33.getRkn());
            real_HIP[index] = Double.parseDouble(dataEntity33.getHip());
            real_LUA[index] = Double.parseDouble(dataEntity33.getLua());
            // 数据虚部
            imag_RKN[index] = new Double(0);
            imag_HIP[index] = new Double(0);
            imag_LUA[index] = new Double(0);
            index++;
        }
        // 快速傅里叶变换
        FFT(real_RKN, imag_RKN, windowSize);
        FFT(real_HIP, imag_HIP, windowSize);
        FFT(real_LUA, imag_LUA, windowSize);
        // 计算能量
        for (int i = 0; i < windowSize; i++) {
            energy[0] += Math.abs((real_RKN[i]) * (real_RKN[i]) - (imag_RKN[i]) * (imag_RKN[i]));
            energy[1] += Math.abs((real_HIP[i]) * (real_HIP[i]) - (imag_HIP[i]) * (imag_HIP[i]));
            energy[2] += Math.abs((real_LUA[i]) * (real_LUA[i]) - (imag_LUA[i]) * (imag_LUA[i]));
        }
        for (int j = 0; j < 3; j++) {
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
