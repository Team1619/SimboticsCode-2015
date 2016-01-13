package org.simbotics.simbot2015.auton.intake;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;

public class CanGrabberSetOpen extends AutonCommand {

	private boolean setOpen;
	
	public CanGrabberSetOpen (boolean open) {
		super(RobotComponent.INTAKE);
		this.setOpen = !open;
	}
	
	@Override
	public boolean calculate() {
		RobotOutput.getInstance().setCanGrabber(this.setOpen);
		return true;
	}

	@Override
	public void override() {
		// nothing to do here
		
	}

}
