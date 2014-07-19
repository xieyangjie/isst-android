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
public class MyExpListFragment extends BaseUserCenterListFragment {

	private static MyExpListFragment INSTANCE = new MyExpListFragment();

	public MyExpListFragment() {
		super();
		super.setUserCenterCategory(UserCenterCategory.MYEXPIENCE);
		//super.setJobCategory(JobCategory.INTERNSHIP);
	}

	public static MyExpListFragment getInstance() {
		return INSTANCE;
	}
}