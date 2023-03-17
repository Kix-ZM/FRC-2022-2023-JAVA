// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class GyroScope extends SubsystemBase {

  private AHRS gScope;

    // Creates a new Gyro.
    public GyroScope() {
        gScope = new AHRS(I2C.Port.kMXP);
        gScope.calibrate();
    }

    // returns the current displacement of the bot from initial calibration
    public float getDispX(){return gScope.getDisplacementX();}
    public float getDispY(){return gScope.getDisplacementY();}
    public float getDispZ(){return gScope.getDisplacementZ();}
  
    // returns the current acceleration of the movement
    public float getAccelX(){return gScope.getWorldLinearAccelX();}
    public float getAccelY(){return gScope.getWorldLinearAccelY();}
    public float getAccelZ(){return gScope.getWorldLinearAccelZ();}

    // returns the current velocity of movement
    public float getVelX(){return gScope.getVelocityX();}
    public float getVelY(){return gScope.getVelocityY();}
    public float getVelZ(){return gScope.getVelocityZ();}

    // returns the magnitude of the current vector of movement
    public float getMagX(){return gScope.getRawMagX();}
    public float getMagY(){return gScope.getRawMagY();}
    public float getMagZ(){return gScope.getRawMagZ();}

    // Returns the angles one can use for the rotation and such of the robot
    public float getAngleX(){return gScope.getPitch();}
    public float getAngleY(){return gScope.getRoll();}
    public float getAngleZ(){return gScope.getYaw();}

    //Returns the current status of the gyro
    public boolean isCalibrating(){return gScope.isCalibrating();}
    public boolean isConnected(){return gScope.isConnected();}

    // to be used to basically calibrate the bot
    // WARNING DO NOT TOUCH GYRO FOR THE NEXT 30-60 SECONDS AFTER
    //         RESETTING THE GYRO, THIS IS TO ENSURE THERE IS NO
    //         ISSUES WITH THE ANGLES OR ANYTHING
    public void calibrateGyro(){
        gScope.calibrate();
    }

    // This method will be called once per scheduler run
    @Override
    public void periodic() {
    }
}
