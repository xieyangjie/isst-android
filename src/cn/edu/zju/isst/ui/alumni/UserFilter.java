package cn.edu.zju.isst.ui.alumni;

public class UserFilter {

	public Integer id;
	public String username;
	public String name;
	public Integer gender;
	public Integer grade;
	public Integer classId;
	public Integer majorId;
	public Integer cityId;
	public String company;
	public UserFilter() {
		clear();
	}
	//清空条件
	public void clear() {
		id = null;
		username = null;
		name = null;
		gender = null;
		grade = null;
		classId = null;
		majorId = null;
		cityId = null;
		company = null;
	}
}
