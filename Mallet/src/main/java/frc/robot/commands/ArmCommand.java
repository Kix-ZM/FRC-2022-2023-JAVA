// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Robot;
// import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;
// import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class ArmCommand extends CommandBase {
  private final ArmSubsystem m_armSub;
  private boolean m_retractButton;
  private boolean m_coneButton;
  private boolean m_cubeButton;
  private boolean m_trigger;
  private double m_zAxis;

  /**
   * Creates a new ArcadeDrive. This command will drive your robot according to the speed supplier
   * lambdas. This command does not terminate.
   *
   * @param drivetrain The drivetrain subsystem on which this command will run
   * @param xaxisSpeedSupplier Lambda supplier of forward/backward speed
   * @param zaxisRotateSupplier Lambda supplier of rotational speed
   */
  public ArmCommand(ArmSubsystem armSub) {
    m_armSub = armSub;
    m_retractButton = RobotContainer.m_controllerOther.getRawButton(3);
    m_coneButton = RobotContainer.m_controllerOther.getRawButton(4);
    m_cubeButton = RobotContainer.m_controllerOther.getRawButton(5);
    m_trigger = RobotContainer.m_controllerOther.getTrigger();
    m_zAxis = RobotContainer.m_controllerOther.getRawAxis(1);
    addRequirements(armSub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_trigger){
      m_armSub.extendArm(m_zAxis);
    }else{
      m_armSub.pivotArm(m_zAxis);
    }
    if(m_retractButton){
      m_armSub.retractClaw();
    }else if(m_coneButton){
      m_armSub.grabCone();
    }else if(m_cubeButton){
      m_armSub.grabCube();
    }
  }



  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_armSub.stopClaw();
    m_armSub.stopExtension();
    m_armSub.stopPivot();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
