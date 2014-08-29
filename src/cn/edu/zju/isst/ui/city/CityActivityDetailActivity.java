/**
 *
 */
package cn.edu.zju.isst.ui.city;

import com.android.volley.VolleyError;

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
import cn.edu.zju.isst.ui.main.BaseActivity;
import cn.edu.zju.isst.util.CroMan;
import cn.edu.zju.isst.util.Lgr;
import cn.edu.zju.isst.util.TSUtil;
import cn.edu.zju.isst.v2.activities.base.ActivityRequest;
import cn.edu.zju.isst.v2.activities.city.event.data.CSTCityEvent;
import cn.edu.zju.isst.v2.activities.city.net.CityActivityDetailResponse;
import cn.edu.zju.isst.v2.activities.city.net.CityActivityParticipateResponse;
import cn.edu.zju.isst.v2.data.CSTJsonParser;
import cn.edu.zju.isst.v2.net.CSTJsonRequest;
import cn.edu.zju.isst.v2.net.CSTNetworkEngine;
import cn.edu.zju.isst.v2.net.CSTRequest;
import cn.edu.zju.isst.v2.net.CSTStatusInfo;

import static cn.edu.zju.isst.constant.Constants.STATUS_NOT_LOGIN;
import static cn.edu.zju.isst.constant.Constants.STATUS_REQUEST_SUCCESS;

/**
 * @author theasir
 */
public class CityActivityDetailActivity extends BaseActivity {

    private final ViewHolder mViewHolder = new ViewHolder();

    private CSTCityEvent mCSTCityEvent;

    private Handler mHandler;

    private Handler mBtnHandler;

    private ProgressDialog mPgdWating;

    private CSTNetworkEngine mEngine = CSTNetworkEngine.getInstance();

    private static final String SUB_URL = "/api/cities";

    private int mId;

    private int mCityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_activity_detail_layout);

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
        mViewHolder.participateBtn = (Button) findViewById(
                R.id.city_activity_detail_participate_btn);
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
                mPgdWating.dismiss();
                switch (msg.what) {
                    case STATUS_REQUEST_SUCCESS:
                        CroMan.showConfirm(CityActivityDetailActivity.this, "提交成功！");
                        mCSTCityEvent.isParticipate = !mCSTCityEvent.isParticipate;
                        showDetail();
                        Lgr.i("提交成功！");
                        break;
                    case STATUS_NOT_LOGIN:
                        break;
                    default:
                        CroMan.showAlert(CityActivityDetailActivity.this, msg.obj.toString());
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
        StringBuilder sb = new StringBuilder();
        sb.append(SUB_URL).append("/" + mCityId).append("/activities")
                .append("/" + mId);
        CityActivityDetailResponse detailResponse = new CityActivityDetailResponse(this) {
            @Override
            public void onResponse(JSONObject response) {
                Message msg = mHandler.obtainMessage();
                Lgr.i(response.toString());
                try {
                    final int status = response.getInt("status");
                    switch (status) {
                        case STATUS_REQUEST_SUCCESS:
                            mCSTCityEvent = (CSTCityEvent) CSTJsonParser
                                    .parseJson(response, new CSTCityEvent());
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

            @Override
            public Object onErrorStatus(CSTStatusInfo statusInfo) {
                return super.onErrorStatus(statusInfo);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
            }
        };

        CSTJsonRequest detailRequest = new ActivityRequest(CSTRequest.Method.GET,
                sb.toString(), null,
                detailResponse);
        mEngine.requestJson(detailRequest);
    }

    private void performParticipateAction() {
        mPgdWating = ProgressDialog.show(CityActivityDetailActivity.this,
                getString(R.string.loading), getString(R.string.please_wait),
                true, false);
        CityActivityParticipateResponse participateResponse = new CityActivityParticipateResponse(
                this) {
            @Override
            public void onResponse(JSONObject response) {
                Message msg = mBtnHandler.obtainMessage();
                Lgr.i(response.toString());
                try {
                    final int status = response.getInt("status");

                    switch (status) {
                        case STATUS_REQUEST_SUCCESS:
                            break;
                        case STATUS_NOT_LOGIN:
                            break;
                        default:
                            break;
                    }
                    msg.what = status;
                    msg.obj = response.getString("message");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mBtnHandler.sendMessage(msg);
            }

            @Override
            public Object onErrorStatus(CSTStatusInfo statusInfo) {
                return super.onErrorStatus(statusInfo);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
            }
        };
        StringBuilder sb = new StringBuilder();

        sb.append(SUB_URL).append("/" + mCityId).append("/activities")
                .append("/" + mId);

        if (mCSTCityEvent.isParticipate) {
            sb.append("/unparticipate");
        } else {
            sb.append("/participate");
        }
        Lgr.i(sb.toString());

        CSTJsonRequest participateRequest = new CSTJsonRequest(CSTRequest.Method.POST,
                sb.toString(), null,
                participateResponse);
        mEngine.requestJson(participateRequest);

    }

    private void showDetail() {
        setTitle(mCSTCityEvent.title);
        mViewHolder.durationTxv.setText("活动时间:"
                + TSUtil.toHM(mCSTCityEvent.startTime) + "-"
                + TSUtil.toHM(mCSTCityEvent.expireTime));
        mViewHolder.locationTxv.setText("活动地点:" + mCSTCityEvent.location);
        mViewHolder.contentWebv.loadDataWithBaseURL(null, mCSTCityEvent.content,
                "text/html", "utf-8", null);
        mViewHolder.participateBtn.setText(mCSTCityEvent.isParticipate ? "取消报名"
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
