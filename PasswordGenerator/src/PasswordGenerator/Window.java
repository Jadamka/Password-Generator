package PasswordGenerator;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Window implements ActionListener{

	// TO-DO:
	//		- when character user chose isnt in password it adds it to random position, but the problem is when there are not two values and it
	//		overwrites the one added (FIND BETTER WAY TO CHECK IF EVERYTHING IS IN THE PASSWORD)
	final JFrame frame;
	final JLabel lengthText;
	final JComboBox cb;
	final JCheckBox symbols, numbers, lowercase, uppercase;
	final JButton generate;
	JTextField result;
	
	Window(){
		// Initialize all variables
		frame = new JFrame();
		lengthText = new JLabel("Password length:");
		cb = new JComboBox();
		AddNumbersToBox();
		generate = new JButton("Generate new password");
		symbols = new JCheckBox("@#$%");
		numbers = new JCheckBox("123456789");
		lowercase = new JCheckBox("abcdefgh");
		uppercase = new JCheckBox("ABCDEFGH");
		result = new JTextField();
		result.setEditable(false);
		
		// Position
		lengthText.setBounds(10, 20, 125, 30);
		cb.setBounds(120, 20, 40, 30);
		symbols.setBounds(10, 60, 150, 20);
		numbers.setBounds(10, 80, 150, 20);
		lowercase.setBounds(10, 100, 100, 20);
		uppercase.setBounds(10, 120, 100, 20);
		generate.setBounds(40, 200, 200, 30);
		result.setBounds(40, 250, 200, 30);
		
		// Adding to frame
		frame.add(generate);
		frame.add(lengthText);
		frame.add(cb);
		frame.add(symbols);
		frame.add(numbers);
		frame.add(lowercase);
		frame.add(uppercase);
		frame.add(result);
		
		// Setup Frame
		frame.setSize(300, 500);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Action
		generate.addActionListener(this);
	}
	
	// When button is clicked do this
	public void actionPerformed(ActionEvent e) {
		if(!(symbols.isSelected()) && !(numbers.isSelected()) && !(lowercase.isSelected()) && !(uppercase.isSelected())){
			result.setForeground(Color.RED);
			result.setText("Need to check at least one box");
		}
		else {
			String passw = GeneratePassword();
			result.setForeground(Color.BLACK);
			result.setText(passw);
		}
	}
	
	// Generating password
	String GeneratePassword() {
		// Get password length selected by user
		int passwordLength = (int)cb.getSelectedItem();
		int reach = 0; /* reach should be 0, because in while loop it checks if its equal to passwordlength and then in the end it will increase by 1
					     if password length is 6 then it would go 1, check, 2, check, 3, check, 4, check, 5, check and stops, so password length would be
					     5 even when users wanted 6*/
		String chars = "";
		String passw = "";
		
		// Get all characters user wants in password
		if(symbols.isSelected()) chars += symbols.getText();
		if(numbers.isSelected()) chars += numbers.getText();
		if(lowercase.isSelected()) chars += lowercase.getText();
		if(uppercase.isSelected()) chars += uppercase.getText();
		
		// Loop while reach aint equal to password length, add character to password
		while(reach != passwordLength) {
			// Generate random int between 0 and chars length (String with all characters user wants)
			Random rand = new Random();
			int rand_int  = rand.nextInt(chars.length());
			// Add character to password string
			passw += chars.charAt(rand_int);
			reach++;
		}
		
		// Convert string to stringBuilder for changing char values inside method CheckChar
		StringBuilder passwBuild = new StringBuilder();
		passwBuild.append(passw);
		
		// Checking if password contains every character user wanted, sending boolean if checkbox is selected if not it returns
		CheckChar(passwBuild, symbols.getText(), symbols.isSelected());
		CheckChar(passwBuild, numbers.getText(), numbers.isSelected());
		CheckChar(passwBuild, lowercase.getText(), lowercase.isSelected());
		CheckChar(passwBuild, uppercase.getText(), uppercase.isSelected());
		
		return passw;
	}
	
	// Check if every any character of characters use chose is in the password
	void CheckChar(StringBuilder passw, String chars, boolean isSelected) {
		// If checkbox for this characters isnt selected it returns
		if(!isSelected) return;
		// If string is will contain certain character it stops for checking
		boolean contains = false;
		// Loops through password
		for(int i = 0; i < passw.length() && !contains; i++) {
			// Loops through characters
			for(int j = 0; j < chars.length(); j++) {
				if(passw.equals(chars)) {
					contains = true;
					break;
				}
			}
		}
		
		// If it doesnt contain certain characters put random character into random position in string and call again CheckChar method
		if(!contains) {
			Random rand = new Random();
			int rand_int_passw = rand.nextInt(passw.length());
			int rand_int_chars = rand.nextInt(chars.length());
			
			passw.setCharAt(rand_int_passw, chars.charAt(rand_int_chars));
			CheckChar(passw, chars, false);
		}
	}
	
	// Add available password lengths to comboBox for user to choose (6 - 16)
	void AddNumbersToBox() {
		for(int i = 6; i <= 16; i++)
			cb.addItem(i);
	}
	
	public static void main(String[] args) {
		new Window();
	}

}
