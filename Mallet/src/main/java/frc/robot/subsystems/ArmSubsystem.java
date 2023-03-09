// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {

  private final CANSparkMax m_Claw = new CANSparkMax(5, MotorType.kBrushless);
  private final CANSparkMax m_Extension = new CANSparkMax(6, MotorType.kBrushless);
  private final CANSparkMax m_pivot = new CANSparkMax(7, MotorType.kBrushless);
  
  private final DigitalInput m_clawLimitSwitch = new DigitalInput(0);
  private final DigitalInput m_extensionSwitchEnd = new DigitalInput(1);
  private final DigitalInput m_extensionSwitch = new DigitalInput(2);
  private final DigitalInput m_pivotSwitch = new DigitalInput(3); 

  private final RelativeEncoder m_ClawEncoder = m_Claw.getEncoder();
  private final RelativeEncoder m_ExtensionEncoder = m_Extension.getEncoder();
  private final RelativeEncoder m_pivotEncoder = m_pivot.getEncoder();

  private boolean check = false;

  /** Creates a new Drivetrain. */
  public ArmSubsystem(){
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_pivot.setInverted(true);
    m_pivot.setIdleMode(IdleMode.kBrake);
    m_Extension.setIdleMode(IdleMode.kBrake);
    m_Claw.setIdleMode(IdleMode.kBrake);
    resetAllEncoders();
  }

  //Pivot Functions
  public void pivotArm(double yAxis){
    if(!isPivotAtLimit() && getPivotEncoder()>0 && getPivotEncoder() < Constants.maxPivot)
      m_pivot.setVoltage(yAxis);
    else
      m_pivot.setVoltage(0.0);
  }

  public void stopPivot(){
    m_pivot.setVoltage(0.0);
  }

  public boolean isPivotAtLimit(){
    return m_pivotSwitch.get();
  }

  public void resetPivots(){
    m_pivotEncoder.setPosition(0);
  }

  public double getPivotEncoder(){
    return m_pivotEncoder.getPosition();
  }


  //Extension Functions
  public void extendArm(double yAxis){
    if(m_extensionSwitch.get()){
      if(yAxis<0)
        m_Extension.setVoltage(0);
    }
    else if(m_extensionSwitchEnd.get()){
      if(yAxis>0)
        m_Extension.setVoltage(0);
    }
    else
      m_Extension.setVoltage(yAxis);
  }

  public void stopExtension(){
    m_Extension.setVoltage(0.0);
  }

  public boolean isExtensionAtLimitB(){
    return m_extensionSwitchEnd.get();
  }
  public boolean isExtensionAtLimitF(){
    return m_extensionSwitch.get();
  }
  public void toggleExtentionMotorIdle(){
    if(check)
      m_Extension.setIdleMode(IdleMode.kCoast);
    else
      m_Extension.setIdleMode(IdleMode.kBrake);
  }

  public void resetExtensionEncoders(){
    m_ExtensionEncoder.setPosition(0);
  }

  public double getExtensionEncoder(){
    return m_ExtensionEncoder.getPosition();
  }


  //Claw Functions
  public void grabClaw(double speed){
    if(!isClawAtLimit() && getClawEncoder()< Constants.maxClaw)
      m_Claw.setVoltage(speed);
  }
  public void retractClaw(){
    if(!isClawAtLimit()){
      m_Claw.setVoltage(-0.2);
    }
    else m_Claw.setVoltage(0.0);
  }
  public void stopClaw(){
    m_Claw.setVoltage(0.0);
  }

  public boolean isClawAtLimit(){
    return m_clawLimitSwitch.get();
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