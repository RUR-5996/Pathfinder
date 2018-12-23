package org.usfirst.frc.team3630.drive; 
//All this stuff will later be in a separate method - I just had to move some stuff from modified Pathfinder class to somewhere else

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team3630.robot.Constants;
import org.usfirst.frc.team3630.robot.Robot;
import org.usfirst.frc.team3630.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.*;
import jaci.pathfinder.modifiers.TankModifier;

import java.io.File;


public class AutoDriveExecutor 
{
	EncoderFollower leftF, rightF;
	double motorOutputL, motorOutputR, gyroHeading, desiredHeading, angleDifference, turn;
	TankModifier modifier;
	Trajectory left, right;
	Trajectory.Segment seg;
	Trajectory trajectory;
	Waypoint[] waypoints;
	Trajectory.Config config;
	public AHRS ahrs;
	
	public void driveInit(double[] xCoords, double[] yCoords, double[] angles) //inits how to execute the driving part
	{
		ahrs = new AHRS(SPI.Port.kMXP);
		File file = new File("pathfinder.csv"); //creates a file with the trajectory
		createTrajectory(xCoords, yCoords, angles); //creates trajectory
		modifyTrajectory(Constants.wheelBase); //creates modified Trajectory for tank drive
		leftF = new EncoderFollower(left); //something that is comparing the encoder values to the pre-calculated values
        rightF = new EncoderFollower(right);
        
        leftF.configureEncoder(RobotMap.getTicks(RobotMap.twoL), Constants.ticksPerRotation, in2m(Constants.wheelRadius*2)); //sets all values for calculations for distance
        rightF.configureEncoder(RobotMap.getTicks(RobotMap.oneR), Constants.ticksPerRotation, in2m(Constants.wheelRadius*2));
        
        leftF.configurePIDVA(Constants.kP, Constants.kI, Constants.kD, 1/Constants.maxSpeed, 0); //initialization of PID -> play a bit with the values
        rightF.configurePIDVA(Constants.kP, Constants.kI, Constants.kD, 1/Constants.maxSpeed, 0);
        Pathfinder.writeToCSV(file, trajectory);
	}
	
	public void execute()
	{
		motorOutputL = leftF.calculate(RobotMap.twoL.getSelectedSensorPosition()); //calculates current speed of a side
        motorOutputR = rightF.calculate(RobotMap.oneR.getSelectedSensorPosition());
        gyroHeading = ahrs.getAngle();
        SmartDashboard.putNumber("degree", Robot.ahrs.getAngle());
        desiredHeading = Pathfinder.r2d(leftF.getHeading());
        angleDifference = Pathfinder.boundHalfDegrees(desiredHeading+gyroHeading); //different from the original documentation
        turn = 0.8*(-1.0/80.0)*angleDifference; //not my calculations -> something that defines the heading
        RobotMap.drive.tankDrive(motorOutputL+turn, motorOutputR-turn); //processing of the speeds in tankDrive
        System.out.println(angleDifference + "  |||||  " + desiredHeading + "  |||||  " + gyroHeading + "||||" + (desiredHeading-gyroHeading) + "|||"); //just for checking the outputs of the motors
	}
	
	public void modifyTrajectory(double wheelbase) {
        modifier = new TankModifier(trajectory); //creates modifier

        modifier.modify(wheelbase); //adjusts the modifier for our specific chassis

        left = modifier.getLeftTrajectory();
        right = modifier.getRightTrajectory(); //sets the trajectories of left and right side
    }
	
	public void createTrajectory(double[] xCoords, double[] yCoords, double[] angles) { //input is in in and degrees
        waypoints = new Waypoint[] {new Waypoint(0.0, 0.0, Pathfinder.d2r(0.0)), new Waypoint(0.6, -0.6, Pathfinder.d2r(90))}; //at least two waypoints!
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.005, 0.5, 1.5, 10.0); //cofigurates trajectory
        trajectory = Pathfinder.generate(waypoints, config); //generates trajectory
        for (int i = 0; i < trajectory.length(); i++) {
            seg = trajectory.get(i);
            
            System.out.printf("%f,%f,%f,%f,%f,%f,%f,%f\n", 
                seg.dt, seg.x, seg.y, seg.position, seg.velocity, 
                    seg.acceleration, seg.jerk, seg.heading); //prints values for each segment of the trajectory
        }
        ahrs.reset();//just to make sure that we are heading to 0 when starting
    }
	
	public static double in2m(double in) //converting from in to m -> pathfinder intakes m and radians
	{
		return in*0.0254;
	}
	
	public static double m2in(double m)
	{
		return m*39.3701;
	}
}
