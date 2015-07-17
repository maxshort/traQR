<html>

<body>
    <h1>Select Destination</h1>

    <form action = "/directions">
        <input type="hidden" name="from" value="${tripStart.id}">
        <select name ="to">
            <#list locationsWithoutStart as location>
                <option value=${location.id}>${location.name}</option>
            </#list>
        </select>

        <input type="submit" value="Get directions">

</body>

</html>