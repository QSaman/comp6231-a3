/**
 * 
 */
package comp6231.a3.campus.communication.corba;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.omg.CORBA.ORB;

import comp6231.a3.campus.Campus;
import comp6231.a3.common.DateReservation;
import comp6231.a3.common.TimeSlot;
import comp6231.a3.common.corba.data_structure.CorbaDateReservation;
import comp6231.a3.common.corba.data_structure.CorbaTimeSlot;
import comp6231.a3.common.corba.users.AdminOperationsPOA;
import comp6231.a3.common.users.AdminOperations;

/**
 * @author saman
 *
 */
public class AdminCampusServant extends AdminOperationsPOA {
	
	Campus campus;
	
	public AdminCampusServant(Campus campus)
	{
		this.campus = campus;
	}
	
	public void startServer(String[] args)
	{
        // create and initialize the ORB
        ORB orb = ORB.init(args, null);
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.corba.users.AdminOperationsOperations#createRoom(java.lang.String, int, comp6231.a2.common.corba.data_structure.CorbaDateReservation, comp6231.a2.common.corba.data_structure.CorbaTimeSlot[])
	 */
	@Override
	public boolean createRoom(String user_id, int room_number, CorbaDateReservation date, CorbaTimeSlot[] time_slots) {
		ArrayList<TimeSlot> ts = new ArrayList<TimeSlot>();
		for (CorbaTimeSlot cts : time_slots)
			ts.add(new TimeSlot(cts));
		return campus.createRoom(user_id, room_number, new DateReservation(date), ts);
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.corba.users.AdminOperationsOperations#deleteRoom(java.lang.String, int, comp6231.a2.common.corba.data_structure.CorbaDateReservation, comp6231.a2.common.corba.data_structure.CorbaTimeSlot[])
	 */
	@Override
	public boolean deleteRoom(String user_id, int room_number, CorbaDateReservation date, CorbaTimeSlot[] time_slots) {
		ArrayList<TimeSlot> ts = new ArrayList<TimeSlot>();
		for (CorbaTimeSlot cts : time_slots)
			ts.add(new TimeSlot(cts));
		return campus.deleteRoom(user_id, room_number, new DateReservation(date), ts);
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.corba.users.AdminOperationsOperations#startWeek(java.lang.String)
	 */
	@Override
	public boolean startWeek(String user_id) {
		try {
			return campus.startWeek(user_id);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
