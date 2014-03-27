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

import android.content.Context;
import cn.edu.zju.isst.util.L;

/**
 * @author theasir
 *
 */
public class DataManager {

	public static void syncLogin(User user, Context context){
		writeObjectToDB("user", user, context);
		L.i("Write user!");
	}
	
	public static void writeObjectToDB(String name, Serializable object, Context context){
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
	
	public static Serializable objectFromDB(String name, Context context){
		Serializable object = null;
		try {
			byte[] data = new DBManager(context).get(name);
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ObjectInputStream ois = new ObjectInputStream(bis);
			object = (Serializable) ois.readObject();
		} catch (IOException e) {
			// TODO: handle exception
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return object;
	}
	
}
