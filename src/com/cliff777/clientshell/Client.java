package com.cliff777.clientshell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client {

	//public static String ADDRESS = "localhost";
	public static String ADDRESS = "69.175.123.210";
	public static int PORT = 12345;
	public static String name;

	public static boolean running = true;

	public static Socket socket = null;
	public static BufferedReader in = null;
	public static PrintWriter out = null;

	private Receiver receiver;
	private static Sender sender;

	public Client(String name, String address) {
		Client.name = name;
		Client.ADDRESS = address;
		
		System.out.println("cChat started...");

		System.out.println("Connecting to " + ADDRESS + " on port " + PORT);

		try {
			socket = new Socket(ADDRESS, PORT);
		} catch (Exception e) {
			System.out.println("Unable to connect");
			return;
		} 

		System.out.println("Connected!");

		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
			e.printStackTrace();
		}

		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
			e.printStackTrace();
		}

		this.receiver = new Receiver(in);
		this.receiver.start();

		Client.sender = new Sender(out, name);
		Client.sender.start();


	}
	
	public static Sender getSender()
	{
		return sender;
	}

	public static void die() {
		System.out.println("Dying");
		try {
			Client.running = false;
			Client.socket.close();
			Client.in.close();
			Client.out.close();
			//System.exit(0);
		}catch(Exception e){}
	}

}
