/**
 * 
 */
package cn.edu.zju.isst.ui.main;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.constant.Nav;
import cn.edu.zju.isst.dummy.DummyFragment;
import cn.edu.zju.isst.util.L;
import static cn.edu.zju.isst.constant.Nav.*;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


/**
 * @author theasir
 *
 */
public class MainActivity extends ActionBarActivity implements
		SlidingMenuFragment.OnGroupMenuItemClickListener {

	private SlidingMenu m_smMainMenu;
	
	private Fragment m_fragCurrentContent;
	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.content_frame);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_frame, new SlidingMenuFragment())
                    .commit();
        }

        setUpSlidingMenu();
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            m_smMainMenu.toggle();
            return true;
    }
    return super.onOptionsItemSelected(item);
	}

	@Override
	public void onGroupMenuItemClick(Nav item) {
		switch (item) {
			case NEWS:
				switchContent(DummyFragment.newInstance(NEWS.getName()));
				break;
			case WIKI:
				switchContent(DummyFragment.newInstance(WIKI.getName()));
				break;
			case SCAC:
				switchContent(DummyFragment.newInstance(SCAC.getName()));
				break;
			case SERV:
				switchContent(DummyFragment.newInstance(SERV.getName()));
				break;
			case STUD:
				switchContent(DummyFragment.newInstance(STUD.getName()));
				break;
			case INTE:
				switchContent(DummyFragment.newInstance(INTE.getName()));
				break;
			case JOBS:
				switchContent(DummyFragment.newInstance(JOBS.getName()));
				break;
			case REFE:
				switchContent(DummyFragment.newInstance(REFE.getName()));
				break;
			case EXPE:
				switchContent(DummyFragment.newInstance(EXPE.getName()));
				break;
			case CIMA:
				switchContent(DummyFragment.newInstance(CIMA.getName()));
				break;
			case CIAC:
				switchContent(DummyFragment.newInstance(CIAC.getName()));
				break;
			case CIAL:
				switchContent(DummyFragment.newInstance(CIAL.getName()));
				break;
			case CONT:
				switchContent(DummyFragment.newInstance(CONT.getName()));
				break;
			case USCE:
				switchContent(DummyFragment.newInstance(USCE.getName()));
				break;
			default:
				break;
		}
		
	}
	
	private void setUpSlidingMenu() {
		m_smMainMenu = new SlidingMenu(this);
		m_smMainMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		m_smMainMenu.setShadowWidthRes(R.dimen.sm_shadow_width);
		m_smMainMenu.setShadowDrawable(R.drawable.sm_shadow);
		m_smMainMenu.setBehindOffsetRes(R.dimen.sm_offset);
		m_smMainMenu.setFadeDegree(0.35f);
		m_smMainMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		m_smMainMenu.setMenu(R.layout.sm_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.sm_frame, SlidingMenuFragment.getInstance())
				.commit();
	}

	private void switchContent(Fragment fragment) {
		m_fragCurrentContent = fragment;

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, m_fragCurrentContent).commit();
		m_smMainMenu.showContent();
	}

}
