/**
 * 
 */
package cn.edu.zju.isst.ui.main;

import static cn.edu.zju.isst.constant.Nav.CIAC;
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
import cn.edu.zuj.isst.ui.city.CastellanFragment;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * @author theasir
 * 
 */
public class NewMainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main_drawer_activity);

	mCurrentFragment = null;

	if (savedInstanceState == null) {
	    mCurrentFragment = NewsListFragment.getInstance();
	    getFragmentManager().beginTransaction()
		    .add(R.id.content_frame, mCurrentFragment).commit();
	}

	setUpActionbar();

	setUpDrawer();

	requestGlobalData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.main_activity_ab_menu, menu);
	return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case android.R.id.home:
	    mDrawerLayout.openDrawer(mDrawerList);
	    return true;
	case R.id.action_logout:
	    logout();
	    return true;
	default:
	    return super.onOptionsItemSelected(item);
	}
    }

    private void setUpActionbar() {
	ActionBar actionBar = getActionBar();
	actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setUpDrawer() {
	mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	mDrawerList = (ListView) findViewById(R.id.left_drawer);

	mDrawerList.setAdapter(new MainDrawerAdapter(this));
	mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private void switchContent(Fragment fragment) {
	if (fragment != mCurrentFragment) {
	    mCurrentFragment = fragment;

	    getFragmentManager().beginTransaction()
		    .replace(R.id.content_frame, mCurrentFragment).commit();
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
	CSTSettings.setAutoLogin(false, NewMainActivity.this);
	NewMainActivity.this.startActivity(new Intent(NewMainActivity.this,
			LoginActivity.class));
	NewMainActivity.this.finish();
}

    private class DrawerItemClickListener implements
	    ListView.OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
		long id) {
	    Nav nav = Nav.values()[position];
	    NewMainActivity.this.setTitle(nav.getName());
	    mDrawerList.setItemChecked(position, true);
	    
	    switch (nav) {
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
		switchContent(ContactListFragment
			.getInstance(FilterType.MY_CITY));
		break;
	    case CONT:
		switchContent(ContactListFragment
			.getInstance(FilterType.MY_CLASS));
		break;
	    case USCE:
		switchContent(UserCenterFragment.getInstance());
		break;
	    default:
		break;
	    }
	    
	    mDrawerLayout.closeDrawer(mDrawerList);
	}

    }

}
