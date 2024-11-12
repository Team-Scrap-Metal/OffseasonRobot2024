// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.drive.ModuleIOInputsAutoLogged;
import frc.robot.subsystems.gyro.Gyro;

/** Add your docs here. */
public class LimeLight extends SubsystemBase {

    private ModuleIOInputsAutoLogged inputs = new ModuleIOInputsAutoLogged();
    private final Gyro gyro;


    public Rotation2d getYaw() {
        return gyro.getYaw();
           }



}



