package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.K_ClawSub;;

public class ClawSub extends SubsystemBase{
  // This is the Claw Extension Motor
  // Idle - Break
  // ID - 8
  private final CANSparkMax motor = new CANSparkMax(6, MotorType.kBrushless);
  private final RelativeEncoder encoder = motor.getEncoder();
  
  // Limit Switch
  // WARNING - MAKE SURE THE LIMITS ARE HAVING THE YELLOW IN GROUND!
  //           YES IT LOOKS WRONG BUT BLAME ELECTRICAL FOR THEIR WIRING!
  //           --> DEFAULT IS ALWAYS TRUE BUT WHEN HIT THEY RETURN FALSE!
  private final DigitalInput clampLimit = new DigitalInput(4);

  // Calculation variables
  private double desiredAngle = 0;
  // open further than default point
  private double minAngle = 0;
  // close close to full clamp point
  private double maxAngle = 60;

  private boolean isOpen = true;

  private boolean isStopped = false;
  
  public ClawSub(){
    if(K_ClawSub.isUsingClaw){
      motor.setIdleMode(IdleMode.kBrake);
      // set conversion factor so getPosition returns degrees
      encoder.setPositionConversionFactor((K_ClawSub.calibrateEndingAngle-K_ClawSub.calibrateStartingAngle) / K_ClawSub.calibrateAngleEncoderValue);
      
      // code to set default to find conversion factor
      // encoder.setPositionConversionFactor(1);
      encoder.setPosition(0);
      motor.setInverted(true);
      minAngle = desiredAngle;
      maxAngle = desiredAngle + maxAngle;
    }
  }

  // Handles motor movement
  // Adjusts voltage / motor speed based on difference between current and desired angle
  // - voltage is close claw
  public void moveMotors(){
    if(isStopped)
      emergencyStop();
    else{
      double calculatedVoltage = (desiredAngle - encoder.getPosition())/5;
      // Controller (if used) deadzone
      if (Math.abs(calculatedVoltage) < 0.01)
        calculatedVoltage = 0;
      // If calculated voltage too high reset it to within +- maximum clamp seed
      if (Math.abs(calculatedVoltage) > K_ClawSub.clampSpeed)
        calculatedVoltage = calculatedVoltage > 0 ? K_ClawSub.clampSpeed : -K_ClawSub.clampSpeed;
      // Makes sure only moving if not triggering limit switches
      if ((calculatedVoltage < 0 && clampLimit.get()) || calculatedVoltage > 0) {
        motor.setVoltage(calculatedVoltage);
      } else {
        motor.setVoltage(0);
        // if at a limit switch, then set maximum or minimum
        if (!clampLimit.get())
          maxAngle = encoder.getPosition();
        else
          minAngle = encoder.getPosition();
      }
    }
  }

  public boolean clamp() {
    // if current not at max (current increases when motor experiences resistance / is clamped on something)
    // then keep clamping down
    if (motor.getOutputCurrent() < K_ClawSub.maxCurrent) {
      motor.setVoltage(-K_ClawSub.clampSpeed);
      return false;
    } 
    // otherwise maintain position and say it's done
    else {
      desiredAngle = encoder.getPosition()-2;
      isOpen = false;
      return true;
    }
  }

  public void setAngle (double angle) {
    desiredAngle = angle;
  }

  // opens claw by 30 degrees to open it
  public void openClaw() {
    desiredAngle+=50;
    isOpen = true;
  }

  // Changes angle to aim for
  // If change is too far in either direction revert the change
  public void changeAngle (double increment) {
    // controller deadzone
    if (Math.abs(increment) > .05) {
      if (motor.getOutputCurrent() < K_ClawSub.maxCurrent  || increment > 0) {
        desiredAngle += increment;
        if (increment > 0)
          isOpen = true;
      }
      else {
        desiredAngle = encoder.getPosition()-4;
      }
    }
    SmartDashboard.putNumber("increment", increment);
  }

  // returns current through motor
  public double getCurrent() {
    return motor.getOutputCurrent();
  }

  // sets current position here
  public void zeroEncoder() {
    encoder.setPosition(0);
    desiredAngle = 0;
  }

  public boolean isOpen() {
    return isOpen;
  }

  // Stops the motor in case of emergency
  public void emergencyStop(){
    motor.stopMotor();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Claw Encoder", encoder.getPosition());
    SmartDashboard.putNumber("Claw Current", motor.getOutputCurrent());
    SmartDashboard.putNumber("Desired Angle", desiredAngle);
    SmartDashboard.putBoolean("Inverted", motor.getInverted());
    SmartDashboard.putBoolean("Claw Open", isOpen);

  }
}
