package frc.robot;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.*;
import frc.robot.commands.AutoGroups.AutoGroup_Balance;
import frc.robot.commands.AutoGroups.AutoGroup_PlaceAndLeave;
import frc.robot.commands.claw.ClawClose;
import frc.robot.commands.claw.ClawMove;
import frc.robot.commands.claw.ClawOpen;
import frc.robot.commands.extend.ExtenderMove;
import frc.robot.commands.extend.MoveExtenderBackwards;
import frc.robot.commands.extend.MoveExtenderForward;
import frc.robot.commands.pivot.PivotAngle;
import frc.robot.commands.pivot.PivotDown;
import frc.robot.commands.pivot.PivotMove;
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

  //SMARTDASHBOARD
  private SendableChooser<String> m_autoChooser = new SendableChooser<String>();

  //SHUFFLEBOARD
  private ShuffleboardTab main = Shuffleboard.getTab("Driver's Tab");
    //GYRO INFO
    private GenericEntry entry_GyroX = 
        main.add("Pitch (Up/Down)", 0).withWidget(BuiltInWidgets.kGyro).getEntry();
    /*private GenericEntry entry_GyroY = 
        main.add("Roll", 0).withWidget(BuiltInWidgets.kGyro).getEntry();*/
    private GenericEntry entry_GyroZ = 
        main.add("Yaw (Side to Side)", 0).withWidget(BuiltInWidgets.kGyro).getEntry();
    //CLAW INFO
     private GenericEntry entry_ClawEncoder = 
         main.add("Claw Encoder", 0).withWidget(BuiltInWidgets.kTextView).getEntry();
    //PIVOT INFO
    private GenericEntry entry_PivotEncoder = 
      main.add("Pivot Encoder", 0).withWidget(BuiltInWidgets.kTextView).getEntry();
    private GenericEntry entry_PivotMaxAngle = 
      main.add("Pivot Max Angle", 0).withWidget(BuiltInWidgets.kTextView).getEntry();
    //EXTENSION INFO
    private GenericEntry entry_ExtEncoder = 
      main.add("Ext Encoder", 0).withWidget(BuiltInWidgets.kTextView).getEntry();
    //LIMELIGHT INFO
    private GenericEntry entry_LimelightXOffset =
      main.add("LimelightXOffset", 0).withWidget(BuiltInWidgets.kTextView).getEntry();
    private GenericEntry entry_LimelightYOffset =
      main.add("LimelightYOffset", 0).withWidget(BuiltInWidgets.kTextView).getEntry();
  

  public RobotContainer() {
    configureButtonBindings();
    main.add("Limelight", "CameraServer", "http://10.44.70.11:5800");
    main.add("Webcam", "CameraServer", "http://wpilibpi.local/1181");
    String[] autoList = {"Place and Leave", "Place and Balance", "Leave and Balance", "Do Nothing"};
    SmartDashboard.putStringArray("Auto List", autoList);
    initializeAutoChooser();
  }

  //update shuffleboard layout
  public void updateShuffleboard(){
    //GYRO
    entry_GyroX.setDouble(m_gyro.getAngleX());
    //entry_GyroY.setDouble(m_gyro.getAngleY()); DONT NEED ROLL - WILL LEAVE JUST IN CASE
    entry_GyroZ.setDouble(m_gyro.getAngleZ());

    //CLAW
    entry_ClawEncoder.setDouble(m_clawMotor.getClawEncoder().getPosition());

    // //PIVOT
    entry_PivotEncoder.setDouble(m_pivotMotor.getEncoder1().getPosition());
    entry_PivotMaxAngle.setDouble(m_pivotMotor.getMaxAngle());

    // //EXTENSION INFO
    entry_ExtEncoder.setDouble(m_extensionMotor.getEncoder().getPosition());

    // //LIMELIGHT INFO
    entry_LimelightXOffset.setDouble(m_limelight.getXOffset());
    entry_LimelightYOffset.setDouble(m_limelight.getYOffset());
  }

  public void initializeAutoChooser() {
    m_autoChooser.setDefaultOption("Do Nothing", "Do Nothing");
    //m_autoChooser.addOption("Leave Community", new AutoGroup_LeaveCommunity(m_drivetrain));
    m_autoChooser.addOption("Place and Leave", "Place and Leave");
    //m_autoChooser.addOption("Balance", new AutoGroup_Balance(m_drivetrain, m_gyro));
    m_autoChooser.addOption("Place and Balance", "Place and Balance");
    m_autoChooser.addOption("Leave and Balance", "Leave and Balance");
    //m_autoChooser.addOption("Move Test", new AutoGroup_MoveTest(m_drivetrain, m_gyro));
    SmartDashboard.putData(m_autoChooser);
  }

  //assign button functions
  private void configureButtonBindings() {
    m_drivetrain.setDefaultCommand(new ArcadeDrive(m_drivetrain, m_controller_drive));
    m_extensionMotor.setDefaultCommand(new ExtenderMove(m_extensionMotor));
    m_pivotMotor.setDefaultCommand(new PivotMove(m_pivotMotor));
    m_clawMotor.setDefaultCommand(new ClawMove(m_clawMotor, m_controller_arm));

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
    controllerButtons_drive.get("trigger").onTrue(new TurnToTarget(m_drivetrain, m_gyro, m_limelight));
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
    // controllerButtons_arm.get("1").onTrue(new ClawClampToggle(m_clawMotor));
    controllerButtons_arm.get("1").toggleOnTrue(Commands.startEnd(m_clawMotor::clamp2, m_clawMotor::moveMotors, m_clawMotor));
    // retracts arm
    controllerButtons_arm.get("4").whileTrue(new MoveExtenderBackwards(m_extensionMotor));
    // extends arm
    controllerButtons_arm.get("5").whileTrue(new MoveExtenderForward(m_extensionMotor));
    // moves pivot down
    controllerButtons_arm.get("2").whileTrue(new PivotDown(m_pivotMotor));
    // moves pivot up
    controllerButtons_arm.get("3").whileTrue(new PivotUp(m_pivotMotor));
    // select cube mode
    controllerButtons_arm.get("6").onTrue(new SetConeMode(m_limelight, m_clawMotor));
    // select cube mode
    controllerButtons_arm.get("7").onTrue(new SetCubeMode(m_limelight, m_clawMotor));
    //close claw
    controllerButtons_arm.get("8").whileTrue(new ClawClose(m_clawMotor));
    //open claw
    controllerButtons_arm.get("9").whileTrue(new ClawOpen(m_clawMotor));
    // move arm to have a 30 degree with the floor
    controllerButtons_arm.get("10").onTrue(new PivotAngle(m_pivotMotor, 30));
    // move arm to have a 90 degree with the floor
    controllerButtons_arm.get("11").onTrue(new PivotAngle(m_pivotMotor, 90));
  }

  public Command getAutoInput(){
    String autoName = m_autoChooser.getSelected(); //Make "Default" the default option
    System.out.println(autoName);
    
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
      case "Do Nothing":
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
  // resetting pivot position to current for when teleop inits
  public static void resetPivotPosition() {
    m_pivotMotor.zeroEncoder();
  }

  public static void resetDesiredAngle() {
    m_pivotMotor.resetDesiredToMin();
  }
}