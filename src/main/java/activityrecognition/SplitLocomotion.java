package activityrecognition;

import activityrecognition.dao.SplitLocomotionDao;
import activityrecognition.daoImpl.SplitLocomotionImpl;
import activityrecognition.entity.DataEntity;

import java.util.List;

/**
 * 将不同的动作类型数据存储在不同的表中
 * 1   -   Locomotion   -   Stand
   2   -   Locomotion   -   Walk
   4   -   Locomotion   -   Sit
   5   -   Locomotion   -   Lie
 * Created by anyang on 2016/12/23.
 */
public class SplitLocomotion {

    public void startSplit() {
        System.out.println("********************动作类型划分开始**********************");
        split(1, "stand");
        split(2, "walk");
        split(4, "sit");
        split(5, "lie");
        System.out.println("********************动作类型划分结束**********************");
    }


    public void split(int Locomotion, String tableName){
        // 创建FeatureExtractionDao类
        SplitLocomotionDao dao = new SplitLocomotionImpl();
        // 查询特定动作数据信息的sql语句
        String sqlFind = "select * from preprocessingdata where Locomotion=" + Locomotion + ";";
        // 执行查询操作
        List<DataEntity> dataEntityList = dao.search(sqlFind);
        // sql插入语句
        String[] sqlAdd = new String[dataEntityList.size()];
        // sql数组下标
        int index = 0;
        // 循环读取查询到的数据记录
        for(int i = 0; i < dataEntityList.size(); i++){
            DataEntity dataEntity = dataEntityList.get(i);
            sqlAdd[index] = "insert into " + tableName + " (Time, RKN_accX, RKN_accY, RKN_accZ, HIP_accX, HIP_accY, " +
                    "HIP_accZ, LUA_accX, LUA_accY, LUA_accZ, Locomotion) values ('" + dataEntity.getTime() + "', '"
                    + dataEntity.getRkn_x() + "', '" + dataEntity.getRkn_y() + "', '" + dataEntity.getRkn_z() + "', '"
                    + dataEntity.getHip_x() + "', '" + dataEntity.getHip_y() + "', '" + dataEntity.getRkn_z() +
                    "', '" + dataEntity.getLua_x() + "', '" + dataEntity.getLua_y() + "', '"
                    + dataEntity.getLua_z() + "', '" + dataEntity.getLocomotion() + "');";
            index++;
        }
        // 执行插入语句
        dao.save(sqlAdd);
        System.out.println(tableName + "表插入完毕，共" + sqlAdd.length + "条数据！");
    }
}
