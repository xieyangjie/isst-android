package cn.edu.zju.isst.ui.contact;

import java.io.Serializable;

public class ContactFilter  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 890650792358672364L;
	
	public Integer id;
	public String username;
	public String name;
	public Integer gender;
	public Integer grade;
	public Integer classId;
	public Integer majorId;
	public Integer cityId;
	public String company;
	//以下是现实的字符串
	public String genderString;
	public String majorString;
	public String cityString;
	public ContactFilter() {
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
