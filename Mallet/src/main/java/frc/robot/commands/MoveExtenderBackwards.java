package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ExtensionMotor;


public class MoveExtenderBackwards extends CommandBase{

    // Required Subsystem of Extension Motor
    private ExtensionMotor m_extender;

    public MoveExtenderBackwards(ExtensionMotor extender){
        m_extender = extender;
        addRequirements(m_extender);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    // Tells the Extension Motor to go Backwards
    @Override
    public void execute() {
        m_extender.moveMotor(-1);
    }

    // Called once the command ends or is interrupted.
    // Tells the Extension Motor to Stop
    @Override
    public void end(boolean interrupted) {
        m_extender.moveMotor(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
