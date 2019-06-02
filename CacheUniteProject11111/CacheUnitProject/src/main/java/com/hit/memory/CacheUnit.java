package com.hit.memory;

import com.hit.algorithm.IAlgoCache;
import com.hit.dm.DataModel;

public class CacheUnit<T> extends java.lang.Object {

	public IAlgoCache<java.lang.Long, DataModel<T>> algo;
	private DataModel<T>[] rSave;

	public CacheUnit(com.hit.algorithm.IAlgoCache<java.lang.Long, DataModel<T>> algo) {
		this.algo = algo;
	}

	@SuppressWarnings("unchecked")
	public DataModel<T>[] getDataModels(java.lang.Long[] ids) {
		
		this.rSave = (DataModel<T>[]) new DataModel[ids.length];
		for (int k = 0; k < ids.length; k++) {
			this.rSave[k] = this.algo.getElement(ids[k]);
		}

		return rSave;
	}

	@SuppressWarnings("unchecked")
	public DataModel<T>[] putDataModels(DataModel<T>[] models) {

		this.rSave = (DataModel<T>[]) new DataModel[models.length];
		for (int k = 0; k < models.length; k++) {
			this.rSave[k] = this.algo.putElement(models[k].getDataModelId(), models[k]);
		}
		return rSave;
	}

	public void removeDataModels(java.lang.Long[] ids) {
		for (int k = 0; k < ids.length; k++) {
			this.algo.removeElement(ids[k]);
		}
	}

}