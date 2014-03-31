/**
 * 
 */
package cn.edu.zju.isst.ui.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.constant.Nav;
import cn.edu.zju.isst.constant.NavGroup;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

/**
 * 侧拉菜单Fragment类
 * 
 * @author theasir
 * 
 */
public class SlidingMenuFragment extends Fragment {

	private List<String> m_listGroupNames = new ArrayList<String>();
	private Map<String, List<String>> m_mapGroupCollection = new HashMap<String, List<String>>();
	private String[] m_strFrom;
	private int[] m_nTo;
	private List<Map<String, Object>> m_listDataOfListItem = new ArrayList<Map<String, Object>>();

	private OnGroupMenuItemClickListener m_listenerOnMenuItemClick;

	private ExpandableListView m_explsvMenu;
	private ListView m_lsvMenu;

	private static SlidingMenuFragment INSTANCE = new SlidingMenuFragment();

	public SlidingMenuFragment() {
		initConstants();
	}

	public static SlidingMenuFragment getInstance() {
		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			m_listenerOnMenuItemClick = (OnGroupMenuItemClickListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnMenuItemClickListener");
		}
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.sm_main, null);

		m_explsvMenu = (ExpandableListView) rootView
				.findViewById(R.id.main_group);
		m_lsvMenu = (ListView) rootView.findViewById(R.id.main_list);

		return rootView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final SlidingMenuExpListAdapter expListAdapter = new SlidingMenuExpListAdapter(
				getActivity(), m_listGroupNames, m_mapGroupCollection);
		m_explsvMenu.setAdapter(expListAdapter);
		m_explsvMenu
				.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
					@Override
					public boolean onChildClick(
							ExpandableListView expandableListView, View view,
							int groupPosition, int childPosition, long l) {
						int navIndex = 0;
						for (int i = 0; i < groupPosition; i++) {
							navIndex += m_mapGroupCollection.get(
									m_listGroupNames.get(i)).size();
						}
						navIndex += childPosition;
						m_listenerOnMenuItemClick.onGroupMenuItemClick(Nav
								.values()[navIndex]);
						return true;
					}
				});

		final SlidingMenuListAdapter listAdapter = new SlidingMenuListAdapter(
				getActivity(), m_listDataOfListItem, R.layout.sm_list_item,
				m_strFrom, m_nTo);
		m_lsvMenu.setAdapter(listAdapter);
		m_lsvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onDetach()
	 */
	@Override
	public void onDetach() {
		super.onDetach();
		m_listenerOnMenuItemClick = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnGroupMenuItemClickListener {
		public void onGroupMenuItemClick(Nav item);
	}

	/**
	 * 初始化导航常量
	 */
	private void initConstants() {
		for (NavGroup group : NavGroup.values()) {
			m_listGroupNames.add(group.getName());
		}

		for (int i = 0; i < m_listGroupNames.size(); i++) {
			List<String> tempList = new ArrayList<String>();
			for (Nav nav : Nav.values()) {
				if (nav.getIndex() == i) {
					tempList.add(nav.getName());
				}
			}
			m_mapGroupCollection.put(m_listGroupNames.get(i), tempList);
		}

		m_strFrom = new String[] { "menu_name" };
		m_nTo = new int[] { R.id.list_nav };
		List<String> listMenu = new ArrayList<String>();

		for (Nav nav : Nav.values()) {
			if (nav.getIndex() >= m_listGroupNames.size()) {
				listMenu.add(nav.getName());
			}
		}

		for (int i = 0; i < listMenu.size(); i++) {
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put(m_strFrom[0], listMenu.get(i));
			m_listDataOfListItem.add(tempMap);
		}

	}
}
