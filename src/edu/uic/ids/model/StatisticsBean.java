package edu.uic.ids.model;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.math3.stat.StatUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.jdbc.JDBCXYDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.date.MonthConstants;

import com.mysql.jdbc.Connection;

import edu.uic.ids.controller.Database;

@ManagedBean(name = "statisticsBean")
@SessionScoped
public class StatisticsBean {
	boolean renderIndexTimeSeriesChart;
	boolean renderPieChart;
	ArrayList<Double> descStats;
	String selectedQuiz;
	double minValue;
	double maxValue;
	double mean;
	double variance;
	double std;
	double median;
	double q1;
	double q3;
	double iqr;
	double range;
	boolean renderStats;
	boolean renderBarChart;

	public StatisticsBean() {
		renderIndexTimeSeriesChart = false;
		renderPieChart = false;
		renderBarChart = false;
		descStats = new ArrayList<>();
		renderStats = false;
	}

	public void init() {

	}

	public String analyzeScores() {
		String status = "ANALYZESCORES";
		renderStats = false;
		renderBarChart = false;
		return status;
	}

	public JFreeChart createPieChart() {
		// create a dataset...
		double x[] = null;
		renderPieChart = true;
		if (renderBarChart) {
			renderBarChart = false;
		}
		Connection conn = (Connection) Database.connectDB();
		x = Database.descriptiveStatistics(conn, selectedQuiz);
		DefaultPieDataset data = new DefaultPieDataset();
		int groupOne = 0;
		int groupTwo = 0;
		int groupThree = 0;
		int groupFour = 0;
		double percent = 0.0;

		for (int i = 0; i < x.length; i++) {
			percent = x[i] / 20.0;
			if (percent > 0.0 && percent <= 25.0)
				groupOne++;
			if (percent > 25.0 && percent <= 50.0)
				groupTwo++;
			if (percent > 50.0 && percent <= 75.0)
				groupThree++;
			if (percent > 75.0 && percent <= 100.0)
				groupFour++;
		}

		data.setValue("0%-25%", groupOne);
		data.setValue("25%-50%", groupTwo);
		data.setValue("50%-75%", groupThree);
		data.setValue("75%-100%", groupFour);

		JFreeChart chart = ChartFactory.createPieChart("Pie Chart", data, true, true, false);
		return chart;
	}

	public JFreeChart createBarChart(Object[][] sData) {
		if (isRenderBarChart()) {
			renderBarChart = false;
		} else {
			renderBarChart = true;
		}
		if (renderPieChart || renderStats) {
			renderPieChart = false;
			renderStats = false;
		}
		// System.out.println(rendeBarChart);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		JFreeChart chart = null;
		try {
			String str1 = null;
			String str2 = null;
			String temp1 = null;
			double int1 = 0.0;
			for (int i = 0; i < sData.length; i++) {
				str1 = sData[i][0].toString();
				str2 = sData[i][1].toString();
				temp1 = sData[i][2].toString();
				int1 = Double.parseDouble(temp1);
				// System.out.println(int1 + " " + str1 + " " + str2);
				dataset.addValue(int1, str1, str2);
			}
			// System.out.println("dataset " + dataset.getRowCount());
			chart = ChartFactory.createBarChart3D("Bar Chart", "Category", "Value", dataset, PlotOrientation.VERTICAL,
					true, true, false);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return chart;
	}

	public String descriptiveStatistics() {
		String status = "STATSSUCCESS";
		Connection conn = null;
		try {
			// Note: Apache Commons Math StatsUtils static class here - could
			// use
			// alternate
			double values[] = null; // populate from db table using SQL or
									// similar
			conn = (Connection) Database.connectDB();
			values = Database.descriptiveStatistics(conn, selectedQuiz);
			if (renderBarChart) {
				renderBarChart = false;
			}
			renderPieChart = false;
			renderStats = true;
			// create class to hold descriptive statistics, and ArrayList of
			// that
			// class
			// populate java List with results to display in table
			setMinValue(StatUtils.min(values));
			setMaxValue(StatUtils.max(values));
			setMean(StatUtils.mean(values));
			setVariance(StatUtils.variance(values, getMean()));
			setStd(Math.sqrt(variance));
			setMedian(StatUtils.percentile(values, 50.0));
			setQ1(StatUtils.percentile(values, 25.0));
			setQ3(StatUtils.percentile(values, 75.0));
			setIqr(getQ3() - getQ1());
			setRange(getMaxValue() - getMinValue());
			descStats.add(minValue);
			descStats.add(maxValue);
			descStats.add(mean);
			descStats.add(std);
			descStats.add(median);
			descStats.add(q1);
			descStats.add(q3);
			descStats.add(iqr);
			descStats.add(range);
		} catch (Exception ex) {
		}
		return status;
	}

	public static JFreeChart createTimeSeriesChart() {
		// here we just populate a series with random data...
		TimeSeries series = new TimeSeries("Random Data");
		Day current = new Day(1, MonthConstants.JANUARY, 2001);
		for (int i = 0; i < 100; i++) {
			series.add(current, Math.random() * 100);
			current = (Day) current.next();
		}
		XYDataset data = new TimeSeriesCollection(series);
		JFreeChart chart = ChartFactory.createTimeSeriesChart("Time Series Chart", "Date", "Close", data, true, true,
				false);
		return chart;
	}

	public static JFreeChart createTimeSeriesChart(String title, String ticker, String[] tDate, double[] values) {
		// here we just populate a series with random data...
		TimeSeries series = new TimeSeries(title);
		Day d;
		for (int i = 0; i < tDate.length; i++) {
			String year = tDate[i].substring(0, 4);
			String month = tDate[i].substring(5, 7);
			String day = tDate[i].substring(8, 10);
			d = new Day(Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year));
			series.add(d, values[i]);
		}
		XYDataset data = new TimeSeriesCollection(series);
		JFreeChart chart = ChartFactory.createTimeSeriesChart("Time Series Chart: " + ticker, // chart
																								// title
				"Date", // x axis label
				"Close: " + ticker, // y axis label
				data, // data
				true, true, false);
		return chart;
	}

	public JFreeChart createIndexTimeSeriesChart(String title, String ticker, double[] values) {

		JFreeChart chart = null;
		try {
			renderIndexTimeSeriesChart = true;
			// System.out.println("indexTimeseriesChart invoked " +
			// renderIndexTimeSeriesChart);
			XYSeries series = new XYSeries(title);
			for (int i = 0; i < values.length; i++) {
				series.add(i, values[i]);
			}
			XYSeriesCollection dataset = new XYSeriesCollection();
			dataset.addSeries(series);
			chart = ChartFactory.createXYLineChart(title, // chart title
					"Index", // x axis label
					ticker, // y axis label
					dataset, // data
					PlotOrientation.VERTICAL, true, // include legend
					true, // tooltips
					false // urls
			);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return chart;
	}

	public static JFreeChart createIndexScatterplotChart(String title, String tickerX, String tickerY, double[] valuesX,
			double[] valuesY) {

		XYSeries series = new XYSeries(title);
		for (int i = 0; i < valuesX.length; i++) {
			series.add(valuesX[i], valuesY[i]);
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);

		JFreeChart chart = ChartFactory.createXYLineChart(title, // chart title
				tickerX, // x axis label
				tickerY, // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
		);
		return chart;
	}

	public static JFreeChart createScatterplotChart() {
		// here we just populate a series with random data...

		XYSeries series1 = new XYSeries("First");
		series1.add(1.0, 1.0);
		series1.add(2.0, 4.0);
		series1.add(3.0, 3.0);
		series1.add(4.0, 5.0);
		series1.add(5.0, 5.0);
		series1.add(6.0, 7.0);
		series1.add(7.0, 7.0);
		series1.add(8.0, 8.0);
		XYSeries series2 = new XYSeries("Second");
		series2.add(1.0, 5.0);
		series2.add(2.0, 7.0);
		series2.add(3.0, 6.0);
		series2.add(4.0, 8.0);
		series2.add(5.0, 4.0);
		series2.add(6.0, 4.0);
		series2.add(7.0, 2.0);
		series2.add(8.0, 1.0);
		XYSeries series3 = new XYSeries("Third");
		series3.add(3.0, 4.0);
		series3.add(4.0, 3.0);
		series3.add(5.0, 2.0);
		series3.add(6.0, 3.0);
		series3.add(7.0, 6.0);
		series3.add(8.0, 3.0);
		series3.add(9.0, 4.0);
		series3.add(10.0, 3.0);
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);

		JFreeChart chart = ChartFactory.createXYLineChart("Line Chart Demo 2", // chart
																				// title
				"X", // x axis label
				"Y", // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
		);

		return chart;
	}

	public static JFreeChart createXYChart(StatisticsBean statsBean) {
		// here we just populate a series with random data or SQL SELECT
		// connect.setUserInfo(statsBean);
		Connection con = (Connection) Database.connectDB();
		JDBCXYDataset xy = null;
		String sqlQuery = null;
		// sqlQuery = "SELECT surfaceArea, Population FROM country ORDER BY
		// surfaceArea";
		sqlQuery = "SELECT Population, GNP FROM country ORDER BY Population";
		JFreeChart chart = null;
		try {
			xy = new JDBCXYDataset(con);
			xy.executeQuery(sqlQuery);

			// chart = ChartFactory.createXYLineChart(
			// title, xAxisLabel, yAxisLabel,
			// dataset, orientation, legend, tooltips, urls)
			chart = ChartFactory.createXYLineChart(
					// "Country XY Line Chart", "Surface Area", "Population",
					"Country", "Population", "GNP", xy, PlotOrientation.VERTICAL, true, false, false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		// could save x y values into array
		return chart;
	}

	public boolean isRenderIndexTimeSeriesChart() {
		return renderIndexTimeSeriesChart;
	}

	public void setRenderIndexTimeSeriesChart(boolean renderIndexTimeSeriesChart) {
		this.renderIndexTimeSeriesChart = renderIndexTimeSeriesChart;
	}

	public boolean isRenderPieChart() {
		return renderPieChart;
	}

	public void setRenderPieChart(boolean renderPieChart) {
		this.renderPieChart = renderPieChart;
	}

	public ArrayList<Double> getDescStats() {
		return descStats;
	}

	public void setDescStats(ArrayList<Double> descStats) {
		this.descStats = descStats;
	}

	public String getSelectedQuiz() {
		return selectedQuiz;
	}

	public void setSelectedQuiz(String selectedQuiz) {
		this.selectedQuiz = selectedQuiz;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getVariance() {
		return variance;
	}

	public void setVariance(double variance) {
		this.variance = variance;
	}

	public double getStd() {
		return std;
	}

	public void setStd(double std) {
		this.std = std;
	}

	public double getMedian() {
		return median;
	}

	public void setMedian(double median) {
		this.median = median;
	}

	public double getQ1() {
		return q1;
	}

	public void setQ1(double q1) {
		this.q1 = q1;
	}

	public double getQ3() {
		return q3;
	}

	public void setQ3(double q3) {
		this.q3 = q3;
	}

	public double getIqr() {
		return iqr;
	}

	public void setIqr(double iqr) {
		this.iqr = iqr;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}

	public boolean getRenderStats() {
		return renderStats;
	}

	public void setRenderStats(boolean renderStats) {
		this.renderStats = renderStats;
	}

	public boolean isRenderBarChart() {
		return renderBarChart;
	}

	public void setRenderBarChart(boolean renderBarChart) {
		this.renderBarChart = renderBarChart;
	}

}
