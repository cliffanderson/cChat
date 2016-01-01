package com.cliff777.net;

import com.cliff777.chat.obj.ChatWindow;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
	/*
	 * Reads input from the server, and prints it to the console
	 */
public class Receiver extends Thread
{

	private ClientConnection connection;
	private BufferedReader in;

	public Receiver(ClientConnection connection)
    {
		this.connection = connection;

		try
        {
			this.in = new BufferedReader(new InputStreamReader(this.connection.getSocket().getInputStream()));
		}
        catch (IOException e)
        {
			JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
			e.printStackTrace();
		}

		this.start();
	}
	
	@Override
	public void run()
    {
		
		while(this.connection.isRunning())
        {
			String line = null;
			
			try
            {
				line = in.readLine();

			}
            catch (IOException e)
            {
				//most likely, connection was lost
				ChatWindow.addMessageToWindow("ERROR: Lost connection to the server");
				JOptionPane.showMessageDialog(null, "ERROR: Lost connection to the server: " + e.getMessage());
				this.connection.terminateConnection();
				return;
			}
			
			ChatWindow.addMessageToWindow(line);
		}
	}

	public void terminateConnection()
	{
		try
		{
            System.out.println("stopping inoput....");
            this.in.close();
			System.out.println("receiver input has been closed");
		}
		catch (IOException e)
		{
			System.err.println("Error terminating receiver connection: ");
			e.printStackTrace();
		}
	}

}