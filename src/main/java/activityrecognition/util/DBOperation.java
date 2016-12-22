package activityrecognition.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 该类作用：数据库操作，增删查改
 * 
 * 主要方法：excutesql，closeConn和findsql
 * closeConn：关闭数据库连接
 * excutesql：适用于实现增删改操作，不适用于查询，且一次性可以执行多条sql语句
 * findsql：用于查询功能的方法，且只适用于查询
 * @author anyang
 *
 */
public class DBOperation {
	public Connection con=null;
	public Statement st=null;
	public ResultSet rs=null;
	
	// 只适用于增删改操作，不适用于查询操作
	public boolean excutesql(String[] sql) {
		
		boolean bool = true;
		DButil util = new DButil();
		Connection con=util.openConnection();
		
		try {
			// 自动提交：在做记录更新时，系统会自动提交,不能保持事务的一致性，也就不能保证数据完整。
			// 手动提交：它则把事务处理将由你来完成，在发生异常时，可以进行事务回滚。保持事务的一致。
			con.setAutoCommit(false);	
		    st=con.createStatement();
		   
			for(int i = 0; i < sql.length; i++){
//				System.out.println(sql[i]);
				// 当要执行多条sql语句时，可以通过jdbc的批处理机制完成，这样可以提高执行效率。
				st.addBatch(sql[i]);
			}
			// 执行批处理
			st.executeBatch();
			con.commit();
//			System.out.println("执行成功！");
			
		} catch (SQLException e) {
			try {	
//				System.out.println("执行失败！");
				bool=false;
				con.rollback();
				
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			e.printStackTrace();
			
		}finally{
			
			if(st!=null){
				try {
					st.close();
					// 如果数据库连接不为null，则关闭数据库连接
					if(con!=null){	
						con.close();
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}								
			}
		}
		return bool;
	}
	
	// 用于查询功能的方法，且只适用于查询
	public ResultSet findsql(String sql) {
		
		try {
			DButil util = new DButil();
			con=util.openConnection();
			st=con.createStatement();
			rs=st.executeQuery(sql);
					
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	 return rs;
	}
	
	// 查询操作后，关闭数据库连接
	public void closeConn() {
		
		if(rs!=null)
		{
			try {
				rs.close();
				if(st!=null){	
					st.close();
					if(con!=null){	
						con.close();
					}
				}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		}
	}
}
