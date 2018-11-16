class Sensor:
    def __init__(self, sensor_id, distance, trig, echo):
        self.ID = sensor_id
        self.DISTANCE = distance
        self.TRIG = trig
        self.ECHO = echo
        self.TUNED = False

    def initSensor(self, GPIO):
        GPIO.setup(self.ECHO, GPIO.IN)
        GPIO.setup(self.TRIG, GPIO.OUT)

    def getSensorId(self):
        return self.ID

    def getDistance(self):
        return self.DISTANCE


    def measureDistance(self, GPIO, TIME):
        GPIO.output(self.TRIG, GPIO.LOW)
        TIME.sleep(0.1)
        GPIO.output(self.TRIG, GPIO.HIGH)
        TIME.sleep(0.00001)
        GPIO.output(self.TRIG, GPIO.LOW)
        while GPIO.input(self.ECHO) == False:
            pulse_start = TIME.time()
        while GPIO.input(self.ECHO) == True:
            pulse_end = TIME.time()

        pulse_duration = pulse_end - pulse_start
        distance = pulse_duration * 17150
        self.DISTANCE = distance

    def tuneSensor(self, GPIO, TIME):
        total_distance = 0
        total_reading = 10
        for i in range(10):
            self.measureDistance(GPIO, TIME)
            total_distance += self.DISTANCE
            TIME.sleep(0.05)
        self.TUNED = True
        return total_distance / total_reading
