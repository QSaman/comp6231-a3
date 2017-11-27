package comp6231.a2.campus;

import java.util.Properties;

public abstract class CampusCommunication {
	protected Campus campus;
	
	public class RemoteInfo
	{
		public int port;
		public String address;
	}
	
	public CampusCommunication()
	{
	}
	
	public void setCampus(Campus campus)
	{
		this.campus = campus;
	}
	
	public abstract RemoteInfo getRemoteInfo(String campus_name);
	public abstract String[] getAllCampusNames();
	public abstract void startServer();
}
