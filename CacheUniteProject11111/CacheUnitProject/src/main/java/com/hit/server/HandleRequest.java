package com.hit.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.DataModel;
import com.hit.services.CacheUnitController;

public class HandleRequest<T> extends java.lang.Object implements java.lang.Runnable {

	private String jsonString;
	private String requestHeader;
	private Request<DataModel<T>[]> request;
	private Socket clientSocket;
	private CacheUnitController<T> controller;

	public HandleRequest(java.net.Socket clientSocket, CacheUnitController<T> controller) {
		this.clientSocket = clientSocket;
		this.controller = controller;
		jsonString = new String();
		requestHeader = new String();
	}

	@Override
	public void run() {

		try {

			Scanner reader = new Scanner(new InputStreamReader(this.clientSocket.getInputStream()));
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

			this.jsonString = reader.nextLine();

			java.lang.reflect.Type ref = new TypeToken<Request<DataModel<T>[]>>() {
			}.getType();
			this.request = new Gson().fromJson(jsonString, ref);

			
			try {
				requestHeader = this.request.getHeaders().get("action");
			}catch(Exception e ) {
				System.out.println("nothing was selected..");
			}
			

			
			//list of requests
			switch (requestHeader) {
			
            case "UPDATE":            
            if (controller.update(this.request.getBody()) == true) {
				writer.println("Succeeded !");
				writer.flush();
			} else {
				writer.println("Failed !");
				writer.flush();
			}
                     break;
                     
            case "GET":            	
				DataModel<T>[] data = controller.get(this.request.getBody());
				String getData="";
				boolean failed = false;
				for (int i = 0; i < data.length; i++) {
					getData+=data[i]+"@";
					if (data[i] == null) {
						writer.println("Failed !");
						writer.flush();
						failed = true;
					}
				}
				if (!failed) {
					writer.println("Succeeded !"+"@"+getData);
					writer.flush();
					
				}
				
                     break;
                     
            case "DELETE": 
            	if (controller.delete(this.request.getBody()) == true) {
					writer.println("Succeeded !");
					writer.flush();
				} else {
					writer.println("Failed !");
					writer.flush();
				}
                     break;
                        
            case "STATISTICS":  
            	String s;
				s = controller.getStatistics();
				writer.println(s);
				writer.flush();
                     break;
            default:
            	break;
			}
                     
 
			reader.close();
			writer.close();
			this.clientSocket.close();
		} catch (IOException e) {
			System.out.println("In Handle Request run");
			e.printStackTrace();
		}

	}

}
