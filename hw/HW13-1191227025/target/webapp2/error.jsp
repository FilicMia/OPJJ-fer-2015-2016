<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
 	<head>
		<title>Background color chooser</title>
	</head>
	<body bgcolor="${sessionScope.pickedBgColor}">
		
		<p><span style = "color:red">Error occurred!</span></p>
		
		<!--Error message is saved in session under key errorMessage-->
		<p>${errorMessage}</p>
		
		<a href="index.jsp">Main page</a>
	</body>
</html>