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
<title>Database Access</title>
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
					<h:form>
						<li><a href="index.jsp">Login Page</a></li>
						<li><h:commandLink id="logout" rendered="true"
								action="#{loginBean.dbLogout}" value="Database logout"></h:commandLink>
						</li>
					</h:form>
				</ul>
			</div>
		</div>
		<div class="header">
			<div class="container">
				<div style="height: 10px">
					<h2>Database Access view</h2>
				</div>
			</div>
		</div>
		<div class="content">
			<div class="container">
				<div class="main" align="center">
					<h:form enctype="multipart/form-data" id="assessentForm">
						<center>
							<div class="btn">
								<t:commandButton id="SubmitCreate"
									value="Create Application Tables"
									action="#{dynamicDataTables.createApplicationTables}"></t:commandButton>
							</div>
							<div class="btn">
								<h:commandButton id="SubmitDropOne" value="Drop Selected Table"
									action="#{dynamicDataTables.dropSelectedTable}"></h:commandButton>
							</div>
							<div class="btn">
								<h:commandButton id="SubmitDropAll"
									value="Drop Application Tables"
									action="#{dynamicDataTables.dropApplicationTables}"></h:commandButton>
							</div>
							<div class="btn">
								<h:commandButton id="SubmitViewTables" value="Display Table"
									action="#{dynamicDataTables.fetchTableData}"></h:commandButton>
							</div>
							<div class="btn">
								<h:commandButton id="Submit" value="Display Table List"
									action="#{dynamicDataTables.fetchTablesFromDatabase}"></h:commandButton>
							</div>
							<h:selectOneListbox id="selectedTable"
								value="#{dynamicDataTables.selectedTable}">
								<f:selectItems id="tableList"
									value="#{dynamicDataTables.databaseTableList}" />
							</h:selectOneListbox>
						</center>
					</h:form>
					<br>
					<hr />
					<h:messages style="color:red;list-style-type:none;"
						showSummary="false" showDetail="true" globalOnly="true">
					</h:messages>
					<br> <br>
				</div>
			</div>
		</div>
	</f:view>
	<div class="footer">
		<div class="container" align="right">&copy; Copyright QuizApp</div>
	</div>
</body>
</html>