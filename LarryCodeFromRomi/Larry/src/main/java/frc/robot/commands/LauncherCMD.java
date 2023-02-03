// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.Launcher;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import java.util.function.Supplier;

public class LauncherCMD extends CommandBase {
  private final Launcher m_launcher;
  //private double m_zaxisRotate;
  //private final Supplier<Double> m_xaxisSpeedSupplier;
  //private final Supplier<Double> m_zaxisRotateSupplier;

  /**
   * Creates a new ArcadeDrive. This command will drive your robot according to the speed supplier
   * lambdas. This command does not terminate.
   *
   * @param drivetrain The drivetrain subsystem on which this command will run
   * @param xaxisSpeedSupplier Lambda supplier of forward/backward speed
   * @param zaxisRotateSupplier Lambda supplier of rotational speed
   */
  public LauncherCMD(Launcher drivetrain) {
    m_launcher = drivetrain;
    //addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      m_launcher.stop();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(RobotContainer.m_fireButton.get())
      m_launcher.launch();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(RobotContainer.m_fireButton.get())
        return false;
    else 
        return true;
  }
}
