package frc.robot.commands.claw;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSub;


public class ClawMove extends CommandBase{
    // Required Subsystems
    private ClawSub m_claw;
    private Joystick m_joystick;

    // Creation Function of the Class
    public ClawMove(ClawSub claw, Joystick joystick){
        m_claw = claw;
        m_joystick = joystick;
        addRequirements(m_claw);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    // Relatively change claw by joystick
    @Override
    public void execute() {
        m_claw.changeAngle(m_joystick.getRawAxis(1));
        m_claw.moveMotors();
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