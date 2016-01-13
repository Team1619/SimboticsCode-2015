package org.simbotics.simbot2015.auton.mode;


import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.AutonOverride;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.auton.drive.*;
import org.simbotics.simbot2015.auton.indexer.IndexerDropSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerLastToteSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerSetLatches;
import org.simbotics.simbot2015.auton.indexer.IndexerToteSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerWait;
import org.simbotics.simbot2015.auton.indexer.IndexerWaitForHasTote;
import org.simbotics.simbot2015.auton.intake.BootSetOut;
import org.simbotics.simbot2015.auton.intake.CanGrabberSetOpen;
import org.simbotics.simbot2015.auton.intake.IntakeSetOpen;
import org.simbotics.simbot2015.auton.intake.IntakeSetSpeed;
import org.simbotics.simbot2015.auton.util.AutonWait;
import org.simbotics.simbot2015.util.Vect;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ThreeToteStackStraightLineInZone implements AutonMode {
	private int largeEps =0;
	private int smallEps =0;
	
	@Override
	public AutonCommand[] getMode() {
		AutonBuilder ab = new AutonBuilder();
	
		this.largeEps =(int)SmartDashboard.getNumber("Error EPS Large: ");
		this.smallEps =(int)SmartDashboard.getNumber("Error EPS Small");
		
		System.out.println("Error Eps Large: "+this.largeEps);
		System.out.println("Error Eps Small: "+this.smallEps);
		
		ab.addCommand(new SetGyroOffset(180));
		
		ab.addCommand(new IndexerSetLatches(false));
		ab.addCommand(new IntakeSetOpen(false));
		ab.addCommand(new BootSetOut(false));
		ab.addCommand(new CanGrabberSetOpen(true));
		
		ab.addCommand(new IndexerToteSequence());
		
		
		
	
		
		ab.addCommand(new IntakeSetSpeed(-1));
		
		ab.addCommand(new DriveAtSpeedUntilLightSensor(0.7, 180, true, 3000, false));
		ab.addCommand(new DriveWait());
	
		ab.addCommand(new IndexerWaitForHasTote(1000));
		
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerToteSequence());
		
		
		ab.addCommand(new IntakeSetSpeed(-1));
		ab.addCommand(new DriveAtSpeedUntilLightSensor(0.5, 180,true,2500,true)); // drives until it sees 3rd tote 
		ab.addCommand(new DriveWait());
		//ab.addCommand(new DriveToPoint(new Vect(-212, -5), 180,this.smallEps,false,2000));
		//ab.addCommand(new DriveWait());
		
		//ab.addCommand(new IndexerWaitForHasTote(1000));
		
		
		ab.addCommand(new IndexerLastToteSequence()); // pickup of last tote
		//ab.addCommand(new IndexerWait()); // can remove to save time
		
		ab.addCommand(new DriveToPoint(new Vect(-220, 40), 180,this.largeEps,false,2000,false,1.0));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeed(0.0));
		
		ab.addCommand(new DriveToPoint(new Vect(-308, 134), 180,this.smallEps,false,3000,false,1.0));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeed(1.0));
		ab.addCommand(new AutonOverride(RobotComponent.INDEXER));
		ab.addCommand(new IndexerDropSequence());
		
		ab.addCommand(new IndexerWait());
	
		ab.addCommand(new AutonWait(250));
		
		//ab.addCommand(new IndexerWait()); // can remove to save time
		
		ab.addCommand(new DriveToPoint(new Vect(-220, 134), 180,this.smallEps,false,-1,false,0.8));
		ab.addCommand(new DriveWait());
		
		
		
		
		return ab.getAutonList();
		


	}

}
