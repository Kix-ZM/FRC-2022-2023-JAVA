package frc.robot.commands.AutoGroups;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroScope;
import frc.robot.commands.TurnBy;
import frc.robot.commands.ResetEncoders;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoGroup_PlaceAndLeave extends SequentialCommandGroup {
    //Variables
    private Drivetrain m_drivetrain;
    private GyroScope m_gyro;

    public AutoGroup_PlaceAndLeave(Drivetrain drivetrain, GyroScope gyro){
        //Adding a drivetrain
        m_drivetrain = drivetrain;
        m_gyro = gyro;
        //Adding Order of commands
        addCommands(
            new ResetEncoders(m_drivetrain),
            /*place piece (with arm code)*/
            new TurnBy(m_drivetrain, m_gyro, 180),
            /*move until robot is a certain distance away from cube*/
            new TurnBy(m_drivetrain, m_gyro, 180)
            /*move back into community and center with AprilTag*/
            /*place piece*/
        );
    }
}
