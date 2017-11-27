/**
 * 
 */
package comp6231.a3.campus;

import java.util.ArrayList;

import comp6231.a3.common.DateReservation;
import comp6231.a3.common.TimeSlot;
import comp6231.a3.common.users.CampusUser;

/**
 * @author saman
 *
 */
public class StudentRecord {	
	
	private class ReservationRecord
	{
		private DateReservation date;
		private TimeSlot time_slot;
		private String booking_id;
				
		
		public ReservationRecord(String booking_id, DateReservation date, TimeSlot time_slot)
		{
			this.date = date;
			this.time_slot = time_slot;
			this.booking_id = booking_id;
		}

		/**
		 * @return the date
		 */
		public DateReservation getDate() {
			return date;
		}

		/**
		 * @return the time_slot
		 */
		public TimeSlot getTime_slot() {
			return time_slot;
		}

		/**
		 * @return the booking_id
		 */
		public String getBooking_id() {
			return booking_id;
		}		
	}
	
	private ArrayList<ReservationRecord> records;
	private CampusUser user;
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return user.getUserId() + ", canBook?" + canBook(3);
	}

	public StudentRecord(CampusUser user)
	{
		records = new ArrayList<ReservationRecord>();
		this.user = user;
	}
	
	public synchronized void saveBookRoomRequest(String booking_id, DateReservation date, TimeSlot time_slot)
	{
			records.add(new ReservationRecord(booking_id, date, time_slot));		
	}
	
	public synchronized boolean removeBookRoomRequest(String booking_id)
	{
		for (int i = 0; i < records.size(); ++i)
		{
			String bi = records.get(i).booking_id; 
			if (bi.equals(booking_id))
			{
				records.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public synchronized boolean canBook(int max_booking_num)
	{
			return records.size() < max_booking_num;
	}
	
	public synchronized TimeSlot findTimeSlot(String booking_id)
	{			
		for (ReservationRecord record : records)
			if (record.booking_id.equals(booking_id))
			{
				return record.time_slot;
			}
		return null;
	}
}
