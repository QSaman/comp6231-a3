/**
 * 
 */
package comp6231.a3.campus.communication.web_service;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import comp6231.a3.campus.Campus;
import comp6231.a3.common.DateReservation;
import comp6231.a3.common.TimeSlot;
import comp6231.a3.common.web_service.AdminOperations;

/**
 * @author saman
 *
 */
@WebService(endpointInterface = "comp6231.a3.common.web_service.AdminOperations")
@SOAPBinding(style=Style.RPC)
public class AdminServer implements AdminOperations {
	Campus campus;

	/**
	 * 
	 */
	public AdminServer() {
	}
	
	public void setCampus(Campus campus)
	{
		this.campus = campus;
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.web_service.AdminOperations#createRoom(java.lang.String, int, java.lang.String, java.util.ArrayList)
	 */
	@WebMethod
	@Override
	public boolean createRoom(String user_id, int room_number, String date, ArrayList<String> time_slots) {
		
		return campus.createRoom(user_id, room_number, new DateReservation(date), TimeSlot.toTimeSlot(time_slots));
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.web_service.AdminOperations#deleteRoom(java.lang.String, int, java.lang.String, java.util.ArrayList)
	 */
	@WebMethod
	@Override
	public boolean deleteRoom(String user_id, int room_number, String date, ArrayList<String> time_slots) {
		return campus.deleteRoom(user_id, room_number, new DateReservation(date), TimeSlot.toTimeSlot(time_slots));
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.web_service.AdminOperations#startWeek(java.lang.String)
	 */
	@WebMethod
	@Override
	public boolean startWeek(String user_id) {
		try {
			return campus.startWeek(user_id);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
