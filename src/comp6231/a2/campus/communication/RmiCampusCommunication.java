package comp6231.a2.campus.communication;

import comp6231.a2.campus.CampusCommunication;
import comp6231.a2.campus.CampusOperations;
import comp6231.a2.campus.CampusCommunication.RemoteInfo;
import comp6231.a2.common.DateReservation;
import comp6231.a2.common.TimeSlot;
import comp6231.a2.common.TimeSlotResult;
import comp6231.a2.common.users.AdminOperations;
import comp6231.a2.common.users.StudentOperations;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Properties;

public class RmiCampusCommunication extends CampusCommunication implements AdminOperations, StudentOperations, CampusOperations {
	
	private Registry registry;
	CampusOperations campus_operations;
	
	public RmiCampusCommunication(Registry registry) {
		this.registry = registry;		
	}

	@Override
	public RemoteInfo getRemoteInfo(String campus_name) {		
		RemoteInfo info = new RemoteInfo();
		try {
			campus_operations = (CampusOperations)registry.lookup(campus_name);
			info.address = campus_operations.getAddress();
			info.port = campus_operations.getPort();
			return info;
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
		
	}

	@Override
	public String[] getAllCampusNames() {
		try {
			return registry.list();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void startServer() {
		System.setProperty("java.security.policy", "file:./src/comp6231/security.policy");
//		System.setProperty("java.rmi.server.codebase", "file:///media/NixHddData/MyStuff/Programming/Projects/Java/workspace/A1RMI/bin/");
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		
		Remote stub = null;
		try {
			stub = UnicastRemoteObject.exportObject(this, 0);
			registry.rebind(campus.getName(), stub);			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean createRoom(String user_id, int room_number, DateReservation date, ArrayList<TimeSlot> time_slots)
			throws RemoteException {
		return campus.createRoom(user_id, room_number, date, time_slots);
	}

	@Override
	public boolean deleteRoom(String user_id, int room_number, DateReservation date, ArrayList<TimeSlot> time_slots)
			throws RemoteException {
		return campus.deleteRoom(user_id, room_number, date, time_slots);
	}

	@Override
	public boolean startWeek(String user_id)
			throws RemoteException, NotBoundException, IOException, InterruptedException {
		return campus.startWeek(user_id);
	}

	@Override
	public void testMethod() throws RemoteException {
		campus.testMethod();		
	}

	@Override
	public String bookRoom(String user_id, String campus_name, int room_number, DateReservation date,
			TimeSlot time_slot) throws RemoteException, NotBoundException, IOException, InterruptedException {
		return campus.bookRoom(user_id, campus_name, room_number, date, time_slot);
	}

	@Override
	public ArrayList<TimeSlotResult> getAvailableTimeSlot(DateReservation date)
			throws RemoteException, NotBoundException, IOException, InterruptedException {
		return campus.getAvailableTimeSlot(date);
	}

	@Override
	public boolean cancelBooking(String user_id, String bookingID)
			throws RemoteException, NotBoundException, IOException, InterruptedException {
		return campus.cancelBooking(user_id, bookingID);
	}

	@Override
	public int getPort() throws RemoteException {
		return campus.getPort();
	}

	@Override
	public String getAddress() throws RemoteException {
		return campus.getAddress();
	}

	@Override
	public String getCampusName() throws RemoteException {
		return campus.getName();
	}

	@Override
	public String changeReservation(String user_id, String booking_id, String new_campus_name, int new_room_number,
			DateReservation new_date, TimeSlot new_time_slot)
			throws NotBoundException, IOException, InterruptedException {
		return campus.changeReservation(user_id, booking_id, new_campus_name, new_room_number, new_date, new_time_slot);
	}
}
