package frc.robot.commands.AutoGroups;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.PivotSub;
import frc.robot.commands.ResetEncoders;
import frc.robot.commands.claw.ClawCloseV2;
import frc.robot.commands.claw.ClawMoveV2;
import frc.robot.commands.extend.ExtenderSetPositionWaitForComplete;
import frc.robot.commands.pivot.PivotAngle;
import frc.robot.commands.pivot.PivotMove;
import frc.robot.commands.claw.ClawOpenV2;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ClawSubV2;
import frc.robot.subsystems.Drivetrain;


public class AutoGroup_MiddleDrop extends SequentialCommandGroup {
    //Variables

    public AutoGroup_MiddleDrop(Drivetrain m_Drivetrain, PivotSub m_pivotMotor, ExtensionSub m_extensionMotor, ClawSubV2 m_clawMotor){
        //Adding Order of commands
        addCommands(
            new ResetEncoders(m_Drivetrain),
            new ClawCloseV2(m_clawMotor),
            new PivotAngle(m_pivotMotor, 90),

            // new ExtenderSetPositionWaitForComplete(m_extensionMotor, -12),
            // Commands.race(new PivotMove(m_pivotMotor), )
            
            new ClawOpenV2(m_clawMotor),
            new PivotAngle(m_pivotMotor, 20)
            // new WaitCommand(.5),
            // new ExtenderSetPositionWaitForComplete(m_extensionMotor, 0),
        );
    }
}


                                                                           
