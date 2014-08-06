package cn.edu.zju.isst.v2.dummy;

import com.android.volley.Request;

import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.util.CM;
import cn.edu.zju.isst.util.L;
import cn.edu.zju.isst.util.T;
import cn.edu.zju.isst.v2.net.CSTResponse;
import cn.edu.zju.isst.v2.net.VolleyImpl;

/**
 * Created by i308844 on 8/6/14.
 */
public class DummySwipeToRefreshFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener {

    private FrameLayout mRootView;

    private View mLoadingView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Handler mHandler;

    private boolean showLoading;

    private int mCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        FrameLayout rootView = (FrameLayout) inflater
                .inflate(R.layout.frame_container, null, false);
        mLoadingView = inflater.inflate(R.layout.loading, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.swipe_refresh, null);

        rootView.addView(mLoadingView);

        mRootView = rootView;

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout
                .setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light,
                        android.R.color.holo_blue_light, android.R.color.holo_orange_light);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dummy_test_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action1:
                showLoading = toggleLoading(showLoading);
                return true;
            case R.id.action2:
                testRequest();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        CM.showInfo(getActivity(), "Refreshing Complete!");
                    default:
                        break;
                }
            }
        };
    }

    private void testRequest() {

        VolleyImpl.requestJsonObject(Request.Method.GET,
                "http://www.cst.zju.edu.cn/isst/api/android/version", null,
                new CSTResponse<JSONObject>(getActivity().getApplicationContext()) {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //This is an idea bug, refer to http://youtrack.jetbrains.com/issue/IDEA-72835
                        L.i(((Object) DummySwipeToRefreshFragment.this).getClass()
                                + " request result:\n" + jsonObject.toString());

                        mCount++;
                        if (mCount >= 100) {
                            mSwipeRefreshLayout.setRefreshing(false);

                            Message msg = mHandler.obtainMessage();
                            msg.what = 0;
                            mHandler.sendMessage(msg);

                            mCount = 0;
                        }
                    }
                }
        );
    }

    private boolean toggleLoading(boolean show) {
        if (show) {
            mRootView.removeAllViews();
            mRootView.addView(mLoadingView);
        } else {
            mRootView.removeAllViews();
            mRootView.addView(mSwipeRefreshLayout);
        }
        return !show;
    }

    @Override
    public void onRefresh() {
        T.showShort(getActivity(), "Start Refreshing!");
        for (int i = 0; i < 100; i++) {
            testRequest();
        }
    }
}
