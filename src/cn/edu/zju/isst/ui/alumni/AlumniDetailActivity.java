package cn.edu.zju.isst.ui.alumni;

import static cn.edu.zju.isst.constant.Constants.STATUS_NOT_LOGIN;
import static cn.edu.zju.isst.constant.Constants.STATUS_REQUEST_SUCCESS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.TextView;
import cn.edu.zju.isst.R;
import cn.edu.zju.isst.api.AlumniApi;
import cn.edu.zju.isst.db.User;
import cn.edu.zju.isst.net.CSTResponse;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.ui.life.ArchiveDetailActivity;
import cn.edu.zju.isst.ui.main.BaseActivity;
import cn.edu.zju.isst.util.L;

public class AlumniDetailActivity extends BaseActivity {

	//用户
	private int m_Id;
	private User m_user;

	private Handler m_handlerAlumniDetail;	
	
	//控件
	private TextView m_tvName;
	private TextView m_tvGender;
	private TextView m_tvGrade;
	private TextView m_tvMajor;
	private TextView m_tvMobile;
	private TextView m_tvEmail;
	private TextView m_tvCity;
	private TextView m_tvCompany;
	private TextView m_tvLocation;	
	
	public AlumniDetailActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alumni_detail_activity);
		
		m_handlerAlumniDetail = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case STATUS_REQUEST_SUCCESS:
					//需要判断非空
					m_tvName.setText(m_user.getName());
//					m_tvGender.setText(m_user.getGender());
//					m_tvGrade.setText(m_user.getGrade());
//					m_tvMajor.setText(m_user.getMajotId());
					m_tvMobile.setText(m_user.getPhone());
//					m_tvEmail.setText(m_user.getEmail());
					break;
				case STATUS_NOT_LOGIN:
					break;
				default:
					break;
				}
			}
		};

		ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		m_Id = getIntent().getIntExtra("id", -1);
		//请求数据
		AlumniApi.getUser(m_Id, new AlumniDetailRequestListener());
		
		//控件
		m_tvName = (TextView)findViewById(R.id.alumni_detail_activity_name_txv);
		m_tvGender = (TextView)findViewById(R.id.alumni_detail_activity_gender_txv);
		m_tvGrade = (TextView)findViewById(R.id.alumni_detail_activity_grade_txv);
		m_tvMajor = (TextView)findViewById(R.id.alumni_detail_activity_major_txv);
		m_tvMobile = (TextView)findViewById(R.id.alumni_detail_activity_mobile_txv);
		m_tvCity = (TextView)findViewById(R.id.alumni_detail_activity_city_txv);
		m_tvCompany = (TextView)findViewById(R.id.alumni_detail_activity_company_txv);
		m_tvLocation = (TextView)findViewById(R.id.alumni_detail_activity_location_txv);

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			AlumniDetailActivity.this.finish();
			return true;
			}
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private class AlumniDetailRequestListener implements RequestListener {

		@Override
		public void onComplete(Object result) {
			// TODO Auto-generated method stub
			L.i("yyy --- onComplete");
			Message msg = m_handlerAlumniDetail.obtainMessage();
			try {
				msg.what = ((JSONObject) result).getInt("status");
				JSONObject jsonObject = (JSONObject) result;
				m_user =new User(jsonObject.getJSONObject("body"));
				L.i("yyy --- onComplete  get user " + m_user.getName());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			m_handlerAlumniDetail.sendMessage(msg);
		}

		@Override
		public void onHttpError(CSTResponse response) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onException(Exception e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
