package Front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Function.TableFunc;

public class StudentTableSection {
	JButton signIn;
	JButton cancel;
	JButton addsectionButton;
	JTextField studentName;
	JTextField studentContact;
	JTextField studentCourseSec;
	JLabel studentNameLbl;
	JLabel studentCourseLbl;
	JLabel studentContactLbl;
	JLabel studentStatusLbl;
	JPanel updatestudent;

	JPanel studentstablePanel;
	DefaultTableModel model;
	JScrollPane scrollPane;
	JTable table;
	JButton removeStudent;
	JButton studentinfoUpdate;
	JComboBox<String>sections;
	JComboBox<String> studentStatus;
	
	String profName;
	String[] status = {"REGULAR", "IRREGULAR"};
	String[] sectionsList = {"SELECT SECTION"};
	String[] columns = {"Student ID", "Student Name","Student Section","Student Contact", "Student Status"};
	int rowCount=0;
	int selectedRow; 
	TableFunc tf = new TableFunc();
	
	StudentTableSection(String profName){
		this.profName = profName;
		
		model = new	DefaultTableModel();
		scrollPane = new JScrollPane();
		table = new JTable();
		
		sections = new JComboBox<>(sectionsList);
		sections.setBounds(10, 10, 200, 40);
		sections.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  15));
		sections.setBackground(Color.white);
		sections.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		sections.setToolTipText(" Sections "); 
		sections.setEditable(false);
		sections.addActionListener( e -> {
			model.setRowCount(0);
			tf.comboBoxSelect((String)sections.getSelectedItem(), model);
		});
		
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		table.getTableHeader().setBackground(Color.white);
		table.getTableHeader().setForeground(Color.black);
		table.setSelectionBackground(Color.black);
		table.setSelectionForeground(Color.white);
		table.getTableHeader().setFont(new Font("Arial",Font.BOLD,13));
		table.getTableHeader().setEnabled(false);
		table.getTableHeader().setPreferredSize(new Dimension(0,20));
		table.setRowHeight(30);
		
		model.setRowCount(0);
		tf.students(model,profName);
		
		scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.white);
		scrollPane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		scrollPane.setBounds(10,55,620,380);
		scrollPane.setVisible(true);
		
		removeStudent = new JButton("Remove Student");
		removeStudent.setBackground(Color.decode("#27AAE1"));
		removeStudent.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  17));
		removeStudent.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		removeStudent.setBounds(460,445,170,25);
		removeStudent.setFocusable(false);
		removeStudent.setForeground(Color.white);
		removeStudent.addActionListener(e -> { 
			if(table.getSelectedRow() > -1) {
				tf.deleteEmployee(model,table,table.getSelectedRow());
				model.setRowCount(0);
				tf.students(model,profName);
			}else JOptionPane.showMessageDialog(null, "SELECT ROW FIRST");
		});
		
		studentinfoUpdate = new JButton("Update Info");
		studentinfoUpdate.setBackground(Color.decode("#27AAE1"));
		studentinfoUpdate.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  17));
		studentinfoUpdate.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		studentinfoUpdate.setBounds(270,445,180,25);
		studentinfoUpdate.setFocusable(false);
		studentinfoUpdate.setForeground(Color.white);
		studentinfoUpdate.addActionListener(e -> {
			if(table.getSelectedRow() > -1) {
				studentInfo();
				visibility(true);
				selectedRow = table.getSelectedRow();
			}else JOptionPane.showMessageDialog(null, "SELECT ROW FIRST");
			
		});
		

		studentName = new JTextField();
		studentName.setBounds(220,100,250,40);
		studentName.setForeground(Color.gray);
		studentName.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		studentName.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		studentName.setVisible(false);
		
		studentNameLbl= new JLabel();
		studentNameLbl.setText("Student Full Name");
		studentNameLbl.setForeground(Color.white);
		studentNameLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		studentNameLbl.setBounds(220,140,180,20);
		studentNameLbl.setVisible(false);
		
		
		studentCourseSec = new JTextField();
		studentCourseSec.setBounds(220,170,250,40);
		studentCourseSec.setForeground(Color.gray);
		studentCourseSec.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		studentCourseSec.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		studentCourseSec.setVisible(false);
		
		
		studentCourseLbl = new JLabel();
		studentCourseLbl.setText("Course");
		studentCourseLbl.setForeground(Color.white);
		studentCourseLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		studentCourseLbl.setBounds(220,210,180,20);
		studentCourseLbl.setVisible(false);
		
		
		studentContact = new JTextField();
		studentContact.setBounds(220,240,250,40);
		studentContact.setForeground(Color.gray);
		studentContact.setFont(new Font(Font.DIALOG, Font.PLAIN,  15));
		studentContact.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		studentContact.setVisible(false);
		
		
		studentContactLbl = new JLabel();
		studentContactLbl.setText("Student Contact No.");
		studentContactLbl.setForeground(Color.white);
		studentContactLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		studentContactLbl.setBounds(220,280,180,20);
		studentContactLbl.setVisible(false);
		
		studentStatus = new JComboBox<>(status);
		studentStatus.setBounds(220,310,250,40);
		studentStatus.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  15));
		studentStatus.setBackground(Color.white);
		studentStatus.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		studentStatus.setToolTipText(" STATUS "); 
		studentStatus.setVisible(false);
		
		studentStatusLbl = new JLabel();
		studentStatusLbl.setText("Student Status");
		studentStatusLbl.setForeground(Color.white);
		studentStatusLbl.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  14));
		studentStatusLbl.setBounds(220,350,180,20);
		studentStatusLbl.setVisible(false);
		
		signIn = new JButton("Save Changes");
		signIn.setBackground(Color.decode("#27AAE1"));
		signIn.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  20));
		signIn.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		signIn.setBounds(220,390,200,30);
		signIn.setFocusable(false);
		signIn.setVisible(false);
		signIn.setForeground(Color.white);
		signIn.addActionListener(e -> {
			updateFunction(); 
			model.setRowCount(0);
			tf.students(model,profName);
		});
		
		
		cancel = new JButton("X");
		cancel.setBackground(Color.decode("#E5383B"));
		cancel.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  25));
		cancel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		cancel.setBounds(420,390,50,31);
		cancel.setFocusable(false);
		cancel.setForeground(Color.white);
		cancel.setVisible(false);
		cancel.addActionListener(e -> { 
			visibility(false);
		});
		
		studentstablePanel = new JPanel();
		studentstablePanel.setBounds(295,15,640,480);
		studentstablePanel.setBackground(Color.black);
		studentstablePanel.setVisible(false);
		studentstablePanel.setLayout(null);
		studentstablePanel.add(scrollPane);
		studentstablePanel.add(removeStudent);
		studentstablePanel.add(studentinfoUpdate);
		studentstablePanel.add(sections);
		studentstablePanel.add(studentName);
		studentstablePanel.add(studentNameLbl);
		studentstablePanel.add(studentCourseSec);
		studentstablePanel.add(studentCourseLbl);
		studentstablePanel.add(studentContact);
		studentstablePanel.add(studentContactLbl);
		studentstablePanel.add(studentStatus);
		studentstablePanel.add(studentStatusLbl);
		studentstablePanel.add(signIn);
		studentstablePanel.add(cancel);
		
	}
	
	public String[] showexistingSections() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt0 = con.prepareStatement("SELECT COUNT(*) AS sectionName FROM comsections"); 
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM comsections WHERE profName = '" + profName + "'"); 
			ResultSet rs0 = pstmt0.executeQuery();
			ResultSet rs = pstmt.executeQuery();


			/*int arrCount = 0;
			if(rs0.next()) {
				rowCount = rs0.getInt("sectionName");
				sectionsList = new String[rowCount];
				while(rs.next()) { 
					sectionsList[arrCount] = rs.getString("sectionName");
					sections.addItem(rs.getString("sectionName")); 
					++arrCount;
				}	
				
			}*/
			
			while(rs.next()) { 
				sections.addItem(rs.getString("sectionName")); 
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
	
	public void pageCustomization(boolean themeChoice) {
		if(!themeChoice) {
			sections.setBackground(Color.gray);
			sections.setForeground(Color.white);
			studentinfoUpdate.setBackground(Color.gray);
			studentinfoUpdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
			removeStudent.setBackground(Color.gray);
			removeStudent.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
			scrollPane.getViewport().setBackground(Color.gray);
			table.getTableHeader().setBackground(Color.gray);
			table.getTableHeader().setForeground(Color.white);
			table.setSelectionBackground(Color.gray);
			table.setSelectionForeground(Color.white);
			studentstablePanel.setBackground(Color.black);
			
			studentName.setBackground(Color.gray);
			studentName.setForeground(Color.white);
			studentNameLbl.setForeground(Color.gray);
			studentCourseSec.setBackground(Color.gray);
			studentCourseSec.setForeground(Color.white);
			studentCourseLbl.setForeground(Color.gray);
			studentContact.setBackground(Color.gray);
			studentContact.setForeground(Color.white);
			studentContactLbl.setForeground(Color.gray);
			studentStatus.setBackground(Color.gray);
			studentStatus.setForeground(Color.white);
			studentStatusLbl.setForeground(Color.gray);
			signIn.setBackground(Color.gray);
			cancel.setBackground(Color.black);
			cancel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));

		}else {
			sections.setBackground(Color.white);
			sections.setForeground(Color.black);
			studentinfoUpdate.setBackground(Color.decode("#27AAE1"));
			studentinfoUpdate.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.white));
			removeStudent.setBackground(Color.decode("#27AAE1"));
			removeStudent.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.white));
			scrollPane.getViewport().setBackground(Color.white);
			table.getTableHeader().setBackground(Color.white);
			table.getTableHeader().setForeground(Color.black);
			table.setSelectionBackground(Color.black);                         
			table.setSelectionForeground(Color.white);
			studentstablePanel.setBackground(Color.white);
			
			studentName.setBackground(Color.white);
			studentName.setForeground(Color.black);
			studentNameLbl.setForeground(Color.white);
			studentCourseSec.setBackground(Color.white);
			studentCourseSec.setForeground(Color.black);
			studentCourseLbl.setForeground(Color.white);
			studentContact.setBackground(Color.white);
			studentContact.setForeground(Color.black);
			studentContactLbl.setForeground(Color.white);
			studentStatus.setBackground(Color.white);
			studentStatus.setForeground(Color.black);
			studentStatusLbl.setForeground(Color.white);
			signIn.setBackground(Color.decode("#27AAE1"));
			cancel.setBackground(Color.decode("#E5383B"));
			cancel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
			
		}
		
	}
	
	public void studentInfo() {

		studentName.setText((String) table.getValueAt(table.getSelectedRow(),1));
		studentCourseSec.setText((String) table.getValueAt(table.getSelectedRow(),2));
		studentContact.setText((String) table.getValueAt(table.getSelectedRow(),3));
		
		if(((String)table.getValueAt(table.getSelectedRow(), 4)).equalsIgnoreCase("IRREGULAR")) {
			studentStatus.setSelectedIndex(1);
		}else studentStatus.setSelectedIndex(0);
	
	}
	
	public void updateFunction() {
		Pattern pattern1 = Pattern.compile(".*[a-zA-Z].*");
		Matcher matcher1 = pattern1.matcher(studentContact.getText());
		Pattern pattern2 = Pattern.compile(".*[-!@#$%^&*()+=_}{|\"':;?/>.<,\\\\[\\\\]].*");
		Matcher matcher2 = pattern2.matcher(studentName.getText());
		Pattern pattern3 = Pattern.compile(".*[-!@#$%^&*()+=_}{|\"':;?/>.<,\\\\[\\\\]].*");
		Matcher matcher3 = pattern3.matcher(studentContact.getText());
		Pattern pattern4 = Pattern.compile(".*[0-9].*");
		Matcher matcher4 = pattern4.matcher(studentName.getText());
		
		String text1 = studentName.getText();
		String text2 = studentCourseSec.getText();
		String text3 = studentContact.getText();

		if(!(text1.trim()).isEmpty() && !(text2.trim()).isEmpty() && !(text3.trim()).isEmpty() && text3.length() == 11)  {
			if(!matcher1.matches() && !matcher2.matches() && !matcher3.matches() && !matcher4.matches()) {
				visibility(false);
				tf.updateInformation((String)table.getValueAt(selectedRow, 0), text1, text2, text3, 
																(String)studentStatus.getSelectedItem());
			}else JOptionPane.showMessageDialog(null, "YOUR INPUT CONTAIN INVALID CHARACTERS", "ERROR", JOptionPane.WARNING_MESSAGE);
		}else JOptionPane.showMessageDialog(null, "INVALID INPUT", "ERROR", JOptionPane.WARNING_MESSAGE);
	}
	
	public void visibility(boolean visible) {
		studentName.setVisible(visible);
		studentNameLbl.setVisible(visible);
		studentCourseSec.setVisible(visible);
		studentCourseLbl.setVisible(visible);
		studentContact.setVisible(visible);
		studentContact.setVisible(visible);
		studentContactLbl.setVisible(visible);
		studentStatus.setVisible(visible);
		studentStatusLbl.setVisible(visible);
		signIn.setVisible(visible);
		cancel.setVisible(visible);
		sections.setVisible(!visible);
		scrollPane.setVisible(!visible);
		removeStudent.setVisible(!visible);
		studentinfoUpdate.setVisible(!visible);
	}
}
