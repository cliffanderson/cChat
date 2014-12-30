package com.cliff777.chat;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.JOptionPane;

import com.cliff777.chat.listener.KeyEventHandler;
import com.cliff777.net.Client;

public class ChatMain extends Application
{
	public static ChatMain instance;
	
	final static int WIDTH = 800;
	final static int HEIGHT = 600;
	
	public static TextArea chat = new TextArea();
	public static TextField message = new TextField();
	
	private static String name = "";
	
	private static String messageString = "";
		
	@Override
	public void start(Stage stage) throws Exception
	{
		chat.setEditable(false);
		
		BorderPane pane = new BorderPane();
		
		pane.setCenter(chat);
		pane.setBottom(message);
		
		Scene scene = new Scene(pane, 800, 600);
		
		stage.setTitle("'" + name + "' on cChat");
		stage.setScene(scene);
		stage.show();
		
		message.requestFocus();
		
		message.setOnAction(new KeyEventHandler());
		
		
		//configure shutdown
		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
				{
					@Override
					public void handle(WindowEvent arg0) {
						//shutdown connection
						Client.die();
						
						System.exit(0);
					}
				});
	}
	
	public String getMessageBoxText()
	{
		return message.getText();
	}
	
	public void clearMessageBox()
	{
		message.clear();
	}
	
	public void addMessageToScreen(String m)
	{
		messageString = m;
		//chat.setText(chat.getText() + message + "\n");
		Platform.runLater(new Runnable() {
			@Override
			public void run()
			{
				chat.appendText(messageString + "\n");
				chat.setScrollTop(Double.MAX_VALUE);
			}
		});
		
	}
	
	public static void main(String[] args)
	{		
		//get name
		while(name.isEmpty())
		{
			name = JOptionPane.showInputDialog("Please enter your name");
		}
		
		instance = new ChatMain();
		
		//get address
		String address = "";
		while(address.isEmpty())
		{
			address = JOptionPane.showInputDialog("Please enter the server address");
		}
		
		//start server
		new Client(name, address);
		
		//JavaFX
		launch();
	}
}
