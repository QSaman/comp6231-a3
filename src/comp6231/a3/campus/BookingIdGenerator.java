/**
 * 
 */
package comp6231.a3.campus;

import comp6231.a3.common.DateReservation;

/**
 * @author saman
 *
 */
public class BookingIdGenerator {
	private static final Object booking_id_number_lock = new Object();
	private static int booking_id_number = 0;
	
	private String campus_name;
	private String booking_id;
	private DateReservation date;
	private int room_number;
	
	public static String generate(String campus_name, DateReservation date, int room_number)
	{
		synchronized (booking_id_number_lock) {
			++booking_id_number;
		}
		return campus_name + "@" + date + "@" + room_number + "#" + booking_id_number;
	}
	
	public BookingIdGenerator(String booking_id)
	{
		this.booking_id = booking_id;
		parseBookingId();
	}
	
	private void parseBookingId()
	{
		int i;
		for (i = 0; i < booking_id.length() && booking_id.charAt(i) != '#'; ++i);
		if (i >= booking_id.length())
			throw new IllegalArgumentException("Invalid Booking ID: " + booking_id);
		String[] tokens = booking_id.substring(0, i).split("@");
		if (tokens.length != 3)
			throw new IllegalArgumentException("Invalid Booking ID: " + booking_id);
		campus_name = tokens[0];
		date = new DateReservation(tokens[1]);
		room_number = Integer.parseInt(tokens[2].trim());
	}
	
	public String getCampusName()
	{
		return campus_name;
	}
	
	public String getBookingId()
	{
		return booking_id;
	}
	
	public DateReservation getDate()
	{
		return date;
	}
	
	public int getRoomNumber()
	{
		return room_number;
	}
}
