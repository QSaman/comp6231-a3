package comp6231.a3.users;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import comp6231.a3.campus.Bootstrap;
import comp6231.a3.common.LoggerHelper;
import comp6231.a3.common.corba.users.AdminOperationsHelper;
import comp6231.a3.common.corba.users.StudentOperationsHelper;
import comp6231.a3.common.users.CampusUser;
import comp6231.a3.common.users.StudentOperations;
import comp6231.a3.common.web_service.AdminOperations;

public abstract class ClientUserFactory {
	static ORB orb = null;
	static org.omg.CORBA.Object objRef = null;
	static NamingContextExt ncRef;
	static 
	{
		System.setProperty("java.security.policy", "file:./src/comp6231/security.policy");
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());		
	}
	
	private static void initCorba() throws InvalidName
	{
		if (orb != null)
			return;
		Properties props = new Properties();
		props.put("org.omg.CORBA.ORBInitialPort", Bootstrap.corba_port);
		props.put("org.omg.CORBA.ORBInitialHost", "localhost");
		String[] args = {"-ORBInitialPort", Bootstrap.corba_port, "-ORBInitialHost", "localhost"};
		orb = ORB.init(args, null);
		objRef = orb.resolve_initial_references("NameService");
		ncRef = NamingContextExtHelper.narrow(objRef);
	}
	
	public static StudentClient createStudentClient(CampusUser user) throws SecurityException, IOException, NotBoundException
	{
		if (!user.isStudent())
			return null;
		Logger logger = LoggerHelper.getCampusUserLogger(user);
		StudentInterface student_interface = null;
		switch(Bootstrap.com_type)
		{
		case RMI:
			Registry registry = LocateRegistry.getRegistry();
			StudentOperations remote_stub = (StudentOperations)registry.lookup(user.getCampus());
			student_interface = new StudentRmi(remote_stub);
			break;
		case CORBA:
			try {
				initCorba();
				comp6231.a3.common.corba.users.StudentOperations corba = StudentOperationsHelper.narrow(ncRef.resolve_str(user.getCampus() + "/student"));
				student_interface = new StudentCorba(corba);
			} catch (InvalidName | NotFound | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			break;
		case WEB_SERVICE:
			String url_str = "http://localhost:8080/" + user.getCampus() + "/student?wsdl";
			URL url = new URL(url_str);
			QName qName = new QName("http://web_service.communication.campus.a3.comp6231/", "StudentServerService");
			Service service = Service.create(url, qName);
			comp6231.a3.common.web_service.StudentOperations web_service = service.getPort(comp6231.a3.common.web_service.StudentOperations.class);
			student_interface = new StudentWebService(web_service);
			break;
		default:
			return null;
		}
				
		return new StudentClient(user, logger, student_interface);
	}
	
	public static AdminClient createAdminClient(CampusUser user) throws SecurityException, IOException, NotBoundException
	{
		if (!user.isAdmin())
			return null;
		Logger logger = LoggerHelper.getCampusUserLogger(user);
		AdminInterface admin = null;
		switch(Bootstrap.com_type)
		{
		case RMI:
			Registry registry = LocateRegistry.getRegistry();
			comp6231.a3.common.users.AdminOperations rmi = (comp6231.a3.common.users.AdminOperations)registry.lookup(user.getCampus());
			admin = new AdminRmi(rmi);
			break;
		case CORBA:			
			try {
				initCorba();
				comp6231.a3.common.corba.users.AdminOperations corba = AdminOperationsHelper.narrow(ncRef.resolve_str(user.getCampus() + "/admin"));
				admin = new AdminCorba(corba);
			} catch (NotFound | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName | InvalidName e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			break;
		case WEB_SERVICE:
			String url_str = "http://localhost:8080/" + user.getCampus() + "/admin?wsdl";
			URL url = new URL(url_str);
			QName qName = new QName("http://web_service.communication.campus.a3.comp6231/", "AdminServerService");
			Service service = Service.create(url, qName);
			comp6231.a3.common.web_service.AdminOperations web_service = service.getPort(comp6231.a3.common.web_service.AdminOperations.class);
			admin = new AdminWebService(web_service);
			break;
		default:
			return null;
		}
		return new AdminClient(user, logger, admin);
	}
}
