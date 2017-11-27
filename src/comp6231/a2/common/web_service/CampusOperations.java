/**
 * 
 */
package comp6231.a2.common.web_service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 * @author saman
 *
 */
@WebService
@SOAPBinding(style=Style.RPC)
public interface CampusOperations {
	public int getPort();
	public String getAddress();
	String getCampusName();

}
