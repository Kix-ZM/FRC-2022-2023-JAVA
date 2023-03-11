package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AimCommand extends CommandBase {
    private final Drivetrain m_drivetrain;
    private final Limelight m_limelight;

    public AimCommand(Drivetrain drivetrain, Limelight limelight) {
        m_drivetrain = drivetrain;
        m_limelight = limelight;
        addRequirements(drivetrain);
        addRequirements(limelight);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

    }

    // Called every time the scheduler runs while the command is scheduled.

    // CHANGE TO IMPLEMENT TURNANGLE WHEN MERGE 
    @Override
    public void execute() {
        m_drivetrain.arcadeDrive(0,-m_limelight.getXOffset()*Constants.K_DEC_TO_PI*0.9);
        //m_drivetrain.runTest(RobotContainer.m_controller.getRawAxis(2));
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
