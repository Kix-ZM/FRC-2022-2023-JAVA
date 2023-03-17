// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.subsystems.ClawSub;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetCubeMode extends CommandBase {
    private final Limelight m_limelight;
    private final ClawSub m_claw;
    public SetCubeMode(Limelight limelight, ClawSub claw) {
        m_limelight = limelight;
        m_claw = claw;
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
        // sets limelight to look for april tags
        m_limelight.setPipeline(1);
        // sets claw target type to cube
        m_claw.setTargetType(0);

        //m_drivetrain.runTest(RobotContainer.m_controller.getRawAxis(2));
    }



    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}