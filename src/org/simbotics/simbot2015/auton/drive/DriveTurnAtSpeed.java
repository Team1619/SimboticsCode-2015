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

public class DriveTurnAtSpeed extends AutonCommand {

	private SensorInput sensorIn; 
	private RobotOutput robotOut;
	
	private double desiredHeading;
	

	
	private boolean firstCycle = false;
	

	private boolean turningRight = false;
	private double speed =0.0;
	
	
	
	public DriveTurnAtSpeed(double speed, double heading,long timeout) {
		super(RobotComponent.DRIVE, timeout);
		
		this.sensorIn = SensorInput.getInstance();
		this.robotOut = RobotOutput.getInstance();
		
		this.desiredHeading = heading;
		
		this.speed = speed;
		this.firstCycle = true;
		
		

	}
	
	
	
	public DriveTurnAtSpeed(double speed, double heading){
		this(speed,heading,-1);
	}
	

	@Override
	public boolean calculate() {
		
		if(this.firstCycle) {
			
			if(this.desiredHeading < this.sensorIn.getAngle()){
				this.turningRight = true;// turning right
			}else{
				this.turningRight = false; // turning left
			}
			System.out.println("STaRTING TURN");
			this.firstCycle = false;
			
		}
		
		
		
		if(this.turningRight){   
			if(this.sensorIn.getAngle() < this.desiredHeading) { // 
				
				this.robotOut.setDriveBack(-this.speed);
				this.robotOut.setDriveLeft(-this.speed);
				this.robotOut.setDriveRight(-this.speed);
				System.out.println("ON ANGLE");
			
				return false;
			}else{
        		this.robotOut.setDriveBack(0.0);
        		this.robotOut.setDriveLeft(0.0);
        		this.robotOut.setDriveRight(0.0);	
        		return true;
			}
			
        } else { // turing left
        	if(this.sensorIn.getAngle() > this.desiredHeading) { // 
				
				this.robotOut.setDriveBack(this.speed);
				this.robotOut.setDriveLeft(this.speed);
				this.robotOut.setDriveRight(this.speed);
				System.out.println("ON ANGLE");
			
				return false;
			}else{
        		this.robotOut.setDriveBack(0.0);
        		this.robotOut.setDriveLeft(0.0);
        		this.robotOut.setDriveRight(0.0);	
        		return true;
			}
           
        }
  	}

	@Override
	public void override() {
		this.robotOut.setDriveBack(0.0);
		this.robotOut.setDriveLeft(0.0);
		this.robotOut.setDriveRight(0.0);	
	}
}