package Front;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Function.LandingPageFunctions;

public class LandingPage extends JFrame{
	
	JLabel landingimgLbl;
	JLabel headerlbl;
	JLabel logolbl;
	JLabel profIdtxt;
	JLabel profpasstxt;
	JButton x;
	JButton loginButton;
	JButton signinButton;
	JButton forgotpassButton;
	JButton showpasswordButton;
	JButton adminButton;
	JTextField profId;
	JPasswordField profPass;
	
	JTextField questiontoAnswer;
	JPasswordField answertoQuestion;
	JPasswordField setnewPassword;
	JPasswordField confirmnewPassword;
	JButton proceedButt;
	JButton cancelButt;
	JButton saveButt;

	String forgotPasswordId;
	LandingPageFunctions lp = new LandingPageFunctions();
	signingIn s = new signingIn();

	LandingPage(){
		
		ImageIcon landingImg = new ImageIcon("lpImg.png");
		ImageIcon secondlandingImg = new ImageIcon("lpImg2.png");
		ImageIcon eyeIcon = new ImageIcon("eyeicon.png");
			
		x = new JButton("X");
		x.setBounds(780, 0, 20, 20);
		x.setFocusable(false);
		x.setBackground(Color.white);
		x.setForeground(Color.RED);
		x.setFont(new Font("Arial",Font.PLAIN,15));
		x.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		x.addActionListener(e -> { this.dispose(); });
		
		landingimgLbl = new JLabel();
		landingimgLbl.setIcon(landingImg);
		landingimgLbl.setBounds(0,0,400,500);
		
		headerlbl = new JLabel();
		headerlbl.setText("WELCOME");
		headerlbl.setForeground(Color.decode("#27AAE1"));
		headerlbl.setFont(new Font(Font.DIALOG, Font.BOLD,  40));
		headerlbl.setBounds(500,70,220,50);
		
		profId = new JTextField();
		profId.setBounds(450,170,300,40);
		profId.setHorizontalAlignment(JTextField.CENTER);
		profId.setForeground(Color.gray);
		profId.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		profId.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		
		
		
		profIdtxt = new JLabel();
		profIdtxt.setText("Employee ID Number");
		profIdtxt.setForeground(Color.gray);
		profIdtxt.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		profIdtxt.setBounds(530,210,180,20);
		
		showpasswordButton = new JButton();
		showpasswordButton.setIcon(eyeIcon);
		showpasswordButton.setBackground(Color.white);
		showpasswordButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0,Color.white));
		showpasswordButton.setBounds(730,265,20,20);
		showpasswordButton.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) { profPass.setEchoChar((char) 0); }
			public void mouseReleased(MouseEvent e) { profPass.setEchoChar('•'); }
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}	
		});
		
		profPass = new JPasswordField();
		profPass.setBounds(450,250,300,40);
		profPass.setHorizontalAlignment(JTextField.CENTER);
		profPass.setForeground(Color.gray);
		profPass.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		profPass.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		profPass.setOpaque(false);
		
		profpasstxt = new JLabel();
		profpasstxt.setText("Employee Passcode");
		profpasstxt.setForeground(Color.gray);
		profpasstxt.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		profpasstxt.setBounds(530,290,180,20);
	
		
		loginButton = new JButton("LOG IN");
		loginButton.setFont(new Font(Font.DIALOG, Font.PLAIN,16));
		loginButton.setFocusable(false);
		loginButton.setBackground(Color.decode("#27AAE1"));
		loginButton.setForeground(Color.white);
		loginButton.setBounds(440,370,220,40);
		loginButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		loginButton.addActionListener(e -> {
			if(profId.getText().isEmpty() || String.valueOf(profPass.getPassword()).isEmpty()) {
				JOptionPane.showMessageDialog(null, "INVALID LOG IN");
			}else {
				lp.profLoginFunc(profId.getText(), String.valueOf(profPass.getPassword())); 
				checkloginErrors();
			}
		});
		
		signinButton = new JButton("SIGN IN");
		signinButton.setFont(new Font(Font.DIALOG, Font.PLAIN,16));
		signinButton.setFocusable(false);
		signinButton.setBackground(Color.gray);
		signinButton.setForeground(Color.white);
		signinButton.setBounds(665,370,100,40);
		signinButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		signinButton.addActionListener(e -> {
			s.setVisible(true);
		});
		
		
		forgotpassButton = new JButton("Forgot Password?");
		forgotpassButton.setFont(new Font(Font.DIALOG, Font.BOLD,15));
		forgotpassButton.setFocusable(false);
		forgotpassButton.setBackground(Color.white);
		forgotpassButton.setForeground(Color.decode("#27AAE1"));
		forgotpassButton.setBounds(540,450,130,15);
		forgotpassButton.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#27AAE1")));
		forgotpassButton.addActionListener(e -> {
			forgotPasswordId = profId.getText();
			forgotPassword();
		});
		
		
		questiontoAnswer = new JTextField();
		questiontoAnswer.setBounds(450,170,300,40);
		questiontoAnswer.setHorizontalAlignment(JTextField.CENTER);
		questiontoAnswer.setForeground(Color.gray);
		questiontoAnswer.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		questiontoAnswer.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		questiontoAnswer.setToolTipText(" Answer this question to update your password "); 
		questiontoAnswer.setEnabled(false);
		questiontoAnswer.setVisible(false);
		
		
		answertoQuestion = new JPasswordField();
		answertoQuestion.setBounds(450,250,300,40);
		answertoQuestion.setHorizontalAlignment(JTextField.CENTER);
		answertoQuestion.setForeground(Color.gray);
		answertoQuestion.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		answertoQuestion.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		answertoQuestion.setOpaque(false);
		answertoQuestion.setVisible(false);

		
		setnewPassword = new JPasswordField();
		setnewPassword.setBounds(450,170,300,40);
		setnewPassword.setHorizontalAlignment(JTextField.CENTER);
		setnewPassword.setForeground(Color.gray);
		setnewPassword.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		setnewPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		setnewPassword.setVisible(false);
		
		
		confirmnewPassword = new JPasswordField();
		confirmnewPassword.setBounds(450,250,300,40);
		confirmnewPassword.setHorizontalAlignment(JTextField.CENTER);
		confirmnewPassword.setForeground(Color.gray);
		confirmnewPassword.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		confirmnewPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		confirmnewPassword.setOpaque(false);
		confirmnewPassword.setVisible(false);

		
		proceedButt = new JButton("PROCEED");
		proceedButt.setFont(new Font(Font.DIALOG, Font.PLAIN,16));
		proceedButt.setFocusable(false);
		proceedButt.setBackground(Color.decode("#27AAE1"));
		proceedButt.setForeground(Color.white);
		proceedButt.setBounds(440,370,220,40);
		proceedButt.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		proceedButt.setVisible(false);
		proceedButt.addActionListener(e -> {
			settingnewPasswordVisible();
		});
		
		cancelButt = new JButton("CANCEL");
		cancelButt.setFont(new Font(Font.DIALOG, Font.PLAIN,16));
		cancelButt.setFocusable(false);
		cancelButt.setBackground(Color.gray);
		cancelButt.setForeground(Color.white);
		cancelButt.setBounds(665,370,100,40);
		cancelButt.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		cancelButt.setVisible(false);
		cancelButt.addActionListener(e -> {
			cancelFunc();
		});
		
		saveButt = new JButton("SAVE");
		saveButt.setFont(new Font(Font.DIALOG, Font.PLAIN,16));
		saveButt.setFocusable(false);
		saveButt.setBackground(Color.decode("#27AAE1"));
		saveButt.setForeground(Color.white);
		saveButt.setBounds(440,370,220,40);
		saveButt.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		saveButt.setVisible(false);
		saveButt.addActionListener(e -> {
			savingnewPassword();
		});
		
		
		add(x);
		this.add(headerlbl);
		this.add(profId);
		this.add(profIdtxt);
		this.add(showpasswordButton);
		this.add(profPass);
		this.add(profpasstxt);
		this.add(landingimgLbl);
		this.add(loginButton);
		this.add(signinButton);
		this.add(forgotpassButton);
		this.add(questiontoAnswer);
		this.add(answertoQuestion);
		this.add(setnewPassword);
		this.add(confirmnewPassword);
		this.add(proceedButt);
		this.add(cancelButt);
		this.add(saveButt);
		this.setSize(800,500);
		this.getContentPane().setBackground(Color.white);
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setVisible(true);
		
		
		while(true) {
			try {
				Thread.sleep(10000);
				landingimgLbl.setIcon(secondlandingImg);
				Thread.sleep(10000);
				landingimgLbl.setIcon(landingImg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void checkloginErrors() {
		if(lp.adminRecognized) {
			//new AdminPage(profId.getText()).setVisible(false);;
			new AdminPage(profId.getText());
			profId.setText("");
			profPass.setText("");
			lp.adminRecognized = false;
		}else if(lp.loginAuthorize){
			// new ProfInterface(lp.profName).setVisible(false);;
			 new ProfInterface(lp.profName);
			 profId.setText("");
			 profPass.setText("");
			 lp.loginAuthorize = false;
		}else {
			JOptionPane.showMessageDialog(null, "INPUT ERROR");
			profId.setText("");
			profPass.setText("");
		}
	}
	
	public void forgotPassword() {
		try {
			if(!profId.getText().isEmpty()) {
				boolean existed = lp.checkifUsernameExist(forgotPasswordId);
				if(existed) {
					System.out.println("FORGOT PASSWORD VALID");
					lp.retrieveQuestion(profId.getText());
					questiontoAnswer.setText(lp.question);
					questiontoAnswer.setVisible(true);
					answertoQuestion.setVisible(true);
					proceedButt.setVisible(true);
					cancelButt.setVisible(true);
					headerlbl.setVisible(false);
					showpasswordButton.setVisible(false);
					profId.setVisible(false);
					profIdtxt.setVisible(false);
					profPass.setVisible(false);
					profpasstxt.setVisible(false);
					loginButton.setVisible(false);
					signinButton.setVisible(false);
					forgotpassButton.setVisible(false);
				}
			}
		}catch(Exception e) {
			
		}
		
	}
	
	public void settingnewPasswordVisible() {
		if(String.valueOf(answertoQuestion.getPassword()).isEmpty()) {
			JOptionPane.showMessageDialog(null, "Fill the blank first");
		}else if(String.valueOf(answertoQuestion.getPassword()).equals(lp.answertoQuestion)) {
			questiontoAnswer.setVisible(false);
			answertoQuestion.setVisible(false);
			showpasswordButton.setVisible(false);
			proceedButt.setVisible(false);
			cancelButt.setVisible(true);
			saveButt.setVisible(true);
			setnewPassword.setVisible(true);
			confirmnewPassword.setVisible(true);
			profIdtxt.setVisible(true);
			profpasstxt.setVisible(true);
			profIdtxt.setText("New Password");
			profpasstxt.setText("Confirm Password");
		}else JOptionPane.showMessageDialog(null, "INVALID INPUT");
	}
	
	public void savingnewPassword() {
		String newPass = String.valueOf(setnewPassword.getPassword());
		String conPass = String.valueOf(confirmnewPassword.getPassword());
		Pattern pattern = Pattern.compile(".*[-!@#$%^&*()+=_}{|\"':;?/>.<,\\\\[\\\\]].*");
		Matcher matcher = pattern.matcher(String.valueOf(newPass));
		
		if(matcher.matches()) {
			if(newPass.equals(conPass)) {
				questiontoAnswer.setVisible(false);
				answertoQuestion.setVisible(false);
				proceedButt.setVisible(false);
				cancelButt.setVisible(false);
				setnewPassword.setVisible(false);
				confirmnewPassword.setVisible(false);
				headerlbl.setVisible(true);
				profId.setVisible(true);
				profIdtxt.setVisible(true);
				profPass.setVisible(true);
				profpasstxt.setVisible(true);
				loginButton.setVisible(true);
				signinButton.setVisible(true);
				forgotpassButton.setVisible(true);
				showpasswordButton.setVisible(true);
				profId.setText("");
				profPass.setText("");
				profIdtxt.setText("Employee ID Number");
				profpasstxt.setText("Employee Passcode");
				lp.updateoldPassword(forgotPasswordId,newPass);
			} else JOptionPane.showMessageDialog(null, "Passwords doesn't match");
		}else JOptionPane.showMessageDialog(null, "Use special character for stronger password");

	}
	
	public void cancelFunc() {
		questiontoAnswer.setVisible(false);
		answertoQuestion.setVisible(false);
		proceedButt.setVisible(false);
		questiontoAnswer.setVisible(false);
		answertoQuestion.setVisible(false);
		proceedButt.setVisible(false);
		cancelButt.setVisible(false);
		setnewPassword.setVisible(false);
		confirmnewPassword.setVisible(false);
		headerlbl.setVisible(true);
		profId.setVisible(true);
		profIdtxt.setVisible(true);
		profPass.setVisible(true);
		profpasstxt.setVisible(true);
		loginButton.setVisible(true);
		signinButton.setVisible(true);
		forgotpassButton.setVisible(true);
		showpasswordButton.setVisible(true);
		profId.setText("");
		profPass.setText("");
		profIdtxt.setText("Employee ID Number");
		profpasstxt.setText("Employee Passcode");
	}
	
	
}
