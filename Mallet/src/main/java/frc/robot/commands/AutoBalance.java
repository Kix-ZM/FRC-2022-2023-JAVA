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
  private int backwardsScaler;
  
  /**
   * Creates a new AutoBalance. This command balances the robot on the charging station. 
   * THIS WILL ONLY WORK IF THE ROBOT'S X ANGLE STARTS OFF POSITIVE (IMU orientation)
   * This command does not terminate.
   *
   * @param drivetrain The drivetrain subsystem on which this command will run
   * @param gyro The gyro subsystem on which this command will run
   */
  public AutoBalance(Drivetrain drivetrain, GyroScope gyro, boolean backwards) {
    startBalancing = false;
    onPlatform = false;

    // if starting from other side of charger, go backwards and change which side is other side
    backwardsScaler = backwards ? -1 : 1; 

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
    double pitchAngleDegrees = m_gyro.getAngleX();
    if (!startBalancing)
    {
      m_drivetrain.arcadeDrive(backwardsScaler*Constants.K_FWD_SPEED, 0);
      // if at high enough angle so we know it is on the platform
      if (Math.abs(pitchAngleDegrees) > Constants.K_PLAT_DEGREE_THRESH)
        onPlatform = true;
      
      // if on platform and tilted to the other side of the charger
      if (onPlatform && (backwardsScaler == 1 ? pitchAngleDegrees < 0 : pitchAngleDegrees > 0)) 
        startBalancing = true;
    }
    else {
      double yawAngleDegrees = m_gyro.getAngleZ();
      double toAdjustSpeed = 0;
      double toAdjustRotate = 0;
      // if angle positive, go forwards
      // if angle negative, go backwards
      if (Math.abs(pitchAngleDegrees) > Constants.K_BALANCE_THRESH_DEG)
          toAdjustSpeed = pitchAngleDegrees > 0 // if tilted backwards
              ? -Constants.K_ADJUST_SPEED // go forward
              : Constants.K_ADJUST_SPEED; // if tilted forwards go backwards
      if (Math.abs(yawAngleDegrees) > Constants.K_BALANCE_THRESH_DEG)
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
