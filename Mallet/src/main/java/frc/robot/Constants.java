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
    public static final double maxSpeed = 0.8;
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
    public static final double degLimit = 2.5;

    //Motors on the real robot
    public static final boolean isUsingFLMotor = true;
    public static final boolean isUsingFRMotor = true;
    public static final boolean isUsingBLMotor = true;
    public static final boolean isUsingBRMotor = true;
}
