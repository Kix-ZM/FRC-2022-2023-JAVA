// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
// import frc.robot.commands.LauncherCMD;
// import frc.robot.commands.AutonomousDistance;
// import frc.robot.commands.AutonomousTime;
import frc.robot.subsystems.Drivetrain;
// import frc.robot.subsystems.Launcher;
// import frc.robot.subsystems.OnBoardIO;
// import frc.robot.subsystems.OnBoardIO.ChannelMode;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import edu.wpi.first.wpilibj2.command.PrintCommand;
// import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.*;
import frc.robot.commands.AutoSequentialGroup.AutoDrive_Backwards;
import frc.robot.commands.AutoSequentialGroup.AutoDrive_Default;
import frc.robot.commands.AutoSequentialGroup.AutoDrive_Forwards;
import frc.robot.commands.Debug.DebugCommand;
import frc.robot.commands.Debug.DebugSequentialCommand;
import frc.robot.subsystems.*;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private static final Drivetrain m_drivetrain = new Drivetrain();
  // private static final Launcher m_launcher = new Launcher();
  private static final ArmSubsystem m_armSub = new ArmSubsystem();
  private static final GyroScope m_gyro = new GyroScope();
 // private static final ADXRS450_Gyro m_gyro = new ADXRS450_Gyro();
  // private final OnBoardIO m_onboardIO = new OnBoardIO(ChannelMode.INPUT, ChannelMode.INPUT);
  // Assumes a gamepad plugged into channnel 0
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


  // NOTE: The I/O pin functionality of the 5 exposed I/O pins depends on the hardware "overlay"
  // that is specified when launching the wpilib-ws server on the Romi raspberry pi.
  // By default, the following are available (listed in order from inside of the board to outside):
  // - DIO 8 (mapped to Arduino pin 11, closest to the inside of the board)
  // - Analog In 0 (mapped to Analog Channel 6 / Arduino Pin 4)
  // - Analog In 1 (mapped to Analog Channel 2 / Arduino Pin 20)
  // - PWM 2 (mapped to Arduino Pin 21)
  // - PWM 3 (mapped to Arduino Pin 22)
  //
  // Your subsystem configuration should take the overlays into account

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    /*
    m_autoChooser.setDefaultOption("Default", getAutoDrive_Default());
    m_autoChooser.addOption("Forwards", getAutoDrive_Forwards());
    m_autoChooser.addOption("Backwards", getAutoDrive_Backwards());
    SmartDashboard.putData("Selected AutoData", m_autoChooser);
    // Configure the button bindings
    */
    // m_drivetrain.setDefaultCommand(new ArcadeDrive(m_drivetrain));
    configureButtonBindings();
    String[] autoList = {"Drive Forwards", "Drive Backwards", "Balance", "Default"};
    SmartDashboard.putStringArray("Auto List", autoList);
    m_drivetrain.setDefaultCommand(new ArcadeDrive(m_drivetrain));
    
  }
  public void checkAutoInput(){
    // At the beginning of auto
    String autoName = SmartDashboard.getString("Auto Selector", "Drive Forwards"); // This would make "Drive Forwards the default auto
    System.out.println("Cal");
    switch(autoName) {
      case "Drive Forwards":
        m_autoGroup = getAutoDrive_Forwards();
        break;
      case "Drive Backwards":
        m_autoGroup = getAutoDrive_Backwards();
        break;
      case "Balance":
        m_autoGroup = getDebugSequentialCommand();
      case "Default":
        m_autoGroup = getAutoDrive_Default();
        break;
      default:
        break;
    }
  }
  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    m_resetEncoderTrigger.onTrue(getResetEncodersCommand());
    m_debugTrigger.onTrue(getDebugCommand());
    m_debugSeqTrigger.onTrue(getGyroCMD());
    m_turn2.onTrue(getTurnAngle(2.0f, true));
    m_turn5.onTrue(getTurnAngle(5.0f, true));
    m_turn90.onTrue(getTurnAngle(90.0f, true));
    m_to90.onTrue(getTurnAngle(90.0f, false));
    // Default command is arcade drive. This will run unless another command
    // is scheduled over it.
    
    // Example of how to use the onboard IO
    // Button onboardButtonA = new Button(m_onboardIO::getButtonAPressed);
    // onboardButtonA
    //     .whenActive(new PrintCommand("Button A Pressed"))
    //     .whenInactive(new PrintCommand("Button A Released"));

    // Setup SmartDashboard options
    // m_chooser.setDefaultOption("Auto Routine Distance", new AutonomousDistance(m_drivetrain));
    // m_chooser.addOption("Auto Routine Time", new AutonomousTime(m_drivetrain));
    // SmartDashboard.putData(m_chooser);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //return m_chooser.getSelected();
    // return new AutoDrive(m_drivetrain);
    return null;
  }

  /**
   * Use this to pass the teleop command to the main {@link Robot} class.
   *
   * @return the command to run in teleop
   */
  public Command getArcadeDriveCommand() {
    return new ArcadeDrive(m_drivetrain);
  }

  public Command getResetEncodersCommand(){
    return new ResetEncoders(m_drivetrain);
  }
  
  public Command getAutoDriveCommand(){
    //return m_autoChooser.getSelected();
    checkAutoInput();
    return m_autoGroup;
  }

  public Command getGyroCMD(){
    return new GyroCMD(m_gyro);
  }

  public Command getDebugCommand(){
    return new DebugCommand(m_gyro);
  }
  public SequentialCommandGroup getDebugSequentialCommand(){
    return new DebugSequentialCommand(m_drivetrain, m_gyro);
  }

  public Command getArmCommand(){
    return new ArmCommand(m_armSub);
  }

  public SequentialCommandGroup getAutoDrive_Default(){
    return new AutoDrive_Default(m_drivetrain);
  }
  public SequentialCommandGroup getAutoDrive_Forwards(){
    return new AutoDrive_Forwards(m_drivetrain);
  }
  public SequentialCommandGroup getAutoDrive_Backwards(){
    return new AutoDrive_Backwards(m_drivetrain);
  }

  public static Drivetrain getDriveTrainSub(){
    return m_drivetrain;
  }
  public static Command getTurnAngle(float turnAmount, boolean isTurningBy){
    return new TurnAngle(m_drivetrain, m_gyro, turnAmount, isTurningBy);
  }
}