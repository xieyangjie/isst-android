package cn.edu.zju.isst.v2.contact.gui;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import cn.edu.zju.isst.R;
import cn.edu.zju.isst.util.Lgr;
import cn.edu.zju.isst.v2.contact.data.CSTAlumni;
import cn.edu.zju.isst.v2.contact.data.CSTAlumniDataDelegate;
/**
 * Created by i308844 on 8/12/14.
 */
public class ContactListAdapter extends CursorAdapter {

    public ContactListAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Lgr.i("ContactListAdapter", "——newView");
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.contact_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Lgr.i("ContactListAdapter", "——bindView");
        CSTAlumni alumni = CSTAlumniDataDelegate.getAlumni(cursor);
        view.setTag(alumni);
        ViewHolder holder = getBindViewHolder(view);
        holder.nameTxv.setText(alumni.name);
    }

    protected ViewHolder getBindViewHolder(View view) {
        ViewHolder holder = new ViewHolder();
        holder.nameTxv = (TextView) view
                .findViewById(R.id.contact_list_item_name);
        return holder;
    }

    protected final class ViewHolder {
        public TextView nameTxv;
    }
}
