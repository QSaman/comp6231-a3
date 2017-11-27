/**
 * 
 */
package comp6231.a3.users;

import java.util.ArrayList;

import comp6231.a3.common.DateReservation;
import comp6231.a3.common.TimeSlot;
import comp6231.a3.common.corba.data_structure.CorbaTimeSlot;
import comp6231.a3.common.corba.users.AdminOperations;

/**
 * @author saman
 *
 */
public class AdminCorba implements AdminInterface {
	AdminOperations remote_stub;

	/**
	 * 
	 */
	public AdminCorba(AdminOperations remote_stub) {
		this.remote_stub = remote_stub;
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.users.AdminInterface#createRoom(java.lang.String, int, comp6231.a2.common.DateReservation, java.util.ArrayList)
	 */
	@Override
	public boolean createRoom(String user_id, int room_number, DateReservation date, ArrayList<TimeSlot> time_slots) {
			
		return remote_stub.createRoom(user_id, room_number, date.toCorba(), TimeSlot.toCorba(time_slots));
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.users.AdminInterface#deleteRoom(java.lang.String, int, comp6231.a2.common.DateReservation, java.util.ArrayList)
	 */
	@Override
	public boolean deleteRoom(String user_id, int room_number, DateReservation date, ArrayList<TimeSlot> time_slots) {
		return remote_stub.deleteRoom(user_id, room_number, date.toCorba(), TimeSlot.toCorba(time_slots));
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.users.AdminInterface#startWeek(java.lang.String)
	 */
	@Override
	public boolean startWeek(String user_id) {
		return remote_stub.startWeek(user_id);
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.users.AdminInterface#testMethod()
	 */
	@Override
	public void testMethod() {

	}

}
