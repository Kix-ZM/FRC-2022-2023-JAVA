package frc.robot.commands.claw;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubV2;


public class ClawMove extends CommandBase{
    // Required Subsystems
    private ClawSubV2 m_claw;
    private Joystick m_joystick;

    // Creation Function of the Class
    public ClawMove(ClawSubV2 claw, Joystick joystick){
        m_claw = claw;
        m_joystick = joystick;
        addRequirements(m_claw);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    // Tells the Pivot Motor to turn in a direction designated by the 3rd Axis of the Controller
    @Override
    public void execute() {
        m_claw.changeAngle(m_joystick.getRawAxis(1)/1.4);
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
