/**
 * 
 */
package cn.edu.zju.isst.ui.main;

import static cn.edu.zju.isst.constant.Nav.*;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import cn.edu.zju.isst.R;
import cn.edu.zju.isst.api.LogoutApi;
import cn.edu.zju.isst.constant.Nav;
import cn.edu.zju.isst.db.DataManager;
import cn.edu.zju.isst.dummy.DummyFragment;
import cn.edu.zju.isst.net.CSTResponse;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.settings.CSTSettings;
import cn.edu.zju.isst.ui.contact.ContactListFragment;
import cn.edu.zju.isst.ui.contact.ContactListFragment.FilterType;
import cn.edu.zju.isst.ui.job.EmploymentListFragment;
import cn.edu.zju.isst.ui.job.ExperienceListFragment;
import cn.edu.zju.isst.ui.job.InternshipListFragment;
import cn.edu.zju.isst.ui.job.RecommedListFragment;
import cn.edu.zju.isst.ui.life.CampusActivityListFragment;
import cn.edu.zju.isst.ui.life.NewsListFragment;
import cn.edu.zju.isst.ui.life.RestaurantListFragment;
import cn.edu.zju.isst.ui.life.StudyListFragment;
import cn.edu.zju.isst.ui.life.WikGridFragment;
import cn.edu.zju.isst.ui.login.LoginActivity;
import cn.edu.zju.isst.ui.usercenter.UserCenterFragment;
import cn.edu.zju.isst.util.L;
import cn.edu.zju.isst.util.T;
import cn.edu.zuj.isst.ui.city.CastellanFragment;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * 主页面
 * 
 * @author theasir
 * 
 *         TODO WIP
 */
public class MainActivity extends BaseActivity implements
		SlidingMenuFragment.OnGroupMenuItemClickListener {

	private long m_lExitTime = -1;

	/**
	 * 侧拉菜单
	 */
	private SlidingMenu m_smMainMenu;

	/**
	 * 当前显示fragment
	 */
	private Fragment m_fragCurrentContent;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_frame);

		m_fragCurrentContent = null;

		if (savedInstanceState == null) {
			m_fragCurrentContent = NewsListFragment.getInstance();
			getFragmentManager().beginTransaction()
					.add(R.id.content_frame, m_fragCurrentContent).commit();
		}

		setUpSlidingMenu();

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		requestGlobalData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_activity_ab_menu, menu);
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
			m_smMainMenu.toggle();
			return true;
		case R.id.action_logout:
			logout();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyUp(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_UP) {
			if ((System.currentTimeMillis() - m_lExitTime) > 2000) {
				T.showShort(MainActivity.this, R.string.back_twice_to_exit);
				m_smMainMenu.toggle();
				m_lExitTime = System.currentTimeMillis();
			} else {
				// finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.edu.zju.isst.ui.main.SlidingMenuFragment.OnGroupMenuItemClickListener
	 * #onGroupMenuItemClick(cn.edu.zju.isst.constant.Nav)
	 */
	@Override
	public void onGroupMenuItemClick(Nav item) {
		switch (item) {
		case NEWS:
			switchContent(NewsListFragment.getInstance());
			break;
		case WIKI:
			switchContent(WikGridFragment.getInstance());
			break;
		case SCAC:
			switchContent(CampusActivityListFragment.getInstance());
			break;
		case SERV:
			switchContent(RestaurantListFragment.getInstance());
			break;
		case STUD:
			switchContent(StudyListFragment.getInstance());
			break;
		case INTE:
			switchContent(InternshipListFragment.getInstance());
			break;
		case JOBS:
			switchContent(EmploymentListFragment.getInstance());
			break;
		case REFE:
			switchContent(RecommedListFragment.getInstance());
			break;
		case EXPE:
			switchContent(ExperienceListFragment.getInstance());
			break;
		case CIMA:
			switchContent(CastellanFragment.GetInstance());
			break;
		case CIAC:
			switchContent(DummyFragment.newInstance(CIAC.getName()));
			break;
		case CIAL:
			switchContent(ContactListFragment.getInstance(FilterType.MY_CITY));
			break;
		case CONT:
			switchContent(ContactListFragment.getInstance(FilterType.MY_CLASS));
			break;
		case USCE:
			switchContent(UserCenterFragment.getInstance());
			break;
		default:
			break;
		}

	}

	public void logout() {
		LogoutApi.logout(new RequestListener() {
	
			@Override
			public void onComplete(Object result) {
				// TODO Auto-generated method stub
	
			}
	
			@Override
			public void onHttpError(CSTResponse response) {
				L.i("logout onHttpError: " + response.getStatus());
	
			}
	
			@Override
			public void onException(Exception e) {
				// TODO Auto-generated method stub
	
			}
		});
		DataManager.deleteCurrentUser();
		CSTSettings.setAutoLogin(false, MainActivity.this);
		MainActivity.this.startActivity(new Intent(MainActivity.this,
				LoginActivity.class));
		MainActivity.this.finish();
	}

	/**
	 * 设置侧拉菜单
	 */
	private void setUpSlidingMenu() {
		m_smMainMenu = new SlidingMenu(this);
		m_smMainMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		m_smMainMenu.setShadowWidthRes(R.dimen.sm_shadow_width);
		m_smMainMenu.setShadowDrawable(R.drawable.sm_shadow);
		m_smMainMenu.setBehindOffsetRes(R.dimen.sm_offset);
		m_smMainMenu.setFadeDegree(0.35f);
		m_smMainMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		m_smMainMenu.setMenu(R.layout.sm_frame);
		getFragmentManager().beginTransaction()
				.replace(R.id.sm_frame, SlidingMenuFragment.getInstance())
				.commit();
	}

	/**
	 * 切换fragment
	 * 
	 * @param fragment
	 */
	private void switchContent(Fragment fragment) {
		if (fragment != m_fragCurrentContent) {
			m_fragCurrentContent = fragment;

			getFragmentManager().beginTransaction()
					.replace(R.id.content_frame, m_fragCurrentContent).commit();
		}
		m_smMainMenu.showContent();
	}

}
