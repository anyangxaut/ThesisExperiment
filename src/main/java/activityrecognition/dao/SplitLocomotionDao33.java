package activityrecognition.dao;

import activityrecognition.entity.DataEntity33;

import java.util.List;

/**
 * 接口类：定义操作数据库的方法
 * Created by anyang on 2016/12/23.
 */
public interface SplitLocomotionDao33 {
    public List<DataEntity33> search(String sql);
    public boolean save(String[] sql);
}
