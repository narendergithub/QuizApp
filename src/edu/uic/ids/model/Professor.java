package edu.uic.ids.model;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "professor")
@SessionScoped
public class Professor {

	String status = null;
	String uploadType;
	String downloadType;
	String first_name;
	String last_name;
	String role;
	String course_code;
	String email_id;
	String password;
	String retype_password;
	boolean studentView = false;

	public Professor() {
	}

	@PostConstruct
	public void init() {
	}

	public String registerUser() {

		status = "USERREGISTERSUCCESS";
		return status;
	}

	public String viewAsStudent() {
		status = "VIEWSTUDENT";
		setStudentView(true);
		return status;
	}

	public String viewCourseAssessment() {
		status = "VIEWCOURSEASSESSMENT";
		return status;
	}

	public String viewRoster() {
		status = "VIEWROSTER";
		return status;
	}

	public String viewGradedAssessments() {
		status = "GRADEDASSESSMENTS";
		return status;
	}

	public String dloadCourseAssessment() {
		setDownloadType("Assessment");
		// System.out.println("down load assessment invoked");
		status = "DLOADCOURSEASSESSMENT";
		return status;
	}

	public String dloadStudRoster() {
		setDownloadType("Roster");
		// System.out.println("download roster invoked");
		status = "ROSTERDLOAD";
		return status;
	}

	public String courseStats() {
		status = "COURSESTATS";
		return status;
	}

	public String uploadQuiz() {
		status = "UPLOADQUIZ";
		return status;
	}

	public String uploadExam() {
		status = "UPLOADEXAM";
		return status;
	}

	public String genReports() {
		status = "GENREPORTS";
		return status;
	}

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public String getRetype_password() {
		return retype_password;
	}

	public void setRetype_password(String retype_password) {
		this.retype_password = retype_password;
	}

	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}

	public String getDownloadType() {
		return downloadType;
	}

	public void setDownloadType(String downloadType) {
		this.downloadType = downloadType;
	}

	public boolean isStudentView() {
		return studentView;
	}

	public void setStudentView(boolean studentView) {
		this.studentView = studentView;
	}

}
