/**
 * 
 */
package cn.edu.zju.isst.ui.life;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.api.ArchiveApi;
import cn.edu.zju.isst.db.Archive;
import cn.edu.zju.isst.db.DataManager;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.util.L;
import android.R.anim;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import static cn.edu.zju.isst.constant.Constants.*;

/**
 * @author theasir
 * 
 */
public class NewsListFragment extends ListFragment {

	private static final int TYPE_NEW = 100;
	private static final int TYPE_OLD = 200;

	private List<Archive> m_listAchive;
	private Handler m_handlerNewsList;
	private NewsListAdapter m_adapterNewsList;

	private ListView m_lsvNewsList;

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
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		m_lsvNewsList = (ListView) view.findViewById(android.R.id.list);
		
		m_listAchive = new ArrayList<Archive>();

		m_handlerNewsList = new Handler() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case REQUEST_SUCCESS + TYPE_NEW:
					m_adapterNewsList.notifyDataSetChanged();
					break;
				case NOT_LOGIN:
					break;
				default:
					break;
				}
			}

		};

		m_adapterNewsList = new NewsListAdapter(getActivity());

		// new AsyncTask<Void, Void, Void>() {
		//
		// @Override
		// protected Void doInBackground(Void... params) {
		// requestData(TYPE_NEW, null, null, null);
		// return null;
		// }
		//
		// /*
		// * (non-Javadoc)
		// *
		// * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		// */
		// @Override
		// protected void onPostExecute(Void result) {
		// m_adapterNewsList.notifyDataSetChanged();
		// }
		//
		// }.execute();

		L.i("!!!!!!!!!!");
		
		requestData(TYPE_NEW, null, null, null);

		setListAdapter(m_adapterNewsList);
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
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), ArchiveDetailActivity.class);
		intent.putExtra("id", m_listAchive.get(position).getId());
		getActivity().startActivity(intent);
	}

	private void refreshList(JSONObject jsonObject) {
		// m_listAchive = new ArrayList<Archive>();
		try {
			JSONArray jsonArray = jsonObject.getJSONArray("body");
			for (int i = 0; i < jsonArray.length(); i++) {
				m_listAchive.add(new Archive((JSONObject) jsonArray.get(i)));
				L.i("Add archive to list!");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// DataManager.syncNewsList(m_listAchive, getActivity());
	}

	private void requestData(int type, Integer page, Integer pageSize,
			String keywords) {
		switch (type) {
		case TYPE_NEW:
			ArchiveApi.getNewsList(page, pageSize, keywords,
					new NewsListRequestListener(type));
			break;
		case TYPE_OLD:
			break;
		default:
			break;
		}
	}

	private class NewsListRequestListener implements RequestListener {

		private int type;

		public NewsListRequestListener(int type) {
			this.type = type;
		}

		@Override
		public void onComplete(Object result) {
			Message msg = m_handlerNewsList.obtainMessage();
			try {
				msg.what = ((JSONObject) result).getInt("status") + type;
				refreshList((JSONObject) result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			m_handlerNewsList.sendMessage(msg);
		}

		@Override
		public void onError(Exception e) {
			// TODO Auto-generated method stub

		}

	}

	private final class ViewHolder {
		public TextView titleTxv;
		public TextView dateTxv;
		public TextView publisherTxv;
		public TextView descriptionTxv;
	}

	private class NewsListAdapter extends BaseAdapter {

		private LayoutInflater inflater;

		public NewsListAdapter(Context context) {
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
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
						.findViewById(R.id.title_txv);
				holder.dateTxv = (TextView) convertView
						.findViewById(R.id.date_txv);
				holder.publisherTxv = (TextView) convertView
						.findViewById(R.id.publisher_txv);
				holder.descriptionTxv = (TextView) convertView
						.findViewById(R.id.description_txv);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.titleTxv.setText(m_listAchive.get(position).getTitle());
			holder.dateTxv.setText(String.valueOf(m_listAchive.get(position)
					.getUpdatedAt()));
//			holder.publisherTxv.setText(m_listAchive.get(position)
//					.getPublisher().getName());
			holder.publisherTxv.setText("TEST");
			holder.descriptionTxv.setText(m_listAchive.get(position)
					.getDescription());

			return convertView;
		}

	}

}
