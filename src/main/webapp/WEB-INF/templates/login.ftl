<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<h1>This is our login page.</h1>

<#if error??>
    <p>${error}</p>
</#if>

<form method="post" action="/login/process">
    <input type="text" name="email">
    <input type="password" name="password">
    <input type="submit" value="Login">

    <#--    This is how we add csrf token -->
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>


</body>
</html>




