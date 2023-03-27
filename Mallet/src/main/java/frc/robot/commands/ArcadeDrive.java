package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArcadeDrive extends CommandBase {
  private final Drivetrain m_drivetrain;
  private Joystick m_joystick;
  private double startTime, currentTime;

  public ArcadeDrive(Drivetrain drivetrain, Joystick joystick) {
    m_drivetrain = drivetrain;
    m_joystick = joystick;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    //Checks if the joystick is not active in order to update startTime
    m_drivetrain.arcadeDrive(-m_joystick.getRawAxis(1), -m_joystick.getRawAxis(0));
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
  
  //Computes the speed based on time held
  public double getSpeed(double xaxisSpeed) {
    /*
    Uses an exponential function (can plug this into desmos): 
    f(x) = 2^(x/3)-0.8
    *0.8 is used to get the x-int of 0.2 in order to have a dead zone as any speed below 0.1 doesn't work
    *3000 is 3 is time in seconds to max speed (aka 1 although it is not exactly at 3) and the 1000 is for the converting to milliseconds
    */
    double speed = (Math.pow(2, (currentTime - startTime) / 3000) - .8) * Math.signum(xaxisSpeed);
    //Prevents the speed from going over the given joystick speed both negative and positibe
    if (Math.abs(speed) > Math.abs(xaxisSpeed))
      speed = xaxisSpeed;
    return speed;
  }
}