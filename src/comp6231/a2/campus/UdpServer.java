/**
 * 
 */
package comp6231.a2.campus;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;

import comp6231.a2.common.DateReservation;
import comp6231.a2.common.TimeSlot;


/**
 * @author saman
 *
 */
public class UdpServer extends Thread {
	public class WaitObject
	{
		public String bookingId;
		public boolean status;
		public int available_timeslots;
	}
	private Campus campus;
	private DatagramSocket socket;	//https://stackoverflow.com/questions/6265731/do-java-sockets-support-full-duplex
	private final Object write_socket_lock = new Object();
	public final static int datagram_send_size = 256;
	private HashMap<Integer, WaitObject> wait_list;
	private final Object wait_list_lock = new Object();
	
	public UdpServer(Campus campus) throws SocketException, RemoteException
	{
		this.campus = campus;
		socket = new DatagramSocket(this.campus.getPort());
		wait_list = new HashMap<Integer, WaitObject>();
	}
	
	public void addToWaitList(int message_id, WaitObject wait_object)
	{
		Object obj = wait_list.get(message_id);
		if (obj != null)
			throw new IllegalArgumentException("message id is mapped to another object");
		synchronized (wait_list_lock) {
			wait_list.put(message_id, wait_object);
		}		
	}
	
	private void processRequest(byte[] message, InetAddress address, int port)
	{
		//System.out.println("Received message from " + address + ":" + port);
		MessageProtocol protocol = new MessageProtocol();
		MessageProtocol.MessageType type = protocol.decodeMessage(message);
		WaitObject obj = null;
		switch(type)
		{
		case Book_Room:
			try {
				String booking_id = campus.bookRoom(protocol.getUserId(), campus.getName(), protocol.getRoomNumer(), protocol.getDate(), protocol.getTimeSlot());
				byte[] reply = MessageProtocol.encodeBookRoomResponseMessage(protocol.getMessageId(), booking_id);
				sendDatagram(reply, address, port);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Book_Room_Response:
			//System.out.println("Booked_id received: " + protocol.getBookingId());			
			synchronized (wait_list_lock) {
				 obj = wait_list.get(protocol.getMessageId());
				if (obj == null)
				{
					//System.out.println("Duplicate message recieived with id " + protocol.getMessageId());
					return;
				}
			}
			obj.bookingId = protocol.getBookingId();
			synchronized (obj) {
				obj.notifyAll();
			}			
			synchronized (wait_list_lock) {
				wait_list.remove(protocol.getMessageId());
			}
			break;
		case Cancel_Book_Room:
			try {
				boolean status = campus.cancelBooking(protocol.getUserId(), protocol.getBookingId());
				byte[] reply = MessageProtocol.encodeCancelBookRoomResponseMessage(protocol.getMessageId(), status);
				sendDatagram(reply, address, port);
			} catch (NotBoundException | IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Cancel_Book_Room_Response:
			//System.out.println("UDP server Cancel booking receivend: " + protocol.getStatus());
			synchronized (wait_list_lock) {
				 obj = wait_list.get(protocol.getMessageId());
				if (obj == null)
				{
					//System.out.println("Duplicate message recieived with id " + protocol.getMessageId());
					return;
				}
			}
			obj.status = protocol.getStatus();
			synchronized (obj) {
				obj.notifyAll();
			}
			synchronized (wait_list_lock) {
				wait_list.remove(protocol.getMessageId());
			}
			break;
		case Get_Available_TimeSlots:
			//System.out.println("UDP server available time slots receivend: " + protocol.getAvailableTimeSlotNumber());
			int res = campus.getThisCampusAvailableTimeSlots(protocol.getDate());			
			try {
				byte[] reply = MessageProtocol.encodeGetAvailableTimeSlotsResponseMessage(protocol.getMessageId(), res);
				sendDatagram(reply, address, port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Get_Available_TimeSlots_Response:			
			synchronized (wait_list_lock) {
				 obj = wait_list.get(protocol.getMessageId());
				if (obj == null)
				{
					//System.out.println("Duplicate message recieived with id " + protocol.getMessageId());
					return;
				}
			}
			obj.available_timeslots = protocol.getAvailableTimeSlotNumber();
			synchronized (obj) {
				obj.notifyAll();
			}
			synchronized (wait_list_lock) {
				wait_list.remove(protocol.getMessageId());
			}
			break;
		case Remove_Student_Record:
			//System.out.println("UDP server receivend remove student record request: " + protocol.getUserId() + ", " + protocol.getBookingId());						
			try {
				boolean status = campus.removeStudentRecord(protocol.getUserId(), protocol.getBookingId());
				byte[] reply = MessageProtocol.encodeRemoveStudentRecordResponseMessage(protocol.getMessageId(), status);
				sendDatagram(reply, address, port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Remove_Student_Record_Response:
			synchronized (wait_list_lock) {
				 obj = wait_list.get(protocol.getMessageId());
				if (obj == null)
				{
					//System.out.println("Duplicate message recieived with id " + protocol.getMessageId());
					return;
				}
			}
			obj.status = protocol.getStatus();
			synchronized (obj) {
				obj.notifyAll();
			}
			synchronized (wait_list_lock) {
				wait_list.remove(protocol.getMessageId());
			}
			break;
		case Start_Week:
			//System.out.println("UDP server received start week request");
			try {
				campus.startWeek();
				byte[] reply = MessageProtocol.encodeStartWeekResponseMessage(protocol.getMessageId(), true);
				sendDatagram(reply, address, port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Start_Week_Response:
			synchronized (wait_list_lock) {
				 obj = wait_list.get(protocol.getMessageId());
				if (obj == null)
				{
					//System.out.println("Duplicate message recieived with id " + protocol.getMessageId());
					return;
				}
			}
			obj.status = protocol.getStatus();
			synchronized (obj) {
				obj.notifyAll();
			}
			synchronized (wait_list_lock) {
				wait_list.remove(protocol.getMessageId());
			}
			break;
		}
	}
	
	public void sendDatagram(byte[] message, InetAddress address, int port) throws IOException
	{
		//System.out.println("Send message to " + address + ":" + port);
		DatagramPacket packet = new DatagramPacket(message, message.length, address, port);
		synchronized (write_socket_lock) {
			socket.send(packet);
		}		
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			byte[] buffer = new byte[datagram_send_size];
			DatagramPacket packet = new DatagramPacket(buffer, datagram_send_size);
			try {
				socket.receive(packet);
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						UdpServer.this.processRequest(packet.getData(), packet.getAddress(), packet.getPort());
					}
				});
				thread.start();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
