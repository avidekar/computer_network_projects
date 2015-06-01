

import java.io.*;

import java.util.*;
import java.net.*;
import java.util.zip.*;


public class udpclient
{
	
public static void main(String args[])

{
	Scanner scanner = new Scanner(System.in);	
	System.out.println("Enter the source file path");
	String file=scanner.next();

	System.out.println("Enter the port number");
	int port_number=scanner.nextInt();
try
{
	
boolean flag=true;

		//Creating the file object
		File file=new File("C:\\Users\\avidekar\\Desktop\\client_server\\client.txt");
		FileInputStream fileinputstream = new FileInputStream(file);
		
		//Creating the socket
		DatagramSocket datagramsocket = new DatagramSocket();
			
		DatagramPacket send_packet;
		
		DatagramPacket receive_packet;
		
		//Gives the IP address of your machine
		InetAddress ia=InetAddress.getLocalHost();
		
		//Connecting to the server through IP address and port number
		datagramsocket.connect(ia,port_number);
		
		System.out.println("Client UDP Socket has been created successfully");
		
		//Obtaining the length of the file
		int file_length=fileinputstream.available();
		
		//Obtaining the size of the byte array for the last transfer
		int last_iteration_size=file_length%10;
		int number_of_iterations=0;
		
		
		
		if(last_iteration_size==0)
		{
		//Obtaining the number of iterations for the loop
		 number_of_iterations=file_length/10;
		}
		
		else
		{
			number_of_iterations=(file_length/10)+1;
		}
		
		//Sending the number of iterations to the server
		byte no_of_iterations_array[]=new byte[1];
		no_of_iterations_array[0]=(byte)number_of_iterations;
		DatagramPacket send_no_of_iterations=new DatagramPacket(no_of_iterations_array,no_of_iterations_array.length);
		datagramsocket.send(send_no_of_iterations);
			
		
		//Creating the packet array to be sent to the server
		byte packet_array[]=new byte[21];
		
		//Making all the elements of the packet array as 0 in the beginning
		for(int i=0;i<packet_array.length;i++)
		{
			packet_array[i]=0;
		}
	
		
	    //Initializing the sequence number
		byte sequence_number=0;
		
		//Initializing the checksum value
		long client_checksum=0;
		
		//Creating the buffer into which bytes are to be read
		byte client_read[]=new byte[10];
		
		byte client_last_read[]=new byte[last_iteration_size];
		
		//Creating the checksum object
		Checksum cs;
		
		byte checksum_array[];
		
		//Creating the checksum string
		String checksum_string;
		
		DatagramPacket receive_acknowledgement;
		
		//Creating an acknowledgement array containing the sequence number
		byte acknowledgement_array[]=new byte[1];
		
		while(flag)
		{
		for(int i=0;i<number_of_iterations;i++)
		{
			fileinputstream.read(client_read);
			
			
			System.out.println("The byte array of the data looks as follows:");
			for(int ik=0;ik<client_read.length;ik++)
			{
				System.out.print(client_read[ik]+"\t");
			}
			
			
			//Copying the sequence number in the first index of the packet array header
			packet_array[0]=sequence_number;
			
			//Copying the 10 bytes of data in the packet-array
			for(int j=11;j<packet_array.length;j++)
			{
				packet_array[j]=client_read[j-11];
			}
			
			//Calculating the checksum for the data
			cs=new CRC32();
			cs.update(client_read,0,client_read.length);
			client_checksum=cs.getValue();
			//System.out.println("The long value of the checksum is: "+client_checksum);
			
			checksum_string=String.valueOf(client_checksum);
			if(checksum_string.length()==9)
			{
				checksum_string="0"+checksum_string;
			}
			else if(checksum_string.length()==8)
			{
				checksum_string="0"+"0"+checksum_string;
			}
			checksum_array=checksum_string.getBytes();
			
			
			System.out.println("The byte array of the checksum looks like follows:");
			for(int ca=0;ca<checksum_array.length;ca++)
			{
				System.out.print(checksum_array[ca]+"\t");
			}
			
			
			//Copying the checksum array to the packet array
			for(int i1=0;i1<checksum_array.length;i1++)
			{
				packet_array[i1+1]=checksum_array[i1];
			}
			
			while(flag)
			{
				
				
				//Displaying the packet
				System.out.println("The packet looks like follows:");
				for(int pack=0;pack<packet_array.length;pack++)
				{
					System.out.print(packet_array[pack]+"\t");
				}
				
				
			//Sending the fully loaded packet to the server side
		    send_packet=new DatagramPacket(packet_array,packet_array.length);
			datagramsocket.send(send_packet);
			
			
			//Receiving the acknowledgement byte array from the server
			try
			{
			datagramsocket.setSoTimeout(10000);
			receive_acknowledgement=new DatagramPacket(acknowledgement_array,acknowledgement_array.length);
			datagramsocket.receive(receive_acknowledgement);
        		
  catch(SocketTimeoutException e)
			{
	  
                  	  
	           continue;
	  
	  
			}
			
			//flag=false;
			
			}
			flag=false;
			} //end of while
		//end of  the main outer for loop
			flag=true;
			
			//Making all the elements of the packet array as 0 again in the end
				for(int pv=0;pv<packet_array.length;pv++)
				{
					packet_array[pv]=0;
				}
			
		System.out.println("End of File");
		System.out.println("No more packets to send.");
	
		
		
		
		//Closing the FileInputStream
		fileinputstream.close();
		//Closing the UDP socket
		datagramsocket.close();
	
			}
}
	   
		   
	   
	   

		
	catch(SocketException e)
	{
		System.out.println("Data is retransmitting");
		System.out.println(e);
	}
	catch(UnknownHostException e)
	{
		System.out.println("There is an unknown host exception");
		System.out.println(e);
	}
	catch(IOException e)
	{
		System.out.println("packet is lost data packet is retransmitting");
		
		
		
		
	System.out.println(e);
	}
}
}
