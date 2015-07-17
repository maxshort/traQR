<!DOCTYPE html>
<html>
	<head>
		<title>Get directions</title>
	</head>
	<style>
		form {
			clear: both;
			float: left;
		}
		label {
			clear: both;
			float: left;
		}
		select {
			clear: both;
			float: left;
		}
		input {
			clear: both;
			float: left;
		}
		textarea {
			clear: both;
			float: left;
		}
	</style>
	
	<body>
		<#include "/menu.ftl">
		<form method="GET" action="/directions/">
			
			
			<label for="fromLocation">Start Point:</label>

			<select name="fromLocation" id="fromLocation">
				<#list locations as Location>
					<option value="${Location.id}">${Location.name}</option>
				</#list>
			</select>

			<label for="toLocation">End Point:</label>

			<select name="toLocation" id="toLocation">
				<#list locations as Location>
					<option value="${Location.id}">${Location.name}</option>
				</#list>
			</select>

			<input  type="submit" value="Submit"/>
			
		</form>
	</body>

</html>