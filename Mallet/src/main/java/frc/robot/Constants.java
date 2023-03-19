package frc.robot;

public final class Constants {
    public static final double K_SPEED = 0.5; //speed for turning
    public static final double K_WHEEL_DIAMETER_INCH = 6; //diameter of the wheels in inches
    public static final double K_TICKS_PER_FEET = 40.964489;

    /*----------
        AUTO
    -----------*/
    public static final double K_FORWARDS_FEET = 9* K_TICKS_PER_FEET; //17.666666
    public static final double K_BACKWARDS_FEET = 6 * K_TICKS_PER_FEET; //6.572500
    public static final float K_TURN_ERROR_RANGE = 2f;
    public static final double K_DEC_TO_PI = 0.01745;

    //Turn By
    public static final double K_MIN_TURNING_SPEED = 0.2; //minimum speed to turn at
    public static final double K_MAX_TURNING_SPEED = 0.5; //maximum speed to turn at

    //Balancing
    public static final double K_PLAT_DEGREE_THRESH = 10; //angle at which the robot is considered to be on the platform
    public static final double K_BALANCE_THRESH_DEG = 4.2; //angle at within which the robot is considered to be balanced
    public static final double K_FWD_SPEED = 0.47; //starting speed towards platform
    public static final double K_ADJUST_SPEED = .3;  //speed to adjust angle or brake
    public static final double K_ADJUST_ROTATE = 0.1; //ask brent what this does

    // Vision constants 
    public static final double K_LIMELIGHT_MOUNT_ANG_DEG = 25.0; // LIMELIGHT ANGLE FROM VERTICAL! NOT ANGLE OF ATTACK!
    public static final double K_LIMELIGHT_LENS_HEIGHT_INCH = 20.0; // HEIGHT FROM FLOOR OF LIMELIGHT



    public static final class K_ExtSub {
        public static final boolean isUsingExt = true;     //If is using the extension subsystem
        public static final double calibrateStartingAngle = 0;
        public static final double calibrateEndingAngle = 9;
        public static final double calibrateAngleEncoderValue = 90.9325180053;
        public static final double extSpeed = 5;
    }

    public static final class K_PivotSub {
        public static final boolean isUsingPivot = true;
        public static final double calibrateStartingAngle = 6;
        public static final double calibrateEndingAngle = 90;
        public static final double calibrateAngleEncoderValue = 14.75;
        public static final double pivotSpeed = 4.3;
    }   
    
    public static final class K_ClawSub {
        public static final boolean isUsingClaw = true;    //If is using the claw subsystem
        public static final double calibrateStartingAngle = 90;
        public static final double calibrateEndingAngle = 180;
        public static final double calibrateAngleEncoderValue = 9.57146931;
        public static final double clampVoltage = 1.5;
        public static final double coneMaxCurrent = .3;
        public static final double cubeMaxCurrent = .2;
    }
}
