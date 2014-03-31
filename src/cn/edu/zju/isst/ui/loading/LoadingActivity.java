/**
 * 
 */
package cn.edu.zju.isst.ui.loading;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.settings.CSTSettings;
import cn.edu.zju.isst.ui.login.LoginActivity;
import cn.edu.zju.isst.ui.main.MainActivity;
import cn.edu.zju.isst.util.L;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * 加载页面
 * 
 * @author theasir
 * 
 */
public class LoadingActivity extends Activity {

	private Handler m_handlerLoading;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_activity);

		jump();
	}

	private void jump() {
		L.i(this.getClass().getName() + " jump isAutoLogin? "
				+ CSTSettings.isAutoLogin(LoadingActivity.this));
		if (CSTSettings.isAutoLogin(LoadingActivity.this)) {
			LoadingActivity.this.startActivity(new Intent(LoadingActivity.this,
					MainActivity.class));
		} else {
			LoadingActivity.this.startActivity(new Intent(LoadingActivity.this,
					LoginActivity.class));
		}
		LoadingActivity.this.finish();
	}

}
