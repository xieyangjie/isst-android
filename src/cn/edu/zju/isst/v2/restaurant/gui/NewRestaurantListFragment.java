package cn.edu.zju.isst.v2.restaurant.gui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.v2.data.CSTJsonParser;
import cn.edu.zju.isst.v2.data.CSTRestaurant;
import cn.edu.zju.isst.v2.net.CSTJsonRequest;
import cn.edu.zju.isst.v2.net.CSTNetworkEngine;
import cn.edu.zju.isst.v2.net.CSTRequest;
import cn.edu.zju.isst.v2.restaurant.data.CSTRestaurantDataDelegate;
import cn.edu.zju.isst.v2.restaurant.net.RestaurantResponse;


public class NewRestaurantListFragment extends ListFragment {

    private static final String RESTAURANT_URL = "/api/restaurants";

    private CSTNetworkEngine mEngine = CSTNetworkEngine.getInstance();

    private final List<CSTRestaurant> m_listRestaurant = new ArrayList<CSTRestaurant>();

    private ListView list;

    private ArrayList<String> names = new ArrayList<>();

    public NewRestaurantListFragment() {
    }

    public static NewRestaurantListFragment getInstance() {
        return new NewRestaurantListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.restaurant_list_fragment, null);
        list = (ListView) rootView.findViewById(android.R.id.list);
        requestRestaurantInfo();
        list.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                names));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(getActivity(), NewRestaurantDetailActivity.class);
        i.putExtra("id", m_listRestaurant.get(position).id);
        startActivity(i);
    }

    private void requestRestaurantInfo() {
        RestaurantResponse resResponse = new RestaurantResponse(getActivity()) {
            @Override
            public void onResponse(JSONObject response) {
                CSTRestaurant restaurant = (CSTRestaurant) CSTJsonParser
                        .parseJson(response, new CSTRestaurant());
                for(CSTRestaurant res:restaurant.itemList){
                    names.add(res.name);
                }
            }
        };
        CSTJsonRequest resRequest = new CSTJsonRequest(CSTRequest.Method.GET, RESTAURANT_URL, null,
                resResponse);

        mEngine.requestJson(resRequest);
    }
}
