<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.sql.*" %>
<%@ page import="travel.DAO" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">


<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>


<style>
	.title-bar{
	width: 100%;
	color: white;
	padding: 10px;
	font-family: serif;
	font-size: 100px;
	text-align: center;
	background-color: #1abc9c;
	}


	.travel-form{
		margin: auto;
		align-content: center;
		display: block;
    	text-align: center;
    	background-color: #DBDADA;
    	padding: 20px;
	}

	.travel-form form{
		display: inline-block;
	}

	.travel-form select, input{
		height: 60px;
		width: 300px;
	}

	.search{
		background-color: #000faa;
		color: white;
		font-style: strong;
		font-size: large;
		font-weight: 300;
		font-family: monospace;
		border-radius: 100px;
		border: solid;
		background-image: linear-gradient(to bottom right, #6666ff, #9999ff, #0066ff); 
	}

	.special-deals{
		width: 100%;
		padding: 10px;
		font-family: serif;
		font-size: 50px;
		text-align: center;
		color: white;
		font-family: cursive;
		background-image: linear-gradient(to bottom right, #0636ff, #0699ff, #f016ff);  
	}

</style>



<title>Home</title>
</head>
<body>

<!-- Title bar -->
<div class="title-bar">
	<h1> TravelThruAir </h1>
</div>


<%
	String url,user,pass;
	url="jdbc:mysql://localhost:3306/travel";
	System.out.println(url);
	user="root";
	pass="";
	
	DAO dao=new DAO(url,user,pass);
	ResultSet rs;
	rs=dao.getCities();
%>


<!-- Form -->
<div class="travel-form">
	<form action="search" method="post">
		<select class="src" name="src">
			<%
				do
				{%>
					<option value="<%=rs.getString("code")%>"> <strong><%=rs.getString("code")%></strong>-<%=rs.getString("city")%> </option>
				<%}
				while(rs.next());
				rs=dao.getCities();
			%>
		</select>

		<select class="dest" name="dest">
			<%
				do
				{%>
					<option value="<%=rs.getString("code")%>"> <strong><%=rs.getString("code")%></strong>-<%=rs.getString("city")%> </option>
				<%}
				while(rs.next());
			%>
		</select>

		<input type="date" id="date" name="date" required="">
		<input class="search" type="submit" value="SEARCH">
		
	</form>
</div>

<div class="special-deals">
	SPECIAL DEALS
</div>


<!-- Offers -->
<div class="offers">
	<%
		rs=dao.getOffers();
		if(rs==null)
		{%>
			<div class="error">No offers right now</div>
		<% }
		else
		{
			{%>
				<div class="offer">
					START TIME: <%=rs.getString("start_time") %>
					END TIME: <%=rs.getString("end_time") %>
					DISCOUNT: <%=rs.getString("discount") %>
					SRC: <%=rs.getString("src_city") %>
					DEST: <%=rs.getString("dest_city") %>
				</div>
			<%}
			while(rs.next());
		}
	%>
</div>

</body>

<script type="text/javascript">
	var today = new Date().toISOString().split('T')[0];
	document.getElementsByName("date")[0].setAttribute('min', today);
</script>

</html>