<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<head>
<meta charset="UTF-8">
<title>Login</title>
<%
	String msg = (String)request.getAttribute("msg");
	if(msg!=null){
		if(msg=="fail"){
			out.println("<script>alert('confirm id or password!');</script>");
		}else{
			out.println("<script>alert('hi!');</script>");

		}
		msg=null;
	} 
	
%>
</head>
<script type="text/javascript">
	 
	function fLogin(){
		var inputId = $("#inputId").val();
		var pwd1 = $("#inputPassword").val();
		if((inputId=="")){
			alert(" null value  ");
		}  else{
      		 $("#frm").submit();
        }

	}


</script>
<body>

<article class="container">
		<div class="page-header">
			<div class="col-md-6 col-md-offset-3">
				<h3>LOGIN</h3>
			</div>
		</div>
		<div class="col-sm-6 col-md-offset-3">
			<form role="form" action="doLogin" method="post" name="frm" id="frm">
				<div class="form-group">
					<label for="inputId">ID</label> <input type="text"
						class="form-control" id="inputId" name="member_id"
						placeholder="enter your ID" required> 
				</div>

				<div class="form-group">
					<label for="inputPassword">PASSWORD</label> 
					<input type="password" class="form-control" id="inputPassword" name="member_pw"
						placeholder="enter your password" required>
				</div>
				<div class="form-group text-center">
				
				<button type="button" id="login" class="btn btn-primary" onclick="fLogin()">
						LOGIN </button>
				<input type="button" class="btn btn-warning" onclick="location.href='goJoin'" value="GOJOIN"/>
				
				</div>
				</form>
					
				</div>
				</article>
	 
</body>
</html>