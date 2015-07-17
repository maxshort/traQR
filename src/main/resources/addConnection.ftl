<!DOCTYPE html>
<html>
	<head>
		<title>Connect two locations</title>
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
		<form method="POST" action="/connections">
			
			
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
			
			<label for="minutes">Estimated walk-time in whole minutes:</label>
		
			<input name="minutes"  type="text" id="minutes" size="40" />


	
			<label for="description">Directions</label>
		
			<textarea name="description" cols="42" rows="10" id="description" ></textarea>
		
	
	
			<input  type="submit" value="Submit"/>
				
				
			
		</form>
	</body>

</html