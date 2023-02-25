// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
// import frc.robot.RobotContainer;
import frc.robot.subsystems.GyroScope;
// import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import java.util.function.Supplier;

public class GyroCMD extends CommandBase {
  private final GyroScope m_gyro;
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
  public GyroCMD(GyroScope t_gyro) {
    m_gyro = t_gyro;
    addRequirements(t_gyro);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    //m_drivetrain.runTest(RobotContainer.m_controller.getRawAxis(2));
  }

  public boolean degCheck(){
    return (m_gyro.getAngleX() > Constants.degLimit || m_gyro.getAngleX() < -1*Constants.degLimit)? false: true;
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
