package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.K_ClawSub;

public class ClawSub extends SubsystemBase{
  // This is the Claw Extension Motor
  // Idle - Break
  // ID - 8
  private final CANSparkMax motor;
  private final RelativeEncoder encoder;
  
  // Limit Switch
  // WARNING - MAKE SURE THE LIMITS ARE HAVING THE YELLOW IN GROUND!
  //           YES IT LOOKS WRONG BUT BLAME ELECTRICAL FOR THEIR WIRING!
  //           --> DEFAULT IS ALWAYS TRUE BUT WHEN HIT THEY RETURN FALSE!
  private final DigitalInput clampLimit;

  // Calculation variables
  private double desiredAngle = 0;

  // close close to full clamp point
  private double maxAngle = 150;

  private boolean isOpen = true;

  private boolean isStopped = false;

  // 0 is cube
  // 1 is cone
  private int targetType = 1;
  
  public ClawSub(){
    if(K_ClawSub.isUsingClaw){
      motor = new CANSparkMax(7, MotorType.kBrushless);
      encoder = motor.getEncoder();
      clampLimit = new DigitalInput(2);
      motor.setIdleMode(IdleMode.kBrake);
      // set conversion factor so getPosition returns degrees
      encoder.setPositionConversionFactor((K_ClawSub.calibrateEndingAngle-K_ClawSub.calibrateStartingAngle) / K_ClawSub.calibrateAngleEncoderValue);
      // code to set default to find conversion factor
      // encoder.setPositionConversionFactor(1);
      encoder.setPosition(0);
      motor.setInverted(true);
      motor.setSmartCurrentLimit(8, 100);
    }
  }

  //Return the encoder
  public RelativeEncoder getClawEncoder(){
    return encoder;
  }

  //Return if the claw is open
  public boolean getIsOpen(){
    return isOpen;
  }

  // Handles motor movement
  // Adjusts voltage / motor speed based on difference between current and desired angle
  // - voltage is close claw
  public void moveMotors(){
    if(K_ClawSub.isUsingClaw){
      isOpen = true;
      if(isStopped)
        emergencyStop();
      else{
        double calculatedVoltage = (desiredAngle - encoder.getPosition())/5;
        // Controller (if used) deadzone
        if (Math.abs(calculatedVoltage) < 0.01)
          calculatedVoltage = 0;
        // If calculated voltage too high reset it to within +- maximum clamp seed
        if (Math.abs(calculatedVoltage) > K_ClawSub.clampVoltage)
          calculatedVoltage = calculatedVoltage > 0 ? K_ClawSub.clampVoltage : -K_ClawSub.clampVoltage;
        // Makes sure only moving if not triggering limit switches
        if ((calculatedVoltage < 0 && clampLimit.get()) || calculatedVoltage > 0) {
          motor.setVoltage(calculatedVoltage);
        } else {
          motor.setVoltage(0);
          // if at a limit switch, then set maximum or minimum
          if (!clampLimit.get())
            maxAngle = encoder.getPosition();
        }
      }
    }
  }

  public boolean clamp() {
    if(K_ClawSub.isUsingClaw){
      // if current not at max (current increases when motor experiences resistance / is clamped on something)
      // then keep clamping down
      if (motor.getOutputCurrent() < K_ClawSub.coneMaxCurrent) {
        motor.setVoltage(-K_ClawSub.clampVoltage);
        return false;
      } 
      // otherwise maintain position and say it's done
      else {
        desiredAngle = encoder.getPosition()-2;
        return true;
      }
    }
    return false;
  }

  public void clamp2() {
    double currentLimit = targetType == 1 ? K_ClawSub.coneMaxCurrent : K_ClawSub.cubeMaxCurrent;
    if(K_ClawSub.isUsingClaw){
      // if current not at max (current increases when motor experiences resistance / is clamped on something)
      // then keep clamping down
      motor.setVoltage(K_ClawSub.clampVoltage);
      // if (motor.getOutputCurrent() >= currentLimit - 10) {
      //   zeroEncoder();
      //   desiredAngle = encoder.getPosition() + 90;
      // }
      isOpen = false;
    }
  }

  // 0 is cube
  // 1 is cone
  public void setTargetType(int index) {
    targetType = index;
  }

  // set to specific angle (not that accurate because we might end up starting at different positions)
  public void setAngle (double angle) {
    if(K_ClawSub.isUsingClaw){
      desiredAngle = angle;
    }
  }

  //Returns the current angle of the motor
  public double getCurrentAngle(){
    if(K_ClawSub.isUsingClaw){
      return encoder.getPosition();
    }
    return 0.0;
  }

  //Return the intended angle of the motor
  public double getDesiredAngle(){
    if(K_ClawSub.isUsingClaw){
      return desiredAngle;
    }
    return 0.0;
  }


  // Changes angle to aim for
  // If change is too far in either direction revert the change
  public void changeAngle (double increment) {
    if(K_ClawSub.isUsingClaw){
      // controller deadzone
      if (Math.abs(increment) > .05 && desiredAngle < maxAngle) {
        if (motor.getOutputCurrent() < K_ClawSub.coneMaxCurrent  || increment > 0) {
          desiredAngle += increment;
          if (increment > 0)
            isOpen = true;
        }
        else {
          desiredAngle = encoder.getPosition()-4;
          isOpen = false;
          maxAngle = encoder.getPosition()+150;
        }
      }
    }
  }

  // returns current through motor
  public double getCurrent() {
    if(K_ClawSub.isUsingClaw){
      return motor.getOutputCurrent();
    }
    return 0.0;
  }

  // sets current position here
  public void zeroEncoder() {
    if(K_ClawSub.isUsingClaw){
      encoder.setPosition(0);
      desiredAngle = 0;
    }
  }

  public boolean isOpen() {
    if(K_ClawSub.isUsingClaw){
      return isOpen;
    }
    return false;
  }

  // Stops the motor in case of emergency
  public void emergencyStop(){
    if(K_ClawSub.isUsingClaw){
      motor.stopMotor();
    }
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Claw Current", motor.getOutputCurrent());
  }
}
