package frc.robot;

public final class Constants {
    public static final double K_SPEED = 0.5; //speed for turning
    public static final double K_WHEEL_DIAMETER_INCH = 6; //diameter of the wheels in inches
    public static final double K_TICKS_PER_FEET = 40.964489;

    /*----------
        AUTO
    -----------*/
    public static final double K_FORWARDS_FEET = 9 * K_TICKS_PER_FEET; //17.666666
    public static final double K_BACKWARDS_FEET = 6 * K_TICKS_PER_FEET; //6.572500
    public static final float K_TURN_ERROR_RANGE = 2f;
    public static final double K_DEC_TO_PI = 0.01745;

    //Turn By
    public static final double K_MIN_TURNING_SPEED = 0.5; //minimum speed to turn at
    public static final double K_MAX_TURNING_SPEED = 0.5; //maximum speed to turn at

    //Balancing
    public static final double K_PLAT_DEGREE_THRESH = 10; //angle at which the robot is considered to be on the platform
    public static final double K_BALANCE_THRESH_DEG = 4.2; //angle at within which the robot is considered to be balanced
    public static final double K_FWD_SPEED = 0.3575; //starting speed towards platform
    public static final double K_ADJUST_SPEED = 0.26;  //speed to adjust angle or brake
    public static final double K_ADJUST_ROTATE = 0.1; //ask brent what this does

    // Vision constants 
    public static final double K_LIMELIGHT_MOUNT_ANG_DEG = 25.0; // LIMELIGHT ANGLE FROM VERTICAL! NOT ANGLE OF ATTACK!
    public static final double K_LIMELIGHT_LENS_HEIGHT_INCH = 20.0; // HEIGHT FROM FLOOR OF LIMELIGHT
}
