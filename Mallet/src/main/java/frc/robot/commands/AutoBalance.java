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
  private boolean startBalancing;
  private boolean onPlatform;
  /**
   * Creates a new AutoBalance. This command balances the robot on the charging station. 
   * THIS WILL ONLY WORK IF THE ROBOT'S Y ANGLE STARTS OFF NEGATIVE (IMU orientation)
   * This command does not terminate.
   *
   * @param drivetrain The drivetrain subsystem on which this command will run
   * @param gyro The gyro subsystem on which this command will run
   */
  public AutoBalance(Drivetrain drivetrain, GyroScope gyro) {
    startBalancing = false;
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
    double pitchAngleDegrees = m_gyro.getAngleY();
    if (!startBalancing)
    {
      m_drivetrain.arcadeDrive(Constants.K_FWD_SPEED, 0);
      // if at high enough angle so we know it is on the platform
      if (Math.abs(pitchAngleDegrees) > Constants.K_PLAT_DEGREE_THRESH)
        onPlatform = true;
      
      // if on platform and tilted to the other side of the charger
      if (onPlatform && pitchAngleDegrees > 0) 
        startBalancing = true;
    }
    else {
      double yawAngleDegrees = m_gyro.getAngleZ();
      double toAdjustSpeed = 0;
      double toAdjustRotate = 0;
      // if angle negative, go forwards
      // if angle positive, go backwards
      if (Math.abs(pitchAngleDegrees) > Constants.K_BALANCE_THRESH_DEG)
          toAdjustSpeed = pitchAngleDegrees < 0 // if tilted backwards
              ? -Constants.K_ADJUST_SPEED // go forward
              : Constants.K_ADJUST_SPEED; // if tilted forwards go backwards
      while (Math.abs(yawAngleDegrees) > Constants.K_BALANCE_THRESH_DEG)
          toAdjustRotate = yawAngleDegrees < 0 // if tilted left
              ? Constants.K_ADJUST_ROTATE  // go right
              : -Constants.K_ADJUST_ROTATE; // go left
      m_drivetrain.arcadeDrive(-toAdjustSpeed, -toAdjustRotate);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // don't end, stay balanced until autonomous ends
    return false;
  }
}
