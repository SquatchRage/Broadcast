import java.awt.*;
import javax.swing.*;
import javax.swing.SwingUtilities;

public class CTS implements Runnable
{
	Talker talker;
	JLabel replyLBL;
	Client client;

	public CTS(Talker t, Client c)
	{
		talker = t;
		client = c;
		new Thread(this).start();
	}

	public void run()
	{
		boolean error;

		error = false;

		while(!error)
		{
			final String reply;
			final String[] sendReply;

			try
			{
				reply = talker.receive();
				System.out.println("MESSAGE RECEIVED IN CTS " + reply);

				sendReply = reply.split(",");


				SwingUtilities.invokeLater(
					new Runnable()
					{
						public void run()
						{
							if(sendReply[0].equals("GOOD_LOGGING_IN") || sendReply[0].equals("BAD_CANT_LOG_IN") || sendReply[0].equals("BAD_CREDS"))
							{
								client.sendLoginReply(reply);
							}

							else if(sendReply[0].equals("Broadcast"))
							{

								client.chatBox.dlm.addElement(sendReply[1]);
								client.chatBox.repaint();
							
							}
							
						}
					}
				);//end of invokeLater
			}
			catch(Exception e)
			{
				error = true;
			}
		}


	}
}