// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

// import frc.robot.Robot;
// import frc.robot.Robot;
// import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;
import edu.wpi.first.wpilibj.Joystick;
// import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import java.util.function.Supplier;
// import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class ArmCommand extends CommandBase {
  private final ArmSubsystem m_armSub;
  private final int m_process;

  // Speed via joystick and via the weird axis thingy
  // Meant to be used only on extention, rotation shall be done via external means
  private final Joystick m_Joystick;
  /**
   * Creates a new ArcadeDrive. This command will drive your robot according to the speed supplier
   * lambdas. This command does not terminate.
   *
   * @param drivetrain The drivetrain subsystem on which this command will run
   * @param xaxisSpeedSupplier Lambda supplier of forward/backward speed
   * @param zaxisRotateSupplier Lambda supplier of rotational speed
   */
  public ArmCommand(ArmSubsystem armSub, Joystick t_joy, int process) {
    m_armSub = armSub;
    m_Joystick = t_joy;
    m_process = process;
    addRequirements(armSub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    /*Arm Controls : 
    Z - Axis controls pivot
    Z - Axis w/ Trigger pressed controls extension
    Buton 3, 4, 5 controls arm
    #3 is for retracting claw to max
    #4 is for grabbing a cone
    #5 is for grabbing a cube
    */

    switch(m_process){
      case 1:
        // TO BE CHANGED WHEN SAGAR FINDS THE RIGHT SPEED TO USED!!!
        m_armSub.grabClaw(1.0);
      break;
      case 2:
        m_armSub.stopClaw();
        m_armSub.stopExtension();
        m_armSub.stopPivot();
      break;
      case 3:
        m_armSub.toggleExtentionMotorIdle();
      break;
      case 4:
        m_armSub.extendArm(m_Joystick.getRawAxis(2));
      break;
      case 5:
        m_armSub.extendArm(-m_Joystick.getRawAxis(2));
      break;
      case 8:
        m_armSub.pivotArm(m_Joystick.getRawAxis(2));
      break;
      case 9:
        m_armSub.pivotArm(-m_Joystick.getRawAxis(2));
      break;
      default:
        System.out.println("ERROR SHOULD NEVER BE HERE!!! - ARM COMMAND RUNNING DEFAULT");
      break;
    }

    /*
    if(m_trigger){
      m_armSub.extendArm(m_zAxis);
    }else{
      m_armSub.pivotArm(m_zAxis);
    }
    if(m_retractButton){
      m_armSub.retractClaw();
    }else if(m_coneButton){
      m_armSub.grabCone();
    }else if(m_cubeButton){
      m_armSub.grabCube();
    }
     */
  }



  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_armSub.stopClaw();
    m_armSub.stopExtension();
    m_armSub.stopPivot();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
