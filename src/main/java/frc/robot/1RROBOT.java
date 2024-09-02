
package frc.robot;

import frc.robot.Constants.RobotStateConstants;
import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

public class Robot extends LoggedRobot {
    private static final String defaultAuto = "Default"
    private static final String customAuto = "My Auto" 
    private String autoSelected;
    private final LoggedDashboardChooser<String> chooser = new LoggedDashboardChooser<>("Auto") 
}

@Override
public void robotInit() {
    logger.recordMetadata("Project Name", BuildConstants.MAVEN_NAME)
    logger.recordMetadata("Build Date", BuildConstants.BUILD_DATE)
    logger.recordMetadata("GitSHA", BuildConstants.GIT_SHA) 
    logger.recordMetadata("GitDate", BuildConstants.GitDate)
    logger.recordMetadata("GitBranch", BuildConstants.GitBranch)

    switch(BuildConstants.DIRTY){
        case 0:
     logger.recordMetadata("GitDirty", "All Changes Commited")       
     break;
        case 1:

     logger.recordMetadata("GitDirty", "Uncommited Changes")       
     break;
        case 2:

     logger.recordMetadata("GitDirty", "Unkown")       
     break;

     
    }
}

switch (RobotStateConstants.getMode()) {
    case REAL:
    logger.addDataReceiver(new WPILOGReader());
    logger.addDataReceiver(new NT4Publisher());
    break;
    case SIM:
    logger.addDataReceiver(new NT4Publisher());
    break;
    case REPLAY:
    setUseTiming(false);
    String logPath = LogFileUtil.findReplayLog();
    logger.setReplaySource(new WPILOGReader(logPath));
    logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim")));
    break;

}

logger.start();


chooser.addDefaultOption("Default Auto", defaultAuto);
chooser.addOptionA("My Auto", customAuto);


@Override
public void robotPeriodic() {

}

@Override
public void autonomousInit() {
    autoSelected = chooser.get();
    System.out.println("Auto Selected" + autoSelected)


}


@Override 
public void autonomousPeriodic() {
    switch(autoSelected) {
        case customAuto:
           break;
        case defaultAuto:
        default:
            break; 


    }
}

@Override 
public void teleopInit() {

}

@Override
public void teleopPeriodic()  {

} 

@Override 
public void disabledInit() {

}
@Override
public void disabledPeriodic() {

}
@Override
public void testInit() {

}
@Override
public void testPeriodic() {

}
@Override 
public void simulationInit() {

}
@Override
public void simulationPeriodic() {
    
}