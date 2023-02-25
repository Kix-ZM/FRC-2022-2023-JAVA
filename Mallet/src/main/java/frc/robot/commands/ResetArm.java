package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ResetArm extends CommandBase{
    //Comment : MIGHT BE A BIT BUGGY, CHECK WHEN RUNNNING TO SEE IF IT GOES CORRECT DIRECTION
    private ArmSubsystem m_armSubsystem;
    private boolean status;
    private int clawPhase;
    private int pivotPhase;
    private int extensionPhase;
    private boolean readyPositionStatus;

    private double clawEndPos;
    private double pivotEndPos = 10.5;
    private double errorRange = 0.0;
    public ResetArm(ArmSubsystem armSubsystem, boolean isHoldingCube){
        m_armSubsystem = armSubsystem;
        status = false;
        clawPhase = 0;
        pivotPhase = 0;
        extensionPhase = 0;
        readyPositionStatus = false;
        if(isHoldingCube){
            clawEndPos = Constants.encoder_cube;
        }else{
            clawEndPos = Constants.encoder_cone;
        }
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(!readyPositionStatus){
        resetPhase();
    }else{
        readyConfigPhase();
    }
  }

  //Sets the motors to the right angle
  public void readyConfigPhase(){
    if(clawPhase == 1){
        if(m_armSubsystem.getClawEncoder()>clawEndPos){
            m_armSubsystem.moveClaw(-0.2);
        }else if(m_armSubsystem.getClawEncoder()<clawEndPos){
            m_armSubsystem.moveClaw(0.2);
        }else{
            pivotPhase = 2;
        } 
    }
    if(pivotPhase == 1&&clawPhase != 1){
        if(m_armSubsystem.getPivotEncoder()>pivotEndPos){
            m_armSubsystem.pivotArm(-0.2);
        }else if(m_armSubsystem.getPivotEncoder()<pivotEndPos){
            m_armSubsystem.pivotArm(0.2);
        }else{
            pivotPhase = 2;
        }
    }
    if(clawPhase ==2 && pivotPhase ==2){
        status = true;
    }
  }

  public void resetPhase(){
    if(extensionPhase==0){
        if(!m_armSubsystem.isExtensionAtLimit()){
            m_armSubsystem.extendArm(-0.2);
        }else{
            m_armSubsystem.stopExtension();
            extensionPhase = 1;
        }
    }
    if(pivotPhase==0){
        if(!m_armSubsystem.isPivotAtLimit()){
            m_armSubsystem.pivotArm(-0.2);
        }else{
            m_armSubsystem.stopPivot();
            pivotPhase = 1;
        }
    }
    if(clawPhase == 0){
        if(!m_armSubsystem.isClawAtLimit()){
            m_armSubsystem.retractClaw();
        }else{
            m_armSubsystem.stopClaw();
            clawPhase = 1;
        }
    }
    if(extensionPhase==0 && extensionPhase==0 && clawPhase==0 && !readyPositionStatus){
        m_armSubsystem.resetAllEncoders();
        readyPositionStatus = true;
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
