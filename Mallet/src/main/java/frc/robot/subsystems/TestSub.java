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

    // This is the Arm Extension Motor
    // Idle - Coast
    private final CANSparkMax motor2 = new CANSparkMax(2, MotorType.kBrushless);
    public TestSub(){
        motor1.setIdleMode(IdleMode.kBrake);
        motor2.setIdleMode(IdleMode.kCoast);
    }
    public void moveMotors(double motor1Speed, double motor2Speed){
      if(motor1Speed<Constants.minSpeed){motor1.set(Constants.minSpeed);}
      else if(motor1Speed>Constants.maxSpeed){motor1.set(Constants.maxSpeed);}
      else{motor1.set(motor1Speed);}

      if(motor2Speed<Constants.minSpeed){motor2.set(Constants.minSpeed);}
      else if(motor2Speed>Constants.maxSpeed){motor2.set(Constants.maxSpeed);}
      else{motor2.set(motor2Speed);}
    }

  @Override
  public void periodic() {
    
  }
}
