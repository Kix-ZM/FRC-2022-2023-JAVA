package frc.robot.commands.pivot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotSub;


public class PivotMoveToAngle extends CommandBase{
    // Required Subsystems
    private PivotSub m_pivot;

    // Creation Function of the Class
    public PivotMoveToAngle(PivotSub pivot){
        m_pivot = pivot;
        addRequirements(m_pivot);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // Move motor to stabalize or go to
        m_pivot.moveMotors();
    }

    // Called once the command ends or is interrupted.
    // Nothing is called here as it is covered already in the subsystem to stop the motor.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        //Checks to see if the motor is close to the desired angle
        return Math.abs(m_pivot.getCurentAngle() - m_pivot.getDesiredAngle()) < 2;
    }
}
