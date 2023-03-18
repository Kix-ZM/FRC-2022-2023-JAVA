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
    addPeriodic(m_robotContainer::updateShuffleboard, .04, .005);
  }

  //called periodically regardless of mode
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    // m_robotContainer.updateShuffleboard();
  }

  //called when the robot is disabled
  @Override
  public void disabledInit() {
    // RobotContainer.resetDesiredAngle();
  }

  //called continuously while robot is disabled
  @Override
  public void disabledPeriodic() {}

  //called when auto is selected
  @Override
  public void autonomousInit() {
    m_robotContainer.getAutoInput().schedule();
  }

  //called periodically when robot is in auto
  @Override
  public void autonomousPeriodic() {
    // System.out.println(m_robotContainer.getAutoInput().toString());
  }

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
