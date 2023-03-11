// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final double SPEED = 0.25;                  // Speed for the Drive Train, not used here in this branch currently
    public static final double kCountsPerRevolution = 4096.0; // Used in Auto to determine movement of drive train, not used in this branch
    public static final double kWheelDiameterInch = 6;        // 70 mm
    public static final double tSpeed = 0.20;                 // Used in Drive Train for unique Process, not currently used in this branch
    public static final double minSpeed = 0.5;                // Minimum Speed in terms of Voltage
    public static final double maxSpeed = 5;                  // Maximum Speed in terms of Voltage

    //Which subsystems to use
    public static final boolean isUsingClaw = true;    //If is using the claw subsystem
    public static final boolean isUsingPivot = true;   //If is using the pivot subsystem
    public static final boolean isUsingExt = true;     //If is using the extension subsystem
}
