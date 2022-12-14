// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import frc.robot.util.SwerveModuleConstants;


/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants 
{
    public static final class Swerve {
        public static final double stickDeadband = 0.1;
    
        public static final boolean invertGyro = false; // Always ensure Gyro is CCW+ CW-
    
        /* Drivetrain Constants */
        public static final double trackWidth = Units.inchesToMeters(20);
        public static final double wheelBase = Units.inchesToMeters(20);
        public static final double wheelDiameter = Units.inchesToMeters(4.0);
        public static final double wheelCircumference = wheelDiameter * Math.PI;
    
        public static final double openLoopRamp = 0.25;
        public static final double closedLoopRamp = 0.0;
    
        public static final double driveGearRatio = (6.75 / 1.0);
        public static final double angleGearRatio = ((150/7) / 1.0);
    
        public static final SwerveDriveKinematics swerveKinematics =
            new SwerveDriveKinematics(
              new Translation2d(wheelBase / 2.0, trackWidth / 2.0),
              new Translation2d(wheelBase / 2.0, -trackWidth / 2.0),
              new Translation2d(-wheelBase / 2.0, trackWidth / 2.0),
              new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0));
    
        /* Swerve Compensation */
        public static final double voltageComp = 12.0;
    
        /* Swerve Current Limiting */
        public static final int angleContinuousCurrentLimit = 20;
        public static final int driveContinuousCurrentLimit = 80;
    
        /* Angle Motor PID Values */
        public static final double angleKP = 0.015;
        public static final double angleKI = 0.0;
        public static final double angleKD = 0.005;
        public static final double angleKFF = 0.0;
    
        /* Drive Motor PID Values */
        public static final double driveKP = 0.0;
        public static final double driveKI = 0.0;
        public static final double driveKD = 0.0;
        public static final double driveKFF = 0.0;
    
        /* Drive Motor Characterization Values */
        public static final double driveKS = 0.667;
        public static final double driveKV = 2.44;
        public static final double driveKA = 0.27;
    
        /* Drive Motor Conversion Factors */
        public static final double driveConversionVelocityFactor = ((wheelDiameter * Math.PI) / driveGearRatio) / 60.0;
        public static final double angleConversionFactor = 360.0 / 12.8;
    
        /* Swerve Profiling Values */
        public static final double maxSpeed = 4.5; // meters per second
        public static final double maxAngularVelocity = 11.5;
    
        /* Neutral Modes */
        public static final IdleMode angleNeutralMode = IdleMode.kBrake;
        public static final IdleMode driveNeutralMode = IdleMode.kBrake;
    
        /* Motor Inverts */
        public static final boolean driveInvert = false;
        public static final boolean angleInvert = false;
    
        /* Angle Encoder Invert */
        public static final boolean canCoderInvert = false;
    
        /* Module Specific Constants */
        /* Front Right Module - Module 0 */
        public static final class Mod0 {
          public static final int driveMotorID = 1;
          public static final int angleMotorID = 2;
          // public static final int canCoderID = 1;
          public static final int steerEncoderID = 0;
          public static final double angleOffset = 237.77;
          public static final SwerveModuleConstants constants =
              new SwerveModuleConstants(driveMotorID, angleMotorID, steerEncoderID, angleOffset);
        }
    
        /* Front Left Module - Module 1 */
        public static final class Mod1 {
          public static final int driveMotorID = 11;
          public static final int angleMotorID = 10;
          // public static final int canCoderID = 2;
          public static final int steerEncoderID = 1;
          public static final double angleOffset = -5.46;
          public static final SwerveModuleConstants constants =
              new SwerveModuleConstants(driveMotorID, angleMotorID, steerEncoderID, angleOffset);
        }
    
        /* Back Left Module - Module 2 */
        public static final class Mod2 {
          public static final int driveMotorID = 20;
          public static final int angleMotorID = 21;
          // public static final int canCoderID = 3;
          public static final int steerEncoderID = 2;
          public static final double angleOffset = 256.32;
          public static final SwerveModuleConstants constants =
              new SwerveModuleConstants(driveMotorID, angleMotorID, steerEncoderID, angleOffset);
        }
    
        /* Back Right Module - Module 3 */
        public static final class Mod3 {
          public static final int driveMotorID = 31;
          public static final int angleMotorID = 30;
          // public static final int canCoderID = 4;
          public static final int steerEncoderID = 3;
          public static final double angleOffset = 191.97;
          public static final SwerveModuleConstants constants =
              new SwerveModuleConstants(driveMotorID, angleMotorID, steerEncoderID, angleOffset);
        }
      }
}
