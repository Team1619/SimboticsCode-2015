package org.simbotics.simbot2015.auton.canburglar;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;

public class CanburglarSetLatch extends AutonCommand {

	private boolean open;
	
	public CanburglarSetLatch(boolean open) {
		super(RobotComponent.CANBURGLAR);
		
		this.open = open;
	}
	
	@Override
	public boolean calculate() {
		RobotOutput.getInstance().setCanburglarLatch(this.open);
		return true;
	}

	@Override
	public void override() {
		// nothing to do
	}

}
