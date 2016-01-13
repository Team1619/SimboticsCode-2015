package org.simbotics.simbot2015.auton.mode;


import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.AutonOverride;
import org.simbotics.simbot2015.auton.RobotComponent;
import org.simbotics.simbot2015.auton.drive.*;
import org.simbotics.simbot2015.auton.indexer.IndexerDropSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerLastToteSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerOverride;
import org.simbotics.simbot2015.auton.indexer.IndexerSetLatches;
import org.simbotics.simbot2015.auton.indexer.IndexerToteSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerWait;
import org.simbotics.simbot2015.auton.indexer.IndexerWaitForHasTote;
import org.simbotics.simbot2015.auton.intake.BootSetOut;
import org.simbotics.simbot2015.auton.intake.CanGrabberSetOpen;
import org.simbotics.simbot2015.auton.intake.IntakeSetOpen;
import org.simbotics.simbot2015.auton.intake.IntakeSetSpeed;
import org.simbotics.simbot2015.auton.intake.IntakeSetSpeedIndependant;
import org.simbotics.simbot2015.auton.util.AutonWait;
import org.simbotics.simbot2015.util.Vect;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ThreeToteStack2056WaysToWin implements AutonMode {
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
		
		
		ab.addCommand(new DriveToPoint(new Vect(5, 20), 180,this.smallEps,1000)); // back up and go towards the scoring platform
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new DriveToPoint(new Vect(0, 31), 210,this.largeEps,true,1000,true,1.0)); // go all the way out
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeedIndependant(1,-1)); // first can spin
		
		ab.addCommand(new DriveToPoint(new Vect(-55, 31), 210,this.largeEps,1000)); // move infront of the first can
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new DriveToPoint(new Vect(-55, -5), 180,this.smallEps,1000)); // might need to add more x come in to grab 2nd tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeed(-1));

		ab.addCommand(new DriveAtSpeedUntilLightSensor(0.5, 180,true ,2500,true)); // drive until sees second tote
		ab.addCommand(new DriveWait());
		
		//ab.addCommand(new DriveToPoint(new Vect(-125,-5), 180,this.smallEps,true,350,true,1.0));
		//ab.addCommand(new DriveWait());
		//ab.addCommand(new DriveToPoint(new Vect(-122, -5), 180,this.smallEps,1500));
		//ab.addCommand(new DriveWait());
	
		ab.addCommand(new IndexerWaitForHasTote(250));
		
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new AutonWait(100));
		ab.addCommand(new IndexerToteSequence());
		ab.addCommand(new DriveToPoint(new Vect(-114,20), 180,this.smallEps,1500));
		ab.addCommand(new AutonWait(350));
		ab.addCommand(new IntakeSetSpeedIndependant(1,-1)); // first can spin
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(-122,27), 210,this.largeEps,true,1500,true,1.0));
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new DriveToPoint(new Vect(-155, 27), 210,this.largeEps,true,1500,true,1.0));  
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new DriveToPoint(new Vect(-155,-3), 180,this.smallEps,1500));
		ab.addCommand(new DriveWait());
		
		
		
		
		ab.addCommand(new IntakeSetSpeed(-1));
		
		ab.addCommand(new DriveAtSpeedUntilLightSensor(0.7, 180,true,2500,true)); // drives until it sees 3rd tote 
		ab.addCommand(new DriveWait());
		//ab.addCommand(new DriveToPoint(new Vect(-212, -5), 180,this.smallEps,false,2000));
		//ab.addCommand(new DriveWait());
		
		//ab.addCommand(new IndexerWaitForHasTote(1000));
		
		
		ab.addCommand(new IndexerLastToteSequence()); // pickup of last tote
		//ab.addCommand(new IndexerWait()); // can remove to save time
		
		ab.addCommand(new DriveToPoint(new Vect(-170, 40), 180,this.largeEps,true,2000,false,1.0));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeed(0.0));
		
		ab.addCommand(new DriveToPoint(new Vect(-160, 152), 180,this.smallEps,true,3000,true,1.0));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeed(1.0));
		ab.addCommand(new AutonOverride(RobotComponent.INDEXER));
		ab.addCommand(new IndexerDropSequence());
		
		ab.addCommand(new IndexerWait());
	
		ab.addCommand(new AutonWait(150));
		
		//ab.addCommand(new IndexerWait()); // can remove to save time
		
		ab.addCommand(new DriveToPoint(new Vect(-115, 152), 180,this.smallEps,false,-1,false,0.85));
		ab.addCommand(new DriveWait());
		
		
		
		
		return ab.getAutonList();
	}

}