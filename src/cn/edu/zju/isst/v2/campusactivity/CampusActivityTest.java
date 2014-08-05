package cn.edu.zju.isst.v2.campusactivity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.v2.campusactivity.data.CSTCampusActivity;
import cn.edu.zju.isst.v2.campusactivity.data.CSTCampusActivityDataDelegate;
import cn.edu.zju.isst.v2.campusactivity.data.CSTCampusActivityProvider;

public class CampusActivityTest extends Activity {
    private Button add_campusactivity;
    private Button delete_campusactivity;
    private CSTCampusActivityProvider provider;
    private ListView listView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_test);
        provider = new CSTCampusActivityProvider(getApplicationContext());
        listView = (ListView) findViewById(R.id.listView);
        add_campusactivity = (Button) findViewById(R.id.add);
        add_campusactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CSTCampusActivity ca=new CSTCampusActivity();
                ca.id=1;
                ca.title="足球赛";
                ca.picture="picture";
                ca.description="非常精彩";
                ca.content="太好玩了";
                ca.publishername="cb";
                ca.updatedAt=10;
                ca.startTime=9;
                ca.expireTime=11;
                CSTCampusActivity cb=new CSTCampusActivity();
                cb.id=2;
                cb.title="篮球赛";
                cb.picture="picture";
                cb.description="非常精彩";
                cb.content="太好玩了";
                cb.publishername="cb";
                cb.updatedAt=10;
                cb.startTime=9;
                cb.expireTime=11;
                CSTCampusActivityDataDelegate.saveCampusActivity(getApplicationContext(),ca);
                CSTCampusActivityDataDelegate.saveCampusActivity(getApplicationContext(),cb);
                show();
            }
        });
        delete_campusactivity=(Button)findViewById(R.id.delete);
        delete_campusactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CSTCampusActivityDataDelegate.deleteAllCampusActivity(getApplicationContext());
                show();
            }
        });
    }

    public void show() {
        ArrayList<CSTCampusActivity> campusactivities = new ArrayList<CSTCampusActivity>();
        Cursor cursor = getContentResolver().query(CSTCampusActivityProvider.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            CSTCampusActivity campusactivitydemo = CSTCampusActivityDataDelegate.getCampusActivity(cursor);
            campusactivities.add(campusactivitydemo);
        }
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (CSTCampusActivity ca : campusactivities) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("title", ca.description);
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_1,
                new String[]{"title"}, new int[]{android.R.id.text1});
        listView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.campus_activity_test, menu);
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
