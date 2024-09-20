package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase{
    
    private final IntakeIO io;
    private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();
    
    public Intake(IntakeIO io){
        System.out.println("[Init] Creating Intake");
        this.io = io;
    }

    @Override
      public void periodic() {
      this.updateInputs();
      Logger.processInputs("Intake", inputs);
    }
    
    /**
   * Update inputs without running the rest of the periodic logic. This is useful since these
   * updates need to be properly thread-locked.
   */
  public void updateInputs() {
    io.updateInputs(inputs);
  }

  public void setIntakeVoltage(double volts){
    io.setIntakeVoltage(volts);
  }

  public void setIntakePercent(double percent){
    io.setIntakeVoltage(percent * 12);
  }

  public double geVelocityRPM(){
    return inputs.velocityRPM;
  }

}
