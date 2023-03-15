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
  public static Joystick m_controller_arm = new Joystick(0);
  public static Joystick m_controller_drive = new Joystick(1);

  //INIT JOYSTICK ARRAYS
  public static HashMap<String, Trigger> controllerButtons_arm = new HashMap<String, Trigger>();
  public static HashMap<String, Trigger> controllerButtons_drive = new HashMap<String, Trigger>();

  //INIT SMARTDASHBOARD
  SendableChooser<Command> m_autoChooser = new SendableChooser<Command>();

  public RobotContainer() {
    configureButtonBindings();
    //Default auto
    m_autoChooser.setDefaultOption("Default", new AutoGroup_Default(m_drivetrain));
    //drive forwards and leave the community
    m_autoChooser.addOption("Leave Community", new AutoGroup_LeaveCommunity(m_drivetrain));
    //place a game piece and leave the community
    m_autoChooser.addOption("Place and Leave", new AutoGroup_PlaceAndLeave(m_drivetrain));
    //Drive forward until it reaches the platform then balance
    m_autoChooser.addOption("Balance", new AutoGroup_Balance(m_drivetrain, m_gyro));
    //Place a game piece then drive forward and balance
    m_autoChooser.addOption("Place and Balance", new AutoGroup_PlaceAndBalance(m_drivetrain, m_gyro));
    //Leave the community over the Charge station and get back on and balance
    m_autoChooser.addOption("Leave and Balance", new AutoGroup_LeaveCommAndBalance(m_drivetrain, m_gyro));
    //A autogroup for testing
    m_autoChooser.addOption("Move Test", new AutoGroup_MoveTest(m_drivetrain, m_gyro));
    //Puts options in Smartdashboard
    SmartDashboard.putData("Select Auto", m_autoChooser);
  }

  //assign button functions
  private void configureButtonBindings() {
    m_drivetrain.setDefaultCommand(new ArcadeDrive(m_drivetrain));

    // Add joystick buttons to maps
    controllerButtons_drive.put("trigger", new JoystickButton(m_controller_drive, 1));
    controllerButtons_arm.put("trigger", new JoystickButton(m_controller_arm, 1));
    for (int i = 2; i <= 11; i++)
    {
      controllerButtons_arm.put(Integer.toString(i), new JoystickButton(m_controller_arm, i));
      controllerButtons_drive.put(Integer.toString(i), new JoystickButton(m_controller_drive, i));
    }

    //DRIVE CONTROLLER
    // rotate to target
    controllerButtons_drive.get("trigger").onTrue(new AimCommand(m_drivetrain, m_limelight));
    // reset encoders
    controllerButtons_drive.get("11").onTrue(resetEncodersCommand());
    // turn to 180 degrees
    controllerButtons_drive.get("2").onTrue(new TurnToMatch(m_drivetrain, m_gyro, 180));
    // turn to 0 degrees
    controllerButtons_drive.get("3").onTrue(new TurnToMatch(m_drivetrain, m_gyro, 0));
    // turn left 90 degrees
    controllerButtons_drive.get("4").onTrue(new TurnBy(m_drivetrain, m_gyro, -90));
    // turn left 90 degrees
    controllerButtons_drive.get("5").onTrue(new TurnBy(m_drivetrain, m_gyro, 90));

    //ARM CONTROLLER
    // select next piece to target
    controllerButtons_arm.get("8").onTrue(new NextPipeline(m_limelight));
  }

  // At the beginning of auto
  public Command getAutoInput(){
    return m_autoChooser.getSelected();
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