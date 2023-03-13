package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class ExtensionMotor extends SubsystemBase{
    // This is the Arm Extension Motor

    // Idle - Break 
    // ID - 7
    private final CANSparkMax motor2 = new CANSparkMax(6, MotorType.kBrushless);
    private final RelativeEncoder m_Encoder = motor2.getEncoder();
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



    public void moveWithEncoders(double direction){
      System.out.println(m_Encoder.getPosition());
      if(Constants.isUsingExt){
        if(Math.abs(m_Encoder.getPosition()) <= 86/*Put the real encoder value here */){
          System.out.println(m_Encoder.getPosition());
          if(!BtmLimit.get()){
            m_Encoder.setPosition(0);
            motor2.setVoltage(0);
          } 
          if (direction > 0 && TopLimit.get()) {
            motor2.setVoltage((Constants.maxSpeed + Constants.minSpeed)/4); 
          }
        }
        else if(Math.abs(m_Encoder.getPosition()) >= 85.0){
          if (direction < 0 && BtmLimit.get()) {
            motor2.setVoltage(-(Constants.maxSpeed + Constants.minSpeed)/4);
          } 
          else {
            motor2.setVoltage(0);
          }
        }
        else{
          motor2.stopMotor();
        }
            
      
    }
  }

      // If limit switch is hit in direction of movement, stop movement in that direction
    public void moveMotor(double direction) {
      System.out.println(m_Encoder.getPosition());
      if(Constants.isUsingExt){
        if (direction > 0 && TopLimit.get()) {
          motor2.setVoltage((Constants.maxSpeed + Constants.minSpeed)/4);
        }else if (direction < 0 && BtmLimit.get()) {
          motor2.setVoltage(-(Constants.maxSpeed + Constants.minSpeed)/4);
        } else {
          motor2.setVoltage(0);
          if(!BtmLimit.get()){
            m_Encoder.setPosition(0);
          }
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