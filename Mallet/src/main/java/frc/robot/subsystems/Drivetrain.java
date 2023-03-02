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
  private final CANSparkMax m_flMotor = new CANSparkMax(3, MotorType.kBrushless);
  private final CANSparkMax m_blMotor = new CANSparkMax(4, MotorType.kBrushless);
  MotorControllerGroup m_left = new MotorControllerGroup(m_flMotor, m_blMotor);
  
  private final CANSparkMax m_frMotor = new CANSparkMax(1, MotorType.kBrushless);
  private final CANSparkMax m_brMotor = new CANSparkMax(2, MotorType.kBrushless);
  MotorControllerGroup m_right = new MotorControllerGroup(m_frMotor, m_brMotor);
  
  // The Romi has onboard encoders that are hardcoded
  // to use DIO pins 4/5 and 6/7 for the left and right
  private final RelativeEncoder m_leftEncoder = m_flMotor.getEncoder();
  private final RelativeEncoder m_rightEncoder = m_frMotor.getEncoder();
  private final RelativeEncoder m_leftBackEncoder = m_blMotor.getEncoder();
  private final RelativeEncoder m_rightBackEncoder = m_brMotor.getEncoder();

  // Set up the differential drive controller
  private final DifferentialDrive m_diffDrive = new DifferentialDrive(m_left, m_right);
 // private final DifferentialDrive m_diffDrive = new DifferentialDrive(m_brMotor, m_frMotor);
  


  // Set up the RomiGyro
  // private final RomiGyro m_gyro = new RomiGyro();

  // Set up the BuiltInAccelerometer
  private final BuiltInAccelerometer m_accelerometer = new BuiltInAccelerometer();

  /** Creates a new Drivetrain. */
  public Drivetrain() {
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
    m_diffDrive.arcadeDrive(xaxisSpeed, zaxisRotate);
  }

  public void resetEncoders() {
    m_leftEncoder.setPosition(0);
    m_rightEncoder.setPosition(0);
    m_leftBackEncoder.setPosition(0);
    m_rightBackEncoder.setPosition(0);
  }

  public void runTest(double speed){
    m_brMotor.set(speed);
  }
  // FOr when the time calls for it, run this
  public void stopMotors(){
    m_diffDrive.arcadeDrive(0, 0);
  }

  public RelativeEncoder getLeftEncoder() {
    return m_leftEncoder;
  }

  public RelativeEncoder getRightEncoder() {
    return m_rightEncoder;
  }

  public RelativeEncoder getRightBackEncoder(){
    return m_rightBackEncoder;
  }

  public RelativeEncoder getLeftBackEncoder(){
    return m_leftBackEncoder;
  }

  public double getLeftDistanceInch() {
    return m_leftEncoder.getPosition();
  }

  public double getLeftBackDistanceInch(){
    return m_leftBackEncoder.getPosition();
  }

  public double getRightDistanceInch() {
    return m_rightEncoder.getPosition();
  }

  public double getRightBackDistanceInch(){
    return m_rightBackEncoder.getPosition();
  }

  public double getLeftAverageDistanceInch(){
    return ((getLeftDistanceInch()) + (getLeftBackDistanceInch())) / 2.0;
  }

  public double getRightAverageDistanceInch(){
    return ((getRightDistanceInch()) + (getRightBackDistanceInch())) / 2.0;
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
