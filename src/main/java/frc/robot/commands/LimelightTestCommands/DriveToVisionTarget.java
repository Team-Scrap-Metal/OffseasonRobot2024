// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.LimelightTestCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drive.Drive;

public class DriveToVisionTarget extends Command {
  /** Creates a new DriveToVisionTarget. */
  private Drive drive;
  private double distance;
  public DriveToVisionTarget(Drive drive, double distance) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drive = drive;
    this.distance = distance;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.setRaw(0,0,0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
