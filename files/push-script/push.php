<?php

    // put server key into common.php
	include('common.php');

	if ($_SERVER["REQUEST_METHOD"] == "POST") {

		$body = $_POST["body"];
		$title = $_POST["title"];

		sendPushNotification($title, $body, $apiKey);

	}

	function sendPushNotification($title, $body, $apiKey) {

		$url = "https://fcm.googleapis.com/fcm/send";

        $values = array();
        $values ['title'] = $title;
        $values ['body'] =  $body;

        $data = array();
        $data ['to'] = "/topics/ghurboNotifcations";
        $data ['data'] = $values;

        $content = json_encode($data);

        $curl = curl_init($url);
        curl_setopt($curl, CURLOPT_HEADER, false);
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($curl, CURLOPT_HTTPHEADER,
                array(
                	"Content-type: application/json",
                    "Authorization: key=" . $apiKey
                	));
        curl_setopt($curl, CURLOPT_POST, true);
        curl_setopt($curl, CURLOPT_POSTFIELDS, $content);

        $json_response = curl_exec($curl);

        if (curl_errno($curl)) {
            echo 'FCM error: ' . curl_error($curl);
        } else {
        	echo "<br><div class='textOutput'>Push sent to all devices.</div>";
        	echo "<br><a href='index.php'>Send New Push Notification</a>";
        }
        curl_close($curl);
	}

?>