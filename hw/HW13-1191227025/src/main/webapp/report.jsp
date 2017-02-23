<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>OS usage</title>
	</head>
   <body  bgcolor="${sessionScope.pickedBgColor}">
   		<h2>OS usage</h2>
   		<p>Here are the results of OS usage in survey that we completed.</p>
   		<c:url value="/reportImage" var="imgUrl"/>
   		<img alt="OS usage" src="${imgUrl}">
   		
   		<a href="index.jsp">Main page</a>
   		
   </body>
</html>