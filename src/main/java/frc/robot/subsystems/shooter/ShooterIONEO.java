package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import frc.robot.Constants.RobotStateConstants;

public class ShooterIONEO implements ShooterIO {
  private final CANSparkMax CANSparkMaxLeft;
  private final CANSparkMax CANSparkMAxRight;
  private final RelativeEncoder RelativeEncoderLeft;
  private final RelativeEncoder RelativeEncoderRight;

  public ShooterIONEO() {
    CANSparkMAxRight = new CANSparkMax(ShooterConstants.RIGHT_CAN_ID, MotorType.kBrushless);
    CANSparkMaxLeft = new CANSparkMax(ShooterConstants.LEFT_CAN_ID, MotorType.kBrushless);


    RelativeEncoderRight = CANSparkMAxRight.getEncoder();
    RelativeEncoderLeft = CANSparkMaxLeft.getEncoder();

    CANSparkMAxRight.setSmartCurrentLimit(ShooterConstants.CURR_LIM_A);
    CANSparkMaxLeft.setSmartCurrentLimit(ShooterConstants.CURR_LIM_A);
    CANSparkMAxRight.setInverted(ShooterConstants.RIGHT_INVERTED);
    CANSparkMaxLeft.setInverted(ShooterConstants.LEFT_INVERTED);
    CANSparkMAxRight.setIdleMode(IdleMode.kBrake);
    CANSparkMaxLeft.setIdleMode(IdleMode.kBrake);
    CANSparkMAxRight.setCANTimeout(RobotStateConstants.CAN_CONFIG_TIMEOUT_SEC);
    CANSparkMaxLeft.setCANTimeout(RobotStateConstants.CAN_CONFIG_TIMEOUT_SEC);
    RelativeEncoderLeft.setPosition(0);

  }
  public void updateInputs(ShooterIOInputs inputs) {
    /** Velocity of the shooter Rollers in Rotations per Minute */
    inputs.leftVelocityRPM = RelativeEncoderLeft.getVelocity();
    /** Number of volts being sent to the shooter motor */
    inputs.leftAppliedVolts = CANSparkMaxLeft.getAppliedOutput() * CANSparkMaxLeft.getBusVoltage();
    /** Velocity of the shooter Rollers in Rotations per Minute */
    inputs.rightVelocityRPM = RelativeEncoderRight.getVelocity();
    /** Number of volts being sent to the shooter motor */
    inputs.rightAppliedVolts = CANSparkMAxRight.getAppliedOutput() * CANSparkMAxRight.getBusVoltage(); 
    /** Number of Amps being used by the shooter motor */
    inputs.currentAmps = new double[] {CANSparkMAxRight.getOutputCurrent(), CANSparkMaxLeft.getMotorTemperature()};
    /** Tempature of the shooter motor */
    inputs.tempCelsius = new double[] {CANSparkMAxRight.getMotorTemperature(), CANSparkMaxLeft.getMotorTemperature()};
  }

  public  void setLeftShooterVoltage(double volts) {
    CANSparkMaxLeft.setVoltage(volts);
  }
  /**
   * Sets the voltage for the Shooter
   *
   * @param volts -12 to 12
   */
  public  void setRightShooterVoltage(double volts) {
    CANSparkMAxRight.setVoltage(volts);
  }

  /**
   * Sets the Brake Mode for the Shooter
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public  void setIdleMode(boolean enable) {
    if(enable) {
      CANSparkMAxRight.setIdleMode(IdleMode.kBrake);
      CANSparkMaxLeft.setIdleMode(IdleMode.kBrake);
    } else {
      CANSparkMAxRight.setIdleMode(IdleMode.kCoast);
      CANSparkMaxLeft.setIdleMode(IdleMode.kCoast);
    }
  }





}

