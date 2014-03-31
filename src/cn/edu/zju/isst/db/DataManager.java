/**
 * 
 */
package cn.edu.zju.isst.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import android.content.Context;
import cn.edu.zju.isst.util.Judgement;
import cn.edu.zju.isst.util.L;

/**
 * 数据操作类
 * 
 * @author theasir
 * 
 */
public class DataManager {

	/**
	 * 以下字段为数据库表的记录
	 */
	private static final String USER_IN_DB = "user";
	private static final String NEWS_LIST_IN_DB = "newslist";
	private static final String WIKI_LIST_IN_DB = "wikilist";
	private static final String STUD_LIST_IN_DB = "studlist";
	private static final String SCAC_LIST_IN_DB = "scaclist";

	/**
	 * 同步登录接口返回数据
	 * 
	 * @param user
	 *            用户
	 * @param context
	 *            用于加载DBHelper获取当前数据库
	 */
	public static void syncLogin(User user, Context context) {
		if (user.getId() >= 0 && !user.getUsername().isEmpty()
				&& !user.getPassword().isEmpty()) {// TODO 更好的判断user有效的方法
			writeObjectToDB(USER_IN_DB, user, context);
			L.i("Write user to DB!");
		}
	}

	/**
	 * 获取当前数据库中的用户对象
	 * 
	 * @param context
	 *            用于加载DBHelper获取当前数据库
	 * @return 当前数据库中的用户对象
	 */
	public static User getCurrentUser(Context context) {
		Object object = objectFromDB(USER_IN_DB, context);
		if (!Judgement.isNullOrEmpty(object)) {// TODO better class cast method?
			User user = null;
			try {
				user = (User) object;
			} catch (ClassCastException e) {
				// TODO: handle exception
			}
			if (!Judgement.isNullOrEmpty(user)) {
				return user;
			}
		}
		return null;
	}

	/**
	 * 同步新闻列表接口返回数据
	 * 
	 * @param newsList
	 *            新闻列表
	 * @param context
	 *            用于加载DBHelper获取当前数据库
	 */
	public static void syncNewsList(List<Archive> newsList, Context context) {
		if (!Judgement.isNullOrEmpty(newsList)) {
			writeObjectToDB(NEWS_LIST_IN_DB, (Serializable) newsList, context);
			L.i("Write newslist to DB!");
		}
	}

	/**
	 * 获取当前数据库中的新闻列表对象
	 * 
	 * @param context
	 *            用于加载DBHelper获取当前数据库
	 * @return 当前数据库中的新闻列表对象
	 */
	@SuppressWarnings("unchecked")
	public static List<Archive> getCurrentNewsList(Context context) {
		Object object = objectFromDB(NEWS_LIST_IN_DB, context);
		if (!Judgement.isNullOrEmpty(object)) {
			List<Archive> newsList = null;
			try {
				newsList = (List<Archive>) object;
			} catch (ClassCastException e) {
				// TODO: handle exception
			}
			if (!Judgement.isNullOrEmpty(newsList)) {
				return newsList;
			}
		}
		return null;
	}

	/**
	 * 将目标对象序列化后写入当前数据库
	 * 
	 * @param name
	 *            数据库表记录
	 * @param object
	 *            目标对象
	 * @param context
	 *            用于加载DBHelper获取当前数据库
	 */
	public static void writeObjectToDB(String name, Serializable object,
			Context context) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			L.i("Write object!!!");
			new DBManager(context).add(name, bos.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 将数据库中的对象反序列化并读取出来
	 * 
	 * @param name
	 *            数据库表记录
	 * @param context
	 *            用于加载DBHelper获取当前数据库
	 * @return 目标对象
	 */
	public static Serializable objectFromDB(String name, Context context) {
		Serializable object = null;
		try {
			byte[] data = new DBManager(context).get(name);
			if (data != null && !data.equals(null)) {
				ByteArrayInputStream bis = new ByteArrayInputStream(data);
				ObjectInputStream ois = new ObjectInputStream(bis);
				object = (Serializable) ois.readObject();
			}
		} catch (IOException e) {
			// TODO: handle exception
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return object;
	}

}
