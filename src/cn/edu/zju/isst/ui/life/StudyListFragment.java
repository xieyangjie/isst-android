/**
 * 
 */
package cn.edu.zju.isst.ui.life;

import cn.edu.zju.isst.api.ArchiveCategory;
import cn.edu.zju.isst.ui.main.BaseArchiveListFragment;

/**
 * 学习园地列表页
 * 
 * @author xyj
 * 
 */
public class StudyListFragment extends BaseArchiveListFragment {

	private static StudyListFragment INSTANCE = new StudyListFragment();

	public StudyListFragment() {
		super();
		super.setArchiveCategory(ArchiveCategory.STUDING);
	}

	public static StudyListFragment getInstance() {
		return INSTANCE;
	}
}