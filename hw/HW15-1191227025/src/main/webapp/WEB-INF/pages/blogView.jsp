<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Log in</title>

<style type="text/css">
.greska {
	font-family: fantasy;
	font-weight: 300;
	font-size: 0.9em;
	color: red;
}
</style>
</head>
<header>
</header>

<c:choose>
	<c:when test="${!errors.isEmpty()}">
	</c:when>

	<c:otherwise>
	<body>
		<p><b> Author: </b> <c:out value="${author.nick}"/></p>
		<c:choose>
		<c:when test="${entries==null || entries.isEmpty()}">
		Doesnt exsists, write it down!
		</c:when>
		<c:otherwise>
		<c:forEach var="entry" items="${author.entries}">
		<p>Headline:</p>
		<a href="../entry/show?id=<c:out value='${entry.id}'/>"><c:out value="${entry.title}"/></a>
		<c:if test="${sessionScope.get('current.user')!=null&&(author.nick==sessionScope.get('current.user.nick'))}">
			<a href="/blog/servleti/author/<c:out value='${author.nick}'/>/edit?id=<c:out value='${entry.id}'/>">   Edit entry</a>
		</c:if>
		
		</c:forEach>
		</c:otherwise>
		</c:choose>
	</body>
	
	<c:if test="${sessionScope.get('current.user')!=null&&(author.nick==sessionScope.get('current.user.nick'))}">
	<h5><a href="/blog/servleti/author/<c:out value='${author.nick}'/>/new">New Entry</a></h5>
	</c:if>
	
 	<h5><a href="../main">Main page</a></h5>
	
		<c:if test="${sessionScope.get('current.user')==null}" >
			<body>
				<h4>
					<a href="../register">Register!</a>
				</h4>
				<p>${sessionScope.get('current.user.nick')}</p>
			</body>
		</c:if>

		
	</c:otherwise>
</c:choose>
</html>