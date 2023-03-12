package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArcadeDrive extends CommandBase {
  private final Drivetrain m_drivetrain;
  private Joystick m_joystick;
  private double startTime, currentTime;

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
    if (Math.abs(m_joystick.getRawAxis(1)) < 0.1)
      startTime = System.currentTimeMillis() / 1000;
    currentTime = System.currentTimeMillis() / 1000;
    //m_drivetrain.arcadeDrive(-m_joystick.getRawAxis(1),-m_joystick.getRawAxis(0));
    m_drivetrain.arcadeDrive(getSpeed(-m_joystick.getRawAxis(1)), -m_joystick.getRawAxis(0));
    //m_drivetrain.runTest(RobotContainer.m_controller.getRawAxis(2));
  }

  @Override
  public void end(boolean interrupted) {
    
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  public double getSpeed(double xaxisSpeed) {
    double speed = (Math.pow(2, (currentTime - startTime) / 3) - .8) * xaxisSpeed / Math.abs(xaxisSpeed);
    if (Math.abs(speed) > Math.abs(xaxisSpeed))
      speed = xaxisSpeed;
    return speed;
  }
}