package com.cliff777.net;

import java.io.PrintWriter;
import java.util.Scanner;

public class Sender extends Thread {
	/*
	 * Sends console input from the user to the server
	 */

	private PrintWriter out;
	private String name;

	public Sender(PrintWriter out, String name) {
		this.out = out;
		this.name = name;
	}
	
	public void send(String s)
	{
		out.println(s);
	}

	@Override
	public void run() {

		Scanner console = new Scanner(System.in);
		
		//tell the server the name
		out.println(name);
		out.flush();

		while(Client.running) {
			String line = console.nextLine();

			try
			{
				if(line.equals("*stop")) {
					console.close();
					out.println("*disconnect"); //tell the mainframe we are disconnecting
					out.flush();
					Client.die();
				}else{
					out.println(line);
					out.flush();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		console.close();
	}
}
