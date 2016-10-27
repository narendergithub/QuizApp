package edu.uic.ids.model;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "grades")
@SessionScoped
public class Grades {
	private String assessmentName;
	private String student_email_id;
	private int assessment_grade;
	private String professor_comments;
	private ArrayList<String> professor_comments_list = new ArrayList<>();

	public void init() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("professor_comments_list",
				professor_comments_list);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("professor_comments",
				professor_comments);
	}

	public String viewGradedAssessments() {
		String status = "GRADEDASSESSMENTS";
		return status;
	}

	public ArrayList<String> getProfessor_comments_list() {
		return professor_comments_list;
	}

	public void setProfessor_comments_list(ArrayList<String> professor_comments_list) {
		this.professor_comments_list = professor_comments_list;
	}

	public String getAssessmentName() {
		return assessmentName;
	}

	public void setAssessmentName(String assessmentName) {
		this.assessmentName = assessmentName;
	}

	public String getStudent_email_id() {
		return student_email_id;
	}

	public void setStudent_email_id(String student_email_id) {
		this.student_email_id = student_email_id;
	}

	public int getAssessment_grade() {
		return assessment_grade;
	}

	public void setAssessment_grade(int assessment_grade) {
		this.assessment_grade = assessment_grade;
	}

	public String getProfessor_comments() {
		return professor_comments;
	}

	public void setProfessor_comments(String professor_comments) {
		this.professor_comments = professor_comments;
		// adding the professor comments from each row into an array list
		professor_comments_list.add(professor_comments);
	}

}
