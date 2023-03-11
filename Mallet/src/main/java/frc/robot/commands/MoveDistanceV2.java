package frc.robot.commands;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveDistanceV2 extends CommandBase{
    private Drivetrain m_Drivetrain;
    private double endDistance;
    private double m_newFeet;
    
    public MoveDistanceV2(Drivetrain drivetrain, double newFeet, boolean backwards){
        System.out.println("constructor");
        m_Drivetrain  = drivetrain;
        m_Drivetrain.resetEncoders();
        m_newFeet = newFeet;
        endDistance = m_Drivetrain.getAverageDistanceInch() + newFeet;
        addRequirements(drivetrain);
    }
    
    public void initialize() {
        System.out.println("initialize");
        m_Drivetrain.resetEncoders();
        endDistance = m_Drivetrain.getAverageDistanceInch() + m_newFeet;
    }

    public void execute() {
        m_Drivetrain.arcadeDrive(.4, 0);
    }
    
    public void end(boolean interrupted){
        m_Drivetrain.arcadeDrive(0.0, 0.0);
    }
    public boolean isFinished(){
        //return Math.abs(m_Drivetrain.getAverageDistanceInch() - endDistance) < 1;
        return endDistance < m_Drivetrain.getAverageDistanceInch();
    }
}
