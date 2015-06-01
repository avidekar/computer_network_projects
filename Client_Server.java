import java.io.*;
import java.net.*;
public class Client_Server
{
public static void main(String[] args)throws IOException
{
Socket socketclient = new Socket("127.0.0.1",8989);
System.out.println("Client : "+" Connection eshtablished");
System.out.println("Hello");

int totalbytes;
char[] c = new char[10];
String fileinputsource = "C:\\Users\\avidekar\\Desktop\\client_server\\client.txt";  
try
{
   PrintWriter printwriter = new PrintWriter(socketclient.getOutputStream(), true);  
   FileInputStream fileinputstream = new FileInputStream(fileinputsource);       
   byte[] bytearray = new byte[fileinputstream.available()];
   totalbytes = fileinputstream.read(bytearray);       
   System.out.println("Number of bytes read: "+totalbytes);
  
   int bytesentcount=totalbytes/10;
   if((totalbytes%10)!= 0)
   bytesentcount++;
   printwriter.println(bytesentcount);
   printwriter.println(totalbytes%10);        
   System.out.println("Number of times 10 bytes of data would be sent is :"+bytesentcount);
   System.out.println("Bytes read: ");
   int temp;
   for(int p = 0 ; p < bytesentcount ; p++)
   {		
	        if(p==(bytesentcount-1))
	        temp=totalbytes%10;
	        else
	        temp=10;
	        int h=p*10;
	        for(int k=0;k<temp;k++)
			         {
			            c[k]=(char)(bytearray[h+k]);				
			            System.out.print(c[k]);
	                }  
	         String d=new String(c);
	         printwriter.println(d);
   }
   fileinputstream.close();
}
catch(Exception ex)
{
System.out.println("Error in Connection");
}
  socketclient.close();
}

}

	
