package Function;

import java.awt.Color;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TableFunc {
	String name; 
	String course;
	String status; 
	
	public String showName;
	public String showId;
	public String showStatus;
	public String showSection;
	public byte[] imageData;
	public boolean endClassSucess = false;
	public boolean backToBlack = false;
	public int numberToRemove = 0;
	
	public LinkedList<Integer> pcNumbers  = new LinkedList<>();
    public LinkedList<Integer> availPc = new LinkedList<>();
    public LinkedList<Integer> warningPc = new LinkedList<>();

	public void comboBoxSelect(String section, DefaultTableModel model) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM studentstable where section = '" + section + "'");
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				model.addRow(new Object[] {
					rs.getString("student_id"),
					rs.getString("studentName"),
					rs.getString("section"),
					rs.getString("studentContact"),
					rs.getString("studentStatus")
				});
					
			}
			 rs.close();
			 pstmt.close();
			 Conn.close();
			
		}catch(SQLException e2) {
			//e2.printStackTrace();
		}
	}
	
	public byte[] getInfo(String id) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven" , "root" , "");
			PreparedStatement pst0 = con.prepareStatement("SELECT * FROM studentstable where student_id = '" + id + "'");
			ResultSet rs0 = pst0.executeQuery();
			
			while(rs0.next()) {
                Blob blob = rs0.getBlob("studentPhoto");
                this.imageData = blob.getBytes(1, (int) blob.length());
				this.showId = rs0.getString("student_id");
				this.showName = rs0.getString("studentName");
				this.showSection = rs0.getString("section");
				this.showStatus = rs0.getString("studentStatus");

			}
			
		}catch(Exception e) {
			//e.printStackTrace();
		}
		return imageData;
		
	}
	
	public void students(DefaultTableModel model, String profName) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM studentstable where profName = '" + profName + "'");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				model.addRow(new Object[] {
					rs.getString("student_id"),
					rs.getString("studentName"),
					rs.getString("section"),
					rs.getString("studentContact"),
					rs.getString("studentStatus")
				});
					
			}
			 rs.close();
			 pstmt.close();
			 Conn.close();
			
		}catch(SQLException e2) {
			//e2.printStackTrace();
		}
	}
	
	public void pcRecord(DefaultTableModel model) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM pcrecords");
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				model.addRow(new Object[] {
					rs.getInt("PcNumber"),
					rs.getString("id"),
					rs.getString("StudentName"),
					rs.getString("courseName"),
					rs.getString("status"),
					rs.getTime("EndTime")
				});
					
			}
			
			rs.close();
			pstmt.close();
			Conn.close();
			
		}catch(SQLException e2) {
			//e2.printStackTrace();
		}
	}

	
	public void updateEndTime(String id,String end) {
		String updatedTime = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven" , "root" , "");
			PreparedStatement pst0 = con.prepareStatement("SELECT * FROM pcrecords WHERE id = '" + id + "';");
			PreparedStatement pst = con.prepareStatement("UPDATE pcrecords" + " SET EndTime = ? WHERE id = '" + id + "';");
			ResultSet rs = pst0.executeQuery();
			
			while(rs.next()) {
				System.out.println("okayokayokay");
				Time targetTime = rs.getTime("EndTime");
				Time timeValue = Time.valueOf(end + ":00");
				LocalTime timeToLocal = targetTime.toLocalTime();
				LocalTime addedtimeToLocal = timeValue.toLocalTime();
				System.out.println(timeToLocal + "  " + addedtimeToLocal);

				int endtimeHour = timeToLocal.getHour();
				int endtimeMin = timeToLocal.getMinute();
				
				int addedHour = addedtimeToLocal.getHour();
				int addMin = addedtimeToLocal.getMinute();
				
				int totalHour = endtimeHour + addedHour;
				int totalMinutes = endtimeMin + addMin;
				
				if(totalMinutes >= 60) {
					totalMinutes = totalMinutes - 60;
					totalHour++;
				}
				
				/*if(totalHour >= 22) {
					JOptionPane.showMessageDialog(null, "YOU CAN'T ADD MORE TIME", "WARNING" , JOptionPane.WARNING_MESSAGE);
					break;
				}*/
				
				System.out.println(totalHour + ":" + totalMinutes);
				updatedTime = totalHour + ":" + totalMinutes;
				break;
			}
			
			System.out.println(updatedTime);
			Time updatedTimeValue = Time.valueOf(String.valueOf(updatedTime) + ":00");
			pst.setTime(1, updatedTimeValue);
			
			pst.execute();
			pst0.close();
			pst.close();
			rs.close();
			con.close();

			

			
		}catch(Exception e) {
			
		}
	}
	
	public void deletestudentToPc(String id, String profName) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven" , "root" , "");
			PreparedStatement pst0 = con.prepareStatement("SELECT * FROM profs where name = '" + profName + "'");
			ResultSet rs = pst0.executeQuery();
			
			JPasswordField passwordField = new JPasswordField();
	        int option = JOptionPane.showConfirmDialog(null, passwordField, "Enter Password", JOptionPane.OK_CANCEL_OPTION);
	        
	        if (option == JOptionPane.OK_OPTION) {
	            char[] passwordChars = passwordField.getPassword();
	            String password = new String(passwordChars);
	            while(rs.next()) {
	            	if(password.equals(rs.getString("password"))) {
	        			PreparedStatement pst = con.prepareStatement("DELETE FROM `pcrecords` WHERE id = '" + id + "'");
	        			pst.executeUpdate();
	        			pst.close();

	            	}else {
	            		JOptionPane.showMessageDialog(null, "INVALID PASSWORD");
	            	}
	            }
	        }
	        
		}catch(Exception e) {
			System.out.println("CHECK DELETE STUDENT TO PC");
		}

	}
	public void updateInformation(String id, String name, String section, String contact, String status) {
		try {				
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven" , "root" , "");
			PreparedStatement pst = con.prepareStatement("UPDATE studentstable" + " SET studentName = '" + name + "' WHERE student_id = '" + id + "';");
			PreparedStatement pst1 = con.prepareStatement("UPDATE studentstable" + " SET section = '" + section + "' WHERE student_id = '" + id + "';");
			PreparedStatement pst2 = con.prepareStatement("UPDATE studentstable" + " SET studentContact = '" + contact + "' WHERE student_id = '" + id + "';");
			PreparedStatement pst3 = con.prepareStatement("UPDATE studentstable" + " SET studentStatus = '" + status + "' WHERE student_id = '" + id + "';");
		
			pst.execute();
			pst1.execute();	
			pst2.execute();
			pst3.execute();

			pst.close();
			pst1.close();
			pst2.close();
			pst3.close();
			con.close();
			JOptionPane.showMessageDialog(null, "UPDATE SUCESS");
		}catch(Exception e) {
			System.out.println("CHECK UPDATE STUDENT INFORMATION IN DB CLASS");
		}
		
	}
	
	public void deleteEmployee(DefaultTableModel model, JTable table, int i) {

		if(i>-1) {
			try {
				String hrconfirm = JOptionPane.showInputDialog(null,"ARE YOU SURE?");
				String hrconfirm2 = hrconfirm.trim();
				
				if(hrconfirm2.equals("YES")) {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven" , "root" , "");
					PreparedStatement pst = con.prepareStatement("DELETE FROM studentstable WHERE studentName = '" + (String) table.getValueAt(i, 1) + "'");
					pst.executeUpdate();
					pst.close();
					con.close();
			        //warningPc.clear();
			        //pcNumbers.clear();
					
					model.removeRow(i);
					JOptionPane.showMessageDialog(null, "DELETED", "WARNING", JOptionPane.WARNING_MESSAGE);
				}
				
			}catch (Exception e1) {
				System.out.println("CHECK DELETE INFORMATION IN DB CLASS");
			}
			
		}else JOptionPane.showMessageDialog(null, "SELECT A ROW", "WARNING", JOptionPane.WARNING_MESSAGE);
	}
	
	public void savetoPc(String pcnum ,String id, String start, String end, String timelimit) {
		LocalDate currentDate = LocalDate.now();
		System.out.println(id);
		try {
						
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pst = con.prepareStatement("insert into pcrecords (pcNumber,id,StudentName,courseName,status,date,StartTime,EndTime,timeLimit) values (?,?,?,?,?,?,?,?,?)");
			PreparedStatement pst0 = con.prepareStatement("insert into overall_record (PcNumber,date,id,name,course,status,time_enter,time_end) values (?,?,?,?,?,?,?,?)");	
			PreparedStatement pst1 = con.prepareStatement("SELECT * FROM studentstable where student_id = '" + id + "'");	
			ResultSet rs1 = pst1.executeQuery();
			Time timeValue = Time.valueOf(start + ":00");
			Time timeValue2 = Time.valueOf(end + ":00");
			Time timeValue3 = Time.valueOf(timelimit + ":00");
			
			while(rs1.next()) {	
				this.name = rs1.getString("studentName");
				this.course = rs1.getString("section");
				this.status = rs1.getString("studentStatus");
			}
			
			System.out.println(name);
			pst.setInt(1,Integer.parseInt(pcnum));
			pst.setString(2,id);
			pst.setString(3,name);
			pst.setString(4, course);
			pst.setString(5,status);
			pst.setDate(6, Date.valueOf(currentDate));
			pst.setTime(7, timeValue);
			pst.setTime(8, timeValue2);
			pst.setTime(9, timeValue3);

			pst0.setInt(1,Integer.parseInt(pcnum));
			pst0.setDate(2,Date.valueOf(currentDate));
			pst0.setString(3,id);
			pst0.setString(4,name);
			pst0.setString(5, course);
			pst0.setString(6, status);
			pst0.setTime(7, timeValue);
			pst0.setTime(8, timeValue2);
		
			pst.executeUpdate();
			pst0.executeUpdate();
			checkTime();
			System.out.println("ADD SUCCESS");
			pst.close();
			pst1.close();
			rs1.close();
			pst0.close();
		
		}catch(Exception e) {
			JOptionPane.showConfirmDialog(null, "ERROR IDENTIFICATION", "ERROR", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	
	public LinkedList checkTime() {
        LocalTime currentTime;
        
        try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM pcrecords"); 
			ResultSet rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				currentTime = LocalTime.now();
				Time targetTime = rs.getTime("EndTime");
				LocalTime localTime = targetTime.toLocalTime();
				Duration duration = Duration.between(currentTime, localTime);
			    boolean isFiveMinutesBefore = duration.toMinutes() <= 5;
				
			    if(currentTime.isAfter(localTime)) {	
			    	PreparedStatement pstmt1 = Conn.prepareStatement("DELETE FROM `pcrecords` WHERE EndTime = '" + targetTime + "'"); 
					pstmt1.executeUpdate();
					pstmt1.close();
					occupiedPc();
				}else occupiedPc();

						
			}
			
		}catch(Exception e1) {
		}
		return warningPc;	
        
	}
	
	
	
	
	
	
	public void occupiedPc() {
		  
        try { 
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM pcrecords"); 
			ResultSet rs1 = pstmt.executeQuery();
			
		
			warningPc = new LinkedList<>();
			pcNumbers = new LinkedList<>();

			while(rs1.next()) {
				
				LocalTime currentTime = LocalTime.now();
				Time targetTime = rs1.getTime("EndTime");
				LocalTime localTime = targetTime.toLocalTime();
				Duration duration = Duration.between(currentTime, localTime);
			    boolean isFiveMinutesBefore = duration.toMinutes() <= 5;
			    
				if(isFiveMinutesBefore) {
					warningPc.offer(rs1.getInt("pcNumber"));
				}else pcNumbers.offer(rs1.getInt("pcNumber"));
			}
		
			
		
		}catch(Exception e1) {
		}
	
	}
	
	
	public void sendnewMessage(String id, String message) {
		LocalDate currentDate = LocalDate.now();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven" , "root" , "");
			PreparedStatement pst0 = con.prepareStatement("INSERT INTO feedback (id,date,message) value (?,?,?);");
			
			pst0.setString(1,id);
			pst0.setDate(2 ,Date.valueOf(currentDate));
			pst0.setString(3, message);
			
			
			pst0.executeUpdate();
			pst0.close();
			con.close();
		}catch(Exception e) {
			System.out.println("CHECK SEND MESSAGE");
		}
	}
	
	
	public void endClass() {
		try {
			String inputEnd = JOptionPane.showInputDialog("TYPE 'END' TO END THE CLASS").trim();

			if(inputEnd.equals("END")) {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven" , "root" , "");
			PreparedStatement pst = con.prepareStatement("DELETE FROM pcrecords;");

			pst.execute();	
			pst.close();
			con.close();
			endClassSucess = true;

			}else JOptionPane.showMessageDialog(null, "INVALID INPUT");

		}catch (Exception e1) {
				System.out.println("CHECK END CLASS IN DB CLASS");
		}	
	
			
	}
}
