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

public class PivotMotor extends SubsystemBase{
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
    
    public PivotMotor(){
      if(Constants.isUsingPivot){
        motor1.setIdleMode(IdleMode.kBrake);
        motor2.setIdleMode(IdleMode.kBrake);
        motor1.setInverted(true);
        encoder1.setPosition(0);
        encoder2.setPosition(0);
        // set conversion factor so getPosition returns degrees
        encoder2.setPositionConversionFactor((Constants.calibrateEndingAngle-Constants.calibrateStartingAngle) / Constants.calibrateAngleEncoderValue);
      }
    }

    // Handles the movement of the motor.
    // if the limit switches are hit in the direction of movement,
    // then movement in that direction is stopped on motor
    public void moveMotors(double speed){
      if(isStopped)
        emergencyStop();
      else{
        if((speed > 0 && TopLimit.get()) || (speed < 0 && BtmLimit.get())){
          if(Math.abs(speed)<Constants.minSpeed){pivotMotors.setVoltage(0);}
          else if(Math.abs(speed)>Constants.maxSpeed){
            pivotMotors.setVoltage(Constants.maxSpeed*(Math.abs(speed)/speed));
          }
          else{pivotMotors.setVoltage(speed);}
        }
        else{pivotMotors.setVoltage(0);}
      }
    }

    // Stops the motor in case of emergency
    public void emergencyStop(){
      pivotMotors.stopMotor();
    }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Pivot Encoder 1", encoder1.getPosition());
    SmartDashboard.putNumber("Pivot Encoder 2", encoder2.getPosition());

  }
}
