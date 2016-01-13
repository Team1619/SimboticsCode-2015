package org.simbotics.simbot2015.auton.indexer;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;
import org.simbotics.simbot2015.io.SensorInput;

public class IndexerFullDropSequence extends AutonCommand {

	private RobotOutput robotOut;
	private SensorInput sensorIn;
	private int currentPos;
	private int ticksPerRev =3825;
	private boolean firstCycle = true;
	private int liftUpHeight = 1350; 
	private int slowDropVal = 1250; 
	private boolean hasLiftedStack = false;
	private boolean slowDownRangeFlag = false;
	private boolean hasSeenHooksFlag = false;
	// TODO: fill me in
	
	public IndexerFullDropSequence() {
		super(RobotComponent.INDEXER);
		this.robotOut = RobotOutput.getInstance();
		this.sensorIn = SensorInput.getInstance();
	}
	
	@Override
	public boolean calculate() {
		if(this.firstCycle){
			//this.robotOut.setIndexerOpen(true);
			this.robotOut.setIntakeOpen(true);
			this.firstCycle = false;
		}
		
		this.currentPos = this.sensorIn.getIndexerEnc();
		double pos = this.currentPos%this.ticksPerRev;
		
		if(pos <= this.liftUpHeight && !this.hasLiftedStack) { // we want to drop but we have not lifted it up yet
			this.robotOut.setIndexerMotor(1.0);
			return false;
		}else if(pos > this.liftUpHeight && !this.hasLiftedStack){ // we got to position now we can drop
			this.hasLiftedStack = true; 
			this.robotOut.setIndexerOpen(true);
			return false;
		}else if(pos > this.slowDropVal && this.hasLiftedStack && !this.slowDownRangeFlag){
			this.robotOut.setIndexerMotor(-0.8);
			
			return false;
		}else if (!this.sensorIn.canSeeHooks()  && (pos > 30 && pos < 1500) && !this.hasSeenHooksFlag ) { // go down until light sensor
			System.out.println("Go Down Section!");
			this.slowDownRangeFlag = true;
			this.robotOut.setIndexerMotor(-1.0);
			
			return false;
		}else if(!this.hasSeenHooksFlag){
			this.hasSeenHooksFlag =true;
			System.out.println("Has Seen Hooks");
			return false;
		}else{ // we have finished the drop routine
			this.robotOut.setIndexerMotor(0.0);
			System.out.println("Finished");
			
			return true;
			
		}
		
	}

	@Override
	public void override() {
		// TODO Auto-generated method stub

	}

}