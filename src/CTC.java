import java.net.*;
import java.util.*;
import java.util.Arrays;
import java.util.Collections;
import java.lang.*;

public class CTC implements Runnable
{
	Talker   talker;
	User  	 user;
	Server   server;
	Socket   normalSocket;

	public CTC(Socket socket, Server s)
	{
		normalSocket = socket;
		server = s;
		talker = new Talker(normalSocket);
		new Thread(this).start();
	}

	public void run()
	{
		String[]            username;
		String[]            password;
		String              getCreds;
		String[]            reply;
		String              replyMsg;
		boolean             loggedIn;
		boolean             stillLoggedIn;

		username = null;
		reply = null;

		try
		{
			loggedIn = false;

			user = null;

			while(!loggedIn)
			{
				getCreds = talker.receive();

				System.out.println("Message received in CTC " + getCreds);
				username = getCreds.split(",");

				System.out.println("Size of username: " + username.length);

				getCreds = talker.receive();
				password = getCreds.split(",");

				user = server.findUser(username[1]);

				if(username[0].equals("Register"))
				{
					System.out.println("Trying to register in");

					if((user  != null))
					{
						System.out.println("Inside false outcome");
						replyMsg = "BAD_CANT_LOG_IN";
						talker.send(replyMsg);
					}
					else
					{
						user = new User(username[1], password[1], this);
						loggedIn = true;
						server.addUser(user);
						System.out.println("Inside true outcome");
						replyMsg = "GOOD_LOGGING_IN";
						talker.send(replyMsg);
						System.out.println("Reply sent");
					}
				}
				else if(username[0].equals("Login"))
				{
					if(user == null)
					{
						replyMsg = "BAD_CREDS";
						talker.send(replyMsg);
					}
					else
					{
						if(!server.onlineList.contains(user.username))
						{
							if(user.password.equals(password[1]))
							{
								replyMsg = "GOOD_LOGGING_IN";
								user.setCTC(this);
								loggedIn = true;
								talker.send(replyMsg);
								//System.out.println("Reply sent");
							}
							else
							{
								//System.out.println("Bad password");
								replyMsg = "BAD_CREDS";
								talker.send(replyMsg);
							}
						}
						else
						{
							//System.out.println("Already logged in");
							replyMsg = "BAD_CREDS";
							talker.send(replyMsg);
						}
					}
				}
			}//end first while loop

			//System.out.println("Outside first while loop in CTC");
			talker.setId(username[1]);
			//System.out.println("Setting CTC talker id");
			server.onlineList.add(user);
		}
		catch(Exception e)
		{
		}

		//System.out.println("About to go into never ending loop");
		stillLoggedIn = true;

		while(stillLoggedIn)
		{
			try
			{
				replyMsg = talker.receive();

				//System.out.println("MESSAGE RECEIVED IN CTC " + replyMsg);

				reply = replyMsg.split(",");

				//System.out.println("Size of reply in CTC: " + reply.length);

				if(reply[0].equals("Broadcast"))
				{
					//System.out.println("IN TOALL");
					System.out.println("Msg to send " + reply[2] + " " + reply[1]);
					server.broadcast("Broadcast," + reply[1]);
				}	
			}
			catch(Exception e)
			{
				stillLoggedIn = false;
			}
		}

		//System.out.println("Outside of while loop");
		try
		{
			replyMsg = "LOGGED_OUT";
			talker.send(replyMsg);
		}
		catch(Exception e)
		{
		}
	}//end run

	public void send(String msg)
	{
		try
		{
			System.out.println("Sending in CTC");
			talker.send(msg);
		}
		catch(Exception e)
		{
		}
	}
}