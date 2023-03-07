package frc.robot;

public final class Constants {
    public static final double K_SPEED = 0.25; //speed for turning
    public static final double K_WHEEL_DIAMETER_INCH = 6; //diameter of the wheels in inches
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
    public static final double startingSpeedMax = 0.5;
    // speed to adjust balance
    public static final double adjustSpeedMax = 0.3;
    // speed to adjust angle in case of slipping
    public static final double adjustRotateMax = 0.1;
}
