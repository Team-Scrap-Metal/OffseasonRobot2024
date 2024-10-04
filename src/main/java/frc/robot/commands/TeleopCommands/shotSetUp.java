// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.TeleopCommands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.shooter.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class shotSetUp extends SequentialCommandGroup {
  /** Creates a new shotSetUp. */
  public shotSetUp(Intake intake, Shooter shoot) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        Commands.runOnce(
            () -> {
              intake.setIntakePercent(-0.5);
            },
            intake),
        new WaitCommand(0.2),
        Commands.runOnce(
            () -> {
              shoot.setBothSetpoint(4000, 3000);
              intake.setIntakePercent(0);
            },
            shoot));
  }
}
