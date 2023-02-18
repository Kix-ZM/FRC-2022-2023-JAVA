package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import frc.robot.commands.ResetEncoders;
import frc.robot.commands.AutoDrive;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Auto extends SequentialCommandGroup {
    //Variables
    Drivetrain m_Drivetrain;
    public Auto(Drivetrain drivetrain){
        //Adding a drivetrain
        m_Drivetrain = drivetrain;
        //Adding Order of commands
        addCommands(
            new ResetEncoders(this.m_Drivetrain),
            new AutoDrive(this.m_Drivetrain)
        );
    }
}