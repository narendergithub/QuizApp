package edu.uic.ids.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.apache.commons.io.FilenameUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import com.mysql.jdbc.Statement;

import edu.uic.ids.model.Assessment;
import edu.uic.ids.model.Course;
import edu.uic.ids.model.DatabaseConnect;
import edu.uic.ids.model.Grades;
import edu.uic.ids.model.LoginBean;
import edu.uic.ids.model.Professor;
import edu.uic.ids.model.StatisticsBean;
import edu.uic.ids.model.Student;

@ManagedBean
@SessionScoped
public class ActionBeanFile<HttpServletResponse> {

	private UploadedFile uploadedFile;
	private Professor prof;
	private DatabaseConnect dbconnect;
	private Grades grades;
	// private Student stud;
	private String fileExtension;
	private String fileLabel;
	private String uploadedFileContents;
	private String fileName;
	private long fileSize;
	private String disabled = "true";
	private String fileContentType;
	private String fileLabelSelected;
	private String FileLabelSelectedroster;
	private boolean renderassessment;
	private boolean renderroster;
	private boolean renderGradedQuiz;
	private String submittedAnswers;
	private ArrayList<String> answerList;
	private ArrayList<Course> courseList;
	private LoginBean login;
	private ArrayList<String> marksList;
	private ArrayList<Assessment> assessmentList;
	private ArrayList<Assessment> asmtDloadList;
	private ArrayList<Student> studentList;
	private ArrayList<String> fileLabelList;
	private ArrayList<String> fileLabelListRoster;
	private ArrayList<String> quizList;
	private ArrayList<Grades> viewGradesList;
	private boolean renderProfCommentsOut;
	private boolean renderProfCommentsInp;
	private boolean renderUploadTypeQuiz;
	private boolean renderUploadTypeRoster;
	private String quizToDload;
	private String selectedQuiz;
	private String takeQuizDisabled;
	private boolean renderTakeQuiz;
	private String disableAddCommentBtn;
	private String disableUpdateCommentBtn;
	private boolean renderStats;
	private StatisticsBean stats;

	// private Assessment asmt;

	public ActionBeanFile() {
		assessmentList = new ArrayList<>();
		studentList = new ArrayList<>();
		fileLabelList = new ArrayList<>();
		disableAddCommentBtn = "true";
		disableUpdateCommentBtn = "true";
		renderassessment = false;
		renderroster = false;
		renderGradedQuiz = false;
		renderProfCommentsOut = false;
		renderProfCommentsInp = false;
		renderUploadTypeQuiz = false;
		renderUploadTypeRoster = false;
		courseList = new ArrayList<>();
		fileLabelListRoster = new ArrayList<>();
		quizList = new ArrayList<>();
		answerList = new ArrayList<>();
		marksList = new ArrayList<>();
		viewGradesList = new ArrayList<>();
		asmtDloadList = new ArrayList<>();
		renderStats = false;
	}

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, Object> m = context.getExternalContext().getSessionMap();
		prof = (Professor) m.get("professor");
		dbconnect = (DatabaseConnect) m.get("databaseConnect");
		login = (LoginBean) m.get("loginBean");
		grades = (Grades) m.get("grades");
		stats = (StatisticsBean) m.get("statisticsBean");
	}

	public String back() {
		String status = "";
		try {
			Connection conn = null;
			conn = Database.connectDB();
			// System.out.println("back is invoked");
			assessmentList.clear();
			answerList.clear();
			fileLabelList.clear();
			fileLabelListRoster.clear();
			marksList.clear();
			quizList.clear();
			studentList.clear();
			asmtDloadList.clear();
			courseList.clear();
			viewGradesList.clear();
			disableAddCommentBtn = "true";
			disabled = "true";
			disableUpdateCommentBtn = "true";
			takeQuizDisabled = "true";
			status = Database.fetchRole(login.getUsername(), conn);
			// System.out.println(status);

		} catch (Exception ex) {
			// ex.printStackTrace();
		}
		return status;
	}

	public String registration() {
		String status = "REGSUCCESS";
		Connection conn = null;
		try {
			conn = Database.connectDB();
			// System.out.println(prof.getRole() + prof.getCourse_code());
			// System.out.println(prof.getPassword() + " " +
			// prof.getRetype_password());
			if (!prof.getEmail_id().isEmpty()) {
				if (prof.getEmail_id().contains("@uic.edu")) {
					if (prof.getPassword().equals(prof.getRetype_password())) {
						status = Database.registerStaff(prof, conn);
						if (status.equalsIgnoreCase("REGISTERFAILURE")) {
							status = "REGFAILURE";
							FacesMessage msg1 = new FacesMessage(
									"Failed to Register !." + "" + "Try using different credentials");
							FacesContext.getCurrentInstance().addMessage(null, msg1);
						} else {
							FacesMessage msg = new FacesMessage("Registration successful");
							FacesContext.getCurrentInstance().addMessage(null, msg);
							status = "REGSUCCESS";
						}
					} else {
						FacesMessage msg = new FacesMessage("password does not match !");
						FacesContext.getCurrentInstance().addMessage(null, msg);
						status = "REGFAILURE";
					}
				} else {
					FacesMessage msg = new FacesMessage("Email id must contain '@uic.edu'");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					status = "REGFAILURE";
				}
			} else {
				FacesMessage msg = new FacesMessage(
						"Email ID already exists in the system.Please try re-login or use a different email ID");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				status = "REGFAILURE";
			}
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error while validating the registration details !");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			status = "REGFAILURE";
			// e.printStackTrace();
		}
		return status;

	}

	public String viewStudentAssessment() {
		renderTakeQuiz = false;
		if (getRenderassessment()) {
			renderassessment = false;
			assessmentList.clear();
		}
		return "ViewStudentAssessmentSuccess";
	}

	public String viewGrades() {
		String status = "VIEWGRADESASSTUDENT";
		renderassessment = false;
		renderGradedQuiz = false;
		return status;
	}

	public String initiateFileLabelsAssessment() {
		try {
			Connection conn = Database.connectDB();
			if (getRenderassessment()) {
				assessmentList.clear();
			}
			if (assessmentList.isEmpty()) {
				renderassessment = false;
			}
			// // System.out.println(renderassessment);
			Database.viewUploadFileLabels(fileLabelList, "Assessment",
					Database.fetchCourseCodeStaff(login.getUsername(), conn), login.getUsername(), conn);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return "VIEWCOURSEASSESSMENT";
	}

	public String initiateFileLabelsRoster() {
		try {
			Connection conn = Database.connectDB();
			if (getRenderroster()) {
				studentList.clear();
			}
			if (studentList.isEmpty()) {
				renderroster = false;
			}
			Database.viewUploadFileLabels(fileLabelListRoster, "Roster",
					Database.fetchCourseCodeStaff(login.getUsername(), conn), login.getUsername(), conn);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return "VIEWROSTER";
	}

	public String viewQuizToDownload() {
		String status = "VIEWQUIZTODLOADSUCCESS";
		takeQuizDisabled = "false";
		try {
			Connection conn = Database.connectDB();
			if (renderassessment == true) {
				quizList.clear();
			}
			renderTakeQuiz = true;
			renderassessment = true;
			status = Database.viewStudentQuizForDload(login.getUsername(), quizList,
					Database.fetchCourseCodeStaff(login.getUsername(), conn), "Assessment", conn);
			if (quizList.isEmpty()) {
				takeQuizDisabled = "true";
			}
		} catch (Exception e) {
			status = "VIEWQUIZTODLOADFAILURE";
			// e.printStackTrace();
		}
		return status;
	}

	public String viewQuiz() {
		String status = "VIEWQUIZ";
		takeQuizDisabled = "false";
		try {
			// System.out.println("View quiz method is trigerred");
			Connection conn = Database.connectDB();
			if (renderassessment == true) {
				quizList.clear();
			}
			renderTakeQuiz = true;
			renderassessment = true;
			status = Database.viewStudentQuiz(login.getUsername(), quizList,
					Database.fetchCourseCodeStudent(login.getUsername(), conn), "Assessment", conn);
			if (quizList.isEmpty()) {
				takeQuizDisabled = "true";
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return status;
	}

	public String viewQuizForStats() {
		String status = "VIEWQUIZSTATSSUCCESS";
		try {
			stats.setRenderStats(true);
			stats.setRenderBarChart(false);
			stats.setRenderPieChart(false);
			takeQuizDisabled = "false";
			// System.out.println("View quiz method is trigerred");
			Connection conn = Database.connectDB();
			if (renderassessment) {
				quizList.clear();
			}
			renderTakeQuiz = true;
			renderassessment = true;
			status = Database.fetchGrades(viewGradesList, login.getUsername(), conn);
			// status = Database.viewStudentQuiz(login.getUsername(), quizList,
			// Database.fetchCourseCodeStaff(login.getUsername(), conn),
			// "Assessment", conn);
			if (quizList.isEmpty()) {
				takeQuizDisabled = "true";
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return status;
	}

	public String takeQuiz() {
		// System.out.println("take quiz is invoked");
		String status = "TAKEQUIZSUCCESS";
		Connection conn = Database.connectDB();
		if (!assessmentList.isEmpty()) {
			assessmentList.clear();
		}
		if (!answerList.isEmpty()) {
			answerList.clear();
		}
		try {
			Database.selectAssessment(assessmentList, getSelectedQuiz(),
					Database.fetchCourseCodeStudent(login.getUsername(), conn), conn);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return status;
	}

	public String fetchQuiz(String quizName) {
		// System.out.println("fetch quiz is invoked");
		if (!assessmentList.isEmpty()) {
			assessmentList.clear();
		}
		renderassessment = true;
		String status = "FETCHQUIZSUCCESS";
		Connection conn = Database.connectDB();
		// System.out.println("table name " + quizName);
		try {
			Database.selectCompletedAssessment(assessmentList, quizName,
					Database.fetchCourseCodeStudent(login.getUsername(), conn), login.getUsername(), conn);

		} catch (Exception e) {
			// e.printStackTrace();
		}
		return status;
	}

	public String evaluateQuiz() {
		String status = "EVALQUIZSUCCESS";
		// System.out.println("Evaluate quiz invoked");
		int score = 0;
		try {
			Connection conn = Database.connectDB();
			score = Database.evaluateQuiz(assessmentList, answerList, marksList, getSelectedQuiz(), conn);
			// System.out.println(status + " " + score);
			if (renderassessment) {
				disabled = "false";
				renderassessment = false;
				FacesMessage msg = new FacesMessage("You have completed the quiz. Your score is :" + score
						+ ".  Press back button to return to student's home page.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				// System.out.println("mark list " + marksList);
				status = Database.gradeQuiz(getSelectedQuiz(), assessmentList, answerList, marksList,
						login.getUsername(), conn);
			}
			if (!status.equals("QUIZGRADEFAILURE")) {
				Database.insertAssessmentGrades(getSelectedQuiz(), login.getUsername(), score, conn);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}

		// System.out.println("score :" + score);
		return status;
	}

	public String viewGradedQuiz() {
		String status = "VIEWGRADEDQUIZSUCCESS";
		renderassessment = false;
		try {
			disableUpdateCommentBtn = "true";
			renderProfCommentsOut = true;
			renderProfCommentsInp = false;
			if (!viewGradesList.isEmpty()) {
				viewGradesList.clear();
				disableAddCommentBtn = "true";
				disableUpdateCommentBtn = "true";
				renderGradedQuiz = false;
			} else {
				renderGradedQuiz = true;
				disableAddCommentBtn = "false";
			}
			Connection conn = Database.connectDB();
			Statement stmt = (Statement) conn.createStatement();
			String roleStatus = "empty";
			roleStatus = login.fetchRole(roleStatus, stmt);
			// System.out.println("role " + roleStatus);
			if (roleStatus.equals("PROFESSOR") || roleStatus.equals("TA") || roleStatus.equals("TUTOR")) {
				Database.fetchGrades(viewGradesList, login.getUsername(), conn);
			}
			if (viewGradesList.isEmpty()) {
				FacesMessage msg = new FacesMessage("No graded assessments available");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error retrieving graded assessments");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// e.printStackTrace();
		}

		return status;
	}

	public String viewGradedQuizStudent() {
		String status = "VIEWGRADEDQUIZSTUDENTSUCCESS";
		renderassessment = false;
		try {
			if (disableAddCommentBtn.equals("true")) {
				disableAddCommentBtn = "false";
			}
			disableUpdateCommentBtn = "true";
			renderProfCommentsOut = true;
			renderProfCommentsInp = false;
			// System.out.println(renderGradedQuiz);
			if (!viewGradesList.isEmpty()) {
				viewGradesList.clear();
			} else {
				renderGradedQuiz = true;
			}
			Connection conn = Database.connectDB();
			Database.fetchGradesForStudent(login.getUsername(), viewGradesList, conn);
			if (viewGradesList.isEmpty()) {
				FacesMessage msg = new FacesMessage("No graded assessments available for student");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				renderGradedQuiz = true;
			}

		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error retrieving graded assessments for student");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// e.printStackTrace();
		}
		return status;
	}

	public String updateComments() {
		String status = "UPDATECOMMENTSSUCCESS";
		try {
			disableUpdateCommentBtn = "true";
			Connection conn = null;
			conn = Database.connectDB();
			Database.fetchRole(login.getUsername(), conn);
			status = Database.insertProfessorComments(viewGradesList, grades, conn);
			if (!status.equals("INSERTCOMMENTSFAILURE")) {
				FacesMessage msg = new FacesMessage("Comments have been added successfully !");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				viewGradesList.clear();
				renderProfCommentsInp = false;
				renderProfCommentsOut = false;
			}
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error in updating professor comments");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// e.printStackTrace();
		}
		return status;
	}

	public String addComments() {
		String status = "ADDCOMMENTSSUCCESS";
		try {
			disableAddCommentBtn = "true";
			disableUpdateCommentBtn = "false";
			if (isRenderProfCommentsOut()) {
				renderProfCommentsOut = false;
				renderProfCommentsInp = true;
			} else {
				renderProfCommentsInp = true;
			}
			// if (!grades.getProfessor_comments().isEmpty()) {
			// grades.setProfessor_comments("");
			// }
			// viewGradedQuiz();
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error in adding professor comments");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// e.printStackTrace();
			status = "ADDCOMMENTSFAIL";
		}
		return status;
	}

	public String courseQuery() {
		String status = "STUDENTCOURSESUCCESS";
		try {
			Connection conn = Database.connectDB();
			if (!courseList.isEmpty()) {
				courseList.clear();
			}
			status = Database.getCourseList(courseList, login.getUsername(), conn);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return status;
	}

	public String refreshCourseList() {
		String status = "COURSEREFRESHSUCCESS";
		courseList.clear();
		// System.out.println(status);
		// System.out.println(courseList.size());
		return status;
	}

	public String assessmentQuery() {
		String status = "ASSESSMENTQUERYSUCCESS";
		try {
			if (getRenderassessment()) {
				assessmentList.clear();
			}
			Connection conn = Database.connectDB();
			status = Database.selectAssessment(assessmentList, getFileLabelSelected(),
					Database.fetchCourseCodeStaff(login.getUsername(), conn), conn);
			if (assessmentList.isEmpty()) {
				renderassessment = false;
			} else {
				renderassessment = true;
			}

		} catch (Exception e) {
			status = "ASSESSMENTQUERYFAILURE";
			// e.printStackTrace();
		}
		return status;
	}

	public String rosterQuery() {
		Connection conn = Database.connectDB();
		if (renderroster = true) {
			studentList.clear();
		}
		renderroster = true;
		String courseCode = Database.fetchCourseCodeStaff(login.getUsername(), conn);
		String status = Database.selectRoster(studentList, "f15g109_student", courseCode, conn);
		return status;
	}

	public String uploadCourseAssessment() {
		String status = "UPLOADCOURSEASSESSMENT";
		renderUploadTypeQuiz = true;
		renderUploadTypeRoster = false;
		return status;
	}

	public String uploadRoster() {
		String status = "ROSTERUPLOADED";
		renderUploadTypeRoster = true;
		renderUploadTypeQuiz = false;

		return status;
	}

	public String importRoster() {
		String status = "IMPORTROSTERSUCCESS";
		csv_databaseImport("Roster");
		return status;
	}

	public String importAssessment() {
		String status = "IMPORTQUIZSUCCESS";
		csv_databaseImport("Assessment");
		return status;
	}

	public String csv_databaseImport(String uploadType) {
		String status = "CSVIMPORTSUCCESS";
		String fileImportName = processFileUpload();
		// System.out.println("FileOutput : " + fileImportName);
		BufferedReader br = null;
		try {
			if (fileImportName != null) {
				fileLabel = "f15g109_" + getFileLabel();
				Connection conn = Database.connectDB();
				if (Database.validateTableName(getFileLabel(), conn)) {
					try {
						status = Database.viewStafftable("f15g109_staff", conn);
						if (uploadType.equalsIgnoreCase("Assessment")) {
							status = Database.createAssessment(getFileLabel(), conn);
						}
						String line = "";
						// System.out.println(getFileLabel());
						br = new BufferedReader(new FileReader(fileImportName));
						line = br.readLine();
						while ((line = br.readLine()) != null) {
							// System.out.println(line);
							String[] tokens = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
							tokens[0] = tokens[0].replace("\"", "");
							tokens[1] = tokens[1].replace("\"", "");
							tokens[2] = tokens[2].replace("\"", "");
							tokens[3] = tokens[3].replace("\"", "");
							if (uploadType.equalsIgnoreCase("Assessment")) {
								assessmentTokens(tokens);
							}
							if (uploadType.equalsIgnoreCase("Roster")) {
								rosterTokens(tokens);
							}
						}

						if (uploadType.equals("Assessment")) {
							String result = Database.insertAssessment(assessmentList, getFileLabel(),
									Database.fetchCourseCodeStaff(login.getUsername(), conn), conn);
							if (!result.equals("INSERTASSESSMENTFAILURE")) {
								status = Database.insertFileUpload("Assessment", getFileLabel(), login.getUsername(),
										Database.fetchCourseCodeStaff(login.getUsername(), conn), conn);
							}
						}

						if (uploadType.equals("Roster")) {
							String studentTable = "f15g109_student";
							status = Database.insertRoster(studentList, studentTable,
									Database.fetchCourseCodeStaff(login.getUsername(), conn), conn);
						}
					} catch (NullPointerException np) {
						FacesMessage msg = new FacesMessage("Error in uploading the file ");
						FacesContext.getCurrentInstance().addMessage(null, msg);
						np.printStackTrace();
					}
					if (status.contains("SUCCESS")) {
						FacesMessage msg = new FacesMessage("File uploaded successfully");
						FacesContext.getCurrentInstance().addMessage(null, msg);
						status = "CSVIMPORTSUCCESS";
						assessmentList.clear();
						studentList.clear();
					}
				} else {
					if (uploadType.equals("Assessment")) {
						FacesMessage msg = new FacesMessage(
								"A file with the same name already exists. Please enter a different name");
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}
					status = "CSVIMPORTFAILURE";
				}
			} else {
				status = "CSVIMPORTFAILURE";
			}
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error in uploading the file ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// e.printStackTrace();
			status = "CSVIMPORTFAILURE";
		}
		fileLabel = "";
		return status;
	}

	public void assessmentTokens(String[] tokens) throws NumberFormatException {
		Assessment asmt = new Assessment();
		// asmt.setAssessmentNumber(Integer.parseInt(tokens[0]));
		asmt.setQuestionNumber(Integer.parseInt(tokens[0]));
		asmt.setActualQuestion(tokens[1]);
		asmt.setAnswer(tokens[2]);
		asmt.setTolerance(tokens[3]);
		if (!asmt.getTolerance().isEmpty()) {
			asmt.setQuestionType("Numerical");
		} else {
			asmt.setQuestionType("Categorical");
		}

		assessmentList.add(asmt);
	}

	public void rosterTokens(String[] tokens) throws NumberFormatException {
		Student stdnt = new Student();
		stdnt.setStudent_id(Integer.parseInt(tokens[0]));
		stdnt.setFirst_name(tokens[1]);
		stdnt.setLast_name(tokens[2]);
		stdnt.setUniv_id_num(tokens[3]);
		stdnt.setEmail_id(tokens[4]);
		stdnt.setPassword(tokens[5]);
		studentList.add(stdnt);
	}

	public String processFileUpload() {
		// String status = "IMPORTFAIL";
		uploadedFileContents = null;
		FacesContext context = FacesContext.getCurrentInstance();
		String path = context.getExternalContext().getRealPath("/Files");
		// System.out.println("path : " + path);
		File tempFile = null;
		FileOutputStream fos = null;
		boolean fileNameflag = true;
		try {
			try {
				fileName = uploadedFile.getName();
				fileSize = uploadedFile.getSize();
				String baseName = FilenameUtils.getBaseName(fileName) + "." + FilenameUtils.getExtension(fileName);
				fileContentType = uploadedFile.getContentType();
				uploadedFileContents = new String(uploadedFile.getBytes());
				tempFile = new File(path + "/" + baseName);
				fos = new FileOutputStream(tempFile);
				fos.write(uploadedFile.getBytes());
				fos.close();
			} catch (Exception e) {
				FacesMessage msg = new FacesMessage("Error in uploading the file!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				// e.printStackTrace();
			}
		} catch (NullPointerException np) {
			FacesMessage msg = new FacesMessage("Error in uploading the file!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// np.printStackTrace();
			fileNameflag = false;
		}
		String temp = null;
		if (fileNameflag) {
			try {
				temp = tempFile.toString();
				// System.out.println("file path" + temp);
			} catch (NullPointerException npe) {
				FacesMessage msg = new FacesMessage("Select a file to upload ");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				// e.printStackTrace();
			}
		}
		// System.out.println(fileNameflag);
		return temp;
	} // end
		// processFileUpload()

	public String displayTableContents() {
		return "";
	}

	public String downloadRoster() {
		String status = "DLOADROSTERSUCCESS";
		downloadFile("Roster");
		return status;
	}

	public String downloadAssessment() {
		String status = "DLOADQUIZSUCCESS";
		downloadFile("Assessment");
		return status;
	}

	public String downloadAttemptedAssessment() {
		String status = "DLOADATTEMPTEDQUIZSUCCESS";
		downloadFile("AttemptedAssessment");
		return status;
	}

	public String downloadFile(String uploadType) {
		String status = "DLOADROSTERSUCCESS";
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			FileOutputStream fos = null;
			Connection conn = null;
			ResultSet rs = null;
			conn = Database.connectDB();
			if (uploadType.equalsIgnoreCase("Assessment") || uploadType.equalsIgnoreCase("AttemptedAssessment")) {
				fileExtension = ".csv";
			}
			String fileNameBase = "Download" + fileExtension;
			// System.out.println("file name base" + fileNameBase);
			String path = fc.getExternalContext().getRealPath("/tmp");
			String fileName = path + "/" + "UserName" + "_" + fileNameBase;
			File f = new File(fileName);
			try {

				if (uploadType.equalsIgnoreCase("Roster")) {
					if (!(fileExtension.equalsIgnoreCase(".xml") || fileExtension.equalsIgnoreCase(".csv"))) {
						FacesMessage msg = new FacesMessage("Please select either xml or csv file type to download");
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}
					rs = Database.fetchRosterDownload("f15g109_student", login.getUsername(), conn);
				} else if (uploadType.equalsIgnoreCase("Assessment")) {
					rs = Database.fetchAssessmentDownload(getFileLabelSelected(), conn);
				} else if (uploadType.equalsIgnoreCase("AttemptedAssessment")) {
					rs = Database.fetchAssessmentDownload(getQuizToDload(), conn);
				}
			} catch (Exception ex) {
				FacesMessage msg = new FacesMessage("Please select either xml or csv file type to download");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				// ex.printStackTrace();
			}
			Result result = ResultSupport.toResult(rs);
			Object[][] sData = result.getRowsByIndex();
			String columnNames[] = result.getColumnNames();
			StringBuffer sb = new StringBuffer();
			try {
				fos = new FileOutputStream(fileName);
				for (int i = 0; i < columnNames.length; i++) {
					sb.append(columnNames[i].toString() + ",");
				}
				sb.append("\n");
				fos.write(sb.toString().getBytes());
				for (int i = 0; i < sData.length; i++) {
					sb = new StringBuffer();
					for (int j = 0; j < sData[0].length; j++) {
						sb.append("\"" + sData[i][j].toString() + "\"" + ",");
					}
					sb.append("\n");
					fos.write(sb.toString().getBytes());
				}
				fos.flush();
				fos.close();
			} catch (FileNotFoundException ex) {
				// ex.printStackTrace();
			} catch (IOException excep) {
				// excep.printStackTrace();
			}
			String mimeType = ec.getMimeType(fileName);
			FileInputStream in = null;
			byte b;
			ec.responseReset();
			ec.setResponseContentType(mimeType);
			ec.setResponseContentLength((int) f.length());
			String selectedFileName = null;
			if (uploadType.equals("Assessment")) {
				selectedFileName = getFileLabelSelected() + fileExtension;
			} else if (uploadType.equals("Roster")) {
				selectedFileName = "StudentRoster" + fileExtension;
			} else if (uploadType.equals("AttemptedAssessment")) {
				selectedFileName = getQuizToDload() + fileExtension;
			}
			ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + selectedFileName + "\"");
			try {
				in = new FileInputStream(f);
				OutputStream output = ec.getResponseOutputStream();
				while (true) {
					b = (byte) in.read();
					if (b < 0) {
						break;
					}
					output.write(b);
				}
			} catch (Exception ex) {
				status = "DLOADROSTERFAIL";
				FacesMessage msg = new FacesMessage("Error in downloading file");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				// ex.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (Exception ex) {
					// ex.printStackTrace();
				}
			}
			fc.responseComplete();
			if (status.equalsIgnoreCase("DLOADROSTERSUCCESS")) {
				FacesMessage msg = new FacesMessage("File downloaded successfully !");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error in File downloaded ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// e.printStackTrace();
		}
		return status;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getFileLabel() {
		return fileLabel;
	}

	public void setFileLabel(String fileLabel) {
		this.fileLabel = fileLabel;
	}

	public String getUploadedFileContents() {
		return uploadedFileContents;
	}

	public void setUploadedFileContents(String uploadedFileContents) {
		this.uploadedFileContents = uploadedFileContents;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public ArrayList<Assessment> getAssessmentList() {
		return assessmentList;
	}

	public void setAssessmentList(ArrayList<Assessment> assessmentList) {
		this.assessmentList = assessmentList;
	}

	public ArrayList<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(ArrayList<Student> studentList) {
		this.studentList = studentList;
	}

	public Professor getProf() {
		return prof;
	}

	public void setProf(Professor prof) {
		this.prof = prof;
	}

	public ArrayList<String> getFileLabelList() {
		return fileLabelList;
	}

	public void setFileLablelList(ArrayList<String> fileLablelList) {
		this.fileLabelList = fileLablelList;
	}

	public String getFileLabelSelected() {
		return fileLabelSelected;
	}

	public void setFileLabelSelected(String fileLabelSelected) {
		this.fileLabelSelected = fileLabelSelected;
	}

	public Boolean getRenderassessment() {
		return renderassessment;
	}

	public void setRenderassessment(Boolean renderassessment) {
		this.renderassessment = renderassessment;
	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}

	public LoginBean getLogin() {
		return login;
	}

	public void setLogin(LoginBean login) {
		this.login = login;
	}

	public Boolean getRenderroster() {
		return renderroster;
	}

	public void setRenderroster(Boolean renderroster) {
		this.renderroster = renderroster;
	}

	public String getFileLabelSelectedroster() {
		return FileLabelSelectedroster;
	}

	public void setFileLabelSelectedroster(String fileLabelSelectedroster) {
		FileLabelSelectedroster = fileLabelSelectedroster;
	}

	public void setFileLabelList(ArrayList<String> fileLabelList) {
		this.fileLabelList = fileLabelList;
	}

	public ArrayList<String> getFileLabelListRoster() {
		return fileLabelListRoster;
	}

	public void setFileLabelListRoster(ArrayList<String> fileLabelListRoster) {
		this.fileLabelListRoster = fileLabelListRoster;
	}

	public ArrayList<String> getQuizList() {
		return quizList;
	}

	public void setQuizList(ArrayList<String> quizList) {
		this.quizList = quizList;
	}

	public String getSubmittedAnswers() {
		return submittedAnswers;
	}

	public void setSubmittedAnswers(String submittedAnswers) {
		this.submittedAnswers = submittedAnswers;
		// System.out.println(submittedAnswers);
		answerList.add(submittedAnswers);
		// System.out.println("asnwer list " + answerList);
	}

	public ArrayList<String> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(ArrayList<String> answerList) {
		this.answerList = answerList;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public DatabaseConnect getDbconnect() {
		return dbconnect;
	}

	public void setDbconnect(DatabaseConnect dbconnect) {
		this.dbconnect = dbconnect;
	}

	public String getSelectedQuiz() {
		return selectedQuiz;
	}

	public void setSelectedQuiz(String selectedQuiz) {
		this.selectedQuiz = selectedQuiz;
	}

	public ArrayList<String> getMarksList() {
		return marksList;
	}

	public void setMarksList(ArrayList<String> marksList) {
		this.marksList = marksList;
	}

	public String getTakeQuizDisabled() {
		return takeQuizDisabled;
	}

	public void setTakeQuizDisabled(String takeQuizDisabled) {
		this.takeQuizDisabled = takeQuizDisabled;
	}

	public boolean isRenderTakeQuiz() {
		return renderTakeQuiz;
	}

	public void setRenderTakeQuiz(boolean renderTakeQuiz) {
		this.renderTakeQuiz = renderTakeQuiz;
	}

	public ArrayList<Grades> getViewGradesList() {
		return viewGradesList;
	}

	public void setViewGradesList(ArrayList<Grades> viewGradesList) {
		this.viewGradesList = viewGradesList;
	}

	public boolean isRenderGradedQuiz() {
		return renderGradedQuiz;
	}

	public void setRenderGradedQuiz(boolean renderGradedQuiz) {
		this.renderGradedQuiz = renderGradedQuiz;
	}

	public void setRenderassessment(boolean renderassessment) {
		this.renderassessment = renderassessment;
	}

	public void setRenderroster(boolean renderroster) {
		this.renderroster = renderroster;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public Grades getGrades() {
		return grades;
	}

	public void setGrades(Grades grades) {
		this.grades = grades;
	}

	public ArrayList<Assessment> getAsmtDloadList() {
		return asmtDloadList;
	}

	public void setAsmtDloadList(ArrayList<Assessment> asmtDloadList) {
		this.asmtDloadList = asmtDloadList;
	}

	public String getQuizToDload() {
		return quizToDload;
	}

	public void setQuizToDload(String quizToDload) {
		this.quizToDload = quizToDload;
	}

	public boolean isRenderProfCommentsOut() {
		return renderProfCommentsOut;
	}

	public void setRenderProfCommentsOut(boolean renderProfCommentsOut) {
		this.renderProfCommentsOut = renderProfCommentsOut;
	}

	public boolean isRenderProfCommentsInp() {
		return renderProfCommentsInp;
	}

	public void setRenderProfCommentsInp(boolean renderProfCommentsInp) {
		this.renderProfCommentsInp = renderProfCommentsInp;
	}

	public String getDisableAddCommentBtn() {
		return disableAddCommentBtn;
	}

	public void setDisableAddCommentBtn(String disableAddCommentBtn) {
		this.disableAddCommentBtn = disableAddCommentBtn;
	}

	public String getDisableUpdateCommentBtn() {
		return disableUpdateCommentBtn;
	}

	public void setDisableUpdateCommentBtn(String disableUpdateCommentBtn) {
		this.disableUpdateCommentBtn = disableUpdateCommentBtn;
	}

	public boolean isRenderUploadTypeQuiz() {
		return renderUploadTypeQuiz;
	}

	public void setRenderUploadTypeQuiz(boolean renderUploadTypeQuiz) {
		this.renderUploadTypeQuiz = renderUploadTypeQuiz;
	}

	public boolean isRenderUploadTypeRoster() {
		return renderUploadTypeRoster;
	}

	public void setRenderUploadTypeRoster(boolean renderUploadTypeRoster) {
		this.renderUploadTypeRoster = renderUploadTypeRoster;
	}

	public boolean isRenderStats() {
		return renderStats;
	}

	public void setRenderStats(boolean renderStats) {
		this.renderStats = renderStats;
	}
}