package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.K_PivotSub;

public class PivotSubPID extends SubsystemBase{
  // These are the Pivot Motors
  // Idle - Break on both
  // ID's 5 & 6
  private final CANSparkMax motor;
  private final SparkMaxPIDController pid;
  private final RelativeEncoder encoder;
  private double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxVel, minVel, maxAcc, allowedErr;
  // This is the motorControllerGroup of the 2 prior motors
  // Intended to make the Pivot Point Turn

  // Limit Switches
  // WARNING - MAKE SURE THE LIMITS ARE HAVING THE YELLOW IN GROUND!
  //           YES IT LOOKS WRONG BUT BLAME ELECTRICAL FOR THEIR WIRING!
  //           --> DEFAULT IS ALWAYS TRUE BUT WHEN HIT THEY RETURN FALSE!
  private final DigitalInput BtmLimit = new DigitalInput(0);
  private final DigitalInput TopLimit = new DigitalInput(1);

  // Determines if we got to stop all movement on the motor
  private double desiredAngle = 6;
  private double maxAngle = 175;
  private double minAngle = 0;
  
  public PivotSubPID(){
    if(K_PivotSub.isUsingPivot){
      motor = new CANSparkMax(5, MotorType.kBrushless);
      encoder = motor.getEncoder();
      pid = motor.getPIDController();
      motor.setIdleMode(IdleMode.kBrake);

      // set conversion factor so getPosition returns degrees
      encoder.setPositionConversionFactor((K_PivotSub.calibrateEndingAngle-K_PivotSub.calibrateStartingAngle) / K_PivotSub.calibrateAngleEncoderValue);
      // set conversion ratio to 1 ONLY FOR CALIBRATING FOR ANGLE
      // encoder1.setPositionConversionFactor(1);

      encoder.setPosition(0);
      desiredAngle = encoder.getPosition();

      // PID coefficients
      kP = 0.0000002015; 
      kI = 0.0000005;
      kD = 0; 
      kIz = 0.005; 
      kFF = 0.000101; 
      kMaxOutput = .22; 
      kMinOutput = -.2;
      // Smart Motion Coefficients
      maxVel = 1600; // rpm
      maxAcc = 1500;

      // set PID coefficients
      pid.setP(kP);
      pid.setI(kI);
      pid.setD(kD);
      pid.setIZone(kIz);
      pid.setFF(kFF);
      pid.setOutputRange(kMinOutput, kMaxOutput);

      /**
       * Smart Motion coefficients are set on a SparkMaxPIDController object
       * 
       * - setSmartMotionMaxVelocity() will limit the velocity in RPM of
       * the pid controller in Smart Motion mode
       * - setSmartMotionMinOutputVelocity() will put a lower bound in
       * RPM of the pid controller in Smart Motion mode
       * - setSmartMotionMaxAccel() will limit the acceleration in RPM^2
       * of the pid controller in Smart Motion mode
       * - setSmartMotionAllowedClosedLoopError() will set the max allowed
       * error for the pid controller in Smart Motion mode
       */
      int smartMotionSlot = 0;
      pid.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
      pid.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
      pid.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
      pid.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

      // display PID coefficients on SmartDashboard
      SmartDashboard.putNumber("P Gain", kP);
      SmartDashboard.putNumber("I Gain", kI);
      SmartDashboard.putNumber("D Gain", kD);
      SmartDashboard.putNumber("I Zone", kIz);
      SmartDashboard.putNumber("Feed Forward", kFF);
      SmartDashboard.putNumber("Max Output", kMaxOutput);
      SmartDashboard.putNumber("Min Output", kMinOutput);

      // display Smart Motion coefficients
      SmartDashboard.putNumber("Max Velocity", maxVel);
      SmartDashboard.putNumber("Min Velocity", minVel);
      SmartDashboard.putNumber("Max Acceleration", maxAcc);
      SmartDashboard.putNumber("Allowed Closed Loop Error", allowedErr);
      SmartDashboard.putNumber("Set Position", 0);

      // button to toggle between velocity and smart motion modes
    
      SmartDashboard.putBoolean("Mode", true);

    }
  }

  //Return the encoder
  public RelativeEncoder getEncoder(){
    return encoder;
  }

  //Return the maxAngle
  public double getMaxAngle(){
    return maxAngle;
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
    if(K_PivotSub.isUsingPivot)
      return encoder.getPosition();
    return 0.0;
  }

  //Returns the current desired angle
  public double getDesiredAngle(){
    if(K_PivotSub.isUsingPivot)
      return desiredAngle;
    
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


  // Stops the motor in case of emergency
  public void emergencyStop() {
    if(K_PivotSub.isUsingPivot){
      motor.stopMotor();
    }
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Bottom Limit", BtmLimit.get());
    SmartDashboard.putNumber("Pivot Encoder", encoder.getPosition());
    SmartDashboard.putBoolean("Top Limit", TopLimit.get());

    double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    double iz = SmartDashboard.getNumber("I Zone", 0);
    double ff = SmartDashboard.getNumber("Feed Forward", 0);
    double max = SmartDashboard.getNumber("Max Output", 0);
    double min = SmartDashboard.getNumber("Min Output", 0);
    double maxV = SmartDashboard.getNumber("Max Velocity", 0);
    double minV = SmartDashboard.getNumber("Min Velocity", 0);
    double maxA = SmartDashboard.getNumber("Max Acceleration", 0);
    double allE = SmartDashboard.getNumber("Allowed Closed Loop Error", 0);

    // if PID coefficients on SmartDashboard have changed, write new values to controller
    if((p != kP)) { pid.setP(p); kP = p; }
    if((i != kI)) { pid.setI(i); kI = i; }
    if((d != kD)) { pid.setD(d); kD = d; }
    if((iz != kIz)) { pid.setIZone(iz); kIz = iz; }
    if((ff != kFF)) { pid.setFF(ff); kFF = ff; }
    if((max != kMaxOutput) || (min != kMinOutput)) { 
      pid.setOutputRange(min, max); 
      kMinOutput = min; kMaxOutput = max; 
    }
    if((maxV != maxVel)) { pid.setSmartMotionMaxVelocity(maxV,0); maxVel = maxV; }
    if((minV != minVel)) { pid.setSmartMotionMinOutputVelocity(minV,0); minVel = minV; }
    if((maxA != maxAcc)) { pid.setSmartMotionMaxAccel(maxA,0); maxAcc = maxA; }
    if((allE != allowedErr)) { pid.setSmartMotionAllowedClosedLoopError(allE,0); allowedErr = allE; }
    desiredAngle = SmartDashboard.getNumber("Set Position", 0);
      /**
       * As with other PID modes, Smart Motion is set by calling the
       * setReference method on an existing pid object and setting
       * the control type to kSmartMotion
       */
      pid.setReference(desiredAngle, CANSparkMax.ControlType.kSmartMotion);
  }
}
