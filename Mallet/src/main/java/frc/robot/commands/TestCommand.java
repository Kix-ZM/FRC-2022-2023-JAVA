package frc.robot.commands;
import javax.print.attribute.standard.JobHoldUntil;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TestSub;


public class TestCommand extends CommandBase{
    private TestSub m_test;
    private Joystick m_joystick;
    public TestCommand(TestSub test, Joystick joystick){
        m_test = test;
        m_joystick = joystick;
        addRequirements(m_test);
    }
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_test.moveMotor1(m_joystick.getRawAxis(0));
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
