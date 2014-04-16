package com.dmgkz.mcjobs.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOsaver {

	public static void saveFile(Object obj, String path) throws Exception{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
	
		oos.writeObject(obj);
		oos.flush();
		oos.close();
	}

	public static Object getFile(String path) throws Exception{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
		Object obj = ois.readObject();
		ois.close();
		return obj;
	}

}
