package org.simbotics.simbot2015.auton.indexer;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;
import org.simbotics.simbot2015.io.SensorInput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IndexerCanSequence extends AutonCommand {

	private RobotOutput robotOut;
	private SensorInput sensorIn;
	private double goal =0; 
	private int currentPos;
	private int ticksPerRev =3825;
	
	public IndexerCanSequence() {
		super(RobotComponent.INDEXER);
		this.robotOut = RobotOutput.getInstance();
		this.sensorIn = SensorInput.getInstance();
	}
	
	@Override
	public boolean calculate(){
		this.currentPos = this.sensorIn.getIndexerEnc();
		
		if (this.goal == 0) {
			this.goal = Math.ceil((((double)this.currentPos+1180)/this.ticksPerRev)) * this.ticksPerRev;
		}
		double pos = this.currentPos - this.goal + this.ticksPerRev; 
		if(this.currentPos < this.goal){ // not there
			if(pos > 2000){ // within slow down range
				this.robotOut.setIndexerMotor(1);
				this.robotOut.setIndexerOpen(false);
				this.robotOut.setIntakeOpen(false);
				return false;
			}else{ // far away
				this.robotOut.setIndexerMotor(1);
				return false;
			}
		} else{
			
			this.robotOut.setIndexerMotor(0.0);
			return true;
		}
		
	}

	@Override
	public void override() {
		this.robotOut.setIndexerMotor(0.0);
	}

}
