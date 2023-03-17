package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ResetEncoders extends CommandBase{
    private Drivetrain m_Drivetrain;
    
    public ResetEncoders(Drivetrain drivetrain){
        m_Drivetrain = drivetrain;
        addRequirements(m_Drivetrain);
    }
    public void initialize(){
        m_Drivetrain.resetEncoders();
        System.out.println(Double.toString(m_Drivetrain.getAverageDistanceInch()) + "Reset Encoders to 0 via command");
        SmartDashboard.putString("ResetEncoders", "Reset Encoders to 0");
    }
    public void execute(){}
    public void end(boolean interrupted){}
    public boolean isFinished(){ 
        return true;
    }
}
