package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoDrive extends CommandBase{
    private boolean finishStatus = false;
    private Drivetrain m_Drivetrain;
    private double m_distance;
    public AutoDrive(Drivetrain drivetrain, double distance){
        m_Drivetrain  = drivetrain;
        m_distance = distance;
    }
    public void initialize(){}
    public void execute(){
        if(m_Drivetrain.getAverageDistanceInch()< m_distance){
            m_Drivetrain.arcadeDrive(0.1, 0);
        }else{
            finishStatus = true;
        }
    }
    public void end(boolean interrupted){
        m_Drivetrain.arcadeDrive(0.0, 0.0);
    }
    public boolean isFinished(){
        return finishStatus;
    }
}
