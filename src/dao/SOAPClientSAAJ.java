package dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class SOAPClientSAAJ {

	private String url = "http://localhost:49759/Main.asmx";

	public SOAPMessage selectFromDB2_Xml(String query) {
		SOAPMessage result = null;
		
		try {
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			SOAPMessage soapResponse = soapConnection.call(createSelectRequest("SelecFromDB_Xml", query), url);
			
			soapConnection.close();
			
			result = soapResponse;
		} catch (UnsupportedOperationException | SOAPException | IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String selectFromDB2_Json(String query) {
		String result = "";
		
		try {
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			SOAPMessage soapResponse = soapConnection.call(createSelectRequest("SelectFromDB_Json", query), url);

			soapResponse.writeTo(System.out);
			soapConnection.close();
		} catch (UnsupportedOperationException | SOAPException | IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void insertIntoDB2(String query) {
		try {
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			SOAPMessage soapResponse = soapConnection.call(createRequest("InsertIntoStore", query), url);

			soapResponse.writeTo(System.out);
			soapConnection.close();
		} catch (UnsupportedOperationException | SOAPException | IOException e) {
			e.printStackTrace();
		}
	}

	public void updateDB2(String query) {
		try {
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			SOAPMessage soapResponse = soapConnection.call(createRequest("UpdateStore", query), url);

			soapResponse.writeTo(System.out);
			soapConnection.close();
		} catch (UnsupportedOperationException | SOAPException | IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteFromDB2(String query) {
		try {
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			SOAPMessage soapResponse = soapConnection.call(createRequest("DeleteFromStore", query), url);

			soapResponse.writeTo(System.out);
			soapConnection.close();
		} catch (UnsupportedOperationException | SOAPException | IOException e) {
			e.printStackTrace();
		}
	}

	private SOAPMessage createSelectRequest(String functionCall, String query) throws IOException, SOAPException {

		String send = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<soap:Body><" + functionCall
				+ " xmlns=\"http://militaryshop.org/webservices\"><query>select * from store</query></" + functionCall
				+ ">" + "</soap:Body></soap:Envelope>";

		InputStream is = new ByteArrayInputStream(send.getBytes());
		SOAPMessage request = MessageFactory.newInstance().createMessage(null, is);

		return request;
	}
	
	private SOAPMessage createRequest(String functionCall, String query) throws IOException, SOAPException {

		String send = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				 + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
				 + "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <UpdateStore xmlns=\"http://militaryshop.org/webservices\"> "
				 + "<query>" + query + "</query>    </UpdateStore>  </soap:Body></soap:Envelope>";

		InputStream is = new ByteArrayInputStream(send.getBytes());
		SOAPMessage request = MessageFactory.newInstance().createMessage(null, is);

		return request;
	}
}
