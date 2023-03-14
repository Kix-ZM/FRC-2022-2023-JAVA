package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubV2;


public class ClawNeutral extends CommandBase{
    // Required Subsystems
    private ClawSubV2 m_claw;

    // Creation Function of the Class
    public ClawNeutral(ClawSubV2 claw){
        m_claw = claw;
        addRequirements(m_claw);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    // need to make target types work
    // Resets angle back to neutral original position
    @Override
    public void execute() {
        m_claw.setAngle(0);
    }

    // Called once the command ends or is interrupted.
    // Nothing is called here as it is covered already in the subsystem to stop the motor.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}
