package org.simbotics.simbot2015.auton.intake;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;

public class IntakeSetSpeedIndependant extends AutonCommand {

	private double setSpeedLeft = 0.0;
	private double setSpeedRight = 0.0;
	
	public IntakeSetSpeedIndependant(double speedLeft, double speedRight) {
		super(RobotComponent.INTAKE);
		this.setSpeedLeft = speedLeft;
		this.setSpeedRight = speedRight;
	}
	
	@Override
	public boolean calculate() {
		RobotOutput.getInstance().setIntakeLeft(this.setSpeedLeft);
		RobotOutput.getInstance().setIntakeRight(this.setSpeedRight);
		return true;
	}

	@Override
	public void override() {
		// single cycle so no timeout		
	}

}
