package frc.robot.commands.pivot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotSub;


public class PivotIdle extends CommandBase{
    // Required Subsystems
    private PivotSub m_pivot;

    // Creation Function of the Class
    public PivotIdle(PivotSub pivot){
        m_pivot = pivot;
        addRequirements(m_pivot);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    // Tells the motor just to move to it's desired angle or stablize itself. This is used as a command that is used to hold up the arm during autonomous
    @Override
    public void execute() {
        m_pivot.moveMotors();
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
