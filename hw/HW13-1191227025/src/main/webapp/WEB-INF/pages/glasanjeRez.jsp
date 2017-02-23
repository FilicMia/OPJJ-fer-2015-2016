<%@ page
	import="hr.fer.zemris.java.hw13.webapp2.jBeans.Band,java.util.List"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%!/**Displays links for winner bands song.
	@param out output writer.
	@param reports list containing bands with its vote count.
	@throws java.io.IOException if writing the link on out colapses
	**/
	
	public void writeWinnersLink(javax.servlet.jsp.JspWriter out,
			List<Band> reports) throws java.io.IOException {
		if (reports.isEmpty())
			return;
		
		out.print("<ol>");
		int score = reports.get(0).getVote();
		int counter = 0;

		while (true) {
			Band band = reports.get(counter);
			if(band.getVote() != score) break;
			
			out.print("<li>"+
					"<a href="+band.getLink()+
					" target=\"_blank\">"+band.getName()+"</a></li>");
			counter++;
		}
		out.print("</ol>");

	}%>

<html>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>
<body bgcolor="${sessionScope.pickedBgColor}">
	<h1>Voting results</h1>
	<p>This are results of voting:</p>
	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="band" items="${votes}">
				<tr>
					<td>${band.name}</td>
					<td>${band.vote}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<h2>Graphical overview:</h2>
	<c:url value="/glasanje-grafika" var="imgUrl" />
	<img alt="Voting results" src="${imgUrl}">


	<h2>Results in XLS form:</h2>
	<p>
		Download of XLS is available here <a href="glasanje-xls">results.xls</a>
	</p>
	<h2>Other</h2>
	<p>Winner band/bands song:</p>
	<% writeWinnersLink(out,(List<Band>)session.getAttribute("votes"));%>
	
	<p><a href="index.jsp">Main page</a></p>
</body>
</html>