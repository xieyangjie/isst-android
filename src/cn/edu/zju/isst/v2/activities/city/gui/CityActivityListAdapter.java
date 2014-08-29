package cn.edu.zju.isst.v2.activities.city.gui;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.util.TSUtil;
import cn.edu.zju.isst.v2.activities.city.event.data.CSTCityEvent;
import cn.edu.zju.isst.v2.activities.city.event.data.CSTCityEventDataDelegate;


/**
 * Created by always on 25/08/2014.
 */
public class CityActivityListAdapter extends CursorAdapter {

    public CityActivityListAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.city_activity_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CSTCityEvent cityEvent = CSTCityEventDataDelegate.getCityevent(cursor);
        view.setTag(cityEvent);

        ViewHolder holder = getBindViewHolder(view);
        holder.titleTxv.setText(cityEvent.title);
        holder.updateTimeTxv.setText("发布时间:"
                + TSUtil.toYMD(cityEvent.updatedAt));
        holder.startTimeTxv.setText("开始时间:"
                + TSUtil.toHM(cityEvent.startTime));
        holder.expireTimeTxv.setText("结束时间:"
                + TSUtil.toHM(cityEvent.expireTime));
        holder.publisherTxv.setText("发布者:"
                + cityEvent.publisher.name);
    }

    protected ViewHolder getBindViewHolder(View view) {
        ViewHolder holder = new ViewHolder();
        holder.titleTxv = (TextView) view
                .findViewById(R.id.city_activity_title);
        holder.updateTimeTxv = (TextView) view
                .findViewById(R.id.city_activity_update_time);
        holder.publisherTxv = (TextView) view
                .findViewById(R.id.city_activity_publisher);
        holder.startTimeTxv = (TextView) view
                .findViewById(R.id.city_activity_start_time);
        holder.expireTimeTxv = (TextView) view
                .findViewById(R.id.city_activity_expire_time);
        return holder;
    }

    protected final class ViewHolder {

        public TextView titleTxv;

        public TextView updateTimeTxv;

        public TextView publisherTxv;

        public TextView startTimeTxv;

        public TextView expireTimeTxv;
    }
}
