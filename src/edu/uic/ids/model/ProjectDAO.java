package edu.uic.ids.model;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "projectDAO")
@SessionScoped
public class ProjectDAO {
	public ArrayList<String> tableDataList = new ArrayList<>();
	// course table
	private String course_code;
	private String course_name;
	private String crn;
	private int credits;
	// staff table
	private String staff_first_name;
	private String staff_last_name;
	private String staff_role;
	private String staff_course_code;
	private String staff_email_id;
	private String staff_password;
	// Student table
	private String student_first_name;
	private String student_last_name;
	private String student_univ_id_num;
	private String student_email_id;
	private String student_password;
	private String student_course_code;
	// assessment_grade table
	private String assessment_name;
	private String grade_student_email_id;
	private String assessment_grade;
	private String professor_comments;
	// assessment_results table
	private String results_assessment_name;
	private String results_student_email_id;
	private String results_course_code;
	private String results_question_number;
	private String results_submitted_answer;
	private String results_question_score;
	// upload_files table
	private String upload_file_type;
	private String upload_file_label;
	private String upload_file_user;
	private String upload_course;

	void init() {
		getTableDataList();
	}

	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getCrn() {
		return crn;
	}

	public void setCrn(String crn) {
		this.crn = crn;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public String getStaff_first_name() {
		return staff_first_name;
	}

	public void setStaff_first_name(String staff_first_name) {
		this.staff_first_name = staff_first_name;
	}

	public String getStaff_last_name() {
		return staff_last_name;
	}

	public void setStaff_last_name(String staff_last_name) {
		this.staff_last_name = staff_last_name;
	}

	public String getStaff_role() {
		return staff_role;
	}

	public void setStaff_role(String staff_role) {
		this.staff_role = staff_role;
	}

	public String getStaff_course_code() {
		return staff_course_code;
	}

	public void setStaff_course_code(String staff_course_code) {
		this.staff_course_code = staff_course_code;
	}

	public String getStaff_email_id() {
		return staff_email_id;
	}

	public void setStaff_email_id(String staff_email_id) {
		this.staff_email_id = staff_email_id;
	}

	public String getStaff_password() {
		return staff_password;
	}

	public void setStaff_password(String staff_password) {
		this.staff_password = staff_password;
	}

	public String getStudent_first_name() {
		return student_first_name;
	}

	public void setStudent_first_name(String student_first_name) {
		this.student_first_name = student_first_name;
	}

	public String getStudent_last_name() {
		return student_last_name;
	}

	public void setStudent_last_name(String student_last_name) {
		this.student_last_name = student_last_name;
	}

	public String getStudent_univ_id_num() {
		return student_univ_id_num;
	}

	public void setStudent_univ_id_num(String student_univ_id_num) {
		this.student_univ_id_num = student_univ_id_num;
	}

	public String getStudent_email_id() {
		return student_email_id;
	}

	public void setStudent_email_id(String student_email_id) {
		this.student_email_id = student_email_id;
	}

	public String getStudent_password() {
		return student_password;
	}

	public void setStudent_password(String student_password) {
		this.student_password = student_password;
	}

	public String getStudent_course_code() {
		return student_course_code;
	}

	public void setStudent_course_code(String student_course_code) {
		this.student_course_code = student_course_code;
	}

	public String getAssessment_name() {
		return assessment_name;
	}

	public void setAssessment_name(String assessment_name) {
		this.assessment_name = assessment_name;
	}

	public String getGrade_student_email_id() {
		return grade_student_email_id;
	}

	public void setGrade_student_email_id(String grade_student_email_id) {
		this.grade_student_email_id = grade_student_email_id;
	}

	public String getAssessment_grade() {
		return assessment_grade;
	}

	public void setAssessment_grade(String assessment_grade) {
		this.assessment_grade = assessment_grade;
	}

	public String getProfessor_comments() {
		return professor_comments;
	}

	public void setProfessor_comments(String professor_comments) {
		this.professor_comments = professor_comments;
	}

	public String getResults_assessment_name() {
		return results_assessment_name;
	}

	public void setResults_assessment_name(String results_assessment_name) {
		this.results_assessment_name = results_assessment_name;
	}

	public String getResults_student_email_id() {
		return results_student_email_id;
	}

	public void setResults_student_email_id(String results_student_email_id) {
		this.results_student_email_id = results_student_email_id;
	}

	public String getResults_course_code() {
		return results_course_code;
	}

	public void setResults_course_code(String results_course_code) {
		this.results_course_code = results_course_code;
	}

	public String getResults_question_number() {
		return results_question_number;
	}

	public void setResults_question_number(String results_question_number) {
		this.results_question_number = results_question_number;
	}

	public String getResults_submitted_answer() {
		return results_submitted_answer;
	}

	public void setResults_submitted_answer(String results_submitted_answer) {
		this.results_submitted_answer = results_submitted_answer;
	}

	public String getResults_question_score() {
		return results_question_score;
	}

	public void setResults_question_score(String results_question_score) {
		this.results_question_score = results_question_score;
	}

	public String getUpload_file_type() {
		return upload_file_type;
	}

	public void setUpload_file_type(String upload_file_type) {
		this.upload_file_type = upload_file_type;
	}

	public String getUpload_file_label() {
		return upload_file_label;
	}

	public void setUpload_file_label(String upload_file_label) {
		this.upload_file_label = upload_file_label;
	}

	public String getUpload_file_user() {
		return upload_file_user;
	}

	public void setUpload_file_user(String upload_file_user) {
		this.upload_file_user = upload_file_user;
	}

	public String getUpload_course() {
		return upload_course;
	}

	public void setUpload_course(String upload_course) {
		this.upload_course = upload_course;
	}

	public ArrayList<String> getTableDataList() {
		return tableDataList;
	}

	public void setTableDataList(ArrayList<String> tableDataList) {
		this.tableDataList = tableDataList;
	}

}
