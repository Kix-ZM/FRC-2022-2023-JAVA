package frc.robot.commands.AutoGroups;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.PivotSub;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ClawSub;
import frc.robot.subsystems.Drivetrain;


public class AutoGroup_MiddleDrop extends SequentialCommandGroup {
    //Variables

    public AutoGroup_MiddleDrop(Drivetrain m_Drivetrain, PivotSub m_pivotMotor, ExtensionSub m_extensionMotor, ClawSub m_clawMotor){
        //Adding Order of commands
        addCommands(
            // new ResetEncoders(m_Drivetrain),
            // new ClawCloseV2(m_clawMotor),
            // new PivotAngle(m_pivotMotor, 90),
            // Commands.race(new PivotMove(m_pivotMotor), new WaitCommand(1).andThen(new ClawOpenV2(m_clawMotor)).andThen(Commands.race(new ClawMoveV2(m_clawMotor), new PivotAngle(m_pivotMotor, 20)))),
            // new PivotMove(m_pivotMotor)
        );
    }
}


                                                                           
