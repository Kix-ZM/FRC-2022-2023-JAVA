package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubV2;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.PivotSub;


public class MotorsMove extends CommandBase{
    // Required Subsystems
    private ClawSubV2 m_claw;
    private ExtensionSub m_extend;
    private PivotSub m_pivot;

    // Creation Function of the Class
    public MotorsMove(ClawSubV2 claw, ExtensionSub extend, PivotSub pivot){
        m_claw = claw;
        m_extend = extend;
        m_pivot = pivot;
        addRequirements(m_claw, m_extend, m_pivot);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    // Relatively change claw by joystick
    @Override
    public void execute() {
        m_claw.moveClaw();
        m_extend.moveMotors();
        m_pivot.moveMotors();
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
