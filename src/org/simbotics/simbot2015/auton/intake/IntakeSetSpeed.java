package org.simbotics.simbot2015.auton.intake;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;

public class IntakeSetSpeed extends AutonCommand {

	private double setSpeed = 0.0;
	
	public IntakeSetSpeed(double speed) {
		super(RobotComponent.INTAKE);
		this.setSpeed = speed;
	}
	
	@Override
	public boolean calculate() {
		RobotOutput.getInstance().setIntakeLeft(this.setSpeed);
		RobotOutput.getInstance().setIntakeRight(this.setSpeed);
		return true;
	}

	@Override
	public void override() {
		// single cycle so no timeout		
	}

}
