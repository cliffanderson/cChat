package com.cliff777.clientshell;

import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.cliff777.chat.ChatMain;

public class Receiver extends Thread {
	/*
	 * Reads input from the server, and prints it to the console
	 */
	
	private BufferedReader in;
	
	public Receiver(BufferedReader in) {
		this.in = in;
	}
	
	@Override
	public void run() {
		
		while(Client.running) {
			String line = null;
			
			try {
				line = in.readLine();
				
			} catch (IOException e) {
				//most likely, connection was lost
				ChatMain.instance.addMessageToScreen("ERROR: Lost connection to the server");
				JOptionPane.showMessageDialog(null, "ERROR: Lost connection to the server: " + e.getMessage());
				Client.die();
				return;
			}
			
			ChatMain.instance.addMessageToScreen(line);
		}
	}

}
