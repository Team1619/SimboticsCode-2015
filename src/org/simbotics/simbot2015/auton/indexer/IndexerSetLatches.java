package org.simbotics.simbot2015.auton.indexer;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.RobotOutput;

public class IndexerSetLatches extends AutonCommand {

	private boolean setLatchOpen;
	
	public IndexerSetLatches(boolean open) {
		super(RobotComponent.INDEXER);
		this.setLatchOpen = open;
	}
	
	@Override
	public boolean calculate() {
		RobotOutput.getInstance().setIndexerOpen(this.setLatchOpen);
		return true;
	}

	@Override
	public void override() {
		// nothing to do

	}

}
