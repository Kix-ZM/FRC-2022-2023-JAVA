// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
/**
 * This class represents the onboard IO of the Romi reference robot. This includes the pushbuttons
 * and LEDs.
 *
 * <p>DIO 0 - Button A (input only) DIO 1 - Button B (input) or Green LED (output) DIO 2 - Button C
 * (input) or Red LED (output) DIO 3 - Yellow LED (output only)
 */
public class LControllerButtons extends SubsystemBase {
  private final Joystick m_lcontroller = RobotContainer.m_lcontroller;

  public LControllerButtons() {
  }

  /** Gets true if a button is pressed. */
  public boolean getButtonPressed(int id) {
    return m_lcontroller.getRawButton(id);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
