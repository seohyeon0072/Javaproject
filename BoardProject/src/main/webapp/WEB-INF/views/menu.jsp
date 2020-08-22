<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<!-- 로그인 세션 -->
	<c:choose>
	<c:when test="${msg=='success' or sessionScope.member_id ne null }">
		${sessionScope.member_id} 님 하이
	
	<form action="doLogout">
		<input type="submit" value="logout" /> <input type="hidden" name=""
			value="${sessionScope.member_id}" />
	</form>

	<form action="goUpdate">
		<input type="submit" value="update" /> <input type="hidden" name=""
			value="${sessionScope.member_id}" />
	</form>
	</c:when>
	<c:when test="${sessionScope.member_id eq null}">
		 
		<input type="button" class="btn btn-warning" onclick="location.href='goJoin'" value="가입하기"/>
		<input type="button" class="btn btn-warning" onclick="location.href='goLogin'" value="로그인하기"/>
		 
	</c:when>
	</c:choose>
</body>
</html>