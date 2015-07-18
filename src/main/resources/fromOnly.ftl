<html>

<body>
	<#include "/menu.ftl">
    <h1>Select Destination</h1>

    <form action = "/directions">
        <input type="hidden" name="fromLocation" value="${tripStart.id}">
        <select name ="toLocation">
            <#list locationsWithoutStart as location>
                <option value=${location.id}>${location.name}</option>
            </#list>
        </select>

        <input type="submit" value="Get directions">

</body>

</html>