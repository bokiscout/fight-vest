# fight-vest
modernizing martial arts

### fight vest includes:
- Web application (go to branch: [web](https://github.com/bokiscout/fight-vest/tree/web "branch: web"))
- Android application (go to branch: [android](https://github.com/bokiscout/fight-vest/tree/android "branch: android"))
- Punch sensor (go to branch: [sensor](https://github.com/bokiscout/fight-vest/tree/sensor "branch: sensor"))

### How does fight-vest work?
When sensor detects punch it sends data to the Android application over bluetooth,

Android application acts as a pasthrough and sends whatever is being recived from the sensor to the cloud over wi-fi or 3g,

Web application acts as a server for permanently storeing and manageing fight data.
