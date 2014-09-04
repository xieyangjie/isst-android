package cn.edu.zju.isst.v2.event.city.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import cn.edu.zju.isst.v2.db.util.CSTSerialUtil;
import cn.edu.zju.isst.v2.user.data.CSTUser;
import cn.edu.zju.isst.v2.user.data.CSTUserProvider;

/**
 * Created by lqynydyxf on 2014/8/6.
 */
public class CSTCityDataDelegate {

    public static CSTCity getCity(Cursor cursor) {
        CSTCity city = new CSTCity();
        city.id = cursor.getInt(cursor.getColumnIndex(CSTUserProvider.Columns.ID.key));
        city.name = cursor.getString(cursor.getColumnIndex(CSTCityProvider.Columns.NAME.key));
        city.cityMaster = (CSTUser) CSTSerialUtil.deserialize(
                cursor.getBlob(cursor.getColumnIndex(CSTCityProvider.Columns.CITY_MASTER.key)));
        return city;
    }

    public static CSTCity getCity(Context context, String id) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver
                .query(CSTCityProvider.CONTENT_URI, null, CSTCityProvider.Columns.ID.key + " = ?",
                        new String[]{
                                id
                        }, null
                );
        if (cursor != null && cursor.moveToFirst()) {
            try {
                return getCity(cursor);
            } finally {
                cursor.close();
            }
        }
        return null;//Should throw exception to avoid null pointer?
    }

    public static void saveCampusActivity(Context context, CSTCity city) {
        ContentResolver resolver = context.getContentResolver();
        resolver.insert(CSTCityProvider.CONTENT_URI, getCityValue(city));
    }

    public static void deleteAllCity(Context context) {
        ContentResolver resolver = context.getContentResolver();
        resolver.delete(CSTCityProvider.CONTENT_URI, null, null);
    }

    private static ContentValues getCityValue(CSTCity city) {
        ContentValues values = new ContentValues();
        values.put(CSTCityProvider.Columns.ID.key, city.id);
        values.put(CSTCityProvider.Columns.NAME.key, city.name);
        values.put(CSTCityProvider.Columns.CITY_MASTER.key,
                CSTSerialUtil.serialize(city.cityMaster));
        return values;
    }
}