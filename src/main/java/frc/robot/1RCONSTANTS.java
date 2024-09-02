




package frc.robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.RobotBase;
import java.util.Optional;






public final class RConstants {


    public static class RRobotStateConstants {
        public static enum RRobotMode {
           RREAL,
           RSIM,
           RREPLAY 
        }
        public static final RRobotMode RGetMode() {
            if(RobotBase.isReal() ) {
                return RRobotMode.RREAL;
            } else if(RobotBase.isSimulation()) {
                return RRobotMode.RSIM;
            } else {
                return RRobotMode.RREPLAY;
            }
        }
        public static final Optional<RAlliance> RGetAlliance() {
            return DriverStation.getAlliance();
            
        }
        public static final int RCAN_CONFIG_TIMEOUT_SEC = 30;
        public static final double RLOOP_PERIODIC_SEC = 0.02;
        public static final double RBATTERY_VOLTAGE = 12;


    }
    public static final class ROPERATOR_CONSTANTS {
        public static final int RDRIVER_PORT = 0;
        public static final int RAUX_PORT = 1;

    }
}











