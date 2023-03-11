package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotMotor;


public class MovePivot extends CommandBase{
    private PivotMotor m_pivot;
    private Joystick m_joystick;
    public MovePivot(PivotMotor pivot, Joystick joystick){
        m_pivot = pivot;
        m_joystick = joystick;
        addRequirements(m_pivot);
    }
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_pivot.moveMotors(-1*m_joystick.getRawAxis(2));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
