/**
 * 
 */
package comp6231.a3.common;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import comp6231.a3.common.users.CampusUser;

/**
 * @author saman
 *
 */
public class LoggerHelper {
	
	private static final String student_client_path = "log/users/students";
	private static final String admin_client_path = "log/users/admins";
	private static final String campus_server_path = "log/campus_servers";
	public static LogManager log_manger = LogManager.getLogManager();
	
	public static String now()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.S");		
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
	
	public static String format(String log_msg)
	{
		String str = String.format("%s: %s", now(), log_msg);
		return str;
	}
	
	private static Logger createLogger(String name, String file_str) throws SecurityException, IOException
	{
		Logger logger = Logger.getLogger(name);
		//logger.setLevel(Level.OFF);
		FileHandler fh = new FileHandler(file_str, true);
		fh.setFormatter(new SimpleFormatter());
		logger.addHandler(fh);
		log_manger.addLogger(logger);
		return logger;
	}
	
	public static Logger getCampusServerLogger(String campus_name) throws SecurityException, IOException
	{
		Logger logger = log_manger.getLogger(campus_name);
		if (logger != null)
			return logger;
		String file_str = new String();
		file_str = campus_server_path + "/" + campus_name + ".log";
		return createLogger(campus_name, file_str);
	}
	
	public static Logger getCampusUserLogger(CampusUser user) throws SecurityException, IOException
	{
		Logger logger = log_manger.getLogger(user.getUserId());
		if (logger != null)
			return logger;
		
		String file_str = new String();
		if (user.isStudent())
			file_str = student_client_path + "/" + user.getUserId() + ".log";
		else if (user.isAdmin())
			file_str = admin_client_path + "/" + user.getUserId() + ".log";
		else
			throw new IllegalArgumentException("Unkown user type: " + user.getUserType().toString());

		return createLogger(user.getUserId(), file_str);
	}
}
