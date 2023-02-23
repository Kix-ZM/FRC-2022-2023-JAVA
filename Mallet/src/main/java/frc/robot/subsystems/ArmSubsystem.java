// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.concurrent.CancellationException;

//import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {

  private final CANSparkMax m_Claw = new CANSparkMax(1, MotorType.kBrushless);
  private final CANSparkMax m_Extension = new CANSparkMax(2, MotorType.kBrushless);
  private final CANSparkMax m_leftPivot = new CANSparkMax(3, MotorType.kBrushless);
  private final CANSparkMax m_rightPivot = new CANSparkMax(4, MotorType.kBrushless);
  private final DigitalInput m_clawLimitSwitch = new DigitalInput(0); 

  MotorControllerGroup m_pivot = new MotorControllerGroup(m_leftPivot, m_rightPivot);

  /** Creates a new Drivetrain. */
  public ArmSubsystem() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    
    m_pivot.setInverted(true);
  }

  public void pivotArm(double yAxis){
    m_pivot.set(yAxis);
  }

  public void extendArm(boolean triggerHeld, double yAxis){
    if(triggerHeld)
      m_Extension.set(yAxis);
  }

  public void grabCone(){
    m_Claw.set(0.25);
  }

  public void grabCube(){
    m_Claw.set(0.1);
  }

  public void retractClaw(){
    if(m_clawLimitSwitch.get()){
      
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}