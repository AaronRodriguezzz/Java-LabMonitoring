package Front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import Function.NewSectionFunc;
import Function.TableFunc;

public class ProfInterface extends JFrame {
	private static final long serialVersionUID = 1L;
	JFrame addSecFrame;
	JPanel sidePanel;
	JPanel leftPanel;
	JButton feedbackMessage;
	JButton addSection;
	JButton showPc;
	JButton showTable;
	JButton endClass;
	JButton scanner;
	JButton customButt;
	JTextField studentId;
	JTextField studentName;
	JTextField studentSection;
	JTextField studentStatus;
	JLabel studentPhoto;
	JLabel studentIdLbl;
	JLabel studentNameLbl;
	JLabel studentSectionLbl;
	JLabel studentStatusLbl;
	JLabel timerLbl;
	SimpleDateFormat sdf;
	BufferedImage img;
	BufferedImageLuminanceSource src;
	Result result;
	BinaryBitmap bitmap;
	String currentTime;
	String profName;
	
	//static boolean start = false;
	LocalDate currentDate;
	NewSection ns;
	PcRecords pr;
	StudentTableSection sts;
	TableFunc tf;
	StudentToPc stp;
	NewMessage nm;
	
	Webcam webcam;
	WebcamPanel webcamP;
	JPanel camPanel;
	Thread scanningThread;
	
	JButton[] pcButtons = new JButton[20];
	int i=0;
	boolean scan = false;
	boolean customDecision = false;
	
	@SuppressWarnings("unchecked")
	ProfInterface(String profName){
		this.profName = profName;
		
		ImageIcon sun = new ImageIcon("sun.png");
		ImageIcon moon = new ImageIcon("moon.png");
		
		currentDate = LocalDate.now();
		ns = new NewSection(profName);
		pr = new PcRecords(profName);
		sts = new StudentTableSection(profName);
		tf = new TableFunc();
		nm = new NewMessage();
		
		ns.webcam.close();
		ns.webcamP.stop();
		  
		sdf = new SimpleDateFormat("hh:mm:ss a");
	    currentTime = sdf.format(new Date());
	    
	    leftPanel = new JPanel();
		leftPanel.setBounds(950,0,240,510);
		leftPanel.setLayout(new GridLayout(10,2));
		leftPanel.setBackground(Color.black);
		
		for(int i=0;i<20;i++) {
			pcButtons[i] = new JButton();
	    	pcButtons[i].setSize(40,20);
	    	pcButtons[i].setFocusable(false);
	    	pcButtons[i].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0,Color.gray));
	    	pcButtons[i].setFont(new Font("ARIAL", Font.PLAIN,15));
	    	pcButtons[i].setBackground(Color.black);
	    	pcButtons[i].setForeground(Color.white);
	    	pcButtons[i].setText("PC - " + (i+1));
	    	leftPanel.add(pcButtons[i]); 	
		} 
		
		
		customButt = new JButton();
		customButt.setFont(new Font(Font.DIALOG, Font.PLAIN,14));
		customButt.setFocusable(false);
		customButt.setBackground(Color.black);
		customButt.setBounds(10,10,30,30);
		customButt.setIcon(moon);
	    customButt.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
	    customButt.addActionListener(e -> {
	    	if(!customDecision) {
	    		customButt.setIcon(sun);
	    		pageCustomization(customDecision);
	    		ns.pageCustomazation(customDecision);
	    		nm.pageCustomization(customDecision);
	    		pr.pageCustomization(customDecision);
	    		sts.pageCustomization(customDecision);
	    		customDecision = true; //darkmode
	    	}else {
	    		customButt.setIcon(moon);
	    		pageCustomization(customDecision);
	    		ns.pageCustomazation(customDecision);
	    		nm.pageCustomization(customDecision);
	    		pr.pageCustomization(customDecision);
	    		sts.pageCustomization(customDecision);
	    		customDecision = false; //lightmode
	    	}
	    	
	    	//cc.newSectionDesign(customDecision);
		});
		
		
	    
	    timerLbl = new JLabel(currentTime);
	    timerLbl.setForeground(Color.decode("#27AAE1"));
	    timerLbl.setFont(new Font(Font.DIALOG, Font.BOLD,  30));
	    timerLbl.setBounds(50,50,200,50);
	    
	    feedbackMessage = new JButton("FEEDBACK");
	    feedbackMessage.setFont(new Font(Font.DIALOG, Font.PLAIN,14));
	    feedbackMessage.setFocusable(false);
	    feedbackMessage.setBackground(Color.decode("#27AAE1"));
	    feedbackMessage.setForeground(Color.white);
	    feedbackMessage.setBounds(55,200,170,40);
	    feedbackMessage.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
	    feedbackMessage.addActionListener(e -> {
	    	nm.messageFrame.setVisible(false);
	    	nm.messageFrame.setVisible(true);
		});
	    
	    
	    scanner = new JButton("SCANNER");
	    scanner.setFont(new Font(Font.DIALOG, Font.PLAIN,14));
	    scanner.setFocusable(false);
	    scanner.setBackground(Color.decode("#27AAE1"));
	    scanner.setForeground(Color.white);
	    scanner.setBounds(55,250,170,40);
	    scanner.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
	    scanner.addActionListener(e -> {
	    	camConditions();
			ns.addsecPanel.setVisible(false);
			pr.pcrecPanel.setVisible(false);
			sts.studentstablePanel.setVisible(false);
		    camPanel.setVisible(true);
		    pr.stopScanningThread();
		});
		
		addSection = new JButton("ADD STUDENTS");
		addSection.setFont(new Font(Font.DIALOG, Font.PLAIN,14));
		addSection.setFocusable(false);
		addSection.setBackground(Color.decode("#27AAE1"));
		addSection.setForeground(Color.white);
		addSection.setBounds(55,300,170,40);
		addSection.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		addSection.addActionListener(e -> {
			stopScanningThread();
			scanner.setText("RESUME SCAN");
			ns.addsecPanel.setVisible(true);
			pr.pcrecPanel.setVisible(false);
			sts.studentstablePanel.setVisible(false);
			webcam.close();
			webcamP.stop();
			ns.webcam.open();
			ns.webcamP.start();
		    scanner.setEnabled(true);
		    camPanel.setVisible(false);
		    pr.stopScanningThread();
		});
		
		showPc = new JButton("SHOW UNITS");
		showPc.setFont(new Font(Font.DIALOG, Font.PLAIN,14));
		showPc.setFocusable(false);
		showPc.setBackground(Color.decode("#27AAE1"));
		showPc.setForeground(Color.white);
		showPc.setBounds(55,350,170,40);
		showPc.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		showPc.addActionListener(e -> {
			stopScanningThread();	
			scanner.setText("RESUME SCAN");
			ns.addsecPanel.setVisible(false);
			sts.studentstablePanel.setVisible(false);
			pr.pcrecPanel.setVisible(true);
			ns.webcam.close();
			ns.webcamP.stop();
			webcam.close();
			webcamP.stop();
		    scanner.setEnabled(true);
		    camPanel.setVisible(false);
		    pr.scan = false;
			pr.scanMethod();
			pr.model.setRowCount(0);
			tf.pcRecord(pr.model);			
		});
		
		showTable = new JButton("STUDENTS TABLE");
		showTable.setFont(new Font(Font.DIALOG, Font.PLAIN,14));
		showTable.setFocusable(false);
		showTable.setBackground(Color.decode("#27AAE1"));
		showTable.setForeground(Color.white);
		showTable.setBounds(55,400,170,40);
		showTable.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		showTable.addActionListener(e -> {
			stopScanningThread();	
			scanner.setText("RESUME SCAN");
			ns.addsecPanel.setVisible(false);
			pr.pcrecPanel.setVisible(false);
			sts.sections.removeAllItems();
			sts.showexistingSections();
			sts.studentstablePanel.setVisible(true);
			ns.webcam.close();
			ns.webcamP.stop();
			webcam.close();
			webcamP.stop();
		    scanner.setEnabled(true);
		    camPanel.setVisible(false);
		    sts.model.setRowCount(0);
			tf.students(sts.model,profName);
		    pr.stopScanningThread();

		});
		
		endClass = new JButton("END CLASS");
		endClass.setFont(new Font(Font.DIALOG, Font.PLAIN,14));
		endClass.setFocusable(false);
		endClass.setBackground(Color.decode("#27AAE1"));
		endClass.setForeground(Color.white);
		endClass.setBounds(55,450,170,40);
		endClass.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		endClass.addActionListener(e -> {
			tf.endClass();
			if(tf.endClassSucess) {
				stopScanningThread();
				this.setVisible(false);
				ns.webcam.close();
				ns.webcamP.stop();
				webcam.close();
				webcamP.stop();
			    scanner.setEnabled(true);
			    camPanel.setVisible(false);	
			    pr.stopScanningThread();
			}
		});
		
		
		/*
		 * student
		 * 
		 * side panel
		 * 
		 * section
		 * 
		 */
		
		studentPhoto = new JLabel();
		studentPhoto.setBounds(40, 20, 210, 210);
		studentPhoto.setHorizontalAlignment(JTextField.CENTER);
		studentPhoto.setForeground(Color.white);
		studentPhoto.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#27AAE1")));
		studentPhoto.setVisible(false);
		
		studentId = new JTextField();
		studentId.setBounds(35,250,220,30);
		studentId.setForeground(Color.white);
		studentId.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		studentId.setBackground(Color.black);
		studentId.setHorizontalAlignment(SwingConstants.CENTER);
		studentId.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,Color.decode("#27AAE1")));
		studentId.setEditable(false);
		studentId.setVisible(false);

		studentIdLbl = new JLabel();
		studentIdLbl.setText("Student ID");
		studentIdLbl.setForeground(Color.white);
		studentIdLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		studentIdLbl.setBounds(100, 280, 150, 20);
		studentIdLbl.setVisible(false);

		studentName = new JTextField();
		studentName.setBounds(35, 310, 220,30);
		studentName.setForeground(Color.white);
		studentName.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		studentName.setBackground(Color.black);
		studentName.setHorizontalAlignment(SwingConstants.CENTER);
		studentName.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,Color.decode("#27AAE1")));
		studentName.setEditable(false);
		studentName.setVisible(false);

		studentNameLbl = new JLabel();
		studentNameLbl.setText("Student Name");
		studentNameLbl.setForeground(Color.white);
		studentNameLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		studentNameLbl.setBounds(90, 340, 150, 20);
		studentNameLbl.setVisible(false);

		studentSection = new JTextField();
		studentSection.setBounds(30, 370, 230,30);
		studentSection.setForeground(Color.white);
		studentSection.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		studentSection.setBackground(Color.black);
		studentSection.setHorizontalAlignment(SwingConstants.CENTER);
		studentSection.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,Color.decode("#27AAE1")));
		studentSection.setEditable(false);
		studentSection.setVisible(false);

		studentSectionLbl = new JLabel();
		studentSectionLbl.setText("Student Section");
		studentSectionLbl.setForeground(Color.white);
		studentSectionLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		studentSectionLbl.setBounds(80,400,150,20);
		studentSectionLbl.setVisible(false);

		studentStatus = new JTextField();
		studentStatus.setBounds(30, 430, 230,30);
		studentStatus.setForeground(Color.white);
		studentStatus.setBackground(Color.black);
		studentStatus.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		studentStatus.setHorizontalAlignment(SwingConstants.CENTER);

		studentStatus.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,Color.decode("#27AAE1")));
		studentStatus.setEditable(false);
		studentStatus.setVisible(false);

		studentStatusLbl = new JLabel();
		studentStatusLbl.setText("Student Status");
		studentStatusLbl.setForeground(Color.white);
		studentStatusLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		studentStatusLbl.setBounds(90,460,180,20);
		studentStatusLbl.setVisible(false);
		
		sidePanel = new JPanel();
		sidePanel.setBounds(0, 0, 280, 530);
		sidePanel.setBackground(Color.black);
		sidePanel.setLayout(null);
		sidePanel.add(showPc);
		sidePanel.add(customButt);
		sidePanel.add(timerLbl);
		sidePanel.add(feedbackMessage);
		sidePanel.add(scanner);
		sidePanel.add(addSection);
		sidePanel.add(showTable);
		sidePanel.add(endClass);
		sidePanel.add(studentPhoto);
		sidePanel.add(studentId);
		sidePanel.add(studentIdLbl);
		sidePanel.add(studentName);
		sidePanel.add(studentNameLbl);
		sidePanel.add(studentSection);
		sidePanel.add(studentSectionLbl);
		sidePanel.add(studentStatus);
		sidePanel.add(studentStatusLbl);

		
		webcam =  Webcam.getDefault();
	    webcam.setViewSize(new Dimension(640,480));
	    webcamP  = new WebcamPanel(webcam);
	    webcamP.setMirrored(false);
	    
		camPanel = new JPanel();
		camPanel.setBounds(295, 15, 640, 480);
		camPanel.add(webcamP);

		add(sidePanel);
		add(ns.addsecPanel);
		add(pr.pcrecPanel);
		add(sts.studentstablePanel);
		add(camPanel);
		add(leftPanel);
		this.setSize(1190,510);
		this.getContentPane().setBackground(Color.white);
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setVisible(true);
		updateTime();
		
		if(!this.isVisible()) {
			ns.webcam.close();
			ns.webcamP.stop();
			webcam.close();
			webcamP.stop();
		}
	
	
	}
	
	public void updateTime() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				while(true)	{
					try {
						timerLbl.setText(sdf.format(Calendar.getInstance().getTime()));
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();		
	}
	
	public void setInfoVisible(boolean enable) {
		studentPhoto.setVisible(enable);
		studentId.setVisible(enable);
		studentIdLbl.setVisible(enable);
		studentName.setVisible(enable);
		studentNameLbl.setVisible(enable);
		studentSection.setVisible(enable);
		studentSectionLbl.setVisible(enable);
		studentStatus.setVisible(enable);
		studentStatusLbl.setVisible(enable);
		feedbackMessage.setVisible(!enable);
		timerLbl.setVisible(!enable);
		scanner.setVisible(!enable);
		addSection.setVisible(!enable);
		showPc.setVisible(!enable);
		showTable.setVisible(!enable);
		endClass.setVisible(!enable);
		
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getInfos(String id) {
		tf.getInfo(id);
		
		ImageIcon img0 = new ImageIcon(tf.imageData);
		Image image=img0.getImage().getScaledInstance(210, 210, java.awt.Image.SCALE_AREA_AVERAGING);
		img0 = new ImageIcon(image);
		studentPhoto.setIcon(img0);
		
		studentId.setText(tf.showId);
		studentName.setText(tf.showName);
		studentSection.setText(tf.showSection);
		studentStatus.setText(tf.showStatus);
	}
	
	public void camConditions() {
		
		if(scanner.getText().equals("SCANNER")) {
    		scanMethod();
    		scanner.setText("STOP SCAN");
    		ns.webcam.close();
			ns.webcamP.stop();
			webcamP.start();
			webcam.open();
    	}else if(scanner.getText().equals("RESUME SCAN")) {
    		scan = false;
    		scanMethod();
    		scanner.setText("STOP SCAN");
    		ns.webcam.close();
			ns.webcamP.stop();
			webcamP.start();
			webcam.open();
    	}else {
    		stopScanningThread();
    		scanner.setText("RESUME SCAN");
    		ns.webcam.close();
			ns.webcamP.stop();
			webcam.close();
			webcamP.stop();
    	}
	}
	
	public void pageCustomization(boolean themeChoice) {
		if(!themeChoice) {
			this.getContentPane().setBackground(Color.black);
			feedbackMessage.setBackground(Color.gray);
			addSection.setBackground(Color.gray);
			showPc.setBackground(Color.gray);
			showTable.setBackground(Color.gray);
			endClass.setBackground(Color.gray);
			scanner.setBackground(Color.gray);
			timerLbl.setForeground(Color.gray);
			for(int i=0;i<20;i++) {	
		    	pcButtons[i].setForeground(Color.gray);
			} 
		}else {
			this.getContentPane().setBackground(Color.white);
			feedbackMessage.setBackground(Color.decode("#27AAE1"));
			addSection.setBackground(Color.decode("#27AAE1"));
			showPc.setBackground(Color.decode("#27AAE1"));
			showTable.setBackground(Color.decode("#27AAE1"));
			endClass.setBackground(Color.decode("#27AAE1"));
			scanner.setBackground(Color.decode("#27AAE1"));
			timerLbl.setForeground(Color.decode("#27AAE1"));
			for(int i=0;i<20;i++) {	
		    	pcButtons[i].setForeground(Color.white);
			} 
		}
	}
	
	public void scanMethod() {		
		scanningThread = new Thread(() -> {
		    do{ // Loop until scan flag is true   	
		            
		    	tf.checkTime();
			    sidePanelfunc();
    
		        try {
		        	
		            img = webcam.getImage();
		            src = new BufferedImageLuminanceSource(img);
		            bitmap = new BinaryBitmap(new HybridBinarizer(src));
		            result = new MultiFormatReader().decode(bitmap);
		            
		            stp = new StudentToPc(result.getText());
		            stp.setVisible(false);
		            stp.setVisible(true);
		            
		            getInfos(result.getText()); 
		            setInfoVisible(true);
		            Thread.sleep(2000);
		            setInfoVisible(false);

		        } catch (Exception e) {
		            System.out.println("SCANNING");
		            try {
		                Thread.sleep(1500);
		            } catch (InterruptedException e1) {
			            System.out.println("CHECK CAMERA");

		            }
		        }
		        

		    }while(!scan);
		});

		scanningThread.start();
		
	}
	
	public void stopScanningThread() {
	    scan = true; // Set the flag to true to stop the scanning thread
	}

 void sidePanelfunc() {

		for(int i=0;i<20;i++) {
			pcButtons[i].setBackground(Color.black);
			
	 		for(int num2:tf.warningPc) {
	 			pcButtons[num2 -1].setBackground(Color.red);
	 		}
	 		
	 		for(int num: tf.pcNumbers) {
				pcButtons[num -1].setBackground(Color.GREEN);
			}

		}
			
	
	}
	
}
