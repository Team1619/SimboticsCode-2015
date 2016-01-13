package org.simbotics.simbot2015.auton.canburglar;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;

public class CanburglarSetWinch extends AutonCommand {

	private double speed;
	
	public CanburglarSetWinch(double speed) {
		super(RobotComponent.CANBURGLAR);
		
		this.speed = speed;
	}
	
	@Override
	public boolean calculate() {
		RobotOutput.getInstance().setCanburglarWinch(speed);
		return true;
	}

	@Override
	public void override() {
		// nothing to do
	}

}
