/**
 * 
 */
package comp6231.a3.common;

import java.io.Serializable;
import java.util.ArrayList;

import comp6231.a3.common.corba.data_structure.CorbaTimeSlot;
import comp6231.a3.common.corba.data_structure.CorbaTimeSlotResult;

/**
 * @author saman
 *
 */
public class TimeSlotResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String campusName;
	private int totalAvailableSlots;

	@Override
	public String toString()
	{
		return campusName + ": " + totalAvailableSlots;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((campusName == null) ? 0 : campusName.hashCode());
		result = prime * result + totalAvailableSlots;
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
		if (!(obj instanceof TimeSlotResult))
			return false;
		TimeSlotResult other = (TimeSlotResult) obj;
		if (campusName == null) {
			if (other.campusName != null)
				return false;
		} else if (!campusName.equals(other.campusName))
			return false;
		if (totalAvailableSlots != other.totalAvailableSlots)
			return false;
		return true;
	}
	
	public TimeSlotResult(CorbaTimeSlotResult time_slot_result)
	{
		campusName = time_slot_result.campus_name;
		totalAvailableSlots = time_slot_result.total_available_slots;
	}

	public TimeSlotResult(String campus_name, int total_available_slots) {
		campusName = campus_name;
		totalAvailableSlots = total_available_slots;
	}
	
	public CorbaTimeSlotResult toCorba()
	{
		CorbaTimeSlotResult ret = new CorbaTimeSlotResult(getCampusName(), getTotalAvailableSlots());
		return ret;
	}
	
	public static ArrayList<TimeSlotResult> toArrayList(CorbaTimeSlotResult[] time_slots)
	{
		ArrayList<TimeSlotResult> ret = new ArrayList<>();
		for (CorbaTimeSlotResult val : time_slots)
			ret.add(new TimeSlotResult(val));
		return ret;
	}
	
	public String getCampusName() {
		return campusName;
	}
	public int getTotalAvailableSlots() {
		return totalAvailableSlots;
	}

}
