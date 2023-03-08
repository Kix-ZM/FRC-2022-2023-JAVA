package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TestSub;


public class TestCommand extends CommandBase{
    private TestSub m_test;
    private Joystick m_joystick;
    private int m_opt;
    public TestCommand(TestSub test, Joystick joystick, int option){
        m_test = test;
        m_joystick = joystick;
        m_opt = option;
        addRequirements(m_test);
    }
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        System.out.println("opion: "+m_opt);
        switch(m_opt){
            case 1:
                //Moves Motors From Z Dial (The one at the base of the joystick)
                m_test.moveMotors(m_joystick.getRawAxis(2),-1*m_joystick.getRawAxis(2),0);
            break;
            case 2:
                m_test.kill();
            break;
            case 3:
                m_test.changeSetting();
            break;
            case 4:
                m_test.changeMovement(-1);
            break;
            case 5:
                m_test.changeMovement(1);
            break;
            default:
                System.out.println("ERROR SHOULD NOT BE HERE IN TEST COMMAND SWITCH STATEMENT!!!");
            break;
        }    
    }

    public void extending(double delta){
        m_test.changeMovement(delta);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}
