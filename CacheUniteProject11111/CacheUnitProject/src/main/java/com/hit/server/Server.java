package com.hit.server;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import com.hit.services.CacheUnitController;

public class Server extends java.lang.Object implements java.beans.PropertyChangeListener, java.lang.Runnable {

	private CacheUnitController<String> cacheController;
	private boolean stopped;
	private ServerSocket serverSocket;
	private boolean serverConnect = false;

	public Server() {
		cacheController = new CacheUnitController<>();
	}

	public void run() {

		try {
			this.serverSocket = new ServerSocket(12345);
			while (!stopped) {
				Socket clientSocket = this.serverSocket.accept();
				(new Thread(new HandleRequest<String>(clientSocket, cacheController))).run();
			}

		} catch (SocketException e) {
			System.out.println("no socket recieved yet...");
		}

		catch (IOException e) {
			System.out.println("Failed to create a new thread in server...");
			e.printStackTrace();
		}

	}

	public void propertyChange(PropertyChangeEvent arg0) {

		if (arg0.getPropertyName().equals("start")) {
			this.stopped = false;
			if (serverConnect == false) {
				(new Thread(this)).start();
				serverConnect = true;
			} else {
				System.out.println("the server is on..");
			}

		} else if (arg0.getPropertyName().equals("stop")) {
			this.stopped = true;
			try {
				this.serverSocket.close();

			} catch (IOException e) {
				System.out.println("socket closed unsuccessfuly");

			}
		}
	}
}
