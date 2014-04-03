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
import cn.edu.zju.isst.util.Judgement;
import cn.edu.zju.isst.util.L;

/**
 * 学习园地列表页
 * 
 * @author xyj
 * 
 *         TODO WIP
 */
public class StudyListFragment extends ListFragment implements OnScrollListener {

	private int m_nVisibleLastIndex;

	private final List<Archive> m_listAchive = new ArrayList<Archive>();
	private Handler m_handlerStudyList;
	private StudyListAdapter m_adapterStudyList;

	private ListView m_lsvStudyList;

	private static StudyListFragment INSTANCE = new StudyListFragment();

	public StudyListFragment() {
	}

	public static StudyListFragment getInstance() {
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
		return inflater.inflate(R.layout.study_list_fragment, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.ListFragment#onViewCreated(android.view.View,
	 * android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		m_lsvStudyList = (ListView) view.findViewById(android.R.id.list);

		initStudyList();

		m_handlerStudyList = new Handler() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case STATUS_REQUEST_SUCCESS:
					m_adapterStudyList.notifyDataSetChanged();
					break;
				case STATUS_NOT_LOGIN:
					break;
				default:
					break;
				}
			}

		};

		m_adapterStudyList = new StudyListAdapter(getActivity());

		setListAdapter(m_adapterStudyList);

		if (m_listAchive.size() == 0) {
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
		// TODO Auto-generated method stub
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
		inflater.inflate(R.menu.study_list_fragment_ab_menu, menu);
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
		L.i(this.getClass().getName() + " onListItemClick postion = ");
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
				&& m_nVisibleLastIndex == m_adapterStudyList.getCount() - 1) {
			requestData(LoadType.LOADMORE);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		m_nVisibleLastIndex = firstVisibleItem + visibleItemCount - 1;
	}

	/**
	 * 初始化新闻列表，若有缓存则读取缓存
	 */
	private void initStudyList() {
		List<Archive> dbStudyList = DataManager
				.getCurrentStudyList(getActivity());
		if (!Judgement.isNullOrEmpty(dbStudyList)) {
			for (Archive study : dbStudyList) {
				m_listAchive.add(study);
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
		if (!m_listAchive.isEmpty()) {
			m_listAchive.clear();
		}
		try {
			JSONArray jsonArray = jsonObject.getJSONArray("body");
			for (int i = 0; i < jsonArray.length(); i++) {
				m_listAchive.add(new Archive((JSONObject) jsonArray.get(i)));
			}
			L.i(this.getClass().getName() + " refreshList: "
					+ "Added archives to studyList!");
			DataManager.syncStudysList(m_listAchive, getActivity());
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
					+ "Added archives to studyList!");
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
				ArchiveApi.getStudyList(null, 10, null,
						new StudyListRequestListener(type));
				break;
			case LOADMORE:
				ArchiveApi.getStudyList(null, 5, null,
						new StudyListRequestListener(type));
				break;
			default:
				break;
			}
		} else {
			Message msg = m_handlerStudyList.obtainMessage();
			msg.what = NETWORK_NOT_CONNECTED;
			m_handlerStudyList.sendMessage(msg);
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
	private class StudyListRequestListener implements RequestListener {

		private LoadType type;

		public StudyListRequestListener(LoadType type) {
			this.type = type;
		}

		@Override
		public void onComplete(Object result) {
			Message msg = m_handlerStudyList.obtainMessage();
			try {
				msg.what = ((JSONObject) result).getInt("status");
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
			} catch (JSONException e) {
				L.i(this.getClass().getName() + " onComplete!");
				e.printStackTrace();
			}

			m_handlerStudyList.sendMessage(msg);
		}

		@Override
		public void onHttpError(CSTResponse response) {
			L.i(this.getClass().getName() + " onHttpError!");
			Message msg = m_handlerStudyList.obtainMessage();
			HttpErrorWeeder.fckHttpError(response, msg);
			m_handlerStudyList.sendMessage(msg);
		}

		@Override
		public void onException(Exception e) {
			L.i(this.getClass().getName() + " onException!");
			Message msg = m_handlerStudyList.obtainMessage();
			ExceptionWeeder.fckException(e, msg);
			m_handlerStudyList.sendMessage(msg);
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
	private class StudyListAdapter extends BaseAdapter {

		private LayoutInflater inflater;

		public StudyListAdapter(Context context) {
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
			holder.dateTxv.setText(String.valueOf(m_listAchive.get(position)
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
