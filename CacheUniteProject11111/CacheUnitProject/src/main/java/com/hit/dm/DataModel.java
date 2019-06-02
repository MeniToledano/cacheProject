package com.hit.dm;

public class DataModel<T> extends java.lang.Object implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private T content;
	private java.lang.Long dataModelId;
	


	public DataModel(java.lang.Long id, T content) {
		this.dataModelId=id;
		this.content=content;
	}

	public boolean equals(java.lang.Object obj) {
		if(!(obj instanceof DataModel)) return false;
		if(obj == this) return true;
		
		@SuppressWarnings("unchecked")
		DataModel<T> tmp = (DataModel<T>) obj;
		return this.getDataModelId().equals(tmp.getDataModelId());
	}

	public T getContent() {
		return this.content;

	}

	public java.lang.Long getDataModelId() {
		return this.dataModelId;

	}

	public int hashCode() {
		return this.dataModelId.hashCode();
	}

	void setContent(T content) {
		this.content = content;
	}

	void setDataModelId(java.lang.Long id) {
		this.dataModelId = id;
	}

	public java.lang.String toString() {
		return "ID= " + dataModelId +" content: "+content ;
	}

}
