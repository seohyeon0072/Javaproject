<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="menu.jsp" />  
	<form action="doInsert" method="post" > 
	<div class="container" align="center"> 
		<div align="left"  
			style="width: 700px; border: 1px solid #eeeeee; padding: 20px; background-color: WHITE; margin-bottom : 99px;">
				<h3>�Խñ� �ۼ�</h3> 
			<hr />   
			<div class="form-inline" style="margin-bottom: 5px">
				<label style="width: 75px">����</label> 
				<input type="text" name="b_title" style="width: 320px" class="form-control" placeholder="������ �Է��ϼ���" required/>
			</div>
	 <div class="form-inline" style="margin-bottom: 5px">
				<label style="width: 75px">ī�װ�</label> 
				<input type="text" name="b_category" style="width: 320px" class="form-control" placeholder="ī�װ��� �Է��ϼ���" required/>
			</div>
			<div class="form-inline" style="margin-bottom: 5px">  
				<label style="width: 75px">�ۼ���</label>  
				<input type="text" name="b_writer" style="width: 320px" class="form-control" value="${member_id}"  placeholder="�ۼ��ڸ� �Է��ϼ���" required readonly/>
			</div>
			 
			
			<div class="form-inline" style="margin-bottom: 5px">
				<label style="width: 90px">����</label> 
				<textarea id="txt" name="b_contents" style="width: 400px; resize: none;"
					class="form-control" rows="5" placeholder="������ �Է��ϼ���."></textarea>
			</div>
			
			<hr>
			 
			 
			<hr />		  
 
			<div class="form-inline" style="margin-bottom: 10px" align="center">
				<input type="submit" class="btn btn-success" value="����ϱ�" /> 
			<a href="main?pageNum=1"><input type="button" class="btn btn-primary" value="�ڷΰ���" /></a>	
			</div>
<!-- 			<input type="hidden" name="cmd" value="insertInfo" /> -->
		</div>
		 <input type="hidden" name="b_id" value="${sessionScope.member_id}">
	</div>  
	</form>
</body> 

	 
</html>
 