package Function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class LandingPageFunctions {
	public boolean loginAuthorize;
	public boolean adminRecognized;
	public String profName;
	public String question;
	public String answertoQuestion;
	public LandingPageFunctions(){}
	
	public String profLoginFunc(String username, String password) {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven" , "root" , "");
			PreparedStatement pstmt = con.prepareStatement("SELECT id,password,name FROM profs where id = ? AND password = ?");
			PreparedStatement pstmt1 = con.prepareStatement("SELECT admin_username,admin_password FROM admin_account where admin_username = ? AND admin_password = ?");
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt1.setString(1, username);
			pstmt1.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			ResultSet rs1 = pstmt1.executeQuery();
			
			while(rs1.next()) {
				if(rs1.getString("admin_username").equals(username) && rs1.getString("admin_password").equals(password) ) {
					adminRecognized = true;
					break;
				}else {
					adminRecognized = false;
				}
			}
			 
				
			rs1.close();
			

			if(rs.next()) {
				profName = rs.getString("name");
				loginAuthorize = true;
			}else {
				loginAuthorize = false;
			}
			
			rs.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return profName;
	
	}
	
	public boolean checkifUsernameExist(String id) {
		boolean idExisted = false;
		Pattern pattern1 = Pattern.compile(".*[a-zA-Z].*");
		Matcher matcher1 = pattern1.matcher(id);
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven" , "root" , "");
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM profs"); 
			PreparedStatement pstmt1 = con.prepareStatement("SELECT * FROM admin_account");
			ResultSet rs = pstmt.executeQuery();
			ResultSet rs1 = pstmt1.executeQuery();
			
			if(matcher1.matches()) {
				while(rs1.next()) {
					if(rs1.getString("admin_username").equals(id)) {
						idExisted = true;
					}
				}
				
			}else {
				
				while(rs.next()) {
					if(rs.getInt("id") == Integer.parseInt(id)) {
						idExisted = true;
					}
				}
				
			}
			
			rs1.close();
			rs.close();
			con.close();
			pstmt.close();
			pstmt1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idExisted;
	}
	
	public void retrieveQuestion(String id) {
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven" , "root" , "");
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM profs where id = '" + id + "'");
			PreparedStatement pstmt1 = con.prepareStatement("SELECT * FROM admin_account where admin_username = '" + id + "'");
			ResultSet rs = pstmt.executeQuery();
			ResultSet rs1 = pstmt1.executeQuery();
			
			while(rs1.next()) {
				if(rs1.getString("admin_username").equals(id)) {
					question = rs1.getString("question");
					answertoQuestion = rs1.getString("answer");
				}
			}
			
			rs1.close();

			while(rs.next()) {
				if(rs.getInt("id") == Integer.parseInt(id)) {
					question = rs.getString("question");
					answertoQuestion = rs.getString("answer");
				}
			}

			con.close();
			rs.close();
			rs1.close();
			pstmt.close();
			pstmt1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateoldPassword(String id, String newPassword) {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven" , "root" , "");
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM profs where id = '" + id + "'"); 
			PreparedStatement pstmt1 = con.prepareStatement("SELECT * FROM admin_account where admin_username = '" + id + "'");
			PreparedStatement pstmt2 = con.prepareStatement("UPDATE profs set password = '" + newPassword + "' where id = '" + id + "';");
			PreparedStatement pstmt3 = con.prepareStatement("UPDATE admin_account set admin_password = '" + newPassword + "' where admin_username = '" + id + "';");

			ResultSet rs = pstmt.executeQuery();
			ResultSet rs1 = pstmt1.executeQuery();
			
			while(rs1.next()) {
				if(rs1.getString("admin_username").equals(id)) {
					pstmt3.execute();
				}
			}
			
			rs1.close();

			
			while(rs.next()) {
				if(rs.getInt("id") == Integer.parseInt(id)) {
					pstmt2.execute();
				}
			}

			JOptionPane.showMessageDialog(null, "Password Updated");
			con.close();
			rs.close();
			rs1.close();
			pstmt.close();
			pstmt1.close();
			pstmt2.close();
			pstmt3.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
