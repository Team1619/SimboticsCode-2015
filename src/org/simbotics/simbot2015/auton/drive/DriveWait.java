package org.simbotics.simbot2015.auton.drive;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;

public class DriveWait extends AutonCommand {
	
	public DriveWait() {
		super(RobotComponent.DRIVE);
	}
	
	public boolean calculate() {
		return true;
	}

	@Override
	public void override() {
		// nothing to do
		
	}

}
