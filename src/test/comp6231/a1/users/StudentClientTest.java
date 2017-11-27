/**
 * 
 */
package test.comp6231.a1.users;

import static org.junit.Assert.*;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import comp6231.a2.campus.Bootstrap;
import comp6231.a2.common.DateReservation;
import comp6231.a2.common.TimeSlot;
import comp6231.a2.common.users.CampusUser;
import comp6231.a2.users.ClientUserFactory;
import comp6231.a2.users.StudentClient;

/**
 * @author saman
 *
 */
public class StudentClientTest {
	
	private AdminClientTest admin_test;
	private ArrayList<Student> booking_list_dvl;
	
	class Student
	{
		public String student_id;
		public String booking_id;
		public Student(String student_id, String booking_id)
		{
			this.student_id = student_id;
			this.booking_id = booking_id;
		}		
	}
	
	public StudentClientTest() {
		admin_test = new AdminClientTest();
	}

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
	 * Test method for {@link comp6231.a1.users.StudentClient#bookRoom(java.lang.String, int, comp6231.a1.common.DateReservation, comp6231.a1.common.TimeSlot)}.
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 */
	@Test
	public final void testBookRoomDVL() throws RemoteException, NotBoundException, IOException, InterruptedException {
		admin_test.testStartWeek();
		admin_test.testCreateRoomDVL();
		admin_test.testCreateRoomKKL();
		admin_test.testCreateRoomWST();
		
		StudentClient client = ClientUserFactory.createStudentClient(new CampusUser("DVLS1111"));
		booking_list_dvl = new ArrayList<Student>();
		String booking_id = client.bookRoom("DVL", 777, new DateReservation("17-09-2017"), new TimeSlot("09:15 - 10:15"));
		assertNotNull(booking_id);
		booking_list_dvl.add(new Student("DVLS1111", booking_id));
		booking_id = client.bookRoom("DVL", 777, new DateReservation("17-09-2017"), new TimeSlot("09:15 - 10:15"));
		assertNull(booking_id);
		booking_id = client.bookRoom("DVL", 777, new DateReservation("17-09-2017"), new TimeSlot("12:15 - 12:16"));
		assertNull(booking_id);
		booking_id = client.bookRoom("KKL", 778, new DateReservation("18-09-2017"), new TimeSlot(8, 0, 10, 0));
		assertNotNull(booking_id);
		booking_list_dvl.add(new Student("DVLS1111", booking_id));
		booking_id = client.bookRoom("KKL", 778, new DateReservation("18-09-2017"), new TimeSlot(8, 0, 10, 0));
		assertNull("This time slot is reserved before!", booking_id);
		StudentClient client2 = ClientUserFactory.createStudentClient(new CampusUser("DVLS1112"));
		booking_id = client2.bookRoom("KKL", 778, new DateReservation("18-09-2017"), new TimeSlot(13, 0, 17, 15));
		assertNotNull(booking_id);
		booking_list_dvl.add(new Student("DVLS1112", booking_id));
		StudentClient client3 = ClientUserFactory.createStudentClient(new CampusUser("DVLS1113"));
		booking_id = client3.bookRoom("WST", 779, new DateReservation("19-09-2017"), new TimeSlot(18, 0, 19, 0));
		assertNotNull(booking_id);
		booking_list_dvl.add(new Student("DVLS1113", booking_id));
		booking_id = client3.bookRoom("WST", 779, new DateReservation("19-09-2017"), new TimeSlot(19, 0, 20, 0));
		assertNotNull(booking_id);
		booking_list_dvl.add(new Student("DVLS1113", booking_id));
	}
	
	@Test
	public final void testchangeReservation() throws SecurityException, IOException, NotBoundException, InterruptedException
	{
		admin_test.testStartWeek();
		admin_test.testCreateRoomDVL();
		admin_test.testCreateRoomKKL();
		admin_test.testCreateRoomWST();
		
		StudentClient client = ClientUserFactory.createStudentClient(new CampusUser("DVLS1111"));
		booking_list_dvl = new ArrayList<Student>();
		String booking_id = client.bookRoom("DVL", 777, new DateReservation("17-09-2017"), new TimeSlot("09:15 - 10:15"));
		assertNotNull(booking_id);
		booking_list_dvl.add(new Student("DVLS1111", booking_id));		
		booking_id = client.bookRoom("KKL", 778, new DateReservation("18-09-2017"), new TimeSlot(8, 0, 10, 0));
		assertNotNull(booking_id);
		booking_list_dvl.add(new Student("DVLS1111", booking_id));
		booking_id = client.changeReservation(booking_list_dvl.get(0).booking_id, "DVL", 777, new DateReservation("18-09-2017"), new TimeSlot(7, 1, 8, 15));
		assertNull(booking_id);
		booking_id = client.changeReservation(booking_list_dvl.get(0).booking_id, "DVL", 777, new DateReservation("17-09-2017"), new TimeSlot(7, 1, 8, 15));
		assertNotNull(booking_id);
		booking_list_dvl.set(0, new Student("DVLS1111", booking_id));
		booking_id = client.bookRoom("DVL", 777, new DateReservation("17-09-2017"), new TimeSlot("09:15 - 10:15"));
		assertNotNull(booking_id);
		booking_list_dvl.add(new Student("DVLS1111", booking_id));
		booking_id = client.changeReservation(booking_list_dvl.get(1).booking_id, "WST", 779, new DateReservation("19-09-2017"), new TimeSlot(14, 0, 15, 0));
		assertNotNull(booking_id);
		booking_list_dvl.set(1, new Student("DVLS1111", booking_id));
		boolean status = client.cancelBooking(booking_list_dvl.get(0).booking_id);
		assertTrue(status);
		booking_id = client.bookRoom("KKL", 778, new DateReservation("18-09-2017"), new TimeSlot(8, 0, 10, 0));
		assertNotNull(booking_id);
		booking_list_dvl.add(new Student("DVLS1111", booking_id));
	}

	/**
	 * Test method for {@link comp6231.a1.users.StudentClient#getAvailableTimeSlot(comp6231.a1.common.DateReservation)}.
	 */
	@Test
	public final void testGetAvailableTimeSlot() {
		//fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link comp6231.a1.users.StudentClient#cancelBooking(java.lang.String)}.
	 * @throws NotBoundException 
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws InterruptedException 
	 */
	@Test
	public final void testCancelBooking() throws SecurityException, IOException, NotBoundException, InterruptedException {
		testBookRoomDVL();
		
		StudentClient client = ClientUserFactory.createStudentClient(new CampusUser("DVLS1111"));
		boolean status = client.cancelBooking("DVL@17-9-2017@777#999999");
		assertFalse("This booking id is not valid", status);
		client = ClientUserFactory.createStudentClient(new CampusUser(booking_list_dvl.get(0).student_id));
		status = client.cancelBooking(booking_list_dvl.get(0).booking_id);
		assertTrue(status);
		client = ClientUserFactory.createStudentClient(new CampusUser(booking_list_dvl.get(4).student_id));
		status = client.cancelBooking(booking_list_dvl.get(4).booking_id);
		assertTrue(status);
		status = client.cancelBooking(booking_list_dvl.get(4).booking_id);
		assertFalse(status);
	}

}
