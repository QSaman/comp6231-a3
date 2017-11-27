/**
 * 
 */
package comp6231.a3.campus.communication.corba;


import java.util.ArrayList;
import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import comp6231.a3.campus.Bootstrap;
import comp6231.a3.campus.CampusCommunication;
import comp6231.a3.common.corba.campus.CampusOperations;
import comp6231.a3.common.corba.campus.CampusOperationsHelper;
import comp6231.a3.common.corba.users.AdminOperations;
import comp6231.a3.common.corba.users.AdminOperationsHelper;
import comp6231.a3.common.corba.users.StudentOperations;
import comp6231.a3.common.corba.users.StudentOperationsHelper;

/**
 * @author saman
 *
 */
public class CorbaCampusCommunication extends CampusCommunication {
	
	static ORB orb = null;

	/* (non-Javadoc)
	 * @see comp6231.a2.campus.CampusCommunication#getRemoteInfo(java.lang.String)
	 */
	@Override
	public RemoteInfo getRemoteInfo(String campus_name) {
		RemoteInfo ret = new RemoteInfo();
        org.omg.CORBA.Object objRef;
		try {
			objRef = orb.resolve_initial_references("NameService");
		} catch (InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
		org.omg.CORBA.Object intercampus_ref = null;
		try {
			intercampus_ref = ncRef.resolve_str(campus_name + "/intercampus");
		} catch (NotFound | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CampusOperations campus_ops = CampusOperationsHelper.narrow(intercampus_ref);
		ret.address = campus_ops.getAddress();
		ret.port = campus_ops.getPort();
		return ret;
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.campus.CampusCommunication#getAllCampusNames()
	 */
	@Override
	public String[] getAllCampusNames() {
        // get the root naming context
        org.omg.CORBA.Object objRef;
		try {
			objRef = orb.resolve_initial_references("NameService");
		} catch (InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        // Use NamingContextExt which is part of the Interoperable
        // Naming Service (INS) specification.
        
        BindingListHolder bList = new BindingListHolder() ;
        BindingIteratorHolder bIterator = new BindingIteratorHolder();
        
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);	
        ncRef.list(100, bList, bIterator);
        ArrayList<String> res = new ArrayList<String>();
        for (int i=0; i<bList.value.length; i++) {
            res.add(bList.value[i].binding_name[0].id);
        }
        String[] ret = new String[res.size()];
        return res.toArray(ret);
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.campus.CampusCommunication#startServer()
	 */
	@Override
	public void startServer() {
        // create and initialize the ORB
		if (orb == null)
		{
			String[] args = {"-ORBInitialPort", Bootstrap.corba_port, "-ORBInitialHost", "localhost"};
			orb = ORB.init(args, null);
		}
        
        // get reference to rootpoa and activate the POAManager
        POA rootpoa;
		try {
			rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			AdminCampusServant admin_servant = new AdminCampusServant(campus);
			StudentCampusServant student_servant = new StudentCampusServant(campus);
			InterCampusServant intercampus_servant = new InterCampusServant(campus);
			
	        // get object reference from the servant
	        org.omg.CORBA.Object admin_ref = rootpoa.servant_to_reference(admin_servant);
	        AdminOperations admin = AdminOperationsHelper.narrow(admin_ref);
	        
	        org.omg.CORBA.Object student_ref = rootpoa.servant_to_reference(student_servant);
	        StudentOperations student = StudentOperationsHelper.narrow(student_ref);
	        
	        org.omg.CORBA.Object intercampus_ref = rootpoa.servant_to_reference(intercampus_servant);
	        CampusOperations intercampus = CampusOperationsHelper.narrow(intercampus_ref);
	        
	        // get the root naming context
	        org.omg.CORBA.Object objRef =
		            orb.resolve_initial_references("NameService");
	        // Use NamingContextExt which is part of the Interoperable
	        // Naming Service (INS) specification.
	        
	        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);	
//	        NameComponent[] campus_path = ;
//	        
	        NamingContext campus_nc_ref = null;
	        try
	        {
	        	campus_nc_ref = (NamingContext) ncRef.resolve_str(campus.getName());
	        }
	        catch(NotFound e)
	        {
	        	try {
					campus_nc_ref = ncRef.bind_new_context(ncRef.to_name(campus.getName()));
				} catch (AlreadyBound e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }
//        	campus_nc_ref = ncRef.bind_new_context(campus_path);
	        
	        // bind the Object Reference in Naming	        
        	NameComponent path[] = ncRef.to_name("admin");
        	campus_nc_ref.rebind(path, admin);
        	
	        path = ncRef.to_name("student");
	        campus_nc_ref.rebind(path, student);	       
	        
	        path = ncRef.to_name("intercampus");
	        campus_nc_ref.rebind(path, intercampus);	        	                       	        
	        	        
		} catch (InvalidName | AdapterInactive | ServantNotActive | WrongPolicy | 
				org.omg.CosNaming.NamingContextPackage.InvalidName | CannotProceed | NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
	}	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static void run() {
		orb.run();		
	}

}
