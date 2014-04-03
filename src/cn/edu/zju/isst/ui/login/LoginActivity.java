/**
 * 
 */
package cn.edu.zju.isst.ui.login;

import static cn.edu.zju.isst.constant.Constants.*;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.webkit.CookieSyncManager;
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
import cn.edu.zju.isst.ui.main.MainActivity;
import cn.edu.zju.isst.util.CM;
import cn.edu.zju.isst.util.Judgement;
import cn.edu.zju.isst.util.L;

/**
 * 登录页面
 * 
 * @author theasir
 * 
 */
public class LoginActivity extends ActionBarActivity {

	private String m_nUserId;
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
		CookieSyncManager.createInstance(LoginActivity.this);

		m_edtxUserName = (EditText) findViewById(R.id.login_activity_username_input);
		m_edtxPassword = (EditText) findViewById(R.id.login_activity_password_input);
		m_chbAutologin = (CheckBox) findViewById(R.id.login_activity_autologin_chb);
		m_btnLogin = (Button) findViewById(R.id.login_activity_login_btn);

		// 为什么要这么做？参看CSTSettings类以及SharedPreferences#getBoolean(String key,
		// boolean defValue)
		if (CSTSettings.isForTheFirstTime(LoginActivity.this)) {
			CSTSettings.setForTheFirstTime(true, LoginActivity.this);
		}

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
					}
					LoginActivity.this.startActivity(new Intent(
							LoginActivity.this, MainActivity.class));
					LoginActivity.this.finish();
					break;
				case STATUS_LOGIN_USERNAME_NOT_EXIST:
					m_edtxUserName.setText("");
					m_edtxPassword.setText("");
					CM.showAlert(LoginActivity.this, (String) msg.obj);
					break;
				case STATUS_LOGIN_PASSWORD_ERROR:
				case STATUS_LOGIN_AUTH_EXPIRED:
				case STATUS_LOGIN_AUTH_FAILED:
					m_edtxPassword.setText("");
					CM.showAlert(LoginActivity.this, (String) msg.obj);
				
					break;
				case NETWORK_NOT_CONNECTED:
					CM.showAlert(LoginActivity.this,
							R.string.network_not_connected);
				default:
					break;
				}
			}

		};

		m_btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				m_pgdWating = ProgressDialog.show(LoginActivity.this,
						getString(R.string.loading),
						getString(R.string.please_wait), true, false);

				m_nUserId = m_edtxUserName.getText().toString();
				m_strPassword = m_edtxPassword.getText().toString()
						.toCharArray();
				if (m_nUserId.trim().length() == 0
						|| String.valueOf(m_strPassword).trim().length() == 0) {
					// TODO
					return;
				}
				if (NetworkConnection.isNetworkConnected(LoginActivity.this)) {
					LoginApi.validate(m_nUserId, String.valueOf(m_strPassword),
							0.0, 0.0, new RequestListener() {

								@Override
								public void onComplete(Object result) {
									Message msg = m_handlerLogin
											.obtainMessage();

									try {
										JSONObject jsonObject = (JSONObject) result;
										final int status = jsonObject
												.getInt("status");
										switch (status) {
										case STATUS_REQUEST_SUCCESS:
											L.i("LOGIN SUCCESS!!!");
											if (Judgement.isValidJsonValue(
													"body", jsonObject)) {
												break;
											}
											DataManager.syncLogin(
													new User(
															jsonObject
																	.getJSONObject("body")),
													LoginActivity.this);
											break;
										case STATUS_LOGIN_USERNAME_NOT_EXIST:
										case STATUS_LOGIN_PASSWORD_ERROR:
										case STATUS_LOGIN_AUTH_EXPIRED:
										case STATUS_LOGIN_AUTH_FAILED:
											if (Judgement.isValidJsonValue(
													"message", jsonObject)) {
												msg.obj = "test";
												break;
											}
											msg.obj = jsonObject.get("message");
											break;
										default:
											// TODO
											break;
										}
										msg.what = status;
										L.i("Msg = " + msg.what);
									} catch (Exception e) {
										L.e("Login Requestlistener onComplete Exception!");
										onException(e);
									}

									m_handlerLogin.sendMessage(msg);

								}

								@Override
								public void onHttpError(CSTResponse response) {
									L.w("Login Requestlistener onHttpError!");
									Message msg = m_handlerLogin
											.obtainMessage();
									HttpErrorWeeder.fckHttpError(response, msg);
									m_handlerLogin.sendMessage(msg);
								}

								@Override
								public void onException(Exception e) {
									L.e("Login Requestlistener onException!");
									Message msg = m_handlerLogin
											.obtainMessage();
									ExceptionWeeder.fckException(e, msg);
									m_handlerLogin.sendMessage(msg);
								}

							});
				} else {
					Message msg = m_handlerLogin.obtainMessage();
					msg.what = NETWORK_NOT_CONNECTED;
					m_handlerLogin.sendMessage(msg);
				}
			}
		});

	}

}
