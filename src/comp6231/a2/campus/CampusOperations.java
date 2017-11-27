/**
 * 
 */
package comp6231.a2.campus;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author saman
 *
 */
public interface CampusOperations extends Remote {
	public int getPort() throws RemoteException;
	public String getAddress() throws RemoteException;
	String getCampusName() throws RemoteException;
}
