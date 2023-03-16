package frc.robot.commands.AutoGroups;

import frc.robot.subsystems.ClawSub;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.GyroScope;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.PivotSub;
import frc.robot.commands.AutoBalance;
import frc.robot.commands.MoveDistance;
import frc.robot.commands.ResetEncoders;
import frc.robot.commands.claw.*;
import frc.robot.commands.extend.*;
import frc.robot.commands.pivot.*;
import frc.robot.commands.pivot.PivotAngle;
import frc.robot.commands.pivot.PivotMoveToAngle;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;

public class AutoGroup_Place extends SequentialCommandGroup {
    //Variables
    private Drivetrain m_Drivetrain;
    private GyroScope m_Gyro;
    private ClawSub m_Claw;
    private ExtensionSub m_Extension;
    private PivotSub m_Pivot;
    private Limelight m_Limelight;
    public AutoGroup_Place(Drivetrain drivetrain, GyroScope gyro, Limelight limelight, ClawSub claw, ExtensionSub extension, PivotSub pivot){
        //Adding a drivetrain
        m_Drivetrain = drivetrain;
        m_Gyro = gyro;
        m_Claw = claw;
        m_Extension = extension;
        m_Pivot = pivot;
        
        //Adding Order of commands
        //Assumes robot is two feet away from starting
        addCommands(
            new ResetEncoders(m_Drivetrain),
            new PivotMoveToAngle(m_Pivot, 90),
            //Use ParalelRaceGroup as only one of these commands will finish and when it does that means its time to move to the next step
            new ParallelRaceGroup(
                new PivotIdle(m_Pivot),
                new MoveDistance(m_Drivetrain, 2, false)
            ),
            new ParallelRaceGroup(
                new ClawOpen(m_Claw),
                new PivotIdle(m_Pivot)
            )           
        );
    }
}
