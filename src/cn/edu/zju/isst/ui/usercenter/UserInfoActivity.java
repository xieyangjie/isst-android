/**
 * 
 */
package cn.edu.zju.isst.ui.usercenter;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.zju.isst.R;
import cn.edu.zju.isst.db.DataManager;
import cn.edu.zju.isst.db.User;
import cn.edu.zju.isst.ui.main.BaseActivity;

/**
 * @author theasir
 * 
 */
public class UserInfoActivity extends BaseActivity {

	public static final int REQUEST_CODE_EDIT = 0x01;
	public static final int RESULT_CODE_DONE = 0x10;
	
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

		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		initComponent();

		initUser();

		showUserInfo(m_userCurrent);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE_EDIT:
			switch (resultCode) {
			case RESULT_CODE_DONE:
				updatedInfo();
				break;

			default:
				break;
			}
			break;

		default:
			break;
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.user_info_activity_ab_menu, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			UserInfoActivity.this.finish();
			return true;
		case R.id.action_edit:
			Intent intent = new Intent(UserInfoActivity.this, UserInfoEditActivity.class);
			intent.putExtra("currentUser", m_userCurrent);
			UserInfoActivity.this.startActivityForResult(intent, REQUEST_CODE_EDIT);
			return true;
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
		m_userCurrent = DataManager.getCurrentUser();
	}

	private void showUserInfo(User currentUser) {
		m_viewHolder.signatureTxv.setText(currentUser.getSignature());
		m_viewHolder.nameTxv.setText(currentUser.getName());
		m_viewHolder.usernameTxv.setText(currentUser.getUsername());
		m_viewHolder.gradeTxv.setText("2013");
		m_viewHolder.classTxv.setText("1309");
		m_viewHolder.majorTxv.setText("移动互联网与游戏开发");
		m_viewHolder.phoneTxv.setText(currentUser.getPhone());
		m_viewHolder.emailTxv.setText(currentUser.getEmail());
		m_viewHolder.qqTxv.setText(currentUser.getQq());
		m_viewHolder.companyTxv.setText(currentUser.getCompany());
		m_viewHolder.positionTxv.setText(currentUser.getPosition());
	}

	private void updatedInfo() {
		// TODO Auto-generated method stub
		
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
