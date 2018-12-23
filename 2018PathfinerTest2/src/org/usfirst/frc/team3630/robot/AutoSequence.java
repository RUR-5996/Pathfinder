package org.usfirst.frc.team3630.robot;

public class AutoSequence //not implemented in the pathfinder yet -> I will work on this later
{
	double[] x, y, angle;
	double[][] points;
	public void basicSequence()
	{
		init(1);
		x[0] = 24;
		y[0] = 24;
		angle[0] = 45;
		Robot.autoDriveExecutor.driveInit(x, y, angle);
	}
	
	public void init(int size)
	{
		x = new double[size];
		y = new double[size];
		angle = new double[size];
		points = new double[3][size];
	}
	
	public void straight()
	{
		init(2);
//		points[0] = [];
		x[0] = 0;
		x[1] = 0.6;
		y[0] = 0;
		y[0] = 0;
		angle[0] = 0;
		angle[1] = 0;
	}
}
