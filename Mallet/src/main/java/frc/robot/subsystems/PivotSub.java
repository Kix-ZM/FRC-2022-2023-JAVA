package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PivotSub
 extends SubsystemBase{
    // These are the Pivot Motors
    // Idle - Break on both
    // ID's 5 & 6
    private final CANSparkMax motor1 = new CANSparkMax(5, MotorType.kBrushless);
    private final CANSparkMax motor2 = new CANSparkMax(6, MotorType.kBrushless);
    private final RelativeEncoder encoder1 = motor1.getEncoder();
    private final RelativeEncoder encoder2 = motor2.getEncoder();
    // This is the motorControllerGroup of the 2 prior motors
    // Intended to make the Pivot Point Turn
    private final MotorControllerGroup pivotMotors = new MotorControllerGroup(motor1, motor2);

    // Limit Switches
    // WARNING - MAKE SURE THE LIMITS ARE HAVING THE YELLOW IN GROUND!
    //           YES IT LOOKS WRONG BUT BLAME ELECTRICAL FOR THEIR WIRING!
    //           --> DEFAULT IS ALWAYS TRUE BUT WHEN HIT THEY RETURN FALSE!
    private final DigitalInput BtmLimit = new DigitalInput(0);
    private final DigitalInput TopLimit = new DigitalInput(1);

    // Determines if we got to stop all movement on the motor
    private boolean isStopped = false;
    private double pivotSpeed = 2.2;
    private double desiredAngle = 0;
    
    public PivotSub(){
      if(Constants.isUsingPivot){
        motor1.setIdleMode(IdleMode.kBrake);
        motor2.setIdleMode(IdleMode.kBrake);
        // set conversion factor so getPosition returns degrees
        encoder1.setPositionConversionFactor((Constants.calibrateEndingAngle-Constants.calibrateStartingAngle) / Constants.calibrateAngleEncoderValue);
        desiredAngle = encoder1.getPosition();

        encoder2.setPosition(0);
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
        if (calculatedVoltage > pivotSpeed) {calculatedVoltage = pivotSpeed;}
        if (calculatedVoltage < -pivotSpeed) {calculatedVoltage = -pivotSpeed;}

        boolean top = encoder1.getPosition()>100 ? false : true;
        if ((calculatedVoltage > 0 && top) || (calculatedVoltage < 0 && BtmLimit.get())) {
          motor1.setVoltage(calculatedVoltage);
        } else {
          motor1.setVoltage(0);
        }
      }
    }

    public void setAngle (double angle) {
      desiredAngle = angle;
    }

    public void changeAngle (double increment) {
      desiredAngle += increment;
      if (desiredAngle > 100) 
        desiredAngle=100;
      if (desiredAngle < 0) 
        desiredAngle=0;
    }

    // Stops the motor in case of emergency
    public void emergencyStop(){
      pivotMotors.stopMotor();
    }

  // peak angle 85 needs adjustment
  @Override
  public void periodic() {
    // SmartDashboard.putNumber("Pivot Encoder 1", -encoder1.getPosition());
    SmartDashboard.putNumber("Pivot Encoder 1", -encoder1.getPosition());

    SmartDashboard.putNumber("Pivot Encoder 2", encoder2.getPosition());

  }
}
