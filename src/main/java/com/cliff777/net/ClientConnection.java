package com.cliff777.net;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection
{
	private String address;
	public static int PORT = 12345;

	private boolean running = true;
	private Socket socket = null;

	private Receiver receiver;
	private Sender sender;

	public ClientConnection(String address, String username)
    {
		this.address = address;

		System.out.println("Connecting to " + this.address + " on port " + PORT);

		try {
			socket = new Socket(this.address, PORT);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: Could not connect to the server");

            System.exit(1);
			return;
		} 

		System.out.println("Connected!");

        this.receiver = new Receiver(this);
        this.sender = new Sender(this, username);
	}

	public void terminateConnection()
    {
		try
        {
            this.running = false;
			this.socket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
	}

    public boolean isRunning()
    {
        return this.running;
    }

    public Socket getSocket()
    {
        return this.socket;
    }

    public void send(String s)
    {
        this.sender.send(s);
    }
}