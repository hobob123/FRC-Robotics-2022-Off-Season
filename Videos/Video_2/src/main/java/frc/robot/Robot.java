// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Compressor;
// import edu.wpi.first.wpilibj.DoubleSolenoid;
// import edu.wpi.first.wpilibj.PneumaticsBase;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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
  private DifferentialDrive drive = new DifferentialDrive(leftMaster, rightMaster);

  //arm
  private WPI_TalonSRX armMaster = new WPI_TalonSRX(5);
  private WPI_VictorSPX armSlave = new WPI_VictorSPX(3);

  //other subsystems
  private WPI_TalonSRX rollerMotor = new WPI_TalonSRX(4);

  //define pneumatics
  // private Compressor compressor = new Compressor(null);
  // private DoubleSolenoid hatchIntake = new DoubleSolenoid(null, 0,1);

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

    //drive deaband
    drive.setDeadband(0.05);
    // compressor.start();

    //set encoder limits on the arm motor
    armMaster.configReverseSoftLimitThreshold((int) (0/ARM_TICKS_TO_DEG),10);
    armMaster.configForwardSoftLimitThreshold((int) (175/ARM_TICKS_TO_DEG),10);

    armMaster.configReverseSoftLimitEnable(true,10);
    armMaster.configForwardSoftLimitEnable(true,10);

  }

  @Override
  public void robotPeriodic()
  {
    
    SmartDashboard.putNumber("Arm", armMaster.getSelectedSensorPosition()*ARM_TICKS_TO_DEG);
    SmartDashboard.putNumber("left drive", leftMaster.getSelectedSensorPosition()*DRIVE_TICKS_TO_FT);
    SmartDashboard.putNumber("right drive", rightMaster.getSelectedSensorPosition()*DRIVE_TICKS_TO_FT);

  }

  @Override
  public void autonomousInit()
  {
    enableMotors(true);
    leftMaster.setSelectedSensorPosition(0,0,10);
    rightMaster.setSelectedSensorPosition(0,0,10);
    armMaster.setSelectedSensorPosition(0,0,10);
  }

  @Override
  public void autonomousPeriodic()
  {
    double leftPos = leftMaster.getSelectedSensorPosition()*DRIVE_TICKS_TO_FT;
    double rightPos = rightMaster.getSelectedSensorPosition()*DRIVE_TICKS_TO_FT;
    double distance = (leftPos + rightPos)/2; 
    if(distance <10)
    {drive.tankDrive(0.6,0.6);}
    else
    {drive.tankDrive(0, 0);}
  }

  @Override
  public void teleopInit()
  {
    enableMotors(true);
  }

  @Override
  public void teleopPeriodic() 
  {
    double power = -driverJoystick.getRawAxis(1);
    double turn = driverJoystick.getRawAxis(4);
    // if(Math.abs(power))
    drive.arcadeDrive(power * 0.6, turn * 0.3);

    //arm control
    double armPower = -operatorJoystick.getRawAxis(1);
    if(Math.abs(armPower)<0.05)
    {
      armPower = 0;
    }
    armPower *= 0.5;
    armMaster.set(ControlMode.PercentOutput, armPower);

    double rollerPower = 0;
    if(operatorJoystick.getRawButton(1)==true)
    {rollerPower = 1;}
    else if(operatorJoystick.getRawButton(2))
    {rollerPower = -1;}

    rollerMotor.set(ControlMode.PercentOutput, rollerPower);
  }

  @Override
  public void disabledInit()
  {enableMotors(false);}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}


  private void enableMotors(boolean on)
  {
    NeutralMode mode;
    if(on)
    {mode = NeutralMode.Brake;}
    else
    {mode = NeutralMode.Coast;}

    leftMaster.setNeutralMode(mode);
    rightMaster.setNeutralMode(mode); 
    leftSlave.setNeutralMode(mode); 
    rightSlave.setNeutralMode(mode);
    //arm
    armMaster.setNeutralMode(mode);
    armSlave.setNeutralMode(mode);
    //other subsystems
    rollerMotor.setNeutralMode(mode);
  
  }


  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
