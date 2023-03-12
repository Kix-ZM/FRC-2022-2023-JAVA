package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
//import frc.robot.Constants.K_ClawSub;
import frc.robot.subsystems.ClawSubV2;


public class ClawClamp extends CommandBase{
    // Required Subsystems
    private ClawSubV2 m_claw;

    // Creation Function of the Class
    public ClawClamp(ClawSubV2 claw){
        m_claw = claw;
        addRequirements(m_claw);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    // need to make target types work
    // Sets angle to clamp based on whether cone or cube
    @Override
    public void execute() {
        m_claw.clamp();
    }

    // Called once the command ends or is interrupted.
    // Nothing is called here as it is covered already in the subsystem to stop the motor.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
        //return m_claw.getCurrent() > K_ClawSub.maxCurrent; 
    }
}
