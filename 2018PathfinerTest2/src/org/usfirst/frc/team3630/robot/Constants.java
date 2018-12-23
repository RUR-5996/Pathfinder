package org.usfirst.frc.team3630.robot;

public class Constants //used all over the code
{
	public static final int timeoutMs = 10;
	
	//gyro
	public static boolean turnToAngle;
	public static final double kP = 0.5;
	public static final double kI = 0.0;
	public static final double kD = 0.0;
	public static final double kF = 0.0;
	public static final double maxSpeed = 0.5; //in m/s
	
	public static final double kTolarenceAngles = 2f;

	public static final double kToleranceIn = 2;
	
	public static final double wheelRadius = 3;
	public static final int ticksPerRotation = 330;
	
	public static final double smoothRotationSpeed = 0.3;
	
	public static final double initAutoCase = 1;
	
	public static final double wheelBase = 0.78; //in meters
}
