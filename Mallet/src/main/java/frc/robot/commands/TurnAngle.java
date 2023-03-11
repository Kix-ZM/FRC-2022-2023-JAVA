package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroScope;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurnAngle extends CommandBase
    private Drivetrain m_drivetrain;
    private GyroScope m_gyro;

    private float m_startDes; 
    private float m_turnAmount;
    private boolean m_isTurnningBy; //determines if we want to match the given angle or not - true means we are turning by the angle rather than matching it
    private int m_direction;
    private boolean status;

    public TurnAngle(Drivetrain p_drivetrain, GyroScope p_gyro, float p_turnAmount, boolean p_isTurnningBy){
        m_drivetrain = p_drivetrain;
        m_gyro = p_gyro;
        addRequirements(m_drivetrain);
        addRequirements(m_gyro);

        m_isTurnningBy = p_isTurnningBy;
        status = false;

        // Getting what the angle should be at the end
        m_turnAmount = p_turnAmount;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        System.out.println("Init");
        status = false;

        if(m_isTurnningBy){
            //Converts gyro to 360 degrees
            m_startDes = (m_gyro.getAngleZ()+360.0f)%360.0f;
            m_turnAmount += m_startDes;
        }

        //Converts 360 degrees back to gyro
        m_turnAmount %= 360.0f;
        if(m_turnAmount > 180.0f){
            m_turnAmount = -1*(180-(m_turnAmount%180));
        }
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        System.out.println("Destination : " + m_turnAmount);
        System.out.println("Start : " + m_startDes);
        System.out.println("Z : " + m_gyro.getAngleZ());
        /*If statements to check the distance it needs to go and if it is within a small margin or not
        and based of that, checks to see the direction to turn to (if it has already reached its destination,
        it will not move*/
        if((m_turnAmount-m_gyro.getAngleZ()>Constants.K_TURN_ERROR_RANGE) || m_turnAmount-m_gyro.getAngleZ() < -1*Constants.K_TURN_ERROR_RANGE){
            m_direction = 1;
        }else{
            m_direction = 0;
        }
        //Direction should only be 0 when the robot reaches the desired angle
        status = (m_direction == 0);
        m_drivetrain.arcadeDrive(0.0, Constants.K_SPEED*m_direction);
    }
  
  
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      m_direction = 0;
      System.out.println("Fin");
      m_drivetrain.arcadeDrive(m_direction, m_direction);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return status;
    }
}
