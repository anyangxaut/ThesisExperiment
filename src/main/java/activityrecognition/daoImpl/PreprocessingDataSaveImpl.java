package activityrecognition.daoImpl;

import activityrecognition.dao.PreprocessingDataSaveDao;
import activityrecognition.util.DBOperation;

/**
 * 接口实现类：实现数据库的操作
 * @author Administrator
 *
 */
public class PreprocessingDataSaveImpl implements PreprocessingDataSaveDao {

	// 将预处理后的数据保存至mysql数据库中
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
