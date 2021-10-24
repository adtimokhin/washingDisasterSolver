<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Washing Disaster Solver | Reports Page</title>
    <link href="../../../resources/css/main.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
</head>
<body style="background: rgb(214, 217, 208);">
<div class="container main-context">
    <div class="row">
        <div class="col-2"></div>
        <div class="col-8">
            <div class="form-container">
                <form method="post" action="/complaint/add">
                    <div class="input-holder">
                        <h4 class="form-title">What machine is occupied?</h4>
                    </div>
                    <div class="input-holder">
                        <p class="mx-auto d-block">Machine Id<br/></p>
                        <input type="number" name="id" class="mx-auto d-block">
                    </div>
                    <div class="input-holder">
                        <p class="mx-auto d-block">What type of machine seems to cause problem?</p>
                    </div>
                    <div class="input-holder">
                        <p class="mx-auto d-block">Washing Machine</p>
                        <input class="mx-auto d-block" type="radio" name="type" value="W" checked="checked">
                        <#--                        <span class="checkmark mx-auto d-block"></span>-->
                    </div>
                    <div class="input-holder">
                        <p class="mx-auto d-block">Drying Machine</p>
                        <input class="mx-auto d-block" type="radio" name="type" value="D">
                        <#--                        <span class="checkmark mx-auto d-block"></span>-->
                    </div>
                    <div class="input-holder">
                        <div class="popup">
                            <input type="submit" value="Submit" class="mx-auto d-block button">
                        </div>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                </form>
            </div>
        </div>
    </div>
    <div class="col-2"></div>
</div>
<div class="container main-context">
    <div class="row">
        <div class="col-2"></div>
        <div class="col-8">

            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <h4 class="error-msg">Current Complaints:</h4>
                    </div>
                </div>

                <div class="row">
                    <div class="col-12 paragraph section">
                        <#if washingMachineComplaints??>
                            <p>Complaints about washing machine users: </p>
                            <ul class="list-unstyled">
                                <ul>
                                    <#list washingMachineComplaints as compalint>
                                        <li>
                                            Booking for washing
                                            machine ${compalint.getBooking().getWashingMachine().getId()} that finishes
                                            at ${compalint.getBooking().getEndDate()} by
                                            user ${compalint.getBooking().getUser().getName()}
                                        </li>
                                    </#list>
                                </ul>
                            </ul>
                        <#else>
                            <p>No complaints about usage of washing machines</p>
                        </#if>
                    </div>
                </div>

                <div class="row">
                    <div class="col-12 paragraph section">
                        <#if dryingMachineComplaints??>
                            <p>Complaints about washing machine users: </p>
                            <ul class="list-unstyled">
                                <ul>
                                    <#list dryingMachineComplaints as compalint>
                                        <li>
                                            Booking for washing machine ${compalint.getBooking().getDryingMachine().getId()}
                                            that finishes
                                            at ${compalint.getBooking().getEndDate()} by
                                            user ${compalint.getBooking().getUser().getName()}
                                        </li>
                                    </#list>
                                </ul>
                            </ul>
                        <#else>
                            <p>No complaints about usage of drying machines</p>
                        </#if>
                    </div>
                </div>

            </div>

        </div>
    </div>
    <div class="col-2"></div>
</div>
<!-- Footer -->
<footer class="footer">
    <div class="container">
        <div class="row">
            <!-- photo -->
            <div class="col-md-4 col-sm-5 col-xs-12" id="logo-container">
                <div class="logo-container">
                    <img src="../../../resources/images/adtimokhinLOGOfooter.svg" class="mx-auto d-block">
                </div>
            </div>
            <!-- Other action buttons -->
            <div class="col-md-4 col-sm-2 col-xs-6">
                <a href="#" class="footer-link-title">adtimokhin@gmail.com</a>
                <a href="https://github.com/adtimokhin/" target="_blank" class="footer-link">GitHub</a>
                <a href="https://www.linkedin.com/in/aleksandr-timokhin-5300361b6" target="_blank"
                   class="footer-link">LinkedIn</a>
                <a href="https://www.instagram.com/adtimokhin/" target="_blank" class="footer-link">Instagram</a>
            </div>

            <div class="col-md-4 col-sm-3 col-xs-6">
                <a href="#" class="footer-link-title">Terms & policy</a>
            </div>
        </div>
    </div>
</footer>
</body>
</html>

