/**
 * 
 */
package comp6231.a3.campus.communication.web_service;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import comp6231.a3.campus.Campus;
import comp6231.a3.common.DateReservation;
import comp6231.a3.common.TimeSlot;
import comp6231.a3.common.TimeSlotResult;
import comp6231.a3.common.web_service.StudentOperations;

/**
 * @author saman
 *
 */
@WebService(endpointInterface = "comp6231.a3.common.web_service.StudentOperations")
@SOAPBinding(style=Style.RPC)
public class StudentServer implements StudentOperations {
	Campus campus;

	/**
	 * 
	 */
	public StudentServer(Campus campus) {
		this.campus = campus;
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.web_service.StudentOperations#bookRoom(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String)
	 */
	@Override
	public String bookRoom(String user_id, String campus_name, int room_number, String date, String time_slot)
			throws NotBoundException, IOException, InterruptedException {
		String ret = campus.bookRoom(user_id, campus_name, room_number, new DateReservation(date), new TimeSlot(time_slot));
		if (ret == null)
			ret = "";
		return ret;
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.web_service.StudentOperations#getAvailableTimeSlot(java.lang.String)
	 */
	@Override
	public String getAvailableTimeSlot(String date) throws NotBoundException, IOException, InterruptedException {
		ArrayList<TimeSlotResult> res = campus.getAvailableTimeSlot(new DateReservation(date));
		String ret = new String();
		for (TimeSlotResult tsr : res)
			ret += tsr.toString() + "\n";
		return ret;
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.web_service.StudentOperations#cancelBooking(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean cancelBooking(String user_id, String bookingID)
			throws NotBoundException, IOException, InterruptedException {
		return campus.cancelBooking(user_id, bookingID);
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.web_service.StudentOperations#changeReservation(java.lang.String, java.lang.String, java.lang.String, int, comp6231.a2.common.DateReservation, comp6231.a2.common.TimeSlot)
	 */
	@Override
	public String changeReservation(String user_id, String booking_id, String new_campus_name, int new_room_number,
			String new_date, String new_time_slot)
			throws NotBoundException, IOException, InterruptedException {
		return campus.changeReservation(user_id, booking_id, new_campus_name, new_room_number, new DateReservation(new_date), new TimeSlot(new_time_slot));
	}

}
