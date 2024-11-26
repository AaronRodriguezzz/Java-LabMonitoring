package Front;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Function.TableFunc;

public class StudentToPc extends JFrame{
	JButton save; 
	JButton add;
	JButton x;
	JLabel pc; 
	JLabel colon;
	JTextField pcNum; 
	JTextField hour; 
	JTextField minutes; 

	LocalTime currentTime;
	DateTimeFormatter formatter;
	String timeStart;
	String timeEnding;
	String timeLimit;
		
	public int hourNow, minutesNow;
	public int endHour,endMinutes;
	public int pcnumber = 0;

	public String strHour, strMinutes,id;
	public boolean repeatid;
	public boolean visible = true;
	public boolean saveTime = false;
	
	TableFunc tf = new TableFunc();
	PcRecords pr;
	
	StudentToPc(String id){
		this.id = id;
		
		x = new JButton("X");
		x.setBounds(280, 0, 20, 20);
		x.setFocusable(false);
		x.setBackground(Color.white);
		x.setForeground(Color.RED);
		x.setFont(new Font("Arial",Font.PLAIN,15));
		x.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		x.addActionListener(e -> { this.dispose(); });
		
		
		save = new JButton("SAVE");
		save.setFont(new Font("ARIAL", Font.PLAIN,18));
		save.setFocusable(false);
		save.setBackground(Color.black);
		save.setForeground(Color.white);
		save.setBounds(120,230,70,30);
		save.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		save.addActionListener(e -> {
			try {
				saveFunction(id);
				pcNum.setEnabled(true);
			}catch(Exception e1) {
				JOptionPane.showMessageDialog(null, "THERE'S AN ERROR IN YOUR INPUTS");
			}
		});	    
		
		add = new JButton("ADD");
		add.setFont(new Font("ARIAL", Font.PLAIN,18));
		add.setFocusable(false);
		add.setVisible(false);
		add.setBackground(Color.black);
		add.setForeground(Color.white);
		add.setBounds(120,230,70,30);
		add.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		add.addActionListener(e -> {
			try {
				addtimefunc();
			}catch(Exception e1) {
				JOptionPane.showMessageDialog(null, "THERE'S AN ERROR IN YOUR INPUTS");
			}
		});
		    
		pc = new JLabel("PC - ");
		pc.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		pc.setOpaque(false);
		pc.setBounds(70, 40, 120, 70);
		pc.setBackground(Color.white);
		pc.setForeground(Color.black);
		pc.setFont(new Font("ARIAL", Font.BOLD, 50));
		
	    
		pcNum = new JTextField("1");
		pcNum.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.black));
		pcNum.setOpaque(false);
		pcNum.setForeground(Color.black);
		pcNum.setBounds(180, 50, 50, 50);
		pcNum.setFont(new Font("ARIAL", Font.BOLD, 50));
		pcNum.setHorizontalAlignment(JTextField.CENTER);	
		    
		colon = new JLabel(" : ");
		colon.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		colon.setOpaque(false);
		colon.setBounds(140, 150, 50, 40);
		colon.setBackground(Color.white);
		colon.setForeground(Color.black);
		colon.setFont(new Font("ARIAL", Font.BOLD, 30));
		    
		hour = new JTextField("00");
		hour.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
		hour.setOpaque(false);
		hour.setBounds(70, 150, 70, 50);
		hour.setFont(new Font("ARIAL", Font.PLAIN, 40));
		hour.setHorizontalAlignment(JTextField.CENTER);

		    
		minutes = new JTextField("00");
		minutes.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
		minutes.setOpaque(false);
		minutes.setBounds(160, 150, 70, 50);
		minutes.setFont(new Font("ARIAL", Font.PLAIN, 40));
		minutes.setHorizontalAlignment(JTextField.CENTER);
		    
		add(pcNum);
		add(pc);
		add(colon);
		add(hour);
		add(minutes);
		add(save);
		add(add);
		add(x);
		this.setSize(300,300);
		this.getContentPane().setBackground(Color.white);
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setVisible(false);
	}
	
	public boolean checkforRepition(String id) {
		System.out.println(id);
	    try {
	    	Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pst = con.prepareStatement("SELECT id FROM pcrecords");	
			ResultSet rs = pst.executeQuery();
			
			//if(rs.next()) {
				while(rs.next()) {
					System.out.println(id + "==" + rs.getString("id"));
					if(id.equalsIgnoreCase(rs.getString("id"))) {
				    	JOptionPane.showMessageDialog(null, "EXISTED DATA");
						repeatid=true;
						break;
					}else repeatid=false;
				}
				
	    }catch(Exception e1) {
	    }
		return visible;
	}
	
	public void addtimefunc() {
		try {
		    	if(Integer.parseInt(hour.getText()) < 10 && Integer.parseInt(minutes.getText()) <= 59 ) {
		    		if(Integer.parseInt(hour.getText()) >= 0 && Integer.parseInt(minutes.getText()) >= 0) { 
		    			if(hour.getText().length() == 2 && minutes.getText().length() == 2 ) {
		    				
		    				String timeAdded = hour.getText() + ":" + minutes.getText();
		    				tf.updateEndTime(id,timeAdded);
		    				add.setVisible(false);
		    				pc.setVisible(true);
		    				pcNum.setVisible(true);
		    				save.setVisible(true);
		    				this.setVisible(false);
		    				
		    				//pr.model.setRowCount(0);
		    				//tf.pcRecord(pr.model);
		    				
	    			}else JOptionPane.showMessageDialog(null, "PUT 2 DIGITS IN EACH TIME FIELD");
	    		}else JOptionPane.showMessageDialog(null, "INVALID TIME");
	    	}else JOptionPane.showMessageDialog(null, "MAXIMUM HOUR IS 9 || MAXIMUM MINUTES IS 59");
		    	
		}catch(Exception e) {
	    	JOptionPane.showMessageDialog(null, "CHECK YOUR INPUT");
		}
	}
	
	public void saveFunction(String id) {
			pcnumber = Integer.parseInt(pcNum.getText());
		    checkforRepition(id);
		    try {
		    	
		    if(pcnumber != 0 && pcnumber <= 20) {
		    	if(Integer.parseInt(hour.getText()) < 10 && Integer.parseInt(minutes.getText()) <= 59 ) {
		    		if(Integer.parseInt(hour.getText()) >= 0 && Integer.parseInt(minutes.getText()) >= 0) { 
		    			if(hour.getText().length() == 2 && minutes.getText().length() == 2  ) {
		    				if(!repeatid) {
		    					try {
		    						currentTime = LocalTime.now();
		    						formatter = DateTimeFormatter.ofPattern("HH:mm");
		    						timeStart = currentTime.format(formatter);
		    						hourNow = currentTime.getHour(); //Integer.parseInt(hour.getText());
		    						minutesNow = currentTime.getMinute();  // Integer.parseInt(minutes.getText());
		    		
		    						
		    						endHour = hourNow + Integer.parseInt(hour.getText());
		    						endMinutes = minutesNow + Integer.parseInt(minutes.getText());
		    						if(endMinutes >= 60) {
		    							hourNow++;
		    							endMinutes = endMinutes - 60;
		    						}
		    					
		    						timeEnding = Integer.toString(endHour) + ":" + Integer.toString(endMinutes);
		    						timeLimit = hour.getText() + ":" + minutes.getText();

		    						tf.savetoPc(pcNum.getText(),id, timeStart, timeEnding,timeLimit);
		    						hour.setText("00");
		    						minutes.setText("00");
		    						this.dispose();
		    					}catch(Exception e1) {
		    						JOptionPane.showMessageDialog(null, "INVALID INPUT");
		    					}
		    				}else JOptionPane.showMessageDialog(null, "DATA ALREADY EXIST");
		    			}else JOptionPane.showMessageDialog(null, "PUT 2 DIGITS IN EACH TIME FIELD");
		    		}else JOptionPane.showMessageDialog(null, "INVALID TIME");
		    	}else JOptionPane.showMessageDialog(null, "MAXIMUM HOUR IS 9 || MAXIMUM MINUTES IS 59");
		    }else JOptionPane.showMessageDialog(null, "INVALID PC NUMBER");
		    
		    }catch(Exception e) {
		    	JOptionPane.showMessageDialog(null, "CHECK YOUR INPUT");
		    }
	}
}
