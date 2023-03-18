package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveDistance extends CommandBase{
    private Drivetrain m_Drivetrain;
    private double endDistance;
    private double m_newFeet;
    private boolean m_isBackwards;
    
    public MoveDistance(Drivetrain drivetrain, double newFeet, boolean backwards){
        System.out.println("constructor");
        m_isBackwards = backwards;
        m_Drivetrain  = drivetrain;
        m_Drivetrain.resetEncoders();
        m_newFeet = newFeet * Constants.K_TICKS_PER_FEET;
        if(!m_isBackwards)
            endDistance = m_Drivetrain.getAverageDistanceInch() + newFeet;
        else    
            endDistance = m_Drivetrain.getAverageDistanceInch() - newFeet;
        addRequirements(drivetrain);
    }
    
    public void initialize() {
        System.out.println("initialize");
        m_Drivetrain.resetEncoders();
        if(!m_isBackwards)
            endDistance = m_Drivetrain.getAverageDistanceInch() + m_newFeet;
        else 
            endDistance = m_Drivetrain.getAverageDistanceInch() - m_newFeet;
    }

    public void execute() {
        if(!m_isBackwards)
            m_Drivetrain.arcadeDrive(.4, 0);
        else   
            m_Drivetrain.arcadeDrive(-0.4, 0);
    }
    
    public void end(boolean interrupted){
        m_Drivetrain.arcadeDrive(0.0, 0.0);
    }
    public boolean isFinished(){
        //return Math.abs(m_Drivetrain.getAverageDistanceInch() - endDistance) < 1;
        if(!m_isBackwards)
            return endDistance < m_Drivetrain.getAverageDistanceInch();
        else
            return endDistance > m_Drivetrain.getAverageDistanceInch();
    }
}
