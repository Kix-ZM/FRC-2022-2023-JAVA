package frc.robot.commands.schedulers;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.extend.ExtenderMove;
import frc.robot.subsystems.ExtensionSub;


public class ExtendMoveScheduler extends CommandBase{
    // Required Subsystems
    private ExtensionSub m_extend;

    // Creation Function of the Class
    public ExtendMoveScheduler(ExtensionSub extend){
        m_extend = extend;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        new ExtenderMove(m_extend).schedule();
    }

    // Schedules extension default movement
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
