package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroScope;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurnAngle extends CommandBase{

    private Drivetrain m_drivetrain;
    private GyroScope m_gyro;
    private float turnDestination;
    private int direction;
    private float errorRange = 2.0f;
    private boolean status;
    public TurnAngle(Drivetrain drivetrain, GyroScope gyro, float turnAmount){
        m_drivetrain = drivetrain;
        m_gyro = gyro;
        status = false;
        addRequirements(m_drivetrain);
        addRequirements(m_gyro);
        // Getting what the angle should be at the end
        turnDestination = m_gyro.getAngleX() + turnAmount;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
  
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        /*If statements to check the distance it needs to go and if it is within a small margin or not
        and based of that, checks to see the direction to turn to (if it has already reached its destination,
        it will not move*/
        if(turnDestination-m_gyro.getAngleX()>errorRange){
            direction = 1;
        }else if(turnDestination-m_gyro.getAngleX() < -1*errorRange){
            direction = -1;
        }else{
            direction = 0;
        }
        //Direction should only be 0 when the robot reaches the desired angle
        if(direction == 0){
            status = true;
        }
        m_drivetrain.arcadeDrive(0.0, Constants.SPEED*direction);
    }
  
  
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      direction = 0;
      m_drivetrain.arcadeDrive(direction, direction);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return status;
    }
}
