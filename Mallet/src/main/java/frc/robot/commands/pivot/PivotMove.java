package frc.robot.commands.pivot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotSub;


public class PivotMove extends CommandBase{
    // Required Subsystems
    private PivotSub m_pivot;
    private Joystick m_joystick;

    // Creation Function of the Class
    public PivotMove
(PivotSub pivot, Joystick joystick){
        m_pivot = pivot;
        m_joystick = joystick;
        addRequirements(m_pivot);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    // Tells the Pivot Motor to turn by joystick movement
    @Override
    public void execute() {
        m_pivot.changeAngle(-m_joystick.getRawAxis(1)/1.4);
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
