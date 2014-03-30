/**
 * 
 */
package cn.edu.zju.isst.ui.life;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.api.ArchiveApi;
import cn.edu.zju.isst.db.Archive;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.ui.main.MainActivity;
import cn.edu.zju.isst.util.L;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import static cn.edu.zju.isst.constant.Constants.*;

/**
 * @author theasir
 * 
 */
public class ArchiveDetailActivity extends ActionBarActivity {

	private int m_nId;

	private Archive m_archiveCurrent;
	private Handler m_handlerArchiveDetail;

	private TextView m_txvTitle;
	private TextView m_txvDate;
	private TextView m_txvPublisher;
	private TextView m_txvContent;
	private WebView m_webvContent;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.archive_detail_activity);
		initComponent();

		ActionBar actionBar = getSupportActionBar();
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
				case REQUEST_SUCCESS:
					L.i("Handler Success Archieve id = "
							+ m_archiveCurrent.getId());
					showArchiveDetail();
					break;
				case NOT_LOGIN:
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
					final int status = jsonObject.getInt("status");
					switch (status) {
					case REQUEST_SUCCESS:
						m_archiveCurrent = new Archive(jsonObject
								.getJSONObject("body"));
						break;
					case NOT_LOGIN:
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
			public void onError(Exception e) {
				m_archiveCurrent = new Archive(null);
				L.i("ArchiveDetailActivity onError : id = " + m_nId + "!");
				if (L.isDebuggable()) {
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

	private void initComponent() {
		m_txvTitle = (TextView) findViewById(R.id.archive_detail_title_txv);
		m_txvDate = (TextView) findViewById(R.id.archive_detail_date_txv);
		m_txvPublisher = (TextView) findViewById(R.id.archive_detail_publisher_txv);
		m_txvContent = (TextView) findViewById(R.id.archive_detail_content_txv);
		m_webvContent = (WebView) findViewById(R.id.archive_detail_content_webv);
		WebSettings settings = m_webvContent.getSettings();
		settings.setUseWideViewPort(true); 
        settings.setLoadWithOverviewMode(true);
	}

	private void showArchiveDetail() {
		m_txvTitle.setText(m_archiveCurrent.getTitle());
		m_txvDate.setText(String.valueOf(m_archiveCurrent.getUpdatedAt()));
		// m_txvPublisher.setText(m_archiveCurrent.getPublisher().getName());
		m_txvPublisher.setText("TESTER");
		// m_txvContent.setText(Html.fromHtml(m_archiveCurrent.getContent()));
		// m_txvContent.setMovementMethod(LinkMovementMethod.getInstance());
		m_webvContent.loadDataWithBaseURL(null, m_archiveCurrent.getContent(),
				"text/html", "utf-8", null);
		
	}

}
