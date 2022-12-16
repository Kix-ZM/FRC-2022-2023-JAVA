// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.RomiArm;

public class SetAllArmPos extends CommandBase {
  private final RomiArm arm_sub;
  private double l_rollerAxis;
  private double r_rollerAxis;

  /** Creates a new RaiseArm. */
  public SetAllArmPos(RomiArm arm) {
    l_rollerAxis = RobotContainer.m_lcontroller.getRawAxis(2);
    r_rollerAxis = RobotContainer.m_rcontroller.getRawAxis(2);
    arm_sub = arm;
    addRequirements(arm_sub);

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {    
    arm_sub.set_raise_armPos((l_rollerAxis+1)/4);
    arm_sub.set_pivot_armPos((r_rollerAxis+1)/4+.5);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

