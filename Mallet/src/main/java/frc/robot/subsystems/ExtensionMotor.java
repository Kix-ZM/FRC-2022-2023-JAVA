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
    // ID - 7
    private final CANSparkMax motor2 = new CANSparkMax(7, MotorType.kBrushless);
    
    // Limits are true when open
    // WARNING - MAKE SURE THE LIMITS ARE HAVING THE YELLOW IN GROUND!
    //           YES IT LOOKS WRONG BUT BLAME ELECTRICAL FOR THEIR WIRING!
    //           --> DEFAULT IS ALWAYS TRUE BUT WHEN HIT THEY RETURN FALSE!
    private final DigitalInput BtmLimit = new DigitalInput(2);
    private final DigitalInput TopLimit = new DigitalInput(3);
    
    public ExtensionMotor(){
      if(Constants.isUsingExt){
        motor2.setIdleMode(IdleMode.kBrake);
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
