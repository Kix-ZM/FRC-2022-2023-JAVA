package frc.robot;

public final class Constants {
    public static final double K_SPEED = 0.25; //speed for turning
    public static final double K_WHEEL_DIAMETER_INCH = 6; //diameter of the wheels in inches
    public static final double tSpeed = 0.20;
    public static final double ticksPerMeter = 133.285034;
    public static final double ticksPerFeet = 40.964489;

    
    public static final long xMIN = 70;
    public static final long xMAX = 90;
    public static final long yMIN = 130;
    public static final long yMAX = 170;

    // Autonomous constants
    public static final double forward_feet = 17 * ticksPerFeet;//17.666666
    public static final double backward_feet = 6 * ticksPerFeet;//6.572500
    public static final double ptd = 57.2958; //seamus what are these
    public static final double dtp = 0.01745;


    // Balancing constants
    //angle at which the robot is considered to be on the platform
    public static final double onPlatThreshDeg = 14.3;
    //angle at within which the robot is considered to be balanced
    public static final double OnBalanceThreshDeg = 4.2;
    // starting speed towards platform
    public static final double startingSpeedMax = 0.50;
    // speed to adjust balance
    public static final double adjustSpeedMax = .26;  // speed to adjust angle in case of slipping
    public static final double adjustRotateMax = 0.1;

    // Vision constants
    public static final double kLimelightMountAngleDegrees = 25.0; // LIMELIGHT ANGLE FROM VERTICAL! NOT ANGLE OF ATTACK!
    public static final double kLimelightLensHeightInches = 20.0; // HEIGHT FROM FLOOR OF LIMELIGHT
}
