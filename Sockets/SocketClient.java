/*
 * This client-side server application was programmed
 * by James Lindsey as a University of North Florida student
 * in the Spring of 2016.
 * It connects with its partner (SocketServer) via sockets on established servers
 */


import java.io.*;
import java.net.*;
import java.util.*;

public class SocketClient {
    public static void main(String[] args) throws IOException {


        String count;
        Socket echoSocket = null;
        PrintWriter output = null;
        BufferedReader input = null;
        
        if(args.length < 1)  // No IP Address, print an error
		{
			System.out.println("Usage: SocketClient <server IP Address>");
			System.exit(0);
		}
        
        String serverIPAdd = new String (args[0]);
        System.out.println ("Attemping to connect to IP Adress: " +
                serverIPAdd + " on port 2358.");

        try {
            echoSocket = new Socket(serverIPAdd, 2358);
            output = new PrintWriter(echoSocket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverIPAdd);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + serverIPAdd);
            System.exit(1);
        }
        
        count = input.readLine();
        
        // Infinite Loop for menu choices
        boolean quit = false;        
        while (quit == false) {

    		System.out.println ("Enter 0-6 for you desired result...");
            System.out.println ("[ 1 ] Host Date/Time");
            System.out.println ("[ 2 ] Host Uptime");
            System.out.println ("[ 3 ] Host Memory Usage");
            System.out.println ("[ 4 ] Host Netstat");
            System.out.println ("[ 5 ] Current Users on Host");
            System.out.println ("[ 6 ] Host Disk Usage");
            System.out.println ("[ 0 ] End Communication");
        	
            // Variables for user input, and the values for response time
    		Scanner inputU = new Scanner(System.in);
    		int userInput = inputU.nextInt();
    		String command;
    		// start timer for response time
    		long start = System.currentTimeMillis();
    		// end timer for response time
    		long end = System.currentTimeMillis();
    		
    		
    		String serverResponse = null;
    		
    		//switch statement handles essentially all of the activity in
    		//individual cases
        	switch (userInput) {
    			case 0: command = "0";
    				System.exit(0);
    				break;
    				
        		case 1: command = "date";
        			start = System.currentTimeMillis();
        			output.println(command);
        			output.println(count);
        			while ((serverResponse = input.readLine()) != null && 
        					!serverResponse.equals("LastLine"))
        			{
        				System.out.println(serverResponse);
        			}
            		end = System.currentTimeMillis();
            		System.out.println("Response Time: " + 
            							(end-start) + " ms");
            		break;
        			
        		case 2: command = "uptime";
    				start = System.currentTimeMillis();
        			output.println(command);
        			output.println(count);
        			while ((serverResponse = input.readLine()) != null && 
        					!serverResponse.equals("LastLine"))
        			{
        				System.out.println(serverResponse);
        			}
        			end = System.currentTimeMillis();
        			System.out.println("Response Time: " + 
        								(end-start) + " ms");
        			break;
        			
        		case 3: command = "free -mo";
    				start = System.currentTimeMillis();	
        			output.println(command);
        			output.println(count);
        			while ((serverResponse = input.readLine()) != null && 
        					!serverResponse.equals("LastLine"))
        			{
        				System.out.println(serverResponse);
        			}
            		end = System.currentTimeMillis();
        			System.out.println("Response Time: " + 
        								(end-start) + " ms");
        			break;
        			
        		case 4: command = "netstat -t";
    				start = System.currentTimeMillis();
        			output.println(command);
        			output.println(count);
        			System.out.println(input.read());
        			while ((serverResponse = input.readLine()) != null && 
        					!serverResponse.equals("LastLine"))
        			{
        				System.out.println(serverResponse);
        			}
            		end = System.currentTimeMillis();
            		System.out.println("Response Time: " + 
            							(end-start) + " ms");
        			break;
        			
        		case 5: command = "users";
    				start = System.currentTimeMillis();
        			output.println(command);
        			output.println(count);
        			while ((serverResponse = input.readLine()) != null && 
        					!serverResponse.equals("LastLine"))
        			{
        				System.out.println(serverResponse);
        			}
            		end = System.currentTimeMillis();
            		System.out.println("Response Time: " + 
            							(end-start) + " ms");
        			break;
        			
        		case 6: command = "df -h";
    				start = System.currentTimeMillis();
        			output.println(command);
        			output.println(count);
        			while ((serverResponse = input.readLine()) != null && 
        					!serverResponse.equals("LastLine"))
        			{
        				System.out.println(serverResponse);
        			}
        			end = System.currentTimeMillis();
            		System.out.println("Response Time: " 
            							+ (end-start) + " ms");
        			break;
        			
        		default: 
        			System.out.println("Unable to resolve your request; "
        								+ "try again.");
        			break;
        		
        	}

        }
	output.close();
	input.close();
	echoSocket.close();
    }
}
