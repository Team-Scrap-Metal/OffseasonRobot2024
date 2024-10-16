package frc.robot.subsystems.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import frc.robot.Constants.RobotStateConstants;
import frc.robot.subsystems.drive.DriveConstants.SimDriveConstants;

/**
 * Runs Simulation for an Individual Swerve Module with the Drive Motor as a Neo and the Turn Motor
 * as a Mini CIM
 */
public class ModuleIOSimNeoCIM implements ModuleIO {

  // Declares Sim Wheels Representing Turning and Driving
  private DCMotorSim driveSim;
  private DCMotorSim turnSim;

  private double turnRelativePositionRad = 0.0;
  private double turnAbsolutePositionRad = 0.0;
  private double driveAppliedVolts = 0.0;
  private double turnAppliedVolts = 0.0;

  public ModuleIOSimNeoCIM() {
    System.out.println("[Init] Creating ModuleIOSimNeoCIM");
    // Builds Drive Wheel for the Neo Motor in the L2 Module
    driveSim =
        new DCMotorSim(
            DCMotor.getNEO(1), DriveConstants.GEAR_RATIO, SimDriveConstants.STEER_MOI_KG_M2);

    // Builds Turn Wheel for the Neo Motor in the L2 Module
    turnSim =
        new DCMotorSim(
            DCMotor.getMiniCIM(1), DriveConstants.GEAR_RATIO, SimDriveConstants.STEER_MOI_KG_M2);
  }

  @Override
  public void updateInputs(ModuleIOInputs inputs) {
    driveSim.update(RobotStateConstants.LOOP_PERIODIC_SEC);
    turnSim.update(RobotStateConstants.LOOP_PERIODIC_SEC);

    /*
     * Used to Represent Absolute Encoder vss Relative Encoder for Steer Motors
     * Get Current Turn Position Rad
     * Relative Position + angleDiff is relative change in difference from moment
     * the robot was turned on
     * Absolute Position + angleDiff is change from 0 location
     * If the Absolute Position is over the distance of a circle or under the
     * distance of a circle add/subtract 2pi to become within a circle
     */
    double angleDiffRad =
        turnSim.getAngularVelocityRadPerSec() * RobotStateConstants.LOOP_PERIODIC_SEC;
    turnRelativePositionRad += angleDiffRad;
    turnAbsolutePositionRad += angleDiffRad;
    while (turnAbsolutePositionRad < 0) {
      turnAbsolutePositionRad += 2.0 * Math.PI;
    }
    while (turnAbsolutePositionRad > 2.0 * Math.PI) {
      turnAbsolutePositionRad -= 2.0 * Math.PI;
    }

    // Drive Position Rad caclulated from Velocity Rads/s multiplied by seconds to
    // leave with Radians
    inputs.drivePositionRad =
        inputs.drivePositionRad
            + (driveSim.getAngularVelocityRadPerSec() * RobotStateConstants.LOOP_PERIODIC_SEC);
    inputs.driveVelocityRadPerSec = driveSim.getAngularVelocityRadPerSec();
    inputs.driveAppliedVolts = driveAppliedVolts;
    // Math.abs = absolute value, sim sometimes makes amps directional
    inputs.driveCurrentAmps = new double[] {Math.abs(driveSim.getCurrentDrawAmps())};
    inputs.driveTempCelsius = new double[] {};

    inputs.turnAbsolutePositionRad = turnAbsolutePositionRad;
    inputs.turnPositionRad = turnRelativePositionRad;
    inputs.turnVelocityRadPerSec = turnSim.getAngularVelocityRadPerSec();
    inputs.turnAppliedVolts = turnAppliedVolts;
    inputs.turnCurrentAmps = new double[] {Math.abs(turnSim.getCurrentDrawAmps())};
    inputs.turnTempCelsius = new double[] {};
  }

  @Override
  public void setDriveVoltage(double volts) {
    driveAppliedVolts = MathUtil.clamp(volts, -12.0, 12.0);
    driveSim.setInputVoltage(driveAppliedVolts);
  }

  @Override
  public void setTurnVoltage(double volts) {
    turnAppliedVolts = MathUtil.clamp(volts, -12.0, 12.0);
    turnSim.setInputVoltage(turnAppliedVolts);
  }
}
