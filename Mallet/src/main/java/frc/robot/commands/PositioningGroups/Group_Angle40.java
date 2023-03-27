package frc.robot.commands.PositioningGroups;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.PivotSub;
import frc.robot.commands.pivot.PivotAngle;
import frc.robot.commands.extend.ExtenderSetPositionWaitForComplete;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Group_Angle40 extends SequentialCommandGroup {

    // ext -6.181
    // pivot 37.228
    //Variables
    public Group_Angle40(ExtensionSub extend, PivotSub pivot){
        //Adding a drivetrain
        //Adding Order of commands
        if (pivot.getEncoder1().getPosition() < 50) {
            addCommands(
                new PivotAngle(pivot, 35),
                new ExtenderSetPositionWaitForComplete(extend, -8)
            );
        }
        else {
            addCommands(
                new ExtenderSetPositionWaitForComplete(extend, 0),
                new PivotAngle(pivot, 35),
                new ExtenderSetPositionWaitForComplete(extend, -8)
            );
        }
    }
}