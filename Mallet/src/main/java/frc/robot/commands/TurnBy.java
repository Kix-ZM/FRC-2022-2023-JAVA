package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroScope;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurnBy extends CommandBase {
    private Drivetrain m_drivetrain;
    private GyroScope m_gyro;
    private double turnAmount; //angle we're turning by
    private double finalAngle; //the final angle the robot should end at
    private double turnDirection;

    public TurnBy (Drivetrain p_drivetrain, GyroScope p_gyro, double angle){
        m_drivetrain = p_drivetrain;
        m_gyro = p_gyro;
        turnAmount = angle;
        addRequirements(m_drivetrain);
        addRequirements(m_gyro);
    }

    public float getGyroZ360(){ //return the gyro position in the range 0 to 359
        float angle = m_gyro.getAngleZ();
        if(angle<0) angle += 360;
        return angle;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        finalAngle = getGyroZ360() + turnAmount; //determine final angle based on current position and the angle to turn by
        finalAngle = (finalAngle+360)%360; // Properly converts final angle to a positive number (ex: 45 degrees and goes counter-clockwise by 90 degrees would be 270 or -90)

        turnDirection = -Math.signum(turnAmount);
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double distance = Math.abs(finalAngle - getGyroZ360()); //find distance from the target angle
        if(distance > 180) distance = Math.abs(distance-360); //wrap around distance
        double speed = 2*Math.max(Constants.K_MIN_TURNING_SPEED,Math.min(Constants.K_MAX_TURNING_SPEED, (.01*(distance-10)+.2))); //set speed dynamically to slow down as we approach the target angle

        m_drivetrain.arcadeDrive(0, speed*turnDirection);
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(finalAngle-getGyroZ360()) < Constants.K_TURN_ERROR_RANGE;
    }
}