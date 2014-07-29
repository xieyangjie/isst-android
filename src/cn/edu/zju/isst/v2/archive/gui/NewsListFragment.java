/**
 *
 */
package cn.edu.zju.isst.v2.archive.gui;

import cn.edu.zju.isst.api.ArchiveCategory;

/**
 * 新闻列表页
 *
 * @author theasir
 */
public class NewsListFragment extends BaseArchiveListFragment {

    private static NewsListFragment INSTANCE = new NewsListFragment();

    public NewsListFragment() {
        super();
        super.setArchiveCategory(ArchiveCategory.CAMPUS);
    }

    public static NewsListFragment getInstance() {
        return INSTANCE;
    }
}