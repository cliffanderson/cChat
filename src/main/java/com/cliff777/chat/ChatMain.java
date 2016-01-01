package com.cliff777.chat;

import com.cliff777.chat.obj.ChatWindow;
import com.cliff777.net.ClientConnection;

import javax.swing.*;

public class ChatMain
{
    private static ClientConnection connection;

	public static void main(String[] args)
	{
        //get name
        String username = "";

        while(username.trim().isEmpty())
        {
            username = JOptionPane.showInputDialog("Please enter your name");
        }

        //get address
        String address = "";
        while(address.trim().isEmpty())
        {
            address = JOptionPane.showInputDialog("Please enter the server address");
        }

        //initialize the connection to the server
        connection = new ClientConnection(address, username);

        //create GUI
        ChatWindow.construct(username);

    }

    public static ClientConnection getConnection()
    {
        return connection;
    }
}