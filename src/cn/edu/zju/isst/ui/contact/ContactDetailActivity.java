package cn.edu.zju.isst.ui.contact;

import static cn.edu.zju.isst.constant.Constants.STATUS_NOT_LOGIN;
import static cn.edu.zju.isst.constant.Constants.STATUS_REQUEST_SUCCESS;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.edu.zju.isst.R;
import cn.edu.zju.isst.api.AlumniApi;
import cn.edu.zju.isst.db.City;
import cn.edu.zju.isst.db.DataManager;
import cn.edu.zju.isst.db.Major;
import cn.edu.zju.isst.db.User;
import cn.edu.zju.isst.net.CSTResponse;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.ui.main.BaseActivity;
import cn.edu.zju.isst.util.J;
import cn.edu.zju.isst.util.L;

public class ContactDetailActivity extends BaseActivity {

	//用户
	private int m_Id;
	private User m_user;
	
	private final List<City> m_listCity = new ArrayList<City>();
	private final List<Major> m_listMajor = new ArrayList<Major>();

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
	private ImageButton m_ibtnMobileCall;
	private ImageButton m_ibtnMessage;
	
	public ContactDetailActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_detail_activity);
		
		//初始化两个列表
		getCityList();
		getMajorList();
		
		m_handlerAlumniDetail = new getUserDetailHandler();
		
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		m_Id = getIntent().getIntExtra("id", -1);
		//请求数据
		AlumniApi.getUser(m_Id, new AlumniDetailRequestListener());
		
		//控件
		m_tvName = (TextView)findViewById(R.id.contact_detail_activity_name_txv);
		m_tvGender = (TextView)findViewById(R.id.contact_detail_activity_gender_txv);
		m_tvGrade = (TextView)findViewById(R.id.contact_detail_activity_grade_txv);
		m_tvMajor = (TextView)findViewById(R.id.contact_detail_activity_major_txv);
		m_tvMobile = (TextView)findViewById(R.id.contact_detail_activity_mobile_txv);
		m_tvCity = (TextView)findViewById(R.id.contact_detail_activity_city_txv);
		m_tvCompany = (TextView)findViewById(R.id.contact_detail_activity_company_txv);
		m_tvLocation = (TextView)findViewById(R.id.contact_detail_activity_location_txv);
		m_tvEmail = (TextView)findViewById(R.id.contact_detail_activity_email_txv);
		m_ibtnMobileCall = (ImageButton)findViewById(R.id.contact_detail_activity_mobile_ibtn);
		m_ibtnMessage = (ImageButton)findViewById(R.id.contact_detail_activity_message_ibtn);

		m_ibtnMobileCall.setOnClickListener(new onMobileCallClickListner());
		m_ibtnMessage.setOnClickListener(new onMessageClickListner());
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			ContactDetailActivity.this.finish();
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
	
	/**
	 * 初始化城市列表
	 */
	private void getCityList() {
		List<City> dbList = DataManager.getCityList();
		if (!J.isNullOrEmpty(dbList)) {
			for (City city : dbList) {
				m_listCity.add(city);
			}
		}
		L.i(" yyy getCityList");
	}

	/**
	 * 初始化专业列表
	 */
	private void getMajorList() {
		List<Major> dbList = DataManager.getMajorList();
		if (!J.isNullOrEmpty(dbList)) {
			for (Major major : dbList) {
				m_listMajor.add(major);
			}
		}
		L.i(" yyy getMajorList");
	}
	
	private String getCityName(int cityID) {
		String res = null;
		for (int i=0;i<m_listCity.size();i++) {
			if (m_listCity.get(i).getId() == cityID) {
				res = m_listCity.get(i).getName();
				break;
			}
		}
		return res;
	}
	
	/**
	 * 按majorID获取majorName
	 * @param majorID
	 * @return majorName
	 */
	private String getMajorName(int majorID) {
		String res = null;
		for (int i=0;i<m_listMajor.size();i++) {
			if (m_listMajor.get(i).getId() == majorID) {
				res = m_listMajor.get(i).getName();
				break;
			}
		}
		return res;
	}
	
	
	private class getUserDetailHandler extends Handler
	{
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case STATUS_REQUEST_SUCCESS:
				//姓名
				m_tvName.setText(m_user.getName());
				//性别
				if (m_user.getGender()>0) {
					m_tvGender.setText(m_user.getGender()==1?"男":"女");
				}
				//年级
				m_tvGrade.setText("" + m_user.getGrade() + "级");
				//专业
				int majorID = m_user.getMajotId();
				m_tvMajor.setText(getMajorName(majorID));
				//电话
				m_tvMobile.setText(m_user.getPhone());
				//Email
				m_tvEmail.setText(m_user.getEmail());
				//城市
				int cityID = m_user.getMajotId();
				m_tvCity.setText(getCityName(cityID));
				//公司
				m_tvCompany.setText(m_user.getCompany());
				break;
			case STATUS_NOT_LOGIN:
				break;
			default:
				break;
			}
		}

	}

	private class onMobileCallClickListner implements OnClickListener {

		@Override
		public void onClick(View v) {
			String number = m_tvMobile.getText().toString();
			Intent intent = new Intent();
	        intent.setAction(Intent.ACTION_CALL);
	        intent.setData(Uri.parse("tel:"+number));
	        startActivity(intent);
		}
		
	}
	
	private class onMessageClickListner implements OnClickListener {

		@Override
		public void onClick(View v) {
			String number = m_tvMobile.getText().toString();
			Intent intent = new Intent();
	        intent.setAction("android.intent.action.SENDTO");
	        intent.setData(Uri.parse("smsto:"+number));
	        startActivity(intent);
		}
		
	}
}
