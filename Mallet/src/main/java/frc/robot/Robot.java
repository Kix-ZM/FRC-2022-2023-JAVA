package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;

  //runs only when the robot is first started
  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
  }

  //called periodically regardless of mode
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  //called when the robot is disabled
  @Override
  public void disabledInit() {}

  //called continuously while robot is disabled
  @Override
  public void disabledPeriodic() {}

  //called when auto is selected
  @Override
  public void autonomousInit() {
    m_robotContainer.autoInput().schedule();
  }

  //called periodically when robot is in auto
  @Override
  public void autonomousPeriodic() {}

  //called when teleop is selected
  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  //called periodically when robot is in teleop
  @Override
  public void teleopPeriodic() {
    m_robotContainer.armControlCommand().schedule();
  }

  //called when test is selected
  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  //called periodically when robot is in test
  @Override
  public void testPeriodic() {}
}
