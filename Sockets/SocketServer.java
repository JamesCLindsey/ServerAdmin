/*
 * This server-side server application was programmed
 * by James Lindsey as a University of North Florida student
 * in the Spring of 2016.
 * It connects with its partner (SocketClient) via sockets on established servers
 */


import java.net.*; 
import java.io.*; 

public class SocketServer extends Thread
{ 
 protected Socket clientSocket;
 static int threadNum =0;
 
 public static void main(String[] args) throws IOException 
   { 
    ServerSocket serverSocket = null; 
    //constantly listening for new connections and creates sockets
    try { 
         serverSocket = new ServerSocket(2358); 
         System.out.println ("Socket Connected!");
         try { 
              while (true)
                 {
                  System.out.println ("Listening for Connection...");
                  new SocketServer (serverSocket.accept()); 
                 }
             } 
    //various catches for possible errors
    catch (IOException e) 
             { 
              System.err.println("ERROR: Accept failed."); 
              System.exit(1); 
             } 
        } 
    catch (IOException e) 
        { 
         System.err.println("ERROR: Could not listen on port: 2358."); 
         System.exit(1); 
        } 
    finally
        {
         try {
              serverSocket.close(); 
             }
         catch (IOException e)
             { 
              System.err.println("ERROR: Could not close port: 2358."); 
              System.exit(1); 
             } 
        }
   }

 private SocketServer (Socket clientSoc)
   {
    clientSocket = clientSoc;
    start();
   }

 public void run()
   {
	 threadNum++; //increments new clients as they connect
    System.out.println ("New Client... Thread #" + threadNum);

    try { 
         PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true); 
         BufferedReader input = new BufferedReader( 
                 new InputStreamReader( clientSocket.getInputStream())); 
         String inputLine;
         String count;
         
         output.println(threadNum); //assigns the thread number to the client
         
         while ((inputLine = input.readLine()) != null) 
             {
        	 count = input.readLine();
        	 System.out.println("Executing Linux command \"" + inputLine + 
        			 			"\" for client #" + count);
        	  String s;
        	  
        	  //process executes the Linux command from the switch statement
        	  //that is client-side
              Process p = Runtime.getRuntime().exec(inputLine);
              BufferedReader br = new BufferedReader(
                      new InputStreamReader(p.getInputStream()));
                  while ((s = br.readLine()) != null) {                 
                      output.println(s); 
                  }
                  output.println("LastLine");

              if (inputLine.equals("0")) 
                  break; 
             } 
         
         output.close(); 
         input.close(); 
         
         clientSocket.close(); 
         
        } 
    catch (IOException e) 
        { 
         System.err.println("Connection Lost...");
         System.exit(1); 
        } 
    }
} 