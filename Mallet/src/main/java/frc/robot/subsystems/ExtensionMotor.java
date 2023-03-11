package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ExtensionMotor extends SubsystemBase{
    // This is the Arm Extension Motor
    // Idle - Coast
    private final CANSparkMax motor2 = new CANSparkMax(6, MotorType.kBrushless);

    private double cMove = 1; 
    private boolean change;
    private boolean isStopped = false;
    
    public ExtensionMotor(){
        motor2.setIdleMode(IdleMode.kCoast);
    }
    public void moveMotors(double speed){
      if(isStopped)
        emergencyStop();
      else{
        if(Math.abs(speed*2)<Constants.minSpeed){motor2.setVoltage(0);}
        else if(Math.abs(speed*2)>Constants.maxSpeed){motor2.setVoltage(Constants.maxSpeed*cMove);}
        else{motor2.setVoltage(speed*2*cMove);}
      }
    }

    public void moveMotor(double direction) {
      if (direction > 0) {
        motor2.setVoltage((Constants.maxSpeed + Constants.minSpeed)/2);
      }else if (direction < 0) {
        motor2.setVoltage(-(Constants.maxSpeed + Constants.minSpeed)/2);
      } else {
        motor2.setVoltage(0);
      }
    }

    public void emergencyStop(){
      motor2.setIdleMode(IdleMode.kBrake);
      motor2.stopMotor();
    }

    // not used either?
    public void kill(){
      isStopped = !isStopped;
    }

    public void changeMovement(double change){
      cMove=change;
    }

    public void changeSetting(){
      if(change)
        motor2.setIdleMode(IdleMode.kBrake);
      else
        motor2.setIdleMode(IdleMode.kCoast);
      change=!change;
    }

  @Override
  public void periodic() {
    
  }
}
