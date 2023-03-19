package frc.robot.commands.schedulers;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.claw.ClawMoveV2;
import frc.robot.subsystems.ClawSub;


public class ClawMoveScheduler extends CommandBase{
    // Required Subsystems
    private ClawSub m_claw;

    // Creation Function of the Class
    public ClawMoveScheduler(){
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    // Relatively change claw by joystick
    @Override
    public void execute() {
        new ClawMoveV2(m_claw).schedule();
    }

    // Called once the command ends or is interrupted.
    // Nothing is called here as it is covered already in the subsystem to stop the motor.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
