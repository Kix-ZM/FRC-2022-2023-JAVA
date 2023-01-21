// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.Encoder;
// import frc.robot.sensors.RomiGyro;
// import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
// import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
  

  // The Romi has the left and right motors set to
  // PWM channels 0 and 1 respectively
  // private final Spark m_leftMotor = new Spark(0);
  //private final Spark m_rightMotor = new Spark(1);

  private final CANSparkMax CAMMotor = new CANSparkMax(5, MotorType.kBrushed);
  private final CANSparkMax armMotor = new CANSparkMax(6, MotorType.kBrushed);
  private DigitalInput limit = new DigitalInput(0);

  /** Creates a new Drivetrain. */
  public ArmSubsystem() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    

    // Use inches as unit for encoder distances
    // m_leftEncoder.setDistancePerPulse((Math.PI * kWheelDiameterInch) / kCountsPerRevolution);
    // m_rightEncoder.setDistancePerPulse((Math.PI * kWheelDiameterInch) / kCountsPerRevolution);
    
    
  }

  // turns arm
  public void turnArm(boolean fBtn, boolean bBtn) {
    if(fBtn!=bBtn&&(fBtn||bBtn))
      armMotor.set(Constants.tSpeed);
  }

  // shifts arm up and down
  public void shiftArm(double shiftSpeed, boolean tgrCheck) {
    if(!limit.get()&&tgrCheck){CAMMotor.set(shiftSpeed);}
    else if(limit.get()){CAMMotor.set(Math.abs(shiftSpeed));}
  }

  // FOr when the time calls for it, run this
  public void stopMotors(){
    armMotor.set(0);
    CAMMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
