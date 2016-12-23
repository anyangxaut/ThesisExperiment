package activityrecognition;

import activityrecognition.dao.SplitLocomotionDao;
import activityrecognition.daoImpl.SplitLocomotionImpl;
import activityrecognition.entity.DataEntity;

import java.util.List;

/**
 * // 窗口划分，特征提取(采样频率64hz，窗口大小0.5s，1s，1.5s，2s，重叠率50%，均值，方差，平均绝对误差，均方根，四分位差，
 * 相关系数，极差，峰度，偏度，协方差，能量，熵，能谱密度，相关系数)
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
        featureExtraction(1, "stand", 109095);
        // 对"walk"动作进行特征提取
        featureExtraction(2, "walk", 65996);
        // 对"sit"动作进行特征提取
        featureExtraction(4, "sit", 43586);
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
            // 计算相关系数-81
            double[] correlationValue = correlation(list, meanValue, varianceValue);
            // 计算能量-9
            double[] energyValue = energy(list);

            sqlAdd[index++] = "INSERT INTO `featureextraction` (`RKN_accX_mean`, `RKN_accY_mean`, `RKN_accZ_mean`, `HIP_accX_mean`, `HIP_accY_mean`, " +
                    "`HIP_accZ_mean`, `LUA_accX_mean`, `LUA_accY_mean`, `LUA_accZ_mean`, `RKN_accX_variance`, `RKN_accY_variance`, " +
                    "`RKN_accZ_variance`, `HIP_accX_variance`, `HIP_accY_variance`, `HIP_accZ_variance`, `LUA_accX_variance`, `LUA_accY_variance`," +
                    "`LUA_accZ_variance`, `RKN_accX_RKN_accY_correlation`, `RKN_accX_RKN_accZ_correlation`," +
                    "`RKN_accX_HIP_accX_correlation`, `RKN_accX_HIP_accY_correlation`, `RKN_accX_HIP_accZ_correlation`, " +
                    "`RKN_accX_LUA_accX_correlation`, `RKN_accX_LUA_accY_correlation`, `RKN_accX_LUA_accZ_correlation`, " +
                    "`RKN_accY_RKN_accX_correlation`,  `RKN_accY_RKN_accZ_correlation`, " +
                    "`RKN_accY_HIP_accX_correlation`, `RKN_accY_HIP_accY_correlation`, `RKN_accY_HIP_accZ_correlation`, " +
                    "`RKN_accY_LUA_accX_correlation`, `RKN_accY_LUA_accY_correlation`, `RKN_accY_LUA_accZ_correlation`, " +
                    "`RKN_accZ_RKN_accX_correlation`, `RKN_accZ_RKN_accY_correlation`, " +
                    "`RKN_accZ_HIP_accX_correlation`, `RKN_accZ_HIP_accY_correlation`, `RKN_accZ_HIP_accZ_correlation`, " +
                    "`RKN_accZ_LUA_accX_correlation`, `RKN_accZ_LUA_accY_correlation`, `RKN_accZ_LUA_accZ_correlation`, " +
                    "`HIP_accX_RKN_accX_correlation`, `HIP_accX_RKN_accY_correlation`, `HIP_accX_RKN_accZ_correlation`, " +
                    " `HIP_accX_HIP_accY_correlation`, `HIP_accX_HIP_accZ_correlation`, " +
                    "`HIP_accX_LUA_accX_correlation`, `HIP_accX_LUA_accY_correlation`, `HIP_accX_LUA_accZ_correlation`, " +
                    "`HIP_accY_RKN_accX_correlation`, `HIP_accY_RKN_accY_correlation`, `HIP_accY_RKN_accZ_correlation`, " +
                    "`HIP_accY_HIP_accX_correlation`,  `HIP_accY_HIP_accZ_correlation`, " +
                    "`HIP_accY_LUA_accX_correlation`, `HIP_accY_LUA_accY_correlation`, `HIP_accY_LUA_accZ_correlation`, " +
                    "`HIP_accZ_RKN_accX_correlation`, `HIP_accZ_RKN_accY_correlation`, `HIP_accZ_RKN_accZ_correlation`, " +
                    "`HIP_accZ_HIP_accX_correlation`, `HIP_accZ_HIP_accY_correlation`, " +
                    "`HIP_accZ_LUA_accX_correlation`, `HIP_accZ_LUA_accY_correlation`, `HIP_accZ_LUA_accZ_correlation`, " +
                    "`LUA_accX_RKN_accX_correlation`, `LUA_accX_RKN_accY_correlation`, `LUA_accX_RKN_accZ_correlation`, " +
                    "`LUA_accX_HIP_accX_correlation`, `LUA_accX_HIP_accY_correlation`, `LUA_accX_HIP_accZ_correlation`, " +
                    "`LUA_accX_LUA_accY_correlation`, `LUA_accX_LUA_accZ_correlation`, " +
                    "`LUA_accY_RKN_accX_correlation`, `LUA_accY_RKN_accY_correlation`, `LUA_accY_RKN_accZ_correlation`, " +
                    "`LUA_accY_HIP_accX_correlation`, `LUA_accY_HIP_accY_correlation`, `LUA_accY_HIP_accZ_correlation`, " +
                    "`LUA_accY_LUA_accX_correlation`, `LUA_accY_LUA_accZ_correlation`, " +
                    "`LUA_accZ_RKN_accX_correlation`, `LUA_accZ_RKN_accY_correlation`, `LUA_accZ_RKN_accZ_correlation`, " +
                    "`LUA_accZ_HIP_accX_correlation`, `LUA_accZ_HIP_accY_correlation`, `LUA_accZ_HIP_accZ_correlation`, " +
                    "`LUA_accZ_LUA_accX_correlation`, `LUA_accZ_LUA_accY_correlation`, " +
                    "`RKN_accX_energy`, `RKN_accY_energy`, `RKN_accZ_energy`, `HIP_accX_energy`, `HIP_accY_energy`, " +
                    "`HIP_accZ_energy`, `LUA_accX_energy`, `LUA_accY_energy`, `LUA_accZ_energy`, `locomotion`) VALUES" +
                    "('" + meanValue[0] + "', '" + meanValue[1] + "', '" + meanValue[2] + "', '" + meanValue[3] + "', '" + meanValue[4] + "', '" + meanValue[5] + "', '" + meanValue[6] + "', '" + meanValue[7] + "', '" + meanValue[8] + "', '" + varianceValue[0] + "', '" + varianceValue[1] + "', '" + varianceValue[2] + "', '" + varianceValue[3] + "', '" + varianceValue[4] + "', '" + varianceValue[5] + "', '" + varianceValue[6] + "', '" + varianceValue[7] + "', '" + varianceValue[8] + "', '" +
                    correlationValue[1] + "', '" + correlationValue[2] + "', '" + correlationValue[3] + "', '" + correlationValue[4] + "', '" + correlationValue[5] + "', '" + correlationValue[6] + "', '" + correlationValue[7] + "', '" + correlationValue[8] + "', '" +
                    correlationValue[9] + "', '" + correlationValue[11] + "', '" + correlationValue[12] + "', '" + correlationValue[13] + "', '" + correlationValue[14] + "', '" + correlationValue[15] + "', '" + correlationValue[16] + "', '" + correlationValue[17] + "', '" +
                    correlationValue[18] + "', '" + correlationValue[19] + "', '" + correlationValue[21] + "', " + "'" + correlationValue[22] + "', '" + correlationValue[23] + "', '" + correlationValue[24] + "', '" + correlationValue[25] + "', '" + correlationValue[26] + "', '" +
                    correlationValue[27] + "', '" + correlationValue[28] + "', '" + correlationValue[29] + "', '" + correlationValue[31] + "', '" + correlationValue[32] + "', '" + correlationValue[33] + "', '" + correlationValue[34] + "', '" + correlationValue[35] + "', '" +
                    correlationValue[36] + "', '" + correlationValue[37] + "', '" + correlationValue[38] + "', '" + correlationValue[39] + "', '" + correlationValue[41] + "', '" + correlationValue[42] + "'," + " '" + correlationValue[43] + "', '" + correlationValue[44] + "', '" +
                    correlationValue[45] + "', '" + correlationValue[46] + "', '" + correlationValue[47] + "', '" + correlationValue[48] + "', '" + correlationValue[49] + "', '" + correlationValue[51] + "', '" + correlationValue[52] + "', '" + correlationValue[53] + "', '" +
                    correlationValue[54] + "', '" + correlationValue[55] + "', '" + correlationValue[56] + "', '" + correlationValue[57] + "', '" + correlationValue[58] + "', '" + correlationValue[59] + "', '" + correlationValue[61] + "', '" + correlationValue[62] + "', '" +
                    correlationValue[63] + "'," + " '" + correlationValue[64] + "', '" + correlationValue[65] + "', '" + correlationValue[66] + "', '" + correlationValue[67] + "', '" + correlationValue[68] + "', '" + correlationValue[69] + "', '" + correlationValue[71] + "', '" +
                    correlationValue[72] + "', '" + correlationValue[73] + "', '" + correlationValue[74] + "', '" + correlationValue[75] + "', '" + correlationValue[76] + "', '" + correlationValue[77] + "', '" + correlationValue[78] + "', '" + correlationValue[79] + "', '" + energyValue[0] + "', '" + energyValue[1] + "', '" + energyValue[2] + "', '" + energyValue[3] + "'," + " '" + energyValue[4] + "', '" + energyValue[5] + "', '" + energyValue[6] + "', '" + energyValue[7] + "', '" + energyValue[8] + "', '" + Locomotion + "');";
        }
        // 执行插入语句
        dao.save(sqlAdd);
        System.out.println("动作" + tableName + "特征提取完毕！");
    }

    // 计算均值
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

    // 计算方差
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

	// 计算相关系数
	public double[] correlation(List<DataEntity> list, double[] meanValue, double[] varianceValue){
		// 计算协方差*n
		double[][] correlation_difference = new double[9][9];
		// 计算相关系数
		double[] correlation = new double[81];
//		// 下标：0-80
//		int index = 0;
//
//		// 控制外层循环--9个轴的加速度数据
//		for(int m = 1; m < 10; m++){
//			// 控制内层循环--9个轴的加速度数据
//			for(int n = 1; n < 10; n++){
//				// 循环读取查询到的数据记录
//				for(int i = 0; i < list.size(); i++){
//					DataEntity dataEntity = list.get(i);
//					correlation_difference[m-1][n-1] += (Double.parseDouble(data.get(m))- meanValue[m-1])
//							* (Double.parseDouble(data.get(n))- meanValue[n-1]);
//				}
//			}
//		}
//
//		// 计算协方差及其相关系数
//		for(int m = 0; m < 9; m++){
//			for(int n = 0; n < 9; n++){
//				correlation[index] = (correlation_difference[m][n] / windowSize) / (Math.sqrt(varianceValue[m]*varianceValue[n]));
//				index++;
//			}
//		}
//
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
