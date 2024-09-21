// Copyright 2021-2024 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.RobotStateConstants;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOKrakenNeo;
import frc.robot.subsystems.drive.ModuleIOSimNeoCIM;
import frc.robot.subsystems.gyro.Gyro;
import frc.robot.subsystems.gyro.GyroIO;
import frc.robot.subsystems.gyro.GyroIOPigeon;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.IntakeIO;
import frc.robot.subsystems.intake.IntakeIOCIM;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.ShooterIO;
import frc.robot.subsystems.shooter.ShooterIONEO;
import frc.robot.utils.PoseEstimator;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  private final Drive m_driveSubsystem;
  private final Gyro m_gyroSubsystem;
  private final Shooter m_shooterSubsystem;
  private final Intake m_intakeSubsystem;
  private final PoseEstimator m_poseEstimator;
  // Controller
  private final CommandXboxController driverController =
      new CommandXboxController(OperatorConstants.DRIVER_PORT);
  private final CommandXboxController auxController =
      new CommandXboxController(OperatorConstants.AUX_PORT);

  // Dashboard inputs
  // private final LoggedDashboardChooser<Command> autoChooser;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    switch (RobotStateConstants.getMode()) {
      case REAL:
        // Real robot, instantiate hardware IO implementations
        m_gyroSubsystem = new Gyro(new GyroIOPigeon());
        m_driveSubsystem =
            new Drive(
                new ModuleIOKrakenNeo(0),
                new ModuleIOKrakenNeo(1),
                new ModuleIOKrakenNeo(2),
                new ModuleIOKrakenNeo(3),
                m_gyroSubsystem);
        m_shooterSubsystem = new Shooter(new ShooterIONEO());
        m_intakeSubsystem = new Intake(new IntakeIOCIM());

        break;

      case SIM:
        // Sim robot, instantiate physics sim IO implementations
        m_gyroSubsystem = new Gyro(new GyroIO() {});
        m_driveSubsystem =
            new Drive(
                new ModuleIOSimNeoCIM(),
                new ModuleIOSimNeoCIM(),
                new ModuleIOSimNeoCIM(),
                new ModuleIOSimNeoCIM(),
                m_gyroSubsystem);

        m_shooterSubsystem = new Shooter(new ShooterIO() {});
        m_intakeSubsystem = new Intake(new IntakeIO() {});
        break;

      default:
        // Replayed robot, disable IO implementations
        m_gyroSubsystem = new Gyro(new GyroIO() {});
        m_driveSubsystem =
            new Drive(
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                m_gyroSubsystem);

        m_shooterSubsystem = new Shooter(new ShooterIO() {});
        m_intakeSubsystem = new Intake(new IntakeIO() {});
        break;
    }

    m_poseEstimator = new PoseEstimator(m_driveSubsystem, m_gyroSubsystem);
    // Configure the button bindings
    configureDriverButtonBindings();
    configureAuxButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureDriverButtonBindings() {
    /** Driver Controls */

    // Driving the robot
    m_driveSubsystem.setDefaultCommand(
        new RunCommand(
            () ->
                m_driveSubsystem.driveWithDeadband(
                    driverController.getLeftX() * 1, // Forward/backward
                    -driverController.getLeftY()
                        * 1, // Left/Right (multiply by -1 bc controller axis is inverted)
                    driverController.getRightX() * (1)), // Rotate chassis left/right
            m_driveSubsystem));

    // Resets robot heading to be wherever the front of the robot is facing
    driverController
        .a()
        .onTrue(new InstantCommand(() -> m_driveSubsystem.updateHeading(), m_driveSubsystem));

    driverController
        .leftBumper()
        .onTrue(
            new InstantCommand(() -> m_intakeSubsystem.setIntakePercent(0.5), m_intakeSubsystem))
        .onFalse(
            new InstantCommand(() -> m_intakeSubsystem.setIntakeVoltage(0), m_intakeSubsystem));
    driverController
        .rightBumper()
        .onTrue(
            new InstantCommand(() -> m_intakeSubsystem.setIntakePercent(-0.5), m_intakeSubsystem))
        .onFalse(
            new InstantCommand(() -> m_intakeSubsystem.setIntakeVoltage(0), m_intakeSubsystem));
  }

  private void configureAuxButtonBindings() {
    /** Aux Controls */
    auxController
        .leftBumper()
        .onTrue(
            new InstantCommand(() -> m_intakeSubsystem.setIntakePercent(0.5), m_intakeSubsystem))
        .onFalse(
            new InstantCommand(() -> m_intakeSubsystem.setIntakeVoltage(0), m_intakeSubsystem));
    auxController
        .rightBumper()
        .onTrue(
            new InstantCommand(() -> m_intakeSubsystem.setIntakePercent(-0.5), m_intakeSubsystem))
        .onFalse(
            new InstantCommand(() -> m_intakeSubsystem.setIntakeVoltage(0), m_intakeSubsystem));

    auxController
        .leftTrigger()
        .onTrue(
            new InstantCommand(
                () -> m_shooterSubsystem.setBothSetpoint(3000, 4000), m_shooterSubsystem))
        .onFalse(
            new InstantCommand(() -> m_shooterSubsystem.setBothSetpoint(0, 0), m_shooterSubsystem));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
    // autoChooser.get();
  }
}
