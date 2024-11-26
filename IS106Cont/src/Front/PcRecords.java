package Front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import Function.TableFunc;

public class PcRecords {
	JPanel pcrecPanel;
	DefaultTableModel model;
	JScrollPane scrollPane;
	JTable table;
	JButton manualRemove;
	JButton addTime;
	String[] columns = {"Pc Number","Student ID","Student Name", "Student Section", "Status" , "End Time"};
	TableFunc tf = new TableFunc();
	StudentToPc stp;
	
	Thread scanningThread;
	boolean scan = true;
	PcRecords(String profName){
		
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
		table.getTableHeader().setPreferredSize(new Dimension(0,20));
		table.setRowHeight(30);
		
		scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.white);
		scrollPane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		scrollPane.setBounds(10,10,620,430);
		scrollPane.setVisible(true);
		
		manualRemove = new JButton("Manual Remove");
		manualRemove.setBackground(Color.decode("#27AAE1"));
		manualRemove.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  17));
		manualRemove.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		manualRemove.setBounds(460,445,170,25);
		manualRemove.setFocusable(false);
		manualRemove.setForeground(Color.white);
		manualRemove.addActionListener(e -> { 
			if(table.getSelectedRow() != -1) {
				tf.deletestudentToPc((String) table.getValueAt(table.getSelectedRow(), 1),profName);
			}else 	JOptionPane.showMessageDialog(null, "SELECT ROW FIRST");
		});
		
		
		addTime = new JButton("Manual Add Time");
		addTime.setBackground(Color.decode("#27AAE1"));
		addTime.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,  17));
		addTime.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
		addTime.setBounds(270,445,180,25);
		addTime.setFocusable(false);
		addTime.setForeground(Color.white);
		addTime.addActionListener(e -> { 
			if(table.getSelectedRow() != -1) {
				System.out.println(table.getValueAt(table.getSelectedRow(), 1));
				stp = new StudentToPc((String) table.getValueAt(table.getSelectedRow(), 1));
				stp.setVisible(true);
				stp.add.setVisible(true);
				stp.pcNum.setEnabled(false);
				stp.pc.setVisible(false);
				stp.pcNum.setVisible(false);
				stp.save.setVisible(false);
			}else 	JOptionPane.showMessageDialog(null, "SELECT ROW FIRST");
		});
		
		pcrecPanel = new JPanel();
		pcrecPanel.setBounds(295,15,640,480);
		pcrecPanel.setBackground(Color.black);
		pcrecPanel.setVisible(false);
		pcrecPanel.setLayout(null);
		pcrecPanel.add(scrollPane);
		pcrecPanel.add(manualRemove);
		pcrecPanel.add(addTime);
		model.setRowCount(0);
		tf.pcRecord(model);
	}
	
	public void pageCustomization(boolean themeChoice) {
		if(!themeChoice) {
			manualRemove.setBackground(Color.gray);
			manualRemove.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
			addTime.setBackground(Color.gray);
			addTime.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
			scrollPane.getViewport().setBackground(Color.gray);
			table.getTableHeader().setBackground(Color.gray);
			table.getTableHeader().setForeground(Color.white);
			table.setSelectionBackground(Color.gray);
			table.setSelectionForeground(Color.white);
			pcrecPanel.setBackground(Color.black);
		}else {
			manualRemove.setBackground(Color.decode("#27AAE1"));
			manualRemove.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.white));
			addTime.setBackground(Color.decode("#27AAE1"));
			addTime.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.white));
			scrollPane.getViewport().setBackground(Color.white);
			table.getTableHeader().setBackground(Color.white);
			table.getTableHeader().setForeground(Color.black);
			table.setSelectionBackground(Color.black);
			table.setSelectionForeground(Color.white);
			pcrecPanel.setBackground(Color.white);

		}
		
	}
	
	public void scanMethod() {		
		scanningThread = new Thread(() -> {
	
		    while (!scan) {
		    	try{
		    		if (table.getRowCount() != 0) {
			            tf.checkTime();
			            model.setRowCount(0);
			            tf.pcRecord(model);    
			        }
		    		
			   	 	System.out.println("time scanning");
					Thread.sleep(1500);
		    	}catch(Exception e) {
		    		
		    	}
		    }
		   
		});
		scanningThread.start();
		
	}
	
	public void stopScanningThread() {
	    scan = true; // Set the flag to true to stop the scanning thread
	}
}
