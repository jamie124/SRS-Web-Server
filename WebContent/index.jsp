<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SRS Web Server Manager</title>
</head>
<body>
<p>Manager for SRS Web Servlet.</p>

<table>
	<tr>
		<td>Command:</td>
		<td>
			<select>
			<option value="numberOfUsers" >Get Number of Users</option>
			</select>
		</td>
	</tr>
</table>

<button onclick="doSubmit();">Submit</button>
</body>

<script src="javascript/jquery-1.5.1.js"></script>
<script type="text/javascript">

function doSubmit(){
	 $.ajax({  
		   type: "GET",  
		   url: "Server",  
		   data: "userStatus=usersList",  
		   success: function(resp){  
		     // we have the response  
		     alert("Server said:\n '" + resp + "'");  
		   },  
		   error: function(e){  
		     alert('Error: ' + e);  
		   }  
		 });  
}
</script>
</html>