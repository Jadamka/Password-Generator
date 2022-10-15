package PasswordGenerator;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Window implements ActionListener{
	final JFrame frame;
	final JLabel lengthText;
	final JComboBox cb;
	final JCheckBox symbols, numbers, lowercase, uppercase;
	final JButton generate;
	final JTextField result;
	String[] alphabet = {"", ""};	// for saving alphabet letters in lowercase and uppercase
	
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
		String chars[] = {"", "", "", ""};	// Save 4 types of characters user can have
		String passw = "";
		
		// Get all characters user wants in password
		if(symbols.isSelected()) chars[0] += symbols.getText();
		if(numbers.isSelected()) chars[1] += numbers.getText();
		if(lowercase.isSelected()) {
			for(int i = 97; i <= 122; i++)	// get characters from a - z
				alphabet[0] += (char)i;
			chars[2] += alphabet[0];
		}
		if(uppercase.isSelected()) {
			for(int i = 65; i <= 90; i++)	// get characters from A - Z
				alphabet[1] += (char)i;
			chars[3] += alphabet[1];
		}
		
		// Loop while reach aint equal to password length, add character to password
		int index = 0;
		while(reach != passwordLength) {
			if(chars[index].length() != 0) {	// When string length in array chars isnt zero do stuff
				// Generate random int between 0 and chars length (String with all characters user wants)
				Random rand = new Random();
				int rand_int  = rand.nextInt(chars[index].length());
				// Add character to password string
				passw += chars[index].charAt(rand_int);
				reach++;
			}
			index++;
			if(index > 3)
				index = 0;
		}
		
		return Shuffle(passw);
	}
	
	// For shuffling characters in password
	String Shuffle(String input) {
		List<Character> characters = new ArrayList<Character>();
		for(char c: input.toCharArray())
			characters.add(c);
		StringBuilder output = new StringBuilder(input.length());
		while(characters.size() != 0) {
			int randPicker = (int)(Math.random()*characters.size());
			output.append(characters.remove(randPicker));
		}
		
		return output.toString();
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
