package org.simbotics.simbot2015.auton.indexer;

import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.io.SensorInput;

public class IndexerWaitForHasTote extends AutonCommand{
    private SensorInput sensorIn;
    
    public IndexerWaitForHasTote(long timeout){
        super(RobotComponent.INDEXER, timeout);
        this.sensorIn = SensorInput.getInstance();
    }

    @Override
    public boolean calculate() {
        return sensorIn.hasTote();
    }

    @Override
    public void override(){
    }
}
