// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

// import frc.robot.RobotContainer;
import frc.robot.subsystems.CameraSub;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import java.util.function.Supplier;

public class CameraCMD extends CommandBase {
  private final CameraSub m_camSub;
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
  public CameraCMD(CameraSub camSub) {
    m_camSub = camSub;
    addRequirements(m_camSub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // m_camSub.startUp();
    // m_camSub.changeCamera();
    m_camSub.periodic();
  }



  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  public CameraSub getCamSub(){return m_camSub;}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}