package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class DebugCommand extends CommandBase{
    Joystick m_joy;

    public DebugCommand(Joystick joy){
        m_joy = joy;
    }
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        System.out.println("0 Axis: " + m_joy.getRawAxis(0) 
        + " 1 Axis : " + m_joy.getRawAxis(1) 
        + "2 Axis: " + m_joy.getRawAxis(2));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}
