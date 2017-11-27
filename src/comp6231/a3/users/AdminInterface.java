/**
 * 
 */
package comp6231.a3.users;

import java.util.ArrayList;

import comp6231.a3.common.DateReservation;
import comp6231.a3.common.TimeSlot;

/**
 * @author saman
 *
 */
public interface AdminInterface {
	
	public boolean createRoom(String user_id, int room_number, DateReservation date, ArrayList<TimeSlot> time_slots);
	public boolean deleteRoom(String user_id, int room_number, DateReservation date, ArrayList<TimeSlot> time_slots);
	public boolean startWeek(String user_id);
	public void testMethod();

}
