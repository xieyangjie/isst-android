package cn.edu.zju.isst.v2.campus.event.data.gui;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.util.TSUtil;
import cn.edu.zju.isst.v2.archive.data.CSTArchive;
import cn.edu.zju.isst.v2.archive.data.CSTArchiveDataDelegate;
import cn.edu.zju.isst.v2.campus.event.data.data.CSTCampusEvent;
import cn.edu.zju.isst.v2.campus.event.data.data.CSTCampusEventDataDelegate;

/**
 * Created by always on 21/08/2014.
 */
public class CampusActivityListAdapter extends CursorAdapter {

    public CampusActivityListAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.campus_activity_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CSTCampusEvent campusEvent = CSTCampusEventDataDelegate.getCampusEvent(cursor);
        view.setTag(campusEvent);

        ViewHolder holder = getBindViewHolder(view);
        holder.titleTxv.setText(campusEvent.title);
        holder.updateTimeTxv.setText(TSUtil.toYMD(campusEvent.updatedAt));
        holder.startTimeTxv.setText(TSUtil.toHM(campusEvent.startTime));
        holder.expireTimeTxv.setText(TSUtil.toHM(campusEvent.expireTime));
        holder.descriptionTxv.setText(campusEvent.description);
    }

    protected ViewHolder getBindViewHolder(View view) {
        ViewHolder holder = new ViewHolder();
        holder.titleTxv = (TextView) view
                .findViewById(R.id.campus_activity_list_item_title_txv);
        holder.updateTimeTxv = (TextView) view
                .findViewById(R.id.campus_activity_list_item_updatetime_txv);
        holder.startTimeTxv = (TextView) view
                .findViewById(R.id.campus_activity_list_item_starttime_txv);
        holder.expireTimeTxv = (TextView) view
                .findViewById(R.id.campus_activity_list_item_expiretime_txv);
        holder.descriptionTxv = (TextView) view
                .findViewById(R.id.campus_activity_list_item_description_txv);
        holder.indicatorView = (View) view
                .findViewById(R.id.campus_activity_list_item_indicator_view);
        return holder;
    }

    protected final class ViewHolder {

        public TextView titleTxv;

        public TextView updateTimeTxv;

        public TextView startTimeTxv;

        public TextView expireTimeTxv;

        public TextView descriptionTxv;

        public View indicatorView;
    }
}
