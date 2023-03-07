package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.*;
import frc.robot.commands.AutoGroups.AutoGroup_BalanceDrive;
import frc.robot.commands.AutoGroups.AutoGroup_Backwards;
import frc.robot.commands.AutoGroups.AutoGroup_Default;
import frc.robot.commands.AutoGroups.AutoGroup_Forwards;
import frc.robot.subsystems.*;

public class RobotContainer {
  //INIT SUBSYSTEMS
  private static final Drivetrain m_drivetrain = new Drivetrain();
  private static final ArmSubsystem m_armSub = new ArmSubsystem();
  private static final GyroScope m_gyro = new GyroScope();

  //INIT JOYSTICKS (NOTE: PLEASE RENAME TO LEFT/RIGHT)
  public static Joystick m_controller = new Joystick(0);
  public static Joystick m_controllerOther = new Joystick(1);

  //INIT CONTROLS
  public static Trigger m_trigger_resetEncoder = new JoystickButton(m_controllerOther, 11);
  public static Trigger m_button_turnBy90 = new JoystickButton(m_controller, 2);
  public static Trigger m_button_goto90 = new JoystickButton(m_controller, 3);

  //INIT SMARTDASHBOARD
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  SendableChooser<Command> m_autoChooser = new SendableChooser<Command>();
  SequentialCommandGroup m_autoGroup = new SequentialCommandGroup();

  public RobotContainer() {
    configureButtonBindings();
    String[] autoList = {"Drive Forwards", "Drive Backwards", "Balance and Drive", "Default"};
    SmartDashboard.putStringArray("Auto List", autoList);
    m_drivetrain.setDefaultCommand(new ArcadeDrive(m_drivetrain));
  }

  //assign button functions
  private void configureButtonBindings() {
    m_trigger_resetEncoder.onTrue(resetEncodersCommand());
    m_button_turnBy90.onTrue(turnAngleCommand(90.0f, true));
    m_button_goto90.onTrue(turnAngleCommand(90.0f, false));
  }

  // At the beginning of auto
  public Command autoInput(){
    String autoName = SmartDashboard.getString("Auto Selector", "Default"); //Make "Default" the default option
    System.out.println("Cal");
    
    Command activeAutoGroup;
    switch(autoName) { //switch between autonomous modes
      //just makes the robot drive forwards
      case "Drive Forwards":
        activeAutoGroup = new AutoGroup_Forwards(m_drivetrain);
        break;
      //just makes the robot drive backwards
      case "Drive Backwards":
        activeAutoGroup = new AutoGroup_Backwards(m_drivetrain);
        break;
      //Drive forward until it reaches the platform then attempt to balance
      case "Balance and Drive":
        activeAutoGroup = new AutoGroup_BalanceDrive(m_drivetrain, m_gyro);
      //Default auto
      case "Default":
      default:
        activeAutoGroup = new AutoGroup_Default(m_drivetrain);  
        break;
    }

    return activeAutoGroup;
  }

  public Command resetEncodersCommand(){
    return new ResetEncoders(m_drivetrain);
  }
  public Command driveTillPlatformCommand(){
    return new DriveTillPlatform(m_drivetrain, m_gyro);
  }
  public Command armControlCommand(){
    return new ArmControl(m_armSub);
  }
  public static Command turnAngleCommand(float turnAmount, boolean isTurningBy){
    return new TurnAngle(m_drivetrain, m_gyro, turnAmount, isTurningBy);
  }
}