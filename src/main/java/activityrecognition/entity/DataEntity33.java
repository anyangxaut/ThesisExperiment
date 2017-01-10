package activityrecognition.entity;

/**
 * (数据实体类)存储数据集中每个传感器的合成加速度
 * Column: 1 MILLISEC
   Column: 2 Accelerometer RKN^
   Column: 5 Accelerometer HIP
   Column: 8 Accelerometer LUA^
 */
public class DataEntity33 {
    private String time;
    private String rkn;
    private String hip;
    private String lua;
    private String locomotion;

    public DataEntity33(String time, String rkn, String hip, String lua, String locomotion) {
        this.time = time;
        this.rkn = rkn;
        this.hip = hip;
        this.lua = lua;
        this.locomotion = locomotion;
    }

    public String getTime() {
        return time;
    }

    public String getRkn() {
        return rkn;
    }

    public String getHip() {
        return hip;
    }

    public String getLua() {
        return lua;
    }

    public String getLocomotion() {
        return locomotion;
    }
}
