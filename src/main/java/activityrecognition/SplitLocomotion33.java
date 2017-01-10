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
public class SplitLocomotion33 {

    public void startSplit() {
        System.out.println("********************动作类型划分开始**********************");
        split(1, "stand_33");
        split(2, "walk_33");
        split(4, "sit_33");
        split(5, "lie_33");
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

            double Rkn_x = Double.parseDouble(dataEntity.getRkn_x());
            double Rkn_y = Double.parseDouble(dataEntity.getRkn_y());
            double Rkn_z = Double.parseDouble(dataEntity.getRkn_z());
            double Rkn = Math.sqrt(Math.pow(Rkn_x, 2) + Math.pow(Rkn_y, 2) + Math.pow(Rkn_z, 2));

            double Hip_x = Double.parseDouble(dataEntity.getHip_x());
            double Hip_y = Double.parseDouble(dataEntity.getHip_y());
            double Hip_z = Double.parseDouble(dataEntity.getHip_z());
            double Hip = Math.sqrt(Math.pow(Hip_x, 2) + Math.pow(Hip_y, 2) + Math.pow(Hip_z, 2));

            double Lua_x = Double.parseDouble(dataEntity.getLua_x());
            double Lua_y = Double.parseDouble(dataEntity.getLua_y());
            double Lua_z = Double.parseDouble(dataEntity.getLua_z());
            double Lua = Math.sqrt(Math.pow(Lua_x, 2) + Math.pow(Lua_y, 2) + Math.pow(Lua_z, 2));

            sqlAdd[index] = "insert into " + tableName + " (Time, RKN, HIP, LUA, Locomotion) values ('" +
                    dataEntity.getTime() + "', '" + String.valueOf(Rkn) + "', '" + String.valueOf(Hip) + "', '"
                    + String.valueOf(Lua) + "', '" + dataEntity.getLocomotion() + "');";
            index++;
        }
        // 执行插入语句
        dao.save(sqlAdd);
        System.out.println(tableName + "表插入完毕，共" + sqlAdd.length + "条数据！");
    }
}
