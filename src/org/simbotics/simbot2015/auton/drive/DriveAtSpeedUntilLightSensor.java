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

public class DriveAtSpeedUntilLightSensor extends AutonCommand {

	private SensorInput sensorIn; 
	private RobotOutput robotOut;
	
	private double desiredHeading;
	
	
	
	private SimDriveControl driveControl;
	
	private boolean firstCycle = false;
	
	private double speed; 
	private int hasToteCount =0;
	
	private boolean AccelerationControl = true;
	private double output=0.0; 
	private boolean goFaster = false;
	
	private SimAccelCap speedCap;
	
	public DriveAtSpeedUntilLightSensor(double speed,double heading,boolean AccelerationControl,long timeout,boolean goFaster) {
		super(RobotComponent.DRIVE, timeout);
		
		this.sensorIn = SensorInput.getInstance();
		this.robotOut = RobotOutput.getInstance();
		
		this.desiredHeading = heading;
		
		this.driveControl = new SimDriveControl();
		this.speedCap = new SimAccelCap();
		this.firstCycle = true;
		this.driveControl.setInAuto(true);
		this.speed = speed; 
		this.AccelerationControl = AccelerationControl;
		this.goFaster = goFaster; 
	}
	
	

	@Override
	public boolean calculate() {
		
		
		if(this.firstCycle) {
			this.firstCycle = false;
			this.driveControl.resetDesiredAngle();
		}
		
		
		if(this.AccelerationControl){
			if(this.goFaster){
				this.output= this.speedCap.calculate(this.speed,true);
			}else{
				this.output= this.speedCap.calculate(this.speed);
			}
		}else{
			
			this.output = this.speed;
		}
		
		
		
		this.driveControl.setAngle(this.desiredHeading);
		   
		if(this.sensorIn.hasTote()){
			this.hasToteCount++;
		}else{
			this.hasToteCount=0;
		}
        if(this.hasToteCount > 0) { // we see a tote
            // shut off motors
			this.robotOut.setDriveBack(0.0);
			this.robotOut.setDriveLeft(0.0);
			this.robotOut.setDriveRight(0.0);
			System.out.println("In Position");
			this.firstCycle = true; //FIXME: this is kinda horrible do an actual implementation
            return true;
        } else {
            // not done yet
        	this.driveControl.gyroAtSpeed(this.output);
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
