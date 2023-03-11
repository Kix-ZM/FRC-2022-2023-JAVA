package frc.robot.commands.AutoGroups;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroScope;
import frc.robot.commands.ResetEncoders;
import frc.robot.commands.AutoBalance;
import frc.robot.commands.MoveDistance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoGroup_MoveTest extends SequentialCommandGroup {
    //Variables
    Drivetrain m_Drivetrain;
    public AutoGroup_MoveTest(Drivetrain drivetrain, GyroScope gyro){
        //Adding a drivetrain
        m_Drivetrain = drivetrain;
        //Adding Order of commands
        addCommands(
            new ResetEncoders(this.m_Drivetrain),
            new MoveDistance(this.m_Drivetrain, 10, false),
            new MoveDistance(this.m_Drivetrain, 2, false)
        );
    }
}
