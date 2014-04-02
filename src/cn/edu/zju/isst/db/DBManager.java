/**
 * 
 */
package cn.edu.zju.isst.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.edu.zju.isst.util.L;

/**
 * 数据库管理类
 * 
 * @author theasir
 * 
 */
public class DBManager {

	private static final String MAIN_TABLE = "main";

	private DBHelper helper;
	private SQLiteDatabase db;

	public DBManager(Context context) {

		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
		// 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,
		// mFactory);
		// 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
	}

	/**
	 * 添加表记录
	 * 
	 * @param name
	 *            记录名
	 * @param data
	 *            记录数据
	 */
	public void insertOrUpdate(String name, byte[] data) {
		db.beginTransaction(); // 开始事务
		try {
			Cursor cursor = db.rawQuery("select * from " + MAIN_TABLE
					+ " where name = '" + name + "'", null);
			L.i("cursor count = " + cursor.getCount());
			if (cursor.getCount() == 0) {
				cursor.close();
				L.i("DB add!");
				// String sql = "INSERT INTO main (name, object) VALUES(?,?)";
				// SQLiteStatement insertStatement = db.compileStatement(sql);
				// insertStatement.clearBindings();
				// insertStatement.bindString(0, name);
				// insertStatement.bindBlob(1, data);
				// insertStatement.executeInsert();
				ContentValues cv = new ContentValues();
				cv.put("name", name);
				cv.put("object", data);
				db.insert(MAIN_TABLE, null, cv);
				L.i("DB add success!");
				db.setTransactionSuccessful(); // 设置事务成功完成
			} else {
				cursor.close();
				db.setTransactionSuccessful();
				db.endTransaction();
				update(name, data);
			}
		} finally {
			db.endTransaction(); // 结束事务
			db.close();
		}
	}

	/**
	 * 更新表记录
	 * 
	 * @param name
	 *            记录名
	 * @param data
	 *            记录数据
	 */
	private void update(String name, byte[] data) {
		db.beginTransaction(); // 开始事务
		try {
			L.i("Query for update!");
			ContentValues cv = new ContentValues();
			cv.put("name", name);
			cv.put("object", data);
			db.update(MAIN_TABLE, cv, "name = '" + name + "'", null);
			L.i("DB update success!");
			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			// db.endTransaction(); // 结束事务
		}
	}

	public void delete(String name) {
		db.beginTransaction(); // 开始事务
		try {
			L.i("DB delete!");
			Cursor cursor = db.rawQuery("select * from " + MAIN_TABLE
					+ " where name = '" + name + "'", null);
			L.i("cursor count = " + cursor.getCount());
			if (cursor.getCount() > 0) {
				db.delete(MAIN_TABLE, "name = '" + name + "'", null);
				L.i("DB delete success!");
			}
			cursor.close();
			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			db.endTransaction(); // 结束事务
			db.close();
		}
	}

	/**
	 * 获取表记录
	 * 
	 * @param name
	 *            记录名
	 * @return 记录数据
	 */
	public byte[] get(String name) {
		byte[] data = null;
		db.beginTransaction(); // 开始事务
		try {
			L.i("DB get!");
			Cursor cursor = db.rawQuery("select * from " + MAIN_TABLE
					+ " where name = '" + name + "'", null);
			L.i("cursor count = " + cursor.getCount());
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				data = cursor.getBlob(cursor.getColumnIndex("object"));
				L.i("DB get successful!");
			}
			cursor.close();
			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			db.endTransaction(); // 结束事务
			db.close();
		}
		return data;
	}

	// private void executeSQLScript(SQLiteDatabase database, String scriptName)
	// {
	// ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	// byte buf[] = new byte[1024];
	// int len;
	// AssetManager assetManager = context.getAssets();
	// InputStream inputStream = null;
	//
	// try {
	// inputStream = assetManager.open("sqlscript/" + scriptName);
	// while ((len = inputStream.read(buf)) != -1) {
	// outputStream.write(buf, 0, len);
	// }
	// outputStream.close();
	// inputStream.close();
	// String[] createScript = outputStream.toString().split(";");
	// for (int i = 0; i < createScript.length; i++) {
	// String sqlStatement = createScript[i].trim();
	// // TODO You may want to parse out comments here
	// if (sqlStatement.length() > 0) {
	// database.execSQL(sqlStatement + ";");
	// }
	// }
	// } catch (IOException e) {
	// // TODO Handle Script Failed to Load
	// } catch (SQLException e) {
	// // TODO Handle Script Failed to Execute
	// }
	// }
}
