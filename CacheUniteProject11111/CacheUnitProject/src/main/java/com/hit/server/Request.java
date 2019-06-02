package com.hit.server;

public class Request<T> extends java.lang.Object implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private java.util.Map<java.lang.String, java.lang.String> headers;
	private T body;
	public Request(java.util.Map<java.lang.String, java.lang.String> headers, T body) {
		this.headers=headers;
		this.body=body;
	}

	public void setHeaders(java.util.Map<java.lang.String, java.lang.String> headers) {
		this.headers=headers;
	}

	public  java.util.Map<java.lang.String, java.lang.String> getHeaders(){
		return this.headers;
	}
	
	public T getBody() {
		return this.body;
	}

	public void setBody(T body) {
		this.body=body;
	}

	@Override
	public java.lang.String toString() {
	
		return "headers:" +this.headers.toString()+ "body:" + this.body.toString();
	}

}
