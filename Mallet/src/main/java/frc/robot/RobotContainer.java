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
import frc.robot.commands.AutoGroups.AutoGroup_Balance;
import frc.robot.commands.AutoGroups.AutoGroup_PlaceAndLeave;
import frc.robot.commands.AutoGroups.AutoGroup_PlaceAndBalance;
import frc.robot.commands.AutoGroups.AutoGroup_Default;
import frc.robot.commands.AutoGroups.AutoGroup_LeaveCommAndBalance;
import frc.robot.commands.AutoGroups.AutoGroup_LeaveCommunity;
import frc.robot.commands.AutoGroups.AutoGroup_MoveTest;
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
    String[] autoList = {"Leave Community", "Place and Leave", "Balance", "Place and Balance", "Leave and Balance", "Move Test", "Default"};
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
    l_controllerButtons.get("trigger").onTrue(new AimCommand(m_drivetrain, m_limelight));
    // reset encoders
    l_controllerButtons.get("11").onTrue(resetEncodersCommand());
    // increment by 90 degrees
    r_controllerButtons.get("2").onTrue(turnAngleCommand(-90.0f, true));
    // set to 90 degrees
    r_controllerButtons.get("3").onTrue(turnAngleCommand(90.0f, false));
    l_controllerButtons.get("4").onTrue(new TurnBy(m_drivetrain, m_gyro, -90));
    l_controllerButtons.get("5").onTrue(new TurnBy(m_drivetrain, m_gyro, 90));
    r_controllerButtons.get("8").onTrue(new NextPipeline(m_limelight));
  }

  // At the beginning of auto
  public Command autoInput(){
    String autoName = SmartDashboard.getString("Auto Selector", "Default"); //Make "Default" the default option
    System.out.println("Cal");
    
    Command activeAutoGroup;
    switch(autoName) { //switch between autonomous modes
      //drive forwards and leave the community
      case "Leave Community":
        activeAutoGroup = new AutoGroup_LeaveCommunity(m_drivetrain);
        break;
      //place a game piece and leave the community
      case "Place and Leave":
        activeAutoGroup = new AutoGroup_PlaceAndLeave(m_drivetrain);
        break;
      //Drive forward until it reaches the platform then balance
      case "Balance":
        activeAutoGroup = new AutoGroup_Balance(m_drivetrain, m_gyro);
        break;
      //Place a game piece then drive forward and balance
      case "Place and Balance":
        activeAutoGroup = new AutoGroup_PlaceAndBalance(m_drivetrain, m_gyro);
        break;
      //Leave the community over the Charge station and get back on and balance
      case "Leave and Balance":
          activeAutoGroup = new AutoGroup_LeaveCommAndBalance(m_drivetrain, m_gyro);
        break;
      case "Move Test":
        activeAutoGroup = new AutoGroup_MoveTest(m_drivetrain, m_gyro);
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

  public static Command turnAngleCommand(float turnAmount, boolean isTurningBy){
    return new TurnAngle(m_drivetrain, m_gyro, turnAmount, isTurningBy);
  }
}