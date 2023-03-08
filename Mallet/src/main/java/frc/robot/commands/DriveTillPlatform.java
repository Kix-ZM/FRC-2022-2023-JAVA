// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.subsystems.GyroScope;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveTillPlatform extends CommandBase {
  private final GyroScope m_gyro;
  private final Drivetrain m_drivetrain;
  /**
   * Creates a new AutoBalance. This command drives until the gyro detects that we are at a slant.
   * If we are at a slant, then we are on the platform. This command terminates when it detects that.
   *
   * @param drivetrain The drivetrain subsystem on which this command will run
   * @param gyro The gyro subsystem on which this command will run
   */
  public DriveTillPlatform(Drivetrain drivetrain, GyroScope t_gyro) {
    m_gyro = t_gyro;
    m_drivetrain = drivetrain;
    addRequirements(t_gyro);
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.print("initializing");

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // drive forward until on platform
    m_drivetrain.arcadeDrive(Constants.forwardSpeed, 0);
    System.out.print("executing");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // end when on platform
    return Math.abs(m_gyro.getAngleY()) > Constants.onPlatThreshDeg;
  }
}
