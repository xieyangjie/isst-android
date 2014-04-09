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
import cn.edu.zju.isst.api.ArchiveCategory;
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
	public static final String USER_IN_DB = "user";
	public static final String NEWS_LIST_IN_DB = "newslist";
	public static final String WIKI_LIST_IN_DB = "wikilist";
	public static final String STUD_LIST_IN_DB = "studlist";
	public static final String SCAC_LIST_IN_DB = "scaclist";
	public static final String RESTAURANT_LIST_IN_DB = "restaurantlist";
	public static final String EXPERIENCE_LIST_IN_DB = "expelist";
	public static final String MAJOR_LIST_IN_DB = "majorlist";
	public static final String CLASS_LIST_IN_DB = "classlist";
	public static final String CITY_LIST_IN_DB = "citylist";
	public static final String CLASSMATE_LIST_IN_DB = "classmatelist";

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

	public static void syncArchiveList(ArchiveCategory archiveCategory,
			List<Archive> newsList, Context context) {
		if (!Judgement.isNullOrEmpty(newsList)) {
			writeObjectToDB(archiveCategory.getNameInDB(),
					(Serializable) newsList, context);
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Archive> getArchiveList(ArchiveCategory archiveCategory,
			Context context) {
		Object object = objectFromDB(archiveCategory.getNameInDB(), context);
		if (!Judgement.isNullOrEmpty(object)) {
			List<Archive> archiveList = null;
			try {
				archiveList = (List<Archive>) object;
			} catch (ClassCastException e) {
				// TODO: handle exception
			}
			if (!Judgement.isNullOrEmpty(archiveList)) {
				return archiveList;
			}
		}
		return null;
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

	public static void syncMajorList(List<Majors> majorList, Context context) {
		if (!Judgement.isNullOrEmpty(majorList)) {
			writeObjectToDB(MAJOR_LIST_IN_DB, (Serializable) majorList, context);
			L.i("Write majorList to DB!");
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Majors> getMajorList(Context context) {
		Object object = objectFromDB(MAJOR_LIST_IN_DB, context);
		if (!Judgement.isNullOrEmpty(object)) {
			List<Majors> majorList = null;
			try {
				majorList = (List<Majors>) object;
			} catch (ClassCastException e) {
				// TODO: handle exception
			}
			if (!Judgement.isNullOrEmpty(majorList)) {
				return majorList;
			}
		}
		return null;
	}

	public static void syncCityList(List<Majors> cityList, Context context) {
		if (!Judgement.isNullOrEmpty(cityList)) {
			writeObjectToDB(CITY_LIST_IN_DB, (Serializable) cityList, context);
			L.i("Write majorList to DB!");
		}
	}

	@SuppressWarnings("unchecked")
	public static List<City> getCityList(Context context) {
		Object object = objectFromDB(CITY_LIST_IN_DB, context);
		if (!Judgement.isNullOrEmpty(object)) {
			List<City> cityList = null;
			try {
				cityList = (List<City>) object;
			} catch (ClassCastException e) {
				// TODO: handle exception
			}
			if (!Judgement.isNullOrEmpty(cityList)) {
				return cityList;
			}
		}
		return null;
	}

	public static void syncClassList(List<Klass> classMateList, Context context) {
		if (!Judgement.isNullOrEmpty(classMateList)) {
			writeObjectToDB(CLASS_LIST_IN_DB, (Serializable) classMateList,
					context);
			L.i("Write majorList to DB!");
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Klass> getClassList(Context context) {
		Object object = objectFromDB(CLASS_LIST_IN_DB, context);
		if (!Judgement.isNullOrEmpty(object)) {
			List<Klass> classMateList = null;
			try {
				classMateList = (List<Klass>) object;
			} catch (ClassCastException e) {
				// TODO: handle exception
			}
			if (!Judgement.isNullOrEmpty(classMateList)) {
				return classMateList;
			}
		}
		return null;
	}

	public static void syncClassMateList(List<User> classList, Context context) {
		if (!Judgement.isNullOrEmpty(classList)) {
			writeObjectToDB(CLASSMATE_LIST_IN_DB, (Serializable) classList,
					context);
			L.i("Write majorList to DB!");
		}
	}

	@SuppressWarnings("unchecked")
	public static List<User> getClassMateList(Context context) {
		Object object = objectFromDB(CLASSMATE_LIST_IN_DB, context);
		if (!Judgement.isNullOrEmpty(object)) {
			List<User> classList = null;
			try {
				classList = (List<User>) object;
			} catch (ClassCastException e) {
				// TODO: handle exception
			}
			if (!Judgement.isNullOrEmpty(classList)) {
				return classList;
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
