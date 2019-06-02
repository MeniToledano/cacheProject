package com.hit.util;

import java.beans.PropertyChangeSupport;
import java.util.Scanner;

public class CLI extends java.lang.Object implements java.lang.Runnable {

	private java.io.InputStream in;
	private java.io.OutputStream out;
	private final PropertyChangeSupport pcs;

	public CLI(java.io.InputStream in, java.io.OutputStream out) {
		this.in = in;
		this.out = out;
		pcs = new PropertyChangeSupport(this);
	}

	public void run() {
		Scanner reader = new Scanner(in);
		System.out.println("Please enter your command: ");
		String command = reader.nextLine();
		
		
		while (!command.equals("close")){
			this.write(command);
			this.pcs.firePropertyChange(command, null, null);
			command = reader.nextLine();
		}
		reader.close();
	}

	public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl) {
		this.pcs.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(java.beans.PropertyChangeListener pcl) {
		this.pcs.removePropertyChangeListener(pcl);
	}

	public void write(java.lang.String string) {
		if (string.equals("start")) {
			System.out.println("Starting server......");
		} else if (string.equals("stop")) {
			System.out.println("Shutdown server");
		} else
			System.out.println("Not a vaild command ");
	}

}
