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
    private final CANSparkMax motor1 = new CANSparkMax(5, MotorType.kBrushless);

    private final DigitalInput PivotLimit = new DigitalInput(2);
    private double cMove = 1; 
    private boolean change;
    private boolean isStopped = false;
    
    public PivotMotor(){
        motor1.setIdleMode(IdleMode.kBrake);
    }
    public void moveMotors(double speed){
      if(isStopped)
        emergencyStop();
      else{
        if(speed > 0 || (speed < 0 && !PivotLimit.get())){
          if(Math.abs(speed)*2<Constants.minSpeed){motor1.setVoltage(0);}
          else if(Math.abs(speed)*2>Constants.maxSpeed){motor1.setVoltage(Constants.maxSpeed*cMove*(Math.abs(speed)/speed));}
          else{motor1.setVoltage(speed*2*cMove);}
        }
        else{motor1.setVoltage(0);}
      }
    }

    public void emergencyStop(){
      motor1.setIdleMode(IdleMode.kBrake);
      motor1.stopMotor();
    }

    public void kill(){
      isStopped = !isStopped;
    }

    public void changeMovement(double change){
      cMove=change;
    }

    public void changeSetting() {
      if(change)
        motor1.setIdleMode(IdleMode.kBrake);
      else
        motor1.setIdleMode(IdleMode.kCoast);
      change=!change;
    }

  @Override
  public void periodic() {
    
  }
}
