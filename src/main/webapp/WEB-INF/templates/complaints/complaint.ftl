<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>News</title>
</head>
<body>
<h1>This is an anonymous complaint form.</h1>
    <form method="post" action="/complaint/add">
        <input type="text" name="isWashingMachine" value="yes">
        <input type="number" name="id" placeholder="What machine seems to be occupied though it shouldn't?">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="submit" value="Submit!">
    </form>


<h2>Current Complaints:</h2>
<#if washingMachineComplaints??>
    <p>Complaints about washing machine users: </p>
    <#list washingMachineComplaints as compalint>
        <p>
            Booking for washing machine ${compalint.getBooking().getWashingMachine().getId()} that finishes at ${compalint.getBooking().getEndDate()} by user ${compalint.getBooking().getUser().getName()}
        </p>
    </#list>
    <#else>
    <p>No complaints abbot usage of washing machines</p>
</#if>

<#if dryingMachineComplaints??>
    <p>Complaints about washing machine users: </p>
    <#list dryingMachineComplaints as compalint>
        <p>
            Booking for washing machine ${compalint.getBooking().getDryingMachine().getId()} that finishes at ${compalint.getBooking().getEndDate()} by user ${compalint.getBooking().getUser().getName()}
        </p>
    </#list>
<#else>
    <p>No complaints abbot usage of drying machines</p>
</#if>
</body>
</html>

