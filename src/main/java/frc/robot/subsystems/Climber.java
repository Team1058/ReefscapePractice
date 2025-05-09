package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase{
    public static class Config {
        public int climberMotorId;
        public boolean invertMotors;
        public double climberReverseSoftLimit;
        public double climberDeploySoftLimit;
    }

        private SparkMax climberMotor;
        private SparkMaxConfig climberConfig;
        public SparkAbsoluteEncoder climberEncoder;
    public Climber (){
        climberMotor = new SparkMax((21), MotorType.kBrushless);
        climberConfig = new SparkMaxConfig();
        climberConfig
            .smartCurrentLimit(80, 80)
            .idleMode(IdleMode.kBrake)
            .inverted(true)
            .openLoopRampRate(0.05)
            .softLimit
            .reverseSoftLimitEnabled(false)
            .forwardSoftLimitEnabled(false);
        climberConfig.limitSwitch.forwardLimitSwitchEnabled(false).reverseLimitSwitchEnabled(false);
        climberEncoder = climberMotor.getAbsoluteEncoder();
        climberMotor.configure(
            climberConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public Command getDeployCommand(DoubleSupplier motorSpeedSupplier){
        return this.runEnd(() -> {climberMotor.set(motorSpeedSupplier.getAsDouble());},() -> {climberMotor.set(0);})
        .until(() -> climberEncoder.getPosition() > 0.4)
        .withName("get Deploy Command");
    }
    public Command getPullBackCommand(DoubleSupplier motorSpeedSupplier){
        return this.runEnd(() -> {climberMotor.set(-motorSpeedSupplier.getAsDouble());},() -> {climberMotor.set(0);})
        .until(() -> climberEncoder.getPosition() < 0.1)
        .withName("Get Pull Back Command");
    }
}
