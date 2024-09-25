package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {
  @AutoLog
  public static class IntakeIOInputs {
    /** Velocity of the intake Rollers in Rotations per Minute */
    public double velocityRPM = 0.0;
    /** Number of volts being sent to the intake motor */
    public double appliedVolts = 0.0;
    /** Number of Amps being used by the intake motor */
    public double[] currentAmps = new double[] {};
    /** Tempature of the intake motor */
    public double[] tempCelsius = new double[] {};
  }

  /** Updates inputs for the Intake */
  public default void updateInputs(IntakeIOInputs inputs) {}

  /**
   * Sets the voltage for the Intake
   *
   * @param volts -12 to 12
   */
  public default void setIntakeVoltage(double volts) {}

  /**
   * Sets the Brake Mode for the Intake
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public default void setBrakeMode(boolean enable) {}
}
