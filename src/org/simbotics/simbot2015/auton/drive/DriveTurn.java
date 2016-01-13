package org.simbotics.simbot2015.auton.drive;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;
import org.simbotics.simbot2015.io.SensorInput;
import org.simbotics.simbot2015.util.SimAccelCap;
import org.simbotics.simbot2015.util.SimDriveControl;
import org.simbotics.simbot2015.util.SimPID;
import org.simbotics.simbot2015.util.Vect;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTurn extends AutonCommand {

	private SensorInput sensorIn; 
	private RobotOutput robotOut;
	
	private double desiredHeading;
	
	private SimPID xControl;
	private SimPID yControl;
	
	private SimDriveControl driveControl;
	
	private boolean firstCycle = false;
	
	private double pVal = 0.0;
	private double iVal = 0.0;
	private double dVal = 0.0;
	private int errorEps = 0;
	private int doneRange = 0;
	private boolean AccelerationControl = true;
	
	private SimAccelCap xCap = new SimAccelCap();
	private SimAccelCap yCap = new SimAccelCap();
	
	
	public DriveTurn(Vect point, double heading, int errorEps, boolean AccelerationControl , long timeout) {
		super(RobotComponent.DRIVE, timeout);
		
		this.sensorIn = SensorInput.getInstance();
		this.robotOut = RobotOutput.getInstance();
		
		this.desiredHeading = heading;
		this.errorEps = errorEps; 
		this.driveControl = new SimDriveControl();
		this.xControl = new SimPID();
		this.xControl.setDesiredValue(point.getX());
		this.yControl = new SimPID();
		this.yControl.setDesiredValue(point.getY());
		this.firstCycle = true;
		this.driveControl.setInAuto(true);
		
		this.AccelerationControl = AccelerationControl;
	}
	
	public DriveTurn(Vect point, double heading, int errorEps, boolean AccelerationControl) {
		this(point, heading, errorEps, AccelerationControl, -1);
	}
	
	public DriveTurn(Vect point, double heading, int errorEps, long timeout){
		this(point,heading,errorEps,true,timeout);
	}
	public DriveTurn(Vect point, double heading, int errorEps){
		this(point, heading, errorEps, true);
	}

	@Override
	public boolean calculate() {
		
		if(this.firstCycle) {
			this.driveControl.resetDesiredAngle();
			this.firstCycle = false;
			System.out.println("STaRTING TURN");
		}
		
		
		this.driveControl.setAngle(this.desiredHeading);
		   
        if(this.driveControl.PIDDone()) { // 
            // shut off motors
			this.robotOut.setDriveBack(0.0);
			this.robotOut.setDriveLeft(0.0);
			this.robotOut.setDriveRight(0.0);
			System.out.println("ON ANGLE");
			//this.firstCycle = true; //FIXME: this is kinda horrible do an actual implementation
            return true;
        } else {
            // not done yet
    		this.driveControl.drive(new Vect(0,0), 0);
    		//System.out.println("output: " + output.mag());
            return false;
        }
  	}

	@Override
	public void override() {
		this.robotOut.setDriveBack(0.0);
		this.robotOut.setDriveLeft(0.0);
		this.robotOut.setDriveRight(0.0);	
	}
}