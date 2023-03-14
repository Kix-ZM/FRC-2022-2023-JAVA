package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawMotor;


public class GraspClaw extends CommandBase{

    // Required Subsystem of Extension Motor
    private ClawMotor m_claw;

    public GraspClaw(ClawMotor claw){
        m_claw = claw;
        addRequirements(m_claw);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    // Tells the Extension Motor to go Backwards
    @Override
    public void execute() {
        m_claw.grabClaw(1);
    }

    // Called once the command ends or is interrupted.
    // Tells the Extension Motor to Stop
    @Override
    public void end(boolean interrupted) {
        m_claw.grabClaw(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
