<%@ page
	import="hr.fer.zemris.java.tecaj_13.model.VotingOption,java.util.List"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>
<body>
	<h1>Voting results</h1>
	<p>This are results of voting:</p>
	<table border="1" class="rez">
		<thead>
			<tr>
				<th>Option</th>
				<th>Votes count</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="option" items="${votes}">
				<tr>
					<td>${option.optionTitle}</td>
					<td>${option.votesCount}</td>
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
	<p>Winner option/options link:</p>
	<ol>
		<c:forEach var="option" items="${leading}">
			
			<li><a href="${option.optionLink}" target="_blank">${option.optionTitle}</a></li>
		</c:forEach>
	</ol>
	<p><a href="/webapp-baza/">Main page</a></p>
</body>
</html>