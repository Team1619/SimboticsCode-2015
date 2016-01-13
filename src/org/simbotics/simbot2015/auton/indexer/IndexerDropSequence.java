package org.simbotics.simbot2015.auton.indexer;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;
import org.simbotics.simbot2015.io.SensorInput;

public class IndexerDropSequence extends AutonCommand {

	private RobotOutput robotOut;
	private SensorInput sensorIn;
	private int currentPos;
	private int ticksPerRev =3825;
	private boolean firstCycle = true;
	
	// TODO: fill me in
	
	public IndexerDropSequence() {
		super(RobotComponent.INDEXER);
		this.robotOut = RobotOutput.getInstance();
		this.sensorIn = SensorInput.getInstance();
	}
	
	@Override
	public boolean calculate() {
		if(this.firstCycle){
			this.robotOut.setIndexerOpen(true);
			this.robotOut.setIntakeOpen(true);
			this.firstCycle = false;
		}
		
		this.currentPos = this.sensorIn.getIndexerEnc();
		double pos = this.currentPos%this.ticksPerRev;
		
		if (pos > this.ticksPerRev*0.05 && pos < this.ticksPerRev * 0.8) { // not there
			this.robotOut.setIndexerMotor(-1.0);
			return false;
		} else {
			this.robotOut.setIndexerMotor(0.0);
			return true;
		}
		
	}

	@Override
	public void override() {
		// TODO Auto-generated method stub

	}

}
