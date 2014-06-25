/**
 * 
 */
package cn.edu.zju.isst.ui.city;

import static cn.edu.zju.isst.constant.Constants.STATUS_NOT_LOGIN;
import static cn.edu.zju.isst.constant.Constants.STATUS_REQUEST_SUCCESS;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.zju.isst.R;
import cn.edu.zju.isst.api.CityActivityApi;
import cn.edu.zju.isst.db.CityActivity;
import cn.edu.zju.isst.net.CSTResponse;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.ui.life.CampusActivityDetailActivity;
import cn.edu.zju.isst.ui.main.BaseActivity;
import cn.edu.zju.isst.ui.usercenter.UserInfoEditActivity;
import cn.edu.zju.isst.util.CM;
import cn.edu.zju.isst.util.TimeString;

/**
 * @author theasir
 * 
 */
public class CityActivityDetailActivity extends BaseActivity {

    private int mId;
    private int mCityId;

    private CityActivity mCityActivity;
    private Handler mHandler;
    private Handler mBtnHandler;

    private final ViewHolder mViewHolder = new ViewHolder();
    private ProgressDialog m_pgdWating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.city_activity_detail_activity);

	setUpActionbar();

	mId = getIntent().getIntExtra("id", -1);
	mCityId = getIntent().getIntExtra("cityId", -1);

	initComponent();

	initHandler();

	setUpListener();

	requestData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case android.R.id.home:
	    CityActivityDetailActivity.this.finish();
	    return true;
	default:
	    return super.onOptionsItemSelected(item);
	}
    }

    private void setUpActionbar() {
	ActionBar actionBar = getActionBar();
	actionBar.setHomeButtonEnabled(true);
	actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initComponent() {
	mViewHolder.pictureImgv = (ImageView) findViewById(R.id.city_activity_detail_picture_imgv);
	mViewHolder.durationTxv = (TextView) findViewById(R.id.city_activity_detail_duration_txv);
	mViewHolder.locationTxv = (TextView) findViewById(R.id.city_activity_detail_location_txv);
	mViewHolder.contentWebv = (WebView) findViewById(R.id.city_activity_detail_content_webv);
	mViewHolder.participateBtn = (Button) findViewById(R.id.city_activity_detail_participate_btn);
    }

    private void initHandler() {
	mHandler = new Handler() {

	    @Override
	    public void handleMessage(Message msg) {
		switch (msg.what) {
		case STATUS_REQUEST_SUCCESS:
		    showDetail();
		    break;
		case STATUS_NOT_LOGIN:
		    break;
		default:
		    break;
		}

	    }
	};

	mBtnHandler = new Handler() {

	    @Override
	    public void handleMessage(Message msg) {
		m_pgdWating.dismiss();
		switch (msg.what) {
		case STATUS_REQUEST_SUCCESS:
		    CM.showConfirm(CityActivityDetailActivity.this, "提交成功！");
		    mCityActivity.isParticipate = !mCityActivity.isParticipate;
		    showDetail();
		    break;
		case STATUS_NOT_LOGIN:
		    break;
		default:
		    break;
		}
	    }
	};
    }

    private void setUpListener() {
	mViewHolder.participateBtn
		.setOnClickListener(new View.OnClickListener() {

		    @Override
		    public void onClick(View v) {
			performParticipateAction();
		    }
		});
    }

    private void requestData() {
	CityActivityApi.getCityActivityDetail(mCityId, mId,
		new RequestListener() {

		    @Override
		    public void onHttpError(CSTResponse response) {
			// TODO Auto-generated method stub

		    }

		    @Override
		    public void onException(Exception e) {
			// TODO Auto-generated method stub

		    }

		    @Override
		    public void onComplete(Object result) {
			Message msg = mHandler.obtainMessage();

			try {
			    JSONObject jsonObject = (JSONObject) result;
			    final int status = jsonObject.getInt("status");
			    switch (status) {
			    case STATUS_REQUEST_SUCCESS:
				mCityActivity = new CityActivity(jsonObject
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

			mHandler.sendMessage(msg);

		    }
		});
    }

    private void performParticipateAction() {
	m_pgdWating = ProgressDialog.show(CityActivityDetailActivity.this,
		getString(R.string.loading), getString(R.string.please_wait),
		true, false);
	if (mCityActivity.isParticipate) {
	    CityActivityApi.unparticipate(mCityId, mId, new RequestListener() {

		@Override
		public void onHttpError(CSTResponse response) {
		    // TODO Auto-generated method stub

		}

		@Override
		public void onException(Exception e) {
		    // TODO Auto-generated method stub

		}

		@Override
		public void onComplete(Object result) {
		    Message msg = mBtnHandler.obtainMessage();

		    try {
			JSONObject jsonObject = (JSONObject) result;
			final int status = jsonObject.getInt("status");
			switch (status) {
			case STATUS_REQUEST_SUCCESS:
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

		    mBtnHandler.sendMessage(msg);
		}
	    });
	} else {
	    CityActivityApi.participate(mCityId, mId, new RequestListener() {

		@Override
		public void onHttpError(CSTResponse response) {
		    // TODO Auto-generated method stub

		}

		@Override
		public void onException(Exception e) {
		    // TODO Auto-generated method stub

		}

		@Override
		public void onComplete(Object result) {
		    Message msg = mBtnHandler.obtainMessage();

		    try {
			JSONObject jsonObject = (JSONObject) result;
			final int status = jsonObject.getInt("status");
			switch (status) {
			case STATUS_REQUEST_SUCCESS:
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

		    mBtnHandler.sendMessage(msg);
		}

	    });
	}
    }

    private void showDetail() {
	setTitle(mCityActivity.title);
	mViewHolder.durationTxv.setText("活动时间:"
		+ TimeString.toHM(mCityActivity.startTime) + "-"
		+ TimeString.toHM(mCityActivity.expireTime));
	mViewHolder.locationTxv.setText("活动地点:" + mCityActivity.location);
	mViewHolder.contentWebv.loadDataWithBaseURL(null, mCityActivity.content,
		"text/html", "utf-8", null);
	mViewHolder.participateBtn.setText(mCityActivity.isParticipate ? "取消报名"
		: "报名参加");
    }

    private class ViewHolder {
	public ImageView pictureImgv;
	public TextView durationTxv;
	public TextView locationTxv;
	public WebView contentWebv;
	public Button participateBtn;
    }
}
