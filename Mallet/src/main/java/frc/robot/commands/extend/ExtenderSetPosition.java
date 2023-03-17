package frc.robot.commands.extend;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ExtensionSub;


public class ExtenderSetPosition extends CommandBase{
    // Required Subsystems
    private ExtensionSub m_extender;
    private double m_finalAngle;

    // Creation Function of the Class
    public ExtenderSetPosition(ExtensionSub ext, double angle){
        m_extender = ext;
        m_finalAngle = angle;
        addRequirements(m_extender);
    }

    // Called when the command is initially scheduled.
    //Sets the angle of the extender
    @Override
    public void initialize() {
        m_extender.setAngle(m_finalAngle);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

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
