/**
 *
 */
package cn.edu.zju.isst.v2.archive.gui;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.TextView;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.db.Archive;
import cn.edu.zju.isst.net.CSTResponse;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.ui.main.BaseActivity;
import cn.edu.zju.isst.util.Judge;
import cn.edu.zju.isst.util.Lgr;
import cn.edu.zju.isst.util.TSUtil;
import cn.edu.zju.isst.v2.archive.net.ArchiveApi;

import static cn.edu.zju.isst.constant.Constants.STATUS_NOT_LOGIN;
import static cn.edu.zju.isst.constant.Constants.STATUS_REQUEST_SUCCESS;

/**
 * 归档详情页
 *
 * @author theasir
 *         <p/>
 *         TODO WIP
 */
public class ArchiveDetailActivity extends BaseActivity {

    /**
     * 归档id
     */
    private int m_nId;

    private Archive m_archiveCurrent;

    private Handler m_handlerArchiveDetail;

    private TextView m_txvTitle;

    private TextView m_txvDate;

    private TextView m_txvPublisher;

    private WebView m_webvContent;

    // Activity需要工厂方法吗？
    // public ArchiveDetailActivity(){
    // }
    //
    // public static ArchiveDetailActivity newInstance(){
    // return new ArchiveDetailActivity();
    // }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.archive_detail_activity);
        initComponent();

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // 注意默认值-1，当Intent中没有id时是无效的，故启动这个ArchiveDetailActivity的Activity必须在Intent中放置"id"参数
        m_nId = getIntent().getIntExtra("id", -1);

        m_handlerArchiveDetail = new Handler() {

            /*
             * (non-Javadoc)
             *
             * @see android.os.Handler#handleMessage(android.os.Message)
             */
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case STATUS_REQUEST_SUCCESS:
                        Lgr.i("Handler Success Archieve id = "
                                + m_archiveCurrent.getId());
                        showArchiveDetail();
                        break;
                    case STATUS_NOT_LOGIN:
                        break;
                    default:
                        break;
                }
            }

        };

        ArchiveApi.getArchiveDetail(m_nId, new RequestListener() {

            @Override
            public void onComplete(Object result) {
                Message msg = m_handlerArchiveDetail.obtainMessage();

                try {
                    JSONObject jsonObject = (JSONObject) result;
                    if (!Judge.isValidJsonValue("status", jsonObject)) {
                        return;
                    }
                    final int status = jsonObject.getInt("status");
                    switch (status) {
                        case STATUS_REQUEST_SUCCESS:
                            if (!Judge.isValidJsonValue("status", jsonObject)) {
                                break;
                            }
                            m_archiveCurrent = new Archive(jsonObject
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

                m_handlerArchiveDetail.sendMessage(msg);

            }

            @Override
            public void onHttpError(CSTResponse response) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onException(Exception e) {
                // m_archiveCurrent = new Archive(null);
                Lgr.i("ArchiveDetailActivity onError : id = " + m_nId + "!");
                if (Lgr.isDebuggable()) {
                    e.printStackTrace();
                }

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
            case android.R.id.home: {
                // Intent intentParent = new Intent(ArchiveDetailActivity.this,
                // MainActivity.class);
                // intentParent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // ArchiveDetailActivity.this.startActivity(intentParent);
                ArchiveDetailActivity.this.finish();
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 初始化组件
     */
    private void initComponent() {
        m_txvTitle = (TextView) findViewById(R.id.archive_detail_activity_title_txv);
        m_txvDate = (TextView) findViewById(R.id.archive_detail_activity_date_txv);
        m_txvPublisher = (TextView) findViewById(R.id.archive_detail_activity_publisher_txv);
        m_webvContent = (WebView) findViewById(R.id.archive_detail_activity_content_webv);
        WebSettings settings = m_webvContent.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        settings.setDefaultFontSize(48);
        // settings.setTextSize(TextSize.NORMAL);
    }

    /**
     * 绑定数据并显示
     */
    private void showArchiveDetail() {
        if (Judge.isNullOrEmpty(m_archiveCurrent)) {
            return;
        }
        m_txvTitle.setText(m_archiveCurrent.getTitle());
        m_txvDate.setText(TSUtil.toFull(m_archiveCurrent.getUpdatedAt()));
        m_txvPublisher.setText(m_archiveCurrent.getPublisher().getName());
        m_webvContent.loadDataWithBaseURL(null, m_archiveCurrent.getContent(),
                "text/html", "utf-8", null);

    }

}
