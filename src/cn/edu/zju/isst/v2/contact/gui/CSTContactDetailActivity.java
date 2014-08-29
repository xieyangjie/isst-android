package cn.edu.zju.isst.v2.contact.gui;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import cn.edu.zju.isst.R;
import cn.edu.zju.isst.ui.main.BaseActivity;
import cn.edu.zju.isst.util.Judge;
import cn.edu.zju.isst.v2.city.data.CSTCity;
import cn.edu.zju.isst.v2.contact.data.CSTAlumni;

/**
 * Created by tan on 2014/8/27.
 */
public class CSTContactDetailActivity extends BaseActivity {
    private final static String PRIVATE_INFO = "未公开";

    private final List<CSTCity> m_listCity = new ArrayList<CSTCity>();

    private CSTAlumni mAlumni;
    // 控件
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

    public CSTContactDetailActivity() {
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail_activity);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        // 用户
        mAlumni = (CSTAlumni)getIntent().getExtras().getSerializable("alumni");
        // 控件
        m_tvName = (TextView) findViewById(R.id.contact_detail_activity_name_txv);
        m_tvGender = (TextView) findViewById(R.id.contact_detail_activity_gender_txv);
        m_tvGrade = (TextView) findViewById(R.id.contact_detail_activity_grade_txv);
        m_tvMajor = (TextView) findViewById(R.id.contact_detail_activity_major_txv);
        m_tvMobile = (TextView) findViewById(R.id.contact_detail_activity_mobile_txv);
        m_tvCity = (TextView) findViewById(R.id.contact_detail_activity_city_txv);
        m_tvCompany = (TextView) findViewById(R.id.contact_detail_activity_company_txv);
        m_tvEmail = (TextView) findViewById(R.id.contact_detail_activity_email_txv);
        m_ibtnMobileCall = (ImageButton) findViewById(R.id.contact_detail_activity_mobile_ibtn);
        m_tvPosition = (TextView) findViewById(R.id.contact_detail_activity_position_txv);
        m_ibtnMessage = (ImageButton) findViewById(R.id.contact_detail_activity_message_ibtn);
        m_ibtnEmail = (ImageButton) findViewById(R.id.contact_detail_activity_email_ibtn);

        m_ibtnMobileCall.setOnClickListener(new onMobileCallClickListner());
        m_ibtnMessage.setOnClickListener(new onMessageClickListner());
        m_ibtnEmail.setOnClickListener(new onEmailClickListner());

        // 显示
        showAlumniDetail();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                CSTContactDetailActivity.this.finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * 显示用户详情
     */
    private void showAlumniDetail() {
        if (Judge.isNullOrEmpty(mAlumni)) {
            return;
        }
        // 姓名
        m_tvName.setText(mAlumni.name);
        // 性别
            m_tvGender.setText(mAlumni.gender.getTypeName());
        // 年级
        m_tvGrade.setText("" + mAlumni.grade + "级");
        // 专业
        m_tvMajor.setText(mAlumni.majorName);
        // 电话
        m_tvMobile.setText(mAlumni.phoneNum);
        // Email
        m_tvEmail.setText(mAlumni.email);
        // 城市
        m_tvCity.setText(mAlumni.cityName);
        // 公司
        m_tvCompany.setText(mAlumni.company);
        // 职位
        m_tvPosition.setText(mAlumni.jobTitle);
        if (mAlumni.pvtPhone) {
            m_tvMobile.setText(PRIVATE_INFO);
            m_ibtnMobileCall.setVisibility(View.GONE);
            m_ibtnMessage.setVisibility(View.GONE);
        }
        if (mAlumni.pvtEmail) {
            m_tvEmail.setText(PRIVATE_INFO);
            m_ibtnEmail.setVisibility(View.GONE);
        }
        if (mAlumni.pvtCompany) {
            m_tvCompany.setText(PRIVATE_INFO);
        }
        if (mAlumni.pvtPosition) {
            m_tvPosition.setText(PRIVATE_INFO);
        }
    }

    /**
     * 拨打电话Listner
     *
     * @author yyy
     */
    private class onMobileCallClickListner implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String number = m_tvMobile.getText().toString();
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                    + number));
            startActivity(intent);
        }

    }

    /**
     * 发送短信Listner
     *
     * @author yyy
     */
    private class onMessageClickListner implements View.OnClickListener {

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
     */
    private class onEmailClickListner implements View.OnClickListener {

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
