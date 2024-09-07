package frc.robot.subsystems.drive;

import java.util.Optional;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import frc.robot.Constants.RobotStateConstants;

public class ModuleIONeoCIM implements ModuleIO {
    private final CANSparkMax driveSparkMax;
    private final WPI_VictorSPX turnVictorSPX;

    private final RelativeEncoder driveRelativeEncoder;
    private final CANcoder turnAbsoluteEncoder;

    private final boolean isTurnMotorInverted = true;
    private final double absoluteEncoderOffset;
    private final int  swerveModuleNumber;

}
public ModuleIONeoCIM(int index) {
    this.swerveModuleNumber = index;
    System.out.println("[Init]")
}
