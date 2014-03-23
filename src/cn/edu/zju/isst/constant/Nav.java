/**
 * 
 */
package cn.edu.zju.isst.constant;

/**
 * @author theasir
 * 导航项常量类
 */
public enum Nav {

	NEWS("软院新闻", 0, 0),
	WIKI("软院百科", 0, 1),
	SCAC("在校活动", 0, 2),
	SERV("便捷服务", 0, 3),
	STUD("学习园地", 0, 4),
	INTE("实习", 1, 0),
	JOBS("就业", 1, 1),
	REFE("内推", 1, 2),
	EXPE("经验交流", 1, 3),
	CIMA("城主", 2, 0),
	CIAC("同城活动", 2, 1),
	CIAL("同城校友", 2, 2),
	CONT("通讯录", 3, 0),
	USCE("个人中心", 4, 0);
	
	private String name;//导航项名称名
	private int index;//导航项组号
	private int indexOfGroup;//导航项组内序号
	
	private Nav(String name, int index, int indexOfGroup){
		this.name = name;
		this.index = index;
		this.indexOfGroup = indexOfGroup;
	}

	public String getName(){
		return name;
	}
	
	public int getIndex(){
		return index;
	}
	
	public int getIndexOfGroup(){
		return indexOfGroup;
	}
}
