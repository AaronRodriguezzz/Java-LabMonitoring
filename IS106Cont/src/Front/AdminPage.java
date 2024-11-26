package Front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import Function.AdminFunctions;

public class AdminPage extends JFrame{
	String[] columns = {"Date", "Pc Number","ID","Student Name", "Course", "Status", "Time Enter", "Time Exit"};
	String[] columns1 = {"Id", "Date", "Message"};

	DefaultTableModel model;
	JScrollPane scrollPane;
	JTable table;
	
	JPanel sidePanel;
	JButton weeklyReport;
	JButton dailyReport;
	JButton monthlyReport;
	JButton backtoLandPage;
	JButton addAdmin;
	JButton changePassword;
	JButton openInfo;
	JButton deleteMessage;
	JButton openMessage;
	JTextField searchBar;
	
	JPanel changepasswordPanel;
	JPasswordField previousPassword;
	JLabel previousPasswordLbl;
	JPasswordField newPassword;
	JLabel newPasswordLbl;
	JButton save;
	JButton cancel;
	JButton showpreviousPassword;
	JButton shownewPassword;
	JButton openMail;


	AdminFunctions af = new AdminFunctions();
	AdditionalAdmin ad = new AdditionalAdmin();
	AdminPage(String adminUsername){
		
		ImageIcon mail = new ImageIcon("mail (2).png");
		ImageIcon back = new ImageIcon("arrow.png");
		ImageIcon acc = new ImageIcon("acc.png");
		ImageIcon password = new ImageIcon("lock.png");
		ImageIcon eyeIcon = new ImageIcon("eyeicon.png");

		
		model = new	DefaultTableModel();
		scrollPane = new JScrollPane();
		table = new JTable();
		
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		table.getTableHeader().setBackground(Color.white);
		table.getTableHeader().setForeground(Color.black);
		table.setSelectionBackground(Color.black);
		table.setSelectionForeground(Color.white);
		table.getTableHeader().setFont(new Font("Arial",Font.BOLD,13));
		table.getTableHeader().setEnabled(false);
		table.getTableHeader().setPreferredSize(new Dimension(0,15));
		table.setRowHeight(30);
		
		scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.black);
		scrollPane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		scrollPane.setBounds(60,45,730	,380);
		scrollPane.setVisible(true);
		
		weeklyReport = new JButton("Weekly Report");
		weeklyReport.setBackground(Color.decode("#27AAE1"));
		weeklyReport.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  14));
		weeklyReport.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		weeklyReport.setBounds(60,435,220,25);
		weeklyReport.setFocusable(false);
		weeklyReport.setForeground(Color.white);
		weeklyReport.addActionListener(e -> {
			model.setRowCount(0);
			af.generateWeeklyReportSql(model);
		});
		
		dailyReport = new JButton("Daily Report");
		dailyReport.setBackground(Color.decode("#27AAE1"));
		dailyReport.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  14));
		dailyReport.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		dailyReport.setBounds(290,435,170,25);
		dailyReport.setFocusable(false);
		dailyReport.setForeground(Color.white);
		dailyReport.addActionListener(e -> {
			model.setRowCount(0);
			af.generateDailyReport(model);
		});
		
		monthlyReport = new JButton("Monthly Report");
		monthlyReport.setBackground(Color.decode("#27AAE1"));
		monthlyReport.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  14));
		monthlyReport.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		monthlyReport.setBounds(470,435,170,25);
		monthlyReport.setFocusable(false);
		monthlyReport.setForeground(Color.white);
		monthlyReport.addActionListener(e -> {
			model.setRowCount(0);
			af.generateMonthlyreportSql(model);
		});
		
		deleteMessage = new JButton("Delete Message");
		deleteMessage.setBackground(Color.decode("#27AAE1"));
		deleteMessage.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  14));
		deleteMessage.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		deleteMessage.setBounds(60,435,220,25);
		deleteMessage.setFocusable(false);
		deleteMessage.setVisible(false);
		deleteMessage.setForeground(Color.white);
		deleteMessage.addActionListener(e -> {
			if(table.getSelectedRow() > -1) {
				af.deleteFeedback((String)table.getValueAt(table.getSelectedRow(), 2));
				model.setRowCount(0);
				model.setColumnIdentifiers(columns1);
				af.generateMessage(model);
			}else JOptionPane.showMessageDialog(null, "SELECT A ROW FIRST");
		});
		
		openMessage = new JButton("Open Message");
		openMessage.setBackground(Color.decode("#27AAE1"));
		openMessage.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  14));
		openMessage.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		openMessage.setBounds(290,435,170,25);
		openMessage.setFocusable(false);
		openMessage.setVisible(false);
		openMessage.setForeground(Color.white);
		openMessage.addActionListener(e -> {
			NewMessage nm = new NewMessage();
			if(table.getSelectedRow() > -1) {
				nm.messageTitle.setText((String)table.getValueAt(table.getSelectedRow(), 0));
				nm.textArea.setText((String)table.getValueAt(table.getSelectedRow(), 2));
				
				nm.messageFrame.setVisible(true);
				nm.cancel.setBounds(390, 330, 80, 25);
				nm.cancel.setText("X");
				nm.send.setVisible(false);
			}else JOptionPane.showMessageDialog(null, "SELECT A ROW FIRST");
			
			
		});
		
		
		searchBar = new JTextField();
		searchBar.setText("Search Information");
		searchBar.setBounds(60,10,260,30);
		searchBar.setBackground(Color.decode("#27AAE1"));
		searchBar.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  15));
		searchBar.setForeground(Color.black);
		searchBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		
		searchBar.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
				if(searchBar.getText().equals("Search Information")) searchBar.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(searchBar.getText().isEmpty()) searchBar.setText("Search Information");
			}	
		});
		
		searchBar.getDocument().addDocumentListener(new DocumentListener() {
			
			void search() {
				String searchText = searchBar.getText();
				try {
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/groupseven","root","");
				    Statement stmt = conn.createStatement();
				    ResultSet rs = stmt.executeQuery("SELECT * FROM overall_record " + " WHERE (date LIKE '%" +searchText+ "%' "
				    	+ "OR PcNumber LIKE '%" +searchText+ "%' "
				    		+ "OR id LIKE '%" +searchText+ "%' "
				    		+ "OR name LIKE '%" +searchText+ "%' "
				    		+ "OR course LIKE '%" +searchText+ "%' "
				    		+ "OR status LIKE '%" +searchText+ "%' "
				    		+ "OR time_enter LIKE '%" +searchText+ "%' "
				    		+ "OR time_end LIKE '%" +searchText+ "%')");
				    
					model.setRowCount(0);
				    while(rs.next()) {
						model.addRow(new Object[] {
							rs.getDate("date"),
							rs.getString("PcNumber"),
							rs.getString("id"),
							rs.getString("name"),
							rs.getString("course"),
							rs.getString("status"),
							rs.getTime("time_enter"),
							rs.getTime("time_end")
						});
							
					}
				    
				    rs.close();
				    stmt.close();
				    conn.close();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				search();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				search();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				search();
			}
		});
		
		
		backtoLandPage = new JButton();
		backtoLandPage.setBackground(Color.black);
		backtoLandPage.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  14));
		backtoLandPage.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		backtoLandPage.setBounds(0,20,50,50);
		backtoLandPage.setFocusable(false);
		backtoLandPage.setForeground(Color.white);
		backtoLandPage.setIcon(back);
		backtoLandPage.addActionListener(e -> {
			this.dispose();
		});
		
		
		changePassword = new JButton();
		changePassword.setBackground(Color.black);
		changePassword.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  14));
		changePassword.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		changePassword.setBounds(0,70,50,50);
		changePassword.setFocusable(false);
		changePassword.setForeground(Color.white);
		changePassword.setIcon(password);
		changePassword.addActionListener(e -> {
			changepasswordPanel.setVisible(true);
			scrollPane.setVisible(false);
			scrollPane.setVisible(false);
			searchBar.setVisible(false);
			weeklyReport.setVisible(false);
			dailyReport.setVisible(false);
			openMessage.setVisible(false);
			deleteMessage.setVisible(false);
			monthlyReport.setVisible(false);
		});
		
		addAdmin = new JButton("+");
		addAdmin.setBackground(Color.black);
		addAdmin.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  30));
		addAdmin.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		addAdmin.setBounds(0,120,50,50);
		addAdmin.setFocusable(false);
		addAdmin.setForeground(Color.decode("#27AAE1"));
		addAdmin.addActionListener(e -> {
			ad.setVisible(true);
			changepasswordPanel.setVisible(false);
		});
		
		
		
		openMail = new JButton();
		openMail.setBackground(Color.black);
		openMail.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  14));
		openMail.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		openMail.setBounds(0,170,50,50);
		openMail.setFocusable(false);
		openMail.setForeground(Color.white);
		openMail.setIcon(mail);
		openMail.addActionListener(e -> {

			changepasswordPanel.setVisible(false);
			ad.setVisible(false);
			weeklyReport.setVisible(false);
			dailyReport.setVisible(false);
			monthlyReport.setVisible(false);
			searchBar.setVisible(false);
			deleteMessage.setVisible(true);
			openMessage.setVisible(true);
			scrollPane.setVisible(true);
			
			model.setRowCount(0);
			model.setColumnIdentifiers(columns1);
			af.generateMessage(model);
			
			previousPassword.setText("");
			newPassword.setText("");
			

		});
		
		openInfo = new JButton();
		openInfo.setBackground(Color.black);
		openInfo.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  14));
		openInfo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		openInfo.setBounds(0,220,50,50);
		openInfo.setFocusable(false);
		openInfo.setForeground(Color.white);
		openInfo.setIcon(acc);
		openInfo.addActionListener(e -> {
			changepasswordPanel.setVisible(false);
			ad.setVisible(false);
			deleteMessage.setVisible(false);
			openMessage.setVisible(false);
			monthlyReport.setVisible(true);
			weeklyReport.setVisible(true);
			dailyReport.setVisible(true);
			monthlyReport.setVisible(true);
			scrollPane.setVisible(true);
			searchBar.setVisible(true);
			previousPassword.setText("");
			newPassword.setText("");
			model.setRowCount(0);
			model.setColumnIdentifiers(columns);
			af.showInformations(model);
			table.setRowHeight(30);

		});
		
		sidePanel = new JPanel();
		sidePanel.setBounds(0, 0, 50, 500);
		sidePanel.setBackground(Color.black);
		sidePanel.setLayout(null);
		sidePanel.add(addAdmin);
		sidePanel.add(changePassword);
		sidePanel.add(backtoLandPage);
		sidePanel.add(openMail);
		sidePanel.add(openInfo);
		
		save = new JButton("SAVE");
		save.setBackground(Color.decode("#27AAE1"));
		save.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  15));
		save.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		save.setBounds(130,210,80,40);
		save.setFocusable(false);
		save.setForeground(Color.white);
		save.addActionListener(e -> {
			af.changePassword(adminUsername, String.valueOf(previousPassword.getPassword()), String.valueOf(newPassword.getPassword()));
			
			if(af.changepassSuccessful) {
				changepasswordPanel.setVisible(false);
				scrollPane.setVisible(true);
				searchBar.setVisible(true);
				weeklyReport.setVisible(true);
				dailyReport.setVisible(true);
				monthlyReport.setVisible(true);
				previousPassword.setText("");
				newPassword.setText("");
			}
		
		});
		
		cancel = new JButton("X");
		cancel.setBackground(Color.black);
		cancel.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  25));
		cancel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.white));
		cancel.setBounds(90,210,40,40);
		cancel.setFocusable(false);
		cancel.setForeground(Color.white);
		cancel.addActionListener( e -> {
			changepasswordPanel.setVisible(false);
			scrollPane.setVisible(true);
			searchBar.setVisible(true);
			weeklyReport.setVisible(true);
			dailyReport.setVisible(true);
			monthlyReport.setVisible(true);
			previousPassword.setText("");
			newPassword.setText("");
			
			
			
		});
		
		previousPassword = new JPasswordField();
		previousPassword.setBounds(50,60,170,35);
		previousPassword.setForeground(Color.white);
		previousPassword.setBackground(Color.white);
		previousPassword.setOpaque(false);
		previousPassword.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		previousPassword.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
		
		showpreviousPassword = new JButton();
		showpreviousPassword.setIcon(eyeIcon);
		showpreviousPassword.setBackground(Color.white);
		showpreviousPassword.setFocusable(false);
		showpreviousPassword.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,Color.white));
		showpreviousPassword.setBounds(220,60,30,35);
		showpreviousPassword.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) { previousPassword.setEchoChar((char) 0); }
			public void mouseReleased(MouseEvent e) { previousPassword.setEchoChar('•'); }
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}	
		});
		
		
		previousPasswordLbl = new JLabel();
		previousPasswordLbl.setText("Previous Password");
		previousPasswordLbl.setForeground(Color.decode("#27AAE1"));
		previousPasswordLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		previousPasswordLbl.setBounds(85,95,180,20);
//		
		newPassword = new JPasswordField();
		newPassword.setBounds(50,130,170,35);
		newPassword.setForeground(Color.gray);
		newPassword.setBackground(Color.white);
		newPassword.setOpaque(false);
		newPassword.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		newPassword.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
		
		shownewPassword = new JButton();
		shownewPassword.setIcon(eyeIcon);
		shownewPassword.setBackground(Color.white);
		shownewPassword.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,Color.white));
		shownewPassword.setBounds(220,130,30,35);
		shownewPassword.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) { newPassword.setEchoChar((char) 0); }
			public void mouseReleased(MouseEvent e) { newPassword.setEchoChar('•'); }
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}	
		});
		
		
		newPasswordLbl = new JLabel();
		newPasswordLbl.setText("New Password");
		newPasswordLbl.setForeground(Color.decode("#27AAE1"));
		newPasswordLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		newPasswordLbl.setBounds(100,165,180,20);
		
		
		
		changepasswordPanel = new JPanel();
		changepasswordPanel.setBounds(250, 100, 300, 300);
		changepasswordPanel.setBackground(Color.black);
		changepasswordPanel.setLayout(null);
		changepasswordPanel.setVisible(false);
		changepasswordPanel.add(save);
		changepasswordPanel.add(cancel);
		changepasswordPanel.add(previousPassword);
		changepasswordPanel.add(previousPasswordLbl);
		changepasswordPanel.add(shownewPassword);
		changepasswordPanel.add(showpreviousPassword);
		changepasswordPanel.add(newPassword);
		changepasswordPanel.add(newPasswordLbl);
		
		add(changepasswordPanel);
		add(sidePanel);
		add(searchBar);
		add(monthlyReport);
		add(dailyReport);
		add(openMessage);
		add(deleteMessage);
		add(weeklyReport);
		add(scrollPane);
		this.setSize(800,500);
		this.getContentPane().setBackground(Color.gray);
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setVisible(true);
	}
	
}
