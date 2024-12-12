// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.LimelightTestCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drive.Drive;
import frc.robot.utils.LimelightHelpers;

public class DriveToVisionTarget extends Command {
  /** Creates a new DriveToVisionTarget. */
  private Drive drive;

  private double distance;
  private double rot = 0;
  private double x = 0;
  private double y = 0;

  public DriveToVisionTarget(Drive drive, double distance) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drive = drive;
    this.distance = distance;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.setRaw(0, 0, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (LimelightHelpers.getTX("limelight") < -10) {
      rot = 2;
    } else if (LimelightHelpers.getTX("limelight") > 10) {
      rot = -2;
    } else {
      rot = 0;
      double distanceX = LimelightHelpers.getTargetPose3d_RobotSpace("limelight").getX();
      double distanceY = LimelightHelpers.getTargetPose3d_RobotSpace("limelight").getY();
      SmartDashboard.putNumber("ValueX", distanceX);
      SmartDashboard.putNumber("ValueY", distanceY);
      if (distanceX > 0.15) {
        x = 0.5;
        System.out.println("running X positive");
      } else if (distanceX < 0.06 && distanceX != 0) {
        x = -0.5;
        System.out.println("running X negative");
      } else {
        x = 0;
        System.out.println("running x 0");
      }

      if (distanceY > 0.5) {
        y = 0.5;
        System.out.println("running y positive");
      } else if (distanceY < 0.20 && distanceY != 0) {
        y = -0.5;
        System.out.println("running y negative");
      } else {
        y = 0;
        System.out.println("running y 0");
      }
    }

    drive.setRaw(x, y, rot);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.setRaw(0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
