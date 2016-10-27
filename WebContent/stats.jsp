<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>
<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Statistics</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="Description" lang="en" content="Online Quiz Application">
<meta name="author" content="F15g109">
<meta name="robots" content="index, follow">

<!-- icons -->
<link rel="apple-touch-icon" href="CSS/assets/img/apple-touch-icon.png">
<link rel="shortcut icon" href="CSS/favicon.ico">

<!-- Override CSS file - add your own CSS rules -->
<link type="text/css" rel="stylesheet" href="CSS/assets/css/styles.css">
</head>
<body>
	<%
		if (session.getAttribute("login") == null)
			response.sendRedirect("dbLogin.jsp");
	%>
	<f:view>
		<div class="header">
			<div class="container">
				<img align="left" src="QuizApp.png" width="200px" height="110px">
				<h1 class="header-heading" align="justify"
					style="color: #ffffff; font-family: Segoe UI">QuizApp</h1>
				<h5 align="right">IDS517 Enterprise Application Development</h5>
				<h5 align="right">Online Quiz Application - f15g109</h5>
			</div>
		</div>
		<div class="nav-bar">
			<div class="container">
				<ul class="nav">
					<h:form id="links">
						<li><h:commandLink id="logout" rendered="true"
								action="#{loginBean.logout}">logout</h:commandLink></li>
						<li><h:commandLink id="backButton" rendered="true"
								action="#{actionBeanFile.back}">Back</h:commandLink></li>
					</h:form>
				</ul>
			</div>
		</div>
		<div class="header">
			<div class="container">
				<div style="height: 10px">
					<h2>Statistics</h2>
				</div>
			</div>
		</div>
		<div class="content">
			<div class="container">
				<div class="main" align="center">
					<h:form>
						<h:commandButton value="Overall performance"
							action="#{processStatistics.barChart }"></h:commandButton>
						<h:commandButton value="Assessment analysis"
							action="#{actionBeanFile.viewQuizForStats}"></h:commandButton>
					</h:form>
					<hr />
					<h:form id="descriptiveStats"
						rendered="#{statisticsBean.renderStats }">
						<h:selectOneListbox id="quizListbox"
							value="#{statisticsBean.selectedQuiz}"
							rendered="#{actionBeanFile.renderassessment}">
							<f:selectItems id="quizlist"
								value="#{actionBeanFile.viewGradesList}" />
						</h:selectOneListbox>
						<h:commandButton id="viewQuizbutton"
							rendered="#{actionBeanFile.renderTakeQuiz}"
							disabled="#{actionBeanFile.takeQuizDisabled}"
							action="#{processStatistics.descriptiveStatistics}"
							value="View Stats"></h:commandButton>
					</h:form>
					<hr />
					<h:form enctype="multipart/form-data"
						rendered="#{statisticsBean.renderPieChart }">
						<h:graphicImage value="#{processStatistics.chartPath }"
							rendered="#{statisticsBean.renderPieChart }" height="450"
							width="600" alt="NoPieChart" />
					</h:form>
					<h:form rendered="#{statisticsBean.renderBarChart}">
						<h:graphicImage value="#{processStatistics.chartPath }"
							rendered="#{statisticsBean.renderBarChart}" height="450"
							width="600" alt="NoBarChart" />
					</h:form>
					<h:form id="stats" rendered="#{statisticsBean.renderStats}">
						<h:panelGrid columns="2" border="1" cellpadding="0"
							rendered="#{statisticsBean.renderStats}" cellspacing="2">
							<f:facet name="header">
								<h:panelGroup style="display:block; text-align:center">
									<h:outputText value="Descriptive Statistics"
										style="font-weight:bold;" />
								</h:panelGroup>
							</f:facet>
							<h:outputLabel style="font-weight:bold;" value="5-number Summary" />
							<h:outputText value="" />
							<h:outputLabel value="minValue" />
							<h:outputText value="#{statisticsBean.minValue}" />
							<h:outputLabel value="Q1" />
							<h:outputText value="#{statisticsBean.q1}" />
							<h:outputLabel value="Q2/median" />
							<h:outputText value="#{statisticsBean.median}" />
							<h:outputLabel value="Q3" />
							<h:outputText value="#{statisticsBean.q3}" />
							<h:outputLabel value="maxValue" />
							<h:outputText value="#{statisticsBean.maxValue}" />
							<h:outputLabel style="font-weight:bold;"
								value="Measures of central tendency:" />
							<h:outputText value="" />
							<h:outputLabel value="mean" />
							<h:outputText value="#{statisticsBean.mean}" />
							<h:outputLabel value="median" />
							<h:outputText value="#{statisticsBean.median}" />
							<h:outputLabel style="font-weight:bold;"
								value="Measures of variation:" />
							<h:outputText value="" />
							<h:outputLabel value="variance" />
							<h:outputText value="#{statisticsBean.variance}" />
							<h:outputLabel value="SD" />
							<h:outputText value="#{statisticsBean.std}" />
							<h:outputLabel value="InterQuartileRange" />
							<h:outputText value="#{statisticsBean.iqr}" />
							<h:outputLabel value="Range" />
							<h:outputText value="#{statisticsBean.range}" />
						</h:panelGrid>
					</h:form>
					<h:messages style="color:red;list-style-type:none;"
						showSummary="false" showDetail="true" globalOnly="true">
					</h:messages>
					<br> <br>
				</div>
			</div>
		</div>
		<div class="footer">
			<div class="container" align="right">&copy; Copyright QuizApp</div>
		</div>
	</f:view>
</body>
</html>