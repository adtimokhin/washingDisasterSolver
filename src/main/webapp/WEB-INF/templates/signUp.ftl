<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>sign up</title>
</head>
<body>
<h1>This is our sign up page.</h1>

<p> It may not look different from our index (or landing) page yet. But that will change pretty quick, I promise.</p>

<div id="errors">
    <#--    Double ?? means that ftl will check if that attribute is present        -->
    <#if errors??>
        <#list errors as error>
        <#--        Remember:  error is an obkect of class UserError        -->
           <p> ${error.errorMessage} </p>
        </#list>
    </#if>

</div>

<form method="post" action="/sign_up">
    <input type="email" name="email">
    <input type="text" name="name">
    <input type="password" name="password">
    <input type="password" name="passwordTwo">
    <input type="submit" value="Register a new user">

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>


</body>
</html>

