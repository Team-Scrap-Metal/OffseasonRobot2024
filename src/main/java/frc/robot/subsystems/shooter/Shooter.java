package frc.robot.subsystems.shooter;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RobotStateConstants;
import org.littletonrobotics.junction.Logger;

public class Shooter extends SubsystemBase {

  private final ShooterIO io;
  private final ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();

  public Shooter(ShooterIO io) {
    System.out.println("[Init] Creating Shooter");
    this.io = io;
  }

  @Override
  public void periodic() {
    this.updateInputs();
    Logger.processInputs("Shooter", inputs);
   
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

  public void setBothShooterVoltage(double volts) {
  this.setRightShooterVoltage(volts);
  this.setLeftShooterVoltage(volts);
  }
  public void setRightShooterPercentage(double volts) {
    this.setRightShooterVoltage(volts * RobotStateConstants.BATTERY_VOLTAGE);

  }
  public void setLeftShooterPercentage(double volts) {
    this.setLeftShooterVoltage(volts * RobotStateConstants.BATTERY_VOLTAGE);
  }
  public void setBothShooterVoltage(double rightPercentage, double leftPercentage ) {
    this.setRightShooterVoltage(rightPercentage * RobotStateConstants.BATTERY_VOLTAGE);
    this.setLeftShooterVoltage(leftPercentage * RobotStateConstants.BATTERY_VOLTAGE);



  }







  
}

