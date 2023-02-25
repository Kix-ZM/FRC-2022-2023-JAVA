package frc.robot.subsystems;

// import edu.wpi.first.cameraserver.CameraServer;

// import edu.wpi.first.cscore.CvSink;
// import edu.wpi.first.cscore.CvSource;

// import edu.wpi.first.cscore.UsbCamera;
// import edu.wpi.first.cscore.VideoSink;

// import edu.wpi.first.cscore.VideoSource.ConnectionStrategy;

import edu.wpi.first.networktables.*;
import edu.wpi.first.networktables.IntegerSubscriber;
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

public class CameraSub extends SubsystemBase {
  
  IntegerSubscriber xSub;
  IntegerSubscriber ySub;


  // Thread m_visionThread;
  // public UsbCamera camera_ori = CameraServer.startAutomaticCapture(0);
  // public UsbCamera camera_new = CameraServer.startAutomaticCapture(1);
  // public VideoSink server = CameraServer.getServer();
  

  public CameraSub(){

    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("vision");

    xSub = table.getIntegerTopic("target_x").subscribe(-1);
    ySub = table.getIntegerTopic("target_y").subscribe(-1);
    
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
  public int getXPos(){
    return (int)(xSub.get());
  }

  public int getYPos(){
    return (int)(ySub.get());
  }

  public int getXCheckAllign(){
    long temp = xSub.get();
    if(temp>Constants.xMAX){return 1;}
    else if(temp<Constants.xMIN){return -1;}
    else return 0;
  }

  public int getYCheckAllign(){
    long temp = ySub.get();
    if(temp>Constants.yMAX){return 1;}
    else if(temp<Constants.yMIN){return -1;}
    else return 0;
  }
  
  @Override
  public void periodic() {
    System.out.print("X: "+xSub.get()+", Y: "+ySub.get());
    System.out.println(", xAllign: "+getXCheckAllign()+", yAllign: "+getYCheckAllign());
  }
}
