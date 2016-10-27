package edu.uic.ids.model;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="teachingAssistant")
@SessionScoped
public class Ta {
	public String uploadTypeTA;

	@PostConstruct
	public void init() {
	}

	public String viewAsStudent() {
		String status = "VIEWSTUDENTTA";
		return status;
	}

	public String getUploadTypeTA() {
		return uploadTypeTA;
	}

	public void setUploadTypeTA(String uploadTypeTA) {
		this.uploadTypeTA = uploadTypeTA;
	}

}
