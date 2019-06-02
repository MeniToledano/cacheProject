package com.hit.client;

import java.io.IOException;

import com.hit.view.CacheUnitView;

public class CacheUnitClientObserver
extends java.lang.Object
implements java.beans.PropertyChangeListener {

	private CacheUnitClient client;
	private CacheUnitView cacheView;
	
	public CacheUnitClientObserver(){
		client=new CacheUnitClient();
		
		
	}
	public void propertyChange(java.beans.PropertyChangeEvent event) {
	
		try {
			String response=new String();
			response=client.send(event.getNewValue().toString());
			cacheView=(CacheUnitView)event.getSource();
			cacheView.updateUIData(response);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}

}
