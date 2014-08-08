package cn.edu.zju.isst.v2.publisher;

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
import cn.edu.zju.isst.v2.publisher.data.CSTPublisher;
import cn.edu.zju.isst.v2.publisher.data.CSTPublisherDataDelegate;
import cn.edu.zju.isst.v2.publisher.data.CSTPublisherProvider;

public class PublisherTest extends Activity {
    private Button add_publisher;
    private Button delete_publisher;
    private CSTPublisherProvider provider;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_test);
        provider = new CSTPublisherProvider(getApplicationContext());
        listView = (ListView) findViewById(R.id.listView);
        add_publisher = (Button) findViewById(R.id.add);
        add_publisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CSTPublisher publisher = new CSTPublisher();
                publisher.id = 1;
                publisher.name = "publisher";
                publisher.phoneNum = "154151655";
                publisher.qqNum = "454551154";
                publisher.email = "admin@163.com";
                CSTPublisherDataDelegate.savePublisher(getApplicationContext(), publisher);
                show();
            }
        });
        delete_publisher = (Button) findViewById(R.id.delete);
        delete_publisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CSTPublisherDataDelegate.deleteAllPublisher(getApplicationContext());
                show();
            }
        });
    }

    public void show() {
        ArrayList<CSTPublisher> publishers = new ArrayList<CSTPublisher>();
        Cursor cursor = getContentResolver().query(CSTPublisherProvider.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            CSTPublisher publisherdemo = CSTPublisherDataDelegate.getPublisher(cursor);
            publishers.add(publisherdemo);
        }
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (CSTPublisher publisher : publishers) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", publisher.name);
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_1,
                new String[]{"name"}, new int[]{android.R.id.text1});
        listView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.publisher_test, menu);
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
