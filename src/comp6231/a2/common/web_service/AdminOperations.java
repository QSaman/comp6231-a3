/**
 * 
 */
package comp6231.a2.common.web_service;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


/**
 * @author saman
 *
 */
@WebService
@SOAPBinding(style=Style.RPC)
public interface AdminOperations {
	boolean createRoom(String user_id, int room_number, String date, ArrayList<String> time_slots);
	boolean deleteRoom(String user_id, int room_number, String date, ArrayList<String> time_slots);
	boolean startWeek(String user_id) throws NotBoundException, IOException, InterruptedException;
}
