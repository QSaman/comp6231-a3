/**
 * 
 */
package comp6231.a2.campus.communication.web_service;

import java.util.HashMap;

import javax.xml.ws.Endpoint;

import comp6231.a2.campus.CampusCommunication;

/**
 * @author saman
 *
 */
public class WebServiceCampusCommunication extends CampusCommunication {
	public String[] campus_names;
	public HashMap<String, RemoteInfo> campus_remote_info;
	private String host_str;

	/**
	 * 
	 */
	public WebServiceCampusCommunication() {
		host_str = "http://localhost:8080";
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.campus.CampusCommunication#getRemoteInfo(java.lang.String)
	 */
	@Override
	public RemoteInfo getRemoteInfo(String campus_name) {
		return campus_remote_info.get(campus_name);
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.campus.CampusCommunication#getAllCampusNames()
	 */
	@Override
	public String[] getAllCampusNames() {
		return campus_names;
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.campus.CampusCommunication#startServer()
	 */
	@Override
	public void startServer() {
		StringBuilder sb = new StringBuilder();
		sb.append(host_str).append('/').append(campus.getName()).append('/');
		Endpoint.publish(sb.toString() + "student", new StudentServer(campus));
		Endpoint.publish(sb.toString() + "admin", new AdminServer(campus));
	}

}
