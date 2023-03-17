package frc.robot.commands.AutoGroups;

import frc.robot.subsystems.ClawSub;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.GyroScope;
import frc.robot.subsystems.PivotSub;
import frc.robot.commands.pivot.PivotAngle;
import frc.robot.commands.ResetEncoders;
import frc.robot.commands.TurnBy;
import frc.robot.commands.MoveDistance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoGroup_Place extends SequentialCommandGroup {
    //Variables
    public AutoGroup_Place(Drivetrain drivetrain, GyroScope gyro, ClawSub claw, ExtensionSub extend, PivotSub pivot){
        System.out.println("AutoGroup_Place");
        //Adding a drivetrain
        //Adding Order of commands

        addCommands(
            new ResetEncoders(drivetrain),
            new PivotAngle(pivot, 90),
            new MoveDistance(drivetrain, 1, false),
            new TurnBy(drivetrain, gyro, 180),
            new MoveDistance(drivetrain, 0, false)
        );
    }
}