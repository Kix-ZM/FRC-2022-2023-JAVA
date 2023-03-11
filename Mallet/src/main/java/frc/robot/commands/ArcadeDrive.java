package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArcadeDrive extends CommandBase {
  private final Drivetrain m_drivetrain;
  private Joystick m_joystick;

  public ArcadeDrive(Drivetrain drivetrain) {
    m_drivetrain = drivetrain;
    m_joystick = RobotContainer.m_lcontroller;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(-m_joystick.getRawAxis(1),-m_joystick.getRawAxis(0));
    //m_drivetrain.runTest(RobotContainer.m_controller.getRawAxis(2));
  }

  @Override
  public void end(boolean interrupted) {
    
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
