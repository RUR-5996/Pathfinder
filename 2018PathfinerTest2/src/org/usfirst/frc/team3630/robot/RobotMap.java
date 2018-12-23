/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3630.robot;

import com.ctre.phoenix.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static WPI_TalonSRX oneR = new WPI_TalonSRX(4); //change these for your Talon IDs
	public static WPI_TalonSRX twoL = new WPI_TalonSRX(11);
	public static WPI_TalonSRX threeR = new WPI_TalonSRX(7);
	public static WPI_TalonSRX fourL = new WPI_TalonSRX(10);
	public static WPI_TalonSRX five = new WPI_TalonSRX(5);
	public static WPI_TalonSRX six = new WPI_TalonSRX(6);
	
	private static SpeedControllerGroup right = new SpeedControllerGroup(oneR, threeR); //we can use Master and Slave talons since we have SRX...
	private static SpeedControllerGroup left = new SpeedControllerGroup(twoL, fourL);
	
	public static DifferentialDrive drive = new DifferentialDrive(left, right); //creating only one drive -> I had some problems when creating two drives
	
	public static void configureTalon(TalonSRX talon) //basic configuration for SRX
	{
		talon.configNominalOutputForward(0, Constants.timeoutMs);
		talon.configNominalOutputReverse(0, Constants.timeoutMs);
		talon.configPeakOutputForward(1, Constants.timeoutMs);
		talon.configPeakOutputReverse(-1, Constants.timeoutMs);
		talon.configAllowableClosedloopError(0, 0, Constants.timeoutMs);
		talon.configNeutralDeadband(0.1, Constants.timeoutMs); //this is also configured in the OI
		talon.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		talon.setInverted(false);
		talon.enableCurrentLimit(true);
		talon.configContinuousCurrentLimit(30, Constants.timeoutMs);
		talon.configPeakCurrentLimit(30, Constants.timeoutMs);
		talon.configPeakCurrentDuration(200, Constants.timeoutMs);
	}
	public static int getTicks(TalonSRX talon) //returns position of encoders
	{
		return talon.getSelectedSensorPosition(0);
	}
	public static void resetEncoders() //resets encoders
	{
		RobotMap.oneR.setSelectedSensorPosition(0);
		RobotMap.twoL.setSelectedSensorPosition(0);
	}
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
