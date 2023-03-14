package frc.robot.commands.AutoGroups;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroScope;
import frc.robot.commands.ResetEncoders;
import frc.robot.commands.AutoBalance;
import frc.robot.commands.MoveDistance;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoGroup_LeaveCommAndBalance extends SequentialCommandGroup {
    //Variables
    public AutoGroup_LeaveCommAndBalance(Drivetrain drivetrain, GyroScope gyro){
        //Adding a drivetrain
        //Adding Order of commands

        addCommands(
            new ResetEncoders(drivetrain),
            
            new MoveDistance(drivetrain, 18, false),
            // balance
            new AutoBalance(drivetrain, gyro, true)

        );
    }
}