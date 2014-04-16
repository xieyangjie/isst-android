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
public class InternshipListFragment extends BaseJobsListFragment {

	private static InternshipListFragment INSTANCE = new InternshipListFragment();

	public InternshipListFragment() {
		super();
		super.setJobCategory(JobCategory.INTERNSHIP);
	}

	public static InternshipListFragment getInstance() {
		return INSTANCE;
	}
}