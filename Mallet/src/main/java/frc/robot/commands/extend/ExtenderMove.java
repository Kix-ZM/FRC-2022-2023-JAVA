package frc.robot.commands.extend;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ExtensionSub;


public class ExtenderMove extends CommandBase{
    // Required Subsystems
    private ExtensionSub m_extender;

    // Creation Function of the Class
    public ExtenderMove(ExtensionSub ext){
        m_extender = ext;
        addRequirements(m_extender);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    // Tells the extension motor to maintain position
    @Override
    public void execute() {
        // Enable for joystick control
        m_extender.moveMotors();
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
