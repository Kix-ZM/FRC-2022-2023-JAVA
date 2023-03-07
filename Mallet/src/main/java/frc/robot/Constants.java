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
    public static final double ticksPerMeter = 133.285034;
    public static final double ticksPerFeet = 40.964489;
    public static final double forward_feet = 17 * ticksPerFeet;//17.666666
    public static final double backward_feet = 6 * ticksPerFeet;//6.572500
    
    
    public static final long xMIN = 70;
    public static final long xMAX = 90;
    public static final long yMIN = 130;
    public static final long yMAX = 170;

    // Balancing constants
    //angle at which the robot is considered to be on the platform
    public static final double onPlatThreshDeg = 8;
    //angle at within which the robot is considered to be balanced
    public static final double OnBalanceThreshDeg = 2;
    // starting speed towards platform
    public static final double startingSpeedMax = 0.3;
    // speed to adjust balance
    public static final double adjustSpeedMax = 0.3;
    // speed to adjust angle in case of slipping
    public static final double adjustRotateMax = 0.1;
}
