// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.Shooter;

public class Shoot extends Command {
  /** Creates a new Shoot. */
  private Shooter shoot;

  private double leftRPM;
  private double rightRPM;

  public Shoot(Shooter shoot, double leftRPM, double rightRPM) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.shoot = shoot;
    this.leftRPM = leftRPM;
    this.rightRPM = rightRPM;
    addRequirements(shoot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shoot.setBothSetpoint(leftRPM, rightRPM);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shoot.setBothSetpoint(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
