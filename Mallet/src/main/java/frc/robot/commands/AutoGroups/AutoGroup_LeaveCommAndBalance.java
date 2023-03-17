package frc.robot.commands.AutoGroups;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroScope;
import frc.robot.commands.ResetEncoders;
import frc.robot.commands.AutoBalance;
import frc.robot.commands.AutoBalanceV2;
import frc.robot.commands.DriveTillPlatform;
import frc.robot.commands.DriveOffPlatform;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class AutoGroup_LeaveCommAndBalance extends SequentialCommandGroup {
    private Drivetrain m_drivetrain;
    private GyroScope m_gyro;

    public AutoGroup_LeaveCommAndBalance(Drivetrain drivetrain, GyroScope gyro){
        m_drivetrain = drivetrain;
        m_gyro = gyro;

        addCommands(
             new ResetEncoders(m_drivetrain),
             new DriveTillPlatform(m_drivetrain, m_gyro),
             new DriveOffPlatform(m_drivetrain, m_gyro),
             new AutoBalanceV2(m_drivetrain, m_gyro, true)
         );
    }
}