package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoDrive_Backwards extends SequentialCommandGroup {
    //Variables
    Drivetrain m_Drivetrain;
    public AutoDrive_Backwards(Drivetrain drivetrain){
        //Adding a drivetrain
        m_Drivetrain = drivetrain;
        //Adding Order of commands
        System.out.println("Bac");
        addCommands(
            new ResetEncoders(this.m_Drivetrain),
            new MoveDistance(m_Drivetrain, Constants.backward_feet, true)
        );
    }
}