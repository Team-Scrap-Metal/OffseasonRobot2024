package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

/** Aintake Loggable Inputs and Outputs of the intake */

public interface intakeIO {

  @AutoLog
  public static class intakeIOInputs {
    /** This returns the voltage the Propulsion Motor Recieves */
    public double intakeAppliedVolts = 0.0;
    /** Returns the position of the Propulsion Motor by how many radians it has rotated */
    public double intakePositionRad = 0.0;
    /**
     * Returns the velocity of the Propulsion Motor by how many radians per second it has rotated
     */
    public double intakeVelocityRadPerSec = 0.0;
    /**
     * Returns the absoltute value of the velocity of the Propulsion Motor by how many radians per
     * second it has rotated (Used to find Displacement)
     */
    public double intakeVelocityRadPerSecAbs = Math.abs(0.0);
    /** The Current Drawn from the Propulsion Motor in Amps */
    public double[] intakeCurrentAmps = new double[] {};
    /** The tempature of the Propulsion Motor in Celsius */
    public double[] intakeTempCelsius = new double[] {};
  }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(ModuleIOInputs inputs) {}

  /** Run the intake motor at the specified voltage. */
  public default void setintakeVoltage(double volts) {}

  /** Run the turn motor at the specified voltage. */
  public default void setTurnVoltage(double volts) {}

}