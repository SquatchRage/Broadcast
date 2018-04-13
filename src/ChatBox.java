import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.io.*;
import java.lang.*;
import java.util.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.datatransfer.*;
import java.awt.dnd.*;

public class ChatBox extends JFrame implements ActionListener
{
	Client client;
	Container cp;
	JButton sendButton;
	JTextArea textArea;
	JPanel panel, listPanel;
	Talker talk;
	JList messageList;
	DefaultListModel dlm;
	Talker talker;
	String ID = "From client class: ";
	JButton           closeBtn;
	JButton           sendBtn;
	JScrollPane       chatTableScrollPane;
	DefaultTableModel chatTableModel;
	String            ownersUsername;
	JLabel reply;


	public ChatBox(Client c)
	{
	
		client = c;

		ownersUsername = Client.userName;

		closeBtn = new JButton("Close");
		closeBtn.addActionListener(this);
		closeBtn.setActionCommand("Close");

		sendButton = new JButton("Broadcast");
		sendButton.addActionListener(this);
		sendButton.setActionCommand("Broadcast");
		
		reply = new JLabel();
		
		dlm = new DefaultListModel();
		messageList = new JList(dlm);
		
		JScrollPane sp = new JScrollPane(messageList);
		sp.setPreferredSize(new Dimension(300, 380));

		textArea = new JTextArea();
		textArea.setEditable(true);
		textArea.requestFocus();
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(300, 200));
	
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		listPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		panel.add(sendButton);
		listPanel.add(sp);
		cp = getContentPane();
			
	
		cp.add(scrollPane, BorderLayout.WEST);
		cp.add(panel, BorderLayout.SOUTH);
		cp.add(listPanel, BorderLayout.EAST);
		setUp();


	}
	

	 void setUp ()
	 {
	     Toolkit tk;
	     Dimension d;
	     
	     setDefaultCloseOperation (EXIT_ON_CLOSE);
	     
	     tk = Toolkit.getDefaultToolkit ();
	     d = tk.getScreenSize ();
	     
	     setSize (d.width/3, d.height/3);
	     setLocation (d.width/4, d.height/4);
	     setTitle ("Client Chat Box");
	     setVisible (true);
	 	}

	


	public void actionPerformed(ActionEvent ae)
	{
		String      user;
		String      msg;
		JScrollBar  vertical;

		 if(ae.getActionCommand().equals("Broadcast"))
		{
			msg = textArea.getText().trim();
			textArea.setText("");

			if(!msg.equals(""))
			{
				// sends messge to ctc
				
				String cmd = "Broadcast," + msg;
				try {
					client.cts.talker.send(cmd);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}
}