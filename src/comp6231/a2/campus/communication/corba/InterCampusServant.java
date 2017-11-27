/**
 * 
 */
package comp6231.a2.campus.communication.corba;

import comp6231.a2.campus.Campus;
import comp6231.a2.common.corba.campus.CampusOperationsPOA;

/**
 * @author saman
 *
 */
public class InterCampusServant extends CampusOperationsPOA {
	
	Campus campus;

	/**
	 * 
	 */
	public InterCampusServant(Campus campus) {
		this.campus = campus;
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.corba.campus.CampusOperationsOperations#getPort()
	 */
	@Override
	public int getPort() {
		return campus.getPort();
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.corba.campus.CampusOperationsOperations#getAddress()
	 */
	@Override
	public String getAddress() {
		return campus.getAddress();
	}

	/* (non-Javadoc)
	 * @see comp6231.a2.common.corba.campus.CampusOperationsOperations#getCampusName()
	 */
	@Override
	public String getCampusName() {
		return campus.getName();
	}

}
