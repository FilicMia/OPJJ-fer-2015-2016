
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date,java.util.Calendar"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%!
	/**
		Method prints on the {@code out} the time passes from 
		the time when application was started.
		It writes it in format "D days, H hours, M minuts, S seconds"
		
		@param out {@link javax.servlet.jsp.JspWriter} on which the time 
			should be written.
		@throws java.io.IOException throws if the writing of the time went wrong.
		
	*/
	public void writeTimeDiff(javax.servlet.jsp.JspWriter out)
			throws java.io.IOException {
		long endTime = System.currentTimeMillis();
		long diff = endTime
				- (long) getServletContext().getAttribute("startTime");
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);

		out.print(diffDays + " days, ");
		out.print(diffHours + " hours, ");
		out.print(diffMinutes + " minutes, ");
		out.print(diffSeconds + " seconds.");

	}%>


<html>
<head>
<title>App info</title>
</head>
<body bgcolor="${sessionScope.pickedBgColor}">
	<p style="font-size: 20px">Application is running for:</p>
	<p style="font-size: 20px">
		<%
			writeTimeDiff(out);
		%>
	</p>
	<a href="index.jsp">Main page</a>
</body>
</html>