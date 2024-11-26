package Function;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.sql.Date;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Front.PicTakingCamera;

public class NewSectionFunc extends JFrame {

    LocalDate currentDate; 
	public boolean checkforExistence = true;
	public NewSectionFunc() {}

	public void newSection(String secName,LocalDate dateVal,String profName) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO comsections(sectionName,date,profName)"+ " values (?,?,?); ");
			PreparedStatement pstmt1 = con.prepareStatement("SELECT sectionName FROM comsections");
			ResultSet rs = pstmt1.executeQuery();
			
			while(rs.next()) {
				if(!secName.equalsIgnoreCase(rs.getString("sectionName"))) {
					checkforExistence = false;
				}else {
					checkforExistence = true;
					break;
				}
			}

			if(!checkforExistence) {
				pstmt.setString(1,secName);
				pstmt.setDate(2,Date.valueOf(dateVal));
				pstmt.setString(3, profName);
				pstmt.executeUpdate();
				pstmt.close();	
			}else {
				JOptionPane.showMessageDialog(null, "THE SECTION ALREADY EXISTED");
			}
		
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "PLEASE CHECK YOUR INPUT");
		}
	}



}	
