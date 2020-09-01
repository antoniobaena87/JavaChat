package chat_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import chat_server.ChatServer;

public class ChatClient{
	
	private boolean runClient = true;
	private String userName = new String();
	
	private Socket clientSocket;
	private BufferedReader in;
	private PrintWriter out;
	
	private ConversationFrame window;
	
	public ChatClient(String userName, String address, int port) {
		
		this.userName = userName;
		window = new ConversationFrame(userName);
		
		try{
			System.out.print("Opening client " + userName + " socket..");
			window.addMessage("#Connecting with server...");
			clientSocket = new Socket(address, port);
			System.out.println(" Socket successfully opened.\n");
		}catch(IOException ex) {
			System.out.println("Error opening client socket: " + ex);
		}
		
		out = null;
		in = null;
		try {
			window.addMessage("# Opening streams...");
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
		window.addMessage("Connected!\n");
	}
	
	public void update() {
		// Check for new messages
		try{
			if(in.ready()){
				String inputLine = in.readLine();
				if(inputLine != null) {
					getMessages(inputLine);
					System.out.println("NEW MESSAGE: " + inputLine);
				}
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		// Send a message, if needed
		if(window.shouldSendMessage()) {
			System.out.print("Sending a message... ");
			out.println(formatMessage());
			System.out.println("Message sent");
		}
		
		if(window.shouldClose() && !window.shouldSendMessage()) {
			close();
		}
	}
	
	private void getMessages(String message) {
		window.addMessage(message);
	}
	
	private String formatMessage() {
		String message = userName + "> " + window.getMessage();
		
		return message;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public Socket getSocket() {
		return clientSocket;
	}
	
	/**
	 * Closes the program.
	 */
	protected void close() {
		try {
			System.out.print("Closing " + userName + " socket...");
			in.close();
			out.close();
			clientSocket.close();
			System.out.println("Socket closed.");
			System.exit(0);
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
