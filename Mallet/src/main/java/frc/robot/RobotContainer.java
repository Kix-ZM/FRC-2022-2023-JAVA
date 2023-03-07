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
  private static final Drivetrain m_drivetrain = new Drivetrain();
  private static final ArmSubsystem m_armSub = new ArmSubsystem();
  private static final GyroScope m_gyro = new GyroScope();

  public static Joystick m_controller = new Joystick(0);
  public static Joystick m_controllerOther = new Joystick(1);
  public static JoystickButton m_fireButton = new JoystickButton(m_controller, 1);
  public static JoystickButton m_forwardButton = new JoystickButton(m_controllerOther, 2);
  public static JoystickButton m_backButton = new JoystickButton(m_controllerOther, 3);

  public static Trigger m_resetEncoderTrigger = new JoystickButton(m_controllerOther, 11);
  public static Trigger m_debugTrigger = new JoystickButton(m_controllerOther, 7);
  public static Trigger m_debugSeqTrigger = new JoystickButton(m_controllerOther, 6);

  public static Trigger m_turn90 = new JoystickButton(m_controller, 2);
  public static Trigger m_to90 = new JoystickButton(m_controller, 3);
  public static Trigger m_turn2 = new JoystickButton(m_controller, 4);
  public static Trigger m_turn5 = new JoystickButton(m_controller, 5);

  // Create SmartDashboard chooser for autonomous routines
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  SendableChooser<Command> m_autoChooser = new SendableChooser<Command>();
  SequentialCommandGroup m_autoGroup = new SequentialCommandGroup();

  public RobotContainer() {
    configureButtonBindings();
    String[] autoList = {"Drive Forwards", "Drive Backwards", "Balance and Drive", "Default"};
    SmartDashboard.putStringArray("Auto List", autoList);
    m_drivetrain.setDefaultCommand(new ArcadeDrive(m_drivetrain));
  }

  // At the beginning of auto
  public Command autoInput(){
    String autoName = SmartDashboard.getString("Auto Selector", "Drive Forwards"); // This would make "Drive Forwards the default auto
    System.out.println("Cal");
    
    Command activeAutoGroup;
    switch(autoName) { //switch between autonomous modes
      case "Drive Forwards":
        activeAutoGroup = new AutoGroup_Forwards(m_drivetrain);
        break;
      case "Drive Backwards":
        activeAutoGroup = new AutoGroup_Backwards(m_drivetrain);
        break;
      case "Balance and Drive":
        activeAutoGroup = new AutoGroup_BalanceDrive(m_drivetrain, m_gyro);
      case "Default":
      default:
        activeAutoGroup = new AutoGroup_Default(m_drivetrain);  
        break;
    }

    return activeAutoGroup;
  }

  private void configureButtonBindings() {
    m_resetEncoderTrigger.onTrue(resetEncodersCommand());
    m_debugSeqTrigger.onTrue(driveTillPlatformCommand());
    m_turn2.onTrue(turnAngleCommand(2.0f, true));
    m_turn5.onTrue(turnAngleCommand(5.0f, true));
    m_turn90.onTrue(turnAngleCommand(90.0f, true));
    m_to90.onTrue(turnAngleCommand(90.0f, false));
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