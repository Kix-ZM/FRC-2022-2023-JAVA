package frc.robot.commands.AutoGroups;

import frc.robot.subsystems.ClawSub;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.GyroScope;
import frc.robot.subsystems.PivotSub;
import frc.robot.commands.AutoBalance;
import frc.robot.commands.ResetEncoders;
import frc.robot.commands.claw.ClawClose;
import frc.robot.commands.claw.ClawMove;
import frc.robot.commands.extend.ExtenderSetPositionWaitForComplete;
import frc.robot.commands.pivot.PivotAngle;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class AutoGroup_PlaceAndBalance extends SequentialCommandGroup {
    //Variables
    private Drivetrain m_Drivetrain;
    public AutoGroup_PlaceAndBalance(Drivetrain drivetrain, GyroScope gyro, PivotSub m_pivotMotor, ExtensionSub m_extensionMotor, ClawSub m_clawMotor){
        //Adding a drivetrain
        m_Drivetrain = drivetrain;
        //Adding Order of commands
        addCommands(
            new ResetEncoders(this.m_Drivetrain),
            new ClawClose(m_clawMotor),
            new WaitCommand(.5),
            new PivotAngle(m_pivotMotor, 120),
            new ExtenderSetPositionWaitForComplete(m_extensionMotor, -9),
            new ClawMove(m_clawMotor),
            new WaitCommand(.5),
            new ExtenderSetPositionWaitForComplete(m_extensionMotor, 0),
            new PivotAngle(m_pivotMotor, 5),
            new AutoBalance(drivetrain, gyro, false)
        );
    }
}
