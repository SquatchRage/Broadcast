import java.util.*;
import java.util.Arrays;
import java.util.Collections;
import java.lang.*;
import java.io.*;

public class User
{
	String username;
	String password;
	CTC ctc;

	public User(String user, String pass, CTC ctc)
	{
		username = user;
		password = pass;
		this.ctc = ctc;
	}
	
	public User(DataInputStream dis)
	{
		try
		{
			username = dis.readUTF();
			password = dis.readUTF();
			
		}
		catch(Exception e)
		{
		}
	}
	
	public void store(DataOutputStream dos)
	{
		try
		{
			dos.writeUTF(username);
			//System.out.println("username stored");
			dos.writeUTF(password);
			//System.out.println("password stored");
		}
		catch(Exception e)
		{
		}
	}



	public void send(String msg)
	{
		ctc.send(msg);
		System.out.println("sending in user");

	}

	
	public void setCTC(CTC ctc)
	{
		this.ctc = ctc;
	}
}