package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.K_ClawSub;;

public class ClawSubV2 extends SubsystemBase{
  // This is the Claw Extension Motor
  // Idle - Break
  // ID - 8
  private final CANSparkMax motor = new CANSparkMax(8, MotorType.kBrushless);
  private final RelativeEncoder encoder = motor.getEncoder();
  
  // Limit Switch
  // WARNING - MAKE SURE THE LIMITS ARE HAVING THE YELLOW IN GROUND!
  //           YES IT LOOKS WRONG BUT BLAME ELECTRICAL FOR THEIR WIRING!
  //           --> DEFAULT IS ALWAYS TRUE BUT WHEN HIT THEY RETURN FALSE!
  private final DigitalInput clampLimit = new DigitalInput(4);

  // Calculation variables
  private double desiredAngle;
  // open further than default point
  private double minAngle = -10;
  // close close to full clamp point
  private double maxAngle = 10;

  private boolean isStopped = false;
  
  public ClawSubV2(){
    if(K_ClawSub.isUsingClaw){
      motor.setIdleMode(IdleMode.kBrake);
      // set conversion factor so getPosition returns degrees
      encoder.setPositionConversionFactor((K_ClawSub.calibrateEndingAngle-K_ClawSub.calibrateStartingAngle) / K_ClawSub.calibrateAngleEncoderValue);
      desiredAngle = encoder.getPosition();
    }
  }

  // Handles motor movement
  // Adjusts voltage / motor speed based on difference between current and desired angle
  // Maintains 
  public void moveMotors(){
    if(isStopped)
      emergencyStop();
    else{
      double calculatedVoltage = (desiredAngle - encoder.getPosition())/3;
      if (calculatedVoltage > K_ClawSub.clampSpeed) {calculatedVoltage = K_ClawSub.clampSpeed;}
      if (calculatedVoltage < -K_ClawSub.clampSpeed) {calculatedVoltage = -K_ClawSub.clampSpeed;}

      if ((calculatedVoltage > 0 && clampLimit.get())) {
        motor.setVoltage(calculatedVoltage);
      } else {
        motor.setVoltage(0);
        if (!clampLimit.get())
          maxAngle = encoder.getPosition();
        else
          minAngle = encoder.getPosition();
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
    encoder.setPosition(0);
  }

  // Stops the motor in case of emergency
  public void emergencyStop(){
    motor.stopMotor();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Claw Encoder", encoder.getPosition());
  }
}
