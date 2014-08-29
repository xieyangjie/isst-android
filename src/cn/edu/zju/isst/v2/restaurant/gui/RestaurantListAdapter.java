package cn.edu.zju.isst.v2.restaurant.gui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.util.Judge;
import cn.edu.zju.isst.v2.data.CSTRestaurant;
import cn.edu.zju.isst.v2.restaurant.data.CSTRestaurantDataDelegate;

/**
 * Created by lqynydyxf on 2014/8/28.
 */
public class RestaurantListAdapter extends CursorAdapter {

    public RestaurantListAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.restaurant_list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        CSTRestaurant restaurant = CSTRestaurantDataDelegate.getRestaurant(cursor);
        view.setTag(restaurant);

        final ViewHolder holder = getBindViewHolder(view);
        holder.nameTxv.setText(restaurant.name);
        holder.hotlineTxv.setText(restaurant.hotLine);
        holder.dialIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Judge.isNullOrEmpty(holder.hotlineTxv.getText())) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri
                            .parse("tel://" + holder.hotlineTxv.getText()));
                    context.startActivity(intent);
                }
            }
        });
        //holder.publisherTxv.setText("" + archive.publisherId);
        //holder.descriptionTxv.setText(archive.description);
    }

    protected ViewHolder getBindViewHolder(View view) {
        final ViewHolder holder = new ViewHolder();
        holder.resIcon = (ImageView) view
                .findViewById(R.id.restaurant_list_item_icon_imgv);
        holder.nameTxv = (TextView) view
                .findViewById(R.id.restaurant_list_item_name_txv);
        holder.hotlineTxv = (TextView) view
                .findViewById(R.id.restaurant_list_item_hotline_txv);
        holder.dialIBtn = (ImageButton) view
                .findViewById(R.id.restaurant_list_item_dial_ibtn);
        return holder;
    }

    protected final class ViewHolder {

        public ImageView resIcon;

        public TextView nameTxv;

        public TextView hotlineTxv;

        public ImageButton dialIBtn;
    }
}
