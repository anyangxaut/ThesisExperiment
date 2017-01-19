package activityrecognition.entity;

/**
 * KNN结点类，用来存储最近邻的k个元组相关的信息
 *
 */
public class KNNNode {
	// 元组标号
	private int index; 
	// 与测试元组的距离
	private double distance; 
	// 所属类别
	private String label; 
	
	public KNNNode(int index, double distance, String label) {
		super();
		this.index = index;
		this.distance = distance;
		this.label = label;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public String getLabel() {
		return label;
	}
	public void setC(String label) {
		this.label = label;
	}
}

