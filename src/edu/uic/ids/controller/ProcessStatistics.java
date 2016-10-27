package edu.uic.ids.controller;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.mysql.jdbc.Connection;

import edu.uic.ids.model.LoginBean;
import edu.uic.ids.model.StatisticsBean;

@ManagedBean(name = "processStatistics")
@SessionScoped
public class ProcessStatistics {
	String chartPath;
	StatisticsBean stats;
	LoginBean login;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, Object> map = context.getExternalContext().getSessionMap();
		stats = (StatisticsBean) map.get("statisticsBean");
		login = (LoginBean) map.get("loginBean");
	}

	public ProcessStatistics() {
		chartPath = null;
	}

	public String descriptiveStatistics() {
		String status = "STATSSUCCESS";
		stats.descriptiveStatistics();
		pieChart();
		return status;
	}

	public String pieChart() {
		// generate data from SQL query
		try {
			JFreeChart chart = null;
			FacesContext context = FacesContext.getCurrentInstance();
			String path = context.getExternalContext().getRealPath("/tmp");
			File out = null;
			String fileName = "Pie_Chart";
			Random random = new Random();
			int randomValue = random.nextInt(50) + 1;
			setChartPath("tmp/" + login.getUsername() + "_" + fileName + randomValue + ".png");
			out = new File(path + "/" + login.getUsername() + "_" + fileName + randomValue + ".png");
			chart = stats.createPieChart();
			ChartUtilities.saveChartAsPNG(out, chart, 600, 450);
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return "CHARTSUCCESS";
	}

	public String barChart() {
		// generate data from SQL query
		Connection conn = (Connection) Database.connectDB();
		ResultSet resultSet = null;
		try {
			String courseCode = Database.fetchCourseCodeStaff(login.getUsername(), conn);
			resultSet = Database.statsDataTimeSeriesChart(login.getUsername(), courseCode, conn);
			Result result = ResultSupport.toResult(resultSet);
			Object[][] sData = result.getRowsByIndex();
			JFreeChart chart = null;
			FacesContext context = FacesContext.getCurrentInstance();
			String path = context.getExternalContext().getRealPath("/tmp");
			File out = null;
			String fileName = "Bar_Chart";
			Random random = new Random();
			int randomValue = random.nextInt(50) + 1;
			setChartPath("tmp/" + login.getUsername() + "_" + fileName + randomValue + ".png");
			out = new File(path + "/" + login.getUsername() + "_" + fileName + randomValue + ".png");
			// System.out.println(chartPath);
			// System.out.println(path);
			chart = stats.createBarChart(sData);
			ChartUtilities.saveChartAsPNG(out, chart, 600, 450);
		} catch (IOException e) {
			// e.printStackTrace();
		}
		return "CHARTSUCCESS";
	}

	public String getChartPath() {
		return chartPath;
	}

	public void setChartPath(String chartPath) {
		this.chartPath = chartPath;
	}
}
