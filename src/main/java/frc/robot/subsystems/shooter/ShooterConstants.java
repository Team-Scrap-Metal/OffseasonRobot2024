package frc.robot.subsystems.shooter;

public class ShooterConstants {

  public static final int LEFT_CAN_ID = 16;
  public static final int RIGHT_CAN_ID = 17;

  /* True = Inverted, False = Not Inverted */
  public static final boolean LEFT_INVERTED = false;
  public static final boolean RIGHT_INVERTED = true;

  public static final int CURR_LIM_A = 30;

  public static final double kP = 0.0;
  public static final double kI = 0.0;
  public static final double kD = 0.0;

  public static final double PID_TOLERANCE_RPM = 100;
}
