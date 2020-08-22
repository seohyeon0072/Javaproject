<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>  
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>글 수정</title>
<link href="resources/css/bootstrap.css" rel="stylesheet" />
</head>
 
<body>
	<jsp:include page="menu.jsp" />
	<form action="updateBoard" method="post">
	<div class="container" align="center"> 
		<div align="left" 
			style="width: 700px; border: 1px solid #eeeeee; padding: 20px; background-color: WHITE; margin-bottom : 99px;">
			<h3>상세보기</h3>
			<hr />
				
			<div class="form-inline" style="margin-bottom: 5px">
				<label style="width: 75px">글번호</label> 
				<input type="text" name="b_num" value="${list.b_num}" style="width: 320px" class="form-control" readonly />
			</div> 
<div class="form-inline" style="margin-bottom: 5px">
				<label style="width: 75px">카테고리</label> 
				<input type="text" name="b_category" value="${list.b_category}" style="width: 320px" class="form-control" />
			</div> 
			<div class="form-inline" style="margin-bottom: 5px">
				<label style="width: 75px">제목</label> 
				<input type="text" name="b_title" value="${list.b_title}" style="width: 320px" class="form-control"  />
			</div>

			<div class="form-inline" style="margin-bottom: 5px">
					<label style="width: 75px">작성자</label> 
					<input type="text" name="b_writer" value="${list.b_writer}" style="width: 320px" class="form-control" readonly />
			</div> 
			
			<div class="form-inline" style="margin-bottom: 5px">
					<label style="width: 75px">조회수</label> 
					<input type="text" name="b_hit" value="${list.b_hit}" style="width: 100px" class="form-control" readonly />
			</div> 
			 
			   
			<div class="form-inline" style="margin-bottom: 5px">
				<label style="width: 75px">내용</label> 
				<textarea id="txt" name="b_contents" style="width: 400px; resize: none;"
					class="form-control" rows="5" placeholder="내용을 입력하세요.">${list.b_contents}</textarea>
			</div>
			 
			<div class="form-inline" style="margin-bottom: 5px">
				<label style="width: 75px">작성일자</label> 
				<input type="text" name="b_reg" value="${list.b_reg}" style="width: 320px" class="form-control" readonly />
			</div>
			<hr />

			<div class="form-inline" style="margin-bottom: 10px" align="center">
	 			 <c:if test="${sessionScope.member_id eq list.b_writer}">	 
					<input type="submit" class="btn btn-success"  value="수정" /> 
					<a href="deleteBoard?b_num=${list.b_num}"><input type="button" class="btn btn-danger" value="삭제"></a>
                </c:if>   
				<a href="home"><input type="button" class="btn btn-info" value="뒤로가기"></a>
			</div>   
				</div>
			</div>  
		</form>	   
		 
</body>

 
</html>