// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

// import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;
// import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import java.util.function.Supplier;

public class ArmCommand extends CommandBase {
  private final ArmSubsystem m_armSub;
  private boolean m_backwardArm;
  private boolean m_forwardArm;
  // private double  m_CAMSpeed;
  // private boolean m_triggerCheck;
  //private final Supplier<Double> m_xaxisSpeedSupplier;
  //private final Supplier<Double> m_zaxisRotateSupplier;

  /**
   * Creates a new ArcadeDrive. This command will drive your robot according to the speed supplier
   * lambdas. This command does not terminate.
   *
   * @param drivetrain The drivetrain subsystem on which this command will run
   * @param xaxisSpeedSupplier Lambda supplier of forward/backward speed
   * @param zaxisRotateSupplier Lambda supplier of rotational speed
   */
  public ArmCommand(ArmSubsystem armSub) {
    m_armSub = armSub;
    m_forwardArm = RobotContainer.m_controllerOther.getRawButton(2);
    m_backwardArm = RobotContainer.m_controllerOther.getRawButton(3);
    // m_CAMSpeed = RobotContainer.m_controllerOther.getRawAxis(0);
    // m_triggerCheck = RobotContainer.m_controllerOther.getTrigger();
    addRequirements(armSub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_armSub.stopMotors();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_armSub.turnArm(m_forwardArm,m_backwardArm);
    //m_armSub.shiftArm(m_CAMSpeed, m_triggerCheck);
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
