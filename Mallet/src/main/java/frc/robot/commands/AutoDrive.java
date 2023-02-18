package frc.robot.commands;

import frc.robot.Constants;
//import frc.robot.Constants;
//import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoDrive extends CommandBase{
    private boolean finishStatus = false;
    private Drivetrain m_Drivetrain;
    
    double forwardDist =  Constants.forward_feet; //how far to move until start moving backwards
    double backwardDist = Constants.backward_feet; //how far to move backwards until we stop
    double speed = 0;

    private boolean isMovingForwards = true;
    public AutoDrive(Drivetrain drivetrain){
        m_Drivetrain  = drivetrain;
    }
    
    public void initialize(){
        System.out.println("AutoDrive Initialized"); //Debug
    }
    public void execute(){
        if(isMovingForwards) //if moving forward
            moveForward();
        else //if moving backwards
            moveBackward();

        m_Drivetrain.arcadeDrive(speed, 0);
    }

        
    
    public void end(boolean interrupted){
        m_Drivetrain.arcadeDrive(0.0, 0.0);
    }
    public boolean isFinished(){
        return finishStatus;
    }

    private void moveForward(){
        if(m_Drivetrain.getAverageDistanceInch() <= forwardDist){ //if we have not yet traveled the full forward distance
            speed = 0.4; //move forwards
            // System.out.println("Moving Forwards");//debug
        } else {
            // System.out.println("Finished");
            stopMove();
        } 
    }

    private void stopMove(){
        m_Drivetrain.arcadeDrive(0,0); //stop moving
        m_Drivetrain.resetEncoders(); //reset encoder data
        isMovingForwards = false; //stop moving forwards
    }

    private void moveBackward(){
        if(m_Drivetrain.getAverageDistanceInch() <= backwardDist){ //if we have not yet travelled the full backwards distance
            speed = -0.4; //move backwards
            // System.out.println("Moving Backwards"); //debug
        } else{ //we have travelled the full backwards distance - we can now stop moving
            // System.out.println("Finished"); //debug
            stopMove();
            finishStatus = true; //we are done moving
        }
    }


}
