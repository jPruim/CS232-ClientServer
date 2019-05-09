/*WorkerServer.java
*Run a client that uses command line args to find host server
*request int n shift and then run
* Created by Jason Pruim, for CS232 @ Calvin College 
* May 2019
*/
import java.net.*;
import java.io.*;
import java.lang.Exception;
import java.lang.Thread;
public class WorkerServer implements Runnable{
		
	Socket client; PrintWriter out; int key;
	BufferedReader in;BufferedReader stdIn;String inputLine;String outputLine;
	public WorkerServer(Socket client){	
	this.client = client;
	}
	public void run(){
		try{
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));	
		}catch(Exception e){System.out.print("Error setting up server\n");System.out.println(e.toString());return;}
		try{
			inputLine = in.readLine();
			key = Integer.parseInt(inputLine);
			if(key<1 ||key>25){throw new NumberFormatException("Invalid Key");}
			out.println(inputLine);
			while ((inputLine = in.readLine()) != null) {
				outputLine = getReturnString(inputLine);
				out.println(outputLine);
			}
		}
    		catch(NumberFormatException nfe){
			out.println("Invalid key: please quit and reboot");
		}catch(Exception e){System.out.println("Server errored");}
	}
	String getReturnString(String userInput){
		String output = "";int n;
		for(int i = 0; i<userInput.length(); i++){
			n = (int) userInput.charAt(i);
			if(n>64 && n<91)//UpperCase
			{
				n-=65;
				n+=key;n= (n % 26 + 26) % 26;
				n+=65;
			}
			else if(n>96 && n<123)//LowerCase
			{
				n-=97;
				n+=key;n= (n % 26 + 26) % 26;					
				n+=97;
			}
			output += (char) n;
		}
		return output;
	}
}		
