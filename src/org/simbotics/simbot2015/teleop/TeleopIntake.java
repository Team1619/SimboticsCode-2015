package org.simbotics.simbot2015.teleop;

import org.simbotics.simbot2015.io.DriverInput;
import org.simbotics.simbot2015.io.RobotOutput;
import org.simbotics.simbot2015.io.SensorInput;

public class TeleopIntake implements TeleopComponent {
	private SensorInput sensorIn;
	private DriverInput driverIn;
	private RobotOutput robotOut; 
	private static TeleopIntake instance;
	private boolean  canTriggerFlag =false;
	
	
	public static TeleopIntake getInstance() {
		if(instance == null) {
			instance = new TeleopIntake();
		}
		return instance;
	}
	
	public TeleopIntake(){
		this.sensorIn = SensorInput.getInstance();
		this.driverIn = DriverInput.getInstance();
		this.sensorIn = SensorInput.getInstance();
		this.robotOut = RobotOutput.getInstance();
		 
	}
	
	
	@Override
	public void calculate() {
		
		
		
		
		
		if(this.driverIn.getCanIndexButton() || this.driverIn.getNoodleIndexButton()){
			this.robotOut.setCanGrabber(true);
			this.robotOut.setIntakeBoot(false);
			
		}else if(this.driverIn.getDropStackTrigger() > 0.3){
			this.robotOut.setIntakeLeft(1);
			this.robotOut.setIntakeRight(1);
			this.robotOut.setIntakeBoot(false);
			this.canTriggerFlag = false;
		}else if(this.driverIn.getIntakeToteButton()){
			this.robotOut.setIntakeLeft(-1);
			this.robotOut.setIntakeRight(-1);
			this.robotOut.setIntakeBoot(false);
			this.robotOut.setIntakeOpen(false);
			this.canTriggerFlag = false;
		}else if(this.driverIn.getIntakeCanButton()){
			//this.robotOut.setIntakeLeft(-1); // bye bye gavin
			//this.robotOut.setIntakeRight(-1);
			this.robotOut.setIntakeBoot(true);
			if(this.driverIn.getBootInButton()){
				this.robotOut.setIntakeOpen(false);
			}else{
				this.robotOut.setIntakeOpen(true);
			}
			
			this.robotOut.setCanGrabber(false);
			this.canTriggerFlag = false;
		}else if(this.driverIn.getOuttakeToteButton()){
			this.robotOut.setIntakeLeft(1);
			this.robotOut.setIntakeRight(1);
			this.canTriggerFlag = false;
		}else if(this.driverIn.getIntakeFromStepTrigger() > 0.3){
			this.robotOut.setIntakeLeft(-1.0);
			this.robotOut.setIntakeRight(-1.0);
			if(!this.canTriggerFlag){
				this.robotOut.setIntakeBoot(false);
				this.robotOut.setIntakeOpen(false);
				this.robotOut.setIndexerOpen(true);
				if(!this.driverIn.getCanGrabberCloseButton()){
					this.robotOut.setCanGrabber(false);
				}
				this.canTriggerFlag= true;
			}
			
		}else if(this.driverIn.getJumpButton()){
			this.robotOut.setIntakeLeft(-1.0);
			this.robotOut.setIntakeRight(-1.0);
			this.robotOut.setIntakeBoot(false);
			this.robotOut.setIntakeOpen(false);
			this.robotOut.setCanGrabber(true);
			
		}else{
			this.robotOut.setIntakeLeft(0);
			this.robotOut.setIntakeRight(0);
		}
		
		if(this.driverIn.getCanGrabberCloseButton()){
			this.robotOut.setCanGrabber(true);
		}else if(this.driverIn.getCanGrabberOpenButton()){
			this.robotOut.setCanGrabber(false);
		}
	
		
		
		if(Math.abs(this.driverIn.getIntakeX()) > 0.2 || Math.abs(this.driverIn.getIntakeY()) > 0.2){
			this.robotOut.setIntakeLeft(this.driverIn.getIntakeY()+this.driverIn.getIntakeX());
			this.robotOut.setIntakeRight(this.driverIn.getIntakeY()-this.driverIn.getIntakeX());
		}
		
		if(this.driverIn.getIntakeCloseButton()){
			this.robotOut.setIntakeOpen(false);
		}
		
	}

	@Override
	public void disable() {
		
		
	}

}
