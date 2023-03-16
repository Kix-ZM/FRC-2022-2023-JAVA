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
import frc.robot.commands.claw.ClawClampToggle;
import frc.robot.commands.extend.MoveExtenderBackwards;
import frc.robot.commands.extend.MoveExtenderForward;
import frc.robot.commands.pivot.PivotAngle;
import frc.robot.commands.pivot.PivotDown;
import frc.robot.commands.pivot.PivotUp;
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
  private static final PivotSub m_pivotMotor = new PivotSub();
  private static final ClawSub m_clawMotor = new ClawSub();
  private static final ExtensionSub m_extensionMotor = new ExtensionSub();

  //INIT JOYSTICKS (NOTE: PLEASE RENAME TO LEFT/RIGHT)
  public static Joystick m_controller_arm = new Joystick(0);
  public static Joystick m_controller_drive = new Joystick(1);

  //INIT JOYSTICK ARRAYS
  public static HashMap<String, Trigger> controllerButtons_arm = new HashMap<String, Trigger>();
  public static HashMap<String, Trigger> controllerButtons_drive = new HashMap<String, Trigger>();

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
    controllerButtons_drive.put("trigger", new JoystickButton(m_controller_drive, 1));
    controllerButtons_arm.put("trigger", new JoystickButton(m_controller_arm, 1));
    for (int i = 1; i <= 11; i++)
    {
      controllerButtons_arm.put(Integer.toString(i), new JoystickButton(m_controller_arm, i));
      controllerButtons_drive.put(Integer.toString(i), new JoystickButton(m_controller_drive, i));
    }

    //DRIVE CONTROLLER
    // rotate to target
    controllerButtons_drive.get("trigger").onTrue(new AimCommand(m_drivetrain, m_limelight));
    // turn to 180 degrees
    controllerButtons_drive.get("2").onTrue(new TurnToMatch(m_drivetrain, m_gyro, 180));
    // turn to 0 degrees
    controllerButtons_drive.get("3").onTrue(new TurnToMatch(m_drivetrain, m_gyro, 0));
    // turn left 90 degrees
    controllerButtons_drive.get("4").onTrue(new TurnBy(m_drivetrain, m_gyro, -90));
    // turn left 90 degrees
    controllerButtons_drive.get("5").onTrue(new TurnBy(m_drivetrain, m_gyro, 90));
    // reset encoders
    controllerButtons_drive.get("11").onTrue(resetEncodersCommand());

    //ARM CONTROLLER
    // toggle claw clamp
    controllerButtons_arm.get("1").onTrue(new ClawClampToggle(m_clawMotor));
    // retracts arm
    controllerButtons_arm.get("2").whileTrue(new MoveExtenderForward(m_extensionMotor));
    // extends arm
    controllerButtons_arm.get("3").whileTrue(new MoveExtenderBackwards(m_extensionMotor));
    // moves pivot down
    controllerButtons_arm.get("4").onTrue(new PivotDown(m_pivotMotor));
    // moves pivot up
    controllerButtons_arm.get("5").onTrue(new PivotUp(m_pivotMotor));
    // select next piece to target
    controllerButtons_arm.get("8").onTrue(new NextPipeline(m_limelight));
    // move arm to have a 30 degree with the floor
    controllerButtons_arm.get("10").onTrue(new PivotAngle(m_pivotMotor, 30));
    // move arm to have a 90 degree with the floor
    controllerButtons_arm.get("11").onTrue(new PivotAngle(m_pivotMotor, 90));

  }

  // At the beginning of auto
  public Command getAutoInput(){
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
        activeAutoGroup = new AutoGroup_PlaceAndLeave(m_drivetrain, m_gyro);
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

  public static Joystick getDriveController(){
    return m_controller_drive;
  }
  public static Joystick getArmController(){
    return m_controller_arm;
  }
}