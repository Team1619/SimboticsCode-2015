package org.simbotics.simbot2015.auton.indexer;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;
import org.simbotics.simbot2015.io.SensorInput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IndexerToteSequence extends AutonCommand {

	private RobotOutput robotOut;
	private SensorInput sensorIn;
	
	private int currentPos;
	private int ticksPerRev = 3825;
	private int goal =0;
	private double output =0;
	
	// TODO: fill me in
	
	public IndexerToteSequence(double speed) {
		super(RobotComponent.INDEXER);
		this.robotOut = RobotOutput.getInstance();
		this.sensorIn = SensorInput.getInstance();
		this.output = speed;
	}
	
	public IndexerToteSequence() {
		super(RobotComponent.INDEXER);
		this.robotOut = RobotOutput.getInstance();
		this.sensorIn = SensorInput.getInstance();
		this.output = 0.7;
	}
	
	@Override
	public boolean calculate() {
		
		this.currentPos = this.sensorIn.getIndexerEnc();
	
		
		if(this.goal ==0){
			this.goal = (int) (Math.ceil(((double)this.currentPos/this.ticksPerRev)+0.05)*this.ticksPerRev);
			
		}
		if(this.currentPos < this.goal){
			this.robotOut.setIndexerMotor(this.output);
			return false;
		}else{
			System.out.print("Finished!");
			this.robotOut.setIndexerMotor(0.0);
			return true;
		}
		
	}
		


	@Override
	public void override() {
		this.robotOut.setIndexerMotor(0.0);

	}

}
