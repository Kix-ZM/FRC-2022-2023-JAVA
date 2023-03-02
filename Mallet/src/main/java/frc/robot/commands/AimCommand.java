// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import java.util.function.Supplier;

public class AimCommand extends CommandBase {
    private final Drivetrain m_drivetrain;
    private final Limelight m_limelight;
    private final double cSpeed;

    public AimCommand(Drivetrain drivetrain, Limelight limelight, Double currentSpeed) {
        m_drivetrain = drivetrain;
        m_limelight = limelight;
        cSpeed = currentSpeed;
        addRequirements(drivetrain);
        addRequirements(limelight);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

    }

    // Called every time the scheduler runs while the command is scheduled.

    // CHANGE TO IMPLEMENT TURNANGLE WHEN MERGE 
    @Override
    public void execute() {
        m_drivetrain.arcadeDrive(-cSpeed,m_limelight.getXCheckAlign()*Constants.ptd);
        //m_drivetrain.runTest(RobotContainer.m_controller.getRawAxis(2));
    }



    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
