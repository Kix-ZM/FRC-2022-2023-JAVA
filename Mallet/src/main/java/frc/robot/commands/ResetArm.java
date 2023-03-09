package frc.robot.commands;
import frc.robot.Constants;
// import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;
// import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ResetArm extends CommandBase{
    //Comment : MIGHT BE A BIT BUGGY, CHECK WHEN RUNNNING TO SEE IF IT GOES CORRECT DIRECTION
    private ArmSubsystem m_armSubsystem;
    private boolean status;
    //Tell whether the claw is in use or not
    private boolean isClawMoving;
    private boolean isPivotMoving;
    private boolean isExtensionMoving;
    //A boolean to tell whether the arm is currently resetting it's encoders or getting into position
    private boolean isResetting;

    //The values on what the starting position for the arm should be
    private double clawStartPos;
    private double pivotStartPos = Constants.pivotStartPos;

    public ResetArm(ArmSubsystem armSubsystem, boolean isHoldingCube){
        m_armSubsystem = armSubsystem;
        status = false;
        isResetting = true;

        isClawMoving = true;
        isPivotMoving = true;
        isExtensionMoving = true;
        
        if(isHoldingCube){
            clawStartPos = Constants.encoder_cube;
        }else{
            clawStartPos = Constants.encoder_cone;
        }
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(isResetting){
        resetPhase();
    }else{
        readyConfigPhase();
    }
  }

  //Sets the motors to the right angle by first grabbing the game piece and then pivoting
  public void readyConfigPhase(){
    if(isClawMoving){
        //Makes the claw grab the game piece
        if(m_armSubsystem.getClawEncoder()>clawStartPos){
            m_armSubsystem.grabClaw(0.2);
        }else if(m_armSubsystem.getClawEncoder()<clawStartPos){
            m_armSubsystem.retractClaw();
        }else{
            isClawMoving = false;
        } 
    }
    if(isPivotMoving && !isClawMoving){
        //Pivots the pivot to the correct angle
        if(m_armSubsystem.getPivotEncoder()>pivotStartPos){
            m_armSubsystem.pivotArm(-0.2);
        }else if(m_armSubsystem.getPivotEncoder()<pivotStartPos){
            m_armSubsystem.pivotArm(0.2);
        }else{
            isPivotMoving = false;
        }
    }
    if(!isPivotMoving && !isClawMoving){
        status = true;
    }
  }

  //Gets the arm pieces to their limit and then set those as the limit, claw should go last because it might be holding something
  public void resetPhase(){
    if(isExtensionMoving){
        //Makes sure encoder value 0 is the max the extension can retract
        if(!m_armSubsystem.isExtensionAtLimitB()){
            m_armSubsystem.extendArm(-0.2);
        }else{
            m_armSubsystem.stopExtension();
            isExtensionMoving = false;
        }
    }
    if(isPivotMoving){
        //Makes sure encoder value 0 is the max the pivot can pivot
        if(!m_armSubsystem.isPivotAtLimit()){
            m_armSubsystem.pivotArm(-0.2);
        }else{
            m_armSubsystem.stopPivot();
            isPivotMoving = false;
        }
    }
    //Ensures all the other arm compoenents are done
    if(isClawMoving && !isExtensionMoving && !isPivotMoving){
        //Makes sure the encoder value 0 is the max the claw can open
        if(!m_armSubsystem.isClawAtLimit()){
            m_armSubsystem.retractClaw();
        }else{
            m_armSubsystem.stopClaw();
            isClawMoving = false;
        }
    }
    //Logic to move onto the next step : moving into ready position
    if(!isClawMoving && !isExtensionMoving && isPivotMoving){
        m_armSubsystem.resetAllEncoders();
        isResetting = false;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_armSubsystem.stopClaw();
    m_armSubsystem.stopExtension();
    m_armSubsystem.stopPivot();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return status;
  }
}
