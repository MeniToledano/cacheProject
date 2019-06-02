package com.hit.services;

import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

public class CacheUnitService<T> extends java.lang.Object {

	private CacheUnit<T> cacheUnit;
	private DaoFileImpl<T> iDao;

	private int capacity;
	private String algorithem = "LRU";
	private int totalNumReq;
	private int totalDataModels;
	private int totalSwaps;

	public CacheUnitService() {
		this.totalSwaps = 0;
		this.totalDataModels = 0;
		this.totalNumReq = 0;
		this.capacity = 1;

		this.cacheUnit = new CacheUnit<T>(new LRUAlgoCacheImpl<>(this.capacity));
		this.iDao = new DaoFileImpl<T>("src/DataSource.txt");
	}

	public boolean update(DataModel<T>[] dataModels) {

		this.totalNumReq++;
		this.totalDataModels += dataModels.length;

		DataModel<T>[] swapToIDao = this.cacheUnit.putDataModels(dataModels); // update cache

		for (int i = 0; i < swapToIDao.length; i++) { // update ram
			if (swapToIDao[i] != null) {
				this.iDao.save(swapToIDao[i]);
				this.totalSwaps += 1;
			}
		}

		@SuppressWarnings("unchecked") // test for updates
		DataModel<T>[] test = (DataModel<T>[]) new DataModel[dataModels.length];

		for (int i = 0; i < dataModels.length; i++) {
			java.lang.Long tempIds[] = { dataModels[i].getDataModelId() };
			test[i] = (cacheUnit.getDataModels(tempIds))[0]; // check if dataModel in cache
	
			if (test[i] == null)
				test[i]=iDao.find(dataModels[i].getDataModelId());

			if (!(dataModels[i].equals(test[i])) )
				return false;
		}

		System.out.println("Updated\n");
		return true;
	}

	public boolean delete(DataModel<T>[] dataModels) {

		this.totalNumReq++;
		this.totalDataModels += dataModels.length;

		java.lang.Long[] tempIds = new java.lang.Long[dataModels.length];

		java.lang.Long[] cacheFinder = new java.lang.Long[1];

		for (int i = 0; i < dataModels.length; i++) {// delete from cache
			cacheFinder[0] = dataModels[i].getDataModelId();
			if (this.cacheUnit.getDataModels(cacheFinder) != null)
				cacheUnit.removeDataModels(cacheFinder);
		}

		for (int i = 0; i < dataModels.length; i++) // delete from ram
			iDao.delete(dataModels[i]);

		@SuppressWarnings("unchecked") // test if deleted
		DataModel<T>[] test = (DataModel<T>[]) new DataModel[dataModels.length];

		test = cacheUnit.getDataModels(tempIds); // check if dataModel in cache

		for (int i = 0; i < dataModels.length; i++) {
			if (test[i] == null)
				test[i] = iDao.find(dataModels[i].getDataModelId()); // check if dataModel in RAM
		}

		for (int i = 0; i < dataModels.length; i++) { // if data models deleted,
			if (test[i] != null) // the entire array be equal to null
				return false;
		}
		return true;
	}

	public DataModel<T>[] get(DataModel<T>[] dataModels) {
		this.totalNumReq++;
		this.totalDataModels += dataModels.length;

		@SuppressWarnings("unchecked")
		DataModel<T>[] data = (DataModel<T>[]) new DataModel[dataModels.length];
		java.lang.Long[] tempIds = new java.lang.Long[dataModels.length];

		for (int i = 0; i < dataModels.length; i++)
			tempIds[i] = dataModels[i].getDataModelId();
		

		data = this.cacheUnit.getDataModels(tempIds);

		for (int i = 0; i < dataModels.length; i++) { // if we couldn't trace datamodel from cache
			if (data[i] == null) { // we will try to find it in IDAO
				data[i] = iDao.find(tempIds[i]);
			}
		}
//System.out.println("bla  "+dataModels[0].getContent().toString() );

		return data;
	}

	public String Statistics() {

		String UIStatis;

		UIStatis = "Capacity: " + this.capacity + "@";
		UIStatis += "Algorithem: " + this.algorithem + "@";
		UIStatis += "Total number of requests: " + this.totalNumReq + "@";
		UIStatis += "Total number of DataModels(GET/DELETE/UPDATE requests): " + this.totalDataModels + "@";
		UIStatis += "Total number of DataModel swaps(from Cache to Disk): " + this.totalSwaps + "@";

		return UIStatis;
	}
}
