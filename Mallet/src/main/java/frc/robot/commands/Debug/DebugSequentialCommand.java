package frc.robot.commands.Debug;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroScope;
import frc.robot.Constants;
import frc.robot.commands.TurnAngle;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DebugSequentialCommand extends SequentialCommandGroup{
    private GyroScope m_gyro;
    private Drivetrain m_drivetrain;
    public DebugSequentialCommand(Drivetrain drivetrain, GyroScope gyro){
        m_gyro = gyro;
        m_drivetrain = drivetrain;
        addCommands(
            new TurnAngle(m_drivetrain, m_gyro, 180.0f, false)
        );
    }
}
