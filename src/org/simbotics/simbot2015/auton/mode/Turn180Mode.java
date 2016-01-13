package org.simbotics.simbot2015.auton.mode;


import org.simbotics.simbot2015.auton.AutonCommand;
import org.simbotics.simbot2015.auton.drive.*;
import org.simbotics.simbot2015.auton.indexer.IndexerCanSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerDropSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerLastToteSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerToteSequence;
import org.simbotics.simbot2015.auton.indexer.IndexerWaitForHasTote;
import org.simbotics.simbot2015.auton.intake.CanGrabberSetOpen;
import org.simbotics.simbot2015.auton.intake.IntakeSetSpeed;
import org.simbotics.simbot2015.auton.util.AutonWait;
import org.simbotics.simbot2015.util.Vect;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turn180Mode implements AutonMode {
	private int largeEps =0;
	private int smallEps =0;
	
	@Override
	public AutonCommand[] getMode() {
		AutonBuilder ab = new AutonBuilder();
	
		this.largeEps =(int)SmartDashboard.getNumber("Error EPS Large: ");
		this.smallEps =(int)SmartDashboard.getNumber("Error EPS Small");
		
		System.out.println("Error Eps Large: "+this.largeEps);
		System.out.println("Error Eps Small: "+this.smallEps);
		
		
		
		
		ab.addCommand(new SetGyroOffset(0));
		
		
		
		ab.addCommand(new CanGrabberSetOpen(false));
		ab.addCommand(new IndexerCanSequence());
		
		ab.addCommand(new IntakeSetSpeed(-1));
		
		ab.addCommand(new DriveToPoint(new Vect(20, 0), 0,this.smallEps));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IndexerWaitForHasTote(1000));
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerToteSequence());
		
		
		ab.addCommand(new DriveToPoint(new Vect(-30, 0), 180,this.largeEps));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new AutonWait(1000));
		
		ab.addCommand(new IntakeSetSpeed(-1));
		
		ab.addCommand(new DriveToPoint(new Vect(-65, 0), 180,this.smallEps));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IndexerWaitForHasTote(1000));
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerToteSequence());
		
		
		ab.addCommand(new DriveToPoint(new Vect(-65, 28), 180,this.largeEps));
		ab.addCommand(new DriveWait());
	
		
		ab.addCommand(new DriveToPoint(new Vect(-135,28), 180,this.largeEps));
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new DriveToPoint(new Vect(-135, 0), 180,this.smallEps));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeed(-1));
		
		
		ab.addCommand(new DriveToPoint(new Vect(-165,0), 180,this.smallEps));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IndexerWaitForHasTote(1000));
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerLastToteSequence());
		
		
		ab.addCommand(new DriveToPoint(new Vect(-165, 140), 180,this.largeEps,false));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IndexerDropSequence());
		
		ab.addCommand(new DriveToPoint(new Vect(-145, 140), 180,this.largeEps,false));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IndexerCanSequence());
		
		
		
		
		return ab.getAutonList();
	}

}
