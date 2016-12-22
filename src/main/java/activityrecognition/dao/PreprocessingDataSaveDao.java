package activityrecognition.dao;
/**
 * 接口类：定义操作数据库的方法
 * @author Administrator
 *
 */
public interface PreprocessingDataSaveDao {

	// 将预处理后的数据保存至mysql数据库中
	public boolean save(String[] sql);
}
