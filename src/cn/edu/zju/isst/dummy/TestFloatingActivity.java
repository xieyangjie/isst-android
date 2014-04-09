/**
 * 
 */
package cn.edu.zju.isst.dummy;

import cn.edu.zju.isst.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 * @author theasir
 * 
 */
public class TestFloatingActivity extends Activity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.zju.isst.ui.main.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setUpActivity();

	}

	private void setUpActivity() {
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
				WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		LayoutParams params = getWindow().getAttributes();
		params.height = 1200; // fixed height
		params.width = LayoutParams.MATCH_PARENT; // fixed width
		params.alpha = 1.0f;
		params.dimAmount = 0.5f;
		getWindow().setAttributes(params);
		setContentView(R.layout.loading_activity);
	}

}
