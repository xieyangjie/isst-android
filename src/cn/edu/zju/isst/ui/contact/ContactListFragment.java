/**
 * 
 */
package cn.edu.zju.isst.ui.contact;

import static cn.edu.zju.isst.constant.Constants.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.zju.isst.R;
import cn.edu.zju.isst.api.AlumniApi;
import cn.edu.zju.isst.db.City;
import cn.edu.zju.isst.db.DataManager;
import cn.edu.zju.isst.db.Major;
import cn.edu.zju.isst.db.User;
import cn.edu.zju.isst.exception.ExceptionWeeder;
import cn.edu.zju.isst.exception.HttpErrorWeeder;
import cn.edu.zju.isst.net.CSTResponse;
import cn.edu.zju.isst.net.NetworkConnection;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.util.J;
import cn.edu.zju.isst.util.L;

/**
 * 通讯录列表页
 * 
 * @author yyy
 * 
 *         TODO WIP
 */
public class ContactListFragment extends Fragment {

	private final List<User> m_listUser = new ArrayList<User>();
	private final List<City> m_listCity = new ArrayList<City>();
	private final List<Major> m_listMajors = new ArrayList<Major>();

	private HandlerAlumniList m_handlerAlumniList;
	private HandlerCityList m_handlerCityList;
	private HandlerMajorList m_handlerMajorList;

	private ListView m_lvAlumni;
	private TextView m_tvFilterCondition;

	private ContactFilter m_userFilter = new ContactFilter();

	private List<NoteBookItem> m_noteBookList = new ArrayList<NoteBookItem>();
	private NoteBookadapter m_noteBookAdapter;

	// 是否需要将获得用户列表写入数据库
	boolean m_flag = true;

	private static ContactListFragment INSTANCE = new ContactListFragment();

	public ContactListFragment() {
	}

	public static ContactListFragment getInstance() {
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
		return inflater.inflate(R.layout.contact_list_fragment, null);
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

		m_handlerAlumniList = new HandlerAlumniList();
		m_handlerCityList = new HandlerCityList();
		m_handlerMajorList = new HandlerMajorList();

		// 初始化数据
		initDate();

		// 控件
		m_lvAlumni = (ListView) view
				.findViewById(R.id.contact_list_fragment_contacts_lsv);
		m_tvFilterCondition = (TextView) view
				.findViewById(R.id.contact_list_fragment_conditon_content_txv);

		m_noteBookAdapter = new NoteBookadapter(getActivity(), m_noteBookList);
		m_lvAlumni.setAdapter(m_noteBookAdapter);

		m_lvAlumni.setOnItemClickListener(new onNotebookItemClickListener());

		showFilterConditon();
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
		inflater.inflate(R.menu.alumni_list_fragment_ab_menu, menu);
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
		case R.id.action_alumni_filter:
			Intent intent = new Intent(getActivity(),
					ContactFilterActivity.class);
			startActivityForResult(intent, 20);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * 初始化本班通讯录列表，若有缓存则读取缓存，无缓存请求数据
	 */
	private void initDate() {
		// 获取用户班级ID
		User user = DataManager.getCurrentUser();
		m_userFilter.classId = user.getClassId();

		// 初始化通讯录列表
		List<User> dbAlumniList = DataManager.getClassMateList();
		if (!m_listUser.isEmpty()) {
			m_listUser.clear();
		}
		if (!J.isNullOrEmpty(dbAlumniList)) {
			for (User Alumni : dbAlumniList) {
				m_listUser.add(Alumni);
			}
			getNoteBookData();
		} else {
			requestData();
		}
	}

	/**
	 * 请求数据
	 * 
	 * @param type
	 *            加载方式
	 */
	private void requestData() {
		if (NetworkConnection.isNetworkConnected(getActivity())) {
			getUserList(m_userFilter);
			AlumniApi.getCityList(new BaseListRequestListener(
					m_handlerCityList, City.class, m_listCity));
			AlumniApi.getMajorList(new BaseListRequestListener(
					m_handlerMajorList, Major.class, m_listMajors));
		} else {
			Message msg = m_handlerAlumniList.obtainMessage();
			msg.what = NETWORK_NOT_CONNECTED;
			m_handlerAlumniList.sendMessage(msg);
		}
	}

	private class BaseListRequestListener<T> implements RequestListener {
		Handler handler;
		List<T> list;
		Class<T> clazz;

		public BaseListRequestListener(final Handler h, Class<T> c, List<T> l) {
			this.handler = h;
			this.list = l;
			this.clazz = c;
		}

		@Override
		public void onComplete(Object result) {
			Message msg = handler.obtainMessage();
			try {
				msg.what = ((JSONObject) result).getInt("status");
				JSONArray jsonArray = ((JSONObject) result)
						.getJSONArray("body");
				list.clear();
				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						list.add(clazz.getConstructor(JSONObject.class)
								.newInstance((JSONObject) jsonArray.get(i)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (JSONException e) {
				L.i(this.getClass().getName() + " onComplete!");
				e.printStackTrace();
			}

			handler.sendMessage(msg);
		}

		@Override
		public void onHttpError(CSTResponse response) {
			L.i(this.getClass().getName() + " onHttpError!");
			Message msg = handler.obtainMessage();
			HttpErrorWeeder.fckHttpError(response, msg);
			handler.sendMessage(msg);
		}

		@Override
		public void onException(Exception e) {
			L.i(this.getClass().getName() + " onException!");
			Message msg = handler.obtainMessage();
			ExceptionWeeder.fckException(e, msg);
			handler.sendMessage(msg);
		}
	}

	/**
	 * 填充m_noteBookList数据
	 */
	private void getNoteBookData() {
		if (m_noteBookList.size() != 0) {
			m_noteBookList.clear();
		}
		for (int i = 0; i < m_listUser.size(); i++) {
			NoteBookItem n = new NoteBookItem();
			n.name = m_listUser.get(i).getName();
			n.index = String.valueOf(Pinyin4j.getHanyuPinyin(n.name).charAt(0));
			m_noteBookList.add(n);
		}

	}

	public class onNotebookItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(getActivity(),
					ContactDetailActivity.class);
			intent.putExtra("id", m_listUser.get(arg2).getId());
			ContactListFragment.this.getActivity().startActivity(intent);
		}
	}

	/**
	 * 显示筛选条件
	 */
	private void showFilterConditon() {
		StringBuilder sb = new StringBuilder();

		if (m_flag) {
			sb.append(" 班级：" + "本班");
		}
		if (!J.isNullOrEmpty(m_userFilter.name)) {
			sb.append(" 姓名：" + m_userFilter.name);
		}
		if (m_userFilter.gender != 0) {
			sb.append(" 性别：" + m_userFilter.genderString);
		}
		if (m_userFilter.grade != 0) {
			sb.append(" 年级：" + m_userFilter.grade);
		}
		if (m_userFilter.majorId != 0) {
			sb.append(" 方向：" + m_userFilter.majorString);
		}
		if (!J.isNullOrEmpty(m_userFilter.company)) {
			sb.append(" 公司：" + m_userFilter.company);
		}
		if (m_userFilter.cityId != 0) {
			sb.append(" 城市：" + m_userFilter.cityString);
		}
		m_tvFilterCondition.setText(sb.toString());
	}

	/**
	 * 调用AlumniApi.getUserList
	 */
	private void getUserList(ContactFilter uf) {
		AlumniApi.getUserList(uf.id, uf.name, uf.gender, uf.grade, uf.classId,
				uf.majorId, uf.cityId, uf.company, new BaseListRequestListener(
						m_handlerAlumniList, User.class, m_listUser));
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 20) {
			m_userFilter.clear();
			m_userFilter = (ContactFilter) data.getExtras().getSerializable(
					"data");
			m_flag = false;
			getUserList(m_userFilter);
			showFilterConditon();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private class HandlerAlumniList extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case STATUS_REQUEST_SUCCESS:
				Collections.sort(m_listUser, new Pinyin4j.PinyinComparator());
				if (m_flag) {
					DataManager.syncClassMateList(m_listUser);
				}
				getNoteBookData();
				m_noteBookAdapter.notifyDataSetChanged();
				break;
			case STATUS_NOT_LOGIN:
				break;
			default:
				break;
			}
		}
	}

	private class HandlerCityList extends Handler {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case STATUS_REQUEST_SUCCESS:
				if (m_flag) {
					DataManager.syncCityList(m_listCity);
				}
				break;
			case STATUS_NOT_LOGIN:
				break;
			default:
				break;
			}
		}
	}

	private class HandlerMajorList extends Handler {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case STATUS_REQUEST_SUCCESS:
				if (m_flag) {
					DataManager.syncMajorList(m_listMajors);
				}
				break;
			case STATUS_NOT_LOGIN:
				break;
			default:
				break;
			}
		}
	}
}
