package frc.robot.commands.AutoGroups;

import frc.robot.subsystems.ClawSub;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.GyroScope;
import frc.robot.subsystems.PivotSub;
import frc.robot.Constants;
import frc.robot.commands.MoveDistance;
import frc.robot.commands.ResetEncoders;
import frc.robot.commands.claw.ClawOpen;
import frc.robot.commands.extend.ExtenderSetPosition;
import frc.robot.commands.pivot.PivotAngle;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoGroup_Routine1 extends SequentialCommandGroup {
    //Variables
    private Drivetrain m_Drivetrain;
    public AutoGroup_Routine1(Drivetrain drivetrain, PivotSub m_pivotMotor, GyroScope gyro, ClawSub m_clawMotor, ExtensionSub m_extensionMotor){
        //Adding a drivetrain
        m_Drivetrain = drivetrain;
        //Adding Order of commands
        addCommands(
            new ResetEncoders(m_Drivetrain),
            new PivotAngle(m_pivotMotor, 105),
            new ExtenderSetPosition(m_extensionMotor, 9),
            new ClawOpen(m_clawMotor),
            new 

            new MoveDistance(m_Drivetrain, Constants.K_FORWARDS_FEET, false)
        );
    }
}
