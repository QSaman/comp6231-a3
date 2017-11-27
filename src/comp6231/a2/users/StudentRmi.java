/**
 * 
 */
package comp6231.a2.users;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import comp6231.a2.common.DateReservation;
import comp6231.a2.common.TimeSlot;
import comp6231.a2.common.TimeSlotResult;
import comp6231.a2.common.users.StudentOperations;

/**
 * @author saman
 *
 */
public class StudentRmi implements StudentInterface {
	
	private StudentOperations remote_stub;

	/**
	 * 
	 */
	public StudentRmi(StudentOperations remote_stub) {
		this.remote_stub = remote_stub;
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.users.StudentInterface#bookRoom(java.lang.String, int, comp6231.a2.common.DateReservation, comp6231.a2.common.TimeSlot)
	 */
	@Override
	public String bookRoom(String user_id, String campus_name, int room_number, DateReservation date, TimeSlot time_slot) {
		try {
			return remote_stub.bookRoom(user_id, campus_name, room_number, date, time_slot);
		} catch (NotBoundException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.users.StudentInterface#getAvailableTimeSlot(comp6231.a2.common.DateReservation)
	 */
	@Override
	public ArrayList<TimeSlotResult> getAvailableTimeSlot(DateReservation date) {
		try {
			return remote_stub.getAvailableTimeSlot(date);
		} catch (NotBoundException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.users.StudentInterface#cancelBooking(java.lang.String)
	 */
	@Override
	public boolean cancelBooking(String user_id, String bookingID) {
		try {
			return remote_stub.cancelBooking(user_id, bookingID);
		} catch (NotBoundException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String changeReservation(String user_id, String booking_id, String new_campus_name, int new_room_number,
			DateReservation new_date, TimeSlot new_time_slot) {
		try {
			return remote_stub.changeReservation(user_id, booking_id, new_campus_name, new_room_number, new_date, new_time_slot);
		} catch (NotBoundException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
