package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.K_PivotSub;

public class PivotSub extends SubsystemBase{
  // These are the Pivot Motors
  // Idle - Break on both
  // ID's 5 & 6
  private final CANSparkMax motor1;
  private final CANSparkMax motor2;
  
  private final RelativeEncoder encoder1;
  private final RelativeEncoder encoder2;
  // This is the motorControllerGroup of the 2 prior motors
  // Intended to make the Pivot Point Turn
  private final MotorControllerGroup pivotMotors; 

  // Limit Switches
  // WARNING - MAKE SURE THE LIMITS ARE HAVING THE YELLOW IN GROUND!
  //           YES IT LOOKS WRONG BUT BLAME ELECTRICAL FOR THEIR WIRING!
  //           --> DEFAULT IS ALWAYS TRUE BUT WHEN HIT THEY RETURN FALSE!
  private final DigitalInput BtmLimit = new DigitalInput(0);
  private final DigitalInput TopLimit = new DigitalInput(1);

  private boolean twoMotors = true;

  // Determines if we got to stop all movement on the motor
  private boolean isStopped = false;
  private double desiredAngle = 0;
  private double maxAngle = 100;
  private double minAngle = 0;
  
  public PivotSub(){
    if(K_PivotSub.isUsingPivot){
      motor1 = new CANSparkMax(5, MotorType.kBrushless);
      motor2 = new CANSparkMax(6, MotorType.kBrushless);
      // motor2.setInverted(true);
      encoder1 = motor1.getEncoder();
      encoder2 = motor2.getEncoder();
      pivotMotors = new MotorControllerGroup(motor1, motor2);
      // pivotMotors.setInverted(isStopped);
      motor1.setIdleMode(IdleMode.kBrake);
      motor2.setIdleMode(IdleMode.kBrake);
      // set conversion factor so getPosition returns degrees
      encoder1.setPositionConversionFactor((K_PivotSub.calibrateEndingAngle-K_PivotSub.calibrateStartingAngle) / K_PivotSub.calibrateAngleEncoderValue);
      desiredAngle = encoder1.getPosition();
    }
  }

  // Handles motor movement
  // Adjusts voltage / motor speed based on difference between current and desired angle
  // Maintains 
  public void moveMotors(){
    if(isStopped)
      emergencyStop();
    else{
      double calculatedVoltage = (desiredAngle - encoder1.getPosition())/3;
      if (calculatedVoltage > K_PivotSub.pivotSpeed) {calculatedVoltage = K_PivotSub.pivotSpeed;}
      if (calculatedVoltage < -K_PivotSub.pivotSpeed) {calculatedVoltage = -K_PivotSub.pivotSpeed;}

      if ((calculatedVoltage > 0 && TopLimit.get()) || (calculatedVoltage < 0 && BtmLimit.get())) {
        (twoMotors ? pivotMotors : motor1).setVoltage(calculatedVoltage);
      } else {
        (twoMotors ? pivotMotors : motor1).setVoltage(0);
        if (!TopLimit.get())
          maxAngle = encoder1.getPosition();
        else
          minAngle = encoder1.getPosition();
      }
    }
  }

  public void setAngle (double angle) {
    desiredAngle = angle;
  }

  // Changes angle to aim for
  // If change is too far in either direction revert the change
  public void changeAngle (double increment) {
    desiredAngle += increment;
    if (desiredAngle > maxAngle) 
      desiredAngle= maxAngle;
    if (desiredAngle < minAngle) 
      desiredAngle= minAngle;
  }

  public void zeroEncoder() {
    encoder1.setPosition(0);
  }

  // Stops the motor in case of emergency
  public void emergencyStop(){
    pivotMotors.stopMotor();
  }

  // peak angle 85 needs adjustment
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Pivot Encoder 1", encoder1.getPosition());
    SmartDashboard.putNumber("Pivot Encoder 2", encoder2.getPosition());
  }
}
