/**
 * 
 */
package comp6231.a3.common;

import java.io.Serializable;
import java.util.Calendar;

import comp6231.a3.common.corba.data_structure.CorbaDateReservation;

/**
 * @author saman
 *
 */
public class DateReservation implements /*Comparable<DateReservation>,*/ Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int year;
	private int month;
	private int day;
	
	public DateReservation(String date) {
		String[] tokens = date.split("-");
		if (tokens.length != 3)
			throw new IllegalArgumentException("Invalid date string");
		int[] tmp = new int[3];
		for (int i = 0; i < 3; ++i)
			tmp[i] = Integer.parseInt(tokens[i].trim());
		setDate(tmp[2], tmp[1], tmp[0]);
	}
	
	public DateReservation(CorbaDateReservation date_reservation) {
		setDate(date_reservation.year, date_reservation.month, date_reservation.day);		
	}
	
	public CorbaDateReservation toCorba()
	{
		CorbaDateReservation ret = new CorbaDateReservation(year, month, day);
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + month;
		result = prime * result + year;
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
		if (!(obj instanceof DateReservation))
			return false;
		DateReservation other = (DateReservation) obj;
		if (day != other.day)
			return false;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	public DateReservation(int year, int month, int day)
	{
		setDate(year, month, day);
	}

/*	@Override
	public int compareTo(DateReservation right) {
		int delta = year - right.year;
		if (delta != 0)
			return delta;
		delta = month - right.month;
		if (delta != 0)
			return delta;
		delta = day - right.day;
		return delta;
	}	*/
	
	private void setDate(int year, int month, int day)
	{
		if (year < 1)
			throw new IllegalArgumentException("Invalid year " + year);
		if (month < 1 || month > 12)
			throw new IllegalArgumentException("Invalid month " + month);
		if (day < 1 || day > 31)
			throw new IllegalArgumentException("Invalid day " + day);
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	@Override
	public String toString()
	{
		return day + "-" + month + "-" + year;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}
	
}
