<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Washing Disaster Solver | Account</title>
    <link href="../../../resources/css/main.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
</head>
<body  style="background: rgb(214, 217, 208);">


<div class="container main-context">
    <div class="row">
        <div class="col-12">
            <h4 class="error-msg">Logged As: ${name}</h4>
        </div>
    </div>

    <#if msg??>
        <div class="row">
            <div class="col-12">
                <h4 class="error-msg">${msg}</h4>
            </div>
        </div>
    </#if>

    <div class="row">
        <div class="col-1"></div>
        <div class="col-10">
            <div class="form-container">
                <div class="input-holder">
                    <h4 class="form-title">My Current Bookings:</h4>
                </div>
                <div class="input-holder">
                    <h3 style="color: #F2F2F2; text-decoration-color: #F2F2F2;">Washing Machine Bookings:</h3>
                </div>
                <#if washings??>
                    <#list washings as w>
                        <div class="input-holder">
                            <p>${w.presentNicely()}</p>
                            <form method="post" action="/booking/cancel/washing_machines">
                                <input type="hidden" name="id" value="${w.getId()}">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                <input type="submit" value="Cancel" class="mx-auto d-block button">
                            </form>
                        </div>
                    </#list>
                <#else>
                    <div class="input-holder">
                        <p>You have to bookings for washing machines</p>
                    </div>
                </#if>

                <div class="input-holder">
                    <h3 style="color: #F2F2F2; text-decoration-color: #F2F2F2;">Drying Machine Bookings:</h3>
                </div>
                <#if dryings??>
                    <#list dryings as d>
                        <div class="input-holder">
                            <p>${d.presentNicely()}</p>
                            <form method="post" action="/booking/cancel/drying_machines">
                                <input type="hidden" name="id" value="${d.getId()}">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                <input type="submit" value="Cancel" class="mx-auto d-block button">
                            </form>
                        </div>
                    </#list>
                <#else>
                    <div class="input-holder">
                        <p>You have to bookings for drying machines</p>
                    </div>
                </#if>
            </div>
        </div>
    </div>
    <div class="col-1"></div>
</div>


<form method="post" action="/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="Logout" class="mx-auto d-block button">
</form>


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

