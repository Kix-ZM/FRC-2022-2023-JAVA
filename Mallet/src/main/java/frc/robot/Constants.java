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
    public static final long xMIN = 70;
    public static final long xMAX = 90;
    public static final long yMIN = 130;
    public static final long yMAX = 170;
    public static final double degLimit = 2.5;
    public static final double kLimelightMountAngleDegrees = 25.0; // LIMELIGHT ANGLE FROM VERTACLE! NOT ANGLE OF ATTACK!
    public static final double kLimelightLensHeightInches = 20.0; // HEIGHT FROM FLOOR OF LIMELIGHT
    public static final double kGoalHeightInches = 60.0;
    public static final double kTapeTop = 41.125; // From floor to base of the tape, this is the higher one
    public static final double kTapeBtm = 22.125; // From floor to base of the tape, this is the lower one
    public static final double kAprilTagTop = 24.375; // Higher AprilTag, measured from floor to base of black squares
    public static final double kAprilTagBtm = 15.25;  // Lower AprilTag, measured from floor to base of black squares
    public static final double onFloor = 0; // Height of the Cube and Cone initially.
}

/*
Tape
Top - 41.125
Btm - 22.125

AprilTag
Top - 24.375
Btm - 15.25

Cone
floor - 0

Cube
floor - 0
 */