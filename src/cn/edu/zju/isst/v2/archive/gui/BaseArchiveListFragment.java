package cn.edu.zju.isst.v2.archive.gui;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.constant.Constants;
import cn.edu.zju.isst.v2.archive.data.ArchiveCategory;
import cn.edu.zju.isst.v2.archive.data.CSTArchive;
import cn.edu.zju.isst.v2.archive.data.CSTArchiveDataDelegate;
import cn.edu.zju.isst.v2.archive.data.CSTArchiveProvider;
import cn.edu.zju.isst.v2.archive.net.ArchiveRequest;
import cn.edu.zju.isst.v2.archive.net.ArchiveResponse;
import cn.edu.zju.isst.v2.gui.CSTBaseFragment;
import cn.edu.zju.isst.v2.net.CSTNetworkEngine;
import cn.edu.zju.isst.v2.net.CSTRequest;
import cn.edu.zju.isst.v2.net.CSTStatusInfo;

/**
 * Created by i308844 on 8/12/14.
 */
public abstract class BaseArchiveListFragment extends CSTBaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    public static final String INTENT_ID = "id";

    public static final int DEFAULT_PAGE_SIZE = 20;

    public static final String ARCHIVE_URL = "/api/archives/categories";

    protected ArchiveCategory mCategory;

    private CSTNetworkEngine mEngine = CSTNetworkEngine.getInstance();

    private ListView mListView;

    private ArchiveListAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Handler mHandler;

    private boolean isLoadMore = false;

    private int mCurrentPage = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_archive_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initComponent(view);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void initComponent(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorScheme(R.color.lightbluetheme_color,
                R.color.lightbluetheme_color_half_alpha, R.color.lightbluetheme_color,
                R.color.lightbluetheme_color_half_alpha);
        mListView = (ListView) view.findViewById(R.id.simple_list);

        bindAdapter();
        setUpListener();
        initHandler();
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        requestData();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return CSTArchiveDataDelegate.getDataCursor(getActivity(),
                null,
                CSTArchiveProvider.Columns.CATEGORY_ID.key + " = ?",
                new String[]{
                        "" + mCategory.id
                },
                CSTArchiveProvider.Columns.UPDATE_TIME.key + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ArchiveDetailActivity.class);
        intent.putExtra(INTENT_ID, ((CSTArchive) view.getTag()).id);
        getActivity().startActivity(intent);
    }

    protected abstract void setCategory(ArchiveCategory category);

    private void bindAdapter() {
        mAdapter = new ArchiveListAdapter(getActivity(), null);
        mListView.setAdapter(mAdapter);
    }

    private void setUpListener() {
        mListView.setOnItemClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constants.STATUS_REQUEST_SUCCESS:

                        break;
                    default:
                        break;
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };
    }

    private void requestData() {
        if (isLoadMore) {
            mCurrentPage++;
        } else {
            mCurrentPage = 1;
        }
        ArchiveResponse archiveResponse = new ArchiveResponse(getActivity(), false) {
            @Override
            public void onResponse(JSONObject response) {
                super.onResponse(response);
                Message msg = mHandler.obtainMessage();
                msg.what = Constants.STATUS_REQUEST_SUCCESS;
                mHandler.sendMessage(msg);
            }

            @Override
            public Object onErrorStatus(CSTStatusInfo statusInfo) {
                return super.onErrorStatus(statusInfo);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
            }
        };
        ArchiveRequest archiveRequest = new ArchiveRequest(CSTRequest.Method.GET,
                ARCHIVE_URL + mCategory.subUrl, null, archiveResponse)
                .setPage(mCurrentPage)
                .setPageSize(DEFAULT_PAGE_SIZE);

        mEngine.requestJson(archiveRequest);
    }
}
