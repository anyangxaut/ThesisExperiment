package activityrecognition.dao;

import java.util.List;

/**
 * 接口类：定义操作数据库的方法
 * @author Administrator
 *
 */
public interface ClassificationAlgorithmsDao {
	// 查询数据库中的数据库,并以List的形式返回数据信息
	public List<List<Double>> search(String sql, int[] selectedColumn);
}
