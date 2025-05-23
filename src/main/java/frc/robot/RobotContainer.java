// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.Leds;


public class RobotContainer {
  private final Climber climber = new Climber();
  private final Intake intake = new Intake();
  private final Shooter shooter = new Shooter();
  private final Leds leds = new Leds(climber);
  

  // The robot's subsystems and commands are defined here...
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    m_driverController.a()
    .whileTrue(
      Commands.race(shooter.runShooterWExit, intake.runIntake).andThen((shooter.pullBackUntilInSensorTripped))
      //intake.runIntake.alongWith(shooter.runShooter).until(shooter::isOutLimitSensorTripped).andThen(shooter.pullBack)
    );
    shooter.isEtherLimitSensorTrippedTrigger.whileTrue(leds.runPattern(leds.greenBase));

    // m_driverController.b()
    // .whileTrue(
    //   intake.runIntake.alongWith(shooter.runShooter).until(shooter::isOutLimitSensorTripped).andThen((shooter.pullBack).until(shooter::isInLimitSensorTripped))
    // );
    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    m_driverController.rightTrigger(.1).whileTrue(climber.getDeployCommand(m_driverController::getRightTriggerAxis).alongWith(leds.runPattern(leds.climbingProgressMaskpattern)));
    m_driverController.leftTrigger(.1).whileTrue(climber.getPullBackCommand(m_driverController::getLeftTriggerAxis).alongWith(leds.runPattern(leds.climbingProgressMaskpattern)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return intake.runIntake;
  }
}
