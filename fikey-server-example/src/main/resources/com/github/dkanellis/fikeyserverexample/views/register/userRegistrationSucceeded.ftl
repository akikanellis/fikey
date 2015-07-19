<html>
<head>
    <title></title>
    <link rel="stylesheet" href="/css/core.css">
</head>
<body>
<div class="v-wrap">
    <article class="v-box">
        <h1>Welcome ${username}, you are now registered!</h1>

        <form method="GET" action="/api/startDeviceRegistration" id="form">
            <input type="hidden" name="username" value=${username} autofocus/>
            <button type="Submit" class="btn green">Add FIDO device</button>
        </form>
    </article>
</div>
</body>
</html>
