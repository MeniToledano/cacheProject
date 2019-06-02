package com.hit.services;

import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

public class CacheUnitController<T> extends java.lang.Object {

	private CacheUnitService<T> cacheUnitSer=new CacheUnitService<T>();   
	
	public CacheUnitController() {
	}

	public boolean update(DataModel<T>[] dataModels) {
		if(cacheUnitSer.update(dataModels))
			return true;
		return false;
	}

	public boolean delete(DataModel<T>[] dataModels) {
		if(cacheUnitSer.delete(dataModels))
			return true;
		return false;
	}

	public DataModel<T>[] get(DataModel<T>[] dataModels) {
		
		return cacheUnitSer.get(dataModels);
	}
	
	public String getStatistics() {
		
		return cacheUnitSer.Statistics();
		
	}

}
