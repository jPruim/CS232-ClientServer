/*CCipherThreadedServer.java
*Run a client that uses command line args to find host server
*request int n shift and then run
* Created by Jason Pruim, for CS232 @ Calvin College 
* May 2019
*/

import java.net.*;
import java.io.*;
import java.lang.Exception;
import java.lang.Thread;


public class CCipherThreadedServer implements Runnable{
	Thread runningThread = null;
	Socket client; ServerSocket server; int port;boolean isStopped = false;
	public CCipherThreadedServer(int newport){
		port = newport;
	}
	public void run(){
		synchronized(this){runningThread = Thread.currentThread();}
		openServerSocket();
		System.out.println("Server Socket Opened");	
		while(!isStopped){		
			client = null;
			try{
				client = server.accept();
			} catch(IOException e){if(isStopped){System.out.println("Server Stopped");return;}
				throw new RuntimeException("Connection Error");
			}
			new Thread(new WorkerServer(client)).start();
		}
		System.out.println("Server Stopped");
	}
		
	void openServerSocket() {
		try {
		    server = new ServerSocket(port);
		} catch (IOException e) {
		    throw new RuntimeException("Cannot open port", e);
		}
    	}
	public synchronized void stop(){
		this.isStopped = true;
		try {
		 	server.close();
		} catch (IOException e) {
		    	throw new RuntimeException("Error closing server", e);
		}
	}
	public static void main(String[] args){
		int port = Integer.parseInt(args[0]);
		CCipherThreadedServer server = new CCipherThreadedServer(port);
		new Thread(server).start();
		//server.stop();
	}
}


