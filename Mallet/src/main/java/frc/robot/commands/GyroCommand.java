// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

// import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
// import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import java.util.function.Supplier;

public class GyroCommand extends CommandBase {
  private final ADXRS450_Gyro m_gyro;
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
  public GyroCommand(ADXRS450_Gyro gyro) {
    m_gyro = gyro;
    // m_CAMSpeed = RobotContainer.m_controllerOther.getRawAxis(0);
    // m_triggerCheck = RobotContainer.m_controllerOther.getTrigger();
    //addRequirements(gyro);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_gyro.calibrate();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //m_gyro.turnArm(m_forwardArm,m_backwardArm);
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
