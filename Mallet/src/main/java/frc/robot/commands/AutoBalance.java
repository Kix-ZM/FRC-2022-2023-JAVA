package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.subsystems.GyroScope;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoBalance extends CommandBase {
  private final GyroScope m_gyro;
  private final Drivetrain m_drivetrain;
  private boolean startBalancing;
  private boolean onPlatform;
  private boolean isBackwards;
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
    isBackwards = backwards;
    m_gyro = gyro;
    m_drivetrain = drivetrain;
    addRequirements(gyro);
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.print("initializing");
    SmartDashboard.putBoolean("Balancing", false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double pitchAngleDegrees = m_gyro.getAngleX();
    if (!startBalancing)
    {
      m_drivetrain.arcadeDrive(backwardsScaler*.34, 0);
      // if at high enough angle so we know it is on the platform
      if (Math.abs(pitchAngleDegrees) > Constants.K_PLAT_DEGREE_THRESH)
        onPlatform = true;
      
      // if on platform and tilted to the other side of the charger
      if (onPlatform && (backwardsScaler == 1 ? pitchAngleDegrees < 0 : pitchAngleDegrees > 0)) {
        m_drivetrain.setBalanceToCurrentPos(isBackwards);
        startBalancing = true;
      }
    }
    else {
      m_drivetrain.balance();
      SmartDashboard.putBoolean("Balancing", true);
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
