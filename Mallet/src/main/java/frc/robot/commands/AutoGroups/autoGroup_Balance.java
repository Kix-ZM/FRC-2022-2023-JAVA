package frc.robot.commands.AutoSequentialGroup;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroScope;
import frc.robot.commands.DriveTillPlatform;
import frc.robot.commands.ResetEncoders;
import frc.robot.commands.AutoBalance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoDriveBalance extends SequentialCommandGroup {
    //Variables
    public AutoDriveBalance(Drivetrain drivetrain, GyroScope gyro){
        //Adding a drivetrain
        //Adding Order of commands

        addCommands(
            new ResetEncoders(drivetrain),

            // drive until on platform            
            // balance
            new AutoBalance(drivetrain, gyro)

        );
    }
}