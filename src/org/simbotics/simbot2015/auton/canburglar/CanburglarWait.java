package org.simbotics.simbot2015.auton.canburglar;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;

public class CanburglarWait extends AutonCommand {

	public CanburglarWait() {
		super(RobotComponent.CANBURGLAR);
	}
	
	@Override
	public boolean calculate() {
		return true;
	}

	@Override
	public void override() {
		// nothing to do
	}

}
