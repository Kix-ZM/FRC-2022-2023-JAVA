package frc.robot.commands;

import frc.robot.Constants;
//import frc.robot.Constants;
//import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveDistance extends CommandBase{
    private boolean finishStatus = false;
    private Drivetrain m_Drivetrain;
    
    double forwardDist =  Constants.forward_feet; //how far to move until start moving backwards
    double backwardDist = Constants.backward_feet; //how far to move backwards until we stop
    double distance;
    boolean isBackwards;
    double startDistance;
    double speed = 0;
    int factor = 1; //adjusts math and speed based on if going forwards or backwards

    public MoveDistance(Drivetrain drivetrain, double newDistance, boolean backwards){
        m_Drivetrain  = drivetrain;
        distance = newDistance;
        isBackwards = backwards;
    }
    
    public void initialize(){
        startDistance = m_Drivetrain.getAverageDistanceInch();
    }
    public void execute(){
        if(isBackwards) factor = -1; //if moving backwards adjust math
        if(distance > factor*(m_Drivetrain.getAverageDistanceInch() - startDistance)){
            speed = 0.4*factor;
        }else{
            speed = 0.0;
            finishStatus = true;
        }
        // if(isMovingForwards) //if moving forward
        //     moveForward();
        // else //if moving backwards
        //     moveBackward();

        m_Drivetrain.arcadeDrive(speed, 0);
    }

        
    
    public void end(boolean interrupted){
        m_Drivetrain.arcadeDrive(0.0, 0.0);
    }
    public boolean isFinished(){
        return finishStatus;
    }
}
