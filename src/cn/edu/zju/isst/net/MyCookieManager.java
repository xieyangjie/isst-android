/**
 * 
 */
package cn.edu.zju.isst.net;

import org.apache.http.client.CookieStore;

/**
 * @author theasir
 * 
 */
public class MyCookieManager {

	private static CookieStore cookieStore = null;

	public static synchronized void setCookieStore(CookieStore cookieStore) {
		if (MyCookieManager.cookieStore != null) {
			MyCookieManager.cookieStore.clear();
		}
		MyCookieManager.cookieStore = cookieStore;
	}

	public static synchronized CookieStore getCookieStore() {
		return MyCookieManager.cookieStore;
	}
	
	public static synchronized void updateCookie(){
		
	}
}
