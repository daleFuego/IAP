package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import dao.DataLoader;
import dao.SOAPClientSAAJ;

import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;

@SuppressWarnings("unused")
public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2292455670620751734L;
	private JPanel contentPane;
	private SOAPClientSAAJ clientSAAJ;
	private ProductsTable productsTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 531, 497);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelBottom = new JPanel();
		contentPane.add(panelBottom, BorderLayout.SOUTH);
		panelBottom.setLayout(new BorderLayout(0, 0));
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		panelBottom.add(btnExit, BorderLayout.CENTER);
		
		JPanel panelMain = new JPanel();
		contentPane.add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCtrls = new JPanel();
		panelMain.add(panelCtrls, BorderLayout.EAST);
		panelCtrls.setBorder(new TitledBorder(null, "Actions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCtrls.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnAddNewProduct = new JButton("Add new product");
		btnAddNewProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataLoader.getInstance().addProduct();
			}
		});
		panelCtrls.add(btnAddNewProduct);
		
		JButton btnEditProduct = new JButton("Edit product");
		btnEditProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DataLoader.getInstance().editProduct(productsTable.getModel(), productsTable.getSelectedRow());
			}
		});
		panelCtrls.add(btnEditProduct);
		
		JButton btnDeleteProduct = new JButton("Delete product");
		btnDeleteProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataLoader.getInstance().deleteProduct(productsTable.getModel(), productsTable.getSelectedRow());
			}
		});
		panelCtrls.add(btnDeleteProduct);
		
		JButton btnUpdateTable = new JButton("Update table");
		btnUpdateTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		});
		panelCtrls.add(btnUpdateTable);
		
		JPanel panelTable = new JPanel();
		panelMain.add(panelTable, BorderLayout.CENTER);
		panelTable.setBorder(new TitledBorder(null, "Products", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTable.setLayout(new CardLayout(0, 0));
		
		productsTable = new ProductsTable();
		
		JScrollPane scrollPaneTable = new JScrollPane(productsTable);
		panelTable.add(scrollPaneTable);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e1) {
		}
		
		updateTable();
	}

	private void updateTable() {
		productsTable.cleanTable();
		productsTable.fillTable(DataLoader.getInstance().getAllItems());
	}

}
