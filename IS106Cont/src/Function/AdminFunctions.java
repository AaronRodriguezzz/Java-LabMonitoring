package Function;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;

public class AdminFunctions {
	public boolean conditionsApprove;
	public boolean addingadminSuccessful = false;
	public boolean changepassSuccessful = false;
	
	

public void generateWeeklyReportSql(DefaultTableModel model) {
	 LocalDate today = LocalDate.now();
     LocalDate firstDayOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
     LocalDate lastDayOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
		
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM overall_record " + "WHERE date >= ? AND date <= ?");

	        pstmt.setDate(1, Date.valueOf(firstDayOfWeek));
	        pstmt.setDate(2, Date.valueOf(lastDayOfWeek));
	        
			ResultSet rs = pstmt.executeQuery();
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
			 pstmt.close();
			 Conn.close();
			
		}catch(SQLException e2) {
			System.out.println("showw");
			e2.printStackTrace();
		}
	}
	

	public void generatereportSql(String firstDate, String secondDate, DefaultTableModel model) {
		
		
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM overall_record " + "WHERE date >= ? AND date <= ?");

	        pstmt.setDate(1, Date.valueOf(firstDate));
	        pstmt.setDate(2, Date.valueOf(secondDate));
	        
			ResultSet rs = pstmt.executeQuery();
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
			 pstmt.close();
			 Conn.close();
			
		}catch(SQLException e2) {
			System.out.println("showw");
			e2.printStackTrace();
		}
	}
	
	public void generateDailyReport(DefaultTableModel model) {
		LocalDate currentDate = LocalDate.now();
		System.out.println(currentDate);
		
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM overall_record " + "WHERE date = ?");
			

	        pstmt.setDate(1, Date.valueOf(currentDate));

			ResultSet rs = pstmt.executeQuery();
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
			 pstmt.close();
			 Conn.close();
			
		}catch(SQLException e2) {
			e2.printStackTrace();
		}
	}

	public void generateMessage(DefaultTableModel model) {
		
		
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM feedback");

			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				model.addRow(new Object[] {
					rs.getString("id"),
					rs.getDate("date"),
					rs.getString("message")
				});
					
			}
			 rs.close();
			 pstmt.close();
			 Conn.close();
			
		}catch(SQLException e2) {
			System.out.println("showw");
			e2.printStackTrace();
		}
	}
	
	public void generateMonthlyreportSql(DefaultTableModel model) {
		LocalDate today = LocalDate.now();
        int monthValue = today.getMonthValue();

		
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM overall_record WHERE MONTH (date) = " + monthValue);
			
			ResultSet rs = pstmt.executeQuery();
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
			 pstmt.close();
			 Conn.close();
			
		}catch(SQLException e2) {
			System.out.println("showw");
			e2.printStackTrace();
		}
	}


	public void showInformations(DefaultTableModel model) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM overall_record");
			ResultSet rs = pstmt.executeQuery();
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
			 pstmt.close();
			 Conn.close();
			
		}catch(SQLException e2) {
			System.out.println("showw");
			e2.printStackTrace();
		}
	}
	
	public void newAdmin(String empID, String password, String name, String passwordConfirm, String question, String answer) {
		
		try {
			if(!checkadminExistence(empID, name)) {
				if(newprofConditions(empID,password,name,passwordConfirm,question,answer)) {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
					PreparedStatement pstmt = con.prepareStatement("insert into admin_account (admin_name,admin_username,admin_password,question,answer) "+ "values (?,?,?,?,?)");		
				
					pstmt.setString(1,name);
					pstmt.setString(2,empID);
					pstmt.setString(3, password);
					pstmt.setString(4, question);
					pstmt.setString(5, answer);
				
					pstmt.executeUpdate();
					pstmt.close();
					addingadminSuccessful = true;
					JOptionPane.showMessageDialog(null, "New Admin Added", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				}else 	JOptionPane.showMessageDialog(null, "SAVING UNSUCCESSFULL \n CHECK YOUR INPUT", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
			}else JOptionPane.showMessageDialog(null, "The admin already existed \n Check the username or name of the admin");

	
			

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean newprofConditions(String empID, String password, String name, String passwordConfirm, String question, String answer) {
		Pattern pattern3 = Pattern.compile(".*[-!@#$%^&*()+=_}{|\"':;?/>.<,\\\\[\\\\]].*");
		Matcher matcher3 = pattern3.matcher(password);
		Pattern pattern4 = Pattern.compile(".*[-!@#$%^&*()+=_}{|\"':;?/>.<,\\\\[\\\\]].*");
		Matcher matcher4 = pattern4.matcher(name);
		Pattern pattern5 = Pattern.compile(".*[0-9].*");
		Matcher matcher5 = pattern5.matcher(name);
		
		
		if(!empID.equals("")&& !password.equals("") && !name.equals("") && !answer.equals("")) {
			if(password.length() >= 8) {
				if(matcher3.matches()) {
					if(password.equals(passwordConfirm)) {
						if(!matcher4.matches() && !matcher5.matches()) {
							
							if(empID.length() < 7 || password.length() < 8 || !matcher3.matches() || !password.equals(passwordConfirm) && matcher4.matches() || matcher5.matches()) {
								conditionsApprove=false;
							}else {
								conditionsApprove=true;
							}
							
						}else JOptionPane.showMessageDialog(null, "You Name should not contain any numbers and special characters", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
					}else JOptionPane.showMessageDialog(null, "Confirm Password Error", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				}else JOptionPane.showMessageDialog(null, "Your Password Must Contain Special Characters", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
			}else JOptionPane.showMessageDialog(null, "Your password must contain minimum of 8 characters", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
		}else JOptionPane.showMessageDialog(null, "Provide all the required information first", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

		
		return conditionsApprove;
	}
	
	public boolean checkadminExistence(String id, String name) {
		boolean existedName = false;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt0 = con.prepareStatement("SELECT COUNT(*) AS admin_username FROM admin_account"); 
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM admin_account"); 
			ResultSet rs0 = pstmt0.executeQuery();
			ResultSet rs = pstmt.executeQuery();
			
			
			if(rs0.next()) {
				while(rs.next()) {
					if(!name.equalsIgnoreCase(rs.getString("admin_name")) && !id.equalsIgnoreCase(rs.getString("admin_username"))) {
						existedName = false;
						System.out.println(name + rs.getString("admin_name"));
						System.out.println(id + rs.getString("admin_username"));


					}else {
						existedName = true;
						break;
					}
					
				}
			}else {
				existedName = false;
				System.out.println("empty database");
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

		return existedName;
	}
	
	public void changePassword(String adminUsername, String oldPassword, String newPassword) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt0 = con.prepareStatement("SELECT * FROM admin_account where admin_username = '" + adminUsername + "'"); 
			PreparedStatement pstmt = con.prepareStatement("UPDATE admin_account set admin_password = ? where admin_username = ?"); 
			ResultSet rs = pstmt0.executeQuery();
			
			pstmt.setString(1, newPassword);
			pstmt.setString(2, adminUsername);
			
			Pattern pattern = Pattern.compile(".*[-!@#$%^&*()+=_}{|\"':;?/>.<,\\\\[\\\\]].*");
			Matcher matcher = pattern.matcher(newPassword);
			
			if(rs.next()) {
				System.out.println(rs.getString("admin_password") + "  " + newPassword);
				if(rs.getString("admin_password").equalsIgnoreCase(oldPassword)) {
					if(newPassword.length() >=8) {
						if(matcher.matches()) {
							pstmt.execute();
							pstmt.close();
							changepassSuccessful = true;
						}else JOptionPane.showMessageDialog(null, "Your password should contain special characters", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
					}else JOptionPane.showMessageDialog(null, "Your password should have 8 characters above", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				}else JOptionPane.showMessageDialog(null, "Old password incorrect", "Error Warning", JOptionPane.WARNING_MESSAGE);
			}
			
			rs.close();
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "PLEASE CHECK YOUR INPUT");
		}
	}
	
	public void deleteFeedback(String message) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt0 = con.prepareStatement("DELETE FROM feedback where message = '" + message + "'" ); 
			
			pstmt0.execute();
			pstmt0.close();
			con.close();
		}catch(Exception e) {
			
		}
	}
}
