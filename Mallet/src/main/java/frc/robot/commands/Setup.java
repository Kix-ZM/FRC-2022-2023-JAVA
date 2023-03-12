package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.GyroScope;

public class Setup extends CommandBase{
    private GyroScope m_gyro;
    private boolean status;
    
    public Setup(GyroScope gyro){
        m_gyro = gyro;
        status = false;
    }
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
  
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if(!m_gyro.isCalibrating()){
            status = true;
        }
    }
  
  
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return status;
    }
}
