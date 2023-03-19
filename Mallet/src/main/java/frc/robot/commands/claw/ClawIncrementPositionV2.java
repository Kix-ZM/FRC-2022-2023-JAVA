package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSub;

public class ClawIncrementPositionV2 extends CommandBase {
    // Required Subsystems
    private ClawSub m_claw;

    // Creation Function of the Class
    public ClawIncrementPositionV2(ClawSub claw) {
        m_claw = claw;
        addRequirements(m_claw);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

    }

    // Called every time the scheduler runs while the command is scheduled.
    // Sets open position closer to clamped position
    @Override
    public void execute() {
        m_claw.changeOpenPosition(20.0/50.0);
        m_claw.moveClaw();
    }

    // Called once the command ends or is interrupted.
    // Nothing is called here as it is covered already in the subsystem to stop the
    // motor.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
