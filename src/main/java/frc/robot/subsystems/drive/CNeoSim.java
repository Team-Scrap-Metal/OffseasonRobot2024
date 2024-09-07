package frc.robot.subsystems.drive;

import java.util.Optional;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import frc.robot.Constants.RobotStateConstants;

/** Runs an Individual Real Module with all Motors as Neos */
public class ModuleIONeoCIM implements ModuleIO {
  private final CANSparkMax driveSparkMax;
  private final WPI_VictorSPX turnVictorSPX;

  private final RelativeEncoder driveRelativeEncoder;
  private final CANcoder turnAbsoluteEncoder;

  private final boolean isTurnMotorInverted = true;
  private final double absoluteEncoderOffset;
  private final int swerveModuleNumber;

  public ModuleIONeoCIM(int index) {
    this.swerveModuleNumber = index;
    System.out.println("[Init] Creating ModuleIONeoCIM" + swerveModuleNumber);

    // sets drive & turn spark maxes, turn encoder, and absolute encoder offset
    switch (index) {
      case 0:
        driveSparkMax =
            new CANSparkMax(DriveConstants.DRIVE_MOTOR.FRONT_RIGHT.CAN_ID, MotorType.kBrushless);
        turnVictorSPX =
            new WPI_VictorSPX(DriveConstants.TURN_MOTOR.FRONT_RIGHT.CAN_ID);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.ABSOLUTE_ENCODER.FRONT_RIGHT.ENCODER_ID);
        absoluteEncoderOffset = DriveConstants.ABSOLUTE_ENCODER_OFFSET_RAD.FRONT_RIGHT.OFFSET;
        break;
      case 1:
        driveSparkMax =
            new CANSparkMax(DriveConstants.DRIVE_MOTOR.FRONT_LEFT.CAN_ID, MotorType.kBrushless);
        turnVictorSPX =
            new WPI_VictorSPX(DriveConstants.TURN_MOTOR.FRONT_LEFT.CAN_ID);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.ABSOLUTE_ENCODER.FRONT_LEFT.ENCODER_ID);
        absoluteEncoderOffset = DriveConstants.ABSOLUTE_ENCODER_OFFSET_RAD.FRONT_LEFT.OFFSET;
        break;
      case 2:
        driveSparkMax =
            new CANSparkMax(DriveConstants.DRIVE_MOTOR.BACK_LEFT.CAN_ID, MotorType.kBrushless);
        turnVictorSPX =
            new WPI_VictorSPX(DriveConstants.TURN_MOTOR.BACK_LEFT.CAN_ID);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.ABSOLUTE_ENCODER.BACK_LEFT.ENCODER_ID);
        absoluteEncoderOffset = DriveConstants.ABSOLUTE_ENCODER_OFFSET_RAD.BACK_LEFT.OFFSET;
        break;
      case 3:
        driveSparkMax =
            new CANSparkMax(DriveConstants.DRIVE_MOTOR.BACK_RIGHT.CAN_ID, MotorType.kBrushless);
        turnVictorSPX =
            new WPI_VictorSPX(DriveConstants.TURN_MOTOR.BACK_RIGHT.CAN_ID);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.ABSOLUTE_ENCODER.BACK_RIGHT.ENCODER_ID);
        ndex;
        absoluteEncoderOffset = DriveConstants.ABSOLUTE_ENCODER_OFFSET_RAD.BACK_RIGHT.OFFSET;
        break;
      default:
        throw new RuntimeException("Invalid module index for ModuleIOSparkMax");
    }

    //Set CAN ID
    driveSparkMax.setCANTimeout(RobotStateConstants.CAN_CONFIG_TIMEOUT_SEC);
    turnVictorSPX.setExpiration(RobotStateConstants.CAN_CONFIG_TIMEOUT_SEC);
    

    //Connects Drive Motor and Drive Encoder
    driveRelativeEncoder = driveSparkMax.getEncoder();

    /** For each drive motor, update values */
    for (int i = 0; i < DriveConstants.DRIVE_MOTOR.values().length; i++) {
      driveSparkMax.setPeriodicFramePeriod(
          PeriodicFrame.kStatus2, DriveConstants.MEASUREMENT_PERIOD_MS);
      turnVictorSPX.setInverted(isTurnMotorInverted);

      driveSparkMax.setSmartCurrentLimit(DriveConstants.CUR_LIM_A);

      driveRelativeEncoder.setPosition(0.0); // resets position
      driveRelativeEncoder.setMeasurementPeriod(
          DriveConstants.MEASUREMENT_PERIOD_MS); // sensor reads every 10ms
      driveRelativeEncoder.setAverageDepth(
          2); // sets velocity calculation process's sampling depth (??)

      // Same for but turn motors
      turnVictorSPX.setSelectedSensorPosition(0);
    }

    // ensure configs remain after power cycles
    driveSparkMax.burnFlash();
  }

  @Override
  /**
   * updates the inputs to be actual values
   *
   * @param inputs from ModuleIOInputsAutoLogged
   */
  public void updateInputs(ModuleIOInputs inputs) {
    inputs.drivePositionRad =
        Units.rotationsToRadians(driveRelativeEncoder.getPosition() / DriveConstants.GEAR_RATIO);

    inputs.driveVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(driveRelativeEncoder.getVelocity())
            / DriveConstants.GEAR_RATIO;

    inputs.driveAppliedVolts = driveSparkMax.getAppliedOutput() * driveSparkMax.getBusVoltage();

    inputs.driveCurrentAmps = new double[] {driveSparkMax.getOutputCurrent()};
    inputs.driveTempCelsius = new double[] {driveSparkMax.getMotorTemperature()};

    inputs.turnAbsolutePositionRad =
        MathUtil.angleModulus(
            new Rotation2d(
                    Units.rotationsToRadians(
                            turnAbsoluteEncoder.getAbsolutePosition().getValueAsDouble())
                        + absoluteEncoderOffset // get Position returns rotations of motor not
                    // degrees
                    )
                .getRadians());

    inputs.turnAppliedVolts = turnVictorSPX.getMotorOutputVoltage() * turnVictorSPX.getBusVoltage();
    inputs.turnCurrentAmps = new double[] {0};
    inputs.turnTempCelsius = new double[] {turnVictorSPX.getTemperature()};
  }

  @Override
  public void setDriveVoltage(double volts) {
    driveSparkMax.setVoltage(volts);
  }

  @Override
  public void setTurnVoltage(double volts) {
    turnVictorSPX.setVoltage(volts);
  }

  @Override
  public void setDriveBrakeMode(boolean enable) {
    driveSparkMax.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
  }

  @Override
  public void setTurnBrakeMode(boolean enable) {
    turnVictorSPX.setNeutralMode(enable ? NeutralMode.Brake : NeutralMode.Coast);
  }
}