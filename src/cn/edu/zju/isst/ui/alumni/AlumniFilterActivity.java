/**
 * 
 */
package cn.edu.zju.isst.ui.alumni;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.ui.main.BaseActivity;
import cn.edu.zju.isst.ui.main.MainActivity;
import cn.edu.zju.isst.util.L;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;  
import android.widget.AdapterView.OnItemSelectedListener; 

/**
 * @author yyy
 *
 */
public class AlumniFilterActivity extends BaseActivity {
	
	private String[][] m_AllClassList = {{"1201","1202"},{"1301","1302","1309"}};
	ArrayList<String> m_gradeList;
	ArrayList<String> m_classList;

	//控件
	private Spinner m_spnGrade;
	private Spinner m_spnClass;
	private Button m_btnOK;
	private Button m_btnCancel;

	private ArrayAdapter<String> m_gradeAdapter;
	private ArrayAdapter<String> m_classAdapter;
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
		setContentView(R.layout.alumni_filter_activity);
		
		m_btnOK = (Button)findViewById(R.id.alumni_filter_ok);
		m_btnCancel = (Button)findViewById(R.id.alumni_filter_cancel);
		
		m_gradeList = new ArrayList<String>();
		m_classList = new ArrayList<String>();
		
		//测试数据，待更改
		m_gradeList.add("12");
		m_gradeList.add("13");
		
		m_spnGrade = (Spinner) findViewById(R.id.alumni_filter_grade_spn);
		//将可选内容与ArrayAdapter连接起来  
		m_gradeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m_gradeList);  
		//设置下拉列表的风格  
		m_gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//将adapter 添加到spinner中  
		m_spnGrade.setAdapter(m_gradeAdapter);
		//添加事件Spinner事件监听    
		m_spnGrade.setOnItemSelectedListener(new GradeSpinnerSelectedListener());
		
		m_spnClass = (Spinner) findViewById(R.id.alumni_filter_class_spn);
		//将可选内容与ArrayAdapter连接起来  
		m_classAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m_classList); 
		//设置下拉列表的风格  
		m_classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//将adapter 添加到spinner中  
		m_spnClass.setAdapter(m_classAdapter);
		//添加事件Spinner事件监听    
		m_spnClass.setOnItemSelectedListener(new ClassSpinnerSelectedListener());
		
		m_btnOK.setOnClickListener(new onBtnOkClickListener());
		m_btnCancel.setOnClickListener(new onBtnCancelClickListener());
	}
	
	//使用数组形式操作  
    class GradeSpinnerSelectedListener implements OnItemSelectedListener{  
  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,  
                long arg3) {
        	m_classList.clear();
        	for (int i=0;i<m_AllClassList[arg2].length;i++)
        	{
        		m_classList.add(m_AllClassList[arg2][i]);
        	}
        	m_classAdapter.notifyDataSetChanged();
        }  
  
        public void onNothingSelected(AdapterView<?> arg0) {  
        }  
    }
    
	//使用数组形式操作  
    class ClassSpinnerSelectedListener implements OnItemSelectedListener{  
  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,  
                long arg3) {  
        }  
  
        public void onNothingSelected(AdapterView<?> arg0) {  
        }  
    }
    
    class onBtnOkClickListener implements OnClickListener {
    	@Override
    	public void onClick(View v) {
    		L.i("yyy" + "onBtnOkClickListener");
    		Integer grade = 2012;
    		
            Map<String, Object> dataMap = new ConcurrentHashMap<String, Object>();
            dataMap.put("grade", grade);
//            dataMap.put(, );
            //判断空，我就不判断了。。。。  
         
            Intent data=new Intent();  
            //data.putExtra("name", str_bookname);  
            //data.putExtra("gender", str_booksale);  
//            data.putExtra("grade", 12);  
            data.putExtra("data", (Serializable)dataMap);
            //data.putExtra("majorId", str_booksale);  
            //请求代码可以自己设置，这里设置成20  
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
