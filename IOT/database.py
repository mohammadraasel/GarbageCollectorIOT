class Database:
    def __init__(self, config, pyrebase, datetime):
        self.CONFIG = config
        self.FIREBASE = pyrebase.initialize_app(self.CONFIG)
        self.DB = self.FIREBASE.database()
        self.DATETIME = datetime



    def updateDatabase(self, sensorId, garbage, loc):
        location = loc.trackLocation()
        if garbage['current'] <= 0:
            garbage['current'] = 0
        data = {
            "sensorId": sensorId,
            "size": str(garbage['depth']), 
            "status": str(garbage['current'])+"%",
            "time": self.DATETIME.now().strftime('%Y-%m-%d %H:%M:%S'),
            "city": location["city"],
            "latitude": location["latitude"],
            "longitude": location["longitude"]
        }
        self.DB.child('/'+sensorId).update(data)
