import rethinkdb as r


class Database:
    def __init__(self, conn ,datetime):
        self.conn = conn
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
        r.table('sensor').insert([data]).run(self.conn)
