import java.net.*;
import java.io.*;
public class server
 {
   public static void main(String[] args) throws IOException 
   {	   
	ServerSocket serversocket = new ServerSocket(8989);
      Socket connectionsocket = serversocket.accept();
      String destination = "C:\\Users\\avidekar\\Desktop\\client_server\\server.txt";       
      byte[] bytereceived = new byte[10];
      try
      {       
    	 FileOutputStream fileoutputstream = new FileOutputStream(destination);
         BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(connectionsocket.getInputStream()));     	
         System.out.println("Server is RUNNING");
         System.out.println("now READING");
         String h =bufferedreader.readLine();
         int f = Integer.parseInt(h);
         String g=bufferedreader.readLine();
         int count=Integer.parseInt(g);         
         for(int i=0;i<f;i++)
         {        
        	 if(i==(f-1))
        	 {        		 
        		 String message = bufferedreader.readLine();
        		 bytereceived=message.getBytes();
        		 System.out.println("Sending the remaining bytes of data ");
        		 if(count<5)
        		 {
        			for(int q=0;q<count;q++)
                	fileoutputstream.write(bytereceived[q]);
        		 }
        		 else
                 	for(int q=5;q<count;q++)
                   {
           			for(int t=0;t<5;t++)
                    fileoutputstream.write(bytereceived[t]);	
                   	fileoutputstream.write(bytereceived[q]);
                   	}
        	 } 	 
         String message = bufferedreader.readLine();
         bytereceived=message.getBytes();
         System.out.println("Breaking 10 bytes and sending into 5 bytes");
         for(int q=0;q<5;q++)
         {
        	fileoutputstream.write(bytereceived[q]);
         }
         System.out.println("Sending other half 5 bytes of 10 bytes");
           for(int q=5;q<10;q++)
           { 
           	fileoutputstream.write(bytereceived[q]);
           }         
         }     
         fileoutputstream.close();     
      }     
      catch(Exception ex)
      {}   
      serversocket.close();
 }
}