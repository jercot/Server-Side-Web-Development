<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css" />
</head>
<body>
	<br>
	<div class="center">
		<c:choose>
			<c:when test="${not empty logged}">
				<a href="${pageContext.request.contextPath}/Logout">Log Out</a>
				<a href="${pageContext.request.contextPath}/Members">Members</a><br><br>
			</c:when>
			<c:otherwise>
				<a href="${pageContext.request.contextPath}/Account">Login/Create Account</a>
				<a href="${pageContext.request.contextPath}/Account">Members</a><br><br>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${not empty threads}">
				<a href="${pageContext.request.contextPath}">Index</a> > ${forumName} <br><br>
				<c:forEach var="item" items="${threads}">
					<div class = "post">
	 					<a href="${pageContext.request.contextPath}/Threads/${item.linkName}.${item.id}/page-1">${item.name}</a><br> 
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				No threads to display. <a href="${pageContext.request.contextPath}">Homepage</a>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${not empty logged}">
			<br>
				<form action="${pageContext.request.contextPath}/CreateThread/${forumLink}.${forumId}" method = "GET">
				<input type="submit" value="New Thread"></form>
			</c:when>
		</c:choose>
	</div>
</body>
</html>