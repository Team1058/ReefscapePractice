package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Intake extends SubsystemBase{
    SparkMax intakeSparkMax;
    public Command runIntake;

    public Intake(){
        intakeSparkMax = new SparkMax(5, MotorType.kBrushless);
        runIntake = this.startEnd
        (
            () -> {
                intakeSparkMax.set(-1);
            },
            () -> {
                intakeSparkMax.set(0);
            }
        );
    }
}
