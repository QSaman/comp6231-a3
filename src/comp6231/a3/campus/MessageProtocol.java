/**
 * 
 */
package comp6231.a3.campus;

import comp6231.a3.common.DateReservation;
import comp6231.a3.common.TimeSlot;

/**
 * @author saman
 *
 * This class is not thread-safe!
 */
public class MessageProtocol {
	public final static String delimiter = "-#__#-";
	private static int message_id_generator;
	private static final Object message_id_generator_lock = new Object();

	private int message_id;
	private String booking_id;
	private String user_id;
	private int room_numer;
	private DateReservation date;
	private TimeSlot time_slot;
	private boolean status;
	private int available_timeslot_number;
	
	enum MessageType
	{
		Book_Room,
		Book_Room_Response,
		Cancel_Book_Room,
		Cancel_Book_Room_Response,
		Get_Available_TimeSlots,
		Get_Available_TimeSlots_Response,
		Remove_Student_Record,
		Remove_Student_Record_Response,
		Start_Week,
		Start_Week_Response
	}
	
	public static int generateMessageId()
	{
		synchronized (message_id_generator_lock) {
			++message_id_generator;			
		}
		return message_id_generator;
	}
	/**
	 * 
	 * @param message_id use generateMessageId to get a new one
	 * @param room_number
	 * @param date
	 * @param time_slot
	 * @return
	 */
	public static byte[] encodeBookRoomMessage(int message_id, String user_id, int room_number, DateReservation date, TimeSlot time_slot)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(MessageType.Book_Room.toString()).append(delimiter).append(message_id).append(delimiter);
		sb.append(user_id).append(delimiter).append(room_number);
		sb.append(delimiter).append(date.toString()).append(delimiter).append(time_slot.toString());
		//System.out.println("Encoded message: " + sb.toString());
		return sb.toString().getBytes();
	}
	
	private void decodeBookRoomMessage(String[] tokens)
	{
		if (tokens.length != 6)
			throw new IllegalArgumentException("The length of message tokens is " + tokens.length + " instead of 6");
		message_id = Integer.parseInt(tokens[1].trim());
		user_id = tokens[2].trim();
		room_numer = Integer.parseInt(tokens[3].trim());
		date = new DateReservation(tokens[4].trim());
		time_slot = new TimeSlot(tokens[5].trim());
	}
	
	public static byte[] encodeBookRoomResponseMessage(int message_id, String booking_id)
	{
		String status = (booking_id == null ? "failed" : "success");
		StringBuilder sb = new StringBuilder();
		sb.append(MessageType.Book_Room_Response.toString()).append(delimiter).append(message_id).append(delimiter).append(status);
		if (booking_id != null)
			sb.append(delimiter).append(booking_id);
		//System.out.println("Encoded message: " + sb.toString());
		return sb.toString().getBytes();
	}
	
	public void decodeBookRoomResponseMessage(String[] tokens)
	{
		if (tokens.length < 3)
			throw new IllegalArgumentException("The number of message tokens are less than 3");
		message_id = Integer.parseInt(tokens[1].trim());
		String status = tokens[2].trim();
		if (status.equals("failed"))
		{
			booking_id = null;
			return;
		}
		if (tokens.length != 4)
			throw new IllegalArgumentException("The number of message tokens should be 4 not " + tokens.length);
		booking_id = tokens[3].trim();
	}
	
	public static byte[] encodeCancelBookRoomMessage(int message_id, String user_id, String booking_id)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(MessageType.Cancel_Book_Room.toString()).append(delimiter).append(message_id).append(delimiter);
		sb.append(user_id).append(delimiter).append(booking_id);
		//System.out.println("Encoded message: " + sb.toString());
		return sb.toString().getBytes();
	}
	
	public void decodeCancelBookRoomMessage(String[] tokens)
	{
		if (tokens.length != 4)
			throw new IllegalArgumentException("The number of message tokens are not 4");
		message_id = Integer.parseInt(tokens[1].trim());
		user_id = tokens[2].trim();
		booking_id = tokens[3].trim();
	}
	
	public static byte[] encodeCancelBookRoomResponseMessage(int message_id, boolean status)
	{
		String status_str = (status ? "success" : "failed");
		StringBuilder sb = new StringBuilder();
		sb.append(MessageType.Cancel_Book_Room_Response).append(delimiter).append(message_id).append(delimiter).append(status_str);
		//System.out.println("Encoded message: " + sb.toString());
		return sb.toString().getBytes();
	}
	
	public void decodeCancelBookRoomResponseMessage(String[] tokens)
	{
		if (tokens.length != 3)
			throw new IllegalArgumentException("The number of tokens should be 3");
		message_id = Integer.parseInt(tokens[1].trim());
		status = (tokens[2].trim().equals("success") ? true : false);
	}
	
	public static byte[] encodeGetAvailableTimeSlotsMessage(int message_id, DateReservation date)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(MessageType.Get_Available_TimeSlots.toString()).append(delimiter).append(message_id).append(delimiter);
		sb.append(date);
		//System.out.println("Encoded message: " + sb.toString());
		return sb.toString().getBytes();
	}
	
	public void decodeGetAvailableTimeSlotsMessage(String[] tokens)
	{
		if (tokens.length != 3)
			throw new IllegalArgumentException("The number of tokens should be 3");
		message_id = Integer.parseInt(tokens[1].trim());
		date = new DateReservation(tokens[2].trim());
	}
	
	public static byte[] encodeGetAvailableTimeSlotsResponseMessage(int message_id, int available_timeslot_number)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(MessageType.Get_Available_TimeSlots_Response.toString()).append(delimiter).append(message_id);
		sb.append(delimiter).append(available_timeslot_number);
		//System.out.println("Encoded message: " + sb.toString());
		return sb.toString().getBytes();
	}
	
	public void decodeGetAvailableTimeSlotsResponseMessage(String[] tokens)
	{
		if (tokens.length != 3)
			throw new IllegalArgumentException("The number of tokens should be 3");
		message_id = Integer.parseInt(tokens[1].trim());
		available_timeslot_number = Integer.parseInt(tokens[2].trim());
	}
	
	public static byte[] encodeRemoveStudentRecordMessage(int message_id, String user_id, String booking_id)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(MessageType.Remove_Student_Record.toString()).append(delimiter).append(message_id).append(delimiter);
		sb.append(user_id).append(delimiter).append(booking_id);
		//System.out.println("Encoded message: " + sb.toString());
		return sb.toString().getBytes();
	}
	
	public void decodeRemoveStudentRecordMessage(String tokens[])
	{
		if (tokens.length != 4)
			throw new IllegalArgumentException("The number of tokens should be 4");
		message_id = Integer.parseInt(tokens[1].trim());
		user_id = tokens[2].trim();
		booking_id = tokens[3].trim();
	}
	
	public static byte[] encodeRemoveStudentRecordResponseMessage(int message_id, boolean status)
	{
		String status_str = (status ? "success" : "failed");
		StringBuilder sb = new StringBuilder();
		sb.append(MessageType.Remove_Student_Record_Response.toString()).append(delimiter).append(message_id);
		sb.append(delimiter).append(status_str);
		//System.out.println("Encoded message: " + sb.toString());
		return sb.toString().getBytes();
	}
	
	public void decodeRemoveStudentRecordResponseMessage(String[] tokens)
	{
		if (tokens.length != 3)
			throw new IllegalArgumentException("The number of tokens should be 3");
		message_id = Integer.parseInt(tokens[1].trim());
		status = (tokens[2].trim().equals("success") ? true : false);		
	}
	
	public static byte[] encodeStartWeekMessage(int message_id)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(MessageType.Start_Week.toString()).append(delimiter).append(message_id);
		//System.out.println("Encoded message: " + sb.toString());
		return sb.toString().getBytes();
	}
	
	public void decodeStartWeekMessage(String[] tokens)
	{
		if (tokens.length != 2)
			throw new IllegalArgumentException("The number of tokens should be 2");
		message_id = Integer.parseInt(tokens[1].trim());
	}
	
	public static byte[] encodeStartWeekResponseMessage(int message_id, boolean status)
	{
		String status_str = (status ? "success" : "failed");
		StringBuilder sb = new StringBuilder();
		sb.append(MessageType.Start_Week_Response.toString()).append(delimiter).append(message_id).append(delimiter);
		sb.append(status_str);
		//System.out.println("Encoded message: " + sb.toString());
		return sb.toString().getBytes();
	}
	
	public void decodeStartWeekResponseMessage(String[] tokens)
	{
		if (tokens.length != 3)
			throw new IllegalArgumentException("The number of tokens should be 3");
		message_id = Integer.parseInt(tokens[1].trim());
		status = (tokens[2].trim().equals("success") ? true : false);	
	}
	
	public MessageType decodeMessage(byte[] message)
	{
		String str = new String(message);
		String[] tokens = str.split(delimiter);
		if (tokens.length < 1)
			throw new IllegalArgumentException("Invalid response message: " + str);
		MessageType msg_type = MessageType.valueOf(tokens[0].trim());
		switch (msg_type)
		{
		case Book_Room:
			decodeBookRoomMessage(tokens);
			break;
		case Book_Room_Response:
			decodeBookRoomResponseMessage(tokens);
			break;
		case Cancel_Book_Room:
			decodeCancelBookRoomMessage(tokens);
			break;
		case Cancel_Book_Room_Response:
			decodeCancelBookRoomResponseMessage(tokens);
			break;
		case Get_Available_TimeSlots:
			decodeGetAvailableTimeSlotsMessage(tokens);
			break;
		case Get_Available_TimeSlots_Response:
			decodeGetAvailableTimeSlotsResponseMessage(tokens);
			break;
		case Remove_Student_Record:
			decodeRemoveStudentRecordMessage(tokens);
			break;
		case Remove_Student_Record_Response:
			decodeRemoveStudentRecordResponseMessage(tokens);
			break;
		case Start_Week:
			decodeStartWeekMessage(tokens);
			break;
		case Start_Week_Response:
			decodeStartWeekResponseMessage(tokens);
			break;
		default:
			throw new IllegalArgumentException("Invalid response message type: " + msg_type);
		}
		return msg_type;
	}
	
	/**
	 * @return the message_id
	 */
	public int getMessageId() {
		return message_id;
	}
	/**
	 * @return the booking_id
	 */
	public String getBookingId() {
		return booking_id;
	}
	/**
	 * @return the user_id
	 */
	public String getUserId() {
		return user_id;
	}
	/**
	 * @return the room_numer
	 */
	public int getRoomNumer() {
		return room_numer;
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
	public TimeSlot getTimeSlot() {
		return time_slot;
	}
	
	public boolean getStatus()
	{
		return status;
	}
	
	public int getAvailableTimeSlotNumber()
	{
		return available_timeslot_number;
	}
	
}
