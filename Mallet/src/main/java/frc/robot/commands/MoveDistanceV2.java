package frc.robot.commands;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveDistanceV2 extends CommandBase{
    private Drivetrain m_Drivetrain;
    private double endDistance;
    

    public MoveDistanceV2(Drivetrain drivetrain, double newFeet, boolean backwards){
        m_Drivetrain.resetEncoders();
        endDistance = m_Drivetrain.getAverageDistanceInch() + newFeet*12;
        m_Drivetrain  = drivetrain;
        addRequirements(drivetrain);
    }
    
    public void initialize() {
        
    }
    public void execute() {
        m_Drivetrain.arcadeDrive(.5, 0);
    }

        
    
    public void end(boolean interrupted){
        m_Drivetrain.arcadeDrive(0.0, 0.0);
    }
    public boolean isFinished(){
        return Math.abs(m_Drivetrain.getAverageDistanceInch() - endDistance) < 1;
    }
}
