/**
 * 
 */
package cn.edu.zju.isst.api;

/**
 * @author theasir
 *
 */
public enum Category {

	CAMPUS("campus"),
	ENCYCLOPEDIA("encyclopediqa"),
	STUDING("studying"),
	EXPERIENCE("experience");
	
	private String subUrl;
	
	private Category(String subUrl){
		this.subUrl = subUrl;
	}

	/**
	 * @return the subUrl
	 */
	public String getSubUrl() {
		return subUrl;
	}
	
}
