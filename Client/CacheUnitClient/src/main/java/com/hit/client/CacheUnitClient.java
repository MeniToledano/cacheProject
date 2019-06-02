package com.hit.client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class CacheUnitClient extends java.lang.Object {
	public CacheUnitClient() {
	}

	public java.lang.String send(java.lang.String request) throws UnknownHostException, IOException {

		String response = "empty response";
		Socket clientSocket = new Socket("127.0.0.1", 12345);

		Scanner reader = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

		writer.println(request);
		writer.flush();
		
		if(reader.hasNext())
		response = reader.nextLine();
		
		reader.close();
		clientSocket.close();
		writer.close();

		return response;

	}
}
