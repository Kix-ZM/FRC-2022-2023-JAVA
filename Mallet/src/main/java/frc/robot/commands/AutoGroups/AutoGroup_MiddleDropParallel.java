package frc.robot.commands.AutoGroups;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.PivotSub;
import frc.robot.commands.MotorsMove;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ClawSubV2;
import frc.robot.subsystems.Drivetrain;

public class AutoGroup_MiddleDropParallel extends SequentialCommandGroup {
    //Variables

    public AutoGroup_MiddleDropParallel(Drivetrain m_Drivetrain, PivotSub m_pivotMotor, ExtensionSub m_extensionMotor, ClawSubV2 m_clawMotor){
        //Adding Order of commands
        addCommands(
            Commands.race(new MotorsMove(m_clawMotor, m_extensionMotor, m_pivotMotor), new AutoGroup_MiddleDrop(m_Drivetrain, m_pivotMotor, m_extensionMotor, m_clawMotor))
        );
    }
}


                                                                           
