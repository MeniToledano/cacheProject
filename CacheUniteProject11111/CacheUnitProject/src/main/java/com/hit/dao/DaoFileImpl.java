package com.hit.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.hit.dm.DataModel;


public class DaoFileImpl<T> extends java.lang.Object implements IDao<java.lang.Long, DataModel<T>> {

	private String filePath;
	private Map<java.lang.Long, DataModel<T>> modelElements = new HashMap<>();
	private Map<java.lang.Long, DataModel<T>> readHM = new HashMap<>();
	private static int capacity = 100;

	public DaoFileImpl(java.lang.String filePath) {
		this.filePath = filePath;
	}

	public DaoFileImpl(java.lang.String filePath, int capacity) {
		this.filePath = filePath;
		this.capacity = capacity;
	}

	@Override
	public void save(DataModel<T> entity) {
		if (entity != null)
			modelElements.put(entity.getDataModelId(), entity);

		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(modelElements);
			oos.flush();
			oos.close();
			fos.close();
			System.out.printf("Serialized HashMap data is saved in the file \n");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public DataModel<T> find(Long id) {
		if (modelElements.containsKey(id))
			return modelElements.get(id);
		return null;
	}

	@Override
	public void delete(DataModel<T> entity) {
		if(this.find(entity.getDataModelId()) !=null )
		modelElements.remove(entity.getDataModelId());
		
		save(null);
	}

	@SuppressWarnings("unchecked")
	public void read() throws ClassNotFoundException, IOException {

		FileInputStream fis = new FileInputStream(filePath);
		ObjectInputStream is = new ObjectInputStream(fis);
		readHM = (Map<Long, DataModel<T>>) is.readObject();
		is.close();
		fis.close();
		if (readHM.isEmpty())
			System.out.println("Empty Map");
		
		for (Long ids : readHM.keySet()) {
			String key = ids.toString();
			String value = readHM.get(ids).toString();
			System.out.println("Key: " + key + " Value: " + value);

		}
	}
}
