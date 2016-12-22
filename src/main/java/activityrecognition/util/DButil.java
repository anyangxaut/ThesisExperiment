package activityrecognition.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 与数据库建立连接
 * @author anyang
 *
 */
public class DButil {

	// 连接数据库	
    public Connection openConnection() {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://202.200.119.179:3306/thesisexperiment?useUnicode=true&characterEncoding=utf8";
			String username = "root";
			String password = "root";

			try {
				Class.forName(driver);
				return DriverManager.getConnection(url, username, password);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
}

	

