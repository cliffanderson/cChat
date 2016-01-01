package com.cliff777.chat.listener;

import com.cliff777.chat.ChatMain;
import com.cliff777.chat.obj.ChatWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class KeyEventHandler implements EventHandler<ActionEvent>
{
	public void handle(ActionEvent event)
	{
        //send the text in the message box
        ChatMain.getConnection().send(ChatWindow.getMessageBoxContent());
        ChatWindow.clearMessageBox();
	}
}
