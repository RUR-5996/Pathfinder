package org.usfirst.frc.team3630.drive;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import org.usfirst.frc.team3630.robot.Constants;
import org.usfirst.frc.team3630.robot.Robot;
import org.usfirst.frc.team3630.robot.RobotMap;
import java.util.ArrayList;
import java.util.List;

public class TeleopDriveExecutor 
{	
	boolean getting, end;
	List<Double> speeds = new ArrayList<Double>();
	List<Double> times = new ArrayList<Double>();
	public double subtotal, velocity, currentSpeed, time;
	public TeleopDriveExecutor()
	{
		getting = false;
	}
	
	public void execute()
	{		
			RobotMap.drive.tankDrive(0.7*Robot.oi.getLeftY(), 0.7*Robot.oi.getRightY());
			getMaxVelocity();
	}
	
	public void getMaxVelocity()
	{
		if(Robot.oi.backB.get())
		{
			end = true;
			getting = false;
			Robot.timer.stop();
		}
		else if(Robot.oi.startB.get()) 
		{
			getting = true;
			end = false;
			Robot.timer.start();
			RobotMap.oneR.setSelectedSensorPosition(0);
			
		}
		if(getting)
		{
			time = Robot.timer.get();
			calculate();
		}
		if(end)
		{
			subtotal = 0;
			for(int i = 0; i<speeds.size(); i++)
			{
				subtotal+=speeds.get(i);
			}
			velocity = subtotal/(double)(speeds.size());
			System.out.println("max speed is: " + velocity);
			end = false;
		}
	}
	
	public void calculate()
	{
		double mPR = 2*Math.PI*AutoDriveExecutor.in2m(Constants.wheelRadius);
		double dist = mPR*(RobotMap.getTicks(RobotMap.oneR)/Constants.ticksPerRotation);
		currentSpeed = dist/time;
		System.out.println(currentSpeed);
		speeds.add(currentSpeed);
	}
}
