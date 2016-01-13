package org.simbotics.simbot2015.auton.indexer;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;

public class IndexerOverride extends AutonCommand {

	public IndexerOverride() {
		super(RobotComponent.INDEXER);
	}
	
	@Override
	public boolean checkAndRun() {
		AutonCommand.overrideComponent(RobotComponent.INDEXER);
		return true;
	}
	
	@Override
	public boolean calculate() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void override() {
		// TODO Auto-generated method stub

	}

}
