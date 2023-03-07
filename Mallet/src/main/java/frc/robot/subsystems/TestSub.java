package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TestSub extends SubsystemBase{
    // This is the Pivot Motor
    // Idle - Break
    private final CANSparkMax motor1 = new CANSparkMax(1, MotorType.kBrushless);
    private double cMove = 1; 
    private boolean change;
    private boolean isStopped = false;


    // This is the Arm Extension Motor
    // Idle - Coast
    private final CANSparkMax motor2 = new CANSparkMax(2, MotorType.kBrushless);    

    public TestSub(){
        motor1.setIdleMode(IdleMode.kBrake);
        motor2.setIdleMode(IdleMode.kCoast);
    }
    public void moveMotors(double motor1Speed, double motor2Speed){
      if(isStopped)
        emergencyStop();
      else{
        if(Math.abs(motor1Speed)*2<Constants.minSpeed){motor1.setVoltage(0);}
        else if(Math.abs(motor1Speed)*2>Constants.maxSpeed){motor1.setVoltage(Constants.maxSpeed*cMove);}
        else{motor1.setVoltage(motor1Speed*2*cMove);}
        if(Math.abs(motor2Speed*2)<Constants.minSpeed){motor2.setVoltage(0);}
        else if(Math.abs(motor2Speed*2)>Constants.maxSpeed){motor2.setVoltage(Constants.maxSpeed*cMove);}
        else{motor2.setVoltage(motor2Speed*2*cMove);}
      }
    }

    public void emergencyStop(){
      motor1.setIdleMode(IdleMode.kBrake);
      motor1.setIdleMode(IdleMode.kBrake);
      motor1.stopMotor();
      motor2.stopMotor();
    }

    public void kill(){
      isStopped = !isStopped;
    }

    public void changeMovement(double change){
      cMove=change;
    }

    public void changeSetting(){
      if(change){
        motor1.setIdleMode(IdleMode.kBrake);
        motor2.setIdleMode(IdleMode.kBrake);
      }
      else{
        motor1.setIdleMode(IdleMode.kCoast);
        motor2.setIdleMode(IdleMode.kCoast);}
      change=!change;
    }

  @Override
  public void periodic() {
    
  }
}
