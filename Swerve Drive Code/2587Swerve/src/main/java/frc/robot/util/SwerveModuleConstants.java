package frc.robot.util;

public class SwerveModuleConstants {
  public final int driveMotorID;
  public final int angleMotorID;
  // public final int cancoderID;
  public final int steerEncoderID;
  public final double angleOffset;

  /**
   * Swerve Module Constants to be used when creating swerve modules.
   * It is used to contain all the constants for a single module
   *
   * @param driveMotorID
   * @param angleMotorID
   * @param steerEncoderID
   * @param angleOffset
   */
  public SwerveModuleConstants(
      int driveMotorID, int angleMotorID, int steerEncoderID, double angleOffset) {
    this.driveMotorID = driveMotorID;
    this.angleMotorID = angleMotorID;
    // this.cancoderID = canCoderID;
    this.angleOffset = angleOffset;
    this.steerEncoderID = steerEncoderID;
  }
}