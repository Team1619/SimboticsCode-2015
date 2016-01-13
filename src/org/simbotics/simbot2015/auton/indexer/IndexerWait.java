package org.simbotics.simbot2015.auton.indexer;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;


public class IndexerWait extends AutonCommand {

	public IndexerWait() {
		super(RobotComponent.INDEXER);
	}
	
	@Override
	public boolean calculate() {
		return true;
	}

	@Override
	public void override() {
		// nothing
		
	}

}
