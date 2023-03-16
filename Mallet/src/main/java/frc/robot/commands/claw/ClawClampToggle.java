// package frc.robot.commands.claw;

// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.subsystems.ClawSub;


// public class ClawClampToggle extends CommandBase{
//     // Required Subsystems
//     private ClawSub m_claw;
//     private boolean done;

//     // Creation Function of the Class
//     public ClawClampToggle(ClawSub claw){
//         m_claw = claw;
//         addRequirements(m_claw);
//     }

//     // Called when the command is initially scheduled.
//     @Override
//     public void initialize() {
//         done = false;
//     }

//     // Called every time the scheduler runs while the command is scheduled.
//     // need to make target types work
//     // Clamps until certain amount of pressure (measured by amount current)
//     @Override
//     public void execute() {
//         if (m_claw.isOpen()) {
//             done = m_claw.clamp();
//         }
//         else {
//             m_claw.openClaw();
//             done = true;
//         }
//     }

//     // Called once the command ends or is interrupted.
//     // Nothing is called here as it is covered already in the subsystem to stop the motor.
//     @Override
//     public void end(boolean interrupted) {}

//     // Returns true when the command should end.
//     @Override
//     public boolean isFinished() {
//         return done; 
//     }
// }
