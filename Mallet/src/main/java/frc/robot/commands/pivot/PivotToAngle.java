package frc.robot.commands.pivot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotSub;


public class PivotToAngle extends CommandBase{
    // Required Subsystems
    private PivotSub m_pivot;
    private double finalAngle;

    // Creation Function of the Class
    public PivotToAngle(PivotSub pivot, double targetAngle){
        m_pivot = pivot;
        addRequirements(m_pivot);
        finalAngle = targetAngle;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    // Tells the Pivot Motor to turn in a direction designated by the 3rd Axis of the Controller
    @Override
    public void execute() {
        m_pivot.setAngle(finalAngle);
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
