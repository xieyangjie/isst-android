/**
 * 
 */
package cn.edu.zju.isst;

import android.app.Application;
import cn.edu.zju.isst.util.L;

/**
 * @author theasir
 * 
 */
public class MainApplication extends Application {

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

}
