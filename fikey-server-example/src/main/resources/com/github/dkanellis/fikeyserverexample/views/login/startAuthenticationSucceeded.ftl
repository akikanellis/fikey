<html>
<head>
    <title></title>
    <link rel="stylesheet" href="/css/core.css">
</head>
<body>
<div class="v-wrap">
    <article class="v-box">
        <h1>Welcome back ${username}.</h1>

        <form method="GET" action="/api/startDeviceAuthentication" id="form">
            <input type="hidden" name="username" value=${username} autofocus/>
            <button type="Submit" class="btn green">Authenticate with FIDO device</button>
        </form>
    </article>
</div>
</body>
</html>
