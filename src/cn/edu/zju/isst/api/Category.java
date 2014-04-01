/**
 * 
 */
package cn.edu.zju.isst.api;

/**
 * 归档类别枚举类
 * 
 * @author theasir
 * 
 */
public enum Category {

	CAMPUS("campus"), ENCYCLOPEDIA("encyclopedia"), STUDING("studying"), EXPERIENCE(
			"experience");

	private String subUrl;

	private Category(String subUrl) {
		this.subUrl = subUrl;
	}

	/**
	 * @return the subUrl
	 */
	public String getSubUrl() {
		return subUrl;
	}

}
