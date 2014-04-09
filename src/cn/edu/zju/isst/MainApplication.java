/**
 * 
 */
package cn.edu.zju.isst;

import android.app.Application;
import cn.edu.zju.isst.util.L;

/**
 * 主应用入口，可以存放全局变量（建议常量类单独管理）
 * 
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
		// 设置调试状态
		L.setDebuggable(true);
	}

}
