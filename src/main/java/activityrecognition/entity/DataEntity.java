package activityrecognition.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * (数据实体类)存储数据集中每条数据项的第1~10列
 * Column: 1 MILLISEC
   Column: 2 Accelerometer RKN^ accX; value = round(original_value), unit = milli g
   Column: 3 Accelerometer RKN^ accY; value = round(original_value), unit = milli g
   Column: 4 Accelerometer RKN^ accZ; value = round(original_value), unit = milli g
   Column: 5 Accelerometer HIP accX; value = round(original_value), unit = milli g
   Column: 6 Accelerometer HIP accY; value = round(original_value), unit = milli g
   Column: 7 Accelerometer HIP accZ; value = round(original_value), unit = milli g
   Column: 8 Accelerometer LUA^ accX; value = round(original_value), unit = milli g
   Column: 9 Accelerometer LUA^ accY; value = round(original_value), unit = milli g
   Column: 10 Accelerometer LUA^ accZ; value = round(original_value), unit = milli g
 */
public class DataEntity {
    private String time;

    private String rkn_x;
    private String rkn_y;
    private String rkn_z;

    private String hip_x;
    private String hip_y;
    private String hip_z;

    private String lua_x;
    private String lua_y;
    private String lua_z;

    private String locomotion;

    public DataEntity(String time, String rkn_x, String rkn_y, String rkn_z, String hip_x, String hip_y, String hip_z,
                      String lua_x, String lua_y, String lua_z, String locomotion) {
        this.time = time;
        this.rkn_x = rkn_x;
        this.rkn_y = rkn_y;
        this.rkn_z = rkn_z;
        this.hip_x = hip_x;
        this.hip_y = hip_y;
        this.hip_z = hip_z;
        this.lua_x = lua_x;
        this.lua_y = lua_y;
        this.lua_z = lua_z;
        this.locomotion = locomotion;
    }

    public DataEntity(List<String> dataEntity) {
        this(dataEntity.get(0), dataEntity.get(1), dataEntity.get(2), dataEntity.get(3), dataEntity.get(4),
                dataEntity.get(5), dataEntity.get(6), dataEntity.get(7), dataEntity.get(8), dataEntity.get(9),
                dataEntity.get(10));
    }

    public String getTime() {
        return time;
    }

    public String getRkn_x() {
        return rkn_x;
    }

    public String getRkn_y() {
        return rkn_y;
    }

    public String getRkn_z() {
        return rkn_z;
    }

    public String getHip_x() {
        return hip_x;
    }

    public String getHip_y() {
        return hip_y;
    }

    public String getHip_z() {
        return hip_z;
    }

    public String getLua_x() {
        return lua_x;
    }

    public String getLua_y() {
        return lua_y;
    }

    public String getLua_z() {
        return lua_z;
    }

    public String getLocomotion() {
        return locomotion;
    }

    @Override
    public String toString() {
        return "DataEntity{" +
                "time='" + time + '\'' +
                ", rkn_x='" + rkn_x + '\'' +
                ", rkn_y='" + rkn_y + '\'' +
                ", rkn_z='" + rkn_z + '\'' +
                ", hip_x='" + hip_x + '\'' +
                ", hip_y='" + hip_y + '\'' +
                ", hip_z='" + hip_z + '\'' +
                ", lua_x='" + lua_x + '\'' +
                ", lua_y='" + lua_y + '\'' +
                ", lua_z='" + lua_z + '\'' +
                '}';
    }

    public List<String> getDataItem() {
        List<String> dataList = new ArrayList<String>();
        dataList.add(getTime());
        dataList.add(getRkn_x());
        dataList.add(getRkn_y());
        dataList.add(getRkn_z());
        dataList.add(getHip_x());
        dataList.add(getHip_y());
        dataList.add(getHip_z());
        dataList.add(getLua_x());
        dataList.add(getLua_y());
        dataList.add(getLua_z());
        dataList.add(getLocomotion());
        return dataList;
    }
}
