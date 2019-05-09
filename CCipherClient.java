/*CCipherClient.java
*Run a client that uses command line args to find host server
*request int n shift and then run
* Created by Jason Pruim, for CS232 @ Calvin College 
* May 2019
*/

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.lang.Exception;


public class CCipherClient {
	Scanner myScanner = new Scanner(System.in);
	Socket client;PrintWriter out;
	BufferedReader in;BufferedReader stdIn;

	public static void main(String[] args){
		CCipherClient c = new CCipherClient();
		c.run(args);
	}
	public void run(String[] args){
		int portNumber = Integer.parseInt(args[1]);
		String hostName = args[0];
		System.out.println("Opening connection to port:"+Integer.toString(portNumber));

		try {
    			client = new Socket(hostName, portNumber);
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			stdIn = new BufferedReader(new InputStreamReader(System.in));
		}catch(Exception e)
		{
			System.out.println("Error occured in initialization of socket");
			System.out.println(e.toString());
			return;
		}		
		// Initiate client
		System.out.print("Enter caesar key:");
		int key = myScanner.nextInt();
		startCaesar(key);
		
		
	}
	void startCaesar(int key){
		//initiallize server with key
		try{out.println(key);System.out.println("echo: " + in.readLine());}catch(Exception e3){System.out.println("Failed to receive confirmation of key");}
		System.out.println("Enter a message to send to server, quit will exit program");
		String userInput;
		while (true) 
		{
			
			try{
				while ((userInput = stdIn.readLine()) != null) {
					if(userInput.equals("quit")) {System.out.println("Quiting program");throw new Exception("Quit called");}
					else{
						//System.out.println("-"+userInput+"-");
	    					out.println(userInput);
	    					System.out.println("echo: " + in.readLine());
					}
				}			
			}catch(Exception e2){try{client.close();}catch(Exception e4){}; break;}
		}
	}
}
