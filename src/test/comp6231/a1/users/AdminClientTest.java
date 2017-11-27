/**
 * 
 */
package test.comp6231.a1.users;

import static org.junit.Assert.*;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import comp6231.a2.campus.Bootstrap;
import comp6231.a2.common.DateReservation;
import comp6231.a2.common.TimeSlot;
import comp6231.a2.common.TimeSlotResult;
import comp6231.a2.common.users.AdminOperations;
import comp6231.a2.common.users.CampusUser;
import comp6231.a2.users.AdminClient;
import comp6231.a2.users.ClientUserFactory;
import comp6231.a2.users.StudentClient;

/**
 * @author saman
 *
 */
public class AdminClientTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Bootstrap.initRmiServers();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link comp6231.a1.users.AdminClient#createRoom(int, comp6231.a1.common.DateReservation, java.util.ArrayList)}.
	 * @throws NotBoundException 
	 * @throws IOException 
	 * @throws SecurityException 
	 */
	@Test
	public final void testCreateRoomDVL() throws SecurityException, IOException, NotBoundException {
		AdminClient dvla1111 = ClientUserFactory.createAdminClient(new CampusUser("DVLA1111"));
		ArrayList<TimeSlot> time_slots = new ArrayList<TimeSlot>();
		time_slots.add(new TimeSlot(7, 1, 8, 15));
		time_slots.add(new TimeSlot(9, 15, 10, 15));
		DateReservation date = new DateReservation("17-09-2017");
		int room_number = 777;
		boolean res = dvla1111.createRoom(room_number, date, time_slots);
		assertTrue(res);
		time_slots.add(new TimeSlot(11, 0, 14, 55));
		res = dvla1111.createRoom(room_number, date, time_slots);
		assertTrue(res);
	}
	
	/**
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 * @throws NotBoundException
	 */
	@Test
	public final void testCreateRoomKKL() throws SecurityException, IOException, NotBoundException {
		AdminClient kkla1111 = ClientUserFactory.createAdminClient(new CampusUser("KKLA1111"));
		ArrayList<TimeSlot> time_slots = new ArrayList<TimeSlot>();
		time_slots.add(new TimeSlot(8, 0, 10, 0));
		time_slots.add(new TimeSlot(10, 15, 11, 15));
		time_slots.add(new TimeSlot(11, 15, 12, 15));
		time_slots.add(new TimeSlot(13, 0, 17, 15));
		DateReservation date = new DateReservation("18-09-2017");
		int room_number = 778;
		boolean res = kkla1111.createRoom(room_number, date, time_slots);
		assertTrue(res);
	}
	
	@Test
	public final void testCreateRoomWST() throws SecurityException, IOException, NotBoundException {
		AdminClient wsta1111 = ClientUserFactory.createAdminClient(new CampusUser("WSTA1111"));
		ArrayList<TimeSlot> time_slots = new ArrayList<TimeSlot>();
		time_slots.add(new TimeSlot(14, 0, 15, 0));
		time_slots.add(new TimeSlot(15, 0, 16, 0));
		time_slots.add(new TimeSlot(16, 0, 17, 0));
		time_slots.add(new TimeSlot(17, 0, 18, 0));
		time_slots.add(new TimeSlot(18, 0, 19, 0));
		time_slots.add(new TimeSlot(19, 0, 20, 0));
		DateReservation date = new DateReservation("19-09-2017");
		int room_number = 779;
		boolean res = wsta1111.createRoom(room_number, date, time_slots);
		assertTrue(res);		
	}
	
	private class ThreadParameters
	{
		int room_number;
		DateReservation date;
		ArrayList<TimeSlot> time_slots;
		
		public ThreadParameters(int room_number, DateReservation date, ArrayList<TimeSlot> time_slot)
		{
			this.room_number = room_number;
			this.date = date;
			this.time_slots = time_slot;
		}
	}
	
	private class ThreadCreateRoom implements Runnable
	{
		ArrayList<ThreadParameters> thread_paras;
		AdminClient client;
		CountDownLatch latch;
		public ThreadCreateRoom(ArrayList<ThreadParameters> thread_paras, CampusUser user, CountDownLatch latch) {
			this.thread_paras = thread_paras;
			this.latch = latch;
			try {
				client = ClientUserFactory.createAdminClient(user);
			} catch (SecurityException | IOException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			try {
				latch.await();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (ThreadParameters para : thread_paras)
			{
				boolean status = client.createRoom(para.room_number, para.date, para.time_slots);
				assertTrue(status);
			}
		}
		
	}
	
	@Test
	public final void testCreateRoomConcurrency() throws SecurityException, IOException, NotBoundException, InterruptedException {
		String[] campus_names = {"DVL", "KKL", "WST"};
		int[] number_time_slot = {66, 29, 100};
		int[] number_rooms = {10, 15, 3};
		int[] number_users = {3, 2, 5};
		
		for (int val : number_time_slot)
			assertTrue(val >= 0 && val <= 24 * 60);
		
		testStartWeek();
		
		ArrayList<Thread> threads = new ArrayList<>();
		ArrayList<ThreadParameters> all = new ArrayList<>(); 
		
		DateReservation date = new DateReservation("19-09-2017");
		CountDownLatch latch = new CountDownLatch(1);
		for (int i = 0; i < campus_names.length; ++i)
		{
			if (number_time_slot[i] == 0)
				continue;
			int duration = (24 * 60) / number_time_slot[i];
			assertTrue("Invalid duration", duration >= 0 && duration <= (24 * 60));
			HashMap<Integer, ArrayList<ThreadParameters>> thread_para = new HashMap<>();
			String user_id_prefix = campus_names[i] + "A";
			int time = 0;
			for (int j = 0; j < number_time_slot[i]; ++j, time += duration)
			{
				int user_key = j % number_users[i];
				
				int hour1 = (time / 60);
				int minute1 = time % 60;
				int hour2 = ((time + duration) / 60);
				int minute2 = (time + duration) % 60;
				TimeSlot time_slot = new TimeSlot(hour1, minute1, hour2, minute2);
				ArrayList<TimeSlot> list = new ArrayList<>();
				list.add(time_slot);
				int room_number = j % number_rooms[i];				
				ArrayList<ThreadParameters> res = thread_para.get(user_key);
				if (res == null)
				{
					res = new ArrayList<ThreadParameters>();
					thread_para.put(user_key, res);
				}
				ThreadParameters tmp = new ThreadParameters(room_number, date, list);
				res.add(tmp);
				all.add(tmp);				
			}
			for (int j = 0; j < number_users[i]; ++j)
			{
				CampusUser user = new CampusUser(user_id_prefix + String.format("%04d", j));
				ArrayList<ThreadParameters> p = thread_para.get(j);
				if (p == null)
					continue;
				Thread thread = new Thread(new ThreadCreateRoom(p, user, latch));
				threads.add(thread);
				thread.start();
			}
		}
		
		int thread_count = 0;
		for (int s : number_users)
			thread_count += s;
		assertTrue(thread_count == threads.size());
		latch.countDown();	//Run all threads in the same time (Depends on OS and the number of cores in CPU)
		for (Thread thread : threads)
			thread.join();
		StudentClient client = ClientUserFactory.createStudentClient(new CampusUser("DVLS0001"));
		ArrayList<TimeSlotResult> res = client.getAvailableTimeSlot(date);
		for (TimeSlotResult r : res)
		{
			boolean found = false;
			for (int i = 0; i < campus_names.length; ++i)
			{
				if (campus_names[i].equals(r.getCampusName()))
				{
					assertTrue(r.getTotalAvailableSlots() == number_time_slot[i]);
					found = true;
					break;
				}
			}
			assertTrue(found);
		}
	}

	/**
	 * Test method for {@link comp6231.a1.users.AdminClient#deleteRoom(int, comp6231.a1.common.DateReservation, java.util.ArrayList)}.
	 * @throws NotBoundException 
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws InterruptedException 
	 */
	@Test
	public final void testDeleteRoomDVL() throws SecurityException, IOException, NotBoundException, InterruptedException {
		AdminClient dvla1111 = ClientUserFactory.createAdminClient(new CampusUser("DVLA1111"));
		dvla1111.startWeek();
		testCreateRoomDVL();
		
		DateReservation date = new DateReservation("17-09-2017");
		int room_number = 777;
		ArrayList<TimeSlot> time_slots = new ArrayList<TimeSlot>();
		time_slots.add(new TimeSlot(7, 1, 8, 15));
		boolean res = dvla1111.deleteRoom(room_number, date, time_slots);
		assertTrue(res);
	}

	/**
	 * Test method for {@link comp6231.a1.users.AdminClient#startWeek()}.
	 * @throws NotBoundException 
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws InterruptedException 
	 */
	@Test
	public final void testStartWeek() throws SecurityException, IOException, NotBoundException, InterruptedException {
		AdminClient dvla1111 = ClientUserFactory.createAdminClient(new CampusUser("DVLA1111"));
		dvla1111.startWeek();
	}

}
