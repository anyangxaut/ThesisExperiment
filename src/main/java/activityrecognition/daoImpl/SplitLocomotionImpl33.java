package activityrecognition.daoImpl;

import activityrecognition.dao.SplitLocomotionDao33;
import activityrecognition.entity.DataEntity33;
import activityrecognition.util.DBOperation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anyang on 2016/12/23.
 */
public class SplitLocomotionImpl33 implements SplitLocomotionDao33 {

    public List<DataEntity33> search(String sql) {
        // 创建返回值对象
        List<DataEntity33> result = new ArrayList<DataEntity33>();
        // 创建数据库操作类对象--增删查改
        DBOperation dboperation = new DBOperation ();
        // 调用DBOperation对象的findsql方法执行sql语句---查
        ResultSet rs = dboperation.findsql(sql);

        try {
            // 循环读取查询到的数据记录
            while (rs != null && rs.next() == true){
                DataEntity33 dataEntity33 = new DataEntity33(rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6));
                result.add(dataEntity33);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            // 关闭数据库连接
            dboperation.closeConn();
        }
        // 返回查询到的数据信息
        return result;
    }

    public boolean save(String[] sql) {
        // 创建数据库操作类对象--增删查改
        DBOperation dboperation = new DBOperation ();
        // 调用DBOperation对象的excutesql方法执行sql语句---增删改
        boolean rs = dboperation.excutesql(sql);
        // 关闭数据库连接
        dboperation.closeConn();
        // sql语句是否执行成功
        return rs;
    }
}
