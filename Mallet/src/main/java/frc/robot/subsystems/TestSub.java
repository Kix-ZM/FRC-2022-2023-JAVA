package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TestSub extends SubsystemBase{
    // This is the Pivot Motor
    // Idle - Break
    private final CANSparkMax pivMotor;
    private double cMove = 1; 
    private boolean change;
    private boolean isStopped = false;


    // This is the Arm Extension Motor
    // Idle - Coast
    private final CANSparkMax extMotor;   
    
    //This is the Claw Motor
    //Idle - Coast
    private final CANSparkMax clawMotor;

    public TestSub(){
      if(Constants.isUsingPivot){
        pivMotor = new CANSparkMax(1, MotorType.kBrushless);
        pivMotor.setIdleMode(IdleMode.kBrake);
      }else{
        pivMotor = null;
      }
      if(Constants.isUsingExt){
        extMotor = new CANSparkMax(1, MotorType.kBrushless);
        extMotor.setIdleMode(IdleMode.kCoast);
      }else{
        extMotor = null;
      }
      if(Constants.isUsingClaw){
        clawMotor = new CANSparkMax(1, MotorType.kBrushless);
        clawMotor.setIdleMode(IdleMode.kCoast);
      }else{
        clawMotor = null;
      }
    }
    public void moveMotors(double pivSpeed, double extSpeed, double clawSpeed){
      if(isStopped)
        emergencyStop();
      else{
        if(Constants.isUsingPivot){
          if(Math.abs(pivSpeed)*2<Constants.minSpeed){pivMotor.setVoltage(0);}
          else if(Math.abs(pivSpeed)*2>Constants.maxSpeed){pivMotor.setVoltage(Constants.maxSpeed*cMove);}
          else{pivMotor.setVoltage(pivSpeed*2*cMove);}
        }
        if(Constants.isUsingExt){
          if(Math.abs(extSpeed*2)<Constants.minSpeed){extMotor.setVoltage(0);}
          else if(Math.abs(extSpeed*2)>Constants.maxSpeed){extMotor.setVoltage(Constants.maxSpeed*cMove);}
          else{extMotor.setVoltage(extSpeed*2*cMove);}
        }
        if(Constants.isUsingClaw){
          if(Math.abs(clawSpeed*2)<Constants.minSpeed){clawMotor.setVoltage(0);}
          else if(Math.abs(clawSpeed*2)>Constants.maxSpeed){clawMotor.setVoltage(Constants.maxSpeed*cMove);}
          else{clawMotor.setVoltage(clawSpeed*2*cMove);}
        }
      }
    }

    public void emergencyStop(){
      if(Constants.isUsingPivot){
        pivMotor.setIdleMode(IdleMode.kBrake);
        pivMotor.stopMotor();
      }
      if(Constants.isUsingExt){
        extMotor.setIdleMode(IdleMode.kBrake);
        extMotor.stopMotor();
      }
      if(Constants.isUsingClaw){
        clawMotor.setIdleMode(IdleMode.kBrake);
        clawMotor.stopMotor();
      }
    }

    public void kill(){
      isStopped = !isStopped;
    }

    public void changeMovement(double change){
      cMove=change;
    }

    public void changeSetting(){
      if(change){
        if(Constants.isUsingPivot){
          pivMotor.setIdleMode(IdleMode.kBrake);
        }
        if(Constants.isUsingExt){
          extMotor.setIdleMode(IdleMode.kBrake);
        }
        if(Constants.isUsingClaw){
          clawMotor.setIdleMode(IdleMode.kBrake);
        }
      }
      else{
        if(Constants.isUsingPivot){
          pivMotor.setIdleMode(IdleMode.kCoast);
        }
        if(Constants.isUsingExt){
          extMotor.setIdleMode(IdleMode.kCoast);
        }
        if(Constants.isUsingClaw){
          clawMotor.setIdleMode(IdleMode.kCoast);
        }
      }
      change=!change;
    }

  @Override
  public void periodic() {
    
  }
}
