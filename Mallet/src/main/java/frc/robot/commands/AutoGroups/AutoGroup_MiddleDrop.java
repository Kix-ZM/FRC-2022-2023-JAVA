package frc.robot.commands.AutoGroups;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.PivotSub;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.claw.ClawClose;
import frc.robot.commands.claw.ClawMove;
import frc.robot.commands.claw.ClawOpen;
import frc.robot.commands.pivot.PivotAngle;
import frc.robot.commands.pivot.PivotMove;
import frc.robot.commands.schedulers.ClawMoveScheduler;
import frc.robot.commands.schedulers.PivotMoveScheduler;
import frc.robot.subsystems.ClawSub;
import frc.robot.subsystems.Drivetrain;


public class AutoGroup_MiddleDrop extends SequentialCommandGroup {
    //Variables

    public AutoGroup_MiddleDrop(Drivetrain m_Drivetrain, PivotSub m_pivotMotor, ExtensionSub m_extensionMotor, ClawSub m_clawMotor){
        //Adding Order of commands
        addCommands(
            new ClawClose(m_clawMotor),
            new PivotAngle(m_pivotMotor, 90),
            Commands.race(new PivotMove(m_pivotMotor), new ClawOpen(m_clawMotor)),
            Commands.race(new ClawMove(m_clawMotor), new PivotAngle(m_pivotMotor, 20)),
            new PivotMove(m_pivotMotor)
        );
    }
}


                                                                           
