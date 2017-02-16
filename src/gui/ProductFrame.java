package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import dao.SOAPClientSAAJ;
import utils.IntegerFilter;

public class ProductFrame extends JFrame {

	private static final long serialVersionUID = 3857835830559587977L;
	private JTextField textFieldID;
	private JTextField textFieldName;
	private JTextField textFieldCount;
	private JTextField textFieldPrice;
	private JButton btnSaveChanges;

	public ProductFrame(boolean enableIDField) {
		setResizable(false);
		setTitle("Product details");
		setVisible(true);
		setSize(262, 228);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(17, 19, 46, 14);
		getContentPane().add(lblId);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(17, 52, 46, 14);
		getContentPane().add(lblName);

		JLabel lblCount = new JLabel("Count:");
		lblCount.setBounds(17, 85, 46, 14);
		getContentPane().add(lblCount);

		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setBounds(17, 118, 46, 14);
		getContentPane().add(lblPrice);

		textFieldID = new JTextField();
		textFieldID.setEditable(enableIDField);
		textFieldID.setBounds(80, 16, 152, 20);
		getContentPane().add(textFieldID);
		textFieldID.setColumns(10);

		textFieldName = new JTextField();
		textFieldName.setBounds(80, 49, 152, 20);
		getContentPane().add(textFieldName);
		textFieldName.setColumns(10);

		textFieldCount = new JTextField();
		textFieldCount.setBounds(80, 82, 152, 20);
		getContentPane().add(textFieldCount);
		textFieldCount.setColumns(10);

		textFieldPrice = new JTextField();
		textFieldPrice.setBounds(80, 115, 152, 20);
		getContentPane().add(textFieldPrice);
		textFieldPrice.setColumns(10);

		btnSaveChanges = new JButton();

		btnSaveChanges.setBounds(6, 151, 115, 23);
		getContentPane().add(btnSaveChanges);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(127, 151, 115, 23);
		getContentPane().add(btnCancel);
		
		PlainDocument doc1 = (PlainDocument) textFieldID.getDocument();
		doc1.setDocumentFilter(new IntegerFilter());
		
		PlainDocument doc2 = (PlainDocument) textFieldCount.getDocument();
		doc2.setDocumentFilter(new IntegerFilter());
		
		PlainDocument doc3 = (PlainDocument) textFieldPrice.getDocument();
		doc3.setDocumentFilter(new IntegerFilter());
	}

	public void fill(String[] rowContent) {
		textFieldID.setText(rowContent[0]);
		textFieldName.setText(rowContent[1]);
		textFieldCount.setText(rowContent[2]);
		textFieldPrice.setText(rowContent[3]);
	}

	public void setButtonOkBehaviour(String option) {
		switch (option) {
		case "UPDATE":
			btnSaveChanges.setText("Save changes");
			btnSaveChanges.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SOAPClientSAAJ clientSAAJ = new SOAPClientSAAJ();
					String query = "UPDATE STORE SET item='" + textFieldName.getText() + "', count="
							+ Integer.parseInt(textFieldCount.getText()) + ", price="
							+ Integer.parseInt(textFieldPrice.getText()) + " where id="
							+ Integer.parseInt(textFieldID.getText());

					System.out.println(query);
					clientSAAJ.updateDB2(query);
					dispose();
				}
			});
			break;
		case "INSERT":
			btnSaveChanges.setText("Create");
			btnSaveChanges.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SOAPClientSAAJ clientSAAJ = new SOAPClientSAAJ();
					String query = "INSERT INTO STORE VALUES (" + Integer.parseInt(textFieldID.getText()) + ", '"
							+ textFieldName.getText() + "', " + Integer.parseInt(textFieldCount.getText()) + ", "
							+ Integer.parseInt(textFieldPrice.getText()) + ")";

					System.out.println(query);
					clientSAAJ.insertIntoDB2(query);
					dispose();
				}
			});
			break;
		default:
			btnSaveChanges.setText("No action");
			break;
		}
	}
}
