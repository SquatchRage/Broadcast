import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Login extends JDialog
	implements DocumentListener, ActionListener 
	{

		JTextField nameField;
		JPasswordField passwordField;
		static String userName, password;
		char[] passArray;
		
		JLabel userLabel, passwordLabel;

		JButton registerButton;
		JButton loginButton;
		
		JPanel fieldPanel;
		JPanel buttonPanel;
		String   sendMsg;
		String registerCommand = "Register";
		String loginCommand = "Login";
		Talker talker;
		String ID = "From Login ";
		

		Container cp;
		

	 
	public Login() throws FileNotFoundException{
		
		

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
		 if(AE.getActionCommand().equals("REGISTER")){
			
		 
		 userName = nameField.getText().trim();
			passArray = passwordField.getPassword();
			password = new String(passArray);

			for(int i = 0; i < passArray.length; i++)
				passArray[i] = 0;
			
				try{

					sendMsg = "REGISTER:" + userName;

					talker.send(sendMsg);

					sendMsg = "REGISTER:" + password;

					talker.send(sendMsg);
				
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Error", "ERROR", JOptionPane.ERROR_MESSAGE);
					  e.printStackTrace();
				}
				
			
			
		}//end register
		else if(AE.getActionCommand().equals("LOGIN"))
		{
			userName = nameField.getText().trim();
			passArray = passwordField.getPassword();
			password = new String(passArray);

			for(int i = 0; i < passArray.length; i++)
				passArray[i] = 0;

			try {
			sendMsg = "LOGIN:" + userName;

			talker.send(sendMsg);

			sendMsg = "LOGIN:" + password;

			talker.send(sendMsg);
				
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
	
	public static void main(String [] args) throws FileNotFoundException{
		
		new Login();
	}


	}// EOC

