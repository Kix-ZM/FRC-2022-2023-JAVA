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
    m_joystick = RobotContainer.getDriveController();
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    //Checks if the joystick is not active in order to update startTime
    if (Math.abs(m_joystick.getRawAxis(1)) < 0.1)
      startTime = System.currentTimeMillis() / 1000;
    currentTime = System.currentTimeMillis() / 1000;
    m_drivetrain.arcadeDrive(getSpeed(-m_joystick.getRawAxis(1)), -m_joystick.getRawAxis(0));
  }

  @Override
  public void end(boolean interrupted) {
    
  }

  @Override
  public boolean isFinished() {
    return false;
  }
  //Computes the speed based on time held
  public double getSpeed(double xaxisSpeed) {
    /*Uses an exponential function (can plug this into desmos): 
    f(x) = 2^(x/3)-0.8
    *0.8 is used to get the x-int of 0.2 in order to have a dead zone as any speed below 0.1 doesn't work
    */
    double speed = (Math.pow(2, (currentTime - startTime) / 3) - .8) * xaxisSpeed / Math.abs(xaxisSpeed);
    //Prevents the speed from going over the given joystick speed
    if (Math.abs(speed) > Math.abs(xaxisSpeed))
      speed = xaxisSpeed;
    return speed;
  }
}