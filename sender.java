
import java.util.*;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class sender {

	public static void main(String args[])
	
	{
		try
		{
			
			Scanner scanner = new Scanner(System.in);
			
			sender send = new sender();
			
			System.out.println("Enter the port number:");
			int port=scanner.nextInt();
			
			InetAddress ip = InetAddress.getLocalHost();
			DatagramSocket datagramsocket=new DatagramSocket();
			
			datagramsocket.connect(ip, port);
			System.out.println("Connection established");
			
			String clientfilepath = "C:\\Users\\avidekar\\Desktop\\client_server\\client.txt";		
			File fclient = new File(clientfilepath);
	        FileInputStream fileinputstream=new FileInputStream(fclient);
	        
	        		int fsize = (int)fclient.length();
	    			System.out.println("The total length of the file in bytes: "+fsize);
	    			byte barray[] = new byte[fsize];
	    			byte temp[] = new byte[10];
	    			byte value[] = new byte[10];
	    			byte final_array[] = new byte[21];
	    			
	    			byte ack[] = new byte[1];
	    			//ack[0]=0;
	    			
	    			fileinputstream.read(barray, 0, barray.length);
	    			
	    			int i,h,j;
	    			
	    		    for(i=0;i<3;i++)
	    			{
	    				j=10*i;
	    				
	    				for(h=0;h<10;h++)
	    				{
	    					temp[h]=barray[j];
	    					j++;
	    					value[h]=0;
	    				}
	    				
	    				value = send.check(temp);
	    				
	    				final_array = send.insert(i,value,temp);
	    			
	    				DatagramPacket datagramsocket1 = new DatagramPacket(final_array,final_array.length);
						System.out.println("Packet no "+i+ " sent");
						System.out.println("Sleep started");
	    				Thread.sleep(10000);
	    				System.out.println("Sleep ends");
					}
	    				DatagramPacket datagramsocket2=new DatagramPacket(ack,ack.length);
	    				datagramsocket.receive(datagramsocket2);
	    				
	    				System.out.println("Acknowledgement received from the client :");
	    				
	    				if(ack[0]==(byte)5)
	    				
	    					System.out.println("Ready to transfer the next packet");
	    				
	    				else if(ack[0]==(byte)10)
	    				{
	    					System.out.println("There was error in transmission");
	    					System.out.println();
	    					System.out.println("Re-transmitting the packet "+i+" again");
	    					i--;
	    				}
	    			}
	    			
	    		    
	    				
		}catch(Exception e){System.out.println();}
	}

	 public byte[] check( byte[] t )
		{
		 	byte temp1[]=new byte[10];
		 	
		 	for(int i=0;i<10;i++)
		 		temp1[i]=0;
			Checksum c=new CRC32();
			c.update(t,0,t.length);
			
			temp1=String.valueOf(c.getValue()).getBytes();
			
			 return temp1;
			 	
		}
	
	public byte[] insert(int iteration,byte[] checksum,byte[]data)

	{
		byte final_array[]=new byte[21];
		if(iteration%2==0)
			final_array[0]=0;
		else
		
			final_array[0]=1;
		for(int i=1;i<11;i++)
			final_array[i]=checksum[i-1];
		
		for(int i=11;i<21;i++)
			final_array[i]=data[i-11];
		
		return final_array;
	}
	 
	
}
