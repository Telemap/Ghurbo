<html>

    <head>
        <title>Ghurbo App - Push Notification</title>
        <link rel="stylesheet" href="css/style.css">
    </head>

    <body align="center" >
    	<form id="pushform" method="POST" action="push.php">
            <br>
            <br>
            <div class="textHeader">Push notification console</div>
            <br>
			<input placeholder="Title" type="text" size="38" name="title">
			<br>
			<br>
            <textarea placeholder="Message" name="body" cols="40" rows="5"></textarea>
            <br>
            <br>
    		<input class="button button2" type="submit" name="submit" value="Send">

    	</form>
    </body>

</html>