// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.RobotContainer;
// import frc.robot.RobotContainer;
import frc.robot.subsystems.GyroScope;
import frc.robot.subsystems.Drivetrain;
//import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import java.util.function.Supplier;
//import edu.wpi.first.wpilibj2.command.button.JoystickButton;

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
  static final double kOffBalanceAngleThresholdDegrees = 2.5;
  static final double kOonBalanceAngleThresholdDegrees = 5;
  boolean autoBalanceXMode=false;
  boolean autoBalanceYMode=false;
  Drivetrain m_Drivetrain;
  private double m_xaxisSpeed;
  private double m_zaxisRotate;

  public void autoBalance()
  {
    double xAxisRate = RobotContainer.m_controller.getX();
    double yAxisRate = RobotContainer.m_controller.getY();
    m_xaxisSpeed = RobotContainer.m_controller.getRawAxis(0);
    m_zaxisRotate = RobotContainer.m_controller.getRawAxis(1);
    double pitchAngleDegrees = m_gyro.getAngleX();
    double rollAngleDegrees = m_gyro.getAngleY();

    if (!autoBalanceXMode && (Math.abs(pitchAngleDegrees) >= Math.abs(kOffBalanceAngleThresholdDegrees))) {
        autoBalanceXMode = true;
    } else if (autoBalanceXMode && (Math.abs(pitchAngleDegrees) <= Math.abs(kOonBalanceAngleThresholdDegrees))) {
        autoBalanceXMode = false;
    }
    if (!autoBalanceYMode && (Math.abs(pitchAngleDegrees) >= Math.abs(kOffBalanceAngleThresholdDegrees))) {
        autoBalanceYMode = true;
    } else if (autoBalanceYMode && (Math.abs(pitchAngleDegrees) <= Math.abs(kOonBalanceAngleThresholdDegrees))) {
        autoBalanceYMode = false;
    }

    // Control drive system automatically,
    // driving in reverse direction of pitch/roll angle,
    // with a magnitude based upon the angle

    if (autoBalanceXMode) {
        double pitchAngleRadians = pitchAngleDegrees * (Math.PI / 180.0);
        xAxisRate = Math.sin(pitchAngleRadians) * -1;
    }
    if (autoBalanceYMode) {
        double rollAngleRadians = rollAngleDegrees * (Math.PI / 180.0);
        yAxisRate = Math.sin(rollAngleRadians) * -1;
    }
    if(yAxisRate>0.5){
      yAxisRate = 0.5;
    }else if(yAxisRate<-0.5){
      yAxisRate = -0.5;
    }

    if(xAxisRate>0.5){
      xAxisRate = 0.5;
    }else if(xAxisRate<-0.5){
      xAxisRate = -0.5;
    }

    try {
      m_Drivetrain.arcadeDrive(-xAxisRate,-yAxisRate);
    } catch (RuntimeException ex) {
           System.out.println("Drive system error: " + ex.getMessage() );
    } catch (Exception e){
        System.out.println(e);
    }

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
