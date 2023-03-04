package frc.robot.commands;
import javax.print.attribute.standard.JobHoldUntil;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TestSub;


public class DebugCommand extends CommandBase{
    public DebugCommand(Joystick joy){
        System.out.println("0 Axis: " + joy.getRawAxis(0) + " 1 Axis : " + joy.getRawAxis(1) + "2 Axis: " + joy.getRawAxis(2));
    }
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}
