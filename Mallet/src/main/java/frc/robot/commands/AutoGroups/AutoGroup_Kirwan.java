package frc.robot.commands.AutoGroups;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.PivotSub;
import frc.robot.commands.claw.ClawClose;
import frc.robot.commands.extend.ExtenderSetPositionWaitForComplete;
import frc.robot.commands.pivot.PivotAngle;
import frc.robot.commands.claw.ClawMove;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ClawSub;


public class AutoGroup_Kirwan extends SequentialCommandGroup {
    //Variables

    public AutoGroup_Kirwan(PivotSub m_pivotMotor, ExtensionSub m_extensionMotor, ClawSub m_clawMotor){
        //Adding Order of commands
        addCommands(
            new ClawClose(m_clawMotor),
            new PivotAngle(m_pivotMotor, 100),
            new ExtenderSetPositionWaitForComplete(m_extensionMotor, -9),
            new ClawMove(m_clawMotor),
            new ExtenderSetPositionWaitForComplete(m_extensionMotor, 0),
            new PivotAngle(m_pivotMotor, 5)
        );
    }
}
