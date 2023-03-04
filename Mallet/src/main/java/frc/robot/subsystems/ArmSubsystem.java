// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.concurrent.CancellationException;

//import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {

  private final CANSparkMax m_Claw = new CANSparkMax(1, MotorType.kBrushless);
  private final CANSparkMax m_Extension = new CANSparkMax(2, MotorType.kBrushless);
  private final CANSparkMax m_leftPivot = new CANSparkMax(3, MotorType.kBrushless);
  private final CANSparkMax m_rightPivot = new CANSparkMax(4, MotorType.kBrushless);

  private final DigitalInput m_clawLimitSwitch;
  private final DigitalInput m_extensionSwitch;
  private final DigitalInput m_pivotSwitch; 

  private final RelativeEncoder m_ClawEncoder = m_Claw.getEncoder();
  private final RelativeEncoder m_ExtensionEncoder = m_Extension.getEncoder();
  private final RelativeEncoder m_leftPivotEncoder = m_leftPivot.getEncoder();
  private final RelativeEncoder m_rightPivotEncoder = m_rightPivot.getEncoder();

  MotorControllerGroup m_pivot = new MotorControllerGroup(m_leftPivot, m_rightPivot);

  /** Creates a new Drivetrain. */
  public ArmSubsystem(){
    if(Constants.hasClawLimitSwitch){
      m_clawLimitSwitch = new DigitalInput(0);
    }else{
      m_clawLimitSwitch = null;
    }
    if(Constants.hasExtLimitSwitch){
      m_extensionSwitch = new DigitalInput(1);
    }else{
      m_extensionSwitch = null;
    }
    if(Constants.hasPivMotor){
      m_pivotSwitch = new DigitalInput(2);
    }else{
      m_pivotSwitch = null;
    }
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_pivot.setInverted(true);
    resetAllEncoders();
  }

  //Pivot Functions
  public void pivotArm(double yAxis){
    if(!isPivotAtLimit() && getPivotEncoder()>0 && getPivotEncoder() < Constants.maxPivot)
      m_pivot.set(yAxis);
    else
      m_pivot.set(0.0);
  }

  public void stopPivot(){
    m_pivot.set(0.0);
  }

  public boolean isPivotAtLimit(){
    if(Constants.hasPivMotor){
      return m_pivotSwitch.get();
    }else{
      if(getPivotEncoder()>Constants.minPivot){
          return false;
      }
    }
    return true;
  }

  public void resetPivots(){
    m_leftPivotEncoder.setPosition(0);
    m_rightPivotEncoder.setPosition(0);
  }

  public double getPivotEncoder(){
    return m_rightPivotEncoder.getPosition() + m_leftPivotEncoder.getPosition();
  }


  //Extension Functions
  public void extendArm(double yAxis){
    if(!isExtensionAtLimit() && getExtensionEncoder() < Constants.maxExtension)
      m_Extension.set(yAxis);
    else
      m_Extension.set(0.0);
  }

  public void stopExtension(){
    m_Extension.set(0.0);
  }

  public boolean isExtensionAtLimit(){
    if(Constants.hasExtLimitSwitch){
      return m_extensionSwitch.get();
    }else{
      if(getExtensionEncoder()>Constants.minExtension){
        return false;
      }
    }
    return true;
  }

  public void resetExtensionEncoders(){
    m_ExtensionEncoder.setPosition(0);
  }

  public double getExtensionEncoder(){
    return m_ExtensionEncoder.getPosition();
  }


  //Claw Functions
  public void moveClaw(double speed){
    if(!isClawAtLimit() && getClawEncoder()< Constants.maxClaw)
      m_Claw.set(speed);
    else
      m_Claw.set(0.0);
  }
  public void grabCone(){
    if(getClawEncoder()< Constants.encoder_cone)
      m_Claw.set(0.25);
    else
      m_Claw.set(0.0);
  }
  public void grabCube(){
    if(getClawEncoder()< Constants.encoder_cube)
      m_Claw.set(0.25);
    else
      m_Claw.set(0.0);
  }
  public void retractClaw(){
    if(!isClawAtLimit()){
      m_Claw.set(-0.2);
    }
  }
  public void stopClaw(){
    m_Claw.set(0.0);
  }

  public boolean isClawAtLimit(){
    if(Constants.hasClawLimitSwitch){
      return m_clawLimitSwitch.get();
    }else{
      if(getClawEncoder()>Constants.minClaw){
        return false;
      }
    }
    return true;
  }

  public void resetClawEncoders(){
    m_ClawEncoder.setPosition(0);
  }

  public double getClawEncoder(){
    return m_ClawEncoder.getPosition();
  }

  public void resetAllEncoders(){
    resetClawEncoders();
    resetClawEncoders();
    resetExtensionEncoders();
    resetPivots();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}