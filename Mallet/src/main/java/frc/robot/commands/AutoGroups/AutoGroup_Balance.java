package frc.robot.commands.AutoGroups;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroScope;
import frc.robot.commands.ResetEncoders;
import frc.robot.commands.AutoBalance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoGroup_Balance extends SequentialCommandGroup {
    //Variables
    public AutoGroup_Balance(Drivetrain drivetrain, GyroScope gyro){
        System.out.println("AutoGroup_Balance");
        //Adding a drivetrain
        //Adding Order of commands

        addCommands(
            new ResetEncoders(drivetrain),

            // balance
            // new AutoBalance(drivetrain, gyro, false)
            new AutoBalance(drivetrain, gyro, false)
        );
    }
}