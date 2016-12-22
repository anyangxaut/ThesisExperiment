package activityrecognition;

import activityrecognition.dao.PreprocessingDataSaveDao;
import activityrecognition.daoImpl.PreprocessingDataSaveImpl;
import activityrecognition.entity.DataEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 对数据进行预处理操作(删除含有缺失数据"NaN"或是Locomotion标签为"0"的数据项)
 *
 */
public class DataPreprocess {
    // 数据集所在目录
    private String filePath = "";

    /**
     * 初始化数据集所在路径
     * @param filePath
     */
    public DataPreprocess(String filePath) {
        this.filePath = filePath;
    }

    // 数据集中S1-ADL1，S1-ADL2，S1-ADL3，S1-Drill，S2-Drill用作训练数据，S1-ADL4，S1-ADL5用作测试数据
    public void startPreprocess(){
        System.out.println("********************数据预处理开始**********************");
        preprocessing("S1-ADL1.dat");
        preprocessing("S1-ADL2.dat");
        preprocessing("S1-ADL3.dat");
        preprocessing("S1-ADL4.dat");
        preprocessing("S1-ADL5.dat");
        preprocessing("S1-Drill.dat");
        preprocessing("S2-Drill.dat");
        System.out.println("********************数据预处理结束**********************");
    }

    private void preprocessing(String fileName){

        List<DataEntity> dataList = new ArrayList<DataEntity>();
        int originDataCounter = 0;

        // 根据文件目录及其名称创建File对象
        File dataFile = new File(filePath + fileName);

        // 判断文件是否存在，且其属性是否为file
        if(dataFile.exists() && dataFile.isFile()){
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFile));
                String dataLine;
                String[] wholeData;
                List<String> originDataItem;
                try {
                    // 按行读取数据集中的数据条目
                    while((dataLine =  bufferedReader.readLine()) != null){
                        // 创建新的List对象
                        // 这里需要注意，必须重新创建一个新的new ArrayList<String>()赋值给dataItem，否则当利用dataItem
                        // 创建DataEntity对象，并添加至dataList.add(dataEntity);中时，会由于引用了同一个dataItem对象而使得dataList
                        // 中的值都变成相同的（因为都引用了同一个对象地址），因此为了避免这种现象，需要新建一个dataItem引用
                        originDataItem = new ArrayList<String>();
                        // 将数据集中的数据条目按照空格分隔开来，并存储在数组中(0~249)
                        wholeData = dataLine.split(" ");
                        // 使用ArrayList存储wholeData中第0~9，243列数据
                        for(int i = 0; i < 10; i++){
                            originDataItem.add(wholeData[i]);

                        }
                        originDataItem.add(wholeData[243]);
                        // 利用dataItem创建DataEntity对象，该对象存储即将要使用到的数据信息，我们将这些数据视为原始数据item
                        DataEntity dataEntity = new DataEntity(originDataItem);
                        // DataEntity对象的集合，为原始数据集
                        dataList.add(dataEntity);
                        // 原始数据数量计数器
                        originDataCounter++;

                    }
                    // 输出信息
                    System.out.print(fileName + "原始数据集数据共" + originDataCounter + "项！");
                    // 数据预处理之删除含有缺失数据"NaN"或是Locomotion标签为"0"的数据item
                    deleteItem(dataList);
                    // 将预处理后的原始数据进行文件存储
//						saveDataAsFile();
                    // 将预处理后的原始数据进行数据库存储
                    saveDataAsDatabase(dataList);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else{
            // 要打开的文件不存在或者该文件属性不是一个文件
            System.out.println(dataFile.getName() + "is not exist or not a file!");
        }
    }

    // 数据预处理之删除含有缺失数据或是Locomotion标签为0的数据item
    private void deleteItem(List<DataEntity> dataList){
        DataEntity dataEntity;
        List<String> deleteDataItem;
        int deleteDataCounter = 0;
        // 按顺序取出原始数据item--DataEntity
        for(int i = 0;i < dataList.size();i++){
            dataEntity = (DataEntity)dataList.get(i);
            deleteDataItem = dataEntity.getDataItem();

            // 按顺序比较DataEntity中是否存在缺失值或是Locomotion标签为0，如果存在，则从原始数据中删除该DataEntity，并break跳出循环
            for(int j = 0; j < deleteDataItem.size();j++){

                if(deleteDataItem.get(j).equals("NaN")){
                    // 删除DataEntity
                    dataList.remove(i);
                    // 因为刚刚删除了一个item，因此i需要减1，使得下次循环可以访问当前item的下一条item
                    i--;
                    // 计数器
                    deleteDataCounter++;
                    break;
                }else if(j == (deleteDataItem.size() - 1) && deleteDataItem.get(j).equals("0")){
                    // 删除DataEntity
                    dataList.remove(i);
                    // 因为刚刚删除了一个item，因此i需要减1，使得下次循环可以访问当前item的下一条item
                    i--;
                    // 计数器
                    deleteDataCounter++;
                    break;
                }
            }
        }
        System.out.println("预处理删除数据共" + deleteDataCounter + "项！");
    }

    // 将预处理后的原始数据进行文件存储
//	private boolean saveDataAsFile(){
//
//		FileWriter writer;
//		try {
//			// 通过保存文件的路径及其文件名称初始化FileWriter对象
//			writer = new FileWriter(filePath + "DataPreprocess.txt",true);
//			// 将预处理后的原始数据逐条进行存储
//			for(int i = 0; i < dataList.size();i++){
//				writer.write(dataList.get(i).getDataInfo().toString() + "\n");
////				System.out.println(dataList.get(i).getDataInfo().get(10));
//				}
//			writer.close();
//			return true;
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//	}

    // 将预处理后的原始数据进行数据库存储
    private boolean saveDataAsDatabase(List<DataEntity> dataList){
        // 返回值
        boolean result = false;
        // 要执行的sql语句
        String[] sql = new String[dataList.size()];

        for(int i = 0; i < dataList.size(); i++){
            DataEntity dataEntity = dataList.get(i);
            sql[i] = "insert into preprocessingdata (Time, RKN_accX, RKN_accY, RKN_accZ, HIP_accX, HIP_accY, HIP_accZ, " +
                    "" + "LUA_accX, LUA_accY, LUA_accZ, Locomotion) values ('" + dataEntity.getTime() + "', '" +
                    dataEntity.getRkn_x() + "', '" + dataEntity.getRkn_y() + "', '" + dataEntity.getRkn_z() + "', '" +
                    dataEntity.getHip_x() + "', '" + dataEntity.getHip_y() + "', '" + dataEntity.getHip_z() + "', '" +
                    dataEntity.getLua_x() + "', '" + dataEntity.getLua_y() + "', '" + dataEntity.getLua_z() + "', '" +
                    dataEntity.getLocomotion() + "');";
        }

        // 创建PreprocessingDataSaveDao对象
        PreprocessingDataSaveDao dao = new PreprocessingDataSaveImpl();
        // 保存预处理后的数据信息
        result = dao.save(sql);
        // 返回result
        return result;
    }
}
