/**
 * 
 */
package comp6231.a3.common.web_service;

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
	boolean createRoom(String user_id, int room_number, String date, String[] time_slots);
	boolean deleteRoom(String user_id, int room_number, String date, String[] time_slots);
	boolean startWeek(String user_id);
}
