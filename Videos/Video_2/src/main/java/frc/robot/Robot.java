// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  //drivetrain
  private WPI_TalonSRX leftMaster = new WPI_TalonSRX(3); // Argument is device id
  private WPI_TalonSRX rightMaster = new WPI_TalonSRX(1);
  private WPI_VictorSPX leftSlave = new WPI_VictorSPX(1);
  private WPI_VictorSPX rightSlave = new WPI_VictorSPX(2);

  //arm
  private WPI_TalonSRX armMaster = new WPI_TalonSRX(5);
  private WPI_VictorSPX armSlave = new WPI_VictorSPX(3);

  //other subsystems
  private WPI_TalonSRX rollerMotor = new WPI_TalonSRX(4);

  //define pneumatics
  private Compressor compressor = new Compressor(null);
  private DoubleSolenoid hatchIntake = new DoubleSolenoid(null, 0,1);

  //joystick declaration
  private Joystick driverJoystick = new Joystick(0);
  private Joystick operatorJoystick = new Joystick(1);

  private final double DRIVE_TICKS_TO_FT = 1.0/4096 * 6 * Math.PI/12;
  private final double ARM_TICKS_TO_DEG = 360/512 * 26/42 * 18/60 * 18/84  ;
  @Override
  public void robotInit()
  {
    //init motors
    leftMaster.setInverted(true);
    rightMaster.setInverted(true);
    armMaster.setInverted(false);

    leftSlave.follow(leftMaster);
    rightSlave.follow(rightMaster);
    armSlave.follow(armMaster);

    leftSlave.setInverted(InvertType.FollowMaster);
    rightSlave.setInverted(InvertType.FollowMaster);
    armSlave.setInverted(InvertType.FollowMaster);

    //init encoders
    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,10);
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,10);
    armMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,10);

    leftMaster.setSensorPhase(false);
    rightMaster.setSensorPhase(false);
    armMaster.setSensorPhase(false);

    //reset encoders
    leftMaster.setSelectedSensorPosition(0,0,10);
    rightMaster.setSelectedSensorPosition(0,0,10);
    armMaster.setSelectedSensorPosition(0,0,10);

    compressor.start();
  }

  @Override
  public void robotPeriodic()
  {
    
    SmartDashboard.putNumber("Arm", armMaster.getSelectedSensorPosition()*ARM_TICKS_TO_DEG);
    SmartDashboard.putNumber("left drive", leftMaster.getSelectedSensorPosition()*DRIVE_TICKS_TO_FT);
    SmartDashboard.putNumber("right drive", rightMaster.getSelectedSensorPosition()*DRIVE_TICKS_TO_FT);

  }

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {}

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
