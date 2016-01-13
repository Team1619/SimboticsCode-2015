package org.simbotics.simbot2015.auton.intake;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;

public class BootSetOut extends AutonCommand {

	private boolean setOut;
	
	public BootSetOut (boolean out) {
		super(RobotComponent.INTAKE);
		this.setOut = out;
	}
	
	@Override
	public boolean calculate() {
		RobotOutput.getInstance().setIntakeBoot(this.setOut);
		return true;
	}

	@Override
	public void override() {
		// nothing to do here
		
	}

}
