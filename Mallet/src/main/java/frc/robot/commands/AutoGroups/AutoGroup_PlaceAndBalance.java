package frc.robot.commands.AutoGroups;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroScope;
import frc.robot.commands.AutoBalance;
import frc.robot.commands.ResetEncoders;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoGroup_PlaceAndBalance extends SequentialCommandGroup {
    //Variables
    Drivetrain m_Drivetrain;
    public AutoGroup_PlaceAndBalance(Drivetrain drivetrain, GyroScope gyro){
        //Adding a drivetrain
        m_Drivetrain = drivetrain;
        //Adding Order of commands
        addCommands(
            new ResetEncoders(this.m_Drivetrain),
            new AutoBalance(drivetrain, gyro, false)
        );
    }
}
