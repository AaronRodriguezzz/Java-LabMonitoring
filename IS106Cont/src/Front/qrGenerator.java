package Front;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class qrGenerator extends JFrame{
	
	public qrGenerator(String id){

		JLabel qrLbl = new JLabel();
		qrLbl.setBounds(25,10,350,350);
		qrLbl.setBackground(Color.black);
		qrLbl.setHorizontalAlignment(JTextField.CENTER);
		qrLbl.setIcon(qrcodeFunction(id));

		JLabel qrId = new JLabel(id);
		qrId.setBounds(0,360,400,40);
		qrId.setBackground(Color.black);
		qrId.setHorizontalAlignment(JTextField.CENTER);
		qrId.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD,  50));
		
		add(qrId);
		add(qrLbl);
		this.getContentPane().setBackground(Color.white);
		this.setLayout(null);
		this.setSize(400,430);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setVisible(true);
	}
	
	public ImageIcon qrcodeFunction(String empID) {
		ImageIcon qrImg = null;
		try {
			String str = empID.trim();
			String path = System.getProperty("user.dir") + "/";
			String charSet = "UTF-8";
			Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
			hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			generateQRcode(str,path+str+".png",charSet,hashMap,430,430);
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
}
