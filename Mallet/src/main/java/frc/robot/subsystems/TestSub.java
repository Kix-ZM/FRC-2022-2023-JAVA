package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TestSub extends SubsystemBase{
    private final CANSparkMax motor1 = new CANSparkMax(2, MotorType.kBrushless);
    private double maxSpeed = 0.3;
    public TestSub(){
        
    }
    public void moveMotor1(double speed){
        if(speed > maxSpeed){
            speed = maxSpeed;
        }
        motor1.set(speed);
    }
    public void stopMotor1(){
        motor1.set(0);
    }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
