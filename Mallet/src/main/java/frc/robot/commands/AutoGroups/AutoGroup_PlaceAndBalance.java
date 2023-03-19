package frc.robot.commands.AutoGroups;

import frc.robot.subsystems.ClawSub;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.GyroScope;
import frc.robot.subsystems.PivotSub;
import frc.robot.commands.AutoBalance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class AutoGroup_PlaceAndBalance extends SequentialCommandGroup {
    //Variables
    public AutoGroup_PlaceAndBalance(Drivetrain drivetrain, GyroScope gyro, PivotSub m_pivotMotor, ExtensionSub m_extensionMotor, ClawSub m_clawMotor){
        //Adding a drivetrain
        //Adding Order of commands
        addCommands(
            new AutoGroup_MiddleDrop(drivetrain, m_pivotMotor, m_extensionMotor, m_clawMotor),
            new AutoBalance(drivetrain, gyro, true)
        );
    }
}
