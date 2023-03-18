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

  private boolean twoMotors = false;

  // Determines if we got to stop all movement on the motor
  private boolean isStopped = false;
  private double desiredAngle = 6;
  private double maxAngle = 175;
  private double minAngle = 0;
  
  public PivotSub(){
    if(K_PivotSub.isUsingPivot){
      motor1 = new CANSparkMax(5, MotorType.kBrushless);
      if (twoMotors) {
        motor2 = new CANSparkMax(10, MotorType.kBrushless);
        encoder2 = motor2.getEncoder();
        motor2.setIdleMode(IdleMode.kBrake);
        pivotMotors = new MotorControllerGroup(motor1, motor2);
      } else {
        motor2 = null;
        encoder2 = null;
        pivotMotors = null;
      }
      // motor2.setInverted(true);
      encoder1 = motor1.getEncoder();
      // pivotMotors.setInverted(isStopped);
      motor1.setIdleMode(IdleMode.kBrake);

      // set conversion factor so getPosition returns degrees
      encoder1.setPositionConversionFactor((K_PivotSub.calibrateEndingAngle-K_PivotSub.calibrateStartingAngle) / K_PivotSub.calibrateAngleEncoderValue);
      // set conversion ratio to 1 ONLY FOR CALIBRATING FOR ANGLE
      // encoder1.setPositionConversionFactor(1);

      encoder1.setPosition(6);
      desiredAngle = encoder1.getPosition();
    }
  }

  //Return the encoder
  public RelativeEncoder getEncoder1(){
    return encoder1;
  }

  //Return the maxAngle
  public double getMaxAngle(){
    return maxAngle;
  }

  // Handles motor movement
  // Adjusts voltage / motor speed based on difference between current and desired angle
  // Maintains 
  public void moveMotors(){
    if(K_PivotSub.isUsingPivot){
      if(isStopped)
        emergencyStop();
      else{
        double calculatedVoltage = (desiredAngle - encoder1.getPosition())/3;
        //System.out.println("Calced Voltage: " + calculatedVoltage);
        if (calculatedVoltage > K_PivotSub.pivotSpeed) {calculatedVoltage = K_PivotSub.pivotSpeed;}
        if (calculatedVoltage < -K_PivotSub.pivotSpeed) {calculatedVoltage = -K_PivotSub.pivotSpeed;}

        if ((calculatedVoltage > 0 && TopLimit.get()) || (calculatedVoltage < 0 && BtmLimit.get())) {
          (twoMotors ? pivotMotors : motor1).setVoltage(calculatedVoltage);
        } else {
          (twoMotors ? pivotMotors : motor1).setVoltage(0);
          // removed set soft min/maxes on limits
          if (!BtmLimit.get())
            encoder1.setPosition(6);
          if (!TopLimit.get())
            encoder1.setPosition(120);
        }
      }
    }
  }

  // sets the desired angle to set angle to
  // 0 - 100 degrees
  public void setAngle (double angle) {
    if(K_PivotSub.isUsingPivot){
      desiredAngle = angle;
    }
  }

  //Returns the current angle of the pivot
  public double getCurrentAngle(){
    if(K_PivotSub.isUsingPivot){
      if(twoMotors){
        return (encoder1.getPosition() + encoder2.getPosition())/2;
      }
      return encoder1.getPosition();
    }
    return 0.0;
  }

  //Returns the current desired angle
  public double getDesiredAngle(){
    if(K_PivotSub.isUsingPivot){
      return desiredAngle;
    }
    return 0.0;
  }

  // Changes angle to aim for
  // If change is past min or max in either direction revert the change
  public void changeAngle (double increment) {
    if(K_PivotSub.isUsingPivot){
      if ((increment > 0 && TopLimit.get()) || (increment < 0 && BtmLimit.get())) {
        desiredAngle += increment;
      }
      if (desiredAngle > maxAngle) 
        desiredAngle= maxAngle;
      if (desiredAngle < minAngle) 
        desiredAngle= minAngle;
    }
  }

  public void zeroEncoder() {
    if(K_PivotSub.isUsingPivot){
      encoder1.setPosition(5);
      desiredAngle = 5;
    }
  }

  public void resetDesiredToMin() {
    desiredAngle = minAngle;
  }

  // Stops the motor in case of emergency
  public void emergencyStop() {
    if(K_PivotSub.isUsingPivot){
      pivotMotors.stopMotor();
    }
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Bottom Limit", BtmLimit.get());
    SmartDashboard.putNumber("Pivot Encoder", encoder1.getPosition());
    SmartDashboard.putBoolean("Top Limit", TopLimit.get());
  }
}
