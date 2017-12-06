<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/bootstrap-4.0.0-beta/css/bootstrap.min.css" />
    <link rel="stylesheet" href="style.css" type="text/css" />
    <title>Hello</title>
</head>
<body>
    <div id="container">
        <div id="menu">
            <div id="logo">
                Visca Panel
            </div>
        </div>
        <br />
        <br />
        <div id="content" style="height: 960px;">
            <form class="form-inline" action="<c:url value="/run" />" method="POST">
                <div class="form-group">
                    <label for="command" style="margin-right: 30px;">Command:</label>
                    <input class="form-control" name="command" id="command">
                </div>
                <button style="margin-left: 30px;" class="btn btn-default">Run</button>
            </form>
            <div class="form-group">
                <label for="comment">Result:</label>
                <textarea class="form-control" rows="5" id="comment">
                    ${komentarz}
                </textarea>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/bootstrap-4.0.0-beta/css/bootstrap.min.css"></script>
</body>
</html>