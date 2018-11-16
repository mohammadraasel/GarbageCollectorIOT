
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
FINISH = True
FINISH_D = False

# db configuration
# config = {
#     "apiKey": "AIzaSyDOEg7tnAMj6Y0z2BCO8wvnexZxOrUcVZw",
#     "authDomain": "gmsusingpi.firebaseapp.com",
#     "databaseURL": "https://gmsusingpi.firebaseio.com/",
#     "storageBucket": "gmsusingpi.appspot.com"
# }


def garbage_calc(distance, trash_height):
    print("Distance %d" % distance)
    print("Trash Height %d" % trash_height)
    garbage_percent = (1 - math.floor(distance) / trash_height) * 100
    print(garbage_percent)
    return round(garbage_percent)


# def garbage_monitor(sensor_id, garbage, notification, threshold):
#     if garbage['current'] <= 0:
#         lcd.show("{}:".format(sensor_id), "empty")
#         notification.setIsSent(False)

#     elif garbage['current'] > 0 and garbage['current'] <= 60 :
#         lcd.show("{}:".format(sensor_id), "{}%".format(garbage['current']))

#     elif garbage['current'] > 60 and garbage['current'] <=70:
#         lcd.show("{}:".format(sensor_id),"almost full")

#     else:
#         lcd.show("{}:".format(sensor_id), "full!")
#         if garbage['current']>=threshold and notification.isSent()!=True:
#             lcd.show("{}:".format(sensor_id), "sms sending..")
#             notification.notifyMe(sensor_id)
#             notification.setIsSent(True)
#             time.sleep(1)

# check internet connection

isRunning = True

def is_connected():
    try:
        socket.create_connection(("www.google.com", 80))
        return True
    except OSError:
        pass
    return False


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

class GarbageBin(threading.Thread):
    def __init__(self, sensor_name, distance, trigger, echo, gpio):
        threading.Thread.__init__(self)
        self.gpio = gpio
        self.gpio.setmode(gpio.BOARD)
        self.trash = Sensor(sensor_name, distance, trigger, echo)
        self.location = Geocode(requests)
        self.db = Database(conn, datetime)
        self.notification = Notification(Client, False)
        self.trash.initSensor(self.gpio)
        self._stop_event = threading.Event()

    def run(self):
        while True:
            run_sensor(self.trash, self.location, self.db, self.notification)

    def stop(self):
        self._stop_event.set()

    def stopped(self):
        return self._stop_event.is_set()


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
    trash = Sensor(sys.argv[1], 0, int(sys.argv[2]), int(sys.argv[3]))
    d_thread = threading.Thread(name='d_thread', target=database_change)
    # sensor_thread = GarbageBin("TRASH_01", 0, 5, 7, GPIO)
    d_thread.start()

    run_sensor(trash)
    # time.sleep(2)
    # sensor_thread.start()
    # bins = []
    # menu = "1. Add New Bin\t2. Delete a Bin\n3. Exit"

    # Get bin_docs from Database
    # bin_docs = r.table('bin').run(conn)

    # for bin in bin_docs:
    #     bins.append(GarbageBin(bin['name'], bin['distance'], bin['trig_pin'], bin['echo_pin']))
    #     # print(bin['name'])

    # time.sleep(2)

    # for bin in bins:
    #     bin.start()
    # while True:
    #     print(menu)
    #     option = int(input("Please Enter Your Option >> "))
    #     print(option)
    #     if option == 3:
            # FINISH = False
            # FINISH_D = True
            # print("Joining")
            # d_thread.join()
            # print("Database Thread Stopped")
            # sensor_thread.stop()
            # sensor_thread.join()
            # print("Sensor Thread Stopped")
            # sys.exit()
    # trash
    # while True:
    #     if trash.TUNED == True:
    #         trash.measureDistance(GPIO, time)
    #         time.sleep(2)
    #         garbage['current'] = garbage_calc(math.floor(trash.getDistance()), bin_depth) #trash_height=25
    #         db.updateDatabase(trash.getSensorId(), garbage, location)
    #         garbage_monitor(trash.getSensorId(),garbage, notification,threshold=80)
    #     else:
    #         bin_depth = math.floor(trash.tuneSensor(GPIO,time))
    #         garbage['depth'] = bin_depth
    #         print("Bin Depth %d"%bin_depth)
       


if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        pass
    finally:
        # lcd.show("gms", "shutting down..")
        GPIO.cleanup()
        print("GMS Stopped")
