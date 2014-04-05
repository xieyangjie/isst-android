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
	private static final String RESTAURANT_LIST_IN_DB = "restaurantlist";

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

	public static void deleteCurrentUser(Context context) {
		new DBManager(context).delete(USER_IN_DB);
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
	 * 同步新闻列表接口返回数据
	 * 
	 * @param wikiList
	 *            百科列表
	 * @param context
	 *            用于加载DBHelper获取当前数据库
	 */
	public static void syncWikiList(List<Archive> wikiList, Context context) {
		if (!Judgement.isNullOrEmpty(wikiList)) {
			writeObjectToDB(WIKI_LIST_IN_DB, (Serializable) wikiList, context);
			L.i("Write wikilist to DB!");
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
	public static List<Archive> getNewsList(Context context) {
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
	 * 同步在校活动列表接口返回数据
	 * 
	 * @param campusActivityList
	 *            新闻列表
	 * @param context
	 *            用于加载DBHelper获取当前数据库
	 */
	public static void syncCampusActivityList(
			List<CampusActivity> campusActivityList, Context context) {
		if (!Judgement.isNullOrEmpty(campusActivityList)) {
			writeObjectToDB(SCAC_LIST_IN_DB, (Serializable) campusActivityList,
					context);
			L.i("Write campusActivityList to DB!");
		}
	}

	/**
	 * 同步学习列表接口返回数据
	 * 
	 * @param studyList
	 *            学习列表
	 * @param context
	 *            用于加载DBHelper获取当前数据库
	 */
	public static void syncStudysList(List<Archive> studyList, Context context) {
		if (!Judgement.isNullOrEmpty(studyList)) {
			writeObjectToDB(STUD_LIST_IN_DB, (Serializable) studyList, context);
			L.i("Write studyList to DB!");
		}
	}

	/**
	 * 获取当前数据库中的在校活动列表对象
	 * 
	 * @param context
	 *            用于加载DBHelper获取当前数据库
	 * @return 当前数据库中的新闻列表对象
	 */
	@SuppressWarnings("unchecked")
	public static List<CampusActivity> getCampusActivityList(Context context) {
		Object object = objectFromDB(SCAC_LIST_IN_DB, context);
		if (!Judgement.isNullOrEmpty(object)) {
			List<CampusActivity> campusActivityList = null;
			try {
				campusActivityList = (List<CampusActivity>) object;
			} catch (ClassCastException e) {
				// TODO: handle exception
			}
			if (!Judgement.isNullOrEmpty(campusActivityList)) {
				return campusActivityList;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<Archive> getCurrentStudyList(Context context) {
		Object object = objectFromDB(STUD_LIST_IN_DB, context);
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
	 * 获取当前数据库中的百科列表对象
	 * 
	 * @param context
	 *            用于加载DBHelper获取当前数据库
	 * @return 当前数据库中的百科列表对象
	 */
	@SuppressWarnings("unchecked")
	public static List<Archive> getCurrentWikiList(Context context) {
		Object object = objectFromDB(WIKI_LIST_IN_DB, context);
		if (!Judgement.isNullOrEmpty(object)) {
			List<Archive> wikiList = null;
			try {
				wikiList = (List<Archive>) object;
			} catch (ClassCastException e) {
				// TODO: handle exception
			}
			if (!Judgement.isNullOrEmpty(wikiList)) {
				return wikiList;
			}
		}
		return null;
	}

	public static void syncRestaurantList(List<Restaurant> restaurantList,
			Context context) {
		if (!Judgement.isNullOrEmpty(restaurantList)) {
			writeObjectToDB(RESTAURANT_LIST_IN_DB,
					(Serializable) restaurantList, context);
			L.i("Write restaurantlist to DB!");
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Restaurant> getRestaurantList(Context context) {
		Object object = objectFromDB(RESTAURANT_LIST_IN_DB, context);
		if (!Judgement.isNullOrEmpty(object)) {
			List<Restaurant> restaurantList = null;
			try {
				restaurantList = (List<Restaurant>) object;
			} catch (ClassCastException e) {
				// TODO: handle exception
			}
			if (!Judgement.isNullOrEmpty(restaurantList)) {
				return restaurantList;
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
		if (Judgement.isNullOrEmpty(name) || Judgement.isNullOrEmpty(object)) {
			return;
		}
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			L.i("Write object!!!");
			new DBManager(context).insertOrUpdate(name, bos.toByteArray());
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
			if (!Judgement.isNullOrEmpty(data)) {
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
