package frc.robot.subsystems;

// import edu.wpi.first.cameraserver.CameraServer;

// import edu.wpi.first.cscore.CvSink;
// import edu.wpi.first.cscore.CvSource;

// import edu.wpi.first.cscore.UsbCamera;
// import edu.wpi.first.cscore.VideoSink;

// import edu.wpi.first.cscore.VideoSource.ConnectionStrategy;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// import frc.robot.RobotContainer;

// import org.opencv.core.Mat;
// import org.opencv.core.Point;
// import org.opencv.core.Scalar;
// import org.opencv.imgproc.Imgproc;
// import org.opencv.video.Video;

/**
 * This is a demo program showing the use of OpenCV to do vision processing. The image is acquired
 * from the USB camera, then a rectangle is put on the image and sent to the dashboard. OpenCV has
 * many methods for different types of processing.
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


  //not sure if we will need this kind of thing
  private enum targetType {
    CUBE,
    CONE,
    NONE
  }
  
  private enum placeType {
    POLE,
    PLATFORM,
    NONE
  }

  private targetType targetFound = targetType.NONE;

  // Thread m_visionThread;
  // public UsbCamera camera_ori = CameraServer.startAutomaticCapture(0);
  // public UsbCamera camera_new = CameraServer.startAutomaticCapture(1);
  // public VideoSink server = CameraServer.getServer();
  
  


  public Limelight(){

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

    // camera_ori.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
    // camera_new.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
    // m_visionThread =
    //     new Thread(
    //         () -> {
    //           // Get the UsbCamera from CameraServer
    //           UsbCamera camera = CameraServer.startAutomaticCapture();
    //           // Set the resolution
    //           camera.setResolution(640, 480);

    //           // Get a CvSink. This will capture Mats from the camera
    //           CvSink cvSink = CameraServer.getVideo();
    //           // Setup a CvSource. This will send images back to the Dashboard
    //           CvSource outputStream = CameraServer.putVideo("Rectangle", 640, 480);

    //           // Mats are very memory expensive. Lets reuse this Mat.
    //           Mat mat = new Mat();

    //           // This cannot be 'true'. The program will never exit if it is. This
    //           // lets the robot stop this thread when restarting robot code or
    //           // deploying.
    //           while (!Thread.interrupted()) {
    //             // Tell the CvSink to grab a frame from the camera and put it
    //             // in the source mat.  If there is an error notify the output.
    //             if (cvSink.grabFrame(mat) == 0) {
    //               // Send the output the error.
    //               outputStream.notifyError(cvSink.getError());
    //               // skip the rest of the current iteration
    //               continue;
    //             }
    //             // Put a rectangle on the image
    //             Imgproc.rectangle(
    //                 mat, new Point(100, 100), new Point(400, 400), new Scalar(255, 255, 255), 5);
    //             // Give the output stream a new image to display
    //             outputStream.putFrame(mat);
    //           }
    //         });
    // m_visionThread.setDaemon(true);
    
  }

  // WARNING, there is no acceptable way to easily stop the thread
  // There is no stop() nor suspend() but you can pause()
  // public void startUp(){
  //   m_visionThread.start();
  // }

  // public void changeCamera(){
  //   if(RobotContainer.m_controller.getTrigger())
  //     server.setSource(camera_ori);
  //   else
  //     server.setSource(camera_new);
  // }
  public double getXOffset(){
    return tx.getDouble(0.0);
  }

  public double getYOffset(){
    return ty.getDouble(0.0);
  }

  public boolean isTarget(){
    return tv.getDouble(0.0) == 0 ? false : true;
    
  }

  public double getArea(){
    return ta.getDouble(0.0);
  }

// IMPLEMENT THIS IN A COMMAND LATER FOR MORE PRECISE CONTROL
// float Kp = -0.1f;  // Proportional control constant
//
// std::shared_ptr<NetworkTable> table = NetworkTable::GetTable("limelight");
// float tx = table->GetNumber("tx");
//
// if (joystick->GetRawButton(9))
// {
//         float heading_error = tx;
//         steering_adjust = Kp * tx;
//
//         left_command+=steering_adjust;
//         right_command-=steering_adjust;
// }

  public int getXCheckAlign(){
    double temp = getXOffset();
    if(temp>Constants.xMAX){return 1;}
    else if(temp<Constants.xMIN){return -1;}
    else return 0;
  }

  public int getYCheckAlign(){
    double temp = getYOffset();
    if(temp>Constants.yMAX){return 1;}
    else if(temp<Constants.yMIN){return -1;}
    else return 0;
  }

  public double getDistance(double param){
    double angleToGoalDegrees = Constants.kLimelightMountAngleDegrees + getYOffset();
    double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);
    return (param - Constants.kLimelightLensHeightInches)/Math.tan(angleToGoalRadians);
  }

/*  index 0-9
 *  1 : cone
 *  2 : cube
 */
  public void setPipeline(double index) {
    pipelineIndex.setDouble(index);
  }

  public void setTargetPipeline (targetType type) {
    switch (type) {
      case CONE:
        setPipeline(1);
        break;
      case CUBE:
        setPipeline(2);
        break;
      case NONE:
        break;
    }
  }

  // returns true if target present and area is over the threshold
  public boolean validTargetFound(){
    return isTarget() && getArea() > 3.0;
  }

  // in progress
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
    SmartDashboard.putNumber("TapeTopDistance", getDistance(Constants.kTapeTop));
    SmartDashboard.putNumber("TapeBtmDistance", getDistance(Constants.kTapeBtm));
    SmartDashboard.putNumber("AprilTagTopDistance", getDistance(Constants.kAprilTagTop));
    SmartDashboard.putNumber("AprilTagBtmDistance", getDistance(Constants.kAprilTagBtm));
    SmartDashboard.putNumber("LimelightXOffset", getXOffset());
    SmartDashboard.putNumber("LimelightYOffset", getYOffset());
    // System.out.print("X: "+xSub.get()+", Y: "+ySub.get());
    // System.out.println(", xAllign: "+getXCheckAllign()+", yAllign: "+getYCheckAllign());
  }
}
