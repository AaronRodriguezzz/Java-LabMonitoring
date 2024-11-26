package Front;

import java.awt.Color;
import java.awt.Font;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Function.TableFunc;

public class NewMessage {
	JTextField messageTitle;
	JTextArea textArea;
	JButton send, cancel;
	JFrame messageFrame;
	JLabel titleLbl;
	JLabel messageLbl;
	JLabel messageLengthLbl;
	int messagecharCount = 0;
	
	TableFunc tf = new TableFunc();
	NewMessage(){
		titleLbl = new JLabel("Identification");
		titleLbl.setBounds(10, 10, 130, 20);
		titleLbl.setFont(new Font("ARIAL", Font.BOLD, 13));
		
		messageTitle = new JTextField(30);
		messageTitle.setBounds(10,30,460,30);
		messageTitle.setBackground(Color.white);
		messageTitle.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray));
		messageTitle.setFont(new Font("ARIAL", Font.PLAIN, 15));

		
		messageLbl = new JLabel("Message");
		messageLbl.setBounds(10, 70, 130, 20);
		messageLbl.setFont(new Font("ARIAL", Font.BOLD, 13));
		
		messageLengthLbl = new JLabel(messagecharCount + "/ 250" );
		messageLengthLbl.setBounds(10, 330, 130, 20);
		messageLengthLbl.setFont(new Font("ARIAL", Font.BOLD, 13));
		
		textArea = new JTextArea();
        textArea.setBounds(10, 90, 460, 230);  
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.white);
        textArea.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray));
        textArea.setFont(new Font("ARIAL", Font.PLAIN, 15));
        textArea.getDocument().addDocumentListener(new DocumentListener() {
        	void getLength () {
        		messagecharCount = textArea.getText().length();
        		messageLengthLbl.setText(messagecharCount + "/ 250" );
        	}
        	
			@Override
			public void insertUpdate(DocumentEvent e) {
				 getLength();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				 getLength();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				 getLength();
			}
			
		});


		send = new JButton("Send");
		send.setBounds(390, 330, 80, 25);
		send.setFocusable(false);
		send.setBackground(Color.decode("#27AAE1"));
		send.setForeground(Color.white);
		send.setFont(new Font("Arial",Font.PLAIN,13));
		send.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		send.addActionListener( e -> {
			
			if(!messageTitle.getText().isEmpty() && !textArea.getText().isEmpty()) {
					if(textArea.getText().length() <= 250) {
						tf.sendnewMessage(messageTitle.getText(), textArea.getText());
						messageFrame.setVisible(false);
						JOptionPane.showMessageDialog(null, "MESSAGE SENT");
					}else JOptionPane.showMessageDialog(null, "250 CHARACTERS MAXIMUM");
			}else JOptionPane.showMessageDialog(null, "FILL ALL THE BLANKS");
		});
		
		cancel = new JButton("Cancel");
		cancel.setBounds(300, 330, 80, 25);
		cancel.setFocusable(false);
		cancel.setBackground(Color.white);
		cancel.setForeground(Color.red);
		cancel.setFont(new Font("Arial",Font.PLAIN,13));
		cancel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red));
		cancel.addActionListener(e -> {
			messageFrame.setVisible(false);
			send.setVisible(true);
			cancel.setBounds(300, 330, 80, 25);
			cancel.setText("Cancel");
			messageTitle.setText("");
			textArea.setText("");
		});

		messageFrame = new JFrame();
		messageFrame.setSize(500,400);
		messageFrame.getContentPane().setBackground(Color.white);
		messageFrame.setLayout(null);
		messageFrame.setUndecorated(true);
		messageFrame.setResizable(false);
		messageFrame.setLocationRelativeTo(null);
		messageFrame.setVisible(false);
		messageFrame.add(textArea);
		messageFrame.add(messageTitle);
		messageFrame.add(messageLbl);
		messageFrame.add(messageLengthLbl);
		messageFrame.add(titleLbl);
		messageFrame.add(send);
		messageFrame.add(cancel);
	}
	
	public void pageCustomization(boolean themeChoice) {
		if(!themeChoice) {
			messageFrame.getContentPane().setBackground(Color.black);
			titleLbl.setForeground(Color.gray);
			messageTitle.setBackground(Color.gray);
			messageTitle.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
			messageLengthLbl.setForeground(Color.gray);
			messageLbl.setForeground(Color.gray);
			textArea.setBackground(Color.gray);
			textArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
			send.setBackground(Color.black);
			send.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#27AAE1")));
			cancel.setBackground(Color.black);
			cancel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red));
		}else {
			messageFrame.getContentPane().setBackground(Color.white);
			titleLbl.setForeground(Color.black);
			messageTitle.setBackground(Color.white);
			messageTitle.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
			messageLengthLbl.setForeground(Color.black);
			messageLbl.setForeground(Color.black);
			textArea.setBackground(Color.white);
			textArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
			send.setBackground(Color.decode("#27AAE1"));
			send.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#27AAE1")));
			cancel.setBackground(Color.white);
			cancel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red));
		}
	}
}
