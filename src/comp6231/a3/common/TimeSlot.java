/**
 * 
 */
package comp6231.a3.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import comp6231.a3.common.corba.data_structure.CorbaTimeSlot;
import comp6231.a3.common.corba.users.corba_timeslot_listHelper;

/**
 * @author saman
 *
 */
public class TimeSlot implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] hour;
	private int[] minute;
	private String booking_id;
	private String username;
	
	public TimeSlot(int hour1, int minute1, int hour2, int minute2)
	{
		initTimeSlot(hour1, minute1, hour2, minute2);
	}
	
	public TimeSlot(CorbaTimeSlot time_slot)
	{
		initTimeSlot(time_slot.hour1, time_slot.minute1, time_slot.hour2, time_slot.minute2);
	}
	
	public static CorbaTimeSlot[] toCorba(ArrayList<TimeSlot> time_slots)
	{
		CorbaTimeSlot[] ts = new CorbaTimeSlot[time_slots.size()];
		for (int i = 0; i < time_slots.size(); ++i)
			ts[i] = time_slots.get(i).toCorba();
			
		return ts;
	}
	
	public CorbaTimeSlot toCorba()
	{
		CorbaTimeSlot ret = new CorbaTimeSlot(hour[0], minute[0], hour[1], minute[1]);
		return ret;
	}
	
	public TimeSlot(String time_slot_str)
	{
		String[] tokens_str = time_slot_str.split("-");
		if (tokens_str.length != 2)
			throw new IllegalArgumentException(time_slot_str + " is not a valid time slot (hh:mm - hh:mm)");
		int[][] time = new int[2][2];
		for (int i = 0; i < 2; ++i)
			time[i] = parseTime(tokens_str[i]);
		initTimeSlot(time[0][0], time[0][1], time[1][0], time[1][1]);
	}
	/**
	 * 
	 * @param time Should be in "hh:mm" format
	 * @return
	 */
	private int[] parseTime(String time_str)
	{
		String[] tokens = time_str.split(":");
		if (tokens.length != 2)
			throw new IllegalArgumentException(tokens[0] + " is not a valid time (hh:mm)");
		int hour = Integer.parseInt(tokens[0].trim());
		int minute = Integer.parseInt(tokens[1].trim());
		int[] ret = new int[2];
		ret[0] = hour;
		ret[1] = minute;
		return ret;
	}
	
	private void initTimeSlot(int hour1, int minute1, int hour2, int minute2)
	{
		if (hour1< 0 || hour1 > 24)
			throw new IllegalArgumentException(hour1 + " is invalid for hour");
		if (hour2 < 0 || hour2 > 24)
			throw new IllegalArgumentException(hour2 + " is invalid for hour");
		if (minute1 < 0 || minute1 > 59)
			throw new IllegalArgumentException(minute1 + " is invalid for minute");
		if (minute2 < 0 || minute2 > 59)
			throw new IllegalArgumentException(minute2 + " is invalid for minute");
		if (hour2 < hour1)
			throw new IllegalArgumentException(hour2 + " should be greater than or equal to " + hour1);
		if (hour1 == hour2 && minute2 < minute1)
			throw new IllegalArgumentException(minute2 + " shoulbe ge greater than or equal to " + minute1);
		hour = new int[2];
		hour[0] = hour1;
		hour[1] = hour2;
		minute = new int[2];
		minute[0] = minute1;
		minute[1] = minute2;
		bookTimeSlot("",  "");
	}
	
	public int getTime(int index)
	{
		if (index < 0 || index > 1)
			throw new IllegalArgumentException("index should be between 0 and 1");
		return hour[index] * 60 + minute[index];
	}
	
	@Override
	public String toString()
	{
		return hour[0] + ":" + minute[0] + " - " + hour[1] + ":" + minute[1];
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(hour);
		result = prime * result + Arrays.hashCode(minute);
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
		if (!(obj instanceof TimeSlot))
			return false;
		TimeSlot other = (TimeSlot) obj;
		if (!Arrays.equals(hour, other.hour))
			return false;
		if (!Arrays.equals(minute, other.minute))
			return false;
		return true;
	}

	public boolean conflict(TimeSlot time_interval)
	{
		int[] external_time = new int[2];
		int[] time = new int[2];
		
		for (int i = 0; i < 2; ++i)
		{
			external_time[i] = time_interval.getTime(i);
			time[i] = getTime(i);
		}
		
		if (external_time[0] >= time[0] && external_time[0] < time[1])
			return true;
		if (external_time[1] >= time[0] && external_time[1] < time[1])
			return true;
		return false;
	}
	public boolean isBooked() {
		return !booking_id.isEmpty();
	}
	
	public void bookTimeSlot(String username, String booking_id)
	{
		this.username = username;
		this.booking_id = booking_id;
	}
	
	public void cancelTimeSlot()
	{
		this.username = "";
		this.booking_id = "";
	}
	
	int getHour1()
	{
		return hour[0];
	}
	
	int getMinute1()
	{
		return minute[0];
	}
	
	int getHour2()
	{
		return hour[1];
	}
	
	int getMinute2()
	{
		return minute[2];
	}

	/**
	 * @return the booking_id
	 */
	public String getBookingId() {
		return booking_id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
}
