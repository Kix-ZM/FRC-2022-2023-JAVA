package frc.robot.commands.PositioningGroups;
import frc.robot.subsystems.ClawSub;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.GyroScope;
import frc.robot.subsystems.PivotSub;
import frc.robot.commands.pivot.PivotAngle;
import frc.robot.commands.extend.ExtenderSetPosition;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Group_Angle90 extends SequentialCommandGroup {
    //Variables
    public Group_Angle90(Drivetrain drivetrain, GyroScope gyro, ClawSub claw, ExtensionSub extend, PivotSub pivot){
        //Adding a drivetrain
        //Adding Order of commands
        if (pivot.getEncoder1().getPosition() < 45) {
            addCommands(
                new PivotAngle(pivot, 100),
                new ExtenderSetPosition(extend, 7.5)
            );
        }
        else {
            addCommands(
                new ExtenderSetPosition(extend, 7.5),
                new PivotAngle(pivot, 100)
            );
        }
    }
}