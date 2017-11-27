package comp6231.a3.users;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import comp6231.a3.campus.Bootstrap;
import comp6231.a3.common.DateReservation;
import comp6231.a3.common.TimeSlot;
import comp6231.a3.common.TimeSlotResult;
import comp6231.a3.common.users.CampusUser;

public class Test {

	public static void main(String[] args) throws SecurityException, IOException, NotBoundException, InterruptedException {
		Bootstrap.initNonBlockingServers();		
		AdminClient admin1 = ClientUserFactory.createAdminClient(new CampusUser("DVLA1111"));
		admin1.startWeek();
		AdminClient admin2 = ClientUserFactory.createAdminClient(new CampusUser("KKLA1111"));
		AdminClient admin3 = ClientUserFactory.createAdminClient(new CampusUser("WSTA1111"));
		DateReservation date = new DateReservation("08-11-2017");
		ArrayList<TimeSlot> time_slots = new ArrayList<>();
		time_slots.add(new TimeSlot("7:00 - 8:00"));
		time_slots.add(new TimeSlot("9:00 - 10:00"));
		time_slots.add(new TimeSlot("11:00 - 12:00"));
		time_slots.add(new TimeSlot("13:00 - 14:00"));
		time_slots.add(new TimeSlot("15:00 - 16:00"));
		admin1.createRoom(201, date, time_slots);
		admin2.createRoom(201, date, time_slots);
		admin3.createRoom(201, date, time_slots);
		
		StudentClient student1 = ClientUserFactory.createStudentClient(new CampusUser("WSTS0001"));		
		String b1 = student1.bookRoom("KKL", 201, date, new TimeSlot("7:00 - 8:00"));
		String b2 = student1.bookRoom("KKL", 201, date, new TimeSlot("9:00 - 10:00"));
		String b3 = student1.bookRoom("KKL", 201, date, new TimeSlot("11:00 - 12:00"));
		String b4 = student1.bookRoom("WST", 201, date, new TimeSlot("7:00 - 8:00")); //Only three booking
		
		String bc1 = student1.changeReservation(b1, "DVL", 201, date, new TimeSlot("7:00 - 8:00"));
		String bc2 = student1.changeReservation(b2,  "WST", 201, date, new TimeSlot("9:00 - 10:00"));
		
		ArrayList<TimeSlotResult> res = student1.getAvailableTimeSlot(date);
		for (TimeSlotResult r : res)
		System.out.println(r.getCampusName() + ": " + r.getTotalAvailableSlots());
		
		student1.cancelBooking(b3);
		
		res = student1.getAvailableTimeSlot(date);
		for (TimeSlotResult r : res)
		System.out.println(r.getCampusName() + ": " + r.getTotalAvailableSlots());
		
		
		
//		StudentClient student1 = ClientUserFactory.createStudentClient(new CampusUser("DVLS0001"));
//		StudentClient student2 = ClientUserFactory.createStudentClient(new CampusUser("DVLS0002"));
//		
//		
//		ArrayList<TimeSlotResult> res = student1.getAvailableTimeSlot(date);
//		for (TimeSlotResult r : res)
//			System.out.println(r.getCampusName() + ": " + r.getTotalAvailableSlots());
//		System.out.println(student1.bookRoom("DVL", 777, date, new TimeSlot("7:00 - 8:00")));
//		System.out.println(student2.bookRoom("DVL", 777, date, new TimeSlot("7:00 - 8:00")));
//		System.out.println(student1.bookRoom("DVL", 777, date, new TimeSlot("9:00 - 10:00")));
//		System.out.println(student1.bookRoom("DVL", 777, date, new TimeSlot("11:00 - 12:00")));
//	
//		
//		System.out.println(student1.bookRoom("DVL", 777, date, new TimeSlot("15:00 - 16:00")));
//		
//		res = student1.getAvailableTimeSlot(date);
//		for (TimeSlotResult r : res)
//			System.out.println(r.getCampusName() + ": " + r.getTotalAvailableSlots());
//		System.out.println(student2.cancelBooking("DVL@17-9-2017@777#3"));
//		System.out.println(student1.cancelBooking("DVL@17-9-2017@777#3"));
//		System.out.println(student1.bookRoom("DVL", 777, date, new TimeSlot("15:00 - 16:00")));
//		
//		StudentClient student3 = ClientUserFactory.createStudentClient(new CampusUser("WSTS0001"));
//		System.out.println(student3.bookRoom("DVL", 777, date, new TimeSlot("20:00 - 22:00")));
//		
//		res = student1.getAvailableTimeSlot(date);
//		for (TimeSlotResult r : res)
//			System.out.println(r.getCampusName() + ": " + r.getTotalAvailableSlots());
				
		
//		AdminClient admin2 = ClientUserFactory.createAdminClient(new CampusUser("KKLA1111"));
//		time_slots = new ArrayList<>();
//		time_slots.add(new TimeSlot("7:00 - 8:00"));
//		time_slots.add(new TimeSlot("9:00 - 10:00"));
//		time_slots.add(new TimeSlot("11:00 - 12:00"));
//		time_slots.add(new TimeSlot("13:00 - 14:00"));
//		time_slots.add(new TimeSlot("15:00 - 16:00"));
//		admin2.createRoom(778, date, time_slots);
//		
//		res = student1.getAvailableTimeSlot(date);
//		for (TimeSlotResult r : res)
//			System.out.println(r.getCampusName() + ": " + r.getTotalAvailableSlots());
//		
//		System.out.println(student1.cancelBooking("DVL@17-9-2017@777#4"));
//		System.out.println(student1.cancelBooking("DVL@17-9-2017@777#4"));
//		System.out.println(student1.bookRoom("KKL", 778, date, new TimeSlot("15:00 - 16:00")));
//		
//		res = student1.getAvailableTimeSlot(date);
//		for (TimeSlotResult r : res)
//			System.out.println(r.getCampusName() + ": " + r.getTotalAvailableSlots());
//		
//		System.out.println(student1.cancelBooking("KKL@17-9-2017@778#5"));
//		
//		res = student2.getAvailableTimeSlot(date);
//		for (TimeSlotResult r : res)
//			System.out.println(r.getCampusName() + ": " + r.getTotalAvailableSlots());
	}

}
