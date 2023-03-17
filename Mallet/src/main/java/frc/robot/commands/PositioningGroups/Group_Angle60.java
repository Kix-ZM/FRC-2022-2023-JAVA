package frc.robot.commands.PositioningGroups;
import frc.robot.subsystems.ClawSub;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.GyroScope;
import frc.robot.subsystems.PivotSub;
import frc.robot.commands.pivot.PivotAngle;
import frc.robot.commands.extend.ExtenderSetPosition;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Group_Angle60 extends SequentialCommandGroup {
    //Variables
    public Group_Angle60(Drivetrain drivetrain, GyroScope gyro, ClawSub claw, ExtensionSub extend, PivotSub pivot){
        //Adding a drivetrain
        //Adding Order of commands
        addCommands(
            new ExtenderSetPosition(extend, 0),
            new PivotAngle(pivot, 60)
        );
    }
}