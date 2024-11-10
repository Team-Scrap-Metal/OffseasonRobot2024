package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import frc.robot.Constants.RobotStateConstants;

public class ShooterIONEO implements ShooterIO {
  
  private final CANSparkMax CANSparkMaxRight;
  private final CANSparkMax CANSparkMaxLeft;
  private final RelativeEncoder RelativeEncoderRight;
  private final RelativeEncoder RelativeEncoderLeft;

  public ShooterIONEO() {
    CANSparkMaxRight = new CANSparkMax(ShooterConstants.RIGHT_CAN_ID, MotorType.kBrushless);
    RelativeEncoderRight = CANSparkMaxRight.getEncoder();
    CANSparkMaxRight.setSmartCurrentLimit(ShooterConstants.CURR_LIM_A);
    CANSparkMaxRight.setInverted(ShooterConstants.RIGHT_INVERTED);
    CANSparkMaxRight.setIdleMode(IdleMode.kBrake);
    CANSparkMaxRight.setCANTimeout(RobotStateConstants.CAN_CONFIG_TIMEOUT_SEC);
    RelativeEncoderRight.setPosition(0);
  }

  /** Updates inputs for the Shooter */
  @Override
  public void updateInputs(ShooterIOInputs inputs) {
    /** Velocity of the shooter Rollers in Rotations per Minute */
    inputs.leftVelocityRPM = 0.0;
    /** Number of volts being sent to the shooter motor */
    inputs.leftAppliedVolts = 0.0;
    /** Velocity of the shooter Rollers in Rotations per Minute */
    inputs.rightVelocityRPM = RelativeEncoderRight.getVelocity();
    /** Number of volts being sent to the shooter motor */
    inputs.rightAppliedVolts = CANSparkMaxRight.getAppliedOutput() * CANSparkMaxRight.getBusVoltage();
    /** Number of Amps being used by the shooter motor */
    inputs.currentAmps = new double[] {CANSparkMaxRight.getOutputCurrent()/*, Left.OutputCurrent */};
    /** Tempature of the shooter motor */
    inputs.tempCelsius = new double[] {CANSparkMaxRight.getMotorTemperature()};
  }

  /**
   * Sets the voltage for the Shooter
   *
   * @param volts -12 to 12
   */
  @Override
  public void setLeftShooterVoltage(double volts) {

  }
  /**
   * Sets the voltage for the Shooter
   *
   * @param volts -12 to 12
   */
  @Override
  public void setRightShooterVoltage(double volts) {
    CANSparkMaxRight.setVoltage(volts);
  }

  /**
   * Sets the Brake Mode for the Shooter
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  @Override
  public void setBrakeMode(boolean enable) {
    if(enable){
      CANSparkMaxRight.setIdleMode(IdleMode.kBrake);
      //left
    } else {
      CANSparkMaxRight.setIdleMode(IdleMode.kCoast);
    }
    }
}
