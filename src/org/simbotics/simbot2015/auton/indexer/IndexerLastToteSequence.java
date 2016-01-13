package org.simbotics.simbot2015.auton.indexer;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;
import org.simbotics.simbot2015.io.SensorInput;

public class IndexerLastToteSequence extends AutonCommand {

	private RobotOutput robotOut;
	private SensorInput sensorIn;
	
	private int currentPos;
	private int ticksPerRev = 3825;
	private double indexerMultiplier =0.2; 
	
	
	// TODO: fill me in
	
	public IndexerLastToteSequence() {
		super(RobotComponent.INDEXER);
		this.robotOut = RobotOutput.getInstance();
		this.sensorIn = SensorInput.getInstance();
	}
	
	@Override
	public boolean calculate() {
		this.currentPos = this.sensorIn.getIndexerEnc();
		double pos = this.currentPos%this.ticksPerRev;
		
		if(this.sensorIn.hasTote()){
			if (pos < this.ticksPerRev*this.indexerMultiplier) { // not there
				this.robotOut.setIndexerMotor(1);
				return false;
			} else if (pos < 1100) {
				this.robotOut.setIndexerMotor(0.6);
				return false;
			} else {
				this.robotOut.setIndexerMotor(0.0);
				return true;
				
			}
		}else{
			this.robotOut.setIndexerMotor(0.0);
			return false;
		}
	
		
	}

	@Override
	public void override() {
		this.robotOut.setIndexerMotor(0.0);

	}

}
