package org.simbotics.simbot2015.auton.mode;


import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.drive.*;
import org.simbotics.simbot2015.auton.indexer.IndexerCanSequence;
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

public class GrabCanFirst implements AutonMode {
	private int largeEps = 0;
	private int smallEps = 0;
	
	@Override
	public AutonCommand[] getMode() {
		AutonBuilder ab = new AutonBuilder();
	
		this.largeEps =(int)SmartDashboard.getNumber("Error EPS Large: ");
		this.smallEps =(int)SmartDashboard.getNumber("Error EPS Small");
		
		System.out.println("Error Eps Large: "+this.largeEps);
		System.out.println("Error Eps Small: "+this.smallEps);
		
		
		ab.addCommand(new SetGyroOffset(-50)); 
		
		//intake can 
		ab.addCommand(new IntakeSetOpen(false));
		ab.addCommand(new CanGrabberSetOpen(true));
		ab.addCommand(new IndexerSetLatches(true));
		ab.addCommand(new BootSetOut(false));
		
		
		ab.addCommand(new IntakeSetSpeed(-0.8));
		
		ab.addCommand(new DriveToPoint(new Vect(0,0), -50, this.smallEps)); // drive forward to pick up tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new AutonWait(200));
		
		
		ab.addCommand(new CanGrabberSetOpen(false)); // close claw
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerCanSequence()); // index can
		
		ab.addCommand(new AutonWait(400));
		
		ab.addCommand(new DriveToPoint(new Vect(0,-20), 0, this.smallEps)); // drive forward to pick up tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeed(-1.0));
		
		ab.addCommand(new DriveToPoint(new Vect(40,-20), 0, this.smallEps,true)); // drive forward to pick up tote
		ab.addCommand(new IndexerWaitForHasTote(1500));
		ab.addCommand(new IndexerToteSequence(0.7));
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(70,6), 30, this.largeEps)); // drive forward to pick up tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(100,6), 30, this.smallEps)); // drive forward to pick up tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(100,-20), 0, this.smallEps)); // drive forward to pick up tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeed(-1.0));
		
		
		ab.addCommand(new DriveToPoint(new Vect(140,-20), 0, this.smallEps)); // drive forward to pick up tote
		ab.addCommand(new IndexerWaitForHasTote(1500));
		ab.addCommand(new IndexerToteSequence(0.7));
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new DriveWait());
		ab.addCommand(new IndexerWait());
		
		
		
		
		ab.addCommand(new DriveToPoint(new Vect(200,6), 30, this.largeEps)); // drive forward to pick up tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(220,6), 30, this.smallEps)); // drive forward to pick up tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(220,-20), 0, this.smallEps)); // drive forward to pick up tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeed(-1.0));
		
		ab.addCommand(new DriveToPoint(new Vect(260,-20), 0, this.smallEps)); // drive forward to pick up tote
		
		ab.addCommand(new IndexerWaitForHasTote(1500));
		ab.addCommand(new IndexerToteSequence(0.7));
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new DriveWait());
		ab.addCommand(new IndexerWait());
		
		/*
		ab.addCommand(new DriveToPoint(new Vect(90,0), 330, this.smallEps)); // drive forward to pick up tote
		ab.addCommand(new IntakeSetSpeed(-1));
		
	
		
		
		ab.addCommand(new DriveToPoint(new Vect(125,-10), 330, this.smallEps,0.5)); // drive forward to pick up tote
		ab.addCommand(new IndexerWaitForHasTote(1500));
		ab.addCommand(new IndexerToteSequence());
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(125,14), 330, this.largeEps)); // drive forward to pick up tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(210,14), 330, this.largeEps)); // drive forward to pick up tote
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(210,0), 330, this.smallEps)); // drive forward to pick up tote
		ab.addCommand(new IntakeSetSpeed(-1));
		
	
		
		
		ab.addCommand(new DriveToPoint(new Vect(245,-10), 330, this.smallEps,0.5)); // drive forward to pick up tote
		ab.addCommand(new IndexerWaitForHasTote(1500));
		ab.addCommand(new IndexerLastToteSequence());
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new DriveWait());
		
		/*ab.addCommand(new DriveToPoint(new Vect(245,150), 330, this.smallEps,1.0)); // drive forward to pick up tote
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new IndexerDropSequence());
		ab.addCommand(new IndexerWait());
		
		ab.addCommand(new DriveToPoint(new Vect(220, 175), 330,this.smallEps,1.0));
		ab.addCommand(new DriveWait());
		
		*/
		
		
		
		
		
		return ab.getAutonList();
	}

}
