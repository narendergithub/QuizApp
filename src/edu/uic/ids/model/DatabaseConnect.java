package edu.uic.ids.model;

import java.sql.Connection;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import edu.uic.ids.controller.Database;

@ManagedBean(name = "databaseConnect")
@SessionScoped
public class DatabaseConnect {
	// Initialize database parameters
	private static final String MYSQL_DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB2_DATABASE_DRIVER = "com.ibm.db2.jcc.DB2Driver";
	private static final String ORACLE_DATABASE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String HOST_NAME_54 = "131.193.209.54";
	private static final String HOST_NAME_LOCALHOST = "localhost";
	private static final String HOST_NAME_57 = "131.193.209.57";

	// get the parameters from the user to connect to the database
	private String userName;
	private String password;
	private String host;
	private String rDbms;
	private String schema;

	public String fetchDBdata() {
		Connection conn = null;
		String status = "CONNECTIONSUCCESS";
		conn = connect();
		if (conn == null) {
			status = "CONNECTIONFAILURE";
		} else {
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Connection established successfully", null);
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("Connection successful", facesMessage);
		}
		// System.out.println("status " + status);
		return status;
	}

	public Connection connect() {
		String driverName = "";
		String url = "";
		if (host.equalsIgnoreCase("server54")) {
			host = HOST_NAME_54;
		} else if (host.equalsIgnoreCase("server57")) {
			host = HOST_NAME_57;
		} else if (host.equalsIgnoreCase("localhost")) {
			host = HOST_NAME_LOCALHOST;
		}
		Connection conn = null;
		if (conn == null) {
			try {
				if (rDbms.equalsIgnoreCase("MySQL")) {
					driverName = MYSQL_DATABASE_DRIVER;
					url = "jdbc:mysql://" + host + ":3306/" + schema;
				} else if (rDbms.equalsIgnoreCase("DB2")) {
					driverName = DB2_DATABASE_DRIVER;
					url = "jdbc:db2://" + host + ":50000/" + schema;
				} else if (rDbms.equalsIgnoreCase("Oracle")) {
					driverName = ORACLE_DATABASE_DRIVER;
					url = "jdbc:oracle:thin:@" + host + ":1521:" + schema;
				}
				Class.forName(driverName);
				Database.setUsername(userName);
				Database.setPassword(password);
				Database.setUrl(url);
				conn = Database.connectDB();
				// System.out.println("Database Connected.Connection : " +
				// conn);
				// System.out.println("host name : " + host);
			} catch (ClassNotFoundException e) {
				// System.out.println("\nUnable to connect to the
				// database.\nConnection : " + conn);
				// e.printStackTrace();
			}
		}
		return conn;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getrDbms() {
		return rDbms;
	}

	public void setrDbms(String rDbms) {
		this.rDbms = rDbms;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

}