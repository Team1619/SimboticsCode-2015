package org.simbotics.simbot2015.teleop;

import org.simbotics.simbot2015.io.DriverInput;
import org.simbotics.simbot2015.io.SensorInput;
import org.simbotics.simbot2015.util.SimDriveControl;
import org.simbotics.simbot2015.util.SimDriveMode;
import org.simbotics.simbot2015.util.SimLib;
import org.simbotics.simbot2015.util.Vect;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class TeleopDrive implements TeleopComponent {

	private SensorInput sensorIn;
	private DriverInput driverIn;
		
	private SimDriveControl driveControl;
	private static TeleopDrive instance;
	
	public SimDriveMode driveMode = SimDriveMode.OUTPUT;
	
	private TeleopDrive() {
		this.driverIn = DriverInput.getInstance();
		this.sensorIn = SensorInput.getInstance();
		
		this.driveControl = new SimDriveControl();
	}
	
	public static TeleopDrive getInstance() {
		if(instance == null) {
			instance = new TeleopDrive();
		}
		return instance;
	}
	
	@Override
	public void calculate() {
		this.driveControl.setInAuto(false);  // so it doesn't reset the heading
		
		if (this.driverIn.getBackButton()) { // Reset all positional values
			this.sensorIn.reset();
			//this.sensorIn.setIndexerEnc(-1160);
			this.driveControl.resetDesiredAngle();
		}

		//receives driver inputs and creates an output vector 
		Vect translate = new Vect(this.driverIn.getDriverY(), -this.driverIn.getDriverX()); 
		double rotate = SimLib.squareMaintainSign( -driverIn.getDriverRotation());


		// set control mode - output only or gyro assist
		if (this.driverIn.getOutputButton() 
				|| sensorIn.gyroFailed()) { // switch to output if gyro is unplugged 
			
			this.driveControl.setDriveMode(SimDriveMode.OUTPUT);
			
		} else if (this.driverIn.getGyroButton()) {
			this.driveControl.resetDesiredAngle();
			this.driveControl.setDriveMode(SimDriveMode.GYRO);
			
		}

		this.driveControl.drive(translate, rotate);

	}
	
	
	
	@Override
	public void disable() {
		// TODO Turn off all drive motors here
	}
	
	
	public void reset(){
		this.driveControl.resetDesiredAngle();
		
	}
}

