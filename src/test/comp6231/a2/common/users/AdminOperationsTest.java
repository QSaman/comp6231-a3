package test.comp6231.a2.common.users;

import static org.junit.Assert.*;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import comp6231.a3.campus.Bootstrap;
import comp6231.a3.common.DateReservation;
import comp6231.a3.common.TimeSlot;
import comp6231.a3.common.users.AdminOperations;

public class AdminOperationsTest {
	private static Registry registry;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Bootstrap.initNonBlockingServers();
		System.setProperty("java.security.policy", "file:./src/comp6231/security.policy");
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		registry = LocateRegistry.getRegistry();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testCreateRoomDVL() throws AccessException, RemoteException, NotBoundException {
		AdminOperations user = (AdminOperations)registry.lookup("DVL");
		ArrayList<TimeSlot> time_slots = new ArrayList<TimeSlot>();
		time_slots.add(new TimeSlot(7, 1, 8, 15));
		time_slots.add(new TimeSlot(9, 15, 10, 15));
		DateReservation date = new DateReservation("17-09-2017");
		int room_number = 777;
		boolean res = user.createRoom("DVLS1111", room_number, date, time_slots);
		assertFalse("Student cannot add a new time slot", res);
		res = user.createRoom("KKLA1111", room_number, date, time_slots);
		assertFalse("Admin from another campus cannot create time slot in DVL", res);
		res = user.createRoom("DVLA1111", room_number, date, time_slots);
		assertTrue(res);
		time_slots.add(new TimeSlot(11, 0, 14, 55));
		res = user.createRoom("DVLA1111", room_number, date, time_slots);
		assertTrue(res);
	}
	
	@Test
	public final void testCreateRoomKKL() throws AccessException, RemoteException, NotBoundException {
		AdminOperations user = (AdminOperations)registry.lookup("KKL");
		ArrayList<TimeSlot> time_slots = new ArrayList<TimeSlot>();
		time_slots.add(new TimeSlot(8, 0, 10, 0));
		time_slots.add(new TimeSlot(10, 15, 11, 15));
		time_slots.add(new TimeSlot(11, 15, 12, 15));
		time_slots.add(new TimeSlot(13, 0, 17, 15));
		DateReservation date = new DateReservation("18-09-2017");
		int room_number = 778;
		boolean res = user.createRoom("KKLA1111", room_number, date, time_slots);
		assertTrue(res);
	}
	
	@Test
	public final void testCreateRoomWST() throws AccessException, RemoteException, NotBoundException {
		AdminOperations user = (AdminOperations)registry.lookup("WST");
		ArrayList<TimeSlot> time_slots = new ArrayList<TimeSlot>();
		time_slots.add(new TimeSlot(14, 0, 15, 0));
		time_slots.add(new TimeSlot(15, 0, 16, 0));
		time_slots.add(new TimeSlot(16, 0, 17, 0));
		time_slots.add(new TimeSlot(17, 0, 18, 0));
		time_slots.add(new TimeSlot(18, 0, 19, 0));
		time_slots.add(new TimeSlot(19, 0, 20, 0));
		DateReservation date = new DateReservation("19-09-2017");
		int room_number = 779;
		boolean res = user.createRoom("WSTA1111", room_number, date, time_slots);
		assertTrue(res);		
	}

	@Test
	public final void testDeleteRoomDVL() throws NotBoundException, IOException, InterruptedException {
		AdminOperations user = (AdminOperations)registry.lookup("DVL");
		startWeek();
		testCreateRoomDVL();		
		DateReservation date = new DateReservation("17-09-2017");
		int room_number = 777;
		ArrayList<TimeSlot> time_slots = new ArrayList<TimeSlot>();
		time_slots.add(new TimeSlot(7, 1, 8, 15));
		boolean res = user.deleteRoom("KKLA1111", room_number, date, time_slots);
		assertFalse("admin from another campus shouldn't delete the room", res);
		res = user.deleteRoom("DVLA1111", room_number, date, time_slots);
		assertTrue(res);
	}
	
	public final void startWeek() throws NotBoundException, IOException, InterruptedException {
		AdminOperations user = (AdminOperations)registry.lookup("DVL");
		user.startWeek("DVLA0000");
	}

}
