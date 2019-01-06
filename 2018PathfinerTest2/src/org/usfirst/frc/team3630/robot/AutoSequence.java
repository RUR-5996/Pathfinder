package org.usfirst.frc.team3630.robot;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class AutoSequence 
{
	double[] x, y, angle;
	double[][] points;
	Waypoint[] waypoints;
	public void basicSequence()
	{
		waypoints  =new Waypoint[0];
		
		Robot.autoDriveExecutor.driveInit(waypoints);
	}
	
	public void left()
	{
		waypoints = new Waypoint[] {new Waypoint(0.0, 0.0, Pathfinder.d2r(0)), 
									new Waypoint(1.0, 1.0, Pathfinder.d2r(-90))};
		
		Robot.autoDriveExecutor.driveInit(waypoints);
	}
	
	public void right()
	{
		waypoints = new Waypoint[] {new Waypoint(0.0, 0.0, Pathfinder.d2r(0)),
									new Waypoint(1.0, -1.0, Pathfinder.d2r(90))};
		
		Robot.autoDriveExecutor.driveInit(waypoints);
	}
	
	public void straight()
	{
		waypoints = new Waypoint[] {new Waypoint(0.0, 0.0, Pathfinder.d2r(0)), 
									new Waypoint(2.0, 0.0, Pathfinder.d2r(0))};
		
		Robot.autoDriveExecutor.driveInit(waypoints);
	}
	
	public void slalom()
	{
		waypoints = new Waypoint[] {new Waypoint(0.0, 0.0, Pathfinder.d2r(0)),
									new Waypoint(1.4, -2.6,Pathfinder.d2r(0)), 
									new Waypoint(3.1,-0.48, Pathfinder.d2r(0)), 
									new Waypoint(4.73,-0.8, Pathfinder.d2r(135)), 
									new Waypoint(5.33, -1.65, Pathfinder.d2r(90))};
		
		Robot.autoDriveExecutor.driveInit(waypoints);
	}
	
	public void doNothing()
	{
		
	}
}
