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
    public static final double SPEED = 0.25;
    public static final double kCountsPerRevolution = 4096.0;
    public static final double kWheelDiameterInch = 6; // 70 mm
    public static final double tSpeed = 0.20;
    public static final double minSpeed = 0.5; // In terms of Voltage
    public static final double maxSpeed = 5; // In terms of Voltage

    public static final boolean isUsingPivot = true;
    public static final boolean isUsingExt = false;
    public static final boolean isUsingClaw = false;

}
