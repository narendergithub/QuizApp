package edu.uic.ids.model;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Student {
	private int student_id;
	private String first_name;
	private String last_name;
	private String univ_id_num;
	private String email_id;
	private String password;
	private String course_code;
	private long last_login;
	private String RosterName;
	public ArrayList<Student> rosterFileLabel;

	public static void init() {
	}

	public ArrayList<Student> getRosterFileLabel() {
		return rosterFileLabel;
	}

	public void setRosterFileLabel(ArrayList<Student> rosterFileLabel) {
		this.rosterFileLabel = rosterFileLabel;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getUniv_id_num() {
		return univ_id_num;
	}

	public void setUniv_id_num(String univ_id_num) {
		this.univ_id_num = univ_id_num;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getLast_login() {
		return last_login;
	}

	public void setLast_login(long l) {
		this.last_login = l;
	}

	public String getRosterName() {
		return RosterName = "f15g109_" + RosterName;
	}

	public void setRosterName(String rosterName) {
		RosterName = rosterName;
	}

	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}
}
