// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.PathPlannerCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake.Intake;

public class IntakeNote extends Command {
  /** Creates a new IntakeNote. */
  private final Intake intake;

  private final double percentSpeed;

  public IntakeNote(Intake intake, double percentSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.intake = intake;
    this.percentSpeed = percentSpeed;
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intake.setIntakePercent(percentSpeed);
    System.out.println("is running");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.setIntakePercent(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
