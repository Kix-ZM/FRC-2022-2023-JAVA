package frc.robot.subsystems;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Limelight subsystem
 */

public class Limelight extends SubsystemBase {
  NetworkTableInstance inst;

  NetworkTableEntry tv;
  NetworkTableEntry tx;
  NetworkTableEntry ty;
  NetworkTableEntry ta;
  NetworkTableEntry pipelineIndex;

  NetworkTable table;
  NetworkTable tabLime;

  // target types for easier use
  private enum targetType {
    POLE,
    CUBE,
    CONE,
    CONE_AND_CUBE,
    APRILTAG,
    NONE
  }

  private targetType targetFound = targetType.NONE;

  public Limelight() {

    inst = NetworkTableInstance.getDefault();
    table = inst.getTable("limelight");

    // is there a target? (0 or 1)
    tv = table.getEntry("tv");
    // target x offset (-27 to 27 degrees)
    tx = table.getEntry("tx");
    // target y offset (-20.5 to 20.5 degrees)
    ty = table.getEntry("ty");
    // target area (0 to 100%)
    ta = table.getEntry("ta");
    // pipeline data data entry
    pipelineIndex = table.getEntry("pipeline");
  }

  // get x angle offset in degrees
  public double getXOffset(){
    return tx.getDouble(0.0);
  }

  // get y angle offset in degrees
  public double getYOffset(){
    return ty.getDouble(0.0);
  }

  // is there a target?
  public boolean isTarget(){
    return tv.getDouble(0.0) == 0 ? false : true;
    
  }

  // get target area in percent
  public double getArea(){
    return ta.getDouble(0.0);
  }

  // return distance to target in inches(ASK SEAMUS)
  public double getDistance(double param){
    double angleToGoalDegrees = Constants.kLimelightMountAngleDegrees + getYOffset();
    double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);
    return (param - Constants.kLimelightLensHeightInches)/Math.tan(angleToGoalRadians);
  }

/*  index 0-9 ( MUST VERIFY )
 *  0 : retroreflective
 *  1 : cone and cube
 *  2 : cone  (NEED TO ADD)
 *  3 : cube
 *  4 : april tags
 */
  public void setPipeline(double index) {
    pipelineIndex.setDouble(index);
  }

  // sets pipeline just based on target type name
  public void setTargetPipeline (targetType type) {
    switch (type) {
      case POLE:
        setPipeline(0);
        break;
      case CONE_AND_CUBE:
        setPipeline(1);
        break;
      case CONE:
        setPipeline(2);
        break;
      case CUBE:
        setPipeline(3);
        break;
      case APRILTAG:
        setPipeline(4);
        break;
      case NONE:
        break;
    }
  }

  // returns true if target present and area is over the threshold
  public boolean validTargetFound(){
    return isTarget() && getArea() > 3.0;
  }

  // in progress (may or may not remove)
  public void determineTargetType() {
    setTargetPipeline(targetType.CONE);
    double coneArea = getArea();
    setTargetPipeline(targetType.CUBE);
    double cubeArea = getArea();

    if (coneArea > 3 && coneArea > cubeArea) {
      
      targetFound = targetType.CONE;
    } else if (cubeArea > 3 && cubeArea > coneArea) {
      targetFound = targetType.CUBE;
    } else {
      targetFound = targetType.NONE;
    }
  }
  
  @Override
  public void periodic() {
    SmartDashboard.putBoolean("LimelightTarget", isTarget());
    SmartDashboard.putNumber("LimelightXOffset", getXOffset());
    SmartDashboard.putNumber("LimelightYOffset", getYOffset());
  }
}