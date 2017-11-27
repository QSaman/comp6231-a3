/**
 * 
 */
package comp6231.a3.users;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import comp6231.a3.common.TimeSlot;
import comp6231.a3.common.web_service.AdminOperations;

/**
 * @author saman
 *
 */

//https://www.youtube.com/watch?v=-3w6LBl8E-8
public class WebServiceClientTest {

	/**
	 * 
	 */
	public WebServiceClientTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException {
		URL url = new URL("http://localhost:8080/DVL/admin?wsdl");
		QName qName = new QName("http://web_service.communication.campus.a3.comp6231/", "AdminServerService");
		Service service = Service.create(url, qName);
		AdminOperations admin = service.getPort(AdminOperations.class);
		ArrayList<String> time_slots = new ArrayList<>();
		time_slots.add("7:00 - 8:00");
		//admin.createRoom("DVLA1111", 7777, "08-11-2017", time_slots);
	}

}
