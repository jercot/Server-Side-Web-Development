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
			<c:when test="${not empty posts}">
				<div class="pages">	
				<a href="${pageContext.request.contextPath}">Index</a> > 
				<a href="${pageContext.request.contextPath}/Forums/${requestScope.forumLink}">${requestScope.forumName}</a> >
				${threadName}
					<br><br>${firstPage!=null ? '<a href="'.concat(link).concat('page-').concat(currentPage-1).concat('">Prev</a>') : ''}
					<c:forEach var="item" items="${pages.pages}">
							${item}
					</c:forEach>
					${lastPage!=null ? '<a href="'.concat(link).concat('page-').concat(currentPage+1).concat('">Next</a>') : ''}
				</div><br>
				<c:forEach var="item" items="${posts}">
	 						<p class = "p-time">${item.time}</p>
					<div class = "post">
						<div class = "poster-info">
	 						${item.poster}<br>
	 						${item.postRegister}<br>
	 						${item.messageCount}
	 						</div>
	 					<div class = "post-info">${item.post}</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				Something went wrong. <a href="${pageContext.request.contextPath}">Refresh</a>
			</c:otherwise>
		</c:choose>
			${firstPage!=null ? '<a href="'.concat(link).concat('page-').concat(currentPage-1).concat('">Prev</a>') : ''}
		<c:forEach var="item" items="${pages.pages}">
				${item}
		</c:forEach>
			${lastPage!=null ? '<a href="'.concat(link).concat('page-').concat(currentPage+1).concat('">Next</a>') : ''}
		<c:choose>
			<c:when test="${not empty logged}">
				<form action="${current}" method = "POST">
				<textarea name="post" cols="100" rows="10">${error ? 'param.post' : ''}</textarea><br>
				<input type="submit" value="Submit Post"></form>
			</c:when>
		</c:choose>
	</div>
		<br><br>
</body>
</html>