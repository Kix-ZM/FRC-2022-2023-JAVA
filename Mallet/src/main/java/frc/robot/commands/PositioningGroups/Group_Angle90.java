package frc.robot.commands.PositioningGroups;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.PivotSub;
import frc.robot.commands.pivot.PivotAngle;
import frc.robot.commands.extend.ExtenderSetPositionWaitForComplete;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Group_Angle90 extends SequentialCommandGroup {
    //Variables
    public Group_Angle90(ExtensionSub extend, PivotSub pivot){
        //Adding a drivetrain
        //Adding Order of commands
        if (pivot.getEncoder1().getPosition() < 45) {
            addCommands(
                new PivotAngle(pivot, 100),
                new ExtenderSetPositionWaitForComplete(extend, -6)
            );
        }
        else {
            addCommands(
                new ExtenderSetPositionWaitForComplete(extend, -6),
                new PivotAngle(pivot, 90)
            );
        }
    }
}