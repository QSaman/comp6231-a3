/**
 * 
 */
package comp6231.a3.common.users;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import comp6231.a3.common.DateReservation;
import comp6231.a3.common.TimeSlot;

/**
 * @author saman
 *
 */
public interface AdminOperations extends Remote {
	
	boolean createRoom(String user_id, int room_number, DateReservation date, ArrayList<TimeSlot> time_slots) throws RemoteException;
	boolean deleteRoom(String user_id, int room_number, DateReservation date, ArrayList<TimeSlot> time_slots) throws RemoteException;
	boolean startWeek(String user_id) throws RemoteException, NotBoundException, IOException, InterruptedException;
	void testMethod() throws RemoteException;

}
