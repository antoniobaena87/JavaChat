package chat_client;

public class User2 {
	public static void main(String[] args) {
		ChatClient chatClient = new ChatClient("Don Jose", "localhost", 2524);
		
		while(true) {
			chatClient.update();
		}
	}
}
