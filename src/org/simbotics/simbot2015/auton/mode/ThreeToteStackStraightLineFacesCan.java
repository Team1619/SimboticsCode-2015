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
import org.simbotics.simbot2015.auton.intake.IntakeSetSpeedIndependant;
import org.simbotics.simbot2015.auton.util.AutonWait;
import org.simbotics.simbot2015.io.SensorInput;
import org.simbotics.simbot2015.util.Vect;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ThreeToteStackStraightLineFacesCan implements AutonMode {
	private int largeEps =0;
	private int smallEps =0;
	private SensorInput sensorIn;
	
	@Override
	public AutonCommand[] getMode() {
		AutonBuilder ab = new AutonBuilder();
		// turning mag of 0.4 pretty good 
		// trying 0.15 jump val and 0.007 accel cap 
		
		this.sensorIn = SensorInput.getInstance();
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
		//ab.addCommand(new IndexerWait());
		
		
		
		
		
		ab.addCommand(new IntakeSetSpeedIndependant(1,-1)); // first can spin
		
		ab.addCommand(new DriveTurn(new Vect(0, 0), 230,this.smallEps,1500));
		ab.addCommand(new DriveWait());
		
		//ab.addCommand(new AutonWait(500));
		
		ab.addCommand(new DriveTurn(new Vect(0, 0), 180,this.smallEps,1000));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IntakeSetSpeed(-1));
		
		ab.addCommand(new DriveToPoint(new Vect(-98, 0), 180,this.smallEps,3000));
		ab.addCommand(new DriveWait());
	
		ab.addCommand(new IndexerWaitForHasTote(700));
		
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerToteSequence()); // 2nd tote
		
		ab.addCommand(new IntakeSetSpeedIndependant(1,-1));
		
		ab.addCommand(new DriveToPoint(new Vect(-140, 0), 180,this.smallEps,1000));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveTurn(new Vect(-140,0), 230,this.smallEps,1000));
		ab.addCommand(new DriveWait());
		
		//ab.addCommand(new AutonWait(500));
		
		ab.addCommand(new DriveTurn(new Vect(-140,0), 180,this.smallEps,1000));
		ab.addCommand(new DriveWait());
	
		
		
		
		ab.addCommand(new IntakeSetSpeed(-1));
		
		
		ab.addCommand(new DriveToPoint(new Vect(-228, 3), 180,this.smallEps,false,3000));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IndexerWaitForHasTote(700));
		
		ab.addCommand(new IntakeSetSpeed(0));
		ab.addCommand(new IndexerLastToteSequence());
		//ab.addCommand(new IndexerWait()); // can remove to save time
		
		ab.addCommand(new DriveToPoint(new Vect(-232, 50), 180,this.smallEps,false,2000));
		ab.addCommand(new DriveWait());
		
		
		ab.addCommand(new DriveToPoint(new Vect(-240, 125), 90,this.smallEps,false,2000));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new IndexerDropSequence());
		ab.addCommand(new IndexerWait()); // can't move until we let go!
		
		ab.addCommand(new DriveToPoint(new Vect(-240, 70), 90,this.smallEps,false));
		ab.addCommand(new DriveWait());
		
		ab.addCommand(new DriveToPoint(new Vect(-260, 60), 270,this.smallEps,false));
		ab.addCommand(new DriveWait());
		
		
		return ab.getAutonList();
	}

}