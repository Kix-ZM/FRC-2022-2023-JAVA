package frc.robot.commands.pivot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotSub;


public class PivotAngle extends CommandBase{
    // Required Subsystems
    private PivotSub m_pivot;
    private double m_finalAngle;
    private double m_currentAngle;
    private int directionScalar = 1;

    // Creation Function of the Class
    public PivotAngle(PivotSub pivot, double targetAngle){
        m_pivot = pivot;
        m_finalAngle = targetAngle;
        addRequirements(m_pivot);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_currentAngle = m_pivot.getCurrentAngle();
        if (m_finalAngle - m_currentAngle < 0 )
            directionScalar = -1;
    }

    // Called every time the scheduler runs while the command is scheduled.
    // Tells the Pivot Motor to turn in a direction designated by the 3rd Axis of the Controller
    @Override
    public void execute() {

        // 4 degrees per second
        m_pivot.changeAngle(directionScalar * 23.0/50.0);
        m_pivot.moveMotors();
        m_currentAngle = m_pivot.getCurrentAngle();
    }

    // Called once the command ends or is interrupted.
    // Nothing is called here as it is covered already in the subsystem to stop the motor.
    @Override
    public void end(boolean interrupted) {
        m_pivot.setAngle(m_finalAngle);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(m_finalAngle - m_currentAngle) < 8;
    }
}
