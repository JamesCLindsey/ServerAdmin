/*
 * This Server-side program was created by James Lindsey on April 20, 2016
 * for CNT4504 (Networks/Distributed Processing) for Professor Douglas Leas.
 * It, with its sister-program (ClientRMI), creates a server diagnostic menu
 * that can be used remotely as long as the remote machine is using ClientRMI. 
 * They use RMI registry protocols to accomplish this connection.
 */

import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/*
 * serverStub is an interface that binds the following methods, 
 * allowing them to execute properly.
 */
interface serverStub extends Remote {
	
	   public String selectedDate() throws RemoteException;
	   public String selectedUptime() throws RemoteException;
	   public String selectedMemoryU() throws RemoteException;
	   public String selectedUsers() throws RemoteException;
	   public String selectedNetst() throws RemoteException;
	   public String selectedDiskU() throws RemoteException;
	   public String END() throws RemoteException;
	}

//~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~
//~*~*~*~*~*~*~*~*~*~*~*~*~*~*~ ServerRMI ~*~*~*~*~*~*~*~*~*~*~*~*~*~*~
//~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~

public class ServerRMI implements serverStub 
	{
		
	   public ServerRMI() {}
	   
		/*
		 * The following 7 method calls from the client-side program
		 * also print the request for server-side viewing. Their main focus,
		 * however, is to send the appropriate Linux command to the 
		 * CmdRun method which executes the process.
		 */
	   
	   
	   public String selectedDate() throws RemoteException 
	   {System.out.println("Sent date ");
	      return runCommand("date");
	   }	   
	   public String selectedUptime() throws RemoteException 
	   {System.out.println("Sent uptime");
	      return runCommand("uptime");
	   }	   
	   public String selectedMemoryU() throws RemoteException 
	   {System.out.println("Sent memory usage");
	      return runCommand("free -mo");
	   }	   
	   public String selectedNetst() throws RemoteException 
	   {System.out.println("Sent netstat");
	      return runCommand("netstat -t");
	   }
	   public String selectedUsers() throws RemoteException 
	   {System.out.println("Sent users");
	      return runCommand("users");
	   }
	   public String selectedDiskU() throws RemoteException 
	   {System.out.println("Sent disk usage");
	      return runCommand("df -h");
	   }
	   public String END() throws RemoteException 
	   {System.out.println("A client has disconnected");
	      return "Disconnecting from server";
	   }
	   
	   /*
	    * runCommand simply executes the commands it is given from the 
	    * above methods that are called by the client-side program
	    */
	   
	   public String runCommand(String command) 
	   {
	      String info = "";    
	      try 
	      {
	         Process runTerm = Runtime.getRuntime().exec(command);
	         Scanner termRead = new Scanner(runTerm.getInputStream());
	         
	         while(termRead.hasNextLine()) {
	            info += termRead.nextLine() + "\n";
	         }
	      }
	      catch(Exception e) {
	         info = "ERROR: Try again";
	      }
	      
	      return info;
	   }
	   
	   /* *~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*
	    *  *~*~*~*~*~*~*~*~* MAIN *~*~*~*~*~*~*~*~*
	    * *~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*
	    * 
	    * The main methods begins listening for ClientRMI and allows
	    * for re-binding, which is necessary for multi-threading.
	    */
		
	   public static void main(String[] args) {
		   
	      try 
	      {
	    	  
		       ServerRMI serverInstance = new ServerRMI();
		       serverStub stub = (serverStub)UnicastRemoteObject.exportObject(serverInstance, 0);
		 
		       Registry registry = LocateRegistry.getRegistry();
		       registry.rebind("Instance", stub);
	         
		       System.out.println("Server Open...");
		       
	      }
	      catch(Exception e) 
	      {
	         System.out.println("Server error: \n" + e.getMessage()); 
	         e.printStackTrace(); 
	      }
	      
	   }
	}