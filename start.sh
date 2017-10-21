#!/bin/bash
DUMP_FILE="/home/pi/dumps/dump-01.csv"

if [ -e $DUMP_FILE ]; then
	sudo rm  $DUMP_FILE
fi
screen -d -m bash -c "sudo airodump-ng wlan0 --output-format=csv --write=dump --write-interval=10 --update=10"
sudo /etc/init.d/cron start
