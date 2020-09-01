package chat_client;

import java.net.InetSocketAddress;

public class User1 {
	public static void main(String[] args) {
		ChatClient chatClient = new ChatClient("Don Pepito", "localhost", 2524);
		
		while(true) {
			chatClient.update();
		}
	}
}
