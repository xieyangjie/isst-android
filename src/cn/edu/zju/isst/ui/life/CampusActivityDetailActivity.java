/**
 * 
 */
package cn.edu.zju.isst.ui.life;

import static cn.edu.zju.isst.constant.Constants.STATUS_NOT_LOGIN;
import static cn.edu.zju.isst.constant.Constants.STATUS_REQUEST_SUCCESS;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.zju.isst.R;
import cn.edu.zju.isst.api.CampusActivityApi;
import cn.edu.zju.isst.db.CampusActivity;
import cn.edu.zju.isst.net.CSTResponse;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.ui.main.BaseActivity;

/**
 * @author theasir
 * 
 */
public class CampusActivityDetailActivity extends BaseActivity {

    private int m_nId;

    private CampusActivity m_campusActivityCurrent;
    private Handler m_handlerCampusActivityDetail;

    private ImageView m_imgvPicture;
    private TextView m_txvDuration;
    private TextView m_txvLocation;
    private WebView m_webvContent;

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.campus_activity_detail_activity);
	initComponent();

	ActionBar actionBar = getActionBar();
	actionBar.setHomeButtonEnabled(true);
	actionBar.setDisplayHomeAsUpEnabled(true);

	m_nId = getIntent().getIntExtra("id", -1);

	m_handlerCampusActivityDetail = new Handler() {

	    /*
	     * (non-Javadoc)
	     * 
	     * @see android.os.Handler#handleMessage(android.os.Message)
	     */
	    @Override
	    public void handleMessage(Message msg) {
		switch (msg.what) {
		case STATUS_REQUEST_SUCCESS:
		    showCampusActivityDetatil();
		    break;
		case STATUS_NOT_LOGIN:
		    break;
		default:
		    break;
		}
	    }

	};

	CampusActivityApi.getCampusActivityDetail(m_nId, new RequestListener() {

	    @Override
	    public void onComplete(Object result) {
		Message msg = m_handlerCampusActivityDetail.obtainMessage();

		try {
		    JSONObject jsonObject = (JSONObject) result;
		    final int status = jsonObject.getInt("status");
		    switch (status) {
		    case STATUS_REQUEST_SUCCESS:
			m_campusActivityCurrent = new CampusActivity(jsonObject
				.getJSONObject("body"));
			break;
		    case STATUS_NOT_LOGIN:
			break;
		    default:
			break;
		    }
		    msg.what = status;
		} catch (JSONException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		m_handlerCampusActivityDetail.sendMessage(msg);

	    }

	    @Override
	    public void onHttpError(CSTResponse response) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void onException(Exception e) {
		// TODO Auto-generated method stub

	    }
	});
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
	    CampusActivityDetailActivity.this.finish();
	    return true;
	default:
	    return super.onOptionsItemSelected(item);
	}
    }

    private void initComponent() {
	m_imgvPicture = (ImageView) findViewById(R.id.campus_activity_detail_activity_picture_imgv);
	m_txvDuration = (TextView) findViewById(R.id.campus_activity_detail_activity_duration_txv);
	m_txvLocation = (TextView) findViewById(R.id.campus_activity_detail_activity_loaction_txv);
	m_webvContent = (WebView) findViewById(R.id.campus_activity_detail_activity_content_webv);
    }

    private void showCampusActivityDetatil() {
	setTitle(m_campusActivityCurrent.getTitle());
	m_txvDuration.setText("活动时间:9:00-12:00");
	m_txvLocation.setText("教学楼N312");
	m_webvContent.loadDataWithBaseURL(null,
		m_campusActivityCurrent.getContent(), "text/html", "utf-8",
		null);
    }

}
