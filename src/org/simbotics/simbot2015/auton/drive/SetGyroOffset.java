package org.simbotics.simbot2015.auton.drive;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;
import org.simbotics.simbot2015.io.SensorInput;
import org.simbotics.simbot2015.util.SimDriveControl;
import org.simbotics.simbot2015.util.SimPID;
import org.simbotics.simbot2015.util.Vect;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetGyroOffset extends AutonCommand {
	private SensorInput sensorIn; 
	private int offSet;
	
	
	public SetGyroOffset(int offSet){
		super(RobotComponent.DRIVE);
		this.sensorIn = SensorInput.getInstance();
		this.offSet = offSet;
	}

	
	public boolean calculate() {
		this.sensorIn.setGyroOffset(this.offSet);
		return true;
	}


	@Override
	public void override() {
		// nothing to do
		
	}
}