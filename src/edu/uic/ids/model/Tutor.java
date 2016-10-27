package edu.uic.ids.model;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "tutor")
@SessionScoped
public class Tutor {
	@PostConstruct
	public void init() {
	}

	public String viewCourseAssessment() {
		String status = "VIEWCOURSEASSESSMENTTUTOR";
		return status;
	}

	public String viewRoster() {
		String status = "VIEWROSTERTUTOR";
		return status;
	}

}
