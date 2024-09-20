package frc.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.CAN;
import frc.robot.Constants.RobotStateConstants;
import frc.robot.Constants.UnitConversions;
import frc.robot.subsystems.drive.ModuleIO.ModuleIOInputs;

public class IntakeIOCIM implements IntakeIO{
    private final WPI_VictorSPX leftVictorSPX;
    private final WPI_VictorSPX rightVictorSPX;

    public IntakeIOCIM(){
        System.out.println("[Init] Creating IntakeIOCIM");
        leftVictorSPX = new WPI_VictorSPX(IntakeConstants.LEFT_CAN_ID);
        rightVictorSPX = new WPI_VictorSPX(IntakeConstants.RIGHT_CAN_ID);
        leftVictorSPX.setExpiration(RobotStateConstants.CAN_CONFIG_TIMEOUT_SEC);
        rightVictorSPX.setExpiration(RobotStateConstants.CAN_CONFIG_TIMEOUT_SEC);

        leftVictorSPX.setInverted(IntakeConstants.LEFT_INVERTED);
        rightVictorSPX.setInverted(IntakeConstants.RIGHT_INVERTED);

        leftVictorSPX.follow(rightVictorSPX);
        leftVictorSPX.setSelectedSensorPosition(0);
        rightVictorSPX.setSelectedSensorPosition(0);
        
    leftVictorSPX.setNeutralMode(NeutralMode.Brake);
    rightVictorSPX.setNeutralMode(NeutralMode.Brake);
    }
    
    @Override
  /**
   * updates the inputs to be actual values
   *
   * @param inputs from ModuleIOInputsAutoLogged
   */
  public void updateInputs(IntakeIOInputs inputs) {
    /** Velocity of the intake Rollers in Rotations per Minute */
    inputs.velocityRPM = ((leftVictorSPX.getSelectedSensorVelocity()/IntakeConstants.MOTOR_TICS) * UnitConversions.MIN_TO_MS)/2;
    /** Number of volts being sent to the intake motor */
    inputs.appliedVolts = (leftVictorSPX.getMotorOutputVoltage() * leftVictorSPX.getBusVoltage()) + (rightVictorSPX.getMotorOutputVoltage() * rightVictorSPX.getBusVoltage());
    /** Number of Amps being used by the intake motor */
    inputs.currentAmps = new double[] {};
    /** Tempature of the intake motor */
    inputs.tempCelsius = new double[] {leftVictorSPX.getTemperature(), rightVictorSPX.getTemperature()};
  }

  @Override
  /**
   * Sets the voltage for the  Intake
   *
   * @param volts -12 to 12
   */
  public void setIntakeVoltage(double volts) {
    leftVictorSPX.setVoltage(volts);
  }

  @Override
   /**
   * Sets the Brake Mode for the  Intake
   *
   * <p>Brake means motor holds position, Coast means easy to move
   *
   * @param enable if enable, it sets brake mode, else it sets coast mode
   */
  public void setBrakeMode(boolean enable) {
    leftVictorSPX.setNeutralMode(enable ? NeutralMode.Brake : NeutralMode.Coast);
    rightVictorSPX.setNeutralMode(enable ? NeutralMode.Brake : NeutralMode.Coast);
  }
}
