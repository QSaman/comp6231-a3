/**
 * 
 */
package comp6231.a2.common.users;

import java.util.ArrayList;

import comp6231.a2.common.DateReservation;
import comp6231.a2.common.TimeSlot;

/**
 * @author saman
 *
 * It represents both students and admins
 */
public class CampusUser {
	public enum UserType
	{
		Student,
		Admin
	}
	
	String user_id;
	UserType user_type;
	String campus;	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CampusUser))
			return false;
		CampusUser other = (CampusUser) obj;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}
	
	public boolean isStudent()
	{
		return user_type == UserType.Student;
	}
	
	public boolean isAdmin()
	{
		return user_type == UserType.Admin;
	}

	public CampusUser(String user_id) {
		this.user_id = user_id;
		parseUserId();		
	}
	
	private void parseUserId()
	{
		int index;
		for (index = 0; index < user_id.length() && !Character.isDigit(user_id.charAt(index)); ++index);
		if (index == user_id.length() || index == 0)
			throw new IllegalArgumentException(user_id + " is not valid");
		if (user_id.charAt(index - 1) == 'S' || user_id.charAt(index - 1) == 's')
			user_type = UserType.Student;
		else if (user_id.charAt(index - 1) == 'A' || user_id.charAt(index - 1) == 'a')
			user_type = UserType.Admin;
		else
			throw new IllegalArgumentException(user_id + " is not valid. I cannot determine it's student or admin");
		campus = user_id.substring(0, index - 1);
	}
	
	public String getUserId()
	{
		return user_id;
	}
	
	public UserType getUserType()
	{
		return user_type;
	}
	
	public String getCampus()
	{
		return campus;
	}

}
