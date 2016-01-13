package org.simbotics.simbot2015.auton.intake;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;

public class IntakeSetOpen extends AutonCommand {

	private boolean setOpen;
	
	public IntakeSetOpen(boolean open) {
		super(RobotComponent.INTAKE);
		this.setOpen = open;
	}
	
	@Override
	public boolean calculate() {
		RobotOutput.getInstance().setIntakeOpen(this.setOpen);
		return true;
	}

	@Override
	public void override() {
		// nothing to do here
		
	}

}
