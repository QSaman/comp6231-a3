/**
 * 
 */
package comp6231.a3.campus.communication.corba;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import comp6231.a3.campus.Campus;
import comp6231.a3.common.DateReservation;
import comp6231.a3.common.TimeSlot;
import comp6231.a3.common.TimeSlotResult;
import comp6231.a3.common.corba.data_structure.CorbaDateReservation;
import comp6231.a3.common.corba.data_structure.CorbaTimeSlot;
import comp6231.a3.common.corba.data_structure.CorbaTimeSlotResult;
import comp6231.a3.common.corba.users.StudentOperationsPOA;

/**
 * @author saman
 *
 */
public class StudentCampusServant extends StudentOperationsPOA {
	
	Campus campus;

	/**
	 * 
	 */
	public StudentCampusServant(Campus campus) {
		this.campus = campus;
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.corba.users.StudentOperationsOperations#bookRoom(java.lang.String, java.lang.String, int, comp6231.a2.common.corba.data_structure.CorbaDateReservation, comp6231.a2.common.corba.data_structure.CorbaTimeSlot)
	 */
	@Override
	public String bookRoom(String user_id, String campus_name, int room_number, CorbaDateReservation date,
			CorbaTimeSlot time_slot) {
		try {
			return campus.bookRoom(user_id, campus_name, room_number, new DateReservation(date), new TimeSlot(time_slot));
		} catch (NotBoundException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.corba.users.StudentOperationsOperations#getAvailableTimeSlot(comp6231.a2.common.corba.data_structure.CorbaDateReservation)
	 */
	@Override
	public CorbaTimeSlotResult[] getAvailableTimeSlot(CorbaDateReservation date) {
		try {
			ArrayList<TimeSlotResult> res = campus.getAvailableTimeSlot(new DateReservation(date));
			CorbaTimeSlotResult[] ret = new CorbaTimeSlotResult[res.size()];
			for (int i = 0; i < res.size(); ++i)
				ret[i] = res.get(i).toCorba();
			return ret;
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.corba.users.StudentOperationsOperations#cancelBooking(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean cancelBooking(String user_id, String bookingID) {
		try {
			return campus.cancelBooking(user_id, bookingID);
		} catch (NotBoundException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String changeReservation(String user_id, String booking_id, String new_campus_name, int new_room_number,
			CorbaDateReservation new_date, CorbaTimeSlot new_time_slot) {
		try {
			return campus.changeReservation(user_id, booking_id, new_campus_name, new_room_number, new DateReservation(new_date), new TimeSlot(new_time_slot));
		} catch (NotBoundException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
