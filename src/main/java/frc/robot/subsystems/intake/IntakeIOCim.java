package frc.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants.RobotStateConstants;

/** Runs an Individual Real Intake with all Motors as Neos */
public class IntakeIOCIM implements IntakeIO {
    private final WPI_VictorSPX leftIntakeMotor;
    private final WPI_VictorSPX rightIntakeMotor;
    
    

  public IntakeIOCIM( ) {
    leftIntakeMotor = new WPI_VictorSPX(IntakeConstants.LEFT_MOTOR_ID);
    rightIntakeMotor = new WPI_VictorSPX(IntakeConstants.RIGHT_MOTOR_ID);

  }
 

  @Override
  /**
   * updates the inputs to be actual values
   *
   * @param inputs from IntakeIOInputsAutoLogged
   */
  public void updateInputs(IntakeIOInputs inputs) {
    public double intakeAppliedVolts = 0.0;
    /** Returns the position of the Propulsion Motor by how many radians it has rotated */
    public double intakePositionRad = 0.0;
    /**
     * Returns the velocity of the Propulsion Motor by how many radians per second it has rotated
     */
    public double intakeVelocityRadPerSec = 0.0;
    /**
     * Returns the absoltute value of the velocity of the Propulsion Motor by how many radians per
     * second it has rotated (Used to find Displacement)
     */
    public double intakeVelocityRadPerSecAbs = Math.abs(0.0);
    /** The Current Drawn from the Propulsion Motor in Amps */
    public double[] intakeCurrentAmps = new double[] {};
    /** The tempature of the Propulsion Motor in Celsius */
    public double[] intakeTempCelsius = new double[] {leftIntakeMotor};

  
  }}