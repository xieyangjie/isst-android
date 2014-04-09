/**
 * 
 */
package cn.edu.zju.isst.ui.alumni;

import static cn.edu.zju.isst.constant.Constants.*;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.bluetooth.BluetoothClass.Device.Major;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.widget.AdapterView.OnItemClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.zju.isst.R;
import cn.edu.zju.isst.api.AlumniApi;
import cn.edu.zju.isst.db.City;
import cn.edu.zju.isst.db.DataManager;
import cn.edu.zju.isst.db.User;
import cn.edu.zju.isst.db.Majors;
import cn.edu.zju.isst.exception.ExceptionWeeder;
import cn.edu.zju.isst.exception.HttpErrorWeeder;
import cn.edu.zju.isst.net.CSTResponse;
import cn.edu.zju.isst.net.NetworkConnection;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.ui.life.ArchiveDetailActivity;
import cn.edu.zju.isst.util.Judgement;
import cn.edu.zju.isst.util.L;

/**
 * 通讯录列表页
 * 
 * @author yyy
 * 
 *         TODO WIP
 */
public class AlumniFragment extends Fragment {

	private int m_nVisibleLastIndex;

	private final List<User> m_listUser = new ArrayList<User>();
	private final List<City> m_listCity = new ArrayList<City>();
	private final List<Majors> m_listMajors = new ArrayList<Majors>();
	
	private Handler m_handlerAlumniList;
	private Handler m_handlerCityList;
	private Handler m_handlerMajorList;

	private ListView m_lvAlumni;
	private TextView m_tvFilterCondition;
	
	private UserFilter m_userFilter = new UserFilter();
	
	List<NoteBookItem> m_noteBookList = new ArrayList<NoteBookItem>();
	NoteBookadapter m_noteBookAdapter;
	
	//是否需要将获得用户列表写入数据库
	boolean m_flag = true;
	
	private static AlumniFragment INSTANCE = new AlumniFragment();

	public AlumniFragment() {
	}

	public static AlumniFragment getInstance() {
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
		return inflater.inflate(R.layout.alumni_list_fragment, null);
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
		
		m_handlerAlumniList = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case STATUS_REQUEST_SUCCESS:
					if(m_flag) {
					DataManager.syncClassMateList(m_listUser, getActivity());
					}
					Collections.sort(m_listUser,new Pinyin4j.PinyinComparator());
					getNoteBookData();
					m_noteBookAdapter.notifyDataSetChanged();
					break;
				case STATUS_NOT_LOGIN:
					break;
				default:
					break;
				}
			}
		};
		
		//初始化数据
		initAlumniList();
		
		//控件
		m_lvAlumni = (ListView) view.findViewById(R.id.alumni_list);
		m_tvFilterCondition = (TextView) view.findViewById(R.id.alumni_filter_conditon_content);
		
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
			Intent intent = new Intent(getActivity(), AlumniFilterActivity.class);
			startActivityForResult(intent, 20);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * 初始化本班通讯录列表，若有缓存则读取缓存，无缓存请求数据
	 */
	private void initAlumniList() {
		L.i("initAlumniList");
		//获取用户班级ID
		User user  = DataManager.getCurrentUser(getActivity());
		m_userFilter.classId = user.getClassId();

		List<User> dbAlumniList = DataManager
				.getClassMateList(getActivity());
		if (!m_listUser.isEmpty()){
			m_listUser.clear();
		}
		if (!Judgement.isNullOrEmpty(dbAlumniList)) {
			for (User Alumni : dbAlumniList) {
				m_listUser.add(Alumni);
			}
			getNoteBookData();
		}
		else {
			//请求数据
			L.i("requestData");
			requestData();
		}
	}

	/**
	 * 
	 */
	private void initMajor() {
		List<City> dbCityList = DataManager
				.getCityList(getActivity());
		if (Judgement.isNullOrEmpty(dbCityList)) {
			//AlumniApi.getCityList(new CityListRequestListener());
		}
	}
	
	/**
	 * 
	 */
	private void initCity() {
		
	}
	

	/**
	 * 请求数据
	 * 
	 * @param type
	 *            加载方式
	 */
	private void requestData() {
		if (NetworkConnection.isNetworkConnected(getActivity())) {
			AlumniApi_getUserList(m_userFilter);
			;
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
		
		public BaseListRequestListener(final Handler h,Class<T> c,List<T> l)
		{
			this.handler = h;
			this.list = l;
			this.clazz = c;
		}
		
		@Override
		public void onComplete(Object result) {
			Message msg = m_handlerAlumniList.obtainMessage();
			try {
				msg.what = ((JSONObject) result).getInt("status");
				try {
					JSONArray jsonArray = ((JSONObject) result).getJSONArray("body");
					for (int i = 0; i < jsonArray.length(); i++) {
						//m_listMajors.add(new Majors((JSONObject) jsonArray.get(i)));
						try {
							list.add(clazz.getConstructor(JSONObject.class).newInstance((JSONObject) jsonArray.get(i)));
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
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
			Message msg = m_handlerAlumniList.obtainMessage();
			HttpErrorWeeder.fckHttpError(response, msg);
			handler.sendMessage(msg);
		}

		@Override
		public void onException(Exception e) {
			L.i(this.getClass().getName() + " onException!");
			Message msg = m_handlerAlumniList.obtainMessage();
			ExceptionWeeder.fckException(e, msg);
			m_handlerAlumniList.sendMessage(msg);
		}
	}
		
	/**
	 *  填充m_noteBookList数据
	 */
	private void getNoteBookData() {
		if (m_noteBookList.size()!=0) {
			m_noteBookList.clear();
		}
		for (int i = 0; i < m_listUser.size(); i++) {
			NoteBookItem n = new NoteBookItem();
			n.name = m_listUser.get(i).getName();
			n.index = String.valueOf(Pinyin4j.getHanyuPinyin(n.name).charAt(0));
			m_noteBookList.add(n);
		}

	}
	
	public class onNotebookItemClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), AlumniDetailActivity.class);
			intent.putExtra("id", m_listUser.get(arg2).getId());
			getActivity().startActivity(intent);
		}
	}
	
	/**
	 * 显示筛选条件
	 */
	private void showFilterConditon(){
		StringBuilder sb = new StringBuilder();
		
		if (m_userFilter.classId!=null){
			sb.append(" 班级："+ m_userFilter.classId);
		}
		if (m_userFilter.gender!=null){
			sb.append(" 性别："+ m_userFilter.gender);
		}
		if (m_userFilter.grade!=null){
			sb.append(" 年级："+ m_userFilter.grade);
		}
		if (m_userFilter.majorId!=null){
			sb.append(" 专业："+ m_userFilter.majorId);
		}
		m_tvFilterCondition.setText(sb.toString());
	}
	
	/**
	 * 调用AlumniApi.getUserList
	 */
	private void AlumniApi_getUserList(UserFilter uf) {
		AlumniApi.getUserList(uf.id, uf.name, uf.gender, uf.grade, uf.classId,
		uf.majorId, uf.cityId, uf.company, new BaseListRequestListener(m_handlerAlumniList, User.class, m_listUser));
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		L.i("yyy --- onActivityResult resultCode" + resultCode);
		if (resultCode == 20) {
			Map<String, Object> dataMap = new ConcurrentHashMap<String, Object>();
			dataMap = (Map<String, Object>) data.getExtras().getSerializable("data");
			
			m_userFilter.clear();
			m_userFilter.name = (String) dataMap.get("name");
			m_userFilter.grade = (Integer) dataMap.get("grade");
			m_userFilter.majorId = (Integer) dataMap.get("majorId");
			AlumniApi_getUserList(m_userFilter);
			m_flag = false;
			showFilterConditon();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
