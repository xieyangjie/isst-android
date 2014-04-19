/**
 * 
 */
package cn.edu.zju.isst.ui.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import cn.edu.zju.isst.R;
import cn.edu.zju.isst.constant.Nav;
import cn.edu.zju.isst.constant.NavGroup;
import cn.edu.zju.isst.util.L;

/**
 * 侧拉菜单Fragment类
 * 
 * @author theasir
 * 
 */
public class SlidingMenuFragment extends Fragment {

	private List<String> m_listGroupNames = new ArrayList<String>();// 存储所有组名
	private Map<String, List<String>> m_mapGroupCollection = new HashMap<String, List<String>>();// 存储可展开组的数据

	private OnGroupMenuItemClickListener m_listenerOnMenuItemClick;

	private ExpandableListView m_explsvMenu;

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
				.findViewById(R.id.sm_main_group);
		// setScrollViewHeightBasedOnChildren(m_explsvMenu, m_lsvMenu);

		// m_explsvMenu.setGroupIndicator(this.getResources().getDrawable(R.drawable.expandable_listview_selector));
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
				.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

					@Override
					public boolean onGroupClick(ExpandableListView parent,
							View v, int groupPosition, long id) {
						// TODO Auto-generated method stub
						L.i("CarpeDiem", String.valueOf(groupPosition));
						if (m_mapGroupCollection.containsKey(m_listGroupNames
								.get(groupPosition)))
							return false;
						int navIndex = 0, tempSize = 0;
						for (int i = 0; i < groupPosition; i++) {
							if (m_mapGroupCollection.containsKey(// 判断是否为一个可展开的组
									m_listGroupNames.get(i))) {
								tempSize = m_mapGroupCollection.get(
										m_listGroupNames.get(i)).size();
								L.i("CarpeDiem",
										"tempSize=" + String.valueOf(tempSize));
								navIndex += tempSize;
							} else
								navIndex += 1;
						}
						L.i("CarpeDiem",
								"navIndex =" + String.valueOf(navIndex));

						expListAdapter.setGoupIndex(groupPosition);
						expListAdapter.setChildIndex(-1);
						expListAdapter.notifyDataSetChanged();
						m_listenerOnMenuItemClick.onGroupMenuItemClick(Nav
								.values()[navIndex]);

						return false;
					}
				});
		m_explsvMenu
				.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
					@Override
					public boolean onChildClick(
							ExpandableListView expandableListView, View view,
							int groupPosition, int childPosition, long l) {
						int navIndex = 0, tempSize = 0;
						for (int i = 0; i < groupPosition; i++) {
							if (m_mapGroupCollection
									.containsKey(m_listGroupNames.get(i))) {
								tempSize = m_mapGroupCollection.get(
										m_listGroupNames.get(i)).size();
								navIndex += tempSize;
							} else
								navIndex += 1;
						}
						navIndex += childPosition;

						expListAdapter.setGoupIndex(groupPosition);
						expListAdapter.setChildIndex(childPosition);
						expListAdapter.notifyDataSetChanged();
						m_listenerOnMenuItemClick.onGroupMenuItemClick(Nav
								.values()[navIndex]);

						return true;
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
			if (tempList.size() > 1)
				m_mapGroupCollection.put(m_listGroupNames.get(i), tempList);
		}

	}

}
