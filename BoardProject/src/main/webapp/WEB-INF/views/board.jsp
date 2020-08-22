<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>

<style>
	.mycontainer {
		margin: 0px auto;
		padding: 30px; 
		width: 1000px;
		height :600px;
		border: 1px solid #cccccc;
		background-color: WHITE;
	}
	 
     th,td {
        text-align: center;
     }

</style>

</head>
<body> 
  
	<div class="mycontainer"> 
	   
 	<form action="searchAll" method="post">
			<div class="form-inline" align="center"
				style="margin-top: 10px; margin-bottom: 40px;">
				<select id="option" name="option" class="form-control">
					<option value="all">전체</option> 
					<option value="b_num">번호</option> 
					<option value="b_title">제목</option>  
					<option value="b_writer">작성자</option> 
				</select>   
				<input type="text" class="form-control" name="search_desc" placeholder="검색어를 입력하세요." />
				<input type="submit" class="btn btn-success" value="검색"/> 
				
	 			 <c:if test="${sessionScope.member_id ne null}">	
            	 	 <a href="insertBoard">
            	 	 <input type="button" class="btn btn-info" value="글쓰기">
            	 	 </a>
                </c:if>  
                
			</div>
		</form> 
	  
		<table class="table">
			<tr class="active">
				<th align="center">번호</th>
				<th align="center">카테고리</th>
				<th align="center">제목</th>
				<th align="center">작성자</th> 
				<th align="center">작성일자</th> 
				<th align="center">조회수</th> 
				<th align="center">비고</th> 
			</tr>  
		<c:choose>
			<c:when test="${empty list}"> 
				<tr>
					<td colspan="6"><h4>no data</h4></td>				
				</tr> 
			</c:when> 
			
			<c:otherwise>
				<c:forEach var="board" items="${list}">
					<tr> 
						<td>${board.b_num}</td> 
						<td>${board.b_category}</td> 
						<td>${board.b_title }</td>
						<td>${board.b_writer }</td>
						<td>${board.b_reg }</td> 
						<td>${board.b_hit }</td> 
						<td> 
							<a href="showDetail?b_num=${board.b_num}">
							<input type="button" class="btn btn-primary btn-xs" value="상세보기"></a>
						</td> 
					</tr>  
				</c:forEach> 
			</c:otherwise>
		</c:choose>
		</table> 
		
		</div> 
			<div align="center">
				<ul class="pagination">
					<li><a href="#" aria-labe="Previous"> 
						<span aria-hidden="true">&laquo;</span>
					</a></li>
						<c:forEach var="i" begin="1" end="${pageCount}" step="1">
							<c:choose>
								<c:when test="${pageNum != i }">
									<li><a href="main?pageNum=${i}" ><b>${i}</b></a></li>
								</c:when> 
								  
								<c:otherwise>    
								<li><a href="main?pageNum=${i}"><b>${i}</b></a></li>	 
								</c:otherwise>
							</c:choose> 
						</c:forEach> 
					
					<li><a href="#" aria-labe="Previous"> 
					<span aria-hidden="true">&raquo;</span>
					</a></li>
				</ul>
			</div>
</body>
</html>