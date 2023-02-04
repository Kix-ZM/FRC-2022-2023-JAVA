package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ResetEncoders extends CommandBase{
    private Drivetrain m_Drivetrain;
    public ResetEncoders(Drivetrain drivetrain){
        m_Drivetrain = drivetrain;
    }
    public void initialize(){
        m_Drivetrain.getRightEncoder().setPosition(0.0);
        m_Drivetrain.getLeftEncoder().setPosition(0.0);
        m_Drivetrain.getRightBackEncoder().setPosition(0.0);
        m_Drivetrain.getLeftBackEncoder().setPosition(0.0);
    }
    public void execute(){}
    public void end(boolean interrupted){}
    public boolean isFinished(){ 
        return true;
    }
}
