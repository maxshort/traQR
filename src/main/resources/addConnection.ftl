<!DOCTYPE html>
<html>
	<head>
		<title>Connect two locations</title>
		<link href="/literallycanvas-0.4.3/css/literallycanvas.css" rel="stylesheet">
		<script type="text/javascript" src="/reactjs/reactwithaddons.js"></script>
		<script type="text/javascript" src="/literallycanvas-0.4.3/js/literallycanvas.js"></script>
		<script type="text/javascript">
			function submitCanvas(){
				document.getElementById("canvasData").value = lc.getImage({scaleDownRetina:true, includeWatermark:false}).toDataURL("img/png");
			}
        </script>
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
		div {
			display:block;
			clear: both;
		}
		p{
			float:left
			clear:both
		}
	</style>
	
	<body>
		<#include "/menu.ftl">
		<div>
		<form method="POST" action="/connections" onsubmit="submitCanvas()">
			
			
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
		
	
			<input type="hidden" name="canvasData" id="canvasData"/>
			<input  type="submit" value="Submit"/>
			
		</form>
		</div>
		<BR>
		<div class="literally without-jquery" style="height: 200px; width:500px"></div>
		<script type="text/javascript">
			var backgroundImage = new Image();
			backgroundImage.src = '/Xbackground.png';
			var lc = LC.init(document.getElementsByClassName('literally without-jquery')[0], {imageSize: {width: 500, height: 200},
																					imageURLPrefix:"/literallycanvas-0.4.3/img",
																					tools:[LC.tools.Pencil, LC.tools.Eraser, LC.tools.Line],
																					backgroundColor:"transparent",
																					watermarkImage:backgroundImage,
																					primaryColor:"#000099"});

			document.getElementsByClassName('lc-color-pickers')[0].style.display = "None"; //don't let them pick colors
		</script>
	</body>

</html