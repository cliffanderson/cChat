package com.cliff777.chat.listener;

import com.cliff777.chat.ChatMain;
import com.cliff777.net.Client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class KeyEventHandler implements EventHandler<ActionEvent>
{

	@Override
	public void handle(ActionEvent event) {
		//ChatMain.instance.addMessageToScreen(ChatMain.instance.getMessageBoxText());
		//ChatMain.instance.clearMessageBox();
		
		Client.getSender().send(ChatMain.instance.getMessageBoxText());
		ChatMain.instance.clearMessageBox();
	}
	

}
