/**
 * 
 */
package cn.edu.zju.isst.ui.usercenter;

import cn.edu.zju.isst.api.JobCategory;
import cn.edu.zju.isst.api.UserCenterCategory;
import cn.edu.zju.isst.ui.main.BaseJobsListFragment;
import cn.edu.zju.isst.ui.main.BaseUserCenterListFragment;

/**我的内推列表
 * 
 * @author xyj
 * 
 */
public class MyRecommendListFragment extends BaseUserCenterListFragment {

	private static MyRecommendListFragment INSTANCE = new MyRecommendListFragment();

	public MyRecommendListFragment() {
		super();
		super.setUserCenterCategory(UserCenterCategory.MYRECOMMEND);
		//super.setJobCategory(JobCategory.INTERNSHIP);
	}

	public static MyRecommendListFragment getInstance() {
		return INSTANCE;
	}
}