package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.AutoLog;

public interface ShooterIO {
@AutoLog
public static class ShooterIOInputs {
    /** Velocity of the shooter Rollers in Rotations per Minute */
    public double leftVelocityRPM = 0.0;
    /** Number of volts being sent to the shooter motor */
    public double leftAppliedVolts = 0.0;
        /** Velocity of the shooter Rollers in Rotations per Minute */
    public double rightVelocityRPM = 0.0;
    /** Number of volts being sent to the shooter motor */
    public double rightAppliedVolts = 0.0;
    /** Number of Amps being used by the shooter motor */
    public double[] currentAmps = new double[] {};
    /** Tempature of the shooter motor */
    public double[] tempCelsius = new double[] {};
}


  /** Updates inputs for the  Shooter */
  public default void updateInputs(ShooterIOInputs inputs) {}

  /**
   * Sets the voltage for the  Shooter
   *
   * @param volts -12 to 12
   */
  public default void setLeftShooterVoltage(double volts) {}
  /**
   * Sets the voltage for the  Shooter
   *
   * @param volts -12 to 12
   */
  public default void setRightShooterVoltage(double volts) {}

  /**
   * Sets the Brake Mode for the  Shooter
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public default void setBrakeMode(boolean enable) {}
}