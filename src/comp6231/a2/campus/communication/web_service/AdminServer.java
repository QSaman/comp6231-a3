/**
 * 
 */
package comp6231.a2.campus.communication.web_service;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import comp6231.a2.campus.Campus;
import comp6231.a2.common.DateReservation;
import comp6231.a2.common.TimeSlot;
import comp6231.a2.common.web_service.AdminOperations;

/**
 * @author saman
 *
 */
@WebService(endpointInterface = "comp6231.a2.campus.communication.web_service.AdminServer")
@SOAPBinding(style=Style.RPC)
public class AdminServer implements AdminOperations {
	Campus campus;

	/**
	 * 
	 */
	public AdminServer(Campus campus) {
		this.campus = campus;
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.web_service.AdminOperations#createRoom(java.lang.String, int, java.lang.String, java.util.ArrayList)
	 */
	@Override
	public boolean createRoom(String user_id, int room_number, String date, ArrayList<String> time_slots) {
		ArrayList<TimeSlot> alts = new ArrayList<>();
		for (String str : time_slots)
			alts.add(new TimeSlot(str));
		
		return campus.createRoom(user_id, room_number, new DateReservation(date), alts);
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.web_service.AdminOperations#deleteRoom(java.lang.String, int, java.lang.String, java.util.ArrayList)
	 */
	@Override
	public boolean deleteRoom(String user_id, int room_number, String date, ArrayList<String> time_slots) {
		ArrayList<TimeSlot> alts = new ArrayList<>();
		for (String str : time_slots)
			alts.add(new TimeSlot(str));
		return campus.deleteRoom(user_id, room_number, new DateReservation(date), alts);
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.web_service.AdminOperations#startWeek(java.lang.String)
	 */
	@Override
	public boolean startWeek(String user_id) throws NotBoundException, IOException, InterruptedException {
		return campus.startWeek(user_id);
	}

}
