// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.LimelightTestCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drive.Drive;
import frc.robot.utils.LimelightHelpers;
import frc.robot.utils.LimelightHelpers.LimelightResults;

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
    drive.setRaw(0,0,0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(LimelightHelpers.getTX("limelight") < -0.5){
      rot = 2;
    } else if (LimelightHelpers.getTX("limelight") > 0.5){
      rot = -2;
    } else {
      rot = 0;
    double distanceX = LimelightHelpers.getTargetPose3d_RobotSpace("limelight").getX();
    double distanceY = LimelightHelpers.getTargetPose3d_RobotSpace("limelight").getY();
    
    if(distanceX > 0.5){
      x = 2;
    } else if (distanceX < -0.5){
      x = -2;
    } else {
      x = 0;
    }

    if(distanceY > 0.5){
      y = 2;
    } else if (distanceY < -0.5){
      y = -2;
    } else {
      y = 0;
    }
    }

    

    drive.setRaw(x, y, rot);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.setRaw(0,0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
