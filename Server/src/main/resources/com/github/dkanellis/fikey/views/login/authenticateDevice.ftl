<html>
<head>
    <title></title>
    <link rel="stylesheet" href="/css/core.css">

    <script src="/js/u2f-api.js"></script>

    <script>
        var request = ${data};
        setTimeout(function() {
            u2f.sign(request.authenticateRequests,
            function(data) {
                if(data.errorCode) {
                    alert("U2F failed with error code: " + data.errorCode);
                    return;
                }
                document.getElementById('tokenResponse').value = JSON.stringify(data);
                document.getElementById('form').submit();
            });
        }, 1000);



    </script>

</head>
<body>
<div class="v-wrap">
    <article class="v-box">
        <div class="key-prompt">
            <h1>Please insert your security key.</h1>
            <img src="/pics/security_key_graphic.png" alt="Security Key" style="width:253px;height:210px">

            <div id="tap-text">
                <br>
                If your Security Key has a button, tap it.
                <br>
                If it doesn't, remove and re-insert it.
            </div>
            <form method="POST" action="/api/finishDeviceAuthentication" id="form">
                <input type="hidden" name="tokenResponse" id="tokenResponse"/>
                <input type="hidden" name="username" id="username" value="${username}"/>
            </form>
        </div>
    </article>
</div>
</body>
</html>
