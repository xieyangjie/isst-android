package cn.edu.zju.isst.v2.archive.gui;

import cn.edu.zju.isst.v2.archive.data.ArchiveCategory;

/**
 * Created by i308844 on 8/25/14.
 */
public class StudyFragment extends BaseArchiveListFragment {

    @Override
    protected void setCategory(ArchiveCategory category) {
        this.mCategory = ArchiveCategory.STUDY;
    }
}
