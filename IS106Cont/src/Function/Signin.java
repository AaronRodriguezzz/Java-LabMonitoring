package Function;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class Signin {
	public boolean conditionsApprove;
	public Signin(){
		
	}
	
	public void newProfessor(String empID, String password, String passwordConfirm, String name, String question, String answer) {
	
		try {
			if(newprofConditions(empID,password,passwordConfirm,name,question,answer)) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
				PreparedStatement pstmt = con.prepareStatement("insert into profs (id,name,password,question,answer) "+ "values (?,?,?,?,?)");		
			
				pstmt.setString(1, empID);
				pstmt.setString(2,name);
				pstmt.setString(3, password);
				pstmt.setString(4, question);
				pstmt.setString(5, answer);
			
				pstmt.executeUpdate();
				pstmt.close();
				JOptionPane.showMessageDialog(null, "New Professor Added", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "SIGN IN ERROR", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	public boolean newprofConditions(String empID, String password, String passwordConfirm, String name, String question, String answer) {
        Pattern pattern0 = Pattern.compile(".*-.*-.*");
		Matcher matcher0 = pattern0.matcher(empID);
		Pattern pattern1 = Pattern.compile(".*[a-zA-Z].*");
		Matcher matcher1 = pattern1.matcher(empID);
		Pattern pattern2 = Pattern.compile(".*[-!@#$%^&*()+=_}{|\"':;?/>.<,\\\\[\\\\]].*");
		Matcher matcher2 = pattern2.matcher(empID);
		Pattern pattern3 = Pattern.compile(".*[-!@#$%^&*()+=_}{|\"':;?/>.<,\\\\[\\\\]].*");
		Matcher matcher3 = pattern3.matcher(password);
		Pattern pattern4 = Pattern.compile(".*[-!@#$%^&*()+=_}{|\"':;?/>.<,\\\\[\\\\]].*");
		Matcher matcher4 = pattern4.matcher(name);
		Pattern pattern5 = Pattern.compile(".*[0-9].*");
		Matcher matcher5 = pattern5.matcher(name);
		
		if(empID.length() < 8 && matcher0.matches()) JOptionPane.showMessageDialog(null, "Employee Id Number Invalid", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
		if(matcher1.matches() || matcher2.matches()) JOptionPane.showMessageDialog(null, "Use Numbers only for ID", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
		if(password.length() < 8) JOptionPane.showMessageDialog(null, "Weak Password", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
		if(!matcher3.matches()) JOptionPane.showMessageDialog(null, "Your Password Must Contain Special Characters", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
		if(!password.equals(passwordConfirm)) JOptionPane.showMessageDialog(null, "Confirm Password Error", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
		if(matcher4.matches() && matcher5.matches()) JOptionPane.showMessageDialog(null, "You Name should not contain any numbers and special characters", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
		if(empID.length() < 8 || matcher1.matches() || matcher2.matches() || password.length() < 8 || !matcher3.matches() || !password.equals(passwordConfirm) 
				&& matcher4.matches() || matcher5.matches()) conditionsApprove=false;
		else conditionsApprove=true;
		
		return conditionsApprove;
	}
}
