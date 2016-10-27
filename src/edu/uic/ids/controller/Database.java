package edu.uic.ids.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import edu.uic.ids.model.Assessment;
import edu.uic.ids.model.Course;
import edu.uic.ids.model.Grades;
import edu.uic.ids.model.Professor;
import edu.uic.ids.model.Student;

public class Database {

	private static String username;
	private static String password;
	private static String url;

	public static Connection connectDB() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException ex) {
			FacesMessage msg = new FacesMessage("Error in connecting to the database");
			FacesContext.getCurrentInstance().addMessage(null, msg);
//			 ex.printStackTrace();
		}
		return conn;
	}

	public static Boolean validateTableName(String tableName, Connection conn) {
		Boolean isvalid = true;
		Statement stmt = null;
		ResultSet rs = null;
		String selectStmt = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'f15g109';";
		try {
			// System.out.println(tableName);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectStmt);
			while (rs.next()) {
				if (rs.getString(1).equalsIgnoreCase(tableName)) {
					isvalid = false;
				}
			}
		} catch (Exception e) {
			isvalid = false;
			FacesMessage msg = new FacesMessage("Error while executing select statement");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// e.printStackTrace();
		}
		return isvalid;
	}

	public static void fetchTableNames(ArrayList<String> tableNameList, String schema, Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		String selectStmt = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '" + schema + "';";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectStmt);
			while (rs.next()) {
				tableNameList.add(rs.getString(1));
			}
			// System.out.println(" " + tableNameList);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error while executing select statement");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// e.printStackTrace();
		}
	}

	public static void createApplicationTables(Connection conn) {
		Statement stmt = null;
		String createCourseStmt = "CREATE TABLE IF NOT EXISTS `f15g109_course` ("
				+ " `course_code` varchar(10) NOT NULL," + " `course_name` varchar(45) DEFAULT NULL,"
				+ " `crn` varchar(10) DEFAULT NULL," + " `credits` int(2) DEFAULT NULL,"
				+ " PRIMARY KEY (`course_code`)" + " ) COMMENT='contains the details of all the courses offered';";
		String createStudentStmt = "CREATE TABLE IF NOT EXISTS `f15g109_student` ("
				+ " `first_name` varchar(45) DEFAULT NULL," + " `last_name` varchar(45) DEFAULT NULL,"
				+ " `univ_id_num` varchar(10) DEFAULT NULL," + " `email_id` varchar(45) NOT NULL,"
				+ " `password` varchar(45) NOT NULL," + "`last_login` datetime DEFAULT CURRENT_TIMESTAMP,"
				+ " `course_code` varchar(45) DEFAULT NULL," + " PRIMARY KEY (`email_id`,`course_code`),"
				+ " KEY `FK_course_idx` (`course_code`),"
				+ " CONSTRAINT `FK_course` FOREIGN KEY (`course_code`) REFERENCES `f15g109_course` (`course_code`) ON DELETE NO ACTION ON UPDATE NO ACTION"
				+ " ) COMMENT='contains the student information';";
		String createStaffStmt = "CREATE TABLE IF NOT EXISTS `f15g109_staff` ("
				+ " `first_name` varchar(45) DEFAULT NULL," + " `last_name` varchar(45) DEFAULT NULL,"
				+ " `role` varchar(45) DEFAULT NULL," + " `course_code` varchar(45) NOT NULL,"
				+ " `email_id` varchar(45) NOT NULL," + " `password` varchar(45) DEFAULT NULL,"
				+ " `last_login` datetime DEFAULT CURRENT_TIMESTAMP,"
				+ "  PRIMARY KEY (`email_id`), KEY `FK_course_staff_idx` (`course_code`),"
				+ " CONSTRAINT `FK_course_staff` FOREIGN KEY (`course_code`) REFERENCES `f15g109_course` (`course_code`) ON DELETE NO ACTION ON UPDATE NO ACTION"
				+ ") COMMENT='contains the details of the staff';";
		String createUploadStmt = "CREATE TABLE IF NOT EXISTS `f15g109_upload_files` ("
				+ " `upload_file_type` varchar(45) DEFAULT NULL,"
				+ " `upload_file_label` varchar(45) NOT NULL DEFAULT '',"
				+ " `upload_file_user` varchar(45) DEFAULT NULL," + " `upload_course` varchar(45) DEFAULT NULL,"
				+ " PRIMARY KEY (`upload_file_label`)," + " KEY `FK_course_asmt_idx` (`upload_course`),"
				+ " KEY `FK_staff_asmt_idx` (`upload_file_user`),"
				+ " CONSTRAINT `FK_course_asmt` FOREIGN KEY (`upload_course`) REFERENCES `f15g109_course` (`course_code`) ON DELETE NO ACTION ON UPDATE NO ACTION"
				+ ");";
		String createAsmtResults = "CREATE TABLE IF NOT EXISTS `f15g109_assessment_results` ("
				+ " `assessment_name` varchar(45) NOT NULL," + " `student_email_id` varchar(45) NOT NULL,"
				+ " `course_code` varchar(45) NOT NULL," + " `question_number` int(60) NOT NULL,"
				+ " `submitted_answer` varchar(45) DEFAULT NULL,`score` varchar(1) DEFAULT NULL,"
				+ " PRIMARY KEY (`assessment_name`,`question_number`,`student_email_id`),"
				+ " KEY `FK_stud_idx` (`student_email_id`)," + " KEY `FK_course_idx` (`course_code`),"
				+ " CONSTRAINT `FK_stud` FOREIGN KEY (`student_email_id`) REFERENCES `f15g109_student` (`email_id`) ON DELETE NO ACTION ON UPDATE NO ACTION"
				+ ");";
		String createAsmtGrade = "CREATE TABLE IF NOT EXISTS `f15g109_assessment_grade` ("
				+ " `assessment_name` varchar(45) NOT NULL," + " `student_email_id` varchar(45) NOT NULL,"
				+ " `assessment_grade` varchar(45) DEFAULT NULL," + " `professor_comments` varchar(45) DEFAULT NULL,"
				+ " PRIMARY KEY (`assessment_name`,`student_email_id`)" + ");";
		String createloginTrack = "CREATE TABLE IF NOT EXISTS `f15g109_login_tracking` ("
				+ " `ip_address` varchar(45) DEFAULT NULL,"
				+ " `last_login` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," + " `user_id` varchar(45) DEFAULT NULL"
				+ ");";
		String insertCourseOne = "INSERT INTO `f15g109_course` (`course_code`, `course_name`, `crn`, `credits`) VALUES ('IDS_400', 'Mathematics', '32154', '4');";
		String insertCourseTwo = "INSERT INTO `f15g109_course` (`course_code`, `course_name`, `crn`, `credits`) VALUES ('IDS_517', 'Quantitative Statistics', '12345', '4');";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(createCourseStmt);
			stmt.executeUpdate(createStudentStmt);
			stmt.executeUpdate(createStaffStmt);
			stmt.executeUpdate(createUploadStmt);
			stmt.executeUpdate(createAsmtResults);
			stmt.executeUpdate(createAsmtGrade);
			stmt.executeUpdate(createloginTrack);
			stmt.executeUpdate(insertCourseOne);
			stmt.executeUpdate(insertCourseTwo);
		} catch (SQLException e) {
			FacesMessage msg = new FacesMessage("Error while creating application tables");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// e.printStackTrace();
		}
	}

	public static String updateLastLogin(String ipAddress, String username, Connection conn) {
		String timeStamp = "";
		Statement stmt = null;
		ResultSet rs = null;
		String insertStmt = "INSERT INTO f15g109_login_tracking (`ip_address`,`user_id`) " + "VALUES(?,?)";
		String selectStmt = "SELECT max(last_login) FROM f15g109_login_tracking Where user_id=  '" + username + "';";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectStmt);
			rs.next();
			timeStamp = rs.getString(1);
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(insertStmt);
			ps.setString(1, ipAddress);
			ps.setString(2, username);
			ps.executeUpdate();

		} catch (SQLException ex) {
			FacesMessage msg = new FacesMessage("Error while inserting login track data information");
			FacesContext.getCurrentInstance().addMessage(null, msg);
//			// ex.printStackTrace();
		}
		return timeStamp;
	}

	public static String createAssessment(String tableName, Connection conn) {
		String createStmt = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + " question_number INT NULL ,"
				+ " question_type VARCHAR(45) NULL ," + " actual_question VARCHAR(300) NULL , "
				+ " answer VARCHAR(100) NULL ," + " tolerance VARCHAR(45) NULL ," + " course_id VARCHAR(45) NULL );";
		Statement stmt = null;
		String status = "CREATEASSESSMENTSUCCESS";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(createStmt);
			// System.out.println("Create assessment successful");
		} catch (Exception Ex) {
			FacesMessage msg = new FacesMessage("Error in creating assessment");
			FacesContext.getCurrentInstance().addMessage(null, msg);
//			// ex.printStackTrace();
			status = "CREATEASSESSMENTFAILURE";
		}

		return status;
	}

	public static String getCourseList(ArrayList<Course> courseList, String emailID, Connection conn) {
		String sql = "SELECT c.course_code, c.course_name, c.crn, c.credits " + " FROM f15g109_student s "
				+ " INNER JOIN f15g109_course c ON c.course_code = s.course_code " + " WHERE s.email_id = '" + emailID
				+ "';";
		Statement stmt = null;
		ResultSet rs = null;
		String status = "STUDENTCOURSESUCCESS";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Course course = new Course();
				course.setCourseCode(rs.getString(1));
				course.setCourseName(rs.getString(2));
				course.setCourseCRN(rs.getString(3));
				course.setCourseCredits(rs.getString(4));
				courseList.add(course);
			}
			// System.out.println("select Statement executed");
		} catch (SQLException ex) {
//			// ex.printStackTrace();
			status = "STUDENTCOURSEFAILURE";
		}
		return status;
	}

	public static String insertFileUpload(String columnType, String columnLabel, String username, String course_code,
			Connection conn) {
		String insertStmt = "";
		String status = "INSERTFILEUPLOADSUCCESS";
		try {
			insertStmt = "INSERT INTO f15g109_upload_files VALUES(?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(insertStmt);
			ps.setString(1, columnType);
			ps.setString(2, columnLabel);
			ps.setString(3, username);
			ps.setString(4, course_code);
			// System.out.println(insertStmt);
			// System.out.println(ps);
			ps.executeUpdate();
			// System.out.println("Insert successful");
		} catch (SQLException ex) {
			FacesMessage msg = new FacesMessage("Error in inserting values");
			FacesContext.getCurrentInstance().addMessage(null, msg);
//			// ex.printStackTrace();
			status = "INSERTFILEUPLOADFAILURE";
			// System.out.println("Insert failed");
		}
		return status;
	}

	public static String viewUploadFileLabels(ArrayList<String> filelabelList, String uploadType, String coureCode,
			String staffName, Connection conn) {
		String selectStmt = "SELECT upload_file_label FROM f15g109_upload_files " + "WHERE upload_file_type = '"
				+ uploadType + "' " + "AND upload_course = '" + coureCode + "' ORDER BY upload_file_label;";
		Statement stmt = null;
		ResultSet rs = null;
		String status = "SELECTUPLOADFILESUCCESS";
		// System.out.println(selectStmt);
		// System.out.println("filelabellist" + filelabelList);
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectStmt);
			// System.out.println("select Statement executed");
			if (!filelabelList.isEmpty()) {
				filelabelList.clear();
			}
			while (rs.next()) {
				// System.out.println(rs.getString(1));
				filelabelList.add(rs.getString(1));

			}
			// System.out.println(filelabelList);
			// System.out.println("Select successful");
		} catch (Exception Ex) {
			FacesMessage msg = new FacesMessage("Error in viewing the data from  table");
			FacesContext.getCurrentInstance().addMessage(null, msg);
//			// ex.printStackTrace();
			status = "SELECTUPLOADFILEFAILURE";
		}
		return status;
	}

	public static String viewStudentQuiz(String userName, ArrayList<String> filelabelList, String courseCode,
			String uploadType, Connection conn) {
		String selectStmt = "SELECT upload_file_label FROM "
				+ "f15g109_upload_files WHERE upload_file_type ='Assessment' AND "
				+ "upload_file_label NOT IN ( SELECT distinct assessment_name " + "FROM f15g109_assessment_results "
				+ "WHERE student_email_id ='" + userName + "' )ORDER BY upload_file_label;";

		Statement stmt = null;
		ResultSet rs = null;
		String status = "VIEWQUIZSUCCESS";
		// System.out.println(selectStmt);
		// System.out.println("filelabellist" + filelabelList);
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectStmt);
			// System.out.println("select Statement executed");
			if (!filelabelList.isEmpty()) {
				filelabelList.clear();
			}
			while (rs.next()) {
				filelabelList.add(rs.getString(1));
			}
			// System.out.println(filelabelList);
			// System.out.println("Select successful");
		} catch (Exception Ex) {
			FacesMessage msg = new FacesMessage("Error in viewing the data from  table");
			FacesContext.getCurrentInstance().addMessage(null, msg);
//			// ex.printStackTrace();
			status = "VIEWQUIZFAILURE";
		}
		return status;
	}

	public static String viewStudentQuizForDload(String userName, ArrayList<String> filelabelList, String courseCode,
			String uploadType, Connection conn) {
		String selectStmt = "SELECT upload_file_label FROM f15g109_upload_files WHERE "
				+ "upload_file_type ='Assessment' AND " + "upload_file_label IN ( SELECT distinct assessment_name FROM "
				+ "f15g109_assessment_results ) " + "ORDER BY upload_file_label;";
		Statement stmt = null;
		ResultSet rs = null;
		String status = "DLOADQUIZSUCCESS";
		// System.out.println(selectStmt);
		// System.out.println("filelabellist" + filelabelList);
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectStmt);
			// System.out.println("select Statement executed");
			if (!filelabelList.isEmpty()) {
				filelabelList.clear();
			}
			while (rs.next()) {
				filelabelList.add(rs.getString(1));
			}
			// System.out.println(filelabelList);
			// System.out.println("Select successful");
		} catch (Exception Ex) {
			FacesMessage msg = new FacesMessage("Error in viewing the data for download from  table");
			FacesContext.getCurrentInstance().addMessage(null, msg);
//			// ex.printStackTrace();
			status = "DLOADQUIZFAILURE";
		}
		return status;
	}

	public static String insertAssessment(List<Assessment> asmtList, String tableName, String course_code,
			Connection conn) {
		String insertStmt = "";
		String status = "INSERTASSESSMENTSUCCESS";
		int count = 0;
		try {
			insertStmt = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(insertStmt);
			for (Assessment asmt : asmtList) {
				ps.setInt(1, asmt.getQuestionNumber());
				ps.setString(2, asmt.getQuestionType());
				ps.setString(3, asmt.getActualQuestion());
				ps.setString(4, asmt.getAnswer());
				ps.setString(5, asmt.getTolerance());
				ps.setString(6, course_code);
				// System.out.println(insertStmt);
				// System.out.println(ps);
				count += ps.executeUpdate();
			}
			FacesMessage msg = new FacesMessage("Data uploaded successfully. " + count + " rows inserted");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// System.out.println("Insert successful");
			count = 0;
		} catch (SQLException ex) {
			FacesMessage msg = new FacesMessage("Error in inserting values");
			FacesContext.getCurrentInstance().addMessage(null, msg);
//			// ex.printStackTrace();
			status = "INSERTASSESSMENTFAILURE";
			// System.out.println("Insert failed");
		}
		return status;
	}

	public static String viewStafftable(String tableName, Connection conn) {
		String selectStmt = "SELECT * FROM " + tableName + ";";
		Statement stmt = null;
		ResultSet rs = null;
		String status = "SELECTSTAFFSUCCESS";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectStmt);
			// System.out.println("select Statement executed");
			while (rs.next()) {
				Professor prof = new Professor();
				prof.setFirst_name(rs.getString(1));
				prof.setLast_name(rs.getString(2));
				prof.setRole(rs.getString(3));
				prof.setCourse_code(rs.getString(4));
				prof.setEmail_id(rs.getString(5));
				prof.setPassword(rs.getString(6));
			}
			// System.out.println("Select successful");
		} catch (Exception Ex) {
			FacesMessage msg = new FacesMessage("Error in viewing the data from assessment table");
			FacesContext.getCurrentInstance().addMessage(null, msg);
//			// ex.printStackTrace();
			status = "SELECTASSESSMENTFAILURE";
		}
		return status;
	}

	public static String insertRoster(List<Student> rosterList, String tableName, String course_code, Connection conn) {
		String insertStmt = "";
		String status = "INSERTROSTERSUCCESS";
		int count = 0;
		try {
			insertStmt = "INSERT INTO " + tableName + ""
					+ "(`first_name`,`last_name`,`univ_id_num`,`email_id`,`password`,`course_code`)"
					+ " VALUES(?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(insertStmt);
			for (Student roster : rosterList) {
				ps.setString(1, roster.getFirst_name());
				ps.setString(2, roster.getLast_name());
				ps.setString(3, roster.getUniv_id_num());
				ps.setString(4, roster.getEmail_id());
				ps.setString(5, roster.getPassword());
				ps.setString(6, course_code);
				// System.out.println(insertStmt);
				// System.out.println(ps);
				count += ps.executeUpdate();
			}
			FacesMessage msg = new FacesMessage("Data uploaded successfully. " + count + " rows inserted");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// System.out.println("Insert roster successful");
		} catch (SQLException ex) {
			FacesMessage msg = new FacesMessage(
					"Student records with the same names in the roster file already exist.Please use a diffrent roster file");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			status = "INSERTROSTERFAILURE";
			// System.out.println(ex.getMessage());
		}
		return status;
	}

	public static String selectAssessment(ArrayList<Assessment> assessmentList, String tableName, String courseCode,
			Connection conn) {
		String selectStmt = "SELECT * FROM " + tableName + ";";
		Statement stmt = null;
		ResultSet rs = null;
		String status = "SELECTASSESSMENTSUCCESS";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectStmt);
			// System.out.println("select Statement executed");
			while (rs.next()) {
				Assessment asmt = new Assessment();
				asmt.setQuestionNumber(rs.getInt(1));
				asmt.setQuestionType(rs.getString(2));
				asmt.setActualQuestion(rs.getString(3));
				asmt.setAnswer(rs.getString(4));
				asmt.setTolerance(rs.getString(5));
				asmt.setCourseID(courseCode);
				assessmentList.add(asmt);
			}
			// System.out.println("Select successful");
		} catch (Exception Ex) {
			FacesMessage msg = new FacesMessage("Error in viewing the data from assessment table");
			FacesContext.getCurrentInstance().addMessage(null, msg);
//			// ex.printStackTrace();
			status = "SELECTASSESSMENTFAILURE";
		}
		return status;
	}

	public static String selectCompletedAssessment(ArrayList<Assessment> assessmentList, String tableName,
			String courseCode, String userName, Connection conn) {
		String selectStmt = "SELECT * FROM " + tableName + ";";
		String selectStmt2 = "SELECT submitted_answer, score " + "FROM f15g109_assessment_results where "
				+ "assessment_name='" + tableName + "' and student_email_id='" + userName + "' " + "and course_code='"
				+ courseCode + "' order by question_number;";
		Statement stmt1 = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String status = "SELECTCOMPLETEDASSESSMENTSUCCESS";
		try {
			stmt1 = conn.createStatement();
			stmt2 = conn.createStatement();
			rs = stmt1.executeQuery(selectStmt);
			rs2 = stmt2.executeQuery(selectStmt2);
			// System.out.println("select Statement executed");
			while (rs.next() && rs2.next()) {
				Assessment asmt = new Assessment();
				asmt.setQuestionNumber(rs.getInt(1));
				asmt.setQuestionType(rs.getString(2));
				asmt.setActualQuestion(rs.getString(3));
				asmt.setAnswer(rs.getString(4));
				asmt.setTolerance(rs.getString(5));
				asmt.setCourseID(rs.getString(6));
				asmt.setSubmittedAnswers(rs2.getString(1));
				asmt.setScore(rs2.getInt(2));
				assessmentList.add(asmt);
			}
			// System.out.println("Select successful");
		} catch (Exception Ex) {
			FacesMessage msg = new FacesMessage("Error in viewing the data from assessment table");
			FacesContext.getCurrentInstance().addMessage(null, msg);
//			// ex.printStackTrace();
			status = "SELECTCOMPLETEDASSESSMENTFAILURE";
		}
		return status;
	}

	public static String registerStaff(Professor prof, Connection conn) {
		String insertStmtStaff = "INSERT INTO f15g109_staff "
				+ "(`first_name`, `last_name`,`role`,`course_code`,`email_id`,`password`) " + "VALUES(?,?,?,?,?,?)";
		String insertStmtStudent = "INSERT INTO f15g109_student "
				+ "(`first_name`, `last_name`,`univ_id_num`,`course_code`,`email_id`,`password`) "
				+ "VALUES(?,?,?,?,?,?)";
		String status = "REGISTERSUCCESS";
		try {
			PreparedStatement psOne = (PreparedStatement) conn.prepareStatement(insertStmtStaff);
			PreparedStatement psTwo = (PreparedStatement) conn.prepareStatement(insertStmtStudent);
			psOne.setString(1, prof.getFirst_name());
			psOne.setString(2, prof.getLast_name());
			psOne.setString(3, prof.getRole());
			psOne.setString(4, prof.getCourse_code());
			psOne.setString(5, prof.getEmail_id());
			psOne.setString(6, prof.getPassword());
			psTwo.setString(1, prof.getFirst_name());
			psTwo.setString(2, prof.getLast_name());
			psTwo.setString(3, "00000000");
			psTwo.setString(4, prof.getCourse_code());
			psTwo.setString(5, prof.getEmail_id());
			psTwo.setString(6, prof.getPassword());
			// System.out.println(" " + psOne);
			// System.out.println(" " + psTwo);
			psOne.executeUpdate();
			psTwo.executeUpdate();
		} catch (SQLException ex) {
//			// ex.printStackTrace();
			status = "REGISTERFAILURE";
		}
		return status;
	}

	public static int evaluateQuiz(ArrayList<Assessment> assessmentList, ArrayList<String> AnswerList,
			ArrayList<String> marksList, String tableName, Connection conn) {

		String selectStmt = "SELECT * FROM " + tableName + ";";
		// System.out.println(" " + selectStmt);
		Statement stmt = null;
		ResultSet rs = null;
		int marks = 0;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectStmt);
			// System.out.println("select Statement executed : quiz");
			while (rs.next()) {
				Assessment asmt = new Assessment();
				asmt.setQuestionType(rs.getString(3));
				asmt.setAnswer(rs.getString(5));
				asmt.setTolerance(rs.getString(6));
				assessmentList.add(asmt);
			}
			for (int i = 0; i < AnswerList.size(); i++) {
				if (!AnswerList.get(i).isEmpty()) {
					// System.out.println(assessmentList.get(i).getQuestionType().toString());
					if (assessmentList.get(i).getQuestionType().toString().equalsIgnoreCase("Categorical")) {
						if (assessmentList.get(i).getAnswer().toString().equalsIgnoreCase(AnswerList.get(i))) {
							marks++;
							marksList.add("1");
						} else {
							marksList.add("0");
						}
					} else {
						String numericFlag = "Numeric";
						numericFlag = isNumeric(AnswerList.get(i).toString());
						// System.out.println(numericFlag);
						if (!numericFlag.equalsIgnoreCase("NotNumeric")) {
							Double tolerance = Double.parseDouble(assessmentList.get(i).getTolerance());
							Double max = tolerance + Double.parseDouble(assessmentList.get(i).getAnswer());
							Double min = Double.parseDouble(assessmentList.get(i).getAnswer()) - tolerance;

							if ((Double.parseDouble(AnswerList.get(i))) <= max
									&& (Double.parseDouble(AnswerList.get(i)) >= min)) {
								marks++;
								marksList.add("1");
							} else {
								marksList.add("0");
							}
						} else {
							marksList.add("0");
						}
					}
				} else {
					marksList.add("0");
				}
			}
			// System.out.println("score " + marks);
		} catch (Exception Ex) {
//			// ex.printStackTrace();
		}
		return marks;

	}

	public static String isNumeric(String answer) {
		String status = "Numeric";
		try {
			Double.parseDouble(answer);
		} catch (Exception ex) {
			status = "NotNumeric";
		}
		return status;
	}

	public static String selectRoster(ArrayList<Student> StudList, String tableName, String courseCode,
			Connection conn) {
		String defaultUIN = "00000000";
		String selectStmt = "SELECT * FROM " + tableName + " WHERE course_code = '" + courseCode
				+ "' AND univ_id_num <> '" + defaultUIN + "';";
		// System.out.println("table name" + tableName);
		Statement stmt = null;
		ResultSet rs = null;
		String status = "SELECTROSTERSUCCESS";
		try {
			stmt = conn.createStatement();
			// System.out.println(selectStmt);
			rs = stmt.executeQuery(selectStmt);
			// System.out.println("select Statement executed");

			while (rs.next()) {
				Student stud = new Student();
				stud.setFirst_name(rs.getString(1));
				stud.setLast_name(rs.getString(2));
				stud.setUniv_id_num(rs.getString(3));
				stud.setEmail_id(rs.getString(4));
				stud.setPassword(rs.getString(5));
				stud.setCourse_code(rs.getString(7));
				StudList.add(stud);
			}
			// System.out.println("Select successful");
		} catch (Exception Ex) {
			FacesMessage msg = new FacesMessage("Error in viewing data from roster table");
			FacesContext.getCurrentInstance().addMessage(null, msg);
//			// ex.printStackTrace();
			status = "VIEWROSTERFAILURE";
		}
		return status;
	}

	public static String dropAssessment(String tableName, Connection conn) {
		String dropStmt = "DROP TABLE IF EXISTS " + tableName + ";";
		String deleteRow = "DELETE FROM f15g109_upload_files WHERE " + "upload_file_label = '" + tableName + "'";
		Statement stmt = null;
		String status = "DROPASSESSMENTSUCCESS";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(dropStmt);
			// System.out.println("Drop Successful");
			stmt.executeUpdate(deleteRow);
		} catch (Exception Ex) {
			FacesMessage msg = new FacesMessage("Error in dropping the the assessment table");
			FacesContext.getCurrentInstance().addMessage(null, msg);
//			// ex.printStackTrace();
			status = "DROPASSESSMENTFAILURE";
		}
		return status;
	}

	public static String dropRoster(String tableName, Connection conn) {
		String dropStmt = "DROP TABLE IF EXISTS " + tableName + ";";
		Statement stmt = null;
		String status = "DROPROSTERSUCCESS";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(dropStmt);
			// System.out.println("Drop Roster Successful");
		} catch (Exception Ex) {
			FacesMessage msg = new FacesMessage("Error in dropping the roster table");
			FacesContext.getCurrentInstance().addMessage(null, msg);
//			// ex.printStackTrace();
			status = "DROPROSTERFAILURE";
		}
		return status;
	}

	public static ResultSet fetchTableData(String selectedTableName, Connection conn) {
		String sql = "SELECT * FROM " + selectedTableName + ";";
		// System.out.println(sql);
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (Exception ex) {
			FacesMessage msg = new FacesMessage("Unable to fetch data from " + selectedTableName);
			FacesContext.getCurrentInstance().addMessage(null, msg);
//			// ex.printStackTrace();
			rs = null;
		}
		return rs;
	}

	public static String fetchCourseCodeStaff(String userName, Connection conn) {
		String sql = "SELECT course_code FROM f15g109_staff WHERE email_id = '" + userName + "';";
		String status = "";
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			status = rs.getString(1);
		} catch (Exception ex) {
			FacesMessage msg = new FacesMessage("Unable to fetch course for staff");
			FacesContext.getCurrentInstance().addMessage(null, msg);
//			// ex.printStackTrace();
		}
		return status;
	}

	public static String fetchRole(String userName, Connection conn) {
		String sql = "SELECT DISTINCT role FROM f15g109_staff WHERE email_id = '" + userName + "';";
		String status = "";
		// System.out.println("username" + userName);
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			status = rs.getString(1);
		} catch (Exception ex) {
			FacesMessage msg = new FacesMessage("Unable to fetch role for staff");
			FacesContext.getCurrentInstance().addMessage(null, msg);
//			// ex.printStackTrace();
		}
		return status;
	}

	public static String fetchStaffLastName(String userName, Connection conn) {
		String sql = "SELECT last_name FROM f15g109_staff WHERE email_id = '" + userName + "';";
		String status = "";
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			status = rs.getString(1);
		} catch (Exception ex) {
			FacesMessage msg = new FacesMessage("Unable to fetch last name of staff");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// ex.printStackTrace();
		}
		return status;
	}

	public static String fetchStudentLastName(String userName, Connection conn) {
		String sql = "SELECT last_name FROM f15g109_student WHERE email_id = '" + userName + "';";
		String status = "";
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			status = rs.getString(1);
		} catch (Exception ex) {
			FacesMessage msg = new FacesMessage("Unable to fetch last name of student");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// ex.printStackTrace();
		}
		return status;
	}

	public static String fetchCourseCodeStudent(String userName, Connection conn) {
		String sql = "SELECT course_code FROM f15g109_student WHERE email_id = '" + userName + "';";
		String status = "";
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			status = rs.getString(1);
		} catch (Exception ex) {
			FacesMessage msg = new FacesMessage("Unable to fetch course for student");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// ex.printStackTrace();
		}
		return status;
	}

	public static void dropApplicationTables(Connection conn) {
		Statement stmt = null;
		String dropStmtOne = "DROP TABLE IF EXISTS `f15g109_assessment_grade`;";
		String dropStmtTwo = "DROP TABLE IF EXISTS `f15g109_assessment_results`;";
		String dropStmtThree = "DROP TABLE IF EXISTS `f15g109_upload_files`;";
		String dropStmtFour = "DROP TABLE IF EXISTS `f15g109_student`;";
		String dropStmtFive = "DROP TABLE IF EXISTS `f15g109_staff`;";
		String dropStmtSix = "DROP TABLE IF EXISTS `f15g109_course`;";
		String dropStmtSeven = "DROP TABLE IF EXISTS `f15g109_login_tracking`";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(dropStmtOne);
			stmt.executeUpdate(dropStmtTwo);
			stmt.executeUpdate(dropStmtThree);
			stmt.executeUpdate(dropStmtFour);
			stmt.executeUpdate(dropStmtFive);
			stmt.executeUpdate(dropStmtSix);
			stmt.executeUpdate(dropStmtSeven);
			// System.out.println("drop executed " + stmt);
		} catch (SQLException e) {
			FacesMessage msg = new FacesMessage("Error while dropping application tables");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// e.printStackTrace();
		}
	}

	public static String gradeQuiz(String selectedQuiz, ArrayList<Assessment> assessmentList,
			ArrayList<String> answerList, ArrayList<String> marksList, String userName, Connection conn) {
		String insertStmt = "INSERT INTO f15g109_assessment_results "
				+ "(`assessment_name`,`student_email_id`,`course_code`,`question_number`,`submitted_answer`,`score`) "
				+ "VALUES(?,?,?,?,?,?)";
		String status = "QUIZGRADESUCCESS";
		int i = 0;
		try {
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(insertStmt);
			for (i = 0; i < marksList.size(); i++) {
				ps.setString(1, selectedQuiz);
				ps.setString(2, userName);
				ps.setString(3, assessmentList.get(i).getCourseID());
				ps.setInt(4, assessmentList.get(i).getQuestionNumber());
				ps.setString(5, answerList.get(i));
				ps.setString(6, marksList.get(i));
				// System.out.println(" " + ps);
				ps.executeUpdate();
			}
		} catch (SQLException ex) {
			FacesMessage msg = new FacesMessage("Error while trying to inserting asseessment grades");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// ex.printStackTrace();
			status = "QUIZGRADEFAILURE";
		}
		return status;
	}

	public static String insertAssessmentGrades(String quizName, String userName, int score, Connection conn) {
		String status = "INSERTGRADESSUCCESS";
		String insertStmt = "INSERT INTO f15g109_assessment_grade "
				+ "(assessment_name,student_email_id,assessment_grade)" + "VALUES(?,?,?)";
		try {
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(insertStmt);
			ps.setString(1, quizName);
			ps.setString(2, userName);
			ps.setInt(3, score);
			// System.out.println(" " + ps);
			ps.executeUpdate();
		} catch (SQLException ex) {
			FacesMessage msg = new FacesMessage("Error while trying to insert values into assessment grades table");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// ex.printStackTrace();
			status = "INSERTGRADESFAILURE";
		}
		return status;
	}

	public static String insertProfessorComments(ArrayList<Grades> viewGradesList, Grades grades, Connection conn) {
		String status = "INSERTCOMMENTSSUCCESS";
		String insertStmt = "UPDATE f15g109_assessment_grade SET "
				+ "professor_comments = ? WHERE student_email_id = ? " + "AND assessment_name = ?;";
		int i = 0;
		try {
			// Grades grades = new Grades();
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(insertStmt);
			// System.out.println(grades.getProfessor_comments());
			// System.out.println(viewGradesList.size());
			for (i = 0; i < viewGradesList.size(); i++) {
				ps.setString(1, grades.getProfessor_comments_list().get(i));
				ps.setString(2, viewGradesList.get(i).getStudent_email_id());
				ps.setString(3, viewGradesList.get(i).getAssessmentName());
				// System.out.println(" " + ps);
				ps.executeUpdate();
			}
			// grades.getProfessor_comments_list().clear();
		} catch (SQLException ex) {
			FacesMessage msg = new FacesMessage("Error while trying to insert values into assessment grades table");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// ex.printStackTrace();
			status = "INSERTCOMMENTSFAILURE";
		}
		return status;
	}

	public static String fetchGradesForStudent(String userName, ArrayList<Grades> viewGradesList, Connection conn) {
		String sql = "SELECT * FROM f15g109_assessment_grade WHERE student_email_id = '" + userName + "';";
		String status = "FETCHGRADESSTUDENTSUCCESS";
		ResultSet rs = null;
		Statement stmt = null;
		// System.out.println("fetch grades for student invoked");
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Grades grades = new Grades();
				grades.setAssessmentName(rs.getString(1));
				grades.setStudent_email_id(rs.getString(2));
				grades.setAssessment_grade(rs.getInt(3));
				// System.out.println(" comments" + rs.getString(4));
				grades.setProfessor_comments(rs.getString(4));
				viewGradesList.add(grades);
			}
		} catch (Exception ex) {
			status = "FETCHGRADESSTUDENTFAILURE";
			FacesMessage msg = new FacesMessage("Unable to fetch grades for student");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// ex.printStackTrace();
		}
		return status;
	}

	public static ResultSet statsDataTimeSeriesChart(String userName, String courseCode, Connection conn) {
		String sql = "select assessment_name,student_email_id,assessment_grade "
				+ "from f15g109_assessment_grade where assessment_name in"
				+ "(select upload_file_label from f15g109_assessment_grade " + "inner join  f15g109_upload_files where "
				+ "upload_file_user='" + userName + "' ); ";
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			// System.out.println("result set " + rs);
		} catch (Exception ex) {
			FacesMessage msg = new FacesMessage("Unable to fetch data for Time series chart");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// ex.printStackTrace();
		}
		return rs;
	}

	public static String fetchGrades(ArrayList<Grades> viewGradesList, String profName, Connection conn) {
		String sql = "SELECT * FROM f15g109_assessment_grade WHERE assessment_name IN ("
				+ "SELECT upload_file_label FROM f15g109_upload_files " + "WHERE upload_file_user = '" + profName
				+ "');";
		String status = "FETCHGRADESSUCCESS";
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Grades grades = new Grades();
				grades.setAssessmentName(rs.getString(1));
				grades.setStudent_email_id(rs.getString(2));
				grades.setAssessment_grade(rs.getInt(3));
				// System.out.println(" comments" + rs.getString(4));
				grades.setProfessor_comments(rs.getString(4));
				viewGradesList.add(grades);
			}
		} catch (Exception ex) {
			status = "FETCHGRADESFAILURE";
			FacesMessage msg = new FacesMessage("Unable to fetch course for student");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// ex.printStackTrace();
		}
		return status;
	}

	public static ResultSet fetchRosterDownload(String tableName, String userName, Connection conn) {
		String courseCode = fetchCourseCodeStaff(userName, conn);
		String default_uin = "00000000";
		String selectStmt = "SELECT * FROM " + tableName + " WHERE course_code = '" + courseCode + "' "
				+ "AND univ_id_num <> '" + default_uin + "';";
		Statement stmt = null;
		ResultSet rs = null;
		// String status = "DOWNLOADROSTERSUCCESS";
		try {
			stmt = conn.createStatement();
			// System.out.println(selectStmt);
			rs = stmt.executeQuery(selectStmt);
			// System.out.println("Select successful");
		} catch (Exception Ex) {
			FacesMessage msg = new FacesMessage("Error in viewing the data from student table");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// ex.printStackTrace();
			// status = "DOWNLOADROSTERFAILURE";
		}
		// System.out.println(status);
		return rs;
	}

	public static ResultSet fetchAssessmentDownload(String tableName, Connection conn) {
		String selectStmt = "SELECT * FROM " + tableName + ";";
		Statement stmt = null;
		ResultSet rs = null;
		// String status = "DOWNLOADQUIZSUCCESS";
		try {
			stmt = conn.createStatement();
			// System.out.println(selectStmt);
			rs = stmt.executeQuery(selectStmt);
			// System.out.println("Select successful");
		} catch (Exception Ex) {
			FacesMessage msg = new FacesMessage("Error in viewing the data from " + tableName + " table");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// ex.printStackTrace();
			// status = "DOWNLOADQUIZFAILURE";
		}
		// System.out.println(status);
		return rs;
	}

	public static double[] descriptiveStatistics(Connection conn, String assessmentName) {
		double x[] = new double[10];
		String sql = "SELECT assessment_grade " + " FROM f15g109_assessment_grade" + " WHERE assessment_name = '"
				+ assessmentName + "';";
		Statement stmt = null;
		ResultSet rs = null;
		int i = 0;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				x[i] = Double.parseDouble(rs.getString(1));
				i++;
			}
			System.out.println(x);
		} catch (SQLException ex) {
			// ex.printStackTrace();
		}
		return x;
	}

	public static void disconnect(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
				// System.out.println("\nDatabase connection is disconnected");
			} catch (SQLException e) {
				FacesMessage msg = new FacesMessage("Error in disconnecting from the database");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				// e.printStackTrace();
			}
		}
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		Database.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		Database.password = password;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		Database.url = url;
	}
}