// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.claw.ClawClampToggle;
import frc.robot.commands.claw.ClawMove;
import frc.robot.commands.extend.ExtenderMove;
import frc.robot.commands.extend.MoveExtenderBackwards;
import frc.robot.commands.extend.MoveExtenderForward;
import frc.robot.commands.pivot.PivotMove;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.PivotSub;
import frc.robot.subsystems.ClawSub;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  public static Joystick m_rcontroller = new Joystick(0);
  public static JoystickButton m_grabber = new JoystickButton(m_rcontroller, 1);
  public static JoystickButton m_emerStop = new JoystickButton(m_rcontroller, 5);
  public static JoystickButton m_retract = new JoystickButton(m_rcontroller, 2);
  public static JoystickButton m_retracts = new JoystickButton(m_rcontroller,7);
  public static JoystickButton m_extend = new JoystickButton(m_rcontroller, 3);
  public static JoystickButton m_extends = new JoystickButton(m_rcontroller,6);

  //Subsystems
  // public ExtensionSub m_extensionMotor = new ExtensionSub();
  public PivotSub m_pivotMotor = new PivotSub();
  public ClawSub m_clawMotor = new ClawSub();

  public ExtensionSub m_extensionMotor2 = new ExtensionSub();

  // Create SmartDashboard chooser for autonomous routines
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    m_clawMotor.setDefaultCommand(new ClawMove(m_clawMotor, m_rcontroller));
    m_pivotMotor.setDefaultCommand(new PivotMove(m_pivotMotor, m_rcontroller));
    m_extensionMotor2.setDefaultCommand(new ExtenderMove(m_extensionMotor2));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // brent v2
    m_retract.whileTrue(new MoveExtenderBackwards(m_extensionMotor2));  // Retracts Arm
    m_extend.whileTrue(new MoveExtenderForward(m_extensionMotor2)); 
    m_grabber.onTrue(new ClawClampToggle(m_clawMotor));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }

  /**
   * Use this to pass the teleop command to the main {@link Robot} class.
   *
   * @return the command to run in teleop
   */
}
