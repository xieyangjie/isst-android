/**
 *
 */
package cn.edu.zju.isst.ui.loading;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.api.VersionApi;
import cn.edu.zju.isst.net.CSTResponse;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.net.UpdateManager;
import cn.edu.zju.isst.settings.CSTSettings;
import cn.edu.zju.isst.ui.login.LoginActivity;
import cn.edu.zju.isst.ui.main.NewMainActivity;
import cn.edu.zju.isst.util.J;
import cn.edu.zju.isst.util.L;
import cn.edu.zju.isst.v2.net.CSTStatusInfo;
import cn.edu.zju.isst.v2.net.NewCSTResponse;
import cn.edu.zju.isst.v2.net.Response.VersionResponse;
import cn.edu.zju.isst.v2.net.VolleyImpl;

import static cn.edu.zju.isst.constant.Constants.STATUS_REQUEST_SUCCESS;

/**
 * 加载页面
 *
 * @author theasir
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
//        // 打开推送
//        L.i("Loading____pushSetting");
//        PushSettings.enableDebugMode(getApplicationContext(), true);
//
//        // 以apikey的方式登录，一般放在主Activity的onCreate中
//
//        PushManager.startWork(getApplicationContext(),
//                PushConstants.LOGIN_TYPE_API_KEY, "PqDQfrucX3ubvW7fm0M23gWu");

        initAlertDialog();

        initHandler();

        requestVersionInfo();
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
                }
        );

        m_aldUpdate.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jump();
                    }
                }
        );

        m_aldUpdate.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                jump();
            }
        });
    }

    private void initHandler() {
        m_handlerLoading = new Handler() {

            /*
             * (non-Javadoc)
             *
             * @see android.os.Handler#handleMessage(android.os.Message)
             */
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case STATUS_REQUEST_SUCCESS:
                        try {
                            if (getPackageManager().getPackageInfo(getPackageName(), 0).versionCode
                                    < (Integer) msg.obj) {
                                m_aldUpdate.show();
                            } else {
                                jump();
                            }
                        } catch (NameNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        break;

                    default:
                        jump();
                        break;
                }
            }

        };
    }

    private void requestVersionInfo() {
        VolleyImpl.requestJsonObject(Request.Method.GET,
                "http://www.cst.zju.edu.cn/isst/api/android/version", null,
                new VersionResponse(this) {
                    @Override
                    public void onResponse(JSONObject result) {
                        //TODO handle response
                        L.i("requestVersionInfo (JSONObject result)：  " + result.toString());

                        Message msg = m_handlerLoading.obtainMessage();
                        try {
                            if (!J.isValidJsonValue("status", (JSONObject) result)) {
                                return;
                            }
                            msg.what = ((JSONObject) result).getInt("status");
                            msg.obj = ((JSONObject) result).getJSONObject("body").getInt("build");
                        } catch (JSONException e) {
                            L.i(this.getClass().getName() + " onComplete!");
                            e.printStackTrace();
                        }
                        m_handlerLoading.sendMessage(msg);
                    }
                }
        );
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
