class Notification:
    def __init__(self, client, smsFlag):
        self.CLIENT = client
        self.SMS_FLAG = smsFlag
    
    def isSent(self):
        return self.SMS_FLAG

    def setIsSent(self, smsFlag):
        self.SMS_FLAG = smsFlag

    def notifyMe(self,sensorId):
        # Your Account SID from twilio.com/console
        account_sid = "AC4ddd01d681c6327448d2e4bdc050cb69"
        # Your Auth Token from twilio.com/console
        auth_token = "89478645138f59218a9fb9103c9d4b6c"

        client = self.CLIENT(account_sid, auth_token)

        message = client.messages.create(
            to="+8801534714244",
            from_="+15598694092",
            body=sensorId+" full! Please collect garbages")

