import math
import socket
import sys
import threading
import time
from datetime import datetime

import RPi.GPIO as GPIO

from geocode import Geocode

from notification import Notification

import requests

from rethink import Database

import rethinkdb as r

from sensor import Sensor

from twilio.rest import Client

# import json
# import pyrebase
# import os
sys.path.append('/home/pi/Desktop/newGMS/lcd')
# import lcd

conn = r.connect('192.168.0.4', 28015)
conn.use('pi')

GPIO.setwarnings(False)
GPIO.cleanup()
GPIO.setmode(GPIO.BOARD)

bin_depth = 100
garbage = {}
isRunning = True


def garbage_calc(distance, trash_height):
    print("Distance %d" % distance)
    print("Trash Height %d" % trash_height)
    garbage_percent = (1 - math.floor(distance) / trash_height) * 100
    print(garbage_percent)
    return round(garbage_percent)

def database_change():
    print("Database Thread Started")
    conn2 = r.connect('192.168.0.4', 28015)
    conn2.use('pi')
    cursor = r.table("status").filter(r.row['name'] == "Trash_01").changes().run(conn2)
    for document in cursor:
        if document['new_val']['status'] == 'inactive':
            print("Sensor is paused")
            change_running_state(False)
        else:
            print("sensor is running")
            change_running_state(True)

def change_running_state(state):
    global isRunning
    isRunning = state

def run_sensor(trash):
    global isRunning
    # print("Sensor Thread Started")
    # trash = Sensor(name, dist, trig, echo)
    # location = Geocode(requests)
    # db = Database(conn, datetime)
    # notification = Notification(Client, False)
    trash.initSensor(GPIO)
    # lcd.lcd_init()
    # lcd.greetings()

    while True:
        if isRunning:
            if trash.TUNED is True:
                trash.measureDistance(GPIO, time)
                time.sleep(2)
                garbage['current'] = garbage_calc(
                    math.floor(trash.getDistance()), bin_depth)
                # db.updateDatabase(trash.getSensorId(), garbage, location)
                # garbage_monitor(
                #     trash.getSensorId(),
                #     garbage, notification, threshold=80)
            else:
                print("Bin Not Tuned. Tunnning Now....")
                garbage['depth'] = math.floor(trash.tuneSensor(GPIO, time))
                print("Bin Depth %d" % bin_depth)

def main():
    global isRunning

    sensor_id = sys.argv[1]
    bin = r.table('bin').get(sensor_id).run(conn)
    if bin is not None:
        trash = Sensor(bin['name'], 0, int(bin['trig_pin']), int(bin['echo_pin']))
        d_thread = threading.Thread(name='d_thread', target=database_change)
        d_thread.start()
        run_sensor(trash)
    
if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        pass
    finally:
        # lcd.show("gms", "shutting down..")
        GPIO.cleanup()
        print("GMS Stopped")
