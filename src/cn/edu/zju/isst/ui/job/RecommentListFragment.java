/**
 * 
 */
package cn.edu.zju.isst.ui.job;

import cn.edu.zju.isst.api.JobCategory;
import cn.edu.zju.isst.ui.main.BaseJobsListFragment;

/**
 * 新闻列表页
 * 
 * @author theasir
 * 
 */
public class RecommentListFragment extends BaseJobsListFragment {

	private static RecommentListFragment INSTANCE = new RecommentListFragment();

	public RecommentListFragment() {
		super();
		super.setJobCategory(JobCategory.RECOMMEND);
	}

	public static RecommentListFragment getInstance() {
		return INSTANCE;
	}
}