<#assign sf=JspTaglibs["http://www.springframework.org/tags/form"]>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create News</title>
</head>
<body>
<h1>
    Create a new article for everyone to read!
</h1>



<div>
    <@sf.form action="/news/add" method="post" modelAttribute="news">
        <div>
            <@sf.label path="headline">Name</@sf.label>
            <@sf.input path="headline"/>
            <@sf.errors path="headline"/>
        </div>
        <div>
            <@sf.label path="text">Text</@sf.label>
            <@sf.input path="text"/>
            <@sf.errors path="text"/>
        </div>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <input type="submit">
    </@sf.form>
</div>

</body>
</html>

