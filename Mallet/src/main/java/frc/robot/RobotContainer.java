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
import frc.robot.commands.AutoGroups.AutoGroup_BalanceOnly;
import frc.robot.commands.AutoGroups.AutoGroup_Backwards;
import frc.robot.commands.AutoGroups.AutoGroup_Default;
import frc.robot.commands.AutoGroups.AutoGroup_Forwards;
import frc.robot.subsystems.*;
import java.util.HashMap;

public class RobotContainer {
  //INIT SUBSYSTEMS
  private static final Drivetrain m_drivetrain = new Drivetrain();
  private static final Limelight m_limelight = new Limelight();
  private static final GyroScope m_gyro = new GyroScope();

  //INIT JOYSTICKS (NOTE: PLEASE RENAME TO LEFT/RIGHT)
  public static Joystick m_lcontroller = new Joystick(0);
  public static Joystick m_rcontroller = new Joystick(1);

  //INIT JOYSTICK ARRAYS
  public static HashMap<String, Trigger> l_controllerButtons = new HashMap<String, Trigger>();
  public static HashMap<String, Trigger> r_controllerButtons = new HashMap<String, Trigger>();

  //INIT SMARTDASHBOARD
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  SendableChooser<Command> m_autoChooser = new SendableChooser<Command>();
  SequentialCommandGroup m_autoGroup = new SequentialCommandGroup();

  public RobotContainer() {
    configureButtonBindings();
    String[] autoList = {"Drive Forwards", "Drive Backwards", "Balance and Drive", "Default"};
    SmartDashboard.putStringArray("Auto List", autoList);
  }

  //assign button functions
  private void configureButtonBindings() {
    m_drivetrain.setDefaultCommand(new ArcadeDrive(m_drivetrain));

    // Add joystick buttons to maps
    l_controllerButtons.put("trigger", new JoystickButton(m_lcontroller, 1));
    r_controllerButtons.put("trigger", new JoystickButton(m_rcontroller, 1));
    for (int i = 2; i <= 11; i++)
    {
      l_controllerButtons.put(Integer.toString(i), new JoystickButton(m_lcontroller, i));
      r_controllerButtons.put(Integer.toString(i), new JoystickButton(m_rcontroller, i));
    }

    // rotate to target
    r_controllerButtons.get("trigger").onTrue(new AimCommand(m_drivetrain, m_limelight));
    // reset encoders
    r_controllerButtons.get("11").onTrue(resetEncodersCommand());
    // increment by 90 degrees
    l_controllerButtons.get("2").onTrue(turnAngleCommand(90.0f, true));
    // set to 90 degrees
    l_controllerButtons.get("3").onTrue(turnAngleCommand(90.0f, false));
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
        System.out.println("Balance Drive Case");
        activeAutoGroup = new AutoGroup_BalanceOnly(m_drivetrain, m_gyro);
        break;
      //Default auto
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
  public static Command turnAngleCommand(float turnAmount, boolean isTurningBy){
    return new TurnAngle(m_drivetrain, m_gyro, turnAmount, isTurningBy);
  }
}