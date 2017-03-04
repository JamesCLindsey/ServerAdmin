/*
 * This Client-side program was created by James Lindsey on April 20, 2016
 * for CNT4504 (Networks/Distributed Processing) for Professor Leas.
 * It, with its sister-program (ServerRMI), creates a server diagnostic menu
 * that can be used remotely from a machine with this program. 
 * They use RMI registry protocols to call the required methods from the server-side application.
 * 
 * Inputs are the menu selection ints 0-6.
 * Outputs are the desired server diagnostics
 * 
 * Known Issue: ServerRMI cannot distinguish between different, mulithreaded clients,
 * 				some excised code remains from those attempts.
 */

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientRMI {
	private ClientRMI() {}

       /* *~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*
     *  *~*~*~*~*~*~*~*~* MAIN *~*~*~*~*~*~*~*~*
     * *~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*
     * 
     * The main  handles the verification of the menu and switch statement..
     * The server handles the bulk of the work.
     */
     
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int userInput = 0;
		long start;
		String ipAdd;
		String portNum;
		//String counter = null;
		//int count =0;
		
	
		
		try 
      {
			//count++;
			
			if(args.length < 2)
			{
				System.out.println("Usage: CNTClient <server IP Address> <port>\n");
				System.exit(0);
			}
			
			ipAdd = args[0];
			portNum = args[1];
         
          /*This method directly connects to the server without 
          *prompting the user for the IP address
          *getRegistry is the method that will able to connect 
          *to the port address and the IP address
          */			
			Registry registry = LocateRegistry.getRegistry(ipAdd);
			serverStub stub = (serverStub)registry.lookup("Instance");
			
			while(userInput != 0) 
         {
				
            /*
             * This while-loop continues to present the following menu until
             * user enters a 0, selecting the End Communication" option.
             */
				
				System.out.println ("Enter 0-6 for you desired result...");
	            System.out.println ("[ 1 ] Host Date/Time");
	            System.out.println ("[ 2 ] Host Uptime");
	            System.out.println ("[ 3 ] Host Memory Usage");
	            System.out.println ("[ 4 ] Host Netstat");
	            System.out.println ("[ 5 ] Current Users on Host");
	            System.out.println ("[ 6 ] Host Disk Usage");
	            System.out.println ("[ 0 ] End Communication");
	
				userInput = input.nextInt();
				
            /*
             * The following switch accepts the user's desired choice through
             * userInput
             */
      
				switch(userInput) 
            {
					case 1: start = System.currentTimeMillis();
						System.out.println(stub.selectedDate());
						
						System.out.println("Response Time: " + (System.currentTimeMillis() - start) + " ms");
						break;
					case 2: start = System.currentTimeMillis();
						System.out.println(stub.selectedUptime());
						System.out.println("Response Time: " + (System.currentTimeMillis() - start) + " ms");
						break;
					case 3: start = System.currentTimeMillis();
						System.out.println(stub.selectedMemoryU());
						System.out.println("Response Time: " + (System.currentTimeMillis() - start) + " ms");
						break;
					case 4: start = System.currentTimeMillis();
						System.out.println(stub.selectedNetst());
						System.out.println("Response Time: " + (System.currentTimeMillis() - start) + " ms");
						break;
					case 5: start = System.currentTimeMillis();
						System.out.println(stub.selectedUsers());
						System.out.println("Response Time: " + (System.currentTimeMillis() - start) + " ms");
						break;
					case 6: start = System.currentTimeMillis();
						System.out.println(stub.selectedDiskU());
						System.out.println("Response Time: " + (System.currentTimeMillis() - start) + " ms");
						break;
					case 0: start = System.currentTimeMillis();
						System.out.println(stub.END());
						System.exit(1);
						break;
					default: System.out.println("Please select your desired result using 0-6");
						break;
				}
			}
		}
		catch(Exception e) 
      {
			System.err.println("Error (Client): " + e.toString());
			e.printStackTrace();
		}
	}
}