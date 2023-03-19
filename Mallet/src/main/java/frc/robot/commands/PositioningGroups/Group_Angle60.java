package frc.robot.commands.PositioningGroups;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.PivotSub;
import frc.robot.commands.pivot.PivotAngle;
import frc.robot.commands.extend.ExtenderSetPositionWaitForComplete;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Group_Angle60 extends SequentialCommandGroup {
    //Variables
    public Group_Angle60(ExtensionSub extend, PivotSub pivot){
        //Adding a drivetrain
        //Adding Order of commands
        addCommands(
            new ExtenderSetPositionWaitForComplete(extend, 0),
            new PivotAngle(pivot, 60)
        );
    }
}