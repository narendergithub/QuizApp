<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
</head>
<body>
	<f:view>
		<div style="color: #B05C1B; background-color: #698590">
			<f:verbatim>
				<center>
					<h1>IDS517 Enterprise Application Development</h1>
					<h1>Online Quiz Application - f15g109</h1>
				</center>
			</f:verbatim>
			<center>
				<h:form id="loginpage">
					<h:panelGrid columns="1" border="1" cellpadding="0" cellspacing="2"
						width="500"
						style="text-align: center;color:#47430F;font-size:25px">
						<f:facet name="header">
							<h:outputText value="Group Members"
								style="font-weight:bold;color:#B05C1B"></h:outputText>
						</f:facet>
						<h:outputLabel value=" Premnath Ramanathan " />
						<h:outputLabel value=" Narender Tankasala " />
						<h:outputLabel value=" Dinesh Kumaar " />
					</h:panelGrid>
					<br />
					<h:commandLink action="dbLogin.jsp" style="font-size:25px">Database Login</h:commandLink>
					<br />
					<h2>Project Documents</h2>
					<h:commandLink action="documentation.jsp" style="font-size:25px">Programmer's Documentation</h:commandLink>
					<br />
				</h:form>
			</center>
		</div>
	</f:view>
</body>
</html>