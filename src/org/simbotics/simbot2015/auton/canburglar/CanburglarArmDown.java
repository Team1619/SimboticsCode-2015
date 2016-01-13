package org.simbotics.simbot2015.auton.canburglar;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;

public class CanburglarArmDown extends AutonCommand {

	private long runtime;
	private long startTime;
	private boolean firstCycle = true;
	
	private RobotOutput robotOut;
	
	public CanburglarArmDown(long howLong) {
		super(RobotComponent.CANBURGLAR);
		
		this.runtime = howLong;
		this.robotOut = RobotOutput.getInstance();
	}
	
	@Override
	public boolean calculate() {
		// set start time
		if(this.firstCycle) {
			this.startTime = System.currentTimeMillis();
			this.firstCycle = false;
		}
		
		long timeElapsed = System.currentTimeMillis() - this.startTime;
		
		if(timeElapsed < this.runtime) {
			this.robotOut.setCanburglarArmReverse();
			return false;
		} else {
			this.robotOut.setCanburglarArmOff();
			return true;
		}
	}

	@Override
	public void override() {
		this.robotOut.setCanburglarArmOff();
	}

}
