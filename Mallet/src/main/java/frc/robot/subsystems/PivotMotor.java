package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PivotMotor extends SubsystemBase{
    // This is the Pivot Motor
    // Idle - Break
    private final CANSparkMax motor1;

    // Limit Switches
    private final DigitalInput BtmLimit;
    private final DigitalInput TopLimit;

    // Ratio to multiply by and hopefully make things move better 
    private double cMove = 1; 

    // Determines if we got to stop all movement on the motor
    private boolean isStopped = false;
    
    public PivotMotor(){
      if(Constants.isUsingPivot){
        motor1 = new CANSparkMax(5, MotorType.kBrushless);
        BtmLimit = new DigitalInput(2);
        TopLimit = new DigitalInput(3);
        motor1.setIdleMode(IdleMode.kBrake);
      }else{
        motor1 = null;
        BtmLimit = null;
        TopLimit = null;
      }
    }

    // Handles the movement of the motor.
    // if the limit switches are hit in the direction of movement,
    // then movement in that direction is stopped on motor
    public void moveMotors(double speed){
      if(Constants.isUsingPivot){
        if(isStopped)
          emergencyStop();
        else{
          if((speed > 0 && TopLimit.get()) || (speed < 0 && BtmLimit.get())){
            if(Math.abs(speed)*2<Constants.minSpeed){motor1.setVoltage(0);}
            else if(Math.abs(speed)*2>Constants.maxSpeed){motor1.setVoltage(Constants.maxSpeed*cMove*(Math.abs(speed)/speed));}
            else{motor1.setVoltage(speed*2*cMove);}
          }
          else{motor1.setVoltage(0);}
        }
      }
    }

    // Stops the motor in case of emergency
    public void emergencyStop(){
      if(Constants.isUsingPivot){
        motor1.stopMotor();
      }
    }

  @Override
  public void periodic() {
    
  }
}
