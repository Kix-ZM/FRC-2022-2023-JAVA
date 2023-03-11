package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ExtensionMotor extends SubsystemBase{
    // This is the Arm Extension Motor

    // Idle - Break 
    private final CANSparkMax motor2;
    
    // Limits are true when open
    private final DigitalInput TopLimit;
    private final DigitalInput BtmLimit;
    
    public ExtensionMotor(){
      if(Constants.isUsingExt){
        motor2 = new CANSparkMax(6, MotorType.kBrushless);
        TopLimit = new DigitalInput(0);
        BtmLimit = new DigitalInput(1);
        motor2.setIdleMode(IdleMode.kBrake);
      }else{
        motor2 = null;
        TopLimit = null;
        BtmLimit = null;
      }
    }

    // If limit switch is hit in direction of movement, stop movement in that direction
    public void moveMotor(double direction) {
      if(Constants.isUsingExt){
        if (direction > 0 && TopLimit.get()) {
          motor2.setVoltage((Constants.maxSpeed + Constants.minSpeed)/4);
        }else if (direction < 0 && BtmLimit.get()) {
          motor2.setVoltage(-(Constants.maxSpeed + Constants.minSpeed)/4);
        } else {
          motor2.setVoltage(0);
        }
      }
    }

    // Stops the movement of the Arm
    public void emergencyStop(){
      if(Constants.isUsingExt){
        motor2.stopMotor();
      }
    }

  @Override
  public void periodic() {
    //System.out.println("TOP:" +TopLimit.get() + ", BOT: " + BtmLimit.get());
  }
}
