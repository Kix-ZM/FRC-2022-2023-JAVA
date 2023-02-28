// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
// import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import frc.robot.sensors.RomiGyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
// import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  

  // The Romi has the left and right motors set to
  // PWM channels 0 and 1 respectively
  // private final Spark m_leftMotor = new Spark(0);
  //private final Spark m_rightMotor = new Spark(1);
// 1 motor claw, 1 motor extnsion, 2 motors pivot
  private final CANSparkMax m_flMotor;
  private final CANSparkMax m_blMotor;
  MotorControllerGroup m_left;
  
  private final CANSparkMax m_frMotor;
  private final CANSparkMax m_brMotor;
  MotorControllerGroup m_right;
  
  // The Romi has onboard encoders that are hardcoded
  // to use DIO pins 4/5 and 6/7 for the left and right
  private final RelativeEncoder m_leftEncoder;
  private final RelativeEncoder m_rightEncoder;
  private final RelativeEncoder m_leftBackEncoder;
  private final RelativeEncoder m_rightBackEncoder;

  // Set up the differential drive controller
  private final DifferentialDrive m_diffDrive = new DifferentialDrive(m_left, m_right);

  private final boolean isUsingFLMotor = true;
  private final boolean isUsingFRMotor = true;
  private final boolean isUsingBLMotor = true;
  private final boolean isUsingBRMotor = true;
 // private final DifferentialDrive m_diffDrive = new DifferentialDrive(m_brMotor, m_frMotor);
  


  // Set up the RomiGyro
  // private final RomiGyro m_gyro = new RomiGyro();

  // Set up the BuiltInAccelerometer
  private final BuiltInAccelerometer m_accelerometer = new BuiltInAccelerometer();

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    //Setup for motors and encoders
    if(isUsingBLMotor){
      m_blMotor = new CANSparkMax(4, MotorType.kBrushless);
      m_leftBackEncoder = m_blMotor.getEncoder();
    } 
    if(isUsingFLMotor){
      m_flMotor = new CANSparkMax(3, MotorType.kBrushless);
      m_leftEncoder = m_flMotor.getEncoder();
    }
    if(isUsingBRMotor){
      m_brMotor = new CANSparkMax(2, MotorType.kBrushless);
      m_rightBackEncoder = m_brMotor.getEncoder();
    } 
    if(isUsingFRMotor){
      m_frMotor = new CANSparkMax(1, MotorType.kBrushless);
      m_rightEncoder = m_frMotor.getEncoder();
    }

    //Setup for motor groups
    if(isUsingBLMotor){
      if(isUsingFLMotor){
        m_left = new MotorControllerGroup(m_flMotor, m_blMotor);
      }else{
        m_left = new MotorControllerGroup(m_blMotor);
      }
    }else if(isUsingFLMotor){
      m_left = new MotorControllerGroup(m_flMotor);
    }

    if(isUsingBRMotor){
      if(isUsingFRMotor){
        m_right = new MotorControllerGroup(m_frMotor, m_brMotor);
      }else{
        m_right = new MotorControllerGroup(m_brMotor);
      }
    }else if(isUsingFRMotor){
      m_right = new MotorControllerGroup(m_frMotor);
    }

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_brMotor.setInverted(true);
    m_frMotor.setInverted(true);

    // Use inches as unit for encoder distances
    // m_leftEncoder.setDistancePerPulse((Math.PI * kWheelDiameterInch) / kCountsPerRevolution);
    // m_rightEncoder.setDistancePerPulse((Math.PI * kWheelDiameterInch) / kCountsPerRevolution);
    m_leftEncoder.setPositionConversionFactor(Constants.kWheelDiameterInch);
    m_rightEncoder.setPositionConversionFactor(Constants.kWheelDiameterInch);
    m_leftBackEncoder.setPositionConversionFactor(Constants.kWheelDiameterInch);
    m_rightBackEncoder.setPositionConversionFactor(Constants.kWheelDiameterInch);
    
    resetEncoders();
  }

  //testing single motor
  public CANSparkMax getTestMotor() {
    return m_brMotor;
  }

  public void arcadeDrive(double xaxisSpeed, double zaxisRotate) {
    if(xaxisSpeed > Constants.maxSpeed){
      xaxisSpeed = Constants.maxSpeed;
    }
    m_diffDrive.arcadeDrive(xaxisSpeed, zaxisRotate);
  }

  public void resetEncoders() {
    m_leftEncoder.setPosition(0);
    m_rightEncoder.setPosition(0);
    m_leftBackEncoder.setPosition(0);
    m_rightBackEncoder.setPosition(0);
  }

  public void runTest(double speed){
    if(speed > Constants.maxSpeed){
      speed = Constants.maxSpeed;
    }
    m_brMotor.set(speed);
  }
  // FOr when the time calls for it, run this
  public void stopMotors(){
    m_diffDrive.arcadeDrive(0, 0);
  }

  public RelativeEncoder getLeftEncoder() {
    if(isUsingFLMotor){
      return m_leftEncoder;
    }
    return null;
  }

  public RelativeEncoder getRightEncoder() {
    if(isUsingFRMotor){
      return m_rightEncoder;
    }
    return null;
  }

  public RelativeEncoder getRightBackEncoder(){
    if(isUsingBRMotor){
      return m_rightBackEncoder;
    }
    return null;
  }

  public RelativeEncoder getLeftBackEncoder(){
    if(isUsingBLMotor){
      return m_leftBackEncoder;
    }
    return null;
  }

  public double getLeftDistanceInch() {
    if(isUsingFLMotor){
      return m_leftEncoder.getPosition();
    }
    return 0.0;
  }

  public double getLeftBackDistanceInch(){
    if(isUsingBLMotor){
      return m_leftBackEncoder.getPosition();
    }
    return 0.0;
  }

  public double getRightDistanceInch() {
    if(isUsingFRMotor){
      return m_rightEncoder.getPosition();
    }
    return 0.0;
  }

  public double getRightBackDistanceInch(){
    if(isUsingBRMotor){
      return m_rightBackEncoder.getPosition();
    }
    return 0.0;
  }

  public double getLeftAverageDistanceInch(){
    if(isUsingBLMotor && isUsingBRMotor){
      return ((getLeftDistanceInch()) + (getLeftBackDistanceInch())) / 2.0;
    }
    return getLeftDistanceInch() + getLeftBackDistanceInch();
  }

  public double getRightAverageDistanceInch(){
    if(isUsingBRMotor && isUsingFRMotor){
      return ((getRightDistanceInch()) + (getRightBackDistanceInch())) / 2.0;
    }
    return ((getRightDistanceInch()) + (getRightBackDistanceInch()));
  }

  public double getAverageDistanceInch() {
    return (getLeftAverageDistanceInch() + getRightAverageDistanceInch()) / 2.0;
  }

  /**
   * The acceleration in the X-axis.
   *
   * @return The acceleration of the Romi along the X-axis in Gs
   */
  public double getAccelX() {
    return m_accelerometer.getX();
  }

  /**
   * The acceleration in the Y-axis.
   *
   * @return The acceleration of the Romi along the Y-axis in Gs
   */
  public double getAccelY() {
    return m_accelerometer.getY();
  }

  /**
   * The acceleration in the Z-axis.
   *
   * @return The acceleration of the Romi along the Z-axis in Gs
   */
  public double getAccelZ() {
    return m_accelerometer.getZ();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
