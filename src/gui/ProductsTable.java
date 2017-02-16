package gui;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class ProductsTable extends JTable {

	private static final long serialVersionUID = 5675502962174242437L;
	private DefaultTableModel model;
	private String selectedItemID;

	public ProductsTable() {
		model = new DefaultTableModel();

		String[] columnNames = new String[] { "ID", "Item", "Count", "Price" };
		model.setColumnIdentifiers(columnNames);

		setModel(model);
		setBorder(new LineBorder(new Color(0, 0, 0)));

		ListSelectionModel cellSelectionModel = getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try {
					selectedItemID = (String) getValueAt(getSelectedRow(), 0);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	public void fillTable(ArrayList<String[]> rows) {
		try {
			for (int i = 0; i < rows.size(); i++) {
				model.addRow(rows.get(i));
				System.out.println(">>> filling with row " + i + " : " + rows.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getSelectedItemID() {
		return selectedItemID;
	}

	public void cleanTable() {
		model.getDataVector().removeAllElements();
		repaint();
	}

}
