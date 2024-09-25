package frc.robot.subsystems.shooter;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RobotStateConstants;
import org.littletonrobotics.junction.Logger;

public class Shooter extends SubsystemBase {

  private final ShooterIO io;
  private final ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();

  // initialize PID controllers
  private PIDController leftPID = new PIDController(0, 0, 0);
  private PIDController rightPID = new PIDController(0, 0, 0);

  public Shooter(ShooterIO io) {
    System.out.println("[Init] Creating Shooter");
    this.io = io;

    leftPID = new PIDController(ShooterConstants.kP, ShooterConstants.kI, ShooterConstants.kD);
    rightPID = new PIDController(ShooterConstants.kP, ShooterConstants.kI, ShooterConstants.kD);
    leftPID.setTolerance(ShooterConstants.PID_TOLERANCE_RPM);
    rightPID.setTolerance(ShooterConstants.PID_TOLERANCE_RPM);
    leftPID.setSetpoint(0.0);
    rightPID.setSetpoint(0.0);
  }

  @Override
  public void periodic() {
    this.updateInputs();
    Logger.processInputs("Shooter", inputs);
    setLeftShooterVoltage(
        (leftPID.getSetpoint() + leftPID.calculate(this.getLeftVelocityRPM()))
            * RobotStateConstants.BATTERY_VOLTAGE
            / 5300);
    setRightShooterVoltage(
        (rightPID.getSetpoint() + rightPID.calculate(this.getLeftVelocityRPM()))
            * RobotStateConstants.BATTERY_VOLTAGE
            / 5300);
  }
  /**
   * Update inputs without running the rest of the periodic logic. This is useful since these
   * updates need to be properly thread-locked.
   */
  public void updateInputs() {
    io.updateInputs(inputs);
  }

  public void setLeftShooterVoltage(double volts) {
    io.setLeftShooterVoltage(volts);
  }

  public void setRightShooterVoltage(double volts) {
    io.setRightShooterVoltage(volts);
  }

  public void setBothVoltage(double volts) {
    setRightShooterVoltage(volts);
    setLeftShooterVoltage(volts);
  }

  public double getLeftVelocityRPM() {
    return inputs.leftVelocityRPM;
  }

  public double getRightVelocityRPM() {
    return inputs.rightVelocityRPM;
  }

  public void setLeftSetpoint(double setpoint) {
    leftPID.setSetpoint(setpoint);
  }

  public void setRightSetpoint(double setpoint) {
    leftPID.setSetpoint(setpoint);
  }

  public void setBothSetpoint(double leftSetpoint, double rightSetpoint) {
    leftPID.setSetpoint(leftSetpoint);
    rightPID.setSetpoint(rightSetpoint);
  }

  public void stopAll() {
    leftPID.setSetpoint(0);
    rightPID.setSetpoint(0);
  }

  public boolean atSetpoint() {
    return leftPID.atSetpoint() && rightPID.atSetpoint();
  }
}
