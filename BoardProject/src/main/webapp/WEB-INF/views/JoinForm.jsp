<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<head>
<meta charset="EUC-KR">
<title>Login</title>
<%
	String msg = (String)request.getAttribute("msg");
	if(msg!=null){
		 out.println("<script>alert('���̵� �̹� �����մϴ�');</script>");
	}
%>
<script type="text/javascript">
	 
	function fCheck() {
		var inputId = $("#inputId").val();
		var inputName = $("#inputName").val();
		var inputMobile = $("#inputMobile").val();
		var InputEmail = $("#InputEmail").val();
		
		var pwd1 = $("#inputPassword").val();
		var pwd2 = $("#inputPasswordCheck").val();
		
		if(confirm("ȸ�������� �Ͻðڽ��ϱ�?")){
			if((inputId=="")||(inputName=="")||(inputMobile=="")||(InputEmail=="")||(pwd1=="")){
				alert("������ �ֽ��ϴ�.");
			}else if (pwd1 != pwd2) {
				alert("��й�ȣ�� ��ġ���� �ʽ��ϴ�. ��й�ȣ�� ��Ȯ�����ּ���.");
			} else{
	      		 $("#frm").submit();
	        }
	    }
		
	};
 
</script>

</head>

<body>
	<article class="container">
		<div class="page-header">
			<div class="col-md-6 col-md-offset-3">
				<h3>ȸ������ Form</h3>
			</div>
		</div>
		<div class="col-sm-6 col-md-offset-3">
			<form role="form" action="doJoin" method="post" name="frm" id="frm">
				<div class="form-group">
					<label for="inputId">���̵�</label> <input type="text"
						class="form-control" id="inputId" name="member_id"
						placeholder="���̵� �Է��� �ּ���" required> 
				</div>

				<div class="form-group">
					<label for="inputPassword">��й�ȣ</label> 
					<input type="password" class="form-control" id="inputPassword" name="member_pw"
						placeholder="��й�ȣ�� �Է����ּ���" required>
				</div>
				<div class="form-group">
					<label for="inputPasswordCheck">��й�ȣ Ȯ��</label> <input
						type="password" class="form-control" id="inputPasswordCheck"
						placeholder="��й�ȣ Ȯ���� ���� �ٽ��ѹ� �Է� �� �ּ���" required>
				</div>
				 
				<div class="form-group">
					<label for="inputName">�̸�</label> <input type="text"
						class="form-control" id="inputName" name="member_name"
						placeholder="�̸��� �Է��� �ּ���" required>
				</div>
				<div class="form-group">
					<label for="inputMobile">�޴��� ��ȣ</label> <input type="tel"
						class="form-control" id="inputMobile" name="member_phone"
						placeholder="�޴�����ȣ�� �Է��� �ּ���" required>
				</div>
				<div class="form-group">
					<label for="InputEmail">�̸��� �ּ�</label> <input type="email"
						class="form-control" id="InputEmail" name="member_email"
						placeholder="�̸��� �ּҸ� �Է����ּ���" required>
				</div>

				<div class="form-group text-center">
					<button type="button" id="join" class="btn btn-primary"
						onclick="fCheck()">
						ȸ������<i class="fa fa-check spaceLeft"></i>
					</button>
					<button type="submit" class="btn btn-warning"
						onclick="history.back();">
						�������<i class="fa fa-times spaceLeft"></i>
					</button>
				</div>
			</form>
		</div>


	</article>

</body>
</html>