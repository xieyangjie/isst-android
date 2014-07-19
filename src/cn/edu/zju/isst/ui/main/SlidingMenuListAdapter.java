/**
 *
 */
package cn.edu.zju.isst.ui.main;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * 侧拉菜单ListAdapter类
 *
 * @author theasir
 *         <p/>
 *         TODO WIP
 */
public class SlidingMenuListAdapter extends SimpleAdapter {

    public SlidingMenuListAdapter(Context context,
                                  List<? extends Map<String, ?>> data, int resource, String[] from,
                                  int[] to) {
        super(context, data, resource, from, to);
        // TODO Auto-generated constructor stub
    }

}
