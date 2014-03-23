/**
 * 
 */
package cn.edu.zju.isst.ui.main;

import java.util.List;
import java.util.Map;

import cn.edu.zju.isst.R;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * @author theasir
 *
 */
public class SlidingMenuExpListAdapter extends BaseExpandableListAdapter {

	private Activity m_actiContext;
	private List<String> m_listGroupNames;
	private Map<String, List<String>> m_mapGroupCollection;
	/**
	 * 
	 */
	public SlidingMenuExpListAdapter(Activity context, List<String> groupNames, Map<String, List<String>> groupCollection) {
		this.m_actiContext = context;
		this.m_listGroupNames = groupNames;
		this.m_mapGroupCollection = groupCollection;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroupCount()
	 */
	@Override
	public int getGroupCount() {
		return m_listGroupNames.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		return m_mapGroupCollection.get(m_listGroupNames.get(groupPosition)).size();
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroup(int)
	 */
	@Override
	public Object getGroup(int groupPosition) {
		return m_listGroupNames.get(groupPosition);
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChild(int, int)
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return m_mapGroupCollection.get(m_listGroupNames.get(groupPosition)).get(childPosition);
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroupId(int)
	 */
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChildId(int, int)
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#hasStableIds()
	 */
	@Override
	public boolean hasStableIds() {
		return true;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
        String nav = (String)getGroup(groupPosition);
        if (convertView == null) {
        	convertView = m_actiContext.getLayoutInflater().inflate(R.layout.sm_group_item, null);
        }
        TextView mainNav = (TextView)convertView.findViewById(R.id.group_nav);
        mainNav.setTypeface(null, Typeface.BOLD);
        mainNav.setText(nav);
        return convertView;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
        String nav = (String)getChild(groupPosition, childPosition);
        if (convertView == null) {
        	convertView = m_actiContext.getLayoutInflater().inflate(R.layout.sm_child_item, null);
        }
        TextView subNav = (TextView)convertView.findViewById(R.id.child_nav);
        subNav.setText(nav);
        return convertView;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
