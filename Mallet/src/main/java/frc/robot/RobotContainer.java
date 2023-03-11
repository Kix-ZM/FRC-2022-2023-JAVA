// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
// import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DebugCommand;
import frc.robot.commands.MoveExtenderBackwards;
import frc.robot.commands.MoveExtenderForward;
import frc.robot.commands.MovePivot;
import frc.robot.commands.StopAllMotors;
import frc.robot.subsystems.ExtensionMotor;
import frc.robot.subsystems.PivotMotor;
// import frc.robot.commands.*;
// import frc.robot.subsystems.*;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  public static Joystick m_controller = new Joystick(0);
  public static JoystickButton m_emerStop = new JoystickButton(m_controller, 5);
  public static JoystickButton m_retract = new JoystickButton(m_controller, 2);
  public static JoystickButton m_extend = new JoystickButton(m_controller, 3);
  //Subsystems
  // public TestSub m_testSub = new TestSub();
  public ExtensionMotor m_extensionMotor = new ExtensionMotor();
  public PivotMotor m_pivotMotor = new PivotMotor();
  // Create SmartDashboard chooser for autonomous routines
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    // m_testSub.setDefaultCommand(getTestCommand(1));
    m_pivotMotor.setDefaultCommand(new MovePivot(m_pivotMotor, m_controller));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    m_emerStop.onTrue(new StopAllMotors(m_pivotMotor, m_extensionMotor)); // Stops all arm action
    m_retract.whileTrue(new MoveExtenderBackwards(m_extensionMotor));     // Retracts Arm
    m_extend.whileTrue(new MoveExtenderForward(m_extensionMotor));        // Extends Arm
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }

  public Command getDebugCommand(){
    return new DebugCommand(m_controller);
  }

  // public Command getTestCommand(int opt){
  //   return new TestCommand(m_testSub, m_controller, opt);
  // }

  /**
   * Use this to pass the teleop command to the main {@link Robot} class.
   *
   * @return the command to run in teleop
   */
}
