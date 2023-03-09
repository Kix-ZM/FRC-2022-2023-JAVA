// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.*;
import frc.robot.commands.*;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  //
  public static Joystick m_controller = new Joystick(0);
  public static JoystickButton m_emerStop = new JoystickButton(m_controller, 2);
  public static JoystickButton m_swap = new JoystickButton(m_controller, 3);

  public static JoystickButton m_extend = new JoystickButton(m_controller, 4);
  public static JoystickButton m_retract = new JoystickButton(m_controller, 5);
  public static JoystickButton m_upPivot = new JoystickButton(m_controller, 8);
  public static JoystickButton m_dwnPivot = new JoystickButton(m_controller, 9);
  //Subsystems
  public ArmSubsystem m_ArmSub = new ArmSubsystem();
  // Create SmartDashboard chooser for autonomous routines
  SendableChooser<Command> m_chooser = new SendableChooser<>();

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
    // Configure the button bindings
    configureButtonBindings();
    m_ArmSub.setDefaultCommand(getArmCommand(2));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    m_emerStop.onTrue(getArmCommand(2));
    m_swap.onTrue(getArmCommand(3));
    m_swap.onFalse(getArmCommand(3));
    // m_swap.toggleOnFalse(getArmCommand(3));
    m_extend.onTrue(getArmCommand(4));
    m_retract.onTrue(getArmCommand(5));
    m_upPivot.onTrue(getArmCommand(8));
    m_dwnPivot.onTrue(getArmCommand(9));
    // m_lower.toggleOnFalse(getArmCommand(5));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }

  public Command getResetCommand(int opt){
    return null;//new ArmCommand(m_ArmSub);
  }

  public Command getArmCommand(int opt){
    return new ArmCommand(m_ArmSub, m_controller, opt);
  }

  /**
   * Use this to pass the teleop command to the main {@link Robot} class.
   *
   * @return the command to run in teleop
   */
}
