package cn.edu.zju.isst.v2.user.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by theasir on 7/29/14.
 */
public class CSTUserDataDelegate {

    public static CSTUser getUser(Cursor cursor) {
        CSTUser user = new CSTUser();

        user.id = cursor.getInt(cursor.getColumnIndex(CSTUserProvider.Columns.ID.key));
        user.userName = cursor
                .getString(cursor.getColumnIndex(CSTUserProvider.Columns.USER_NAME.key));
        user.pwd = cursor.getString(cursor.getColumnIndex(CSTUserProvider.Columns.PASSWORD.key));
        user.name = cursor.getString(cursor.getColumnIndex(CSTUserProvider.Columns.NAME.key));
        if (cursor.getString(cursor.getColumnIndex(CSTUserProvider.Columns.GENDER.key)) == "男") {
            user.gender = CSTUser.Gender.MALE;
        } else {
            user.gender = CSTUser.Gender.FEMALE;
        }
        user.grade = cursor.getInt(cursor.getColumnIndex(CSTUserProvider.Columns.GRADE.key));
        user.clazzId = cursor.getInt(cursor.getColumnIndex(CSTUserProvider.Columns.CLASS_ID.key));
        user.clazzName = cursor
                .getString(cursor.getColumnIndex(CSTUserProvider.Columns.CLASS_NAME.key));
        user.majorName = cursor.getString(cursor.getColumnIndex(CSTUserProvider.Columns.MAJOR.key));
        user.cityId = cursor.getInt(cursor.getColumnIndex(CSTUserProvider.Columns.CITY_ID.key));
        user.cityName = cursor
                .getString(cursor.getColumnIndex(CSTUserProvider.Columns.CITY_NAME.key));
        user.email = cursor.getString(cursor.getColumnIndex(CSTUserProvider.Columns.EMAIL.key));
        user.phoneNum = cursor.getString(cursor.getColumnIndex(CSTUserProvider.Columns.PHONE.key));
        user.qqNum = cursor.getString(cursor.getColumnIndex(CSTUserProvider.Columns.QQ.key));
        user.company = cursor.getString(cursor.getColumnIndex(CSTUserProvider.Columns.COMPANY.key));
        user.jobTitle = cursor
                .getString(cursor.getColumnIndex(CSTUserProvider.Columns.JOB_TITLE.key));
        user.sign = cursor.getString(cursor.getColumnIndex(CSTUserProvider.Columns.SIGN.key));
        user.castellan =
                cursor.getInt(cursor.getColumnIndex(CSTUserProvider.Columns.IS_CASTELLAN.key)) != 0;
        user.pvtQq = cursor.getInt(cursor.getColumnIndex(CSTUserProvider.Columns.PVT_QQ.key)) != 0;
        user.pvtEmail = cursor.getInt(cursor.getColumnIndex(CSTUserProvider.Columns.PVT_EMAIL.key))
                != 0;
        user.pvtPhoneNum =
                cursor.getInt(cursor.getColumnIndex(CSTUserProvider.Columns.PVT_PHONE.key)) != 0;
        user.pvtCompany =
                cursor.getInt(cursor.getColumnIndex(CSTUserProvider.Columns.PVT_COMPANY.key)) != 0;
        user.pvtJobTitle =
                cursor.getInt(cursor.getColumnIndex(CSTUserProvider.Columns.PVT_JOB_TITLE.key))
                        != 0;

        return user;
    }

    public static CSTUser getUser(Context context, String id) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver
                .query(CSTUserProvider.CONTENT_URI, null, CSTUserProvider.Columns.ID.key + " = ?",
                        new String[]{
                                id
                        }, null
                );
        if (cursor != null && cursor.moveToFirst()) {
            try {
                return getUser(cursor);
            } finally {
                cursor.close();
            }
        }
        return null;//Should throw exception to avoid null pointer?
    }

    public static void saveUser(Context context, CSTUser user) {
        ContentResolver resolver = context.getContentResolver();
        resolver.insert(CSTUserProvider.CONTENT_URI, getUserValue(user));
    }

    public static void deleteAllUsers(Context context) {
        ContentResolver resolver = context.getContentResolver();
        resolver.delete(CSTUserProvider.CONTENT_URI, null, null);
    }

    private static ContentValues getUserValue(CSTUser user) {
        ContentValues values = new ContentValues();

        values.put(CSTUserProvider.Columns.ID.key, user.id);
        //TODO put all user info into values

        return values;
    }
}
