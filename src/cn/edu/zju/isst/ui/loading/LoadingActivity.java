/**
 * 
 */
package cn.edu.zju.isst.ui.loading;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.android.pushservice.PushSettings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import cn.edu.zju.isst.R;
import cn.edu.zju.isst.net.UpdateManager;
import cn.edu.zju.isst.settings.CSTSettings;
import cn.edu.zju.isst.ui.login.LoginActivity;
import cn.edu.zju.isst.ui.main.MainActivity;
import cn.edu.zju.isst.ui.main.NewMainActivity;
import cn.edu.zju.isst.util.L;

/**
 * 加载页面
 * 
 * @author theasir
 * 
 */
public class LoadingActivity extends Activity {

    private Handler m_handlerLoading;

    private AlertDialog.Builder m_aldUpdate;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.loading_activity);
	// 打开推送
	L.i("Loading____pushSetting");
	PushSettings.enableDebugMode(getApplicationContext(), true);

	// 以apikey的方式登录，一般放在主Activity的onCreate中

	PushManager.startWork(getApplicationContext(),
		PushConstants.LOGIN_TYPE_API_KEY, "PqDQfrucX3ubvW7fm0M23gWu");

	initAlertDialog();

	jump();
	// m_aldUpdate.show();
    }

    private void initAlertDialog() {
	m_aldUpdate = new AlertDialog.Builder(LoadingActivity.this);
	m_aldUpdate.setTitle(R.string.new_update_avaliable);
	m_aldUpdate.setMessage(R.string.update_detail);
	m_aldUpdate.setPositiveButton(R.string.OK,
		new DialogInterface.OnClickListener() {

		    @Override
		    public void onClick(DialogInterface dialog, int which) {
			UpdateManager.createInstance(LoadingActivity.this
				.getApplicationContext());
			UpdateManager.getInstance().downloadUpdate();

			jump();
		    }
		});

	m_aldUpdate.setNegativeButton(R.string.cancel,
		new DialogInterface.OnClickListener() {

		    @Override
		    public void onClick(DialogInterface dialog, int which) {
			jump();
		    }
		});

	m_aldUpdate.setOnCancelListener(new DialogInterface.OnCancelListener() {

	    @Override
	    public void onCancel(DialogInterface dialog) {
		jump();
	    }
	});
    }

    private void jump() {
	L.i(this.getClass().getName() + " jump isAutoLogin? "
		+ CSTSettings.isAutoLogin(LoadingActivity.this));
	if (CSTSettings.isAutoLogin(LoadingActivity.this)) {
	    LoadingActivity.this.startActivity(new Intent(LoadingActivity.this,
		    NewMainActivity.class));
	    
	} else {
	    LoadingActivity.this.startActivity(new Intent(LoadingActivity.this,
		    LoginActivity.class));
	}
	LoadingActivity.this.finish();
    }

}
