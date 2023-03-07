// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.subsystems.GyroScope;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoBalance extends CommandBase {
  private final GyroScope m_gyro;
  private final Drivetrain m_drivetrain;
  private boolean onPlatform;

  /**
   * Creates a new AutoBalance. This command balances the robot on the charging station. 
   * This command does not terminate.
   *
   * @param drivetrain The drivetrain subsystem on which this command will run
   * @param gyro The gyro subsystem on which this command will run
   */
  public AutoBalance(Drivetrain drivetrain, GyroScope gyro) {
    onPlatform = false;
    m_gyro = gyro;
    m_drivetrain = drivetrain;
    addRequirements(gyro);
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
    // if angle negative, go forwards
    // if angle positive, go backwards
    if (!onPlatform)
    {
      m_drivetrain.arcadeDrive(Constants.startingSpeedMax, 0);
      if (Math.abs(m_gyro.getAngleY()) > Constants.onPlatThreshDeg) {
        onPlatform = !onPlatform;
      }
    }
    else {
      double pitchAngleDegrees = m_gyro.getAngleY();
      double yawAngleDegrees = m_gyro.getAngleZ();
      double toAdjustSpeed = 0;
      double toAdjustRotate = 0;
      if (Math.abs(pitchAngleDegrees) > Constants.OnBalanceThreshDeg)
          toAdjustSpeed = pitchAngleDegrees < 0 // if tilted backwards
              ? -Constants.adjustSpeedMax // go forward
              : Constants.adjustSpeedMax; // go backwards
      if (Math.abs(yawAngleDegrees) > Constants.OnBalanceThreshDeg)
          toAdjustRotate = yawAngleDegrees < 0 // if tilted left
              ? -Constants.adjustRotateMax  // go right
              : Constants.adjustRotateMax; // go left
      m_drivetrain.arcadeDrive(-toAdjustSpeed, -toAdjustRotate);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // end when on platform
    return false;
    // return Math.abs(m_gyro.getAngleY()) > Constants.onPlatThreshDeg;
  }
}
