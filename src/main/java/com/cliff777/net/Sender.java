package com.cliff777.net;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;

	/*
	 * Sends console input from the user to the server
	 */
public class Sender extends Thread {

	private ClientConnection connection;
	private PrintWriter out;
    private String username;

	public Sender(ClientConnection connection, String username) {
		this.connection = connection;

		try {
			out = new PrintWriter(this.connection.getSocket().getOutputStream(), true);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
			e.printStackTrace();
		}

        //first packet must be the user's name
        this.send(username);
	}
	
	public void send(String s)
	{
		if(!s.isEmpty())
			out.println(s);
	}

	public void terminateConnection()
	{
		this.out.close();
	}

}
