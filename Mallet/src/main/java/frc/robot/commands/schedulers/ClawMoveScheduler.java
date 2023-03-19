package frc.robot.commands.schedulers;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.claw.ClawMove;
import frc.robot.subsystems.ClawSub;


public class ClawMoveScheduler extends CommandBase{
    // Required Subsystems
    private ClawSub m_claw;

    // Creation Function of the Class
    public ClawMoveScheduler(ClawSub claw){
        m_claw = claw;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        new ClawMove(m_claw).schedule();
    }

    // Schedules claw default movement
    @Override
    public void execute() {
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
