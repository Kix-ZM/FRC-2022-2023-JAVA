// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RomiArm;

public class ToggleClaw extends CommandBase {
  private final RomiArm arm_sub;
  private boolean arm_open;
  /** Creates a new RaiseArm. */
  public ToggleClaw(RomiArm arm) {
    arm_sub = arm;
    arm_open=false;
    arm_sub.set_grab_armPos(1);
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
    if (arm_open)
        arm_sub.set_grab_armPos(1);
    else
        arm_sub.set_grab_armPos(0);
    arm_open=!arm_open;
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

