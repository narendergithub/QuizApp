package edu.uic.ids.model;

public class Course {
	private String courseName;
	private String courseCode;
	private String courseCRN;
	private String courseCredits;

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseCRN() {
		return courseCRN;
	}

	public void setCourseCRN(String courseCRN) {
		this.courseCRN = courseCRN;
	}

	public String getCourseCredits() {
		return courseCredits;
	}

	public void setCourseCredits(String courseCredits) {
		this.courseCredits = courseCredits;
	}
}