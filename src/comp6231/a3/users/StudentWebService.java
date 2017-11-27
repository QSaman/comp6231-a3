/**
 * 
 */
package comp6231.a3.users;

import java.util.ArrayList;

import comp6231.a3.common.DateReservation;
import comp6231.a3.common.TimeSlot;
import comp6231.a3.common.TimeSlotResult;

import comp6231.a3.common.web_service.StudentOperations;

/**
 * @author saman
 *
 */
public class StudentWebService implements StudentInterface {
	
	StudentOperations remote_stub;

	/**
	 * 
	 */
	public StudentWebService(StudentOperations remote_stub) {
		this.remote_stub = remote_stub;
	}

	/* (non-Javadoc)
	 * @see comp6231.a3.users.StudentInterface#bookRoom(java.lang.String, java.lang.String, int, comp6231.a3.common.DateReservation, comp6231.a3.common.TimeSlot)
	 */
	@Override
	public String bookRoom(String user_id, String campus_name, int room_number, DateReservation date,
			TimeSlot time_slot) {
		String res = remote_stub.bookRoom(user_id, campus_name, room_number, date.toString(), time_slot.toString());
		if (res == "")
			res = null;
		return res;
	}

	/* (non-Javadoc)
	 * @see comp6231.a3.users.StudentInterface#getAvailableTimeSlot(comp6231.a3.common.DateReservation)
	 */
	@Override
	public ArrayList<TimeSlotResult> getAvailableTimeSlot(DateReservation date) {
		String res = remote_stub.getAvailableTimeSlot(date.toString());
		return TimeSlotResult.toList(res);
	}

	/* (non-Javadoc)
	 * @see comp6231.a3.users.StudentInterface#cancelBooking(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean cancelBooking(String user_id, String bookingID) {
		return remote_stub.cancelBooking(user_id, bookingID);
	}

	/* (non-Javadoc)
	 * @see comp6231.a3.users.StudentInterface#changeReservation(java.lang.String, java.lang.String, java.lang.String, int, comp6231.a3.common.DateReservation, comp6231.a3.common.TimeSlot)
	 */
	@Override
	public String changeReservation(String user_id, String booking_id, String new_campus_name, int new_room_number,
			DateReservation new_date, TimeSlot new_time_slot) {
		return remote_stub.changeReservation(user_id, booking_id, new_campus_name, new_room_number, new_date.toString(), new_time_slot.toString());
	}

}
