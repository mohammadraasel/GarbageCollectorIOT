import RPi.GPIO as GPIO
import sys
sys.path.append('/home/pi/Desktop/newGMS/lcd')
import lcd
GPIO.setwarnings(False)
GPIO.cleanup()
GPIO.setmode(GPIO.BOARD)


lcd.lcd_init()
lcd.greetings()

lcd.show("hello"," bolod");

