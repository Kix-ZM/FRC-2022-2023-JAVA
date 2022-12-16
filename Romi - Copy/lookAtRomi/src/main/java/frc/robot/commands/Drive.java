// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import java.util.function.Supplier;

public class Drive extends CommandBase {
  private final Drivetrain m_drivetrain;
  private double axis1;
  private double axis2;
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
  public Drive(Drivetrain drivetrain) {
    m_drivetrain = drivetrain;
    if (Constants.DRIVETYPE){    
      axis1 = RobotContainer.m_lcontroller.getRawAxis(1);
      axis2 = RobotContainer.m_lcontroller.getRawAxis(0);
    }
    else{
      axis1 = RobotContainer.m_lcontroller.getRawAxis(1);
      axis2 = RobotContainer.m_rcontroller.getRawAxis(1);
    }
    
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Constants.DRIVETYPE){    
      m_drivetrain.arcadeDrive(-Constants.DRIVESCALAR*axis1,-Constants.DRIVESCALAR*axis2);
    }
    else{
      m_drivetrain.tankDrive(-Constants.DRIVESCALAR*axis1,-Constants.DRIVESCALAR*axis2);
    }
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
