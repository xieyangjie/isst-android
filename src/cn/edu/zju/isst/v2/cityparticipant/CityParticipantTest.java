package cn.edu.zju.isst.v2.cityparticipant;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.v2.cityparticipant.data.CSTCityParticipant;
import cn.edu.zju.isst.v2.cityparticipant.data.CSTCityParticipantDataDelegate;
import cn.edu.zju.isst.v2.cityparticipant.data.CSTCityParticipantProvider;
import cn.edu.zju.isst.v2.user.data.CSTUser;
import cn.edu.zju.isst.v2.user.data.CSTUserProvider;

public class CityParticipantTest extends Activity {
    private Button add_city_participant;
    private Button delete_city_participant;
    private ListView listView;
    private CSTCityParticipantProvider provider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_test);
        provider = new CSTCityParticipantProvider(getApplicationContext());
        listView = (ListView) findViewById(R.id.listView);
        add_city_participant=(Button)findViewById(R.id.add);
        add_city_participant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CSTCityParticipant cityParticipant=new CSTCityParticipant();
                cityParticipant.id=1;
                cityParticipant.userName="张三";
                cityParticipant.name="李四";
                cityParticipant.grade=98;
                cityParticipant.gender= CSTUser.Gender.MALE;
                cityParticipant.clazzId=1;
                cityParticipant.clazzName="SE";
                cityParticipant.major="leader";
                cityParticipant.email="admin@addmin.com";
                cityParticipant.phoneNum="125161565";
                cityParticipant.qqNum="255511";
                cityParticipant.company="MS";
                cityParticipant.jobTitle="VP";
                cityParticipant.signature="干巴爹";
                cityParticipant.cityId="12";
                cityParticipant.cityName="LA";
                CSTCityParticipant cityParticipant1=new CSTCityParticipant();
                cityParticipant1.id=2;
                cityParticipant1.userName="张三";
                cityParticipant1.name="李四";
                cityParticipant1.grade=98;
                cityParticipant1.gender= CSTUser.Gender.FEMALE;
                cityParticipant1.clazzId=1;
                cityParticipant1.clazzName="SE";
                cityParticipant1.major="leader";
                cityParticipant1.email="admin@addmin.com";
                cityParticipant1.phoneNum="125161565";
                cityParticipant1.qqNum="255511";
                cityParticipant1.company="MS";
                cityParticipant1.jobTitle="VP";
                cityParticipant1.signature="干巴爹";
                cityParticipant1.cityId="12";
                cityParticipant1.cityName="LA";
                CSTCityParticipantDataDelegate.saveCityParticipant(getApplicationContext(), cityParticipant);
                CSTCityParticipantDataDelegate.saveCityParticipant(getApplicationContext(), cityParticipant1);
                show();
            }
        });
        delete_city_participant=(Button)findViewById(R.id.delete);
        delete_city_participant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CSTCityParticipantDataDelegate.deleteAllCityParticipants(getApplicationContext());
                show();
            }
        });
    }
    public void show() {
        ArrayList<CSTCityParticipant> cityParticipants = new ArrayList<CSTCityParticipant>();
        Cursor cursor = getContentResolver().query(CSTCityParticipantProvider.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            CSTCityParticipant cityParticipantdemo = CSTCityParticipantDataDelegate.getCityParticipant(cursor);
            cityParticipants.add(cityParticipantdemo);
        }
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (CSTCityParticipant cityParticipant : cityParticipants) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("sex", cityParticipant.gender.getTypeName());
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_1,
                new String[]{"sex"}, new int[]{android.R.id.text1});
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.city_participant_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
