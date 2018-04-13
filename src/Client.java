import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.lang.*;
import java.util.*;
import java.util.Formatter;
import javax.swing.table.TableColumn;

public class Client extends JFrame implements ActionListener, DocumentListener
{
	Talker talker;
	CTS cts;
	ChatBox chatBox;
	Container                   cp;
	JTextField nameField;
	JPasswordField passwordField;
	static String userName, password;
	char[] passArray;
	JLabel userLabel, passwordLabel, errorLabel;
	JButton registerButton;
	JButton loginButton;
	JPanel fieldPanel;
	JPanel buttonPanel;
	String   sendMsg;	

	public Client()
	{
		talker = new Talker("127.0.0.1", 1234, this);

		cts = new CTS(talker, this);
	
		 //creation of buttons, labels, textfields, lists and panels. 
		registerButton = new JButton("Register");
		registerButton.addActionListener(this);
		registerButton.setActionCommand("Register");
		registerButton.isDefaultButton();
		registerButton.setEnabled(false);
		  
		 
		 loginButton = new JButton("Login");
		 loginButton.addActionListener(this);
		 loginButton.setActionCommand("Login");
		 loginButton.setEnabled(false);

		 
		 userLabel = new JLabel("User: ");
		 passwordLabel = new JLabel("Password: ");
		 errorLabel = new JLabel("");
		 
		 passwordField = new JPasswordField(20);
		 nameField = new JTextField(20);

	     //puts focus in serverField
	     addWindowListener( new WindowAdapter() {
	    	    public void windowOpened( WindowEvent e ){
	    	        nameField.requestFocus();
	    	    }
	    	}); 
	     
		 fieldPanel = new JPanel(new GridBagLayout()); 
		 buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		 buttonPanel.add(registerButton);
		 buttonPanel.add(loginButton);
		 
		 GridBagConstraints gbc = new GridBagConstraints();
		 
			gbc.insets = new Insets(4, 4, 4, 4);

			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;
			fieldPanel.add(userLabel, gbc);

			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.weightx = 1;
			fieldPanel.add(nameField, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.weightx = 1;
			fieldPanel.add(passwordLabel, gbc);

			gbc.gridx = 1;
			gbc.gridy = 1;
			gbc.weightx = 1;
			fieldPanel.add(passwordField, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 1;
			gbc.weightx = 1;
			fieldPanel.add(errorLabel, gbc);
			
	
			
		 cp = getContentPane();
		 cp.add(fieldPanel, BorderLayout.WEST);
		 cp.add(buttonPanel, BorderLayout.SOUTH);
		 

			setSize(500, 300);
			setTitle("Login");
			setLocationRelativeTo(null);
			setVisible(true);
			
			
	//----------------- get info from fields to check if empty ------------------		
			nameField.getDocument().addDocumentListener(this);		
			passwordField.getDocument().addDocumentListener(this);			

	}


	public void checkLength(){
		  if(nameField.getDocument().getLength() > 0 && passwordField.getDocument().getLength() > 0){
			  loginButton.setEnabled(true);
			  registerButton.setEnabled(true);
		  }
		  else{
			  
			  loginButton.setEnabled(false);
			  registerButton.setEnabled(false);
		  }
		}
		
	//----------------------------------------------------------------------------------------------------------------
	 @Override
	 public void actionPerformed(ActionEvent AE) 
	 {     			

		 if(AE.getActionCommand().equals("Register")){
/// IM NOT GETTING HERE, SO TALKER ISNT SEEING REGISTER? ERROR IN CLIENT?		 
		 userName = nameField.getText().trim();
			passArray = passwordField.getPassword();
			password = new String(passArray);

			for(int i = 0; i < passArray.length; i++)
				passArray[i] = 0;
			
				try{

					sendMsg = "Register," + userName;

					cts.talker.send(sendMsg);

					sendMsg = "Register," + password;

					cts.talker.send(sendMsg);
				
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Error", "ERROR", JOptionPane.ERROR_MESSAGE);
					  e.printStackTrace();
				}
				
			
			
		}//end register
		else if(AE.getActionCommand().equals("Login"))
		{
			userName = nameField.getText().trim();
			passArray = passwordField.getPassword();
			password = new String(passArray);
			

			for(int i = 0; i < passArray.length; i++)
				passArray[i] = 0;

			try {
			sendMsg = "Login," + userName;

			cts.talker.send(sendMsg);

			sendMsg = "Login," + password;

			cts.talker.send(sendMsg);
				
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "Error", "ERROR", JOptionPane.ERROR_MESSAGE);
				  e.printStackTrace();
			}
		}//end login
		 
	 	}// End of Action


	@Override
	public void changedUpdate(DocumentEvent arg0) {
		
		checkLength();
	}


	@Override
	public void insertUpdate(DocumentEvent arg0) {
		checkLength();
		
	}


	@Override
	public void removeUpdate(DocumentEvent arg0) {
		checkLength();
		
	}
	


	public void sendLoginReply(String reply)
	{
		System.out.println("Inside sendLoginReply");

		if(reply.startsWith("GOOD_LOGGING_IN"))
		{
			quit();
			cp.removeAll();
			chatBox = new ChatBox(this);
			cts.talker.setId(userName);
		}
		else if(reply.startsWith("BAD_CREDS"))
			errorLabel.setText("Username/Password incorrect");
		else
			errorLabel.setText("Username Not Available. Try Again.");
	}

	
	

	public void quit()
	{
		this.dispose();
	}



	void resetMainFrame()
	{
		Toolkit tk;
		Dimension d;

		tk = Toolkit.getDefaultToolkit();
		d = tk.getScreenSize();
		setSize(d.width/2, d.height/2);
	    setLocation(d.width/4, d.height/4);

	    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	void setUpMainFrame()
	{
		Toolkit tk;
	    Dimension d;

	    tk = Toolkit.getDefaultToolkit();
	    d = tk.getScreenSize();
	    setSize(d.width/4, d.height/4);
	    setLocation(d.width/4, d.height/4);

	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    setTitle("Client");

	    setVisible(true);
    }//end setupMainFrame()
}


