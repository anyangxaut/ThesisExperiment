package activityrecognition.daoImpl;

import activityrecognition.dao.ClassificationAlgorithmsDao;
import activityrecognition.util.DBOperation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassificationAlgorithmsImpl implements ClassificationAlgorithmsDao {
	// 查询数据库中的数据库,并以list的形式返回数据信息
	public List<List<Double>> search(String sql, int[] selectedColumn) {
		// 创建返回值对象
		List<List<Double>> result = new ArrayList<List<Double>>();
		// 创建数据库操作类对象--增删查改
		DBOperation dboperation = new DBOperation ();
		// 调用DBOperation对象的findsql方法执行sql语句---查
		ResultSet rs = dboperation.findsql(sql);
		try {
			// 循环读取查询到的数据记录
			while (rs != null && rs.next() == true){
				// 将每条数据记录存储在list中
				List<Double> data = new ArrayList<Double>();
				for(int i = 0; i < selectedColumn.length; i++){
					data.add(Double.parseDouble(rs.getString(selectedColumn[i] + 1)));
				}
				data.add(Double.parseDouble(rs.getString(35)));
				// 讲数据记录添加至返回值列表
				result.add(data);
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// 关闭数据库连接
		dboperation.closeConn();
		// 返回查询到的数据信息
		return result;
	}
}
