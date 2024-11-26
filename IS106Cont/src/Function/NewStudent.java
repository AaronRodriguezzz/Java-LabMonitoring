package Function;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class NewStudent {
	public boolean existed = false;

	public NewStudent(){}
	
	public ImageIcon qrcodeFunction(String empID) {
		ImageIcon qrImg = null;
		try {
			String str = empID.trim();
			String path = System.getProperty("user.dir") + "/";
			String charSet = "UTF-8";
			Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
			hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			generateQRcode(str,path+str+".png",charSet,hashMap,300,350);
			qrImg = new ImageIcon(str+".png");
		}catch(Exception e1) {
			JOptionPane.showMessageDialog(null, "QR CODE ERROR");
		}
		return qrImg; 
	}
	
	public void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
	    MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
	}
	
	public void addingnewStudent(String studentId,String studentName, String section, String studentContact, String studentStatus, Icon qr, BufferedImage studentPhoto, String profName) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			ByteArrayInputStream byteArrayInputStream1 = new ByteArrayInputStream(imagetoBytes(qr));
			ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(cameraImgBytes(studentPhoto));
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO studentstable(student_id,studentName,section,studentContact,studentStatus,profName,studentQr,studentPhoto)"
														+  " values (?,?,?,?,?,?,?,?); ");				
			

			pstmt.setString(1,studentId);
			pstmt.setString(2, studentName);
			pstmt.setString(3, section);
			pstmt.setString(4, studentContact);
			pstmt.setString(5, studentStatus);
			pstmt.setString(6, profName);
			pstmt.setBlob(7, byteArrayInputStream1);
			pstmt.setBlob(8, byteArrayInputStream2);	

		    Pattern pattern = Pattern.compile(".*[a-zA-Z].*");
		    Matcher matcher = pattern.matcher(studentId);
		    Pattern pattern1 = Pattern.compile(".*[!@#$%^&*()+=_}{|\"':;?/>.<,\\\\[\\\\]].*");
			Matcher matcher1 = pattern1.matcher(studentId);
			Pattern pattern3 = Pattern.compile(".*[-!@#$%^&*()+=_}{|\"':;?/>.<,\\\\[\\\\]].*");
			Matcher matcher3 = pattern3.matcher(studentName);
			Pattern pattern5 = Pattern.compile(".*[0-9].*");
			Matcher matcher5 = pattern5.matcher(studentName);
			Pattern pattern7 = Pattern.compile(".*[-!@#$%^&*()+=_}{|\"':;?/>.<,\\\\[\\\\]].*");
			Matcher matcher7 = pattern7.matcher(studentContact);
			
			if(!matcher.matches() && !matcher1.matches()) {
				if(!matcher3.matches()) {
					if(!matcher5.matches()) {
						if(!matcher7.matches()) {
							pstmt.executeUpdate();
							pstmt.close();
							con.close();
						}else JOptionPane.showMessageDialog(null, "Student Name must not contain special characters", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
					}else JOptionPane.showMessageDialog(null, "Student must not contain numbers", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				}else  JOptionPane.showMessageDialog(null, "Use numbers only", "Confirmation", JOptionPane.INFORMATION_MESSAGE);	
			}else JOptionPane.showMessageDialog(null, "You can only use number(s) and dash for Student ID", "Confirmation", JOptionPane.INFORMATION_MESSAGE);	
		

			
			
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "PLEASE CHECK YOUR INPUT");
		}
	}
	
	public boolean checkstudentExistence(String id, String name) {
		String identification = id.trim();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/groupseven","root","");
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM studentstable"); 
			ResultSet rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				System.out.println(identification + "   " + rs.getString("student_id"));
				if(identification.equals(rs.getString("student_id"))) {
					existed = true;
					System.out.println("EXISTED");
					break;
				}else {
					existed = false;
					System.out.println("NOT EXISTED");
				}
			}
			
			
			if(!rs.next()) {
				existed = false;
			}
			
			rs.close();
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "PLEASE CHECK YOUR INPUT");
		}
		return existed;
	}
	
	public byte[] imagetoBytes(Icon icon) {

		 byte[] bytes = null;
	     BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
	     icon.paintIcon(null, image.getGraphics(), 0, 0);

	      // Convert the BufferedImage to a byte array
	      ByteArrayOutputStream baos = new ByteArrayOutputStream();
	      try {
	    	  
	    	  ImageIO.write(image, "PNG", baos);
	    	  bytes = baos.toByteArray();     
	          
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
		return bytes;
		
	}
	
	  public static byte[] cameraImgBytes(BufferedImage image) {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        try {
	            ImageIO.write(image, "PNG", baos);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return baos.toByteArray();
	    } 
	
	
}
