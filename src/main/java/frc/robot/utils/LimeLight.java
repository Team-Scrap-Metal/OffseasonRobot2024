// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.PoseEstimator;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.drive.ModuleIOInputsAutoLogged;
import frc.robot.subsystems.gyro.Gyro;
import frc.robot.LimelightHelpers;
import frc.robot.LimelightHelpers.PoseEstimate;

import java.lang.Math;


import org.opencv.core.Mat;

/** Add your docs here. */
public class LimeLight extends SubsystemBase {

    private ModuleIOInputsAutoLogged inputs = new ModuleIOInputsAutoLogged();
    private final Gyro gyro;

    
    private double getYaw() {
        return Math.atan2(gyro.getYaw().getCos(), gyro.getYaw().getSin());
    }
    
    private void setRobotOrientation(double Yaw) {
    double robotYaw = Yaw;

    LimelightHelpers.SetRobotOrientation("", robotYaw, 0.0, 0.0, 0.0, 0.0, 0.0);

    LimelightHelpers.PoseEstimate limelightMeasurement = LimelightHelpers.getBotPoseEstimate_wpiBlue("");

    PoseEstimator.(VecBuilder.fill(.5, .5, 9999999));
    }





}



