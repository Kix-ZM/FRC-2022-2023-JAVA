package frc.robot.subsystems;

import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
  private final CANSparkMax armMotor = new CANSparkMax(5, MotorType.kBrushless);

  /** Creates a new Drivetrain. */
  public ArmSubsystem() {}

  // turns arm
  public void turnArm(boolean fBtn, boolean bBtn) {
    if(fBtn!=bBtn&&(fBtn||bBtn)){
      if(fBtn)
        armMotor.set(-Constants.tSpeed);
      else
        armMotor.set(Constants.tSpeed);
    }
    else{
      armMotor.set(0.0);
    }
  }

  // shifts arm up and down
  /*public void shiftArm(double shiftSpeed, boolean tgrCheck) {
    if(!limit.get()&&tgrCheck){CAMMotor.set(shiftSpeed);}
    else if(limit.get()){CAMMotor.set(Math.abs(shiftSpeed));}
  }*/

  // FOr when the time calls for it, run this
  public void stopMotors(){
    armMotor.set(0);
    // CAMMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
