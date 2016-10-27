package edu.uic.ids.model;

import java.util.ArrayList;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Assessment {
	private int assessmentNumber;
	private int questionNumber;
	private String assessmentName;
	private String questionType;
	private String actualQuestion;
	private String answer;
	private String tolerance;
	private long createdAt;
	private long updatedAt;
	private String courseID;
	public ArrayList<Assessment> assessmentTable = new ArrayList<>();
	private String upload_file_label;
	private String upload_course_name;
	private String submittedAnswers;
	private int score;

	public String getUpload_course_name() {
		return upload_course_name;
	}

	public void setUpload_course_name(String upload_course_name) {
		this.upload_course_name = upload_course_name;
	}

	public String getUpload_file_label() {
		return upload_file_label;
	}

	public void setUpload_file_label(String upload_file_label) {
		this.upload_file_label = upload_file_label;
	}

	public ArrayList<Assessment> getAssessmentTable() {
		return assessmentTable;
	}

	public void setAssessmentTable(ArrayList<Assessment> assessmentTable) {
		this.assessmentTable = assessmentTable;
	}

	public int getAssessmentNumber() {
		return assessmentNumber;
	}

	public void setAssessmentNumber(int assessmentNumber) {
		this.assessmentNumber = assessmentNumber;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getActualQuestion() {
		return actualQuestion;
	}

	public void setActualQuestion(String actualQuestion) {
		this.actualQuestion = actualQuestion;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getTolerance() {
		return tolerance;
	}

	public void setTolerance(String tolerance) {
		this.tolerance = tolerance;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long l) {
		this.createdAt = l;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(long l) {
		this.updatedAt = l;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getAssessmentName() {
		return assessmentName = "f15g109_" + assessmentName;
	}

	public void setAssessmentName(String assessmentName) {
		this.assessmentName = assessmentName;
	}

	public String getSubmittedAnswers() {
		return submittedAnswers;
	}

	public void setSubmittedAnswers(String submittedAnswers) {
		this.submittedAnswers = submittedAnswers;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}