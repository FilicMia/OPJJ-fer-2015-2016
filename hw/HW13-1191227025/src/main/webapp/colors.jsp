<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
 	<head>
		<title>Background color chooser</title>
	</head>
	<body bgcolor="${sessionScope.pickedBgColor}">
		
		<ul>
			<li><a href="/webapp2/setcolor?color=cyan">Cyan</a></li>
			<li><a href="/webapp2/setcolor?color=red">Red</a></li>
			<li><a href="/webapp2/setcolor?color=white">White</a></li>
			<li><a href="/webapp2/setcolor?color=green">Green</a></li><br/>
			<li><a href="index.jsp">Main page</a></li>
		</ul>
		
	</body>
</html>