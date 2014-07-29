package cn.edu.zju.isst.v2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by i308844 on 7/28/14.
 */
public abstract class SimpleTableProviderHelper implements ProviderHelper, BaseColumns {

    protected Context mContext;

    protected SQLiteDatabase mDatabase;

    protected abstract Uri getBaseContentUri();

    protected SimpleTableProviderHelper(Context context) {
        mContext = context;
    }

    @Override
    public void setDBRef(SQLiteDatabase database) {
        mDatabase = database;
    }

    @Override
    public void resetContents(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + getTableName());
        onCreate(db);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        String tableName = getTableName();

        SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
        qBuilder.setTables(tableName);

        Cursor cursor = qBuilder
                .query(mDatabase, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(mContext.getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] valuesArray) {
        String tableName = getTableName();

        mDatabase.beginTransaction();
        try {
            for (ContentValues values : valuesArray) {
                mDatabase.insertOrThrow(tableName, null, values);
            }

            mDatabase.setTransactionSuccessful();
        }
        finally {
            mDatabase.endTransaction();
        }

        notifyChange(uri);

        return valuesArray.length;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String tableName = getTableName();

        long rowId = mDatabase.insertOrThrow(tableName, null, values);
        notifyChange(uri);

        return getBaseContentUri().buildUpon().appendPath(tableName).appendPath(Long.toString(rowId)).build();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String tableName = getTableName();

        int count = mDatabase.delete(tableName, selection, selectionArgs);
        notifyChange(uri);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String tableName = getTableName();

        int count = mDatabase.update(tableName, values, selection, selectionArgs);
        notifyChange(uri);
        return count;
    }

    private void notifyChange(Uri uri) {
        mContext.getContentResolver().notifyChange(uri, null, false);
    }
}
