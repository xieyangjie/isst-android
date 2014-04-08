/**
 * 
 */
package cn.edu.zju.isst.ui.usercenter;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.zju.isst.R;
import cn.edu.zju.isst.db.DataManager;
import cn.edu.zju.isst.db.User;
import cn.edu.zju.isst.ui.life.RestaurantDetailActivity;
import cn.edu.zju.isst.ui.main.BaseActivity;

/**
 * @author theasir
 * 
 */
public class UserInfoActivity extends BaseActivity {

	private User m_userCurrent;
	private final ViewHolder m_viewHolder = new ViewHolder();

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.zju.isst.ui.main.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info_activity);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		initComponent();

		initUser();

		showUserInfo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			UserInfoActivity.this.finish();
			return true;
		}

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void initComponent() {
		m_viewHolder.avatarImgv = (ImageView) findViewById(R.id.user_info_activity_user_avatar_imgv);
		m_viewHolder.signatureTxv = (TextView) findViewById(R.id.user_info_activity_signature_txv);
		m_viewHolder.nameTxv = (TextView) findViewById(R.id.user_info_activity_name_txv);
		m_viewHolder.usernameTxv = (TextView) findViewById(R.id.user_info_activity_username_txv);
		m_viewHolder.gradeTxv = (TextView) findViewById(R.id.user_info_activity_grade_txv);
		m_viewHolder.classTxv = (TextView) findViewById(R.id.user_info_activity_class_txv);
		m_viewHolder.majorTxv = (TextView) findViewById(R.id.user_info_activity_major_txv);
		m_viewHolder.phoneTxv = (TextView) findViewById(R.id.user_info_activity_phone_txv);
		m_viewHolder.emailTxv = (TextView) findViewById(R.id.user_info_activity_email_txv);
		m_viewHolder.qqTxv = (TextView) findViewById(R.id.user_info_activity_qq_txv);
		m_viewHolder.companyTxv = (TextView) findViewById(R.id.user_info_activity_company_txv);
		m_viewHolder.positionTxv = (TextView) findViewById(R.id.user_info_activity_position_txv);
	}

	private void initUser() {
		m_userCurrent = DataManager.getCurrentUser(UserInfoActivity.this);
	}

	private void showUserInfo() {
		m_viewHolder.signatureTxv.setText(m_userCurrent.getSignature());
		m_viewHolder.nameTxv.setText(m_userCurrent.getName());
		m_viewHolder.usernameTxv.setText(m_userCurrent.getUsername());
		m_viewHolder.gradeTxv.setText("2013");
		m_viewHolder.classTxv.setText("1309");
		m_viewHolder.majorTxv.setText("移动互联网与游戏开发");
		m_viewHolder.phoneTxv.setText(m_userCurrent.getPhone());
		m_viewHolder.emailTxv.setText(m_userCurrent.getEmail());
		m_viewHolder.qqTxv.setText(m_userCurrent.getQq());
		m_viewHolder.companyTxv.setText(m_userCurrent.getCompany());
		m_viewHolder.positionTxv.setText(m_userCurrent.getPosition());
	}

	private class ViewHolder {
		public ImageView avatarImgv;
		public TextView signatureTxv;
		public TextView nameTxv;
		public TextView usernameTxv;
		public TextView gradeTxv;
		public TextView classTxv;
		public TextView majorTxv;
		public TextView phoneTxv;
		public TextView emailTxv;
		public TextView qqTxv;
		public TextView companyTxv;
		public TextView positionTxv;
	}
}
