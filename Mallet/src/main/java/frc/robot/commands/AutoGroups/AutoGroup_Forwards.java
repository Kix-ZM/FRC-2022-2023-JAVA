package frc.robot.commands.AutoGroups;

import frc.robot.subsystems.Drivetrain;
import frc.robot.Constants;
import frc.robot.commands.MoveDistance;
import frc.robot.commands.ResetEncoders;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoGroup_Forwards extends SequentialCommandGroup {
    //Variables
    Drivetrain m_Drivetrain;
    public AutoGroup_Forwards(Drivetrain drivetrain){
        //Adding a drivetrain
        m_Drivetrain = drivetrain;
        //Adding Order of commands
        addCommands(
            new ResetEncoders(this.m_Drivetrain),
            new MoveDistance(m_Drivetrain, Constants.K_FORWARDS_FEET, false)
        );
    }
}
