package org.simbotics.simbot2015.auton.mode;


import org.simbotics.simbot2015.auton.AutonCommand;
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

public class ToteAutoWithAccel implements AutonMode {
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
		
		
		
		ab.addCommand(new DriveToPointAccel(new Vect(0, 35), 210,this.largeEps));
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new DriveToPointAccel(new Vect(-55, 35), 210,this.largeEps));
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new DriveToPointAccel(new Vect(-55, 0), 180,this.smallEps));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeed(-1));
		
		ab.addCommand(new DriveToPointAccel(new Vect(-85, 0), 180,this.smallEps));
		ab.addCommand(new DriveWait());
	
		ab.addCommand(new IndexerWaitForHasTote(1000));
		
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerToteSequence());
		
		ab.addCommand(new DriveToPointAccel(new Vect(-85,35), 210,this.largeEps));
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new DriveToPointAccel(new Vect(-160, 35), 210,this.largeEps));
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new DriveToPointAccel(new Vect(-160,0), 180,this.smallEps));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeed(-1));
		
		
		ab.addCommand(new DriveToPointAccel(new Vect(-200, 0), 180,this.smallEps));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IndexerWaitForHasTote(1000));
		
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerLastToteSequence());
		ab.addCommand(new IndexerWait());
		
		ab.addCommand(new DriveToPointAccel(new Vect(-200, 110), 90,this.smallEps,1.0));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IndexerDropSequence());
		ab.addCommand(new IndexerWait());
		
		ab.addCommand(new DriveToPointAccel(new Vect(-200, 60), 90,this.smallEps,1.0));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPointAccel(new Vect(-250, 40), 270,this.smallEps,1.0));
		ab.addCommand(new DriveWait());
		
		
		return ab.getAutonList();
	}

}