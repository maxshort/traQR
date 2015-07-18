<html>

<body>
    <#include "/menu.ftl">
    <h1>Success!</h1>
    <p>Great job. You have added ${location.name} as a location</p>
    <p>This location can't be reached by anything because it is not connected to anything else. You may want to <a href="/connections">add a connection</a>.</p>
    <p>You can also <a href="/qr?location=${location.id}">print a QR code for this location</a>.</p>

</body>

</html>