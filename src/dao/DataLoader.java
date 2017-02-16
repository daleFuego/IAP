package dao;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.NodeList;

import gui.ProductFrame;

public class DataLoader {

	private static DataLoader dataLoader;
	private SOAPClientSAAJ clientSAAJ;

	private DataLoader() {
		clientSAAJ = new SOAPClientSAAJ();
	}

	public static DataLoader getInstance() {
		if (dataLoader == null) {
			dataLoader = new DataLoader();
		}

		return dataLoader;
	}

	public ArrayList<String[]> getAllItems() {
		ArrayList<String[]> rows = new ArrayList<>();

		try {
			SOAPMessage response = clientSAAJ.selectFromDB2_Xml("SELECT * FROM STORE");
			SOAPEnvelope env = response.getSOAPPart().getEnvelope();
			SOAPBody body = env.getBody();
			NodeList items = body.getFirstChild().getFirstChild().getChildNodes();

			for (int i = 0; i < items.getLength(); i++) {
				NodeList item = items.item(i).getChildNodes();
				String[] rowContent = new String[4];

				try {
					for (int j = 0; i < item.getLength(); j++) {
						if (item.item(j) != null)
							rowContent[j] = item.item(j).getTextContent();
						else
							break;
					}

					rows.add(rowContent);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			response.removeAllAttachments();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rows;
	}

	public void editProduct(TableModel model, int selectedRow) {
		ProductFrame frame = new ProductFrame(false);
		frame.setButtonOkBehaviour("UPDATE");

		String[] rowContent = new String[4];

		if (selectedRow < 0) {
			selectedRow = 0;
		}

		for (int i = 0; i < 4; i++) {
			rowContent[i] = (String) model.getValueAt(selectedRow, i);
		}

		frame.fill(rowContent);
	}

	public void addProduct() {
		ProductFrame frame = new ProductFrame(true);
		frame.setButtonOkBehaviour("INSERT");
	}

	public void deleteProduct(TableModel model, int selectedRow) {
		int productID = Integer.parseInt((String) model.getValueAt(selectedRow, 0));

		if (JOptionPane.showConfirmDialog(null,
				"You are about to delete product with ID " + productID + ".\nDo you want to continue?", "WARNING",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			SOAPClientSAAJ clientSAAJ = new SOAPClientSAAJ();
			String query = "DELETE FROM STORE WHERE id=" + productID;

			System.out.println(query);
			clientSAAJ.deleteFromDB2(query);
		}
	}
}