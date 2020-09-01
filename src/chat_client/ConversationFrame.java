package chat_client;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ConversationFrame {
	
	JFrame frame;
	JTextArea historicArea;
	JTextField chatField;
	JButton sendButton;
	
	private boolean close = false;
	private boolean sendMessage = false;
	private String message = new String();
	
	final String TITLE = "Chat - ";
	final Dimension FRAME_DIMENSION = new Dimension(300, 500);
	
	public ConversationFrame(String userName) {
		frame = new JFrame();
		frame.setTitle(TITLE + userName); 
		frame.setPreferredSize(FRAME_DIMENSION);
		frame.setLayout(new GridBagLayout());
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close = true;
			}
		});
		
		historicArea = new JTextArea();
		chatField = new JTextField();
		sendButton = new JButton("SEND");
		
		sendButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Send the message
				message = chatField.getText();
				if(!message.isEmpty())
					chatField.setText("");
					sendMessage = true;
			}
		});
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		frame.add(historicArea, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.8;
		gbc.weighty = 0.01;
		frame.add(chatField, gbc);
		
		gbc.gridx = 1;
		gbc.weightx = 0.01;
		frame.add(sendButton, gbc);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	protected boolean shouldSendMessage() {
		return sendMessage;
	}
	
	protected boolean shouldClose() {
		return close;
	}
	
	protected String getMessage() {
		sendMessage = false;
		return message;
	}
	
	protected void addMessage(String message) {
		historicArea.append(message + "\n");
		System.out.println("Added: " + message);
	}
}
