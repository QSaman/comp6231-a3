/**
 * 
 */
package comp6231.a3.users;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Logger;

import comp6231.a3.common.DateReservation;
import comp6231.a3.common.LoggerHelper;
import comp6231.a3.common.TimeSlot;
import comp6231.a3.common.TimeSlotResult;
import comp6231.a3.common.users.CampusUser;
import comp6231.a3.common.users.StudentOperations;

/**
 * @author saman
 *
 */
public class StudentClient {
	
	StudentInterface student_interface;
	private Logger logger;
	private CampusUser user;
	
	public StudentClient(CampusUser user, Logger logger, StudentInterface student_interface)
	{
		this.student_interface = student_interface;
		this.logger = logger;
		this.user = user;
		logger.info("**********************************");
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.users.StudentOperations#bookRoom(java.lang.String, java.lang.String, int, comp6231.a2.common.DateReservation, comp6231.a2.common.TimeSlot)
	 */
	public String bookRoom(String campus_name, int room_number, DateReservation date,
			TimeSlot time_slot) {
		String log_msg = String.format("sending bookRoom(campus name: %s, room number: %d, date: %s, time slot: %s)", 
				campus_name, room_number, date, time_slot);
		logger.info(LoggerHelper.format(log_msg));
		String res = student_interface.bookRoom(user.getUserId(), campus_name, room_number, date, time_slot);
		log_msg = String.format("bookRoom(campus name: %s, room number: %d, date: %s, time slot: %s): %s", 
				campus_name, room_number, date, time_slot, (res == null ? "null" : res));
		logger.info(LoggerHelper.format(log_msg));
		return res;
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.users.StudentOperations#getAvailableTimeSlot(comp6231.a2.common.DateReservation)
	 */
	public ArrayList<TimeSlotResult> getAvailableTimeSlot(DateReservation date) {
		String log_msg = String.format("%s is sending getAvailableTimeSlot(date %s)", user.getUserId(), date);
		logger.info(LoggerHelper.format(log_msg));
		ArrayList<TimeSlotResult> res = student_interface.getAvailableTimeSlot(date);
		log_msg = String.format("%s is sending getAvailableTimeSlot(date %s): %s", user.getUserId(), date, res);
		return res;
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.users.StudentOperations#cancelBooking(java.lang.String, java.lang.String)
	 */
	public boolean cancelBooking(String bookingID) {
		String log_msg = String.format("%s is sending cancelBooking(booking id: %s)", user.getUserId(), bookingID);
		logger.info(LoggerHelper.format(log_msg));
		boolean status = student_interface.cancelBooking(user.getUserId(), bookingID);
		log_msg = String.format("%s is sending cancelBooking(booking id: %s)", user.getUserId(), bookingID);
		logger.info(LoggerHelper.format(log_msg));
		return status;
	}
	
	public String changeReservation(String booking_id, String new_campus_name, int new_room_number, DateReservation new_date, TimeSlot new_time_slot)
	{
		String log_msg = String.format("%s is sending changeReservation(booking id: %s, new_campus_name: %s, new_room_numer: %d, new_date: %s, new_time_slot: %s)",
				user.getUserId(), booking_id, new_campus_name, new_room_number, new_date, new_time_slot);
		logger.info(LoggerHelper.format(log_msg));
		String res = student_interface.changeReservation(user.getUserId(), booking_id, new_campus_name, new_room_number, new_date, new_time_slot);
		log_msg = String.format("%s is sending changeReservation(booking id: %s, new_campus_name: %s, new_room_numer: %d, new_date: %s, new_time_slot: %s): %s",
				user.getUserId(), booking_id, new_campus_name, new_room_number, new_date, new_time_slot, res == null ? "null": res);
		logger.info(LoggerHelper.format(log_msg));
		return res;
	}

}
