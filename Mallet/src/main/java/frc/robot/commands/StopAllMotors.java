package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotMotor;
import frc.robot.subsystems.ClawMotor;
import frc.robot.subsystems.ExtensionMotor;


public class StopAllMotors extends CommandBase{
    // Required Subsystems
    private PivotMotor m_pivot;
    private ExtensionMotor m_extender;
    private ClawMotor m_claw;

    public StopAllMotors(PivotMotor pivot, ExtensionMotor extender, ClawMotor claw){
        m_pivot = pivot;
        m_extender = extender;
        m_claw = claw;
        addRequirements(m_pivot);
        addRequirements(m_extender);
        addRequirements(m_claw);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    // Literally Stops Everything, All Arm Motors will stop
    @Override
    public void execute() {
        m_pivot.emergencyStop();
        m_extender.emergencyStop();   
        m_claw.emergencyStop(); 
    }

    // Called once the command ends or is interrupted.
    // This is never to be interupted
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}
