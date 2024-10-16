package frc.robot.subsystems.drive;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants.RobotStateConstants;

/** Runs an Individual Real Module with all Motors as Neos */
public class ModuleIOKrakenNeo implements ModuleIO {
  private final TalonFX driveTalonFX;
  private final CANSparkMax turnSparkMax;

  private final RelativeEncoder turnRelativeEncoder;
  private final CANcoder turnAbsoluteEncoder;

  private final boolean isTurnMotorInverted = false;
  private final double absoluteEncoderOffset;
  private final int swerveModuleNumber;

  public ModuleIOKrakenNeo(int index) {
    this.swerveModuleNumber = index;
    System.out.println("[Init] Creating ModuleIOKrakenNEO" + swerveModuleNumber);

    // sets drive & turn spark maxes, turn encoder, and absolute encoder offset
    switch (index) {
      case 0:
        driveTalonFX = new TalonFX(DriveConstants.DRIVE_MOTOR.FRONT_RIGHT.CAN_ID);
        turnSparkMax =
            new CANSparkMax(DriveConstants.TURN_MOTOR.FRONT_RIGHT.CAN_ID, MotorType.kBrushless);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.ABSOLUTE_ENCODER.FRONT_RIGHT.ENCODER_ID);
        absoluteEncoderOffset = DriveConstants.ABSOLUTE_ENCODER_OFFSET_RAD.FRONT_RIGHT.OFFSET;
        break;
      case 1:
        driveTalonFX = new TalonFX(DriveConstants.DRIVE_MOTOR.FRONT_LEFT.CAN_ID);
        turnSparkMax =
            new CANSparkMax(DriveConstants.TURN_MOTOR.FRONT_LEFT.CAN_ID, MotorType.kBrushless);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.ABSOLUTE_ENCODER.FRONT_LEFT.ENCODER_ID);
        absoluteEncoderOffset = DriveConstants.ABSOLUTE_ENCODER_OFFSET_RAD.FRONT_LEFT.OFFSET;
        break;
      case 2:
        driveTalonFX = new TalonFX(DriveConstants.DRIVE_MOTOR.BACK_LEFT.CAN_ID);
        turnSparkMax =
            new CANSparkMax(DriveConstants.TURN_MOTOR.BACK_LEFT.CAN_ID, MotorType.kBrushless);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.ABSOLUTE_ENCODER.BACK_LEFT.ENCODER_ID);
        absoluteEncoderOffset = DriveConstants.ABSOLUTE_ENCODER_OFFSET_RAD.BACK_LEFT.OFFSET;
        break;
      case 3:
        driveTalonFX = new TalonFX(DriveConstants.DRIVE_MOTOR.BACK_RIGHT.CAN_ID);
        turnSparkMax =
            new CANSparkMax(DriveConstants.TURN_MOTOR.BACK_RIGHT.CAN_ID, MotorType.kBrushless);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.ABSOLUTE_ENCODER.BACK_RIGHT.ENCODER_ID);
        absoluteEncoderOffset = DriveConstants.ABSOLUTE_ENCODER_OFFSET_RAD.BACK_RIGHT.OFFSET;
        break;
      default:
        throw new RuntimeException("Invalid module index for ModuleIOSparkMax");
    }

    // Set CAN Timeout
    driveTalonFX.setExpiration(RobotStateConstants.CAN_CONFIG_TIMEOUT_SEC);
    turnSparkMax.setCANTimeout(RobotStateConstants.CAN_CONFIG_TIMEOUT_SEC);

    // Connects Turn Motor and Turn Encoder
    turnRelativeEncoder = turnSparkMax.getEncoder();

    /** For each drive motor, update values */
    for (int i = 0; i < DriveConstants.DRIVE_MOTOR.values().length; i++) {
      turnSparkMax.setPeriodicFramePeriod(
          PeriodicFrame.kStatus2, DriveConstants.MEASUREMENT_PERIOD_MS);
      driveTalonFX.setInverted(DriveConstants.KrakenNEOModule.INVERT_TALONFX);
      turnSparkMax.setInverted(DriveConstants.KrakenNEOModule.INVERT_SPARK_MAX);

      CurrentLimitsConfigs currentLimitsConfig =
          new CurrentLimitsConfigs().withSupplyCurrentLimit(DriveConstants.CUR_LIM_A);
      currentLimitsConfig.withSupplyCurrentLimitEnable(DriveConstants.ENABLE_CUR_LIM);
      currentLimitsConfig.withStatorCurrentLimit(DriveConstants.CUR_LIM_A);
      currentLimitsConfig.withStatorCurrentLimitEnable(DriveConstants.ENABLE_CUR_LIM);
      driveTalonFX.getConfigurator().apply(currentLimitsConfig);
      turnSparkMax.setSmartCurrentLimit(DriveConstants.CUR_LIM_A, DriveConstants.CUR_LIM_A);

      driveTalonFX.setPosition(0.0); // resets position
    }

    // Initializes motors in brake mode
    driveTalonFX.setNeutralMode(NeutralModeValue.Brake);
    turnSparkMax.setIdleMode(IdleMode.kBrake);

    // ensure configs remain after power cycles
    turnSparkMax.burnFlash();
  }

  @Override
  /**
   * updates the inputs to be actual values
   *
   * @param inputs from ModuleIOInputsAutoLogged
   */
  public void updateInputs(ModuleIOInputs inputs) {
    inputs.drivePositionRad =
        Units.rotationsToRadians(
            driveTalonFX.getPosition().getValueAsDouble() / DriveConstants.GEAR_RATIO);
    inputs.driveVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(
                driveTalonFX.getVelocity().getValueAsDouble() * 60)
            / DriveConstants.GEAR_RATIO;

    // unit conversions: Kraken getVelocity returns rotations per sec, multiply by 60 to get RPM
    inputs.driveVelocityRadPerSecAbs =
        Math.abs(
            Units.rotationsPerMinuteToRadiansPerSecond(
                    driveTalonFX.getVelocity().getValueAsDouble() * 60)
                / DriveConstants.GEAR_RATIO);

    inputs.driveAppliedVolts =
        driveTalonFX.getMotorVoltage().getValueAsDouble()
            * driveTalonFX.getSupplyVoltage().getValueAsDouble();

    inputs.driveCurrentAmps = new double[] {driveTalonFX.getStatorCurrent().getValueAsDouble()};
    inputs.driveTempCelsius = new double[] {driveTalonFX.getDeviceTemp().getValueAsDouble()};

    // getPosition returns rotations of motor, not the turn angle
    inputs.turnAbsolutePositionRad =
        MathUtil.angleModulus(
            new Rotation2d(
                    Units.rotationsToRadians(
                            turnAbsoluteEncoder.getAbsolutePosition().getValueAsDouble())
                        + absoluteEncoderOffset)
                .getRadians());

    inputs.turnAppliedVolts = turnSparkMax.getAppliedOutput() * turnSparkMax.getBusVoltage();
    inputs.turnCurrentAmps = new double[] {turnSparkMax.getOutputCurrent()};
    inputs.turnTempCelsius = new double[] {turnSparkMax.getMotorTemperature()};
  }

  @Override
  public void setDriveVoltage(double volts) {
    driveTalonFX.setVoltage(volts);
  }

  @Override
  public void setTurnVoltage(double volts) {
    turnSparkMax.setVoltage(volts);
  }

  @Override
  public void setDriveBrakeMode(boolean enable) {
    turnSparkMax.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
  }

  @Override
  public void setTurnBrakeMode(boolean enable) {
    driveTalonFX.setNeutralMode(enable ? NeutralModeValue.Brake : NeutralModeValue.Coast);
  }
}
