package frc.robot.commands.AutoGroups;

import frc.robot.subsystems.ClawSub;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.GyroScope;
import frc.robot.subsystems.PivotSub;
import frc.robot.commands.MoveDistance;
import frc.robot.commands.ResetEncoders;
import frc.robot.commands.TurnBy;
import frc.robot.commands.claw.ClawClose;
import frc.robot.commands.claw.ClawOpen;
import frc.robot.commands.extend.MoveExtenderForward;
import frc.robot.commands.pivot.PivotAngle;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoGroup_Routine1 extends SequentialCommandGroup {
    
    //Variables
    private Drivetrain m_Drivetrain;
    public AutoGroup_Routine1(Drivetrain drivetrain, GyroScope gyro, PivotSub m_pivotMotor, ClawSub m_clawMotor, ExtensionSub m_extensionMotor){
        //Adding a drivetrain
        m_Drivetrain = drivetrain;
        //Adding Order of commands
        addCommands(
            new ResetEncoders(m_Drivetrain),
            new PivotAngle(m_pivotMotor, 30),
            new ClawClose(m_clawMotor),
            new TurnBy(drivetrain, gyro, 180),
            new MoveDistance(drivetrain, 15, isFinished()), //Not sure how many feet we have to move back
            new PivotAngle(m_pivotMotor, 90),
            new MoveExtenderForward(m_extensionMotor),
            new ClawOpen(m_clawMotor),
            new TurnBy(drivetrain, gyro, 180)
        );
    }
}
