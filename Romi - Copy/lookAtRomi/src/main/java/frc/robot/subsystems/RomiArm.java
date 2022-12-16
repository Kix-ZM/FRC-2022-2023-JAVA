// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class RomiArm extends SubsystemBase {

  private final Servo raise_arm = new Servo(Constants.RAISE_ID);
  private final Servo pivot_arm = new Servo(Constants.PIVOT_ID);
  private final Servo grab_arm = new Servo(Constants.GRAB_ID);
  private double raise_pos;
  private double pivot_pos;
  private double grab_pos;


  /** Creates a new RomiArm. */
  public RomiArm() {
    raise_arm.setBounds(1900, 1700, 1450, 1200, 1000);
    pivot_arm.setBounds(1900, 1700, 1450, 1200, 1200);
    grab_arm.setBounds(2400, 1700, 1450, 1200, 500);


    
    reset();
  }

  public void reset() {
    raise_pos = 0.25;
    pivot_pos = 0.25;
    grab_pos = 0.25;

    raise_arm.set(raise_pos);
    pivot_arm.set(raise_pos);
    grab_arm.set(1);
  }

  public void incrementArm(double delta) {
    /* Spec: https://www.pololu.com/docs/0J76/4
     * Range should be 1000 (raised) - 1900 (lowered) us 
     */
    raise_pos = saturateLimit(raise_pos + delta, 0 ,.45); 
    raise_arm.set(raise_pos);
  }

  public double get_armPos() {
    return raise_pos;
  }

  public double saturateLimit(double val, double l_limit, double u_limit) {
    double outval = val;
    if(val > u_limit) {
      outval =  u_limit;
    } else if (val < l_limit) {
      outval = l_limit;
    }
    return outval;
  }

  public double getArmAngle(){
    return raise_arm.getAngle();
  }
  
public void set_raise_armPos(double pos)
{
  raise_pos = pos;
  raise_arm.set(pos);
}

public void set_pivot_armPos(double pos)
{
  pivot_pos = pos;
  pivot_arm.set(pos);
}

public void set_grab_armPos(double pos)
{
  grab_pos = pos;
  grab_arm.set(pos);
}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}