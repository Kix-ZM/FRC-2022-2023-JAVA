package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroScope;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurnBy extends CommandBase {
    private Drivetrain m_drivetrain;
    private GyroScope m_gyro;
    private double incrementBy;
    private double desiredAngle;
    

    public TurnBy (Drivetrain p_drivetrain, GyroScope p_gyro, double angle){
        m_drivetrain = p_drivetrain;
        m_gyro = p_gyro;
        incrementBy = angle;
        addRequirements(m_drivetrain);
        addRequirements(m_gyro);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
       desiredAngle += incrementBy;
       desiredAngle %= 360;
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double difference = desiredAngle - (m_gyro.getAngleZ()+180);
        // div by 90 for scaling
        m_drivetrain.arcadeDrive(0, difference > 0 ? -.4 : .4);
    }
  
  
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        System.out.print("FINISHED TURN ANGLE");
      return Math.abs(desiredAngle-m_gyro.getAngleZ()) < Constants.K_TURN_ERROR_RANGE;
    }
}