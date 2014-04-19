/**
 * 
 */
package cn.edu.zju.isst.ui.main;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.util.L;
import cn.edu.zju.isst.constant.Constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 侧拉菜单ExpandableListAdapter类
 * 
 * @author theasir
 * 
 */
public class SlidingMenuExpListAdapter extends BaseExpandableListAdapter {

	private Activity m_actiContext;
	private List<String> m_listGroupNames;
	private Map<String, List<String>> m_mapGroupCollection;
	private SELECTED m_selectedIndex;
	private Vector<Vector<String>> m_expListResourse;

	/**
	 * 
	 */
	public SlidingMenuExpListAdapter(Activity context, List<String> groupNames,
			Map<String, List<String>> groupCollection) {
		this.m_actiContext = context;
		this.m_listGroupNames = groupNames;
		this.m_mapGroupCollection = groupCollection;
		this.m_selectedIndex = new SELECTED();
		this.m_expListResourse = new Vector<Vector<String>>();
		initResourceVector();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroupCount()
	 */
	@Override
	public int getGroupCount() {
		return m_listGroupNames.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		if (!m_mapGroupCollection.containsKey(m_listGroupNames
				.get(groupPosition)))
			return 0;
		return m_mapGroupCollection.get(m_listGroupNames.get(groupPosition))
				.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroup(int)
	 */
	@Override
	public Object getGroup(int groupPosition) {
		return m_listGroupNames.get(groupPosition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChild(int, int)
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return m_mapGroupCollection.get(m_listGroupNames.get(groupPosition))
				.get(childPosition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroupId(int)
	 */
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChildId(int, int)
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#hasStableIds()
	 */
	@Override
	public boolean hasStableIds() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean,
	 * android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String nav = (String) getGroup(groupPosition);

		View tempView = m_actiContext.getLayoutInflater().inflate(
				R.layout.sm_group_item, null);

		TextView mainNav = (TextView) tempView
				.findViewById(R.id.sm_group_item_nav);
		ImageView mainImage = (ImageView) tempView
				.findViewById(R.id.sm_group_item_img);
		mainNav.setTypeface(null, Typeface.BOLD);
		mainNav.setText(nav);
		if (getChildrenCount(groupPosition) > 0) {
			if (isExpanded) {
				mainImage.setImageResource(R.drawable.ic_expand);
			} else {
				mainImage.setImageResource(R.drawable.ic_not_expand);
			}
		} else {
			Drawable drawable = getDrawableSource((m_expListResourse
					.elementAt(groupPosition)).elementAt(0));
			mainImage.setImageDrawable(drawable);
			if (m_selectedIndex.groupIndex == groupPosition) {
				tempView.setBackgroundColor(Color.BLUE);
			}
		}
		return tempView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean,
	 * android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		String nav = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
			convertView = m_actiContext.getLayoutInflater().inflate(
					R.layout.sm_child_item, null);
		}
		TextView subNav = (TextView) convertView
				.findViewById(R.id.sm_child_item_nav);
		ImageView imgView = (ImageView) convertView
				.findViewById(R.id.sm_child_item_img);

		subNav.setText(nav);
		Drawable drawable = getDrawableSource((m_expListResourse
				.elementAt(groupPosition)).elementAt(childPosition));
		imgView.setImageDrawable(drawable);
		if (groupPosition == m_selectedIndex.groupIndex
				&& childPosition == m_selectedIndex.childIndex) {
			convertView.setBackgroundColor(Color.BLUE);
		} else {
			convertView.setBackgroundColor(Color.alpha(Color.BLUE));
		}
		return convertView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public void setGoupIndex(int groupIndex) {
		this.m_selectedIndex.setGroupIndex(groupIndex);
	}

	public void setChildIndex(int childIndex) {
		this.m_selectedIndex.setChildIndex(childIndex);
	}

	/*
	 * 初始化侧拉栏资源二维数组
	 */
	@SuppressLint("UseValueOf")
	private void initResourceVector() {
		// TODO Auto-generated method stub
		int groupIndex = 0, childIndex = 0, groupCount, childCount;
		Vector<String> tempVector = new Vector<String>();
		groupCount = getGroupCount();

		for (groupIndex = 0; groupIndex < groupCount; groupIndex++) {
			childCount = getChildrenCount(groupIndex);
			// childIndex = 0 时而childcount ＝ 0时，也需要加载资源
			for (childIndex = 0; childIndex < childCount || childIndex == 0; childIndex++) {
				String resName = "ic_explist_0_0";// 暂时写死，有了资源后调整"ic_explist_"+groupIndex
													// +"_" + childIndex;
				tempVector.add(resName);
			}
			if (!tempVector.isEmpty()) {
				m_expListResourse.add(tempVector);

			}

		}
	}

	private Drawable getDrawableSource(String resName) {
		Resources resources = m_actiContext.getResources();

		Drawable drawable = resources.getDrawable(resources.getIdentifier(
				resName, "drawable", Constants.PACKAGE_NAME));
		return drawable;
	}

	public class SELECTED {
		private int groupIndex;
		private int childIndex;

		public SELECTED() {
			setGroupIndex(0);
			setChildIndex(-1);
		}

		public int getGroupIndex() {
			return groupIndex;
		}

		public void setGroupIndex(int groupIndex) {
			this.groupIndex = groupIndex;
		}

		public int getChildIndex() {
			return childIndex;
		}

		public void setChildIndex(int childIndex) {
			this.childIndex = childIndex;
		}
	};

}
