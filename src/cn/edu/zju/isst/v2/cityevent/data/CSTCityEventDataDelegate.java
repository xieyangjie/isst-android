package cn.edu.zju.isst.v2.cityevent.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import cn.edu.zju.isst.v2.archive.data.CSTCityEvent;
import cn.edu.zju.isst.v2.archive.data.CSTPublisher;
import cn.edu.zju.isst.v2.db.Tools;

/**
 * Created by lqynydyxf on 2014/8/7.
 */
public class CSTCityEventDataDelegate {
    public static CSTCityEvent getCityevent(Cursor cursor) {
        CSTCityEvent cityevent = new CSTCityEvent();
        cityevent.id = cursor.getInt(cursor.getColumnIndex(CSTCityEventProvider.Columns.ID.key));
        cityevent.title = cursor.getString(cursor.getColumnIndex(CSTCityEventProvider.Columns.TITLE.key));
        cityevent.imgUrl = cursor.getString(cursor.getColumnIndex(CSTCityEventProvider.Columns.IMGURL.key));
        cityevent.cityId = cursor.getInt(cursor.getColumnIndex(CSTCityEventProvider.Columns.CITYID.key));
        cityevent.location = cursor.getString(cursor.getColumnIndex(CSTCityEventProvider.Columns.LOCATION.key));
        cityevent.startTime = cursor.getLong(cursor.getColumnIndex(CSTCityEventProvider.Columns.STARTTIME.key));
        cityevent.expireTime = cursor.getLong(cursor.getColumnIndex(CSTCityEventProvider.Columns.EXPIRETIME.key));
        cityevent.updatedAt = cursor.getLong(cursor.getColumnIndex(CSTCityEventProvider.Columns.UPDATEAT.key));
        cityevent.content = cursor.getString(cursor.getColumnIndex(CSTCityEventProvider.Columns.CONTENT.key));
        cityevent.isParticipate = cursor.getInt(cursor.getColumnIndex(CSTCityEventProvider.Columns.ISPARTICIPATE.key)) == 1 ? true : false;
        cityevent.publisher = (CSTPublisher) Tools.antiserialize(cursor.getBlob(cursor.getColumnIndex(CSTCityEventProvider.Columns.PUBLISHER.key)));
        return cityevent;
    }

    public static CSTCityEvent getCity(Context context, String id) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver
                .query(CSTCityEventProvider.CONTENT_URI, null, CSTCityEventProvider.Columns.ID.key + " = ?",
                        new String[]{
                                id
                        }, null
                );
        if (cursor != null && cursor.moveToFirst()) {
            try {
                return getCityevent(cursor);
            } finally {
                cursor.close();
            }
        }
        return null;//Should throw exception to avoid null pointer?
    }

    public static void saveCityevent(Context context, CSTCityEvent cityevent) {
        ContentResolver resolver = context.getContentResolver();
        resolver.insert(CSTCityEventProvider.CONTENT_URI, getCityValue(cityevent));
    }

    public static void deleteAllCityevent(Context context) {
        ContentResolver resolver = context.getContentResolver();
        resolver.delete(CSTCityEventProvider.CONTENT_URI, null, null);
    }

    private static ContentValues getCityValue(CSTCityEvent cityevent) {
        ContentValues values = new ContentValues();
        values.put(CSTCityEventProvider.Columns.ID.key, cityevent.id);
        values.put(CSTCityEventProvider.Columns.TITLE.key, cityevent.title);
        values.put(CSTCityEventProvider.Columns.IMGURL.key, cityevent.imgUrl);
        values.put(CSTCityEventProvider.Columns.CITYID.key, cityevent.cityId);
        values.put(CSTCityEventProvider.Columns.LOCATION.key, cityevent.location);
        values.put(CSTCityEventProvider.Columns.STARTTIME.key, cityevent.startTime);
        values.put(CSTCityEventProvider.Columns.EXPIRETIME.key, cityevent.expireTime);
        values.put(CSTCityEventProvider.Columns.UPDATEAT.key, cityevent.updatedAt);
        values.put(CSTCityEventProvider.Columns.CONTENT.key, cityevent.content);
        values.put(CSTCityEventProvider.Columns.ISPARTICIPATE.key, cityevent.isParticipate == true ? 1 : 0);
        values.put(CSTCityEventProvider.Columns.PUBLISHER.key, Tools.serialize(cityevent.publisher));
        return values;
    }
}
