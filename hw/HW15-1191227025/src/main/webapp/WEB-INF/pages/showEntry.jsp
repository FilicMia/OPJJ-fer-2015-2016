<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Log in</title>

<style type="text/css">
.greska {
	font-family: fantasy;
	font-weight: bold;
	font-size: 0.9em;
	color: red;
}
</style>
</head>
<header>
</header>

<c:choose>
	<c:when test="${errors!=null&&!errors.isEmpty()}">
		<c:forEach var="err" items="${errors}">
		<c:out value="${err}" />
		</c:forEach>
	</c:when>

	<c:otherwise>
		<p>
		<h3>

			<b>Title:</b>
			<c:out value="${entry.title}" />
		</h3>
		<body>
			<c:out value="${entry.text}" />
		</body>
		<body>
			<p>
				<b>Author: </b>
				<c:out value="${entry.creator.nick}" />
			</p>
			<p>
				<b>Comments: </b>
			<ul>
				<c:forEach var="comm" items="${entry.comments}">
					<li><p>
				[User: <a href="mailto:<c:out value='${comm.usersEMail}'/> ">
							<c:out value="${comm.usersEMail}" /></a>]
						&nbsp;&nbsp;<c:out value="${comm.message}" />
						</p><p>
							Posted on:
							<c:out value="${comm.postedOn }" />
						</p></li>
				</c:forEach>
			</ul>
			

			<p>Add comment:</p>
			<p>
				<jsp:include page="/WEB-INF/pages/newComment.jsp" />
			</p>

		</body>
	</c:otherwise>
</c:choose>
<ul>
	<li><a href="/blog/servleti/author/<c:out value='${entry.creator.nick}'/> ">Blog of the user <b><c:out value="${entry.creator.nick}"/></b></a></li>
	<li><a href="/blog/servleti/main">Main site</a></li>

</ul>
</html>