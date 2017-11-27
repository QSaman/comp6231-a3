package comp6231.a2.users;

import java.util.ArrayList;

import comp6231.a2.common.DateReservation;
import comp6231.a2.common.LoggerHelper;
import comp6231.a2.common.TimeSlot;
import comp6231.a2.common.TimeSlotResult;

public interface StudentInterface {
	public String bookRoom(String user_id, String campus_name, int room_number, DateReservation date, TimeSlot time_slot);
	public ArrayList<TimeSlotResult> getAvailableTimeSlot(DateReservation date);
	public boolean cancelBooking(String user_id, String bookingID);
	public String changeReservation(String user_id, String booking_id, String new_campus_name, int new_room_number, DateReservation new_date, TimeSlot new_time_slot);

}
