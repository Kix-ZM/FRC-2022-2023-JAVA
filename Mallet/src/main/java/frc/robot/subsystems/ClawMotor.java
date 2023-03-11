package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClawMotor extends SubsystemBase{
    // This is the Claw Extension Motor
    // Idle - Break
    // ID - 8
    private final CANSparkMax clawMotor = new CANSparkMax(8, MotorType.kBrushless);
    
    // Limit Switch
    // WARNING - MAKE SURE THE LIMITS ARE HAVING THE YELLOW IN GROUND!
    //           YES IT LOOKS WRONG BUT BLAME ELECTRICAL FOR THEIR WIRING!
    //           --> DEFAULT IS ALWAYS TRUE BUT WHEN HIT THEY RETURN FALSE!
    private final DigitalInput clawLimit = new DigitalInput(4);
    
    public ClawMotor(){
        clawMotor.setIdleMode(IdleMode.kBrake);
    }

    // If limit switch is hit in direction of movement, stop movement in that direction
    public void grabClaw(double direction) {
      if (direction > 0) {
        clawMotor.setVoltage((Constants.maxSpeed + Constants.minSpeed)/4);
      }else if (direction < 0 && clawLimit.get()) {
        clawMotor.setVoltage(-(Constants.maxSpeed + Constants.minSpeed)/4);
      } else {
        clawMotor.setVoltage(0);
      }
    }

    // Stops the movement of the Arm
    public void emergencyStop(){
      clawMotor.stopMotor();
    }

  @Override
  public void periodic() {
    System.out.println("Current Resistance: "+clawMotor.getBusVoltage()/clawMotor.getAppliedOutput());
  }
}
