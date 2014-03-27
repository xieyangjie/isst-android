/**
 * 
 */
package cn.edu.zju.isst;

import org.apache.http.client.CookieStore;

import android.app.Application;
import cn.edu.zju.isst.util.L;

/**
 * @author theasir
 * 
 */
public class MainApplication extends Application {

	private static CookieStore cookieStore = null;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		L.setDebuggable(true);

	}
	
	public static void setCookieStore(CookieStore cookieStore){
		MainApplication.cookieStore = cookieStore;
	}
	
	public static CookieStore getCookieStore(){
		return MainApplication.cookieStore;
	}
	
}
