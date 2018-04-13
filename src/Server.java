import java.net.*;
import java.util.*;
import java.util.Arrays;
import java.util.Collections;
import java.lang.*;
import java.io.*;

public class Server
{
	
	
	HashTable  userList;
	ArrayList<User> onlineList;

	public Server()
	{
		ServerSocket     serverSocket;
		Socket           normalSocket;
		Talker           talker;
		DataInputStream  dis;

		onlineList = new ArrayList<User>();

		try
		{
			try
			{
				dis = new DataInputStream(new FileInputStream(new File ("user.txt")));
				userList = new HashTable(dis);
				System.out.println("created list in try");
			}
			catch(IOException ioe)
			{
				userList = new HashTable();
				System.out.println("Created list in catch");
			}

			serverSocket = new ServerSocket(1234);
			serverSocket.setSoTimeout(1000);
			normalSocket = null;

			while(true)
			{
				try
				{
					normalSocket = serverSocket.accept();

					new CTC(normalSocket, this);
				}
				catch(SocketTimeoutException ste)
				{
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Error in server");
		}
	}

	public void addUser(User newProfile)
	{
		System.out.println("Inside addUser in server");
		userList.addUser(newProfile);
	}


	public void broadcast(String msg)
	{
		Enumeration<User> userE;

		userE = userList.elements();
		
		User user;

		System.out.println("Msg to broadcast " + msg);
		//System.out.println("Trying to broadcast");

		while(userE.hasMoreElements())
		{
			user = userE.nextElement();
			if(onlineList.contains(user)){
			//System.out.println("Looping");
			try
			{
				System.out.println("Sending");
				user.send(msg);
				//profile.ctc.talker.send(msg);
				System.out.println("Sent");
			}
			catch(Exception e)
			{
				System.out.println("error in broadcast");
			}
		}
	}
		//System.out.println("Safe");
	}

	
	public User findUser(String name)
	{
		User findUser;

		findUser = userList.get(name);

		return findUser;
	}
	


	public static void main(String[] args) 
	{
		new Server();
	}

}