package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import frc.robot.Constants.RobotStateConstants;

public class ShooterIONEO implements ShooterIO {
  private final CANSparkMax leftSparkMax;
  private final CANSparkMax rightSparkMax;

  private final RelativeEncoder leftRelativeEncoder;
  private final RelativeEncoder rightRelativeEncoder;

  public ShooterIONEO() {
    System.out.println("[Init] Creating IntakeIONeo");
    leftSparkMax = new CANSparkMax(ShooterConstants.LEFT_CAN_ID, MotorType.kBrushless);
    rightSparkMax = new CANSparkMax(ShooterConstants.RIGHT_CAN_ID, MotorType.kBrushless);

    leftRelativeEncoder = leftSparkMax.getEncoder();
    rightRelativeEncoder = rightSparkMax.getEncoder();
    leftSparkMax.setCANTimeout(RobotStateConstants.CAN_CONFIG_TIMEOUT_SEC);
    rightSparkMax.setCANTimeout(RobotStateConstants.CAN_CONFIG_TIMEOUT_SEC);

    leftSparkMax.setInverted(ShooterConstants.LEFT_INVERTED);
    rightSparkMax.setInverted(ShooterConstants.RIGHT_INVERTED);

    leftRelativeEncoder.setPosition(0);
    rightRelativeEncoder.setPosition(0);

    leftSparkMax.setSmartCurrentLimit(ShooterConstants.CURR_LIM_A);
    rightSparkMax.setSmartCurrentLimit(ShooterConstants.CURR_LIM_A);

    leftSparkMax.setIdleMode(IdleMode.kBrake);
    rightSparkMax.setIdleMode(IdleMode.kBrake);
  }

  /** Updates inputs for the Shooter */
  public void updateInputs(ShooterIOInputs inputs) {
    /** Velocity of the shooter Rollers in Rotations per Minute */
    inputs.leftVelocityRPM = leftRelativeEncoder.getVelocity();
    /** Number of volts being sent to the shooter motor */
    inputs.leftAppliedVolts = leftSparkMax.getAppliedOutput() * leftSparkMax.getBusVoltage();
    /** Velocity of the shooter Rollers in Rotations per Minute */
    inputs.rightVelocityRPM = rightRelativeEncoder.getVelocity();
    /** Number of volts being sent to the shooter motor */
    inputs.rightAppliedVolts = rightSparkMax.getAppliedOutput() * rightSparkMax.getBusVoltage();
    /** Number of Amps being used by the shooter motor */
    inputs.currentAmps =
        new double[] {leftSparkMax.getOutputCurrent(), rightSparkMax.getOutputCurrent()};
    /** Tempature of the shooter motor */
    inputs.tempCelsius =
        new double[] {leftSparkMax.getMotorTemperature(), rightSparkMax.getMotorTemperature()};
  }

  /**
   * Sets the voltage for the Shooter
   *
   * @param volts -12 to 12
   */
  public void setLeftShooterVoltage(double volts) {
    leftSparkMax.setVoltage(volts);
  }
  /**
   * Sets the voltage for the Shooter
   *
   * @param volts -12 to 12
   */
  public void setRightShooterVoltage(double volts) {
    rightSparkMax.setVoltage(volts);
  }

  /**
   * Sets the Brake Mode for the Shooter
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public void setBrakeMode(boolean enable) {
    rightSparkMax.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
  }
}
