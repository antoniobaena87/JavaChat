package chat_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import chat_client.ChatClient;

public class ChatServer {
	
	private final int PORT = 2524;
	private ServerSocket serverSocket;
	protected static ArrayList<ClientThread> clientThreads = new ArrayList<>();
	
	private int chatters = 0;
	
	private boolean runServer = true;
	
	public ChatServer() throws IOException{
		System.out.print("Opening server socket...");
		serverSocket = new ServerSocket(PORT);
		System.out.print("Server socket successfully opened.\n");
		
		while(runServer) {
			ClientThread clientThread = new ClientThread(serverSocket.accept());
			clientThreads.add(clientThread);
			clientThread.start();
			System.out.println("New client accepted");
		}
	}
	
	public boolean isRunning() {
		return runServer;
	}
}
