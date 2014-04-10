/**
 * 
 */
package cn.edu.zju.isst.ui.alumni;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.db.City;
import cn.edu.zju.isst.db.DataManager;
import cn.edu.zju.isst.db.Major;
import cn.edu.zju.isst.ui.main.BaseActivity;
import cn.edu.zju.isst.util.Judgement;
import cn.edu.zju.isst.util.L;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;  
import android.widget.AdapterView.OnItemSelectedListener; 

/**
 * @author yyy
 *
 */
public class AlumniFilterActivity extends BaseActivity {
	
//	private ArrayList<String> m_gradeList;
	private final List<City> m_listCity = new ArrayList<City>();
	private final List<Major> m_listMajor = new ArrayList<Major>();
	private ArrayList<String> m_arrayListCity = new ArrayList<String>();
	private ArrayList<String> m_arrayListMajor = new ArrayList<String>();

	//控件
	private EditText m_edtName;
	private RadioGroup m_rdgGender;
	private EditText m_edtGrade;
	private Spinner m_spnMajor;
	private Spinner m_spnCity;
	private Button m_btnOK;
	private Button m_btnCancel;

	/**
	 * 
	 */
	public AlumniFilterActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		L.i("yyy onCreate" );
		setUpActivity();
		
		//获取控件
		m_edtName = (EditText)findViewById(R.id.alumni_filter_name_edt);
		m_rdgGender = (RadioGroup)findViewById(R.id.alumni_filter_gender_rdg);
		m_edtGrade = (EditText)findViewById(R.id.alumni_filter_grade_edt);
		m_btnOK = (Button)findViewById(R.id.alumni_filter_ok);
		m_btnCancel = (Button)findViewById(R.id.alumni_filter_cancel);
		m_spnMajor = (Spinner) findViewById(R.id.alumni_filter_major_spn);
		m_spnCity = (Spinner) findViewById(R.id.alumni_filter_city_spn);
		
		m_btnOK.setOnClickListener(new onBtnOkClickListener());
		m_btnCancel.setOnClickListener(new onBtnCancelClickListener());
		
		//获取数据库中的城市列表，专业列表
		getCityList();
		getMajorList();
		
		//设置下拉框
		initSpanner(m_spnCity, m_arrayListCity);
		initSpanner(m_spnMajor, m_arrayListMajor);
	}
	
	private void initSpanner(Spinner spanner,ArrayList<String> list) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
		//设置下拉列表的风格 
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//将adapter 添加到spinner中 
		spanner.setAdapter(adapter);
	}
	
	private void getCityList() {
		L.i(" yyy getCityList") ;
		//初始化城市列表
		List<City> dbList = DataManager
				.getCityList(this);
		L.i("yyy citysize" + dbList.size());
		if (!Judgement.isNullOrEmpty(dbList)) {
			m_arrayListCity.add("不限");
			for (City city : dbList) {
				m_listCity.add(city);
				m_arrayListCity.add(city.getName());
			}
		}
	}
	
	private void getMajorList() {
		L.i(" yyy getMajorList") ;
		//初始化专业列表
		List<Major> dbList = DataManager
				.getMajorList(this);
		if (!Judgement.isNullOrEmpty(dbList)) {
			m_arrayListMajor.add("不限");
			for (Major major : dbList) {
				L.i(" yyy getMajorList add size" + dbList.size()) ;
				m_listMajor.add(major);
				m_arrayListMajor.add(major.getName());
			}
		}
		L.i(" yyy getMajorList") ;
	}
	
	private void setUpActivity() {
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
				WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		LayoutParams params = getWindow().getAttributes();
		params.height = 1200; // fixed height
		params.width = LayoutParams.MATCH_PARENT; // fixed width
		params.alpha = 1.0f;
		params.dimAmount = 0.5f;
		getWindow().setAttributes(params);
		setContentView(R.layout.alumni_filter_activity);
	}
    
    class onBtnOkClickListener implements OnClickListener {
    	@Override
    	public void onClick(View v) {
    		L.i("yyy" + "onBtnOkClickListener");
         
            Intent data=new Intent();  
            UserFilter uf = new UserFilter();
            
            //姓名
            String name = m_edtName.getText().toString();
            //获取性别ID的方法可能有其他的，现在不好
            Integer genderId = null;
            String genderString = null;
            int radioBtnId = m_rdgGender.getCheckedRadioButtonId();
            switch(radioBtnId)
            {
            case R.id.alumni_filter_gender_btn0:
	            genderId = 0;
	            genderString = "不限";
	            break;
            case R.id.alumni_filter_gender_btn1:
	            genderId = 1;
	            genderString = "男";
	            break;
            case R.id.alumni_filter_gender_btn2:
	            genderId = 2;
	            genderString = "女";
	            break;
            default:
            	break;
            }
            //城市ID
            Integer cityId = null;
            String cityString = null;
            int selectedCityPosition = m_spnCity.getSelectedItemPosition() -1;
            if (selectedCityPosition > 0) {
            	cityId = (m_listCity.get(selectedCityPosition)).getId();
            	cityString = (m_listCity.get(selectedCityPosition)).getName();
            }
            //专业ID
            Integer majorId = null;
            String majorString = null;
            int selectedmajorPosition = m_spnMajor.getSelectedItemPosition() -1;
            if (selectedmajorPosition > 0) {
            	majorId = (m_listMajor.get(selectedmajorPosition)).getId();
            	majorString = (m_listMajor.get(selectedmajorPosition)).getName();
            }		
            
            //年级Id
            Integer grade ;
            grade = Integer.getInteger( (m_edtGrade.getText().toString()));
            
            L.i("yyy name " + name);
//            uf.name = name!=""?name:null;
            uf.gender = genderId;
            uf.cityId = cityId;
            uf.grade = grade;
            uf.majorId = majorId;
            uf.cityString = cityString;
            uf.genderString = genderString;
            uf.majorString = majorString;
            //
            data.putExtra("data", (Serializable)uf);
            setResult(20, data); 
            
            //关闭掉这个Activity  
            finish();  
    	}
    }

    class onBtnCancelClickListener implements OnClickListener {
    	@Override
    	public void onClick(View v) {
    		AlumniFilterActivity.this.finish();
    	}
    }
}
