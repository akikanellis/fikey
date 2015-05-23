<html>
<head>
<title></title>

<script src="/u2f-api.js"></script>

<script>
var request = ${data};
setTimeout(function() {
    u2f.register(request.registerRequests, request.authenticateRequests,
    function(data) {
        var form = document.getElementById('form');
        var reg = document.getElementById('tokenResponse');
        if(data.errorCode) {
            alert("U2F failed with error: " + data.errorCode);
            return;
        }
        reg.value=JSON.stringify(data);
        form.submit();
    });
}, 1000);
</script>

</head>
    <body>
    <p>Touch your U2F token.</p>
        <form method="POST" action="/api/finishDeviceRegistration" id="form" onsubmit="return false;">
            <input type="hidden" name="username" value="${username}"/>
            <input type="hidden" name="tokenResponse" id="tokenResponse"/>
        </form>
    </body>
</html>
