package frc.robot.commands.AutoGroups;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.PivotSub;
import frc.robot.commands.MotorsMove;
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


public class AutoGroup_TopDropParallel extends SequentialCommandGroup {
    //Variables

    public AutoGroup_TopDropParallel(Drivetrain m_Drivetrain, PivotSub m_pivotMotor, ExtensionSub m_extensionMotor, ClawSubV2 m_clawMotor){
        //Adding Order of commands
        addCommands(
            Commands.race(new MotorsMove(m_clawMotor, m_extensionMotor, m_pivotMotor), new AutoGroup_TopDrop(m_Drivetrain, m_pivotMotor, m_extensionMotor, m_clawMotor))
        );
    }
}


                                                                           
