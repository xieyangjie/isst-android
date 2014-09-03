package cn.edu.zju.isst.v2.restaurant.gui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.db.Restaurant;
import cn.edu.zju.isst.db.RestaurantMenu;
import cn.edu.zju.isst.util.Judge;
import cn.edu.zju.isst.v2.data.CSTRestaurant;
import cn.edu.zju.isst.v2.data.CSTRestaurantMenu;
import cn.edu.zju.isst.v2.restaurant.data.CSTRestaurantDataDelegate;

public class NewRestaurantDetailActivity extends Activity {

    private final List<CSTRestaurantMenu> m_listRestaurantMenu = new ArrayList<CSTRestaurantMenu>();

    private int m_nId;

    private CSTRestaurant m_restaurantCurrent;


    private TextView m_txvDescription;

    private TextView m_txvHotline;

    private TextView m_txvAddress;

    private TextView m_txvbusinessHours;

    private ImageButton m_ibtnDial;

    private ListView m_lsvMenu;

    public NewRestaurantDetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_detail_activity);
        initComponent();
        m_nId = getIntent().getIntExtra("id", -1);
        m_restaurantCurrent = CSTRestaurantDataDelegate
                .getRestaurant(getApplicationContext(), Integer.toString(m_nId));
        showRestaurantDetail();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                NewRestaurantDetailActivity.this.finish();
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initComponent() {
        m_txvDescription = (TextView) findViewById(R.id.restaurant_detail_activity_description_txv);
        m_txvHotline = (TextView) findViewById(R.id.restaurant_detail_activity_hotline_txv);
        m_txvAddress=(TextView)findViewById(R.id.restaurant_detail_activity_address_txv);
        m_txvbusinessHours=(TextView)findViewById(R.id.restaurant_detail_activity_business_hours_txv);
        m_ibtnDial = (ImageButton) findViewById(R.id.restaurant_detail_activity_dial_ibtn);
        m_lsvMenu = (ListView) findViewById(R.id.restaurant_detail_activity_menu_lsv);
    }

    private void showRestaurantDetail() {
        setTitle(m_restaurantCurrent.name);

        m_txvDescription.setText(m_restaurantCurrent.description);
        m_txvHotline.setText(m_restaurantCurrent.hotLine);
        m_txvAddress.setText(m_restaurantCurrent.address);
        m_txvbusinessHours.setText(m_restaurantCurrent.businessHours);

        final String dialNumber = m_restaurantCurrent.hotLine;

        m_ibtnDial.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!Judge.isNullOrEmpty(dialNumber)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri
                            .parse("tel://" + dialNumber));
                    NewRestaurantDetailActivity.this.startActivity(intent);
                }

            }
        });
    }
}
