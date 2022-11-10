/* This demo uses the Zumo 32U4's accelerometer to detect
whether it is on a slanted surface.  If it is on a slanted
surface, then it uses the motors to face uphill.

It also uses the encoders to avoid rolling down the surface.

Please note that this example is not very robust and it might be
hard to modify it to behave differently.  The accelerometer
readings are affected by the movement of the Zumo, so if you
change the code for controlling the motors, you might also affect
the accelerometer readings.

This code was tested on a Zumo with 4 NiMH batteries and two 75:1
HP micro metal gearmotors.  If you have different batteries or
motors, you might need to adjust the formula for computing
turnSpeed.

Also, if the robot is pointing directly downhill, it might not
move, because the y component of the acceleration would be close
to 0. */

#include <Wire.h>
#include <Zumo32U4.h>

const int16_t maxSpeed = 150;

// Change next line to this if you are using the older Zumo 32U4
// with a black and green LCD display:
// Zumo32U4LCD display;
Zumo32U4OLED display;

Zumo32U4IMU imu;
Zumo32U4Motors motors;
Zumo32U4ButtonA buttonA;
Zumo32U4Encoders encoders;

void setup()
{
  // Start I2C and initialize the IMU sensors.
  Wire.begin();
  imu.init();
  imu.enableDefault();
  imu.configureForFaceUphill();

  display.clear();
  display.print(F("Press A"));
  buttonA.waitForPress();
  display.clear();
}

void loop()
{
  // Read the accelerometer.
  // A value of 16384 corresponds to approximately 1 g.
  imu.readAcc();
  int16_t x = imu.a.x;
  int16_t y = imu.a.y;
  int32_t magnitudeSquared = (int32_t)x * x + (int32_t)y * y;

  // Show the X and Y acceleration values on the display
  // every 150 ms.
  static uint8_t lastDisplayTime;
  if ((uint8_t)(millis() - lastDisplayTime) > 150)
  {
    lastDisplayTime = millis();
    display.gotoXY(0, 0);
    display.print(x);
    display.print(F("       "));
    display.gotoXY(0, 1);
    display.print(y);
    display.print(F("       "));
  }

  // Use the encoders to see how much we should drive forward.
  // If the robot rolls downhill, the encoder counts will become
  // negative, resulting in a positive forwardSpeed to counteract
  // the rolling.
  int16_t forwardSpeed = -(encoders.getCountsLeft() + encoders.getCountsRight());
  forwardSpeed = constrain(forwardSpeed, -maxSpeed, maxSpeed);

  // See if we are actually on an incline.
  // 16384 * sin(5 deg) = 1427
  int16_t turnSpeed;
  if (magnitudeSquared > (int32_t)1427 * 1427)
  {
    // We are on an incline of more than 5 degrees, so
    // try to face uphill using a feedback algorithm.
    turnSpeed = y / 16;
    ledYellow(1);
  }
  else
  {
    // We not on a noticeable incline, so don't turn.
    turnSpeed = 0;
    ledYellow(0);
  }

  // To face uphill, we need to turn so that the X acceleration
  // is negative and the Y acceleration is 0.  Therefore, when
  // the Y acceleration is positive, we want to turn to the
  // left (counter-clockwise).
  int16_t leftSpeed = forwardSpeed - turnSpeed;
  int16_t rightSpeed = forwardSpeed + turnSpeed;

  // Constrain the speeds to be between -maxSpeed and maxSpeed.
  leftSpeed = constrain(leftSpeed, -maxSpeed, maxSpeed);
  rightSpeed = constrain(rightSpeed, -maxSpeed, maxSpeed);

  motors.setSpeeds(leftSpeed, rightSpeed);
}

