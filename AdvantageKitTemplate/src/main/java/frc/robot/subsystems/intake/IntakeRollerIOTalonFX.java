package frc.robot.subsystems.intake;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.util.Units;

public class IntakeRollerIOTalonFX implements IntakeRollerIO {
    private final TalonFX falcon;

    private final StatusSignal<Double> rollerVelocity;
    private final StatusSignal<Double> appliedVolts;
    private final StatusSignal<Double> currentAmps;
    
    // Param: ID for roller motor
    public IntakeRollerIOTalonFX(int ID) {
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.CurrentLimits.StatorCurrentLimit = 30;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;

        falcon = new TalonFX(id);

        falcon.getConfigurator().apply(config);

        rollerVelocity = falcon.getVelocity();
        appliedVolts = falcon.getMotorVoltage();
        currentAmps = falcon.getStatorCurrent();

        BaseStatusSignal.setUpdateFrequencyForAll(100, rollerVelocity, appliedVolts, currentAmps);

    }

    @Override
    // TODO: implement this function the update all the attributes of the IntakeRollerIOInputs object passed in
    // Params: object of IntakeRollerIOInputs 
    // Return: this function returns void
    public void updateInputs(IntakeRollerIOInputs inputs) {
      
        this.rollerVelocity = inputs.rollerVelocity;
        this.appliedVolts = inputs.appliedVolts;
        this.currentAmps = inputs.currentAmps;
    }

    @Override 
    // TODO: implement this function to set the pivot motor to move to a given velocity
    // Params: fill in the function parameters as need by the implementation above
    // Return: this function returns void
    public void setVelocity(double Velocity) {
      falcon.setVelocity(velocity);
    }

    @Override
    // TODO: implement this function to stop the pivot motor
    // Params: none
    // Return: this function returns void
    public void stop() {
       falcon.setVelocity(0);
    }

    @Override
    public void configurePID(double kP, double kI, double kD) {
        Slot0Configs config = new Slot0Configs();

        config.kP = kP;
        config.kI = kI;
        config.kD = kD;

        falcon.getConfigurator().apply(config);
    }
}
