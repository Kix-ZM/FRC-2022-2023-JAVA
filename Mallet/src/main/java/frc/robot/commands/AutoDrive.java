package frc.robot.commands;

import frc.robot.Constants;
//import frc.robot.Constants;
//import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoDrive extends CommandBase{
    private boolean finishStatus = false;
    private Drivetrain m_Drivetrain;
    double forward =  Constants.forward_feet;
    double backward = Constants.backward_feet;
    private boolean isForwards = true;
    public AutoDrive(Drivetrain drivetrain){
        m_Drivetrain  = drivetrain;
    }
    
    public void initialize(){}
    public void execute(){
        if(isForwards){
            if(m_Drivetrain.getAverageDistanceInch()< forward){
                m_Drivetrain.arcadeDrive(0.1, 0);
            }else{
                isForwards = false;
            }
        }else{
            if(m_Drivetrain.getAverageDistanceInch()< backward){
                m_Drivetrain.arcadeDrive(0.1, 0);
            }else{
                finishStatus = true;
            }
        }
    }
    public void end(boolean interrupted){
        m_Drivetrain.arcadeDrive(0.0, 0.0);
    }
    public boolean isFinished(){
        return finishStatus;
    }
}
