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
	<form action="DatabaseRead" method="POST">
		<input type="submit" value="Read names from database" />
	</form>
	<div class="center">
		${initParam.name}<br>
		<c:choose>
			<c:when test="${not empty names}">
				<c:forEach begin="${list.firstIndex}" end="${list.lastIndex}" var="item" items="${sessionScope.names}">
  						<p class = "p-time">${item.time}</p>
					<div class = "post">
						<div class = "poster-info">
  							${item.poster}<br>
  							${item.postRegister}<br>
  							${item.messageCount}
  							</div>
  							<div class = "post-info">
  								${item.post}
  							</div>
 							</div>
				</c:forEach>
				<c:choose>
					<c:when test="${list.firstIndex gt 0}">
						<a href="?choice=prev&max=${fn:length(sessionScope.names)}">Prev</a>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${list.lastIndex lt fn:length(sessionScope.names)}">
						<a href="?choice=next&max=${fn:length(sessionScope.names)}">Next</a>
					</c:when>
				</c:choose>
			</c:when>
		<c:otherwise>
							Nothing read from database yet.
				</c:otherwise>
		</c:choose>
		<form action="DatabaseWrite" method="POST">
			<input type="text" name="name"> <input type="submit" value="Add User">
		</form>
	</div>
</body>
</html>