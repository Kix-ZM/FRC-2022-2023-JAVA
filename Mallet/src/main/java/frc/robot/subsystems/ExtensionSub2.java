package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.K_ExtSub;

public class ExtensionSub2 extends SubsystemBase{
  // This is the Extension Motor
  // Idle - Break
  // ID - 6
  private final CANSparkMax motor = new CANSparkMax(6, MotorType.kBrushless);
  private final RelativeEncoder encoder = motor.getEncoder();
  
  // Limit Switch
  // WARNING - MAKE SURE THE LIMITS ARE HAVING THE YELLOW IN GROUND!
  //           YES IT LOOKS WRONG BUT BLAME ELECTRICAL FOR THEIR WIRING!
  //           --> DEFAULT IS ALWAYS TRUE BUT WHEN HIT THEY RETURN FALSE!
  private final DigitalInput clampLimit = new DigitalInput(4);

  // Calculation variables
  private double desiredPosition = 0;
  // open further than default point
  private double minPosition = -9;
  // close close to full clamp point
  private double maxPosition = 0;

  private boolean isStopped = false;

  private ShuffleboardTab tab = Shuffleboard.getTab("Arm");
  
  public ExtensionSub2(){
    if(K_ExtSub.isUsingExt){
      motor.setIdleMode(IdleMode.kBrake);
      // set conversion factor so getPosition returns degrees
      encoder.setPositionConversionFactor((K_ExtSub.calibrateEndingAngle-K_ExtSub.calibrateStartingAngle) / K_ExtSub.calibrateAngleEncoderValue);
      
      // code to set default to find conversion factor
      // encoder.setPositionConversionFactor(1);
      encoder.setPosition(0);
      motor.setInverted(true);
    }
  }

  // Handles motor movement
  // Applies voltage to move in desired direction as long as not in contact with limit switches
  public void moveMotors(){
    if(isStopped)
      emergencyStop();
    else{
      double calculatedVoltage = (desiredPosition - encoder.getPosition());
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

  public void setAngle (double angle) {
    desiredPosition = angle;
  }

  // Changes angle to aim for
  // If change is too far in either direction revert the change
  public void changePosition (double increment) {
    // controller deadzone
    desiredPosition += increment;
    if (desiredPosition > maxPosition) 
      desiredPosition= maxPosition;
    else if (desiredPosition < minPosition) 
      desiredPosition= minPosition;
    SmartDashboard.putNumber("Ext Increment", increment);
  }

  // sets current position here
  public void zeroEncoder() {
    encoder.setPosition(0);
    desiredPosition = 0;
  }

  // Stops the motor in case of emergency
  public void emergencyStop(){
    motor.stopMotor();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Ext Encoder", encoder.getPosition());
    SmartDashboard.putNumber("Ext Current", motor.getOutputCurrent());
    SmartDashboard.putNumber("Ext Desired Angle", desiredPosition);
    SmartDashboard.putBoolean("Ext Inverted", motor.getInverted());
  }
}
