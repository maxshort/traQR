<!DOCTYPE html>
<html>
	<head>
		<title>All locations</title>
	</head>
	<style>
		
	</style>
	
	<body>
		<h2>All locations:<h2>
		<table>
			<#list locations as Location>
				<tr>
					<td>
						${Location.id}
					</td>
					<td>
						<a href="/qr/?location=${Location.id}">${Location.name}</a>
					</td>
				</tr>
			</#list>
		</table>
	</body>
</html