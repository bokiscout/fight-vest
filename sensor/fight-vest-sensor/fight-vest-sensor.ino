// declare global variables
int INPUT_PIN;
int LED_PIN;
boolean ledState;

boolean waitPunch;
int punchStrength;

char inbyte;

void setup() {
  // put your setup code here, to run once:

  // initialize global variables
  INPUT_PIN = A0;
  LED_PIN = 13;
  ledState = false;
  waitPunch = true;
  punchStrength = 0;

  // initial state of pins
  pinMode(LED_PIN, OUTPUT);       // led pin to behave as output
  digitalWrite(LED_PIN, LOW);     // turn off led
  Serial.begin(9600);             // serial communication is done at 96000 bouds
  analogWrite(INPUT_PIN, 0);      // force 0v,
  // otherwise readings can fluctuate due to noise,
  // but this way readings have smaller values than usual!

  inbyte = 0;
}

void loop() {
  // read data from the sensor
  int value = analogRead(INPUT_PIN);

  if (value >= 700) {
    // strong punch // 5v
    waitPunch = false;      // notify that punch is detected and not waiting for
    punchStrength = 2;

    // blink led at 5V
    ledState = true;
    digitalWrite(LED_PIN, HIGH);
    delay(70);
    
    ledState = false;
    digitalWrite(LED_PIN, LOW);
    delay(70);
  }
  else if (value >= 450) {
    // medium punch // 3.5V
    waitPunch = false;      // notify that punch is detected and not waiting for

    // if coming back from strong punch let it be strong
    // othervise let it be medium punch
    if (punchStrength < 2) {
      punchStrength = 1;
    }

    // turn on led at 3V
    ledState = true;
    digitalWrite(LED_PIN, HIGH);
  }
  else {
    // no punch // 0v

    // if first time 0v notify for punch strength
    // notify for punch strenght
    // then reset value to 0 and turn off led
    if (! waitPunch) {
      if (punchStrength == 2) {
        // strong punch was detected
        Serial.println("# punch = STRONG $");
      }
      else if (punchStrength == 1) {
        // medium punch
        Serial.println("# punch = MEDIUM $");
      }
      else {
        // unknown punch value
        Serial.println("# Unknown punch strength $");
      }

      // reset punch
      punchStrength = 0;

      // reset led
      ledState = false;                 
      digitalWrite(LED_PIN, LOW);
    }

    // notify that we are waiting for next punch
    waitPunch = true;
  }

  // read data send from the Android device
  while(Serial.available() > 0)
  {
    inbyte = Serial.read();
    if(inbyte == 'x'){
      // android device is sending 'x' to check for active conection
      // at multiple points of the activities life cycle
      // just ignore them
      Serial.println("x from android to be ignorred");  
    }
    else{
      Serial.println(inbyte);  
    }
    
  }
  
  delay(10);
}
