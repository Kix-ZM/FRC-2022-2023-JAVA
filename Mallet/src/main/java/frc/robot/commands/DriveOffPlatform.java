package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.GyroScope;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class DriveOffPlatform extends CommandBase {
  private final GyroScope m_gyro;
  private final Drivetrain m_drivetrain;
  private double startingEncoderVal;
  
  /**
   * Creates a new AutoBalance. This command drives until the gyro detects that we are at a slant.
   * If we are at a slant, then we are on the platform. This command terminates when it detects that.
   *
   * @param drivetrain The drivetrain subsystem on which this command will run
   * @param gyro The gyro subsystem on which this command will run
   */
  public DriveOffPlatform(Drivetrain drivetrain, GyroScope t_gyro) {
    m_gyro = t_gyro;
    m_drivetrain = drivetrain;
    addRequirements(t_gyro);
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startingEncoderVal = m_drivetrain.getAverageDistanceInch();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // drive forward until on platform
    m_drivetrain.arcadeDrive(Constants.K_FWD_SPEED, 0);
    //System.out.println(Math.abs(m_drivetrain.getAverageDistanceInch() - startingEncoderVal));
    //System.out.println(m_gyro.getAngleX());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // end when on platform
    return (Math.abs(m_gyro.getAngleX()) < 1.0) && (Math.abs(m_drivetrain.getAverageDistanceInch() - startingEncoderVal) > 8 * Constants.K_TICKS_PER_FEET);
  }
}
