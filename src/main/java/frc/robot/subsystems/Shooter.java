package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Shooter extends SubsystemBase{
    SparkMax shooterSparkMax;
    public Command runShooter;
    public Command pullBackUntilInSensorTripped;
    public Command runShooterWExit;

    public Shooter(){
        shooterSparkMax = new SparkMax(12, MotorType.kBrushless);
        runShooter = this.startEnd
        (
            () -> {
                shooterSparkMax.set(-.35);
            },
            () -> {
                shooterSparkMax.set(0);
            }
        );
        pullBackUntilInSensorTripped = this.startEnd
        (
            () -> {shooterSparkMax.set(0.3);
            },
            () -> {
                shooterSparkMax.set(0);
            }
            )
            .until(this::isInLimitSensorTripped
        );
        runShooterWExit = this.startEnd(
            () -> shooterSparkMax.set(-0.3), ()-> shooterSparkMax.set(0)
            ).until(this::isOutLimitSensorTripped);
        
    }

    public boolean isOutLimitSensorTripped () {
        return shooterSparkMax.getReverseLimitSwitch().isPressed();
    }

    public boolean isInLimitSensorTripped () {
        return shooterSparkMax.getForwardLimitSwitch().isPressed();
    }
}
