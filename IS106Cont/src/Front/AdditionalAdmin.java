package Front;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Function.AdminFunctions;
import Function.Signin;

public class AdditionalAdmin extends JFrame {
	JButton showpasswordButton1;
	JButton showpasswordButton2;
	JButton backtoLandingPage;
	JButton signIn; 
	JButton cancel;
	JLabel headerLbl;
	JLabel typeofAccLbl;
	JLabel password;
	JLabel confirmPassword;
	JLabel empnameLbl;
	JLabel questionLbl;
	JLabel questionanswerLbl;
	JTextField employeeNum;
	JTextField empName;
	JTextField questionAnswer;
	JPasswordField accPassword;
	JPasswordField passwordConfirmation;
	JComboBox<String>questions;
	
	AdminFunctions af = new AdminFunctions();
	AdditionalAdmin(){
		
		ImageIcon headerImg = new ImageIcon("header.png");
		ImageIcon eyeIcon = new ImageIcon("eyeicon.png");
		String[] authenticatorQuestions = {"What is your bestfriend's name?", 
										   "When's your mother's birthday?",
										   "Who's your favorite artist?",
										   "What's your favorite music?",
										   "What's your favorite color?",
										   "What's your dream car?",
										   "Who's your childhood crush?"};
		
		headerLbl = new JLabel();
		headerLbl.setIcon(headerImg);
		headerLbl.setBounds(0,0,800,100);
		
		backtoLandingPage = new JButton("←");
		backtoLandingPage.setBackground(Color.white);
		backtoLandingPage.setFont(new Font("ARIAL", Font.BOLD,  30));
		backtoLandingPage.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		backtoLandingPage.setBounds(5,100,40,30);
		backtoLandingPage.setFocusable(false);
		backtoLandingPage.addActionListener(e -> {
			this.setVisible(false);
		});
		
		employeeNum = new JTextField();
		employeeNum.setBounds(60,150,250,40);
		employeeNum.setForeground(Color.gray);
		employeeNum.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		employeeNum.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		
		typeofAccLbl = new JLabel();
		typeofAccLbl.setText("Admin Username");
		typeofAccLbl.setForeground(Color.gray);
		typeofAccLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		typeofAccLbl.setBounds(60,190,180,20);
		
		showpasswordButton1 = new JButton();
		showpasswordButton1.setIcon(eyeIcon);
		showpasswordButton1.setBackground(Color.white);
		showpasswordButton1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0,Color.white));
		showpasswordButton1.setBounds(285,265,20,20);
		showpasswordButton1.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) { accPassword.setEchoChar((char) 0); }
			public void mouseReleased(MouseEvent e) { accPassword.setEchoChar('•'); }
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}	
		});
		
		accPassword = new JPasswordField();
		accPassword.setBounds(60,250,250,40);
		accPassword.setForeground(Color.gray);
		accPassword.setOpaque(false);
		accPassword.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		accPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		
		password = new JLabel();
		password.setText("Password");
		password.setForeground(Color.gray);
		password.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		password.setBounds(60,290,180,20);
		
		showpasswordButton2 = new JButton();
		showpasswordButton2.setIcon(eyeIcon);
		showpasswordButton2.setBackground(Color.white);
		showpasswordButton2.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0,Color.white));
		showpasswordButton2.setBounds(285,365,20,20);
		showpasswordButton2.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) { passwordConfirmation.setEchoChar((char) 0); }
			public void mouseReleased(MouseEvent e) { passwordConfirmation.setEchoChar('•'); }
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}	
		});
		
		passwordConfirmation = new JPasswordField();
		passwordConfirmation.setBounds(60,350,250,40);
		passwordConfirmation.setOpaque(false);
		passwordConfirmation.setForeground(Color.gray);
		passwordConfirmation.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		passwordConfirmation.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		
		confirmPassword = new JLabel();
		confirmPassword.setText("Confirm Password");
		confirmPassword.setForeground(Color.gray);
		confirmPassword.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		confirmPassword.setBounds(60,390,180,20);
		
		questions = new JComboBox<>(authenticatorQuestions);
		questions.setBounds(400, 230, 250, 30);
		questions.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  12));
		questions.setBackground(Color.white);
		questions.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		questions.setToolTipText(" Answer this question incase you forget your password "); 
		

		empName = new JTextField();
		empName.setBounds(400,150,250,40);
		empName.setForeground(Color.gray);
		empName.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		empName.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		
		empnameLbl = new JLabel();
		empnameLbl.setText("Employee Name");
		empnameLbl.setForeground(Color.gray);
		empnameLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		empnameLbl.setBounds(400,190,180,20);
		
		questions = new JComboBox<>(authenticatorQuestions);
		questions.setBounds(400, 260, 250, 30);
		questions.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  12));
		questions.setBackground(Color.white);
		questions.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		questions.setToolTipText(" Answer this question incase you forget your password "); 
		
		questionLbl = new JLabel();
		questionLbl.setText("Choose a questions");
		questionLbl.setForeground(Color.gray);
		questionLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		questionLbl.setBounds(400,290,180,20);
		
		questionAnswer = new JTextField();
		questionAnswer.setBounds(400,350,250,40);
		questionAnswer.setForeground(Color.gray);
		questionAnswer.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		questionAnswer.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		
		questionanswerLbl = new JLabel();
		questionanswerLbl.setText("Answer");
		questionanswerLbl.setForeground(Color.gray);
		questionanswerLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		questionanswerLbl.setBounds(400,390,180,20);
		
		signIn = new JButton("DONE");
		signIn.setBackground(Color.decode("#27AAE1"));
		signIn.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  20));
		signIn.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		signIn.setBounds(670,450,100,30);
		signIn.setFocusable(false);
		signIn.addActionListener(e -> { 
			af.newAdmin(employeeNum.getText(), String.valueOf(accPassword.getPassword()), empName.getText(), String.valueOf(passwordConfirmation.getPassword()), 
						(String) questions.getSelectedItem(),questionAnswer.getText()); 
			
			if(af.addingadminSuccessful) {
				employeeNum.setText("");
				accPassword.setText("");
				passwordConfirmation.setText("");
				empName.setText("");
				questionAnswer.setText("");
			}
			
		});
		
		
		cancel = new JButton("CANCEL");
		cancel.setBackground(Color.decode("#E5383B"));
		cancel.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  20));
		cancel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		cancel.setBounds(565,450,100,30);
		cancel.setFocusable(false);
		cancel.addActionListener(e -> { this.setVisible(false); });

		add(employeeNum);
		add(typeofAccLbl);
		add(showpasswordButton1);
		add(showpasswordButton2);
		add(accPassword);
		add(passwordConfirmation);
		add(confirmPassword);
		add(password);
		add(confirmPassword);
		add(empName);
		add(empnameLbl);
		add(questions);
		add(questionLbl);
		add(questionAnswer);
		add(questionanswerLbl);
		add(signIn);
		add(cancel);
		add(headerLbl);
		add(backtoLandingPage);
		this.setSize(800,500);
		this.getContentPane().setBackground(Color.white);
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setVisible(false);

	}
}
