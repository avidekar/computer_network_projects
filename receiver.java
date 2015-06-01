

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.Random;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class receiver {
	
	public static void main(String args[])
	{
		try
		{
			receiver r=new receiver();
			
			Random rnum=new Random();
			
			DatagramSocket ds=new DatagramSocket(5555);
			System.out.println("Connecting to the client....");
			
			byte final_array[]=new byte[21];
			byte new_checksum[]=new byte[10];
			byte old_checksum[]=new byte[10];
			byte temp[]=new byte[10];
			byte old_seq;
			int new_seq;
			byte ack[]=new byte[1];
			
			
			
			
			int i,h,j;
			
			for(i=0;i<3;i++)
			{
				DatagramPacket dr1=new DatagramPacket(final_array,final_array.length);
				ds.receive(dr1);
				System.out.println("Packet "+i+" received");
				
				for(h=0;h<10;h++)
				{	new_checksum[h]=0;
					old_checksum[h]=0;
					temp[h]=final_array[h+11];
				}
			
			old_seq=final_array[0];
			new_seq=rnum.nextInt(2);
				
			new_checksum=r.check(temp);
				
			for(int m=0;m<10;m++)
				old_checksum[m]=final_array[m+1];
			
			if(Arrays.equals(new_checksum, old_checksum))
				{
				System.out.println("Checksum Matches");
				System.out.println("Now comparing sequence numbers");
				
					
				
					if((byte)new_seq==old_seq)
						{System.out.println("Sequence number matches");
						ack[0]=(byte)5;
						DatagramPacket dr2=new DatagramPacket(ack,ack.length,dr1.getAddress(),dr1.getPort());
						ds.send(dr2);
						}
						else
							{
							System.out.println("Sequence number does not matches");
							ack[0]=(byte)10;
							DatagramPacket dr2=new DatagramPacket(ack,ack.length,dr1.getAddress(),dr1.getPort());
							ds.send(dr2);
							i--;
							
							}
				}
			
			else
			{
				System.out.println("Checksum does not matches");
				ack[0]=(byte)10;
				DatagramPacket dr2=new DatagramPacket(ack,ack.length,dr1.getAddress(),dr1.getPort());
				ds.send(dr2);
				i--;
				
			}
			
				
				
			}
			
			
		} catch(Exception e){System.out.println(e);}
	
	
	
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

}
