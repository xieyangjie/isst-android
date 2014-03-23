/**
 * 
 */
package cn.edu.zju.isst.constant;

/**
 * @author theasir
 * 导航组常量类
 */
public enum NavGroup {

	LIFE("软院生活", 0),
	WORK("职场信息", 1),
	CITY("同城", 2);
	
	private String name;
	private int index;
	NavGroup(String name, int index){
		this.name = name;
		this.index = index;
	}
	
	public String getName(){
		return name;
	}
	
	public int getIndex(){
		return index;
	}
}
