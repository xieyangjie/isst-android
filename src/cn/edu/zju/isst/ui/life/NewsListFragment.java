/**
 * 
 */
package cn.edu.zju.isst.ui.life;

import static cn.edu.zju.isst.constant.Constants.*;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.zju.isst.R;
import cn.edu.zju.isst.api.ArchiveApi;
import cn.edu.zju.isst.db.Archive;
import cn.edu.zju.isst.db.DataManager;
import cn.edu.zju.isst.exception.ExceptionWeeder;
import cn.edu.zju.isst.exception.HttpErrorWeeder;
import cn.edu.zju.isst.net.CSTResponse;
import cn.edu.zju.isst.net.NetworkConnection;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.ui.main.BaseActivity;
import cn.edu.zju.isst.ui.main.MainActivity;
import cn.edu.zju.isst.util.Judgement;
import cn.edu.zju.isst.util.L;
import cn.edu.zju.isst.util.TimeString;

/**
 * 新闻列表页
 * 
 * @author theasir
 * 
 *         TODO WIP
 */
public class NewsListFragment extends ListFragment implements OnScrollListener {

	private int m_nVisibleLastIndex;
	private int m_nCurrentPage;
	private boolean m_bIsFirstTime;

	private LoadType m_loadType;
	private final List<Archive> m_listAchive = new ArrayList<Archive>();
	private Handler m_handlerNewsList;
	private NewsListAdapter m_adapterNewsList;

	private ListView m_lsvNewsList;

	private static NewsListFragment INSTANCE = new NewsListFragment();

	public NewsListFragment() {
		m_nCurrentPage = 1;
		m_bIsFirstTime = true;
	}

	public static NewsListFragment getInstance() {
		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.ListFragment#onCreateView(android.view.LayoutInflater
	 * , android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.news_list_fragment, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.ListFragment#onViewCreated(android.view.View,
	 * android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		initComponent(view);

		if (m_bIsFirstTime) {
			initNewsList();
		}

		initHandler();

		setUpAdapter();

		setUpListener();

		if (m_bIsFirstTime) {
			requestData(LoadType.REFRESH);
			m_bIsFirstTime = false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// requestData(LoadType.REFRESH);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateOptionsMenu(android.view.Menu,
	 * android.view.MenuInflater)
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.news_list_fragment_ab_menu, menu);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onOptionsItemSelected(android.view.MenuItem
	 * )
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			requestData(LoadType.REFRESH);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.ListFragment#onListItemClick(android.widget.ListView
	 * , android.view.View, int, long)
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		L.i(this.getClass().getName() + " onListItemClick postion = "
				+ position);
		Intent intent = new Intent(getActivity(), ArchiveDetailActivity.class);
		intent.putExtra("id", m_listAchive.get(position).getId());
		getActivity().startActivity(intent);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		L.i(this.getClass().getName()
				+ " onScrollStateChanged VisibleLastIndex = "
				+ m_nVisibleLastIndex);
		if (scrollState == SCROLL_STATE_IDLE
				&& m_nVisibleLastIndex == m_adapterNewsList.getCount() - 1) {
			requestData(LoadType.LOADMORE);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		m_nVisibleLastIndex = firstVisibleItem + visibleItemCount - 1;
		L.i(this.getClass().getName() + " onScroll VisibleLastIndex = "
				+ m_nVisibleLastIndex);
	}

	private void initComponent(View view) {
		m_lsvNewsList = (ListView) view.findViewById(android.R.id.list);
	}

	/**
	 * 初始化新闻列表，若有缓存则读取缓存
	 */
	private void initNewsList() {
		List<Archive> dbNewsList = DataManager.getNewsList(getActivity());
		if (!Judgement.isNullOrEmpty(dbNewsList)) {
			for (Archive news : dbNewsList) {
				m_listAchive.add(news);
			}
		}
	}

	private void initHandler() {
		m_handlerNewsList = new Handler() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case STATUS_REQUEST_SUCCESS:
					switch (m_loadType) {
					case REFRESH:
						refresh((JSONObject) msg.obj);
						break;
					case LOADMORE:
						loadMore((JSONObject) msg.obj);
						break;
					default:
						break;
					}
					m_adapterNewsList.notifyDataSetChanged();
					break;
				case STATUS_NOT_LOGIN:// TODO
					((BaseActivity) getActivity()).updateLogin();
					requestData(m_loadType);
					break;
				default:
					((BaseActivity) getActivity()).dispose(msg);
					break;
				}
			}

		};
	}

	private void setUpAdapter() {
		m_adapterNewsList = new NewsListAdapter(getActivity());
		setListAdapter(m_adapterNewsList);
	}

	private void setUpListener() {
		m_lsvNewsList.setOnScrollListener(this);
	}

	/**
	 * 刷新列表
	 * 
	 * @param jsonObject
	 *            数据源
	 */
	private void refresh(JSONObject jsonObject) {
		if (!m_listAchive.isEmpty()) {
			m_listAchive.clear();
		}
		try {
			JSONArray jsonArray = jsonObject.getJSONArray("body");
			for (int i = 0; i < jsonArray.length(); i++) {
				m_listAchive.add(new Archive((JSONObject) jsonArray.get(i)));
			}
			L.i(this.getClass().getName() + " refreshList: "
					+ "Added archives to newsList!");
			DataManager.syncNewsList(m_listAchive, getActivity());
		} catch (JSONException e) {
			L.i(this.getClass().getName() + " refreshList!");
			e.printStackTrace();
		}
	}

	/**
	 * 加载更多
	 * 
	 * @param jsonObject
	 *            数据源
	 */
	private void loadMore(JSONObject jsonObject) {
		JSONArray jsonArray;
		try {
			jsonArray = jsonObject.getJSONArray("body");
			for (int i = 0; i < jsonArray.length(); i++) {
				m_listAchive.add(new Archive((JSONObject) jsonArray.get(i)));
			}
			L.i(this.getClass().getName() + " loadMore: "
					+ "Added archives to newsList!");
		} catch (JSONException e) {
			L.i(this.getClass().getName() + " loadMore!");
			e.printStackTrace();
		}
	}

	/**
	 * 请求数据
	 * 
	 * @param type
	 *            加载方式
	 */
	private void requestData(LoadType type) {
		if (NetworkConnection.isNetworkConnected(getActivity())) {
			m_loadType = type;
			switch (type) {
			case REFRESH:
				ArchiveApi.getNewsList(1, 20, null,
						new NewsListRequestListener(type));
				m_nCurrentPage = 1;
				break;
			case LOADMORE:
				ArchiveApi.getNewsList(++m_nCurrentPage, 20, null,
						new NewsListRequestListener(type));
				break;
			default:
				break;
			}
		} else {
			Message msg = m_handlerNewsList.obtainMessage();
			msg.what = NETWORK_NOT_CONNECTED;
			m_handlerNewsList.sendMessage(msg);
		}
	}

	/**
	 * 加载方式枚举类
	 * 
	 * @author theasir
	 * 
	 */
	private enum LoadType {
		REFRESH, LOADMORE;
	}

	/**
	 * 新闻列表RequestListener类
	 * 
	 * @author theasir
	 * 
	 */
	private class NewsListRequestListener implements RequestListener {

		private LoadType type;

		public NewsListRequestListener(LoadType type) {
			this.type = type;
		}

		@Override
		public void onComplete(Object result) {
			Message msg = m_handlerNewsList.obtainMessage();
			try {
				msg.what = ((JSONObject) result).getInt("status");
				msg.obj = (JSONObject) result;
			} catch (JSONException e) {
				L.i(this.getClass().getName() + " onComplete!");
				e.printStackTrace();
			}

			m_handlerNewsList.sendMessage(msg);
		}

		@Override
		public void onHttpError(CSTResponse response) {
			L.i(this.getClass().getName() + " onHttpError!");
			Message msg = m_handlerNewsList.obtainMessage();
			HttpErrorWeeder.fckHttpError(response, msg);
			m_handlerNewsList.sendMessage(msg);
		}

		@Override
		public void onException(Exception e) {
			L.i(this.getClass().getName() + " onException!");
			Message msg = m_handlerNewsList.obtainMessage();
			ExceptionWeeder.fckException(e, msg);
			m_handlerNewsList.sendMessage(msg);
		}

	}

	/**
	 * View容器类
	 * 
	 * @author theasir
	 * 
	 */
	private final class ViewHolder {
		public TextView titleTxv;
		public TextView dateTxv;
		public TextView publisherTxv;
		public TextView descriptionTxv;
		public View indicatorView;
	}

	/**
	 * 新闻列表自定义适配器类
	 * 
	 * @author theasir
	 * 
	 */
	private class NewsListAdapter extends BaseAdapter {

		private LayoutInflater inflater;

		public NewsListAdapter(Context context) {
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return m_listAchive.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();

				convertView = inflater
						.inflate(R.layout.archive_list_item, null);
				holder.titleTxv = (TextView) convertView
						.findViewById(R.id.archive_list_item_title_txv);
				holder.dateTxv = (TextView) convertView
						.findViewById(R.id.archive_list_item_date_txv);
				holder.publisherTxv = (TextView) convertView
						.findViewById(R.id.archive_list_item_publisher_txv);
				holder.descriptionTxv = (TextView) convertView
						.findViewById(R.id.archive_list_item_description_txv);
				holder.indicatorView = (View) convertView
						.findViewById(R.id.archive_list_item_indicator_view);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.titleTxv.setText(m_listAchive.get(position).getTitle());
			holder.dateTxv.setText(TimeString.toYMD(m_listAchive.get(position)
					.getUpdatedAt()));
			holder.publisherTxv.setText(m_listAchive.get(position)
					.getPublisher().getName());
			holder.descriptionTxv.setText(m_listAchive.get(position)
					.getDescription());
			// holder.indicatorView.setBackgroundColor(Color.BLUE);

			return convertView;
		}

	}

}
