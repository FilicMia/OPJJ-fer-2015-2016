<%@page import="hr.fer.zemris.java.tecaj_13.model.Poll"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
  <body>

  <h1>Available polls are:</h1>
	<p>Take a part in specific poll by clicking on the link:</p>
	<ol>
		<c:forEach var="poll" items="${polls}">
				<li><a href="glasanje?pollID=${poll.id}">${poll.title}</a></li>
		</c:forEach>
	</ol>
  </body>
</html>