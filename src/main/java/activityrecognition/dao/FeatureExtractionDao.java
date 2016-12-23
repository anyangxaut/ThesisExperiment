package activityrecognition.dao;

import java.util.List;

/**
 * 接口类：定义操作数据库的方法
 * @author Administrator
 *
 */
public interface FeatureExtractionDao {
	public List<List<Double>> search(String sql);
	public boolean save(String[] sql);
}
