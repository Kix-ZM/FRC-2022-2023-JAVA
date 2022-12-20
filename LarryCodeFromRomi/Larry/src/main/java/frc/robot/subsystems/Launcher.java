package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;

//import edu.wpi.first.wpilibj.BuiltInAccelerometer;
//import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import frc.robot.sensors.RomiGyro;
//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
// import edu.wpi.first.wpilibj.motorcontrol.Spark;
//import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Launcher {
    private final CANSparkMax launcher = new CANSparkMax(5, MotorType.kBrushed);
    
      
    // Set up the differential drive controller

    public Launcher() {
        // We need to invert one side of the drivetrain so that positive voltages
        // result in both sides moving forward. Depending on how your robot's
        // gearbox is constructed, you might have to invert the left side instead.
        launcher.setInverted(true);
        launcher.set(0);
    }

    public void stop(){
        launcher.set(0);
    }

    public void launch() {
        launcher.set(Constants.SPEED);
    }

    public void end(boolean interrupted) {
        launcher.set(0);
    }

    public void periodic() {

    }
}

