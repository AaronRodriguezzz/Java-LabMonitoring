package Front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import Function.NewSectionFunc;
import Function.NewStudent;

public class NewSection {
	public JPanel addsecPanel;
	JButton finish;
	JButton signIn;
	JButton cancel;
	JButton addsectionButton;
	JButton qrGenerate;
	JTextField studentId;
	JTextField studentName;
	JTextField studentContact;
	JLabel studentIdLbl;
	JLabel studentNameLbl;
	JLabel studentContactLbl;
	JLabel studentStatusLbl;
	JLabel qrLbl;
	JComboBox<String> sections;
	JComboBox<String> studentStatus;
	String[] sectionsList = {"SELECT SECTION"};
	String[] status = {"REGULAR", "IRREGULAR"};
	PicTakingCamera ptc;
	LocalDate currentDate;
	String profName;
	static int rowCount = 0;
	static boolean addstudLaststep;
	
	NewSectionFunc nsf = new NewSectionFunc();
	NewStudent ns = new NewStudent();
	StudentTableSection sts;

	Webcam webcam;
	WebcamPanel webcamP;
	JFrame cameraFrame;
	JPanel camPanel;

	NewSection(String profName){
		this.profName = profName;
		currentDate = LocalDate.now();
		sts = new StudentTableSection(profName);
		
		sections = new JComboBox<>(sectionsList);
		showexistingSections();
		sections.setBounds(20, 20, 200, 40);
		sections.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  15));
		sections.setBackground(Color.white);
		sections.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		sections.setEditable(false);
		sections.setToolTipText(" Sections "); 
		
		addsectionButton = new JButton("+");
		addsectionButton.setBackground(Color.black);
		addsectionButton.setFont(new Font("ARIAL", Font.PLAIN, 35));
		addsectionButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		addsectionButton.setBounds(220,21,30,37);
		addsectionButton.setForeground(Color.decode("#27AAE1"));
		addsectionButton.setFocusable(false);
		addsectionButton.addActionListener(e -> {
			addsecConditions();
		});
	
		studentId = new JTextField();
		studentId.setBounds(340,100,250,40);
		studentId.setForeground(Color.gray);
		studentId.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		studentId.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		
		studentIdLbl = new JLabel();
		studentIdLbl.setText("Student ID");
		studentIdLbl.setForeground(Color.white);
		studentIdLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		studentIdLbl.setBounds(340,140,180,20);

		studentName = new JTextField();
		studentName.setBounds(340,170,250,40);
		studentName.setForeground(Color.gray);
		studentName.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		studentName.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		
		studentNameLbl= new JLabel();
		studentNameLbl.setText("Student Full Name");
		studentNameLbl.setForeground(Color.white);
		studentNameLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		studentNameLbl.setBounds(340,210,180,20);
		
		studentContact = new JTextField();
		studentContact.setBounds(340,240,250,40);
		studentContact.setForeground(Color.gray);
		studentContact.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		studentContact.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		
		studentContactLbl = new JLabel();
		studentContactLbl.setText("Student Contact No.");
		studentContactLbl.setForeground(Color.white);
		studentContactLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		studentContactLbl.setBounds(340,280,180,20);
		
		studentStatus = new JComboBox<>(status);
		studentStatus.setBounds(340,310,250,40);
		studentStatus.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  15));
		studentStatus.setBackground(Color.white);
		studentStatus.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		studentStatus.setToolTipText(" STATUS "); 
		
		studentStatusLbl = new JLabel();
		studentStatusLbl.setText("Student Status");
		studentStatusLbl.setForeground(Color.white);
		studentStatusLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		studentStatusLbl.setBounds(340,350,180,20);
		
		qrLbl = new JLabel("Qr Code Generator");
		qrLbl.setBounds(20, 100, 300, 350);
		qrLbl.setHorizontalAlignment(JTextField.CENTER);
		qrLbl.setForeground(Color.white);
		qrLbl.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		
		qrGenerate = new JButton("GENERATE QR");
		qrGenerate.setBackground(Color.decode("#27AAE1"));
		qrGenerate.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  20));
		qrGenerate.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		qrGenerate.setBounds(80,455,170,20);
		qrGenerate.setForeground(Color.white);
		qrGenerate.setFocusable(false);
		qrGenerate.setHorizontalAlignment(JTextField.CENTER);
		qrGenerate.addActionListener(e -> { 
			generatebuttonConditions(studentId.getText(),studentName.getText(),studentContact.getText(),
									(String)studentStatus.getSelectedItem(),(String)sections.getSelectedItem());
			
		});
		
		
		signIn = new JButton("PROCEED");
		signIn.setBackground(Color.decode("#27AAE1"));
		signIn.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  25));
		signIn.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		signIn.setBounds(340,400,200,30);
		signIn.setFocusable(false);
		signIn.setForeground(Color.white);
		signIn.addActionListener(e -> { 
			proceedButton(); 
		});
		
		cancel = new JButton("X");
		cancel.setBackground(Color.decode("#E5383B"));
		cancel.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  25));
		cancel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		cancel.setBounds(540,400,50,31);
		cancel.setFocusable(false);
		cancel.setForeground(Color.white);
		cancel.addActionListener(e -> { 
			studentId.setText("");
			studentName.setText("");
			studentContact.setText("");
		});
		
		addsecPanel = new JPanel();
		addsecPanel.setBounds(295, 15, 640, 480);
		addsecPanel.setBackground(Color.black);
		addsecPanel.setVisible(false);
		addsecPanel.setLayout(null);
		addsecPanel.add(sections);
		addsecPanel.add(addsectionButton);
		addsecPanel.add(studentId);
		addsecPanel.add(studentIdLbl);
		addsecPanel.add(studentName);
		addsecPanel.add(studentNameLbl);
		addsecPanel.add(studentContact);
		addsecPanel.add(studentContactLbl);
		addsecPanel.add(studentStatus);
		addsecPanel.add(studentStatusLbl);
		addsecPanel.add(qrLbl);		
		addsecPanel.add(qrGenerate);
		addsecPanel.add(signIn);
		addsecPanel.add(cancel);
		
		webcam =  Webcam.getDefault();
	    webcam.setViewSize(new Dimension(320,240));
	    webcamP  = new WebcamPanel(webcam);
	    webcamP.setMirrored(false);
	    
		camPanel = new JPanel();
		camPanel.setBounds(10, 10, 320, 240);
		camPanel.add(webcamP);
	     
		cameraFrame = new JFrame();
		cameraFrame.setSize(340,290);
		cameraFrame.getContentPane().setBackground(Color.white);
		cameraFrame.setLayout(null);
		cameraFrame.setResizable(false);
		cameraFrame.setLocationRelativeTo(null);
		cameraFrame.setUndecorated(true);
		cameraFrame.setVisible(false);
		cameraFrame.setLocation(970, 500);
		cameraFrame.add(camPanel);
		
	}
	
	public void proceedButton() {
		if(qrLbl.getIcon() != null) {
			ns.addingnewStudent(studentId.getText(), studentName.getText(), (String) sections.getSelectedItem() , 
					studentContact.getText(), (String)studentStatus.getSelectedItem(),
					qrLbl.getIcon(),webcam.getImage(),profName);
			cancel.setEnabled(true);
			qrGenerate.setEnabled(true);
			studentId.setEditable(true);
			studentName.setEditable(true);
			studentContact.setEditable(true);
			studentStatus.setEditable(true);
			cameraFrame.setVisible(false);
			webcam.close();
			webcamP.stop();
			qrLbl.setIcon(null);
			studentId.setText("");
			studentName.setText("");
			studentContact.setText("");
		}else {
			JOptionPane.showInternalMessageDialog(null, "GENERATE QR CODE FIRST");
		}

	}
	
	public void generatebuttonConditions(String id,String name,String contact,String status, String section) {
		Pattern pattern1 = Pattern.compile(".*[a-zA-Z].*");
		Matcher matcher1 = pattern1.matcher(id);
		Pattern pattern2 = Pattern.compile(".*[!@#$%^&*()+=_}{|\"':;?/>.<,\\\\[\\\\]].*");
		Matcher matcher2 = pattern2.matcher(id);
		Pattern pattern3 = Pattern.compile(".*[a-zA-Z].*");
		Matcher matcher3 = pattern3.matcher(contact);
		Pattern pattern4 = Pattern.compile(".*[-!@#$%^&*()+=_}{|\"':;?/>.<,\\\\[\\\\]].*");
		Matcher matcher4 = pattern4.matcher(name);
		Pattern pattern5 = Pattern.compile(".*[0-9].*");
		Matcher matcher5 = pattern5.matcher(name);
		
		if(id.isEmpty() || name.isEmpty() || contact.isEmpty() || status.isEmpty() || section.equals("SELECT SECTION")) {
			JOptionPane.showInternalMessageDialog(null, "COMPLETE ALL INPUTS");
		}else if(!matcher1.matches() && !matcher2.matches() && !matcher4.matches() && !matcher5.matches() && !matcher3.matches()){
			addstudLaststep = ns.checkstudentExistence(id,name);
			if(!addstudLaststep) {
				qrLbl.setIcon(ns.qrcodeFunction(id));
				cameraFrame.setVisible(true);
				qrGenerate.setEnabled(false);
				studentId.setEditable(false);
				studentName.setEditable(false);
				studentContact.setEditable(false);
				studentStatus.setEditable(false);
				sections.setEditable(false);
				cancel.setEnabled(false);
				addstudLaststep = true;
			}else {
				JOptionPane.showInternalMessageDialog(null, "STUDENT ALREADY EXISTED");
			}
		}else if(matcher1.matches() || matcher2.matches()) {
			JOptionPane.showInternalMessageDialog(null, "INVALID ID \n You can only use number(s) and dash");
		}else if(matcher4.matches() || matcher5.matches()) {
			JOptionPane.showInternalMessageDialog(null, "INVALID NAME \n You can only use letters");
		}else if(matcher3.matches()) {
			JOptionPane.showInternalMessageDialog(null, "INVALID CONTACT NUMBER \n You can only use numbers");
		}
		
	
	}
	
	public String[] showexistingSections() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt0 = con.prepareStatement("SELECT COUNT(*) AS sectionName FROM comsections"); 
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM comsections WHERE profName = '" + profName + "'"); 
			ResultSet rs0 = pstmt0.executeQuery();
			ResultSet rs = pstmt.executeQuery();


			int arrCount = 0;
			if(rs0.next()) {
				rowCount = rs0.getInt("sectionName");
				sectionsList = new String[rowCount];
				if(rowCount != 0) {
					while(rs.next()) { 
						sections.addItem(rs.getString("sectionName")); 
						sectionsList[arrCount] = rs.getString("sectionName");
					}	
				}
			}
			
			rs.close();
			rs.close();
			pstmt0.close();
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "PLEASE CHECK YOUR INPUT");
		}
		return sectionsList;
	}
	
	public void addsecConditions() {
		String course = JOptionPane.showInputDialog(null,"Input the course");
		if(course != null && !course.contains(" ")) {
			String section = JOptionPane.showInputDialog(null,"Input the section");
			if(section != null && !section.contains(" ")) {
				String courseSection =course+"-"+section;

				if(sectionsList.length > 1) {
					nsf.newSection(courseSection, currentDate, profName);
					if(!nsf.checkforExistence) {
						sections.addItem(courseSection);
						sts.sections.addItem(courseSection);
					}
				}else {
					nsf.newSection(courseSection,currentDate, profName);
					sections.addItem(courseSection);
				}
				
			}else {
				JOptionPane.showMessageDialog(null, "DON'T USE SPACEBAR");
			}
		}else {
			JOptionPane.showMessageDialog(null, "DON'T USE SPACEBAR");
		}
	}
	
	public void pageCustomazation(boolean themeChoice) {
		if(!themeChoice) {
			camPanel.setBackground(Color.gray);
			sections.setBackground(Color.gray);
			sections.setForeground(Color.white);
			studentId.setBackground(Color.gray);
			studentId.setForeground(Color.white);
			studentIdLbl.setForeground(Color.gray);
			studentName.setBackground(Color.gray);
			studentName.setForeground(Color.white);
			studentNameLbl.setForeground(Color.gray);
			studentContact.setBackground(Color.gray);
			studentContact.setForeground(Color.white);
			studentContactLbl.setForeground(Color.gray);
			studentStatus.setBackground(Color.gray);
			studentStatus.setForeground(Color.gray);
			studentStatusLbl.setForeground(Color.gray);
			qrLbl.setForeground(Color.gray);
			qrGenerate.setBackground(Color.gray);
			signIn.setBackground(Color.gray);
			cancel.setBackground(Color.black);
			
		}else {
			camPanel.setBackground(Color.white);
			sections.setBackground(Color.white);
			sections.setForeground(Color.black);
			studentId.setBackground(Color.white);
			studentId.setForeground(Color.black);
			studentIdLbl.setForeground(Color.white);
			studentName.setBackground(Color.white);
			studentName.setForeground(Color.black);
			studentNameLbl.setForeground(Color.white);
			studentContact.setBackground(Color.white);
			studentContact.setForeground(Color.black);
			studentContactLbl.setForeground(Color.white);
			studentStatus.setBackground(Color.white);
			studentStatus.setForeground(Color.black);
			studentStatusLbl.setForeground(Color.white);
			qrLbl.setForeground(Color.white);
			qrGenerate.setBackground(Color.decode("#27AAE1"));
			signIn.setBackground(Color.decode("#27AAE1"));
			cancel.setBackground(Color.red);
		}
		
	}
	

}
