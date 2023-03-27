package frc.robot.subsystems;
import java.util.HashMap;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Limelight subsystem
 */

public class Limelight extends SubsystemBase {
  private NetworkTableInstance inst;
  private NetworkTableEntry tv;
  private NetworkTableEntry tx;
  private NetworkTableEntry ty;
  private NetworkTableEntry ta;
  private NetworkTableEntry pipelineIndex;

  private NetworkTable table;
  private HashMap<String, Integer> pipelineMap;
  private double curIndex;

  public Limelight() {
    // create pipeline map
    pipelineMap = new HashMap<String, Integer>();
    pipelineMap.put("pole", 0);
    pipelineMap.put("coneAndCube", 1);
    pipelineMap.put("cube", 2);
    pipelineMap.put("cone", 3);
    pipelineMap.put("aprilTag", 4);

// initialize network tables
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
    // pipeline index data entry
    pipelineIndex = table.getEntry("pipeline");
    
    //set pipeline to cone and cube
    pipelineIndex.setDouble(5);
    curIndex = 5;
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
    double angleToGoalDegrees = Constants.K_LIMELIGHT_MOUNT_ANG_DEG + getYOffset();
    double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);
    return (param - Constants.K_LIMELIGHT_LENS_HEIGHT_INCH)/Math.tan(angleToGoalRadians);
  }

/*  index 0-9 ( MUST VERIFY )
 *  0 : retroreflective
 *  1 : cone and cube
 *  2 : cube
 *  3 : cone  (NEED TO ADD)
 *  4 : april tags
 */
  public void setPipeline(double index) {
    pipelineIndex.setDouble(index);
    curIndex = index;
  }

  public void nextPipeline() {
    // if (curIndex < pipelineMap.size()) {
    //   setPipeline(curIndex + 1);
    //   curIndex++;
    // } else {
    //   setPipeline(0);
    //   curIndex=0;
    // }
  }

  // returns true if target present and area is over the threshold
  public boolean validTargetFound(){
    return isTarget() && getArea() > 3.0;
  }
  
  @Override
  public void periodic() {
  }
}