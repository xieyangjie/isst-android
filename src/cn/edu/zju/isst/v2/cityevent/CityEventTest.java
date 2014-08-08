package cn.edu.zju.isst.v2.cityevent;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.v2.city.data.CSTCityProvider;
import cn.edu.zju.isst.v2.cityevent.data.CSTCityEvent;
import cn.edu.zju.isst.v2.cityevent.data.CSTCityEventDataDelegate;
import cn.edu.zju.isst.v2.cityevent.data.CSTCityEventProvider;
import cn.edu.zju.isst.v2.publisher.data.CSTPublisher;

public class CityEventTest extends Activity {
    private Button add_city_event;
    private Button delete_city_event;
    private CSTCityEventProvider provider;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_test);
        provider = new CSTCityEventProvider(getApplicationContext());
        listView = (ListView) findViewById(R.id.listView);
        add_city_event = (Button) findViewById(R.id.add);
        add_city_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CSTPublisher publisher = new CSTPublisher();
                publisher.id = 1;
                publisher.name = "publisher";
                publisher.phoneNum = "154151655";
                publisher.qqNum = "454551154";
                publisher.email = "admin@163.com";
                CSTCityEvent cityevent = new CSTCityEvent();
                cityevent.id = 1;
                cityevent.title = "杭城郊游";
                cityevent.imgUrl = "http://adafaafafa.com";
                cityevent.cityId = 1;
                cityevent.location = "杭州西湖";
                cityevent.startTime = 10;
                cityevent.expireTime = 19;
                cityevent.updatedAt = 20;
                cityevent.content = "非常开心";
                cityevent.isParticipate = true;
                cityevent.publisher = publisher;
                CSTCityEventDataDelegate.saveCityevent(getApplicationContext(), cityevent);
                show();
            }
        });
        delete_city_event = (Button) findViewById(R.id.delete);
        delete_city_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CSTCityEventDataDelegate.deleteAllCityevent(getApplicationContext());
                show();
            }
        });
    }

    public void show() {
        ArrayList<CSTCityEvent> cityevents = new ArrayList<CSTCityEvent>();
        Cursor cursor = getContentResolver().query(CSTCityEventProvider.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            CSTCityEvent cityeventdemo = CSTCityEventDataDelegate.getCityevent(cursor);
            cityevents.add(cityeventdemo);
        }
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (CSTCityEvent cityevent : cityevents) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", cityevent.isParticipate==false?"success":"false");
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_1,
                new String[]{"name"}, new int[]{android.R.id.text1});
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.city_event_test, menu);
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
