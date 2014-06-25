/**
 * 
 */
package cn.edu.zju.isst.ui.city;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.db.City;
import cn.edu.zju.isst.db.DataManager;
import cn.edu.zju.isst.db.User;
import cn.edu.zju.isst.util.J;
import cn.edu.zju.isst.util.L;
import android.R.integer;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ActionBar.OnNavigationListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

/**
 * @author yyy
 * 
 */
public class CastellanFragment extends Fragment {

    private static final String PRIVATE_INFO = "未公开";
    
    private TextView m_tvName;
    private TextView m_tvGender;
    private TextView m_tvGrade;
    private TextView m_tvMajor;
    private TextView m_tvMobile;
    private TextView m_tvEmail;
    private TextView m_tvCity;
    private TextView m_tvCompany;
    private TextView m_tvPosition;
    private ImageButton m_ibtnMobileCall;
    private ImageButton m_ibtnMessage;
    private ImageButton m_ibtnEmail;

    private City m_city;
    private User m_user;
    private final List<City> m_listCity = new ArrayList<City>();
    private ArrayList<String> m_arrayListCity = new ArrayList<String>();
    private static CastellanFragment INSTANCE = new CastellanFragment();

    /**
	 * 
	 */
    public CastellanFragment() {
	// TODO Auto-generated constructor stub
	getCityList();
    }

    public static CastellanFragment GetInstance() {
	return INSTANCE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	SpinnerAdapter adapter = new ArrayAdapter<String>(getActivity(),
		R.layout.action_bar_city_item, m_arrayListCity);
	// 得到ActionBar
	ActionBar actionBar = getActivity().getActionBar();
	// 将ActionBar的操作模型设置为NAVIGATION_MODE_LIST
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	// 为ActionBar设置下拉菜单和监听器
	actionBar.setListNavigationCallbacks(adapter, new DropDownListenser());

	super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	return inflater.inflate(R.layout.castellan_fragment, null);
    }

    @Override
    public void onDestroyView() {
	// 得到ActionBar
	ActionBar actionBar = getActivity().getActionBar();
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
	// 控件
	m_tvName = (TextView) view
		.findViewById(R.id.castellan_fragment_name_txv);
	m_tvGender = (TextView) view
		.findViewById(R.id.castellan_fragment_gender_txv);
	m_tvGrade = (TextView) view
		.findViewById(R.id.castellan_fragment_grade_txv);
	m_tvMajor = (TextView) view
		.findViewById(R.id.castellan_fragment_major_txv);
	m_tvMobile = (TextView) view
		.findViewById(R.id.castellan_fragment_mobile_txv);
	m_tvCity = (TextView) view
		.findViewById(R.id.castellan_fragment_city_txv);
	m_tvCompany = (TextView) view
		.findViewById(R.id.castellan_fragment_company_txv);
	m_tvEmail = (TextView) view
		.findViewById(R.id.castellan_fragment_email_txv);
	m_ibtnMobileCall = (ImageButton) view
		.findViewById(R.id.castellan_fragment_mobile_ibtn);
	m_tvPosition = (TextView) view
		.findViewById(R.id.castellan_fragment_position_txv);
	m_ibtnMessage = (ImageButton) view
		.findViewById(R.id.castellan_fragment_message_ibtn);
	m_ibtnEmail = (ImageButton) view
		.findViewById(R.id.castellan_fragment_email_ibtn);

	m_ibtnMobileCall.setOnClickListener(new onMobileCallClickListner());
	m_ibtnMessage.setOnClickListener(new onMessageClickListner());
	m_ibtnEmail.setOnClickListener(new onEmailClickListner());

	m_city = getCity(DataManager.getCurrentUser().getCityId());

	if (m_city != null) {
	    ActionBar actionBar = getActivity().getActionBar();
	    actionBar
		    .setSelectedNavigationItem(getCityListIndex(m_city.getId()));
	}

	super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 显示用户详情
     */
    private void showUserDetail() {
	if (m_city == null) {
	    return;
	}
	m_user = m_city.getCityMaster();
	if (m_user == null) {
	    return;
	}
	// 姓名
	m_tvName.setText(m_user.getName());
	// 性别
	if (m_user.getGender() > 0) {
	    m_tvGender.setText(m_user.getGender() == 1 ? "男" : "女");
	}
	// 年级
	m_tvGrade.setText("" + 2013 + "级");
	// 专业
	m_tvMajor.setText(m_user.getMajor());
	// 电话
	m_tvMobile.setText(m_user.getPhone());
	// Email
	m_tvEmail.setText(m_user.getEmail());
	// 城市
	m_tvCity.setText(m_city.getName());
	// 公司
	m_tvCompany.setText(m_user.getCompany());
	// 职位
	m_tvPosition.setText(m_user.getPosition());
	if (m_user.isPrivatePhone()) {
	    m_tvMobile.setText(PRIVATE_INFO);
	    m_ibtnMobileCall.setVisibility(View.GONE);
	    m_ibtnMessage.setVisibility(View.GONE);
	}
	if (m_user.isPrivateEmail()) {
	    m_tvEmail.setText(PRIVATE_INFO);
	    m_ibtnEmail.setVisibility(View.GONE);
	}
	if (m_user.isPrivateCompany()) {
	    m_tvCompany.setText(PRIVATE_INFO);
	}
	if (m_user.isPrivatePosition()) {
	    m_tvPosition.setText(PRIVATE_INFO);
	}
    }

    /**
     * 初始化城市列表
     */
    private void getCityList() {
	List<City> dbList = DataManager.getCityList();
	m_arrayListCity.add("城市");
	if (!J.isNullOrEmpty(dbList)) {
	    for (City city : dbList) {
		m_listCity.add(city);
		m_arrayListCity.add(city.getName());
	    }
	}
	L.i(" yyy getCityList");
    }

    /**
     * 按cityID获取city
     * 
     * @param cityID
     * @return
     */
    private City getCity(int cityID) {
	for (City city : m_listCity) {
	    if (city.getId() == cityID) {
		return city;
	    }
	}
	return null;
    }

    private int getCityListIndex(int cityID) {
	int index = 0;
	for (int i = 0; i < m_listCity.size(); i++) {
	    if (m_listCity.get(i).getId() == m_city.getId()) {
		index = i + 1;
	    }
	}
	return index;
    }

    /**
     * 实现 ActionBar.OnNavigationListener接口
     */
    private class DropDownListenser implements OnNavigationListener {

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
	    if (arg0 > 0) {
		m_city = m_listCity.get(arg0 - 1);
		showUserDetail();
	    }
	    return false;
	}
    }

    /**
     * 拨打电话Listner
     * 
     * @author yyy
     * 
     */
    private class onMobileCallClickListner implements OnClickListener {

	@Override
	public void onClick(View v) {
	    String number = m_tvMobile.getText().toString();
	    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
		    + number));
	    startActivity(intent);
	}

    }

    /**
     * 发送短信Listner
     * 
     * @author yyy
     * 
     */
    private class onMessageClickListner implements OnClickListener {

	@Override
	public void onClick(View v) {
	    String number = m_tvMobile.getText().toString();
	    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"
		    + number));
	    startActivity(intent);
	}

    }

    /**
     * 发送邮件listner
     * 
     * @author yyy
     * 
     */
    private class onEmailClickListner implements OnClickListener {

	@Override
	public void onClick(View arg0) {
	    String email = m_tvEmail.getText().toString();
	    Intent intent = new Intent(Intent.ACTION_SENDTO,
		    Uri.parse("mailto:" + email));
	    try {
		startActivity(intent);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

    }
}
