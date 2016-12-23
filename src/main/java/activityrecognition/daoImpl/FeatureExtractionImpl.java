package activityrecognition.daoImpl;

import activityrecognition.dao.FeatureExtractionDao;
import activityrecognition.util.DBOperation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeatureExtractionImpl implements FeatureExtractionDao {

	// 查询数据库中的数据库,并以list的形式返回数据信息
	public List<List<Double>> search(String sql) {
		// 创建返回值对象
		List<List<Double>> result = new ArrayList<List<Double>>();
		// 创建数据库操作类对象--增删查改
		DBOperation dboperation = new DBOperation();
		// 调用DBOperation对象的findsql方法执行sql语句---查
		ResultSet rs = dboperation.findsql(sql);
		
			try {
				// 循环读取查询到的数据记录
				while (rs != null && rs.next() == true){
					// 将每条数据记录存储在list中
					List<Double> data = new ArrayList<Double>();
					for(int i = 2; i < 102 ; i++){
						data.add(Double.parseDouble(rs.getString(i)));
					}
					// 数据记录添加至返回值列表
					result.add(data);
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

	// 将特征提取后的数据保存至mysql数据库中
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
