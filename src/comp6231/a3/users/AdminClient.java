package comp6231.a3.users;

import java.util.ArrayList;
import java.util.logging.Logger;

import comp6231.a3.common.DateReservation;
import comp6231.a3.common.LoggerHelper;
import comp6231.a3.common.TimeSlot;
import comp6231.a3.common.users.CampusUser;

/**
 * @author saman
 *
 */
public class AdminClient {
	
	private Logger logger;
	private CampusUser user;
	AdminInterface admin_interface;
	
	public AdminClient(CampusUser user, Logger logger, AdminInterface admin_interface) {
		this.user = user;
		this.logger = logger;
		this.admin_interface = admin_interface;
		logger.info("**********************************");
	}

	public boolean createRoom(int room_number, DateReservation date, ArrayList<TimeSlot> time_slots) {
		String log_msg = String.format("%s sending createRoom(%d, %s, %s)", user.getUserId(), room_number, date, time_slots);
		logger.info(LoggerHelper.format(log_msg));
		boolean status = admin_interface.createRoom(user.getUserId(), room_number, date, time_slots);
		log_msg = String.format("%s createRoom(%d, %s, %s): %s", user.getUserId(), room_number, date, time_slots, status);
		logger.info(LoggerHelper.format(log_msg));
		return status;
	}

	public boolean deleteRoom(int room_number, DateReservation date, ArrayList<TimeSlot> time_slots) {
		String log_msg = String.format("%s sending deleteRoom(%d, %s, %s)", user.getUserId(), room_number, date, time_slots);
		logger.info(LoggerHelper.format(log_msg));
		boolean status = admin_interface.deleteRoom(user.getUserId(), room_number, date, time_slots);
		log_msg = String.format("%s deleteRoom(%d, %s, %s): %s", user.getUserId(), room_number, date, time_slots, status);
		return status;
	}

	public boolean startWeek() {
		String log_msg = String.format("%s sending startWeek", user.getUserId());
		logger.info(LoggerHelper.format(log_msg));
		boolean status = admin_interface.startWeek(user.getUserId());
		log_msg = String.format("%s sending startWeek: %s", user.getUserId(), status);
		return status;
	}

	public void testMethod() {
		admin_interface.testMethod();		
	}

}
