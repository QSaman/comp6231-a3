/**
 * 
 */
package comp6231.a3.common.web_service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 * @author saman
 *
 */
@WebService
@SOAPBinding(style=Style.RPC)
public interface StudentOperations {
	String bookRoom(String user_id, String campus_name, int room_number, String date, String time_slot);
	String getAvailableTimeSlot(String date);
	boolean cancelBooking(String user_id, String bookingID);
	public String changeReservation(String user_id, String booking_id, String new_campus_name, int new_room_number, String new_date, String new_time_slot);
}
