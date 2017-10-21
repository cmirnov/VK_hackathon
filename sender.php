<?php
define("INPUT_FILE_PATH", "dump-01.csv");
define("URL", "http://google.com/");
define("CAPTURE_RANGE", 60);
define("DEBUG", "1");

$inputFilePath = ((!empty($argv[1]) && file_exists($argv[1])) ? $argv[1] : INPUT_FILE_PATH);
$url = (!empty($argv[2]) ? $argv[2] : URL);


if (($handle = fopen($inputFilePath, "r")) !== FALSE) {
	$started = false;
	$wifiDevices = array();
	$timestamp = time();
    	while (($data = fgetcsv($handle, 1000, ",")) !== FALSE) {
		//print_r($data);
		if ($started == false && trim($data[0]) == "Station MAC") {
			$started = true;
			//echo "asdsa";
			continue;
		}
		if ($started == true)
		{
			//check current line
			if (empty($data[0]) || empty($data[3])) { 
				continue;
			}

			$lastTime = date_parse_from_format("Y-m-d H:i:s", trim($data[2]));

			if (count($lastTime['errors']) > 0 || count($lastTime['warnings']) > 0) {
				continue;
			}

			$lastTimestamp = mktime(
				$lastTime['hour'], 
				$lastTime['minute'], 
				$lastTime['second'], 
				$lastTime['month'], 
				$lastTime['day'], 
				$lastTime['year']
			);

			//echo "Fix: " . $timestamp  . "; Cur: " . $lastTimestamp . "\n";
			if ($timestamp - $lastTimestamp > CAPTURE_RANGE) {
				continue;
			}

			// build item 
			$item = array(
				"mac" => $data[0], // mac address
				"power" => trim($data[3]) // power (distance from item to sniffer)
				// may push some other info 
			);

			// push to res 
			array_push($wifiDevices, $item);
		}
    	}
    	fclose($handle);
}

// json encode 
$encodedData = json_encode($wifiDevices);

//if (DEBUG) {
//	print_r($encodedData);
//}

// sending to server
$ch = curl_init($url);
curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");
curl_setopt($ch, CURLOPT_POSTFIELDS, $encodedData);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_HEADER, true);
curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 10);
curl_setopt($ch, CURLOPT_HTTPHEADER, array(
	'Content-Type: application/json',
	'Content-Length: ' . strlen($encodedData))
);

$result = curl_exec($ch);

$httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);


if (DEBUG) {
	print_r($httpCode);
	print_r($result);
}

?>
