<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SRS Web Server Manager</title>
</head>
<body>
	<p>SRS Web Servlet API tester.</p>
	<form name="mainForm">
		<select id="command">
			<option value="numberOfUsers">Get Number of Users</option>
			<option value="usersList">Users List</option>
		</select>

	</form>
	<button onclick="doSubmit();">Submit</button>
</body>

<script src="javascript/jquery-1.5.1.js"></script>
<script type="text/javascript">
	function doSubmit() {
		var sel = document.getElementById("command");
		sel.options[sel.selectedIndex].value;
		$.ajax({
			type : "GET",
			url : "Server",
			data : "userStatus=" + sel.options[sel.selectedIndex].value,
			success : function(resp) {
				// we have the response  
				alert("Server said:\n '" + resp + "'");
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}
</script>
</html>