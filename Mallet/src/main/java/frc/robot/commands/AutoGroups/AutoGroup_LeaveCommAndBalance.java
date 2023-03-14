package frc.robot.commands.AutoGroups;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroScope;
// import frc.robot.commands.ResetEncoders;
import frc.robot.commands.AutoBalance;
//import frc.robot.commands.MoveDistance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class AutoGroup_LeaveCommAndBalance extends SequentialCommandGroup {
    //Variables
    public AutoGroup_LeaveCommAndBalance(Drivetrain drivetrain, GyroScope gyro){
        //Adding a drivetrain
        //Adding Order of commands
        double pitchAngleDegrees = gyro.getAngleY();

        drivetrain.resetEncoders();
        drivetrain.arcadeDrive(Constants.K_FWD_SPEED, 0);

        if (Math.abs(pitchAngleDegrees) == 0) //Math.abs(pitchAngleDegrees) > 0 && Math.abs(pitchAngleDegrees) < 1
            new AutoBalance(drivetrain,gyro,true);


        // addCommands(
        //     new ResetEncoders(drivetrain),
            
        //     new MoveDistance(drivetrain, 18, false),
        //     // balance
        //     new AutoBalance(drivetrain, gyro, true)

        // );
    }
}