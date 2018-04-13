import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Vector;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.lang.*;
import java.util.*;
import java.util.Formatter;

public class HashTable extends Hashtable<String, User>
{
	public HashTable()
	{
		System.out.println("Created userlist without dis");
	}

	public HashTable(DataInputStream dis)
	{
		int  numOfUsers;
		User user;

		try
		{
			System.out.println("OK");
			numOfUsers = dis.readInt();

			System.out.println("Number of Users: " + numOfUsers);

			for(int i = 0; i < numOfUsers; i++)
			{
				user = new User(dis);
				this.put(user.username, user);
			}

			System.out.println("Creating list with dis");
		}
		catch(IOException ioe)
		{
			System.out.println("Error");
		}
	}

	public void addUser(User user)
	{
		System.out.println("Adding user");
		this.put(user.username, user);
		System.out.println("About to store");
		this.store();
		System.out.println("Stored");
	}

	public void store()
	{
		int              numOfUsers;
		DataOutputStream dos;
		User          profile;
		Enumeration<User> userE;

		userE = this.elements();

		try
		{

			dos = new DataOutputStream(new FileOutputStream( new File ("user.txt")));

			numOfUsers = this.size();

			System.out.println("Size of list: " + numOfUsers);

			dos.writeInt(numOfUsers);

			System.out.println("Storing");

			while(userE.hasMoreElements())
			{
				System.out.println("Getting profile");
				profile = userE.nextElement();
				profile.store(dos);
				System.out.println("Storing profile");
			}
		}
		catch(Exception e)
		{
			System.out.println("Error in storing");
		}
	}
}