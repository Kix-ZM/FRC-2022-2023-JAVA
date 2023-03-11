package frc.robot.commands.AutoGroups;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroScope;
import frc.robot.commands.ResetEncoders;
import frc.robot.Constants;
import frc.robot.commands.MoveDistanceV2;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoGroup_MoveTest extends SequentialCommandGroup {
    //Variables
    private Drivetrain m_Drivetrain;
    public AutoGroup_MoveTest(Drivetrain drivetrain, GyroScope gyro){
        //Adding a drivetrain
        m_Drivetrain = drivetrain;
        //Adding Order of commands
        addCommands(
            new ResetEncoders(this.m_Drivetrain),
            new MoveDistanceV2(this.m_Drivetrain, 5 * Constants.K_TICKS_PER_FEET, false)
            //new MoveDistanceV2(this.m_Drivetrain, 2, false)
        );
    }
}
