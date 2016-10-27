package edu.uic.ids.model;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uic.ids.controller.ActionBeanFile;
import edu.uic.ids.controller.Database;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {
	private String username;
	private String password;
	private String message;
	private String loginURL = "faces/index.jsp";
	private boolean loginCheck = false;
	private String ipAddress;
	private String timestamp;
	private String userLastName;
	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	HttpSession session = (HttpSession) ec.getSession(true);
	Connection conn = null;

	public String login() {
		String sql = "";
		String status = "empty";
		ResultSet rs = null;
		try {
			conn = Database.connectDB();
			Statement stmt = conn.createStatement();
			if (username.isEmpty() && password.isEmpty()) {
				if (username.isEmpty()) {
					FacesMessage msg = new FacesMessage("Username is required ");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
				if (password.isEmpty()) {
					FacesMessage msg = new FacesMessage("Password is required ");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			} else {
				status = fetchRole(status, stmt);
				if (!(status.equals("PROFESSOR") || status.equals("TA") || status.equals("TUTOR"))) {
					sql = "SELECT univ_id_num FROM f15g109_student WHERE email_id = '" + username + "'"
							+ "AND password = '" + password + "'";
					rs = stmt.executeQuery(sql);
					rs.next();
					status = rs.getString("univ_id_num");
					if (!status.equalsIgnoreCase("empty")) {
						status = "STUDENT";
						userLastName = Database.fetchStudentLastName(username, conn);
					} else {
						status = "FAILURE";
					}
				} else {
					userLastName = Database.fetchStaffLastName(username, conn);
				}
				setLoginCheck(true);
				session.setAttribute("login", username);
				setTimestamp(Database.updateLastLogin(fetchIPAddress(), username, conn));
				return status;
			}
		} catch (SQLException ex) {
			setLoginCheck(false);
			FacesMessage msg = new FacesMessage("Username/password is incorrect");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// ex.printStackTrace();
		}
		return status;
	}

	public String fetchRole(String status, Statement stmt) throws SQLException {
		String sql;
		ResultSet rs;
		sql = "SELECT role FROM f15g109_staff WHERE email_id = '" + username + "'" + " AND password = '" + password
				+ "'";
		rs = stmt.executeQuery(sql);
		rs.next();
		try {
			status = rs.getString("role");
			// System.out.println("status " + status);
		} catch (Exception ex) {
			// ex.printStackTrace();
		}
		return status;
	}

	public String fetchIPAddress() {

		try {
			ipAddress = InetAddress.getLocalHost().getHostAddress();
			// System.out.println();
		} catch (UnknownHostException e) {
			FacesMessage msg = new FacesMessage("Error in fetching IP Address");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// e.printStackTrace();
		}
		return ipAddress;
	}

	public String logout() {
		if (setLoginCheck(true)) {
			try {
				session.setAttribute("login", null);
				setLoginCheck(false);
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ec.invalidateSession();
				FacesContext.getCurrentInstance().getExternalContext().redirect(loginURL);
				// System.out.println("Logout successful");
				// clearLists();
			} catch (IOException e) {
				FacesMessage msg = new FacesMessage("Error in logging out of the application");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				// e.printStackTrace();
				return "LOGOUTFAIL";
			}
		}
		return "LOGOUTSUCCESS";
	}

	public String dbLogout() {
		if (setLoginCheck(true)) {
			try {
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ec.invalidateSession();
				loginURL = "faces/dbLogin.jsp";
				FacesContext.getCurrentInstance().getExternalContext().redirect(loginURL);
				Database.disconnect(conn);
				// System.out.println("Logout successful");
				clearLists();
			} catch (IOException e) {
				FacesMessage msg = new FacesMessage("Error in logging out of the Database");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				// e.printStackTrace();
				return "DBLOGOUTFAIL";
			}
		}
		return "DBLOGOUTSUCCESS";
	}

	public String clearLists() {
		try {
			ActionBeanFile<HttpServletResponse> actionBean = new ActionBeanFile<>();
			actionBean.getAssessmentList().clear();
			actionBean.getCourseList().clear();
			actionBean.getFileLabelList().clear();
			actionBean.getStudentList().clear();
			actionBean.getAnswerList().clear();
			actionBean.getAsmtDloadList().clear();
			actionBean.getFileLabelListRoster().clear();
			actionBean.getMarksList().clear();
			actionBean.getQuizList().clear();
			actionBean.getViewGradesList().clear();
		} catch (Exception e) {
			// e.printStackTrace();
		}

		return "ArrayListCleared";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isLoginCheck() {
		return loginCheck;
	}

	public boolean setLoginCheck(boolean loginCheck) {
		this.loginCheck = loginCheck;
		return loginCheck;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

}