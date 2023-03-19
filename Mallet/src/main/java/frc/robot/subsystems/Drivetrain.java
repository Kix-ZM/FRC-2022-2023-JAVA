package frc.robot.subsystems;

import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  private final CANSparkMax m_flMotor = new CANSparkMax(3, MotorType.kBrushless);
  private final CANSparkMax m_blMotor = new CANSparkMax(4, MotorType.kBrushless);
  MotorControllerGroup m_left = new MotorControllerGroup(m_flMotor, m_blMotor);
  private final CANSparkMax m_frMotor = new CANSparkMax(1, MotorType.kBrushless);
  private final CANSparkMax m_brMotor = new CANSparkMax(2, MotorType.kBrushless);
  MotorControllerGroup m_right = new MotorControllerGroup(m_frMotor, m_brMotor);

  private final RelativeEncoder m_leftEncoder = m_flMotor.getEncoder();
  private final RelativeEncoder m_rightEncoder = m_frMotor.getEncoder();
  private final RelativeEncoder m_leftBackEncoder = m_blMotor.getEncoder();
  private final RelativeEncoder m_rightBackEncoder = m_brMotor.getEncoder();

  // Set up the differential drive controller
  private final DifferentialDrive m_diffDrive = new DifferentialDrive(m_left, m_right);
 // private final DifferentialDrive m_diffDrive = new DifferentialDrive(m_brMotor, m_frMotor);

  // Set up the BuiltInAccelerometer
  private final BuiltInAccelerometer m_accelerometer = new BuiltInAccelerometer();

  private double balancePosition;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    m_flMotor.setIdleMode(IdleMode.kBrake);
    m_blMotor.setIdleMode(IdleMode.kBrake);
    m_frMotor.setIdleMode(IdleMode.kBrake);
    m_brMotor.setIdleMode(IdleMode.kBrake);
    // We need to invert one side of the drivetrain so that positive voltages result in both sides moving forward. Depending on how your robot's gearbox is constructed, you might have to invert the left side instead.
    m_brMotor.setInverted(true);
    m_frMotor.setInverted(true);
    m_leftEncoder.setPositionConversionFactor(Constants.K_WHEEL_DIAMETER_INCH);
    m_rightEncoder.setPositionConversionFactor(Constants.K_WHEEL_DIAMETER_INCH);
    m_leftBackEncoder.setPositionConversionFactor(Constants.K_WHEEL_DIAMETER_INCH);
    m_rightBackEncoder.setPositionConversionFactor(Constants.K_WHEEL_DIAMETER_INCH);
    
    resetEncoders();
  }
  //testing single motor
  public CANSparkMax getTestMotor() {
    return m_brMotor;
  }

  // negative is forward or to the right
  public void arcadeDrive(double xaxisSpeed, double zaxisRotate) {
    m_diffDrive.arcadeDrive(xaxisSpeed, .5*zaxisRotate);
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

  public void setBalanceToCurrentPos(boolean backwards) {
    if (!backwards)
      balancePosition = getAverageDistanceInch()-42.5;
    else 
      balancePosition = getAverageDistanceInch()+42.5;
  }

  public void balance() {
    double speed = (balancePosition - getAverageDistanceInch())/4;
    if (Math.abs(speed) > .45) {
      speed = speed > 0 ? .45 : -.45;
    }
    arcadeDrive(speed, 0);
  }

  // For when the time calls for it, run this
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
