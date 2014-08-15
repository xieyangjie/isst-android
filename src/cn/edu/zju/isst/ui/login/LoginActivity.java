/**
 *
 */
package cn.edu.zju.isst.ui.login;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.api.LoginApi;
import cn.edu.zju.isst.db.DataManager;
import cn.edu.zju.isst.db.User;
import cn.edu.zju.isst.exception.ExceptionWeeder;
import cn.edu.zju.isst.exception.HttpErrorWeeder;
import cn.edu.zju.isst.net.CSTResponse;
import cn.edu.zju.isst.net.NetworkConnection;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.settings.CSTSettings;
import cn.edu.zju.isst.ui.main.BaseActivity;
import cn.edu.zju.isst.ui.main.NewMainActivity;
import cn.edu.zju.isst.util.CM;
import cn.edu.zju.isst.util.L;
import cn.edu.zju.isst.v2.net.NewCSTResponse;
import cn.edu.zju.isst.v2.net.Response.LoginResponse;

import static cn.edu.zju.isst.constant.Constants.NETWORK_NOT_CONNECTED;
import static cn.edu.zju.isst.constant.Constants.STATUS_LOGIN_AUTH_EXPIRED;
import static cn.edu.zju.isst.constant.Constants.STATUS_LOGIN_AUTH_FAILED;
import static cn.edu.zju.isst.constant.Constants.STATUS_LOGIN_PASSWORD_ERROR;
import static cn.edu.zju.isst.constant.Constants.STATUS_LOGIN_USERNAME_NOT_EXIST;
import static cn.edu.zju.isst.constant.Constants.STATUS_REQUEST_SUCCESS;

/**
 * 登录页面
 *
 * @author theasir
 */
public class LoginActivity extends BaseActivity {

    private boolean m_bIsLoginAgain;

    private String m_strUserName;

    private char[] m_strPassword;

    private Handler m_handlerLogin;

    private EditText m_edtxUserName;

    private EditText m_edtxPassword;

    private CheckBox m_chbAutologin;

    private Button m_btnLogin;

    private ProgressDialog m_pgdWating;

    /*
     * (non-Javadoc)
     *
     * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // 为什么要这么做？参看CSTSettings类以及SharedPreferences#getBoolean(String key,
        // boolean defValue)
        if (CSTSettings.isForTheFirstTime(LoginActivity.this)) {
            CSTSettings.setForTheFirstTime(true, LoginActivity.this);
        }

        m_bIsLoginAgain = getIntent().getBooleanExtra("isLoginAgain", false);

        if (m_bIsLoginAgain) {
            ActionBar actionBar = getActionBar();
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initComponent();

        initHandler();

        setUpListener();

    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        return super.onCreateOptionsMenu(menu);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (m_bIsLoginAgain) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    LoginActivity.this.finish();
                    return true;
                default:
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initComponent() {
        m_edtxUserName = (EditText) findViewById(R.id.login_activity_username_input);
        m_edtxPassword = (EditText) findViewById(R.id.login_activity_password_input);
        m_chbAutologin = (CheckBox) findViewById(R.id.login_activity_autologin_chb);
        if (CSTSettings.isAutoLogin(LoginActivity.this)) {
            m_chbAutologin.setChecked(true);
        }
        m_btnLogin = (Button) findViewById(R.id.login_activity_login_btn);
    }

    private void initHandler() {
        m_handlerLogin = new Handler() {

            /*
             * (non-Javadoc)
             *
             * @see android.os.Handler#handleMessage(android.os.Message)
             */
            @Override
            public void handleMessage(Message msg) {
                m_pgdWating.dismiss();

                switch (msg.what) {
                    case STATUS_REQUEST_SUCCESS:
                        if (m_chbAutologin.isChecked()) {
                            CSTSettings.setAutoLogin(true, LoginActivity.this);
                        } else {
                            CSTSettings.setAutoLogin(false, LoginActivity.this);
                        }
                        try {
                            DataManager.syncCurrentUser(
                                    new User(((JSONObject) msg.obj)
                                            .getJSONObject("body"))
                            );
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if (!m_bIsLoginAgain) {
                            LoginActivity.this.startActivity(new Intent(
                                    LoginActivity.this, NewMainActivity.class));
                        }
                        LoginActivity.this.finish();
                        break;
                    case STATUS_LOGIN_USERNAME_NOT_EXIST:
                        m_edtxUserName.setText("");
                        m_edtxUserName.requestFocus();
                        m_edtxPassword.setText("");
                        try {
                            CM.showAlert(LoginActivity.this,
                                    ((JSONObject) msg.obj).getString("message"));
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                    case STATUS_LOGIN_PASSWORD_ERROR:
                        m_edtxPassword.setText("");
                        m_edtxPassword.requestFocus();
                        try {
                            CM.showAlert(LoginActivity.this,
                                    ((JSONObject) msg.obj).getString("message"));
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    case STATUS_LOGIN_AUTH_EXPIRED:
                    case STATUS_LOGIN_AUTH_FAILED:
                        // TODO
                        break;
                    default:
                        dispose(msg);
                        break;
                }
            }

        };
    }

    private void setUpListener() {
        m_btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                m_strUserName = m_edtxUserName.getText().toString();
                m_strPassword = m_edtxPassword.getText().toString()
                        .toCharArray();
                if (m_strUserName.trim().length() == 0
                        || String.valueOf(m_strPassword).trim().length() == 0) {
                    AlertDialog.Builder builder = new Builder(
                            LoginActivity.this);
                    builder.setTitle(R.string.login_alertdialog_title);
                    builder.setMessage(R.string.login_alertdialog_message);
                    builder.setPositiveButton(R.string.OK,
                            new OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                    dialog.dismiss();
                                    // TODO
                                }
                            }
                    );
                    Dialog dialog = builder.create();
                    dialog.setCancelable(true);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.show();
                    return;
                }
                m_pgdWating = ProgressDialog.show(LoginActivity.this,
                        getString(R.string.loading),
                        getString(R.string.please_wait), true, false);
                if (NetworkConnection.isNetworkConnected(LoginActivity.this)) {
                    LoginApi.validate(m_strUserName,
                            String.valueOf(m_strPassword), 0.0, 0.0,
                            new LoginResponse(LoginActivity.this) {

                                @Override
                                public void onResponse(JSONObject result) {
                                    Message msg = m_handlerLogin
                                            .obtainMessage();

                                    try {
                                        msg.what = ((JSONObject) result)
                                                .getInt("status");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    msg.obj = (JSONObject) result;
                                    L.i("Msg = " + msg.what);

                                    m_handlerLogin.sendMessage(msg);

                                }

//                                @Override
//                                public void onHttpError(CSTResponse response) {
//                                    L.w("Login Requestlistener onHttpError!");
//                                    Message msg = m_handlerLogin
//                                            .obtainMessage();
//                                    HttpErrorWeeder.fckHttpError(response, msg);
//                                    m_handlerLogin.sendMessage(msg);
//                                }
//
//                                @Override
//                                public void onException(Exception e) {
//                                    L.e("Login Requestlistener onException!");
//                                    e.printStackTrace();
//                                    Message msg = m_handlerLogin
//                                            .obtainMessage();
//                                    ExceptionWeeder.fckException(e, msg);
//                                    m_handlerLogin.sendMessage(msg);
//                                }
                            }
                    );
                } else {
                    Message msg = m_handlerLogin.obtainMessage();
                    msg.what = NETWORK_NOT_CONNECTED;
                    m_handlerLogin.sendMessage(msg);
                }


            }
        });
    }
}