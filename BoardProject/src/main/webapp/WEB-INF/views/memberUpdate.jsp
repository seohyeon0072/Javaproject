<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<meta charset="EUC-KR">
<title>Login</title>

<script type="text/javascript">
	 
	function fCheck() {
		var inputName = $("#inputName").val();
		var inputMobile = $("#inputMobile").val();
		var InputEmail = $("#InputEmail").val();
		
		var pwd1 = $("#inputPassword").val();
		var pwd2 = $("#inputPasswordCheck").val();
		
		if(confirm("회원 수정 하시겠습니까?")){
			if((inputName=="")||(inputMobile=="")||(InputEmail=="")||(pwd1=="")){
				alert("공백이 있습니다.");
			}else if (pwd1 != pwd2) {
				alert("비밀번호가 일치하지 않습니다. 비밀번호를 재확인해주세요.");
			} else{
	      		 $("#frm").submit();
	        }
	    }
		
	};
 	function deleteCk(){
 		if(confirm("회원 삭제 하시겠습니까?")){
 			 $("#dodelete").submit();
 	 		}
 	 	}
</script>

</head>

<body>
	<article class="container">
		<div class="page-header">
			<div class="col-md-6 col-md-offset-3">
				<h3>회원정보 수정</h3>
			</div>
		</div>
		<div class="col-sm-6 col-md-offset-3">
			<form role="form" action="doUpdate" method="post" name="frm" id="frm">
				<div class="form-group">
					<label for="inputId">아이디</label> <input type="text"
						class="form-control" id="inputId" name="member_id"
						placeholder="아이디를 입력해 주세요" value="${info.member_id}" required
						readonly>
				</div>

				<div class="form-group">
					<label for="inputPassword">비밀번호</label> <input type="password"
						class="form-control" id="inputPassword" name="member_pw"
						value="${info.member_pw}" placeholder="비밀번호를 입력해주세요" required>
				</div>
				<div class="form-group">
					<label for="inputPasswordCheck">비밀번호 확인</label> <input
						type="password" class="form-control" id="inputPasswordCheck"
						placeholder="비밀번호 확인을 위해 다시한번 입력 해 주세요" required>
				</div>

				<div class="form-group">
					<label for="inputName">이름</label> <input type="text"
						class="form-control" id="inputName" name="member_name"
						value="${info.member_name}" placeholder="이름을 입력해 주세요" required>
				</div>
				<div class="form-group">
					<label for="inputMobile">휴대폰 번호</label> <input type="tel"
						class="form-control" id="inputMobile" name="member_phone"
						value="${info.member_phone}" placeholder="휴대폰번호를 입력해 주세요" required>
				</div>
				<div class="form-group">
					<label for="InputEmail">이메일 주소</label> <input type="email"
						class="form-control" id="InputEmail" name="member_email"
						value="${info.member_email}" placeholder="이메일 주소를 입력해주세요" required>
				</div>

				<div class="form-group text-center">

					<button type="button" id="modify" class="btn btn-primary"
						onclick="fCheck()">
						회원수정 <i class="fa fa-check spaceLeft"></i>
					</button>

				</div>
				<button type="submit" class="btn btn-warning"
					onclick="history.back();">
					취소<i class="fa fa-times spaceLeft"></i>
				</button>
			</form>
			<form action="dodelete" id="dodelete">
				<button type="button" onclick="deleteCk();">삭제</button>
				<input type="hidden" name="member_id" value="${info.member_id}" />
			</form>


		</div>


	</article>

</body>
</html>