<!DOCTYPE html>
<html>
	<head>
		<title>All locations</title>
	</head>
	<style>
		
	</style>
	
	<body>
		<h2>All locations:<h2>
        <#list locations as Location>
			<a href="/qr/?location=${Location.id}">${Location.name}</a>
		</#list>
	</body>
</html