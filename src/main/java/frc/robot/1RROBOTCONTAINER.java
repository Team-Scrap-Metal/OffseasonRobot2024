package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.RobotStateConstants;

public class RobotContainer()  {
    private final CommandXboxController driverController = new CommandXboxController(OperatorConstants.DRIVER_PORT)
    public RobotContainer() {
        switch(RobotStateConstants.getMode()) 
        case REAL:
            break;

        case SIM:
            break;
        
        default:

            break;


    }

    configureButtonBindings();

}
private void configureButtonBindings() {

}

public Command getAutonomousCommand() {

    
    return null;


}