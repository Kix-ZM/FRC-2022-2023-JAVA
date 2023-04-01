package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.K_ExtSub;

public class ExtensionSub extends SubsystemBase{
  // This is the Extension Motor
  // Idle - Break
  // ID - 6
  private final CANSparkMax motor;
  private final RelativeEncoder encoder;
  
  // Limit Switch
  // WARNING - MAKE SURE THE LIMITS ARE HAVING THE YELLOW IN GROUND!
  //           YES IT LOOKS WRONG BUT BLAME ELECTRICAL FOR THEIR WIRING!
  //           --> DEFAULT IS ALWAYS TRUE BUT WHEN HIT THEY RETURN FALSE!
  private final DigitalInput clampLimit;

  // Calculation variables
  private double desiredPosition = 0;
  // open further than default point
  private double minPosition = -12;
  // close close to full clamp point
  private double maxPosition = 0;

  private boolean isStopped = false;
  
  public ExtensionSub(){
    if(K_ExtSub.isUsingExt){
      motor = new CANSparkMax(6, MotorType.kBrushless);
      encoder  = motor.getEncoder();
      clampLimit = new DigitalInput(4);
      motor.setIdleMode(IdleMode.kBrake);
      // set conversion factor so getPosition returns degrees
      encoder.setPositionConversionFactor((K_ExtSub.calibrateEndingAngle-K_ExtSub.calibrateStartingAngle) / K_ExtSub.calibrateAngleEncoderValue);
      
      // code to set default to find conversion factor
      // encoder.setPositionConversionFactor(1);
      encoder.setPosition(0);
      motor.setInverted(true);
      // motor.setSmartCurrentLimit(10, 100);
      motor.setSmartCurrentLimit(30, 100);
    } else {
      motor = null;
      encoder = null;
      clampLimit = null;
    }
  }

  //Return the encoder
  public RelativeEncoder getEncoder(){
    return encoder;
  }

  // Handles motor movement
  // Applies voltage to move in desired direction as long as not in contact with limit switches
  public void moveMotors(){
    if(K_ExtSub.isUsingExt){
      if(isStopped)
        emergencyStop();
      else{
        double calculatedVoltage = (desiredPosition - encoder.getPosition()*1.2);
        // Controller (if used) deadzone
        if (Math.abs(calculatedVoltage) < 0.01)
          calculatedVoltage = 0;
        // If calculated voltage too high reset it to within +- maximum extension seed
        if (Math.abs(calculatedVoltage) > K_ExtSub.extSpeed)
          calculatedVoltage = calculatedVoltage > 0 ? K_ExtSub.extSpeed : -K_ExtSub.extSpeed;
        // Makes sure only moving if not triggering limit switches
        if ((calculatedVoltage < 0 || clampLimit.get()) || calculatedVoltage > 0) {
          motor.setVoltage(calculatedVoltage);
        } else {
          motor.setVoltage(0);
          // if at a limit switch, then set maximum or minimum
          if (!clampLimit.get())
            maxPosition = encoder.getPosition();
          else
            minPosition = encoder.getPosition();
        }
      }
    }
  }

  public void setPosition (double angle) {
    if(K_ExtSub.isUsingExt){
      desiredPosition = angle;
    }
  }

  //Returns the current angle
  public double getCurrentPosition(){
    if(K_ExtSub.isUsingExt){
      return encoder.getPosition();
    }
    return 0.0;
  }

  //Returns the desired angle
  public double getDesiredPosition(){
    if(K_ExtSub.isUsingExt){
      return desiredPosition;
    }
    return 0.0;
  }

  // Changes angle to aim for
  // If change is too far in either direction revert the change
  public void changePosition (double increment) {
    if(K_ExtSub.isUsingExt){
      // controller deadzone
      desiredPosition += increment;
      if (desiredPosition > maxPosition) 
        desiredPosition= maxPosition;
      else if (desiredPosition < minPosition) 
        desiredPosition= minPosition;
    }
  }

  // sets current position here
  public void zeroEncoder() {
    if(K_ExtSub.isUsingExt){
      encoder.setPosition(0);
      desiredPosition = 0;
    }
  }

  // Stops the motor in case of emergency
  public void emergencyStop(){
    if(K_ExtSub.isUsingExt){
      motor.stopMotor();
    }
  }

  @Override
  public void periodic() {
    if (K_ExtSub.isUsingExt)
      SmartDashboard.putNumber("Extend Current", motor.getOutputCurrent());
  }
}
