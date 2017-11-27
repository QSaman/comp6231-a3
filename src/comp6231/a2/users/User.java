/**
 * 
 */
package comp6231.a2.users;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import comp6231.a2.common.DateReservation;
import comp6231.a2.common.TimeSlot;
import comp6231.a2.common.TimeSlotResult;
import comp6231.a2.common.users.AdminOperations;
import comp6231.a2.common.users.CampusUser;
import comp6231.a2.common.users.StudentOperations;

/**
 * @author saman
 *
 */

//int room_number, Calendar date, ArrayList<TimeSlot> time_slots
public class User {

	/**
	 * @param args
	 * @throws NotBoundException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws NotBoundException, IOException, InterruptedException {
		System.setProperty("java.security.policy", "file:./src/comp6231/security.policy");
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		Registry registry = LocateRegistry.getRegistry();
		AdminOperations user = (AdminOperations)registry.lookup("DVL");
		test(user);
		test((StudentOperations)user);
	}
	
	public static void test(AdminOperations user) throws RemoteException
	{
		TimeSlot time_slot = new TimeSlot("7:10 - 20:32");
		System.out.println(time_slot);
		DateReservation date = new DateReservation("1-10-2017");
		System.out.println(date);
		
		CampusUser user1 = new CampusUser("DVLS1111");
		CampusUser user2 = new CampusUser("DVLA1111");
		System.out.println(user1.getUserType() + " " + user1.getCampus());
		System.out.println(user2.getUserType() + " " + user2.getCampus());
		
		user.testMethod();
		ArrayList<TimeSlot> time_slots = new ArrayList<TimeSlot>();
		time_slots.add(new TimeSlot(7, 1, 8, 15));
		time_slots.add(new TimeSlot(9, 15, 10, 15));
		testCreateRoom(user, time_slots);
		time_slots.add(new TimeSlot(11, 0, 14, 55));
		testCreateRoom(user, time_slots);
		testDeleteRoom(user);
		
	}
	
	public static void test(StudentOperations user) throws NotBoundException, IOException, InterruptedException
	{
		System.out.println(user.bookRoom("DVLS1111", "DVL", 777, new DateReservation("17-09-2017"), new TimeSlot("09:15 - 10:15")));
		String bookd_id = user.bookRoom("DVLS1111", "DVL", 777, new DateReservation("17-09-2017"), new TimeSlot("09:15 - 10:15"));
		if (bookd_id == null)
			System.out.println("Correct");
		else
			System.out.println("Incorrect");
		bookd_id = user.bookRoom("DVLS1111", "DVL", 777, new DateReservation("17-09-2017"), new TimeSlot("12:15 - 14:15"));
		if (bookd_id == null)
			System.out.println("Correct");
		else
			System.out.println("Incorrect");
		boolean status = user.cancelBooking("DVLS1112", "DVL@17-9-2017@777#1");
		if (status)
			System.out.println("cancel booking incorrect");
		else
			System.out.println("cancel booking correct");
		status = user.cancelBooking("DVLS1111", "DVL@17-9-2017@777#1");
		if (status)
			System.out.println("cancel booking correct");
		else
			System.out.println("cancel booking incorrect");
		Registry registry = LocateRegistry.getRegistry();
		user = (StudentOperations)registry.lookup("KKL");
		String booking_id = user.bookRoom("KKLS1111", "DVL", 777, new DateReservation("17-09-2017"), new TimeSlot("11:00 - 14:55"));
		System.out.println("udp server booking id: " + booking_id);
		status = user.cancelBooking("KKLS2111", "DVL@17-9-2017@777#2");
		if (status)
			System.out.println("cancel booking incorrect");
		else
			System.out.println("cancel booking correct");
		status = user.cancelBooking("KKLS1111", "DVL@17-9-2017@877#2");
		if (status)
			System.out.println("cancel booking incorrect");
		else
			System.out.println("cancel booking correct");
		status = user.cancelBooking("KKLS1111", "DVL@17-9-2017@777#2");
		if (status)
			System.out.println("cancel booking correct");
		else
			System.out.println("cancel booking incorrect");
		ArrayList<TimeSlotResult> res = user.getAvailableTimeSlot(new DateReservation("17-09-2017"));
		if (res == null)
			System.out.println("Incorrect");
		else
		{
			System.out.println(res.size());
			for (TimeSlotResult ts_res : res)
				System.out.println(ts_res.getCampusName() + ": " + ts_res.getTotalAvailableSlots());
		}
	}
	
	public static void testCreateRoom(AdminOperations user, ArrayList<TimeSlot> time_slots)
	{
		DateReservation date = new DateReservation("17-09-2017");
		int room_number = 777;
		try {
			user.createRoom("DVLA1111", room_number, date, time_slots);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testDeleteRoom(AdminOperations user)
	{
		DateReservation date = new DateReservation("17-09-2017");
		int room_number = 777;
		ArrayList<TimeSlot> time_slots = new ArrayList<TimeSlot>();
		time_slots.add(new TimeSlot(7, 1, 8, 15));
		try {
			user.deleteRoom("DVLA1111", room_number, date, time_slots);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
