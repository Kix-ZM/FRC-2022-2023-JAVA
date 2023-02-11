package frc.robot.commands;

import frc.robot.Constants;
//import frc.robot.Constants;
//import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoDrive extends CommandBase{
    private boolean finishStatus = false;
    private Drivetrain m_Drivetrain;
    
    double forwardDist =  Constants.forward_feet; //how far to move until start moving backwards
    double backwardDist = Constants.backward_feet; //how far to move backwards until we stop
    private boolean isMovingForwards = true;
    public AutoDrive(Drivetrain drivetrain){
        m_Drivetrain  = drivetrain;
    }
    
    public void initialize(){}
    public void execute(){
        if(Constants.isAutoRun){ //check if automated
            if(isMovingForwards){ //if moving forward

                //if we have not yet traveled the full forward distance
                if(m_Drivetrain.getAverageDistanceInch() <= forwardDist){
                    m_Drivetrain.arcadeDrive(0.4, 0); //move forwards
                    System.out.println("Moving Forwards"); //debug
                 }else{//we have travelled the full distance - switch to moving backwards
                    m_Drivetrain.arcadeDrive(0,0); //stop moving
                    m_Drivetrain.resetEncoders(); //reset encoder data
                    isMovingForwards = false; //stop moving forwards
                    System.out.println("Switching"); //debug
                    SmartDashboard.putString("deb", "switched"); //debug
                }

            }else{ //if moving backwards

                //if we have not yet travelled the full backwards distance
                if(m_Drivetrain.getAverageDistanceInch() <= backwardDist){
                    m_Drivetrain.arcadeDrive(-0.4, 0); //move backwards
                    System.out.println("Moving Backwards"); //debug
                //we have travelled the full backwards distance - we can now stop moving
                }else{
                    finishStatus = true; //we are done moving
                    Constants.isAutoRun = false; //we have ended autonomous mode
                    SmartDashboard.putString("deb", "done"); //debug
                    System.out.println("Finished"); //debug
                }
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
