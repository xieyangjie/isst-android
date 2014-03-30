/**
 * 
 */
package cn.edu.zju.isst.ui.login;

import static cn.edu.zju.isst.constant.Constants.*;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import cn.edu.zju.isst.R;
import cn.edu.zju.isst.api.LoginApi;
import cn.edu.zju.isst.db.DataManager;
import cn.edu.zju.isst.db.User;
import cn.edu.zju.isst.net.NetworkConnection;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.ui.main.MainActivity;
import cn.edu.zju.isst.util.CM;
import cn.edu.zju.isst.util.L;

import de.keyboardsurfer.android.widget.crouton.*;

/**
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

		m_edtxUserName = (EditText) findViewById(R.id.username_input);
		m_edtxPassword = (EditText) findViewById(R.id.password_input);
		m_chbAutologin = (CheckBox) findViewById(R.id.autologin_chb);
		m_btnLogin = (Button) findViewById(R.id.login_btn);

		m_handlerLogin = new Handler() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case REQUEST_SUCCESS:
					LoginActivity.this.startActivity(new Intent(
							LoginActivity.this, MainActivity.class));
					LoginActivity.this.finish();
					break;
				case LOGIN_USERNAME_NOT_EXIST: {
					m_edtxUserName.setText("");
					m_edtxPassword.setText("");
					CM.showAlert(LoginActivity.this, (String) msg.obj);
					break;
				}
				case LOGIN_PASSWORD_ERROR: {
					m_edtxPassword.setText("");
					CM.showAlert(LoginActivity.this, (String) msg.obj);
					break;
				}
				case LOGIN_AUTH_EXPIRED:
					break;
				case LOGIN_AUTH_FAILED:
					break;
				default:
					break;
				}
			}

		};

		m_btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

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
										case REQUEST_SUCCESS:
											L.i("LOGIN SUCCESS!!!");
											DataManager.syncLogin(
													new User(
															jsonObject
																	.getJSONObject("body")),
													LoginActivity.this);
											break;
										case LOGIN_USERNAME_NOT_EXIST:
										case LOGIN_PASSWORD_ERROR:
										case LOGIN_AUTH_EXPIRED:
										case LOGIN_AUTH_FAILED:
											msg.obj = jsonObject.get("message");
											break;
										default:
											// TODO
											break;
										}
										msg.what = status;
										L.i("Msg = " + msg.what);
									} catch (JSONException e) {
										L.e("Login Requestlistener onComplete JSONException!");
										if (L.isDebuggable()) {
											e.printStackTrace();
										}
									}

									m_handlerLogin.sendMessage(msg);

								}

								@Override
								public void onError(Exception e) {
									L.e("Login Requestlistener onError Exception!");
									if (L.isDebuggable()) {
										e.printStackTrace();
									}
								}
							});
				}
			}
		});

	}

}
