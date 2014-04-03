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

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.api.CampusActivityApi;
import cn.edu.zju.isst.db.Archive;
import cn.edu.zju.isst.db.CampusActivity;
import cn.edu.zju.isst.db.DataManager;
import cn.edu.zju.isst.exception.ExceptionWeeder;
import cn.edu.zju.isst.exception.HttpErrorWeeder;
import cn.edu.zju.isst.net.CSTResponse;
import cn.edu.zju.isst.net.NetworkConnection;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.util.Judgement;
import cn.edu.zju.isst.util.L;
import cn.edu.zju.isst.util.TimeString;
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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

/**
 * @author theasir
 * 
 */
public class CampusActivityListFragment extends ListFragment implements
		OnScrollListener {

	private int m_nVisibleLastIndex;

	private final List<CampusActivity> m_listCampusActivity = new ArrayList<CampusActivity>();
	private Handler m_handlerCampusActivityList;
	private CampusActivityListAdapter m_adapterCampusActivityList;

	private ListView m_lsvCampusActivityList;

	private static CampusActivityListFragment INSTANCE = new CampusActivityListFragment();

	public CampusActivityListFragment() {
	}

	public static CampusActivityListFragment getInstance() {
		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
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
		return inflater.inflate(R.layout.campus_activity_list_fragment, null);
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

		m_lsvCampusActivityList = (ListView) view
				.findViewById(android.R.id.list);

		initCampusActivityList();

		m_handlerCampusActivityList = new Handler() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case STATUS_REQUEST_SUCCESS:
					m_adapterCampusActivityList.notifyDataSetChanged();
					break;
				case STATUS_NOT_LOGIN:
					break;
				default:
					break;
				}
			}

		};

		m_adapterCampusActivityList = new CampusActivityListAdapter(
				getActivity());
		setListAdapter(m_adapterCampusActivityList);

		if (m_listCampusActivity.size() == 0) {
			requestData(LoadType.REFRESH);
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
		// Intent intent = new Intent(getActivity(),
		// ArchiveDetailActivity.class);
		// intent.putExtra("id", m_listCampusActivity.get(position).getId());
		// getActivity().startActivity(intent);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		L.i(this.getClass().getName()
				+ " onScrollStateChanged VisibleLastIndex = "
				+ m_nVisibleLastIndex);
		if (scrollState == SCROLL_STATE_IDLE
				&& m_nVisibleLastIndex == m_adapterCampusActivityList
						.getCount() - 1) {
			requestData(LoadType.LOADMORE);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		m_nVisibleLastIndex = firstVisibleItem + visibleItemCount - 1;
	}

	/**
	 * 初始化在校活动列表，若有缓存则读取缓存
	 */
	private void initCampusActivityList() {
		List<CampusActivity> dbNewsList = DataManager
				.getCampusActivityList(getActivity());
		if (!m_listCampusActivity.isEmpty()) {
			m_listCampusActivity.clear();
		}
		if (dbNewsList != null && !dbNewsList.equals(null)) {
			for (CampusActivity news : dbNewsList) {
				m_listCampusActivity.add(news);
			}
		}
	}

	/**
	 * 刷新列表
	 * 
	 * @param jsonObject
	 *            数据源
	 */
	private void refresh(JSONObject jsonObject) {
		if (!m_listCampusActivity.isEmpty()) {
			m_listCampusActivity.clear();
		}
		try {
			if (Judgement.isValidJsonValue("body", jsonObject)) {
				return;
			}
			JSONArray jsonArray = jsonObject.getJSONArray("body");

			for (int i = 0; i < jsonArray.length(); i++) {
				m_listCampusActivity.add(new CampusActivity(
						(JSONObject) jsonArray.get(i)));
			}
			L.i(this.getClass().getName() + " refreshList: "
					+ "Added campusActivity to newsList!");
			DataManager.syncCampusActivityList(m_listCampusActivity,
					getActivity());
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
			if (Judgement.isValidJsonValue("body", jsonObject)) {
				return;
			}
			jsonArray = jsonObject.getJSONArray("body");
			for (int i = 0; i < jsonArray.length(); i++) {
				m_listCampusActivity.add(new CampusActivity(
						(JSONObject) jsonArray.get(i)));
			}
			L.i(this.getClass().getName() + " loadMore: "
					+ "Added campusActivity to newsList!");
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
			switch (type) {// TODO 刷新策略
			case REFRESH:
				// 设置刷新策略，一次性加载最新若干条
				CampusActivityApi.getCampusActivityList(null, 20, null,
						new NewsListRequestListener(type));
				break;
			case LOADMORE:
				CampusActivityApi.getCampusActivityList(null, 5, null,
						new NewsListRequestListener(type));
				break;
			default:
				break;
			}
		} else {
			Message msg = m_handlerCampusActivityList.obtainMessage();
			msg.what = NETWORK_NOT_CONNECTED;
			m_handlerCampusActivityList.sendMessage(msg);
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
			Message msg = m_handlerCampusActivityList.obtainMessage();
			try {
				if (Judgement.isValidJsonValue("status", (JSONObject) result)) {
					return;
				}
				msg.what = ((JSONObject) result).getInt("status");
				if (msg.what == STATUS_REQUEST_SUCCESS) {
					switch (type) {
					case REFRESH:
						refresh((JSONObject) result);
						break;
					case LOADMORE:
						loadMore((JSONObject) result);
						break;
					default:
						break;
					}
				}
			} catch (JSONException e) {
				L.i(this.getClass().getName() + " onComplete!");
				e.printStackTrace();
			}

			m_handlerCampusActivityList.sendMessage(msg);
		}

		@Override
		public void onHttpError(CSTResponse response) {
			L.i(this.getClass().getName() + " onHttpError!");
			Message msg = m_handlerCampusActivityList.obtainMessage();
			HttpErrorWeeder.fckHttpError(response, msg);
			m_handlerCampusActivityList.sendMessage(msg);
		}

		@Override
		public void onException(Exception e) {
			L.i(this.getClass().getName() + " onException!");
			Message msg = m_handlerCampusActivityList.obtainMessage();
			ExceptionWeeder.fckException(e, msg);
			m_handlerCampusActivityList.sendMessage(msg);
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
		public TextView updateTimeTxv;
		public TextView startTimeTxv;
		public TextView expireTimeTxv;
		public TextView descriptionTxv;
		public View indicatorView;
	}

	/**
	 * 在校活动列表自定义适配器类
	 * 
	 * @author theasir
	 * 
	 */
	private class CampusActivityListAdapter extends BaseAdapter {

		private LayoutInflater inflater;

		public CampusActivityListAdapter(Context context) {
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return m_listCampusActivity.size();
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

				convertView = inflater.inflate(
						R.layout.campus_activity_list_item, null);
				holder.titleTxv = (TextView) convertView
						.findViewById(R.id.campus_activity_list_item_title_txv);
				holder.updateTimeTxv = (TextView) convertView
						.findViewById(R.id.campus_activity_list_item_updatetime_txv);
				holder.startTimeTxv = (TextView) convertView
						.findViewById(R.id.campus_activity_list_item_starttime_txv);
				holder.expireTimeTxv = (TextView) convertView
						.findViewById(R.id.campus_activity_list_item_expiretime_txv);
				holder.descriptionTxv = (TextView) convertView
						.findViewById(R.id.campus_activity_list_item_description_txv);
				holder.indicatorView = (View) convertView
						.findViewById(R.id.campus_activity_list_item_indicator_view);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.titleTxv.setText(m_listCampusActivity.get(position)
					.getTitle());
			holder.updateTimeTxv.setText(TimeString.toYMD(m_listCampusActivity.get(position)
					.getUpdatedAt()));
			holder.startTimeTxv.setText(TimeString.toHM(m_listCampusActivity.get(position)
					.getStartTime()));
			holder.expireTimeTxv.setText(TimeString.toHM(m_listCampusActivity.get(position)
					.getExpireTime()));
			holder.descriptionTxv.setText(m_listCampusActivity.get(position)
					.getDescription());

			return convertView;
		}

	}
}
