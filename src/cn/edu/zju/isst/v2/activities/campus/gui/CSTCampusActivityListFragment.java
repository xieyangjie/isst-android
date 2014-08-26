package cn.edu.zju.isst.v2.activities.campus.gui;

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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.net.BetterAsyncWebServiceRunner;
import cn.edu.zju.isst.net.NetworkConnection;
import cn.edu.zju.isst.ui.life.CampusActivityDetailActivity;
import cn.edu.zju.isst.util.Judge;
import cn.edu.zju.isst.util.Lgr;
import cn.edu.zju.isst.v2.activities.base.ActivityRequest;
import cn.edu.zju.isst.v2.activities.campus.data.CSTCampusEvent;
import cn.edu.zju.isst.v2.activities.campus.data.CSTCampusEventDataDelegate;
import cn.edu.zju.isst.v2.activities.campus.data.CSTCampusEventProvider;
import cn.edu.zju.isst.v2.activities.campus.net.CampusActivityResponse;
import cn.edu.zju.isst.v2.data.CSTJsonParser;
import cn.edu.zju.isst.v2.gui.CSTBaseFragment;
import cn.edu.zju.isst.v2.net.CSTJsonRequest;
import cn.edu.zju.isst.v2.net.CSTNetworkEngine;
import cn.edu.zju.isst.v2.net.CSTRequest;

import static cn.edu.zju.isst.constant.Constants.NETWORK_NOT_CONNECTED;

/**
 * Created by always on 21/08/2014.
 */
public class CSTCampusActivityListFragment extends CSTBaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    private static CSTCampusActivityListFragment INSTANCE = new CSTCampusActivityListFragment();

    private int mCurrentPage = 1;

    private int DEFAULT_PAGE_SIZE = 20;

    private CSTNetworkEngine mEngine = CSTNetworkEngine.getInstance();

    private static final String CAMPUS_ACTIVITY_URL = "/api/campus/activities";

    private boolean mIsFirstTime;

    private Handler mHandler;

    private ListView mListView;

    private CampusActivityListAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public CSTCampusActivityListFragment() {
        mIsFirstTime = true;
    }

    public static CSTCampusActivityListFragment getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return CSTCampusEventDataDelegate.getDataCursor(getActivity(), null, null, null,
                CSTCampusEventProvider.Columns.UPDATEAT.key + " DESC");
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
        Intent intent = new Intent(getActivity(), CampusActivityDetailActivity.class);
        intent.putExtra("id", ((CSTCampusEvent) view.getTag()).id);
        getActivity().startActivity(intent);
    }

    @Override
    public void onRefresh() {
        try {
            requestData();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void bindAdapter() {
        mAdapter = new CampusActivityListAdapter(getActivity(), null);
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
                    case 0:
                        mSwipeRefreshLayout.setRefreshing(false);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void requestData() throws UnsupportedEncodingException {
        if (NetworkConnection.isNetworkConnected(getActivity())) {
            CampusActivityResponse activityResponse = new CampusActivityResponse(getActivity(),
                    true) {
                @Override
                public void onResponse(JSONObject result) {
                    super.onResponse(result);
                    Lgr.i(result.toString());
                    Message msg = mHandler.obtainMessage();
                    msg.what = 0;
                    mHandler.sendMessage(msg);

                }
            };


            ActivityRequest activityRequest = new ActivityRequest(CSTRequest.Method.GET, CAMPUS_ACTIVITY_URL, null,
                    activityResponse).setPage(mCurrentPage).setPageSize(DEFAULT_PAGE_SIZE);
            mEngine.requestJson(activityRequest);
        } else {
            Message msg = mHandler.obtainMessage();
            msg.what = NETWORK_NOT_CONNECTED;
            mHandler.sendMessage(msg);
        }
    }
}
