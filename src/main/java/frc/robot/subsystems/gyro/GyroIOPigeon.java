// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.gyro;

import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotState;

/** Runs Real Pigeon 2.0 Gyroscope */
public class GyroIOPigeon implements GyroIO {

  private Pigeon2 gyro;

  public GyroIOPigeon() {
    System.out.println("[Init] Creating GyroIONavX");
    gyro = new Pigeon2(GyroConstants.CAN_ID);
    gyro.setYaw(GyroConstants.AUTO_HEADING_OFFSET_DEGREES);
  }

  @Override
  public void updateInputs(GyroIOInputs inputs) {
    inputs.rollPositionRad =
        new Rotation2d(Units.degreesToRadians(gyro.getRoll().getValueAsDouble()));
    inputs.pitchPositionRad =
        new Rotation2d(Units.degreesToRadians(gyro.getPitch().getValueAsDouble()));
    // Value is Negative because NavX reads CW and everything else runs CCW
    inputs.yawPositionRad =
        RobotState.isAutonomous() == true
            ? new Rotation2d(
                Units.degreesToRadians(
                    gyro.getYaw().getValueAsDouble() + GyroConstants.AUTO_HEADING_OFFSET_DEGREES))
            : new Rotation2d(
                Units.degreesToRadians(
                    gyro.getYaw().getValueAsDouble()
                        + GyroConstants.TELEOP_HEADING_OFFSET_DEGREES));
    inputs.anglePositionRad =
        RobotState.isAutonomous() == true
            ? new Rotation2d(
                Units.degreesToRadians(
                    gyro.getYaw().getValueAsDouble() + GyroConstants.AUTO_HEADING_OFFSET_DEGREES))
            : new Rotation2d(
                Units.degreesToRadians(
                    gyro.getYaw().getValueAsDouble()
                        + GyroConstants.TELEOP_HEADING_OFFSET_DEGREES));
    inputs.rollVelocityRadPerSec =
        Units.degreesToRadians(
            gyro.getAngularVelocityYDevice()
                .getValueAsDouble()); // Gets the angular velocity, in degrees per second, of the
    // roll
    // and converts it to radians per second
    inputs.pitchVelocityRadPerSec =
        Units.degreesToRadians(
            gyro.getAngularVelocityXDevice()
                .getValueAsDouble()); // Gets the angular velocity, in degrees per second, of the
    // pitch
    // and converts it to radians per second
    inputs.yawVelocityRadPerSec =
        Units.degreesToRadians(
            gyro.getAngularVelocityZDevice()
                .getValueAsDouble()); // Gets the angular velocity, in degrees per second, of the
    // yaw and
    // converts it to radians per second

    inputs.temperatureCelcius = gyro.getTemperature().getValueAsDouble();
  }

  @Override
  public void zeroHeading() {
    gyro.reset();
  }
}
