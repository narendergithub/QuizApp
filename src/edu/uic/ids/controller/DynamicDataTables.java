package edu.uic.ids.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlColumn;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;

import edu.uic.ids.model.DatabaseConnect;

@ManagedBean(name = "dynamicDataTables")
@SessionScoped
public class DynamicDataTables {

	// Init
	private String selectedTable;
	private ArrayList<String> databaseTableList;
	private static List<List<String>> dynamicList;
	private static String[] dynamicHeaders;
	private HtmlPanelGroup dynamicDataTableGroup; // Placeholder.
	private ArrayList<String> viewCourseData;
	private boolean renderDynamicTable;
	private DatabaseConnect dbconnect;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, Object> m = context.getExternalContext().getSessionMap();
		dbconnect = (DatabaseConnect) m.get("databaseConnect");
	}

	public DynamicDataTables() {
		// dynamicList = new ArrayList<List<String>>();
		viewCourseData = new ArrayList<>();
		databaseTableList = new ArrayList<>();
	}

	public String back() {
		// System.out.println("back is invoked");
		dynamicList.clear();
		dynamicDataTableGroup = null;
		// System.out.println(dynamicDataTableGroup);
		return "returnToDatabaseAccess";
	}

	public String fetchTableData() {
		String status = "DATAFETCHSUCCESS";

		// fetchTablesFromDatabase();
		return status;
	}

	public String createApplicationTables() {
		String status = "";
		Connection conn = null;
		try {
			conn = Database.connectDB();
			Database.createApplicationTables(conn);
			// System.out.println("application tables created successfully");
		} catch (Exception ex) {
		}
		fetchTablesFromDatabase();
		return status;
	}

	public String fetchTablesFromDatabase() {
		String status = "";
		if (!(databaseTableList.isEmpty())) {
			databaseTableList.clear();
		}
		Connection conn = null;
		try {
			conn = Database.connectDB();
			Database.fetchTableNames(databaseTableList, dbconnect.getSchema(), conn);
		} catch (Exception ex) {
		}
		return status;
	}

	public String dropSelectedTable() {
		String status = "";
		Connection conn = null;
		try {
			conn = Database.connectDB();
			// System.out.println(selectedTable);
			Database.dropAssessment(selectedTable, conn);
		} catch (Exception ex) {
			// // ex.printStackTrace();
		}
		fetchTablesFromDatabase();
		return status;
	}

	public String dropApplicationTables() {
		String status = "";
		Connection conn = null;
		try {
			conn = Database.connectDB();
			Database.dropApplicationTables(conn);
		} catch (Exception ex) {
		}
		fetchTablesFromDatabase();
		return status;
	}

	// Actions
	public void loadDynamicList() {
		// System.out.println("load dynamic list invoked");
		List<String> applicationTablesList = Arrays.asList("f15g109_student", "f15g109_staff", "f15g109_course",
				"f15g109_assessment_grade", "f15g109_assessment_results", "f15g109_upload_files",
				"f15g109_login_tracking");
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = Database.connectDB();
			rs = Database.fetchTableData(selectedTable, conn);
			setRenderDynamicTable(true);
			// System.out.println("" + isRenderDynamicTable());
		} catch (Exception ex) {
			// ex.printStackTrace();
		}

		// Set headers
		// Set rows.
		try {
			dynamicList = new ArrayList<List<String>>();
			if (applicationTablesList.contains(selectedTable)) {
				if (selectedTable.equalsIgnoreCase("f15g109_student")) {
					dynamicHeaders = new String[] { "first_name", "last_name", "univ_id_num", "email_id",
							"course_code" };
					while (rs.next()) {
						dynamicList.add(Arrays.asList(new String[] { rs.getString(1), rs.getString(2), rs.getString(3),
								rs.getString(4), rs.getString(7) }));
					}
				}
				if (selectedTable.equalsIgnoreCase("f15g109_staff")) {
					dynamicHeaders = new String[] { "first_name", "last_name", "role", "course_code", "email_id", };
					while (rs.next()) {
						dynamicList.add(Arrays.asList(new String[] { rs.getString(1), rs.getString(2), rs.getString(3),
								rs.getString(4), rs.getString(5) }));
					}
				}
				if (selectedTable.equalsIgnoreCase("f15g109_course")) {
					dynamicHeaders = new String[] { "course_code", "course_name", "crn", "credits" };
					while (rs.next()) {
						dynamicList.add(Arrays.asList(
								new String[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4) }));
					}
				}
				if (selectedTable.equalsIgnoreCase("f15g109_assessment_grade")) {
					dynamicHeaders = new String[] { "assessment_name", "student_email_id", "assessment_grade",
							"professor_comments" };
					while (rs.next()) {
						dynamicList.add(Arrays.asList(
								new String[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4) }));
					}
				}
				if (selectedTable.equalsIgnoreCase("f15g109_assessment_results")) {
					dynamicHeaders = new String[] { "assessment_name", "student_email_id", "course_code",
							"question_number", "submitted_answer", "score" };
					while (rs.next()) {
						dynamicList.add(Arrays.asList(new String[] { rs.getString(1), rs.getString(2), rs.getString(3),
								rs.getString(4), rs.getString(5), rs.getString(6) }));
					}
				}
				if (selectedTable.equalsIgnoreCase("f15g109_upload_files")) {
					dynamicHeaders = new String[] { "upload_file_type", "upload_file_label", "upload_file_user",
							"upload_course" };
					while (rs.next()) {
						dynamicList.add(Arrays.asList(
								new String[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4) }));
					}
				}
				if (selectedTable.equalsIgnoreCase("f15g109_login_tracking")) {
					dynamicHeaders = new String[] { "ip_address", "last_login", "user_id" };
					while (rs.next()) {
						dynamicList
								.add(Arrays.asList(new String[] { rs.getString(1), rs.getString(2), rs.getString(3) }));
					}
				}
			} else {
				dynamicHeaders = new String[] { "question_number", "question_type", "actual_question", "answer",
						"tolerance", "course_id" };
				while (rs.next()) {
					dynamicList.add(Arrays.asList(new String[] { rs.getString(1), rs.getString(2), rs.getString(3),
							rs.getString(4), rs.getString(5), rs.getString(6) }));
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		if (dynamicList.isEmpty()) {
			// System.out.println("dynamic list is empty");
			renderDynamicTable = false;
		}
	}

	void populateDynamicDataTable() {

		// Create <h:dataTable value="#{myBean.dynamicList}" var="dynamicItem">.
		try {
			HtmlDataTable dynamicDataTable = new HtmlDataTable();
			dynamicDataTable.setValueExpression("value",
					createValueExpression("#{dynamicDataTables.dynamicList}", List.class));
			dynamicDataTable.setVar("dynamicItem");
			dynamicDataTable.setBorder(1);
			dynamicDataTable.setCellpadding("1");
			dynamicDataTable.setCellspacing("0");
			dynamicDataTable.setStyleClass("dataTableEx");
			dynamicDataTable.setWidth("800");
			// System.out.println("dynamic list " + dynamicList.get(0));
			// Iterate over columns.
			for (int i = 0; i < dynamicList.get(0).size(); i++) {

				// Create <h:column>.
				HtmlColumn column = new HtmlColumn();
				dynamicDataTable.getChildren().add(column);

				// Create <h:outputText value="dynamicHeaders[i]"> for <f:facet
				// name="header"> of column.
				HtmlOutputText header = new HtmlOutputText();
				header.setValue(dynamicHeaders[i]);
				column.setHeader(header);

				// Create <h:outputText value="#{dynamicItem[" + i + "]}"> for
				// the
				// body of column.
				HtmlOutputText output = new HtmlOutputText();
				output.setValueExpression("value", createValueExpression("#{dynamicItem[" + i + "]}", String.class));
				column.getChildren().add(output);
			}

			// Add the datatable to <h:panelGroup
			// binding="#{myBean.dynamicDataTableGroup}">.
			dynamicDataTableGroup = new HtmlPanelGroup();
			dynamicDataTableGroup.getChildren().add(dynamicDataTable);
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	// Getters
	// -----------------------------------------------------------------------------------

	public HtmlPanelGroup getDynamicDataTableGroup() {
		// This will be called once in the first RESTORE VIEW phase.
		if (dynamicDataTableGroup == null) {
			loadDynamicList();
			// Preload dynamic list.
			populateDynamicDataTable(); // Populate editable datatable.
		}

		return dynamicDataTableGroup;
	}

	public List<List<String>> getDynamicList() {
		return dynamicList;
	}

	// Setters
	// -----------------------------------------------------------------------------------

	public void setDynamicDataTableGroup(HtmlPanelGroup dynamicDataTableGroup) {
		this.dynamicDataTableGroup = dynamicDataTableGroup;
	}
	// Helpers
	// -----------------------------------------------------------------------------------

	private ValueExpression createValueExpression(String valueExpression, Class<?> valueType) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getApplication().getExpressionFactory().createValueExpression(facesContext.getELContext(),
				valueExpression, valueType);
	}

	public static String[] getDynamicHeaders() {
		return dynamicHeaders;
	}

	public static void setDynamicHeaders(String[] dynamicHeaders) {
		DynamicDataTables.dynamicHeaders = dynamicHeaders;
	}

	public ArrayList<String> getViewCourseData() {
		return viewCourseData;
	}

	public void setViewCourseData(ArrayList<String> viewCourseData) {
		this.viewCourseData = viewCourseData;
	}

	public void setDynamicList(List<List<String>> dynamicList) {
		DynamicDataTables.dynamicList = dynamicList;
	}

	public boolean isRenderDynamicTable() {
		return renderDynamicTable;
	}

	public void setRenderDynamicTable(boolean renderDynamicTable) {
		this.renderDynamicTable = renderDynamicTable;
	}

	public String getSelectedTable() {
		return selectedTable;
	}

	public void setSelectedTable(String selectedTable) {
		this.selectedTable = selectedTable;
	}

	public ArrayList<String> getDatabaseTableList() {
		return databaseTableList;
	}

	public void setDatabaseTableList(ArrayList<String> databaseTableList) {
		this.databaseTableList = databaseTableList;
	}

	public DatabaseConnect getDbconnect() {
		return dbconnect;
	}

	public void setDbconnect(DatabaseConnect dbconnect) {
		this.dbconnect = dbconnect;
	}

}
