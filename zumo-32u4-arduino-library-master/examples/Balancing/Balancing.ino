/* This example shows how to use the Zumo 32U4's accelerometer
and gyro to balance on its front end.

Please note that the balancing algorithm in this code is not
perfect: the robot tends to drift away from its starting position
over time.  We found that this code works better on carpet than
on a hard surface.

You will have to remove the Zumo's blade and front sensor array if
they are installed. After removing the blade, be sure to reinstall
the screws that were holding the blade.

This code is sensitive to changes in the Zumo's center of mass,
so make sure the display is plugged in.

This code was designed for Zumos with 75:1 HP micro metal
gearmotors, and it might need to be adjusted to work on Zumos
with other types of motors. */

#include <Wire.h>
#include <Zumo32U4.h>

// Change next line to this if you are using the older Zumo 32U4
// with a black and green LCD display (and see note for the
// targetAngle constant below):
// Zumo32U4LCD display;
Zumo32U4OLED display;

Zumo32U4ButtonA buttonA;
Zumo32U4ButtonB buttonB;
Zumo32U4ButtonC buttonC;
Zumo32U4Motors motors;
Zumo32U4Buzzer buzzer;
Zumo32U4IMU imu;

// This is the average reading obtained from the gyro's Y axis
// during calibration.
float gyroOffsetY;

// This variable holds our estimation of the robot's angle based
// on the gyro and the accelerometer.  A value of 0 means the
// robot is perfectly vertical.  A value of -90 means that the
// robot is horizontal and the battery holder is facing down.  A
// value of 90 means that the robot is horizontal and the battery
// holder is facing up.
float angle = 0;

// This is just like "angle", but it is based solely on the
// accelerometer.
float aAngle = 0;

// This is the angle the robot tries to balance at.  Since the LCD and OLED
// displays have different weights and cause the Zumo to balance differently,
// the best target angle will depend on which version of the Zumo 32U4 you have.
// We suggest starting with a value of 4.0 for the OLED version or 2.0 for the
// LCD version.
const float targetAngle = 4.0;

void setup()
{
  Wire.begin();

  // Set up the inertial sensors.
  imu.init();
  imu.enableDefault();
  imu.configureForBalancing();

  display.clear();
  display.print(F("Gyro cal"));
  ledYellow(1);

  // Delay to give the user time to remove their finger.
  delay(500);

  // Calibrate the gyro.
  for (uint16_t i = 0; i < 1024; i++)
  {
    // Wait for new data to be available, then read it.
    while(!imu.gyroDataReady()) {}
    imu.readGyro();

    // Add the Y axis reading to the total.
    gyroOffsetY += imu.g.y;
  }
  gyroOffsetY /= 1024;

  display.clear();
  ledYellow(0);

  // Display the angle until the user presses A.
  while (!buttonA.getSingleDebouncedRelease())
  {
    // Update the angle using the gyro as often as possible.
    updateAngleGyro();

    // Every 20 ms (50 Hz), correct the angle using the
    // accelerometer and also print it.
    static uint8_t lastCorrectionTime = 0;
    uint8_t m = millis();
    if ((uint8_t)(m - lastCorrectionTime) >= 20)
    {
      lastCorrectionTime = m;
      correctAngleAccel();
      printAngles();
    }
  }
  delay(500);
}

void loop()
{
  // Update the angle using the gyro as often as possible.
  updateAngleGyro();

  // Every 20 ms (50 Hz), correct the angle using the
  // accelerometer, print it, and set the motor speeds.
  static byte lastCorrectionTime = 0;
  byte m = millis();
  if ((byte)(m - lastCorrectionTime) >= 20)
  {
    lastCorrectionTime = m;
    correctAngleAccel();
    printAngles();
    setMotors();
  }
}

void printAngles()
{
  display.gotoXY(0, 0);
  display.print(angle);
  display.print(F("  "));

  display.gotoXY(0, 1);
  display.print(aAngle);
  display.print("  ");
}

// Reads the gyro and uses it to update the angle estimation.
void updateAngleGyro()
{
  // Figure out how much time has passed since the last update.
  static uint16_t lastUpdate = 0;
  uint16_t m = micros();
  uint16_t dt = m - lastUpdate;
  lastUpdate = m;

  imu.readGyro();

  // Calculate how much the angle has changed, in degrees, and
  // add it to our estimation of the current angle.  The gyro's
  // sensitivity is 0.07 dps per digit.
  angle += ((float)imu.g.y - gyroOffsetY) * 70 * dt / 1000000000;
}

template <typename Ta, typename Tb> float vector_dot(const Zumo32U4IMU::vector<Ta> *a, const Zumo32U4IMU::vector<Tb> *b)
{
  return (a->x * b->x) + (a->y * b->y) + (a->z * b->z);
}

// Reads the accelerometer and uses it to adjust the angle
// estimation.
void correctAngleAccel()
{
  imu.readAcc();

  // Calculate the angle according to the accelerometer.
  aAngle = -atan2(imu.a.z, -imu.a.x) * 180 / M_PI;

  // Calculate the magnitude of the measured acceleration vector,
  // in units of g.
  Zumo32U4IMU::vector<float> const aInG = {
    (float)imu.a.x / 4096,
    (float)imu.a.y / 4096,
    (float)imu.a.z / 4096}
  ;
  float mag = sqrt(vector_dot(&aInG, &aInG));

  // Calculate how much weight we should give to the
  // accelerometer reading.  When the magnitude is not close to
  // 1 g, we trust it less because it is being influenced by
  // non-gravity accelerations, so we give it a lower weight.
  float weight = 1 - 5 * abs(1 - mag);
  weight = constrain(weight, 0, 1);
  weight /= 10;

  // Adjust the angle estimation.  The higher the weight, the
  // more the angle gets adjusted.
  angle = weight * aAngle + (1 - weight) * angle;
}

// This function uses our current angle estimation and a PID
// algorithm to set the motor speeds.  This is the core of the
// robot's balancing algorithm.
void setMotors()
{
  int32_t speed;
  if (abs(angle) > 45)
  {
    // If the robot is tilted more than 45 degrees, it is
    // probably going to fall over.  Stop the motors to prevent
    // it from running away.
    speed = 0;
  }
  else
  {
    static float lastError = 0;
    static float integral = 0;

    float error = angle - targetAngle;

    integral += error;
    integral = constrain(integral, -40, 40);

    float errorDifference = error - lastError;
    speed = error * 35 + errorDifference * 10 + integral * 5;
    speed = constrain(speed, -400, 400);

    lastError = error;
  }
  motors.setSpeeds(speed, speed);
}